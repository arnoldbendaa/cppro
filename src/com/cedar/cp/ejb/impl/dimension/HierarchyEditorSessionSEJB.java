// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionCSO;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.dimension.event.NewDimensionElementListEvent;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorSessionSEJB;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.EventFactory;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class HierarchyEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1><2><3><4><5><6><7><8>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<4><5><6>";
   private static final String DEPENDANTS_FOR_UPDATE = "<4><5><6>";
   private static final String DEPENDANTS_FOR_DELETE = "<4><5><6>";
   private HierarchyEditorEngine mEditorEngine;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient DimensionAccessor mDimensionAccessor;
   private HierarchyEditorSessionSSO mSSO;
   private HierarchyCK mThisTableKey;
   private DimensionEVO mDimensionEVO;
   private HierarchyEVO mHierarchyEVO;


   public HierarchyEditorSessionSEJB() {
      try {
         this.mEditorEngine = new HierarchyEditorEngine(this.getInitialContext(), (DAGContext)null, false);
      } catch (Exception var2) {
         throw new EJBException(var2.getMessage(), var2);
      }
   }

   public HierarchyEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (HierarchyCK)paramKey;

      HierarchyEditorSessionSSO e;
      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6><7><8>");
         this.mHierarchyEVO = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         this.makeItemData();
         e = this.mSSO;
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         if(var11.getCause() instanceof ValidationException) {
            throw (ValidationException)var11.getCause();
         }

         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
//         if(timer != null) {
//            timer.logInfo("getItemData", this.mThisTableKey);
//         }

      }

      return e;
   }

   private void makeItemData() throws Exception {
      this.mSSO = new HierarchyEditorSessionSSO();
      HierarchyImpl editorData = this.buildHierarchyEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(HierarchyImpl editorData) throws Exception {
      editorData.setExternalSystemRef(this.mDimensionEVO.getExternalSystemRef());
      this.setDimensionDAG(new DimensionDAG(this.getDAGContext(), this.mDimensionEVO));
      this.setHierarchyDAG(new HierarchyDAG(this.getDAGContext(), this.getDimensionDAG(), this.mHierarchyEVO));
      ModelRefImpl modelRef = this.getDimensionAccessor().queryOwningModel(this.mDimensionEVO.getPK());
      editorData.setModel(modelRef);
      if(editorData.getModel() != null) {
         this.mEditorEngine.setSaveEvents(true);
         boolean allDimensionElements = DimensionEditorSessionSEJB.isChangeManagementRequestOutstanding(modelRef.getModelPK().getModelId(), editorData.getDimensionRef().getDisplayText());
         editorData.setChangeManagementRequestsPending(allDimensionElements);
         this.mEditorEngine.setAugmentMode(Boolean.valueOf(editorData.getExternalSystemRef() != null));
      } else {
         this.mEditorEngine.setSaveEvents(false);
         this.mEditorEngine.setAugmentMode(Boolean.valueOf(false));
      }

      this.getHierarchyDAG().createLightweightDAG(editorData);
      HashMap allDimensionElements1 = new HashMap(this.getDimensionDAG().getPKMap());
      List assignedDimensionRefs = this.getHierarchyDAG().getAssignedDimensionElements();
      Iterator i = assignedDimensionRefs.iterator();

      while(i.hasNext()) {
         allDimensionElements1.remove(((DimensionElement)i.next()).getKey());
      }

      ArrayList availableDimensionElements = new ArrayList();
      i = allDimensionElements1.values().iterator();

      while(i.hasNext()) {
         availableDimensionElements.add(((DimensionElementDAG)i.next()).createLightweightElement((DimensionImpl)null));
      }

      this.mSSO.addEvent(new NewDimensionElementListEvent(availableDimensionElements));
   }

   private HierarchyImpl buildHierarchyEditData(Object thisKey) throws Exception {
      HierarchyImpl editorData = new HierarchyImpl(thisKey);
      editorData.setDimensionId(this.mHierarchyEVO.getDimensionId());
      editorData.setVisId(this.mHierarchyEVO.getVisId());
      editorData.setDescription(this.mHierarchyEVO.getDescription());
      editorData.setVersionNum(this.mHierarchyEVO.getVersionNum());
      editorData.setDimensionRef(new DimensionRefImpl(this.mDimensionEVO.getPK(), this.mDimensionEVO.getVisId(), this.mDimensionEVO.getType()));
      this.completeHierarchyEditData(editorData);
      return editorData;
   }

   private void completeHierarchyEditData(HierarchyImpl editorData) throws Exception {}

   public HierarchyEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      HierarchyEditorSessionSSO var4;
      try {
         this.mSSO = new HierarchyEditorSessionSSO();
         HierarchyImpl e = new HierarchyImpl((Object)null);
         this.completeGetNewItemData(e);
         this.mSSO.setEditorData(e);
         var4 = this.mSSO;
      } catch (EJBException var9) {
         throw var9;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage(), var10);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getNewItemData", "");
         }

      }

      return var4;
   }

   private void completeGetNewItemData(HierarchyImpl editorData) throws Exception {
      if(editorData.getDimensionRef() != null) {
         this.mDimensionEVO = this.mEditorEngine.completeGetNewItemData(editorData);
      }

   }

   public HierarchyCK insert(HierarchyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      HierarchyImpl editorData = cso.getEditorData();

      Iterator e;
      try {
         if(editorData.getExternalSystemRef() != null || editorData.getModel() == null) {
            this.mDimensionEVO = this.getDimensionAccessor().getDetails(editorData.getDimensionRef(), "");
            this.mHierarchyEVO = this.getHierarchyDAG().createEVO();
            this.mHierarchyEVO.setDimensionId(editorData.getDimensionId());
            this.mHierarchyEVO.setVisId(editorData.getVisId());
            this.mHierarchyEVO.setDescription(editorData.getDescription());
            this.updateHierarchyRelationships(editorData);
            this.completeInsertSetup(editorData);
            this.validateInsert();
            this.mDimensionEVO.addHierarchiesItem(this.mHierarchyEVO);
            this.mDimensionEVO = this.getDimensionAccessor().setAndGetDetails(this.mDimensionEVO, "<3>");
            e = this.mDimensionEVO.getHierarchies().iterator();

            while(true) {
               if(e.hasNext()) {
                  this.mHierarchyEVO = (HierarchyEVO)e.next();
                  if(!this.mHierarchyEVO.insertPending()) {
                     continue;
                  }
               }

               this.insertIntoAdditionalTables(editorData, true);
               this.sendEntityEventMessage("Hierarchy", this.mHierarchyEVO.getPK(), 1);
               HierarchyCK var5 = new HierarchyCK(this.mDimensionEVO.getPK(), this.mHierarchyEVO.getPK());
               return var5;
            }
         }

         this.submitChangeManagementRequest("insert", editorData.getDimensionRef().getNarrative(), editorData.getVisId(), editorData.getDescription(), (ModelRefImpl)editorData.getModel(), editorData.isSubmitChangeManagementRequest());
         return null;
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13.getMessage(), var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }
   }

   private void updateHierarchyRelationships(HierarchyImpl editorData) throws ValidationException {}

   private void completeInsertSetup(HierarchyImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(HierarchyImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public HierarchyCK copy(HierarchyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      HierarchyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (HierarchyCK)editorData.getPrimaryKey();

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<4><5><6>");
         HierarchyEVO e = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         this.mHierarchyEVO = e.deepClone();
         this.mHierarchyEVO.setDimensionId(editorData.getDimensionId());
         this.mHierarchyEVO.setVisId(editorData.getVisId());
         this.mHierarchyEVO.setDescription(editorData.getDescription());
         this.mHierarchyEVO.setVersionNum(0);
         this.updateHierarchyRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         DimensionPK parentKey = (DimensionPK)editorData.getDimensionRef().getPrimaryKey();
         if(!parentKey.equals(this.mDimensionEVO.getPK())) {
            this.mDimensionEVO = this.getDimensionAccessor().getDetails(parentKey, "");
         }

         this.mHierarchyEVO.prepareForInsert((DimensionEVO)null);
         this.mDimensionEVO.addHierarchiesItem(this.mHierarchyEVO);
         this.mDimensionEVO = this.getDimensionAccessor().setAndGetDetails(this.mDimensionEVO, "<3><0><1><2><3><4><5><6><7><8>");
         Iterator iter = this.mDimensionEVO.getHierarchies().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mHierarchyEVO = (HierarchyEVO)iter.next();
               if(!this.mHierarchyEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new HierarchyCK(this.mDimensionEVO.getPK(), this.mHierarchyEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("Hierarchy", this.mHierarchyEVO.getPK(), 1);
            HierarchyCK var7 = this.mThisTableKey;
            return var7;
         }
      } catch (ValidationException var13) {
         throw new EJBException(var13);
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(HierarchyImpl editorData) throws Exception {
      this.getHierarchyDAG().updateEVO(this.mHierarchyEVO);
      this.mEditorEngine.updateEVOFromRemovedElementList(this.mHierarchyEVO);
      Iterator iter = (new ArrayList(this.mHierarchyEVO.getHierarchyElements())).iterator();

      while(iter.hasNext()) {
         HierarchyElementEVO hevo = (HierarchyElementEVO)iter.next();
         this.mHierarchyEVO.changeHierarchyElementKey(hevo.getHierarchyElementId(), -hevo.getHierarchyElementId());
         this.mHierarchyEVO.changeKey(hevo, -hevo.getHierarchyElementId());
      }

   }

   public void update(HierarchyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      HierarchyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (HierarchyCK)editorData.getPrimaryKey();

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<4><5><6>");
         this.mHierarchyEVO = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         this.preValidateUpdate(editorData);
         this.mHierarchyEVO.setDimensionId(editorData.getDimensionId());
         this.mHierarchyEVO.setVisId(editorData.getVisId());
         this.mHierarchyEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mHierarchyEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mHierarchyEVO.getVersionNum());
         }

         this.updateHierarchyRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getDimensionAccessor().setDetails(this.mDimensionEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Hierarchy", this.mHierarchyEVO.getPK(), 3);
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("update", this.mThisTableKey);
         }

      }

   }

   private void preValidateUpdate(HierarchyImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(HierarchyImpl editorData) throws Exception {
      if(editorData.getModel() == null) {
         this.getHierarchyDAG().updateEVO(this.mHierarchyEVO);
         this.mEditorEngine.updateEVOFromRemovedElementList(this.mHierarchyEVO);
      } else {
         this.submitChangeManagementRequest("amend", this.mDimensionEVO.getVisId(), editorData.getVisId(), editorData.getDescription(), (ModelRefImpl)editorData.getModel(), editorData.isSubmitChangeManagementRequest());
      }

   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (HierarchyCK)paramKey;

      AllDimensionsELO e;
      try {
         e = this.getDimensionAccessor().getAllDimensions();
      } catch (Exception var8) {
         var8.printStackTrace();
         throw new EJBException(var8.getMessage(), var8);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getOwnershipData", "");
         }

      }

      return e;
   }

   private void updateAdditionalTables(HierarchyImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (HierarchyCK)paramKey;

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<4><5><6>");
         this.mHierarchyEVO = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         ModelRefImpl e = this.getDimensionAccessor().queryOwningModel(this.mDimensionEVO.getPK());
         if(this.mDimensionEVO.getExternalSystemRef() == null && e != null) {
            if(DimensionEditorSessionSEJB.isChangeManagementRequestOutstanding(e.getModelPK().getModelId(), this.mDimensionEVO.getVisId())) {
               throw new ValidationException("Outstanding change requests exist for dimension:" + this.mDimensionEVO.getVisId());
            }

            this.submitChangeManagementRequest("delete", this.mDimensionEVO.getVisId(), this.mHierarchyEVO.getVisId(), this.mHierarchyEVO.getDescription(), e, true);
         } else {
            this.validateDelete();
            this.deleteDataFromOtherTables();
            this.mDimensionEVO.deleteHierarchiesItem(this.mThisTableKey.getHierarchyPK());
            this.getDimensionAccessor().setDetails(this.mDimensionEVO);
            this.sendEntityEventMessage("Hierarchy", this.mThisTableKey.getHierarchyPK(), 2);
         }
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("delete", this.mThisTableKey);
         }

      }

   }

   private void deleteDataFromOtherTables() throws Exception {}

   private void validateDelete() throws ValidationException, Exception {
      if(this.getDimensionAccessor().queryOwningModel(this.mDimensionEVO.getPK()) != null) {
         throw new ValidationException("Owning dimension is in use in a model");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private DimensionAccessor getDimensionAccessor() throws Exception {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
      }

      return this.mDimensionAccessor;
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public EntityList queryPathToRoots(List<StructureElementKey> elements) throws EJBException {
      return (new StructureElementDAO()).queryPathToRoots(elements);
   }

   public Map<DimensionRef, EntityList> getFilteredTreeRoots(Map<DimensionRef, Map<StructureElementRef, Boolean>> filters) throws EJBException {
      StructureElementDAO seDAO = new StructureElementDAO();
      HashMap result = new HashMap();
      Iterator i$ = filters.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         result.put(entry.getKey(), seDAO.querySelectionRoots((Map)entry.getValue()));
         seDAO.deleteFdTempData();
      }

      return result;
   }

   public EntityList getImmediateChildrenWithFilter(StructureElementRef seRef, Map<StructureElementRef, Boolean> filters) throws EJBException {
      StructureElementDAO seDAO = new StructureElementDAO();
      return seDAO.querySelectionsImmediateChildren(filters, ((StructureElementRefImpl)seRef).getStructureElementPK());
   }

   private int submitChangeManagementRequest(String action, String dimVisId, String hierVisId, String hierDescription, ModelRefImpl modelRef, boolean submit) throws ValidationException, Exception {
      if(action.equalsIgnoreCase("amend") && this.mEditorEngine.getSavedEvents().isEmpty()) {
         return -1;
      } else {
         if(action.equalsIgnoreCase("delete")) {
            ModelDAO eventFactory = new ModelDAO();
            ModelEVO cm = eventFactory.getDetails(modelRef.getModelPK(), "");
            if(cm.getBudgetHierarchyId() == this.mHierarchyEVO.getHierarchyId()) {
               throw new ValidationException("You are not allowed to delete the hierarchy defined as the model\'s budget hierarchy.");
            }
         }

         EventFactory eventFactory1 = new EventFactory();
         ChangeManagementType cm1 = eventFactory1.createHierarchyCMRequest(action, hierVisId, hierDescription, dimVisId, this.mEditorEngine.getSavedEvents(), this.mEditorEngine.isAugmentMode());
         ChangeMgmtEngine engine = new ChangeMgmtEngine(this.getInitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
         engine.registerUpdateRequest(cm1);
         return submit?engine.issueUpdateTask(new UserPK(this.getUserId()), modelRef):-1;
      }
   }

   public List processEvents(List clientEvents) throws ValidationException, CPException {
      return this.mEditorEngine.processEvents(clientEvents);
   }

   public EntityList getAvailableDimensionsForInsert(int dimensionType) throws CPException {
      return this.mEditorEngine.getAvailableDimensionsForInsert(dimensionType);
   }

   private DimensionDAG getDimensionDAG() {
      return this.mEditorEngine.getDimensionDAG();
   }

   private void setDimensionDAG(DimensionDAG dimension) {
      this.mEditorEngine.setDimensionDAG(dimension);
   }

   private HierarchyDAG getHierarchyDAG() {
      return this.mEditorEngine.getHierarchyDAG();
   }

   private void setHierarchyDAG(HierarchyDAG hierarchy) {
      this.mEditorEngine.setHierarchyDAG(hierarchy);
   }

   private DAGContext getDAGContext() {
      return this.mEditorEngine.getDAGContext();
   }
}
