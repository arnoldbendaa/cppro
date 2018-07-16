// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension.calendar;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionCSO;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionSSO;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorSessionSEJB;
import com.cedar.cp.ejb.impl.dimension.DimensionElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.calendar.CalendarEditorEngine;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class CalendarEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><3><4><5><6><1><2>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0><3><4><5><6><1><2>";
   private static final String DEPENDANTS_FOR_UPDATE = "<4><5><6>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1><2><3><4><5><6><7><8>";
   private transient Log mLog = new Log(this.getClass());
   private SessionContext mSessionContext;
   private transient DimensionAccessor mDimensionAccessor;
   private CalendarEditorSessionSSO mSSO;
   private HierarchyCK mThisTableKey;
   private DimensionEVO mDimensionEVO;
   private HierarchyEVO mHierarchyEVO;


   public CalendarEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (HierarchyCK)paramKey;

      CalendarEditorSessionSSO e;
      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0><3><4><5><6><1><2>");
         this.mHierarchyEVO = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         this.makeItemData();
         e = this.mSSO;
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getItemData", this.mThisTableKey);
         }

      }

      return e;
   }

   private void makeItemData() throws Exception {
      this.mSSO = new CalendarEditorSessionSSO();
      CalendarImpl editorData = this.buildHierarchyEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(CalendarImpl editorData) throws Exception {
      editorData.setExternalSystemRef(this.mDimensionEVO.getExternalSystemRef());
      ModelRefImpl modelRef = this.getDimensionAccessor().queryOwningModel(this.mDimensionEVO.getPK());
      editorData.setModel(modelRef);
      CalendarEditorEngine editor = new CalendarEditorEngine(this.getInitialContext(), false);
      editor.primeClientEditData(this.mDimensionEVO, editorData);
      editorData.setExternalSystemRef(this.mDimensionEVO.getExternalSystemRef());
      if(modelRef != null) {
         boolean b = DimensionEditorSessionSEJB.isChangeManagementRequestOutstanding(modelRef.getModelPK().getModelId(), editorData.getVisId());
         editorData.setChangeManagementRequestsPending(b);
      }

   }

   private CalendarImpl buildHierarchyEditData(Object thisKey) throws Exception {
      CalendarImpl editorData = new CalendarImpl(thisKey, new ArrayList());
      editorData.setDimensionId(this.mHierarchyEVO.getDimensionId());
      editorData.setVisId(this.mHierarchyEVO.getVisId());
      editorData.setDescription(this.mHierarchyEVO.getDescription());
      editorData.setVersionNum(this.mHierarchyEVO.getVersionNum());
      editorData.setDimensionRef(new DimensionRefImpl(this.mDimensionEVO.getPK(), "", this.mDimensionEVO.getType()));
      return editorData;
   }

   public CalendarEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      CalendarEditorSessionSSO var4;
      try {
         this.mSSO = new CalendarEditorSessionSSO();
         CalendarImpl e = new CalendarImpl();
         this.completeGetNewItemData(e);
         this.mSSO.setEditorData(e);
         var4 = this.mSSO;
      } catch (EJBException var9) {
         throw var9;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getNewItemData", "");
         }

      }

      return var4;
   }

   private void completeGetNewItemData(CalendarImpl editorData) throws Exception {}

   public HierarchyCK insert(CalendarEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CalendarImpl editorData = cso.getEditorData();

      HierarchyCK var7;
      try {
         CalendarEditorEngine e = new CalendarEditorEngine(this.getInitialContext(), false);
         e.insertCalendar(cso.getEditorData());
         this.mDimensionEVO = e.getDimension().createEVO();
         this.mDimensionEVO.setDimensionId(editorData.getDimensionId());
         this.mDimensionEVO.setVisId(editorData.getVisId());
         this.mDimensionEVO.setDescription(editorData.getDescription());
         this.mDimensionEVO.setExternalSystemRef(editorData.getExternalSystemRef());
         this.mHierarchyEVO = e.getHierarchy().createEVO();
         this.mDimensionEVO.addHierarchiesItem(this.mHierarchyEVO);
         this.updateHierarchyRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mDimensionEVO = this.getDimensionAccessor().create(this.mDimensionEVO);
         Collection hierarchies = this.mDimensionEVO.getHierarchies();
         Iterator i = hierarchies.iterator();
         if(i.hasNext()) {
            this.mHierarchyEVO = (HierarchyEVO)i.next();
         }

         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("Dimension", this.mDimensionEVO.getPK(), 1);
         var7 = new HierarchyCK(this.mDimensionEVO.getPK(), this.mHierarchyEVO.getPK());
      } catch (ValidationException var13) {
         throw new EJBException(var13);
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }

      return var7;
   }

   private void updateHierarchyRelationships(CalendarImpl editorData) throws ValidationException {}

   private void completeInsertSetup(CalendarImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(CalendarImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public HierarchyCK copy(CalendarEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CalendarImpl editorData = cso.getEditorData();
      this.mThisTableKey = (HierarchyCK)editorData.getPrimaryKey();

      try {
         DimensionEVO e = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0><3><4><5><6><1><2>");
         this.mDimensionEVO = e.deepClone();
         this.mDimensionEVO.setVisId(editorData.getVisId());
         this.mDimensionEVO.setDescription(editorData.getDescription());
         this.mDimensionEVO.setType(3);
         this.mDimensionEVO.setExternalSystemRef((Integer)null);
         this.mDimensionEVO.setVersionNum(0);
         this.mHierarchyEVO = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         this.mHierarchyEVO.setDimensionId(editorData.getDimensionId());
         this.mHierarchyEVO.setVisId(editorData.getVisId());
         this.mHierarchyEVO.setDescription(editorData.getDescription());
         this.mHierarchyEVO.setVersionNum(0);
         this.updateHierarchyRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mDimensionEVO.prepareForInsert();
         this.mDimensionEVO = this.getDimensionAccessor().create(this.mDimensionEVO);
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
            this.sendEntityEventMessage("Dimension", this.mDimensionEVO.getPK(), 1);
            this.sendEntityEventMessage("Hierarchy", this.mThisTableKey.getHierarchyPK(), 1);
            HierarchyCK var6 = this.mThisTableKey;
            return var6;
         }
      } catch (ValidationException var12) {
         throw new EJBException(var12);
      } catch (EJBException var13) {
         throw var13;
      } catch (Exception var14) {
         var14.printStackTrace();
         throw new EJBException(var14);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(CalendarImpl editorData) throws Exception {
      this.mDimensionEVO.setVisId(this.mHierarchyEVO.getVisId());
      CalendarEditorEngine engine = new CalendarEditorEngine(this.getInitialContext(), false);
      engine.updateCalendar(this.mDimensionEVO, this.mHierarchyEVO, editorData);
      Iterator hIter;
      if(this.mDimensionEVO.getElements() != null) {
         hIter = (new ArrayList(this.mDimensionEVO.getElements())).iterator();

         while(hIter.hasNext()) {
            DimensionElementEVO hEVO = (DimensionElementEVO)hIter.next();
            this.mDimensionEVO.changeDimensionElementKey(hEVO.getDimensionElementId(), -hEVO.getDimensionElementId());
            this.mDimensionEVO.changeKey(hEVO, -hEVO.getDimensionElementId());
         }
      }

      this.mLog.debug("completeCopySetUp - negated elements, negating hiers...");
      if(this.mDimensionEVO.getHierarchies() != null) {
         hIter = this.mDimensionEVO.getHierarchies().iterator();

         while(hIter.hasNext()) {
            HierarchyEVO hEVO1 = (HierarchyEVO)hIter.next();
            if(hEVO1.getHierarchyElements() != null) {
               Iterator iter = (new ArrayList(hEVO1.getHierarchyElements())).iterator();

               while(iter.hasNext()) {
                  HierarchyElementEVO heEVO = (HierarchyElementEVO)iter.next();
                  hEVO1.changeHierarchyElementKey(heEVO.getHierarchyElementId(), -heEVO.getHierarchyElementId());
                  hEVO1.changeKey(heEVO, -heEVO.getHierarchyElementId());
               }
            }
         }
      }

      this.mLog.debug("completeCopySetUp - exit");
   }

   public void update(CalendarEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CalendarImpl editorData = cso.getEditorData();
      this.mThisTableKey = (HierarchyCK)editorData.getPrimaryKey();

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<4><5><6>");
         this.mHierarchyEVO = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         this.preValidateUpdate(editorData);
         this.mHierarchyEVO.setDimensionId(editorData.getDimensionId());
         this.mHierarchyEVO.setVisId(editorData.getVisId());
         this.mHierarchyEVO.setDescription(editorData.getDescription());
         this.mDimensionEVO.setExternalSystemRef(editorData.getExternalSystemRef());
         if(editorData.getVersionNum() != this.mHierarchyEVO.getVersionNum()) {
            //throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mHierarchyEVO.getVersionNum());
             throw new VersionValidationException("Version update failure. The entity you have been editing has been updated by another user.");
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
         throw new EJBException(var12.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("update", this.mThisTableKey);
         }

      }

   }

   private void preValidateUpdate(CalendarImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(CalendarImpl editorData) throws Exception {
      this.mDimensionEVO.setVisId(this.mHierarchyEVO.getVisId());
      this.mDimensionEVO.setDescription(this.mHierarchyEVO.getDescription());
      if(!editorData.isChangeManagementRequestsPending()) {
         CalendarEditorEngine engine = new CalendarEditorEngine(this.getInitialContext(), false);
         engine.updateCalendar(this.mDimensionEVO, this.mHierarchyEVO, editorData);
         if(editorData.getModel() == null) {
            engine.updateEVOs(this.mDimensionEVO, this.mHierarchyEVO);
         } else if(engine.hasCalendarUpdateEvents()) {
            engine.submitChangeManagementRequest("amend", this.mHierarchyEVO.getVisId(), this.mHierarchyEVO.getDescription(), (ModelRefImpl)editorData.getModel(), this.getUserId(), editorData.isSubmitChangeManagementRequest());
         }
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
         throw new EJBException(var8.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getOwnershipData", "");
         }

      }

      return e;
   }

   private void updateAdditionalTables(CalendarImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (HierarchyCK)paramKey;

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6><7><8>");
         this.mHierarchyEVO = this.mDimensionEVO.getHierarchiesItem(this.mThisTableKey.getHierarchyPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.getDimensionAccessor().remove(this.mThisTableKey.getDimensionPK());
         this.sendEntityEventMessage("Hierarchy", this.mThisTableKey.getHierarchyPK(), 2);
         this.sendEntityEventMessage("Dimension", this.mThisTableKey.getDimensionPK(), 2);
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage());
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
         throw new ValidationException("Owning dimension is in use in a model.");
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

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) throws Exception {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (CPException var6) {
         var6.printStackTrace();
      }

   }
}
