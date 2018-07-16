// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelWithXMLELO;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.dimension.DimensionEditorSessionCSO;
import com.cedar.cp.dto.dimension.DimensionEditorSessionSSO;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtDAO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorEngine;
import com.cedar.cp.ejb.impl.dimension.DimensionElementEVO;
import com.cedar.cp.ejb.impl.dimension.EventFactory;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class DimensionEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1><2><3><4><5><6><7><8>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1><2><3><4><5><6><7><8>";
   private DimensionEditorEngine mDimensionEditor;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient DimensionAccessor mDimensionAccessor;
   private DimensionEditorSessionSSO mSSO;
   private DimensionPK mThisTableKey;
   private DimensionEVO mDimensionEVO;


   public DimensionEditorSessionSEJB() {
      try {
         this.mDimensionEditor = new DimensionEditorEngine(this.getInitialContext(), (DAGContext)null);
      } catch (Exception var2) {
         throw new EJBException(var2.getMessage(), var2);
      }
   }

   public DimensionEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (DimensionPK)paramKey;

      DimensionEditorSessionSSO e;
      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6><7><8>");
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
         if(timer != null) {
            timer.logInfo("getItemData", this.mThisTableKey);
         }

      }

      return e;
   }

   private void makeItemData() throws Exception {
      this.mSSO = new DimensionEditorSessionSSO();
      DimensionImpl editorData = this.buildDimensionEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(DimensionImpl editorData) throws Exception {
      this.mDimensionEditor.setDimensionDAG(new DimensionDAG(this.getDAGContext(), this.mDimensionEVO));
      this.getDimensionDAG().createLightweightDAG(editorData);
      this.mSSO.setModelRef(this.getDimensionAccessor().queryOwningModel(this.mDimensionEVO.getPK()));
      editorData.setModelRef(this.mSSO.getModelRef());
      if(editorData.getModelRef() != null) {
         this.mDimensionEditor.setSaveEvents(true);
         boolean b = isChangeManagementRequestOutstanding(this.mSSO.getModelRef().getModelPK().getModelId(), editorData.getVisId());
         editorData.setChangeManagementRequestsPending(b);
      }

   }

   private DimensionImpl buildDimensionEditData(Object thisKey) throws Exception {
      DimensionImpl editorData = new DimensionImpl(thisKey);
      editorData.setVisId(this.mDimensionEVO.getVisId());
      editorData.setDescription(this.mDimensionEVO.getDescription());
      editorData.setType(this.mDimensionEVO.getType());
      editorData.setExternalSystemRef(this.mDimensionEVO.getExternalSystemRef());
      editorData.setVersionNum(this.mDimensionEVO.getVersionNum());
      this.completeDimensionEditData(editorData);
      return editorData;
   }

   private void completeDimensionEditData(DimensionImpl editorData) throws Exception {}

   public DimensionEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      DimensionEditorSessionSSO var4;
      try {
         this.mSSO = new DimensionEditorSessionSSO();
         DimensionImpl e = new DimensionImpl((Object)null);
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

   private void completeGetNewItemData(DimensionImpl editorData) throws Exception {
      this.setDimensionDAG(new DimensionDAG(this.getDAGContext(), this.getDimensionType()));
      this.getDimensionDAG().createLightweightDAG(editorData);
      editorData.nullPrimaryKey();
      this.mSSO.setModelRef((ModelRefImpl)null);
   }

   public DimensionPK insert(DimensionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DimensionImpl editorData = cso.getEditorData();

      DimensionPK e;
      try {
         this.mDimensionEVO = this.getDimensionDAG().createEVO();
         this.mDimensionEVO.setVisId(editorData.getVisId());
         this.mDimensionEVO.setDescription(editorData.getDescription());
         this.mDimensionEVO.setType(editorData.getType());
         this.mDimensionEVO.setExternalSystemRef(editorData.getExternalSystemRef());
         this.updateDimensionRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mDimensionEVO = this.getDimensionAccessor().create(this.mDimensionEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("Dimension", this.mDimensionEVO.getPK(), 1);
         e = this.mDimensionEVO.getPK();
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
            timer.logInfo("insert", "");
         }

      }

      return e;
   }

   private void updateDimensionRelationships(DimensionImpl editorData) throws ValidationException {}

   private void completeInsertSetup(DimensionImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(DimensionImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public DimensionPK copy(DimensionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DimensionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (DimensionPK)editorData.getPrimaryKey();

      DimensionPK var5;
      try {
         DimensionEVO e = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mDimensionEVO = e.deepClone();
         this.mDimensionEVO.setVisId(editorData.getVisId());
         this.mDimensionEVO.setDescription(editorData.getDescription());
         this.mDimensionEVO.setType(editorData.getType());
         this.mDimensionEVO.setExternalSystemRef(editorData.getExternalSystemRef());
         this.mDimensionEVO.setVersionNum(0);
         this.updateDimensionRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mDimensionEVO.prepareForInsert();
         this.mDimensionEVO = this.getDimensionAccessor().create(this.mDimensionEVO);
         this.mThisTableKey = this.mDimensionEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("Dimension", this.mDimensionEVO.getPK(), 1);
         var5 = this.mThisTableKey;
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }

      return var5;
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(DimensionImpl editorData) throws Exception {
      this.mLog.debug("completeCopySetUp - entry");
      this.getDimensionDAG().setVisId(editorData.getVisId());
      this.getDimensionDAG().setDescription(editorData.getDescription());
      this.getDimensionDAG().setExternalSystemRef((Integer)null);
      this.getDimensionDAG().updateEVO(this.mDimensionEVO);
      this.mLog.debug("completeCopySetUp - updated EVO, negating elements...");
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

   public void update(DimensionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DimensionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (DimensionPK)editorData.getPrimaryKey();

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mDimensionEVO.setVisId(editorData.getVisId());
         this.mDimensionEVO.setDescription(editorData.getDescription());
         this.mDimensionEVO.setType(editorData.getType());
         this.mDimensionEVO.setExternalSystemRef(editorData.getExternalSystemRef());
         if(editorData.getVersionNum() != this.mDimensionEVO.getVersionNum()) {
            //throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mDimensionEVO.getVersionNum());
            throw new VersionValidationException("Version update failure. The entity you have been editing has been updated by another user.");
         }

         this.updateDimensionRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getDimensionAccessor().setDetails(this.mDimensionEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Dimension", this.mDimensionEVO.getPK(), 3);
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

   private void preValidateUpdate(DimensionImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(DimensionImpl editorData) throws Exception {
      this.getDimensionDAG().setVisId(editorData.getVisId());
      this.getDimensionDAG().setDescription(editorData.getDescription());
      this.getDimensionDAG().setExternalSystemRef(editorData.getExternalSystemRef());
      if(editorData.getModelRef() == null) {
         this.getDimensionDAG().updateEVO(this.mDimensionEVO);
      } else {
         this.submitChangeManagementRequest(editorData, editorData.isSubmitChangeManagementRequest());
      }

   }

   private void updateAdditionalTables(DimensionImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (DimensionPK)paramKey;

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6><7><8>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mDimensionAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("Dimension", this.mThisTableKey, 2);
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
         throw new ValidationException("Dimension is in use");
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

   private void submitChangeManagementRequest(DimensionImpl editorData, boolean submit) throws ValidationException, Exception {
      if(!this.mDimensionEditor.getSavedEvents().isEmpty()) {
         EventFactory eventFactory = new EventFactory();
         ChangeManagementType cm = eventFactory.createDimensionCMRequest("amend", editorData.getVisId(), editorData.getDescription(), this.mDimensionEditor.getSavedEvents());
         ChangeMgmtEngine engine = new ChangeMgmtEngine(this.getInitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
         engine.registerUpdateRequest(cm);
         if(submit) {
            engine.issueUpdateTask(new UserPK(this.getUserId()), this.getDimensionAccessor().queryOwningModel(this.mDimensionEVO.getPK()));
         }
      }

   }

	public static boolean isChangeManagementRequestOutstanding(int modelId, String dimVisId) throws Exception {
		ChangeMgmtDAO cmDAO = new ChangeMgmtDAO();
		AllChangeMgmtsForModelWithXMLELO allCMCs = cmDAO.getAllChangeMgmtsForModelWithXML(modelId);
		JAXBContext jc = JAXBContext.newInstance("com.cedar.cp.ejb.impl.cm.xml");
		Unmarshaller u = jc.createUnmarshaller();
		for (int row = 0; row < allCMCs.getNumRows(); row++) {
			String xml = (String) allCMCs.getValueAt(row, "XmlText");
			StreamSource ss = new StreamSource(new StringReader(xml));
			ChangeManagementType cm = (ChangeManagementType) u.unmarshal(ss);
			Iterator i = cm.getEvent().iterator();
			while (i.hasNext()) {
				ChangeManagementEvent cmEvent = (ChangeManagementEvent) i.next();
				if ((cmEvent.getType() != null) && ((cmEvent.getType().value().equals("dimension")) || (cmEvent.getType().value().equalsIgnoreCase("calendar"))) && (cmEvent.getVisId() != null) && (cmEvent.getVisId().equals(dimVisId))) {
					return true;
				}
			}
		}
		return false;
	}

   public List processEvents(List events) throws ValidationException, CPException {
      return this.mDimensionEditor.processEvents(events);
   }

   private DimensionDAG getDimensionDAG() {
      return this.mDimensionEditor.getDimensionDAG();
   }

   private DAGContext getDAGContext() {
      return this.mDimensionEditor.getDAGContext();
   }

   private void setDimensionDAG(DimensionDAG dimension) {
      this.mDimensionEditor.setDimensionDAG(dimension);
   }

   protected int getDimensionType() {
      return this.mDimensionEditor.getDimensionType();
   }

   protected void setDimensionType(int dimensionType) {
      this.mDimensionEditor.setDimensionType(dimensionType);
   }
}
