// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionEditor;
import com.cedar.cp.api.dimension.DimensionEditorSession;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionElementEditor;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.DimensionsProcess;
import com.cedar.cp.api.dimension.HierarchyEditor;
import com.cedar.cp.api.dimension.HierarchyEditorSession;
import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.api.dimension.HierarchyElementEditor;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.HierarchysProcess;
import com.cedar.cp.api.dimension.calendar.Calendar;
import com.cedar.cp.api.dimension.calendar.CalendarEditor;
import com.cedar.cp.api.dimension.calendar.CalendarEditorSession;
import com.cedar.cp.api.dimension.calendar.CalendarPrefsEditor;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.FinanceCubeEditor;
import com.cedar.cp.api.model.FinanceCubeEditorSession;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.FinanceCubesProcess;
import com.cedar.cp.api.model.Model;
import com.cedar.cp.api.model.ModelEditor;
import com.cedar.cp.api.model.ModelEditorSession;
import com.cedar.cp.api.model.ModelsProcess;
import com.cedar.cp.api.model.mapping.MappedCalendar;
import com.cedar.cp.api.model.mapping.MappedCalendarElement;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedDataType;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedDimensionElement;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import com.cedar.cp.api.model.mapping.MappedHierarchy;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.cm.AllChangeMgmtsELO;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarLeafElementKey;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.extsys.ExternalSystemCK;
import com.cedar.cp.dto.extsys.ExternalSystemExportTaskRequest;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubesForModelELO;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.mapping.CreateAllExternalViewsTaskRequest;
import com.cedar.cp.dto.model.mapping.ImportMappedModelTaskRequest;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearImpl;
import com.cedar.cp.dto.model.mapping.MappedDataTypeImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionImpl;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeImpl;
import com.cedar.cp.dto.model.mapping.MappedHierarchyImpl;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionCSO;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionSSO;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtDAO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementActionType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEventType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementEVO;
import com.cedar.cp.ejb.impl.dimension.EventFactory;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemAccessor;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarElementEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarYearEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDataTypeEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionElementEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedFinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedHierarchyEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelAccessor;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEditorSessionSEJB$1;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;

public class MappedModelEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1><2><3><4><5><6>";
   private static final String DEPENDANTS_FOR_INSERT = "<0><1><2><3><4><5><6>";
   private static final String DEPENDANTS_FOR_COPY = "<0><1><2><3><4><5><6>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0><1><2><3><4><5><6>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1><2><3><4><5><6>";
   private int mNextId = -1;
   private transient DimensionAccessor mDimensionAccessor;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient MappedModelAccessor mMappedModelAccessor;
   private transient ModelAccessor mModelAccessor;
   private transient ExternalSystemAccessor mExternalSystemAccessor;
   private MappedModelEditorSessionSSO mSSO;
   private MappedModelPK mThisTableKey;
   private MappedModelEVO mMappedModelEVO;


   public MappedModelEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (MappedModelPK)paramKey;

      MappedModelEditorSessionSSO e;
      try {
         this.mMappedModelEVO = this.getMappedModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6>");
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
      this.mSSO = new MappedModelEditorSessionSSO();
      MappedModelImpl editorData = this.buildMappedModelEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(MappedModelImpl editorData) throws Exception {
      this.checkForOutstandingChangeManagement(editorData.getModelId());
      this.loadMappedModelFromEVO(this.mMappedModelEVO, editorData);
   }

   private MappedModelImpl buildMappedModelEditData(Object thisKey) throws Exception {
      MappedModelImpl editorData = new MappedModelImpl(thisKey);
      editorData.setModelId(this.mMappedModelEVO.getModelId());
      editorData.setExternalSystemId(this.mMappedModelEVO.getExternalSystemId());
      editorData.setCompanyVisId(this.mMappedModelEVO.getCompanyVisId());
      editorData.setLedgerVisId(this.mMappedModelEVO.getLedgerVisId());
      editorData.setVersionNum(this.mMappedModelEVO.getVersionNum());
      ModelPK key = null;
      if(this.mMappedModelEVO.getModelId() != 0) {
         key = new ModelPK(this.mMappedModelEVO.getModelId());
      }

      if(key != null) {
         ModelEVO evoExternalSystem = this.getModelAccessor().getDetails(key, "");
         editorData.setOwningModelRef(new ModelRefImpl(evoExternalSystem.getPK(), evoExternalSystem.getVisId()));
      }

      ExternalSystemPK key1 = null;
      if(this.mMappedModelEVO.getExternalSystemId() != 0) {
         key1 = new ExternalSystemPK(this.mMappedModelEVO.getExternalSystemId());
      }

      if(key1 != null) {
         ExternalSystemEVO evoExternalSystem1 = this.getExternalSystemAccessor().getDetails(key1, "");
         editorData.setExternalSystemRef(new ExternalSystemRefImpl(evoExternalSystem1.getPK(), evoExternalSystem1.getVisId()));
      }

      this.completeMappedModelEditData(editorData);
      return editorData;
   }

   private void completeMappedModelEditData(MappedModelImpl editorData) throws Exception {}

   public MappedModelEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      MappedModelEditorSessionSSO var4;
      try {
         this.mSSO = new MappedModelEditorSessionSSO();
         MappedModelImpl e = new MappedModelImpl((Object)null);
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

   private void completeGetNewItemData(MappedModelImpl editorData) throws Exception {}

   public MappedModelPK insert(MappedModelEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      MappedModelImpl editorData = cso.getEditorData();

      MappedModelPK e;
      try {
         this.mMappedModelEVO = new MappedModelEVO();
         this.mMappedModelEVO.setModelId(editorData.getModelId());
         this.mMappedModelEVO.setExternalSystemId(editorData.getExternalSystemId());
         this.mMappedModelEVO.setCompanyVisId(editorData.getCompanyVisId());
         this.mMappedModelEVO.setLedgerVisId(editorData.getLedgerVisId());
         this.updateMappedModelRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mMappedModelEVO = this.getMappedModelAccessor().create(this.mMappedModelEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("MappedModel", this.mMappedModelEVO.getPK(), 1);
         e = this.mMappedModelEVO.getPK();
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

   private void updateMappedModelRelationships(MappedModelImpl editorData) throws ValidationException {
      Object key;
      if(editorData.getOwningModelRef() != null) {
         key = editorData.getOwningModelRef().getPrimaryKey();
         if(key instanceof ModelPK) {
            this.mMappedModelEVO.setModelId(((ModelPK)key).getModelId());
         } else {
            this.mMappedModelEVO.setModelId(((ModelCK)key).getModelPK().getModelId());
         }

         try {
            this.getModelAccessor().getDetails(key, "");
         } catch (Exception var5) {
            var5.printStackTrace();
            throw new ValidationException(editorData.getOwningModelRef() + " no longer exists");
         }
      } else {
         this.mMappedModelEVO.setModelId(0);
      }

      if(editorData.getExternalSystemRef() != null) {
         key = editorData.getExternalSystemRef().getPrimaryKey();
         if(key instanceof ExternalSystemPK) {
            this.mMappedModelEVO.setExternalSystemId(((ExternalSystemPK)key).getExternalSystemId());
         } else {
            this.mMappedModelEVO.setExternalSystemId(((ExternalSystemCK)key).getExternalSystemPK().getExternalSystemId());
         }

         try {
            this.getExternalSystemAccessor().getDetails(key, "");
         } catch (Exception var4) {
            var4.printStackTrace();
            throw new ValidationException(editorData.getExternalSystemRef() + " no longer exists");
         }
      } else {
         this.mMappedModelEVO.setExternalSystemId(0);
      }

   }

   private void completeInsertSetup(MappedModelImpl editorData) throws Exception {
      this.updateMappedModelEVO(editorData, this.mMappedModelEVO);
   }

   private void insertIntoAdditionalTables(MappedModelImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public MappedModelPK copy(MappedModelEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      MappedModelImpl editorData = cso.getEditorData();
      this.mThisTableKey = (MappedModelPK)editorData.getPrimaryKey();

      MappedModelPK var5;
      try {
         MappedModelEVO e = this.getMappedModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6>");
         this.mMappedModelEVO = e.deepClone();
         this.mMappedModelEVO.setModelId(editorData.getModelId());
         this.mMappedModelEVO.setExternalSystemId(editorData.getExternalSystemId());
         this.mMappedModelEVO.setCompanyVisId(editorData.getCompanyVisId());
         this.mMappedModelEVO.setLedgerVisId(editorData.getLedgerVisId());
         this.mMappedModelEVO.setVersionNum(0);
         this.updateMappedModelRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mMappedModelEVO.prepareForInsert();
         this.mMappedModelEVO = this.getMappedModelAccessor().create(this.mMappedModelEVO);
         this.mThisTableKey = this.mMappedModelEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("MappedModel", this.mMappedModelEVO.getPK(), 1);
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

   private void completeCopySetup(MappedModelImpl editorData) throws Exception {}

   public void update(MappedModelEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      MappedModelImpl editorData = cso.getEditorData();
      this.mThisTableKey = (MappedModelPK)editorData.getPrimaryKey();

      try {
         this.mMappedModelEVO = this.getMappedModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6>");
         this.preValidateUpdate(editorData);
         this.mMappedModelEVO.setModelId(editorData.getModelId());
         this.mMappedModelEVO.setExternalSystemId(editorData.getExternalSystemId());
         this.mMappedModelEVO.setCompanyVisId(editorData.getCompanyVisId());
         this.mMappedModelEVO.setLedgerVisId(editorData.getLedgerVisId());
         if(editorData.getVersionNum() != this.mMappedModelEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mMappedModelEVO.getVersionNum());
         }

         this.updateMappedModelRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getMappedModelAccessor().setDetails(this.mMappedModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("MappedModel", this.mMappedModelEVO.getPK(), 3);
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
   
   public boolean checkForms() {
		System.out.println("check");
		return true;
   }

   private void preValidateUpdate(MappedModelImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(MappedModelImpl editorData) throws Exception {
      this.updateMappedModelEVO(editorData, this.mMappedModelEVO);
   }

   private void updateAdditionalTables(MappedModelImpl editorData) throws Exception {
      this.createCM();
   }

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (MappedModelPK)paramKey;

      try {
         this.mMappedModelEVO = this.getMappedModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mMappedModelAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("MappedModel", this.mThisTableKey, 2);
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

   private void validateDelete() throws ValidationException, Exception {}

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private MappedModelAccessor getMappedModelAccessor() throws Exception {
      if(this.mMappedModelAccessor == null) {
         this.mMappedModelAccessor = new MappedModelAccessor(this.getInitialContext());
      }

      return this.mMappedModelAccessor;
   }

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private ExternalSystemAccessor getExternalSystemAccessor() throws Exception {
      if(this.mExternalSystemAccessor == null) {
         this.mExternalSystemAccessor = new ExternalSystemAccessor(this.getInitialContext());
      }

      return this.mExternalSystemAccessor;
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

   public void refreshMappedModelCalendar(int userId, MappedModelPK paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("refreshMappedModelCalendar", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = paramKey;

      try {
         this.mMappedModelEVO = this.getMappedModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6>");
         Model e = this.loadModel(this.mMappedModelEVO.getModelId());
         CalendarImpl calendar = this.loadCalendar(e);
         Iterator i$ = this.mMappedModelEVO.getMappedCalendarYears().iterator();

         while(i$.hasNext()) {
            MappedCalendarYearEVO mcyEVO = (MappedCalendarYearEVO)i$.next();
            this.refreshMappedModelCalendarYear(this.mMappedModelEVO, mcyEVO, calendar.getLeavesForYear(mcyEVO.getYear()));
         }

         this.getMappedModelAccessor().setDetails(this.mMappedModelEVO);
      } catch (ValidationException var13) {
         throw var13;
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("refreshMappedModelCalendar", this.mThisTableKey);
         }

      }
   }

   private void refreshMappedModelCalendarYear(MappedModelEVO mappedModelEVO, MappedCalendarYearEVO mcyEVO, List<EntityRef> leavesForYear) {
      Iterator i$ = mcyEVO.getMappedCalendarElements().iterator();

      while(i$.hasNext()) {
         MappedCalendarElementEVO mceEVO = (MappedCalendarElementEVO)i$.next();
         if(mceEVO.getTmpDimensionElementIdx() != null) {
            int dimensionElementId = this.getDimensionElementIdFromRef((EntityRef)leavesForYear.get(mceEVO.getTmpDimensionElementIdx().intValue()));
            mceEVO.setDimensionElementId(Integer.valueOf(dimensionElementId));
            mceEVO.setTmpDimensionElementIdx((Integer)null);
         }
      }

   }

   public void refreshMappedModelHierarchy(int userId, MappedModelPK paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("refreshMappedModelHierarchy", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = paramKey;

      try {
         this.mMappedModelEVO = this.getMappedModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6>");
         if(this.mMappedModelEVO.getMappedDimensions() != null) {
            Iterator e = this.mMappedModelEVO.getMappedDimensions().iterator();

            while(e.hasNext()) {
               MappedDimensionEVO mdEVO = (MappedDimensionEVO)e.next();
               if(mdEVO.getMappedHierarchys() != null) {
                  Iterator i$ = mdEVO.getMappedHierarchys().iterator();

                  while(i$.hasNext()) {
                     MappedHierarchyEVO mhEVO = (MappedHierarchyEVO)i$.next();
                     this.refreshMappedHierarchy(mdEVO, mhEVO);
                  }
               }
            }
         }

         this.getMappedModelAccessor().setDetails(this.mMappedModelEVO);
      } catch (ValidationException var13) {
         throw var13;
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("refreshMappedModelHierarchy", this.mThisTableKey);
         }

      }

   }

   private void refreshMappedHierarchy(MappedDimensionEVO mdEVO, MappedHierarchyEVO mhEVO) throws Exception {
      if(mhEVO.getHierarchyId() < 0) {
         HierarcyDetailsFromDimIdELO hierarchies = (new HierarchyDAO()).getHierarcyDetailsFromDimId(mdEVO.getDimensionId());

         for(int row = 0; row < hierarchies.getNumRows(); ++row) {
            String visId = (String)hierarchies.getValueAt(row, "VisId");
            if(mhEVO.getTmpHierarchyVisId() != null && mhEVO.getTmpHierarchyVisId().equals(visId)) {
               int hierarchyId = ((Integer)hierarchies.getValueAt(row, "HierarchyId")).intValue();
               mhEVO.setHierarchyId(hierarchyId);
               mhEVO.setTmpHierarchyVisId((String)null);
               return;
            }
         }

         throw new IllegalStateException("Failed to locate hierarchy for mapped hierarchy:" + mhEVO);
      }
   }

   private void loadMappedModelFromEVO(MappedModelEVO mappedModelEVO, MappedModelImpl mappedModelImpl) throws ValidationException {
      Model model = this.loadModel(mappedModelEVO.getModelId());
      mappedModelImpl.setModelVisId(model.getVisId());
      mappedModelImpl.setModelDescription(model.getDescription());
      this.loadMappedDimensionsFromEVO(mappedModelEVO, mappedModelImpl, model);
      this.loadMappedCalendarFromEVO(mappedModelEVO, mappedModelImpl, model);
      this.loadMappedFinanceCubesFromEVO(mappedModelEVO, mappedModelImpl);
   }

   private void loadMappedFinanceCubesFromEVO(MappedModelEVO mappedModelEVO, MappedModelImpl mappedModelImpl) throws ValidationException {
      EntityList allFinanceCubes = this.getFinanceCubesForModel(mappedModelEVO.getModelId());
      Iterator i$ = mappedModelEVO.getMappedFinanceCubes().iterator();

      while(i$.hasNext()) {
         MappedFinanceCubeEVO mfcEVO = (MappedFinanceCubeEVO)i$.next();
         int rowIndex = this.findRow(allFinanceCubes, "FinanceCubeId", Integer.valueOf(mfcEVO.getFinanceCubeId()));
         if(rowIndex == -1) {
            throw new IllegalStateException("Failed to find finance cube with id:" + mfcEVO.getFinanceCubeId());
         }

         FinanceCubeRef financeCubeRef = (FinanceCubeRef)allFinanceCubes.getValueAt(rowIndex, "FinanceCube");
         String description = (String)allFinanceCubes.getValueAt(rowIndex, "Description");
         MappedFinanceCubeImpl mfcImpl = new MappedFinanceCubeImpl(mappedModelImpl, mfcEVO.getPK(), financeCubeRef.getNarrative(), description, this.loadMappedDataTypes(mfcEVO), new FinanceCubePK(mfcEVO.getFinanceCubeId()));
         mappedModelImpl.getMappedFinanceCubes().add(mfcImpl);
      }

   }

   private List<MappedDataType> loadMappedDataTypes(MappedFinanceCubeEVO mfcEVO) {
      EntityList allDataTypes = this.getAllDataTypes();
      ArrayList items = new ArrayList();
      Iterator i$ = mfcEVO.getMappedDataTypes().iterator();

      while(i$.hasNext()) {
         MappedDataTypeEVO mdtEVO = (MappedDataTypeEVO)i$.next();
         int rowIndex = this.findRow(allDataTypes, "DataTypeId", Short.valueOf((short)mdtEVO.getDataTypeId()));
         if(rowIndex == -1) {
            throw new IllegalStateException("Unable to locate data type with id:" + mdtEVO.getDataTypeId());
         }

         DataTypeRefImpl dataTypeRef = (DataTypeRefImpl)allDataTypes.getValueAt(rowIndex, "DataType");
         MappedDataTypeImpl mdtImpl = new MappedDataTypeImpl((MappedFinanceCubeImpl)null, mdtEVO.getPK(), dataTypeRef, mdtEVO.getValueTypeVisId1(), mdtEVO.getValueTypeVisId2(), mdtEVO.getValueTypeVisId3(), mdtEVO.getImpExpStatus(), mdtEVO.getImpStartYearOffset(), mdtEVO.getImpEndYearOffset(), mdtEVO.getExpStartYearOffset(), mdtEVO.getExpEndYearOffset());
         items.add(mdtImpl);
      }

      return items;
   }

   private void loadMappedCalendarFromEVO(MappedModelEVO mappedModelEVO, MappedModelImpl mappedModelImpl, Model model) throws ValidationException {
      MappedCalendarImpl mcImpl = new MappedCalendarImpl(this.loadCalendar(model), new ArrayList());
      mappedModelImpl.setMappedCalendar(mcImpl);
      Iterator i$ = mappedModelEVO.getMappedCalendarYears().iterator();

      while(i$.hasNext()) {
         MappedCalendarYearEVO mcyEVO = (MappedCalendarYearEVO)i$.next();
         MappedCalendarYearImpl mcyImpl = new MappedCalendarYearImpl(mcyEVO.getPK(), mcyEVO.getYear(), this.loadMappedCalendarYearElements(mcyEVO, mcImpl));
         mcImpl.getMappedCalendarYears().add(mcyImpl);
      }

      Collections.sort(mcImpl.getMappedCalendarYears(), new MappedModelEditorSessionSEJB$1(this));
   }

   private List<MappedCalendarElement> loadMappedCalendarYearElements(MappedCalendarYearEVO mcyEVO, MappedCalendarImpl mcImpl) throws ValidationException {
      HashMap leaves = new HashMap();
      List leavesForYear = mcImpl.getCalendar().getLeavesForYear(mcyEVO.getYear());
      Iterator items = leavesForYear.iterator();

      while(items.hasNext()) {
         EntityRef i$ = (EntityRef)items.next();
         leaves.put(Integer.valueOf(this.getDimensionElementIdFromHierarchyElementFeedKey(i$.getPrimaryKey())), i$);
      }

      ArrayList items1 = new ArrayList();

      MappedCalendarElementImpl mceImpl;
      for(Iterator i$1 = mcyEVO.getMappedCalendarElements().iterator(); i$1.hasNext(); items1.add(mceImpl)) {
         MappedCalendarElementEVO mceEVO = (MappedCalendarElementEVO)i$1.next();
         mceImpl = new MappedCalendarElementImpl();
         mceImpl.setKey(mceEVO.getPK());
         mceImpl.setPeriod(mceEVO.getPeriod());
         if(mceEVO.getDimensionElementId() != null) {
            EntityRef leafRef = (EntityRef)leaves.get(mceEVO.getDimensionElementId());
            if(leafRef == null) {
               throw new IllegalStateException("Failed to locate calendar leaf element with id:" + mceEVO.getDimensionElementId());
            }

            mceImpl.setCalendarElementRef(new EntityRefImpl(new CalendarLeafElementKey(mcyEVO.getYear(), leavesForYear.indexOf(leafRef)), leafRef.getNarrative()));
         }
      }

      return items1;
   }

   private int getDimensionElementIdFromHierarchyElementFeedKey(Object key) {
      if(key instanceof HierarchyElementFeedCK) {
         return ((HierarchyElementFeedCK)key).getHierarchyElementFeedPK().getDimensionElementId();
      } else if(key instanceof HierarchyElementFeedPK) {
         return ((HierarchyElementFeedPK)key).getDimensionElementId();
      } else {
         throw new IllegalStateException("Unexepected key object for hierarchy element feed:" + key.getClass());
      }
   }

   private void loadMappedDimensionsFromEVO(MappedModelEVO mappedModelEVO, MappedModelImpl mappedModelImpl, Model model) {
      EntityList allDimensions = this.getAllDimensions();
      Iterator i$ = mappedModelEVO.getMappedDimensions().iterator();

      while(i$.hasNext()) {
         MappedDimensionEVO mdEVO = (MappedDimensionEVO)i$.next();
         DimensionPK dimPK = new DimensionPK(mdEVO.getDimensionId());
         int dimRow = -1;
         int dimensionRef = 0;

         while(true) {
            if(dimensionRef < allDimensions.getNumRows()) {
               if(!((DimensionRefImpl)allDimensions.getValueAt(dimensionRef, "Dimension")).getPrimaryKey().equals(dimPK)) {
                  ++dimensionRef;
                  continue;
               }

               dimRow = dimensionRef;
            }

            if(dimRow == -1) {
               throw new IllegalStateException("Unable to locate dimension row for dimensionId:" + dimPK);
            }

            DimensionRef var15 = (DimensionRef)allDimensions.getValueAt(dimRow, "Dimension");
            String dimensionVisId = var15.getNarrative();
            String dimensionDescription = (String)allDimensions.getValueAt(dimRow, "Description");
            Integer dimensionType = (Integer)allDimensions.getValueAt(dimRow, "Type");
            DimensionElementEVO nullElementEVO = (new DimensionElementDAO()).getNullElementEVO(dimPK.getDimensionId());
            MappedDimensionImpl mappedDimension = new MappedDimensionImpl(mdEVO.getPK(), mappedModelImpl, var15, dimensionVisId, dimensionDescription, dimensionType.intValue(), mdEVO.getPathVisId(), this.loadMappedHierarchiesFromEVO(mdEVO, model), this.loadMappedDimensionElementsFromEVO(mdEVO), mdEVO.getExcludeDisabledLeafNodes(), nullElementEVO != null?nullElementEVO.getVisId():null, nullElementEVO != null?nullElementEVO.getDescription():null, nullElementEVO != null?Integer.valueOf(nullElementEVO.getCreditDebit()):null);
            mappedModelImpl.getDimensionMappings().add(mappedDimension);
            break;
         }
      }

   }

   private List<MappedHierarchy> loadMappedHierarchiesFromEVO(MappedDimensionEVO mappedDimensionEVO, Model model) {
      EntityList allHierarchies = this.getAllHierarchies();
      ArrayList items = new ArrayList();
      Iterator i$ = mappedDimensionEVO.getMappedHierarchys().iterator();

      while(i$.hasNext()) {
         MappedHierarchyEVO mhEVO = (MappedHierarchyEVO)i$.next();
         int hierRow = -1;
         int hierarchyRef = 0;

         while(true) {
            if(hierarchyRef < allHierarchies.getNumRows()) {
               if(!((HierarchyRefImpl)allHierarchies.getValueAt(hierarchyRef, "Hierarchy")).getPrimaryKey().equals(new HierarchyPK(mhEVO.getHierarchyId()))) {
                  ++hierarchyRef;
                  continue;
               }

               hierRow = hierarchyRef;
            }

            Object var11 = null;
            String hierarchyDescription = null;
            if(hierRow != -1) {
               var11 = (HierarchyRef)allHierarchies.getValueAt(hierRow, "Hierarchy");
               hierarchyDescription = (String)allHierarchies.getValueAt(hierRow, "Description");
            } else {
               var11 = new HierarchyRefImpl(new HierarchyPK(-1), "CM Pending...");
               hierarchyDescription = "CM Pending...";
            }

            MappedHierarchyImpl mhImpl = new MappedHierarchyImpl(mhEVO.getPK(), (HierarchyRef)var11, mhEVO.getHierarchyVisId1(), mhEVO.getHierarchyVisId2(), ((HierarchyRef)var11).getNarrative(), hierarchyDescription, model.getBudgetHierarchyRef().equals(var11));
            items.add(mhImpl);
            break;
         }
      }

      return items;
   }

   private SortedSet<MappedDimensionElement> loadMappedDimensionElementsFromEVO(MappedDimensionEVO mappedDimensionEVO) {
      TreeSet items = new TreeSet();
      Iterator i$ = mappedDimensionEVO.getMappedDimensionElements().iterator();

      while(i$.hasNext()) {
         MappedDimensionElementEVO mdeEVO = (MappedDimensionElementEVO)i$.next();
         MappedDimensionElementImpl mdeImpl = new MappedDimensionElementImpl(mdeEVO.getPK(), mdeEVO.getMappingType(), mdeEVO.getElementVisId1(), mdeEVO.getElementVisId2(), mdeEVO.getElementVisId3());
         items.add(mdeImpl);
      }

      return items;
   }

   private void updateMappedModelEVO(MappedModelImpl mappedModelImpl, MappedModelEVO mappedModelEVO) throws ValidationException, Exception {
      ModelPK modelPK = null;
      this.updateMappedDimensionEVOs(mappedModelImpl, mappedModelEVO);
      HierarchyCK newCalHierarhcyCK = this.updateMappedCalendar(mappedModelImpl, mappedModelEVO);
      if(mappedModelImpl.isNew()) {
         modelPK = this.createModel(mappedModelImpl, mappedModelEVO, newCalHierarhcyCK);
         mappedModelEVO.setModelId(modelPK.getModelId());
      } else {
         modelPK = new ModelPK(mappedModelEVO.getModelId());
      }

      this.updateMappedFinanceCubeEVOs(mappedModelImpl, mappedModelEVO, modelPK);
   }

   private void updateMappedFinanceCubeEVOs(MappedModelImpl mappedModelImpl, MappedModelEVO mappedModelEVO, ModelPK modelPK) throws ValidationException, Exception {
      List newMappedFinanceCubes = mappedModelImpl.getMappedFinanceCubes();
      Object existingEVOs = mappedModelEVO.getMappedFinanceCubes() == null?new ArrayList():mappedModelEVO.getMappedFinanceCubes();
      Iterator i$ = ((Collection)existingEVOs).iterator();

      while(i$.hasNext()) {
         MappedFinanceCubeEVO mfcImpl = (MappedFinanceCubeEVO)i$.next();
         MappedFinanceCube newFinanceCubeCK = mappedModelImpl.findMappedFinanceCube(mfcImpl.getPK());
         if(newFinanceCubeCK != null) {
            this.updateMappedFinanceCubeEVO(newFinanceCubeCK, mfcImpl);
            newMappedFinanceCubes.remove(newFinanceCubeCK);
         } else {
            mappedModelEVO.deleteMappedFinanceCubesItem(mfcImpl.getPK());
         }
      }

      i$ = newMappedFinanceCubes.iterator();

      while(i$.hasNext()) {
         MappedFinanceCube mfcImpl1 = (MappedFinanceCube)i$.next();
         FinanceCubeCK newFinanceCubeCK1 = this.createFinanceCube(mfcImpl1, modelPK);
         int newMappedFinanceCubeId = this.getNextId();
         MappedFinanceCubeEVO mfcEVO = new MappedFinanceCubeEVO(newMappedFinanceCubeId, modelPK.getModelId(), newFinanceCubeCK1.getFinanceCubePK().getFinanceCubeId(), this.createMappedDataTypes(newMappedFinanceCubeId, mfcImpl1));
         mappedModelEVO.addMappedFinanceCubesItem(mfcEVO);
      }

   }

   private Collection<MappedDataTypeEVO> createMappedDataTypes(int newMappedFinanceCubeId, MappedFinanceCube mfcImpl) throws ValidationException, Exception {
      ArrayList newItems = new ArrayList();
      Iterator i$ = mfcImpl.getMappedDataTypes().iterator();

      while(i$.hasNext()) {
         MappedDataType mdt = (MappedDataType)i$.next();
         MappedDataTypeEVO mdtEVO = new MappedDataTypeEVO(this.getNextId(), newMappedFinanceCubeId, this.queryDataTypeId(mdt.getDataTypeRef().getPrimaryKey()), mdt.getExtSysValueType(), mdt.getExtSysCurrency(), mdt.getExtSysBalType(), mdt.getImpExpStatus(), mdt.getImpStartYearOffset(), mdt.getImpEndYearOffset(), mdt.getExpStartYearOffset(), mdt.getExpEndYearOffset());
         mdtEVO.setInsertPending();
         newItems.add(mdtEVO);
      }

      return newItems;
   }

   private int queryDataTypeId(Object key) {
      return ((DataTypePK)key).getDataTypeId();
   }

   private void updateMappedFinanceCubeEVO(MappedFinanceCube mappedFinanceCubeImpl, MappedFinanceCubeEVO mappedFinanceCubeEVO) throws ValidationException, Exception {
      ArrayList newMappedDataTypes = new ArrayList(mappedFinanceCubeImpl.getMappedDataTypes());
      Object existingEVOs = mappedFinanceCubeEVO.getMappedDataTypes() == null?new ArrayList():mappedFinanceCubeEVO.getMappedDataTypes();
      Iterator i$ = ((Collection)existingEVOs).iterator();

      while(i$.hasNext()) {
         MappedDataTypeEVO mdt = (MappedDataTypeEVO)i$.next();
         MappedDataType mdtEVO = mappedFinanceCubeImpl.findMappedDataType(mdt.getPK());
         if(mdtEVO != null) {
            this.updateMappedDataTypeEVO(mdtEVO, mdt);
            newMappedDataTypes.remove(mdtEVO);
         } else {
            mappedFinanceCubeEVO.deleteMappedDataTypesItem(mdt.getPK());
         }
      }

      i$ = newMappedDataTypes.iterator();

      while(i$.hasNext()) {
         MappedDataType mdt1 = (MappedDataType)i$.next();
         MappedDataTypeEVO mdtEVO1 = new MappedDataTypeEVO(this.getNextId(), mappedFinanceCubeEVO.getMappedFinanceCubeId(), this.queryDataTypeId(mdt1.getDataTypeRef().getPrimaryKey()), mdt1.getExtSysValueType(), mdt1.getExtSysCurrency(), mdt1.getExtSysBalType(), mdt1.getImpExpStatus(), mdt1.getImpStartYearOffset(), mdt1.getImpEndYearOffset(), mdt1.getExpStartYearOffset(), mdt1.getExpEndYearOffset());
         mappedFinanceCubeEVO.addMappedDataTypesItem(mdtEVO1);
      }

      this.updateFinanceCube((MappedFinanceCubeImpl)mappedFinanceCubeImpl);
   }

   private void updateMappedDataTypeEVO(MappedDataType mappedDataTypeImpl, MappedDataTypeEVO mappedDataTypeEVO) {
      mappedDataTypeEVO.setImpExpStatus(mappedDataTypeImpl.getImpExpStatus());
      mappedDataTypeEVO.setDataTypeId(((DataTypePK)mappedDataTypeImpl.getDataTypeRef().getPrimaryKey()).getDataTypeId());
      mappedDataTypeEVO.setValueTypeVisId1(mappedDataTypeImpl.getExtSysValueType());
      mappedDataTypeEVO.setValueTypeVisId2(mappedDataTypeImpl.getExtSysCurrency());
      mappedDataTypeEVO.setValueTypeVisId3(mappedDataTypeImpl.getExtSysBalType());
      mappedDataTypeEVO.setImpStartYearOffset(mappedDataTypeImpl.getImpStartYearOffset());
      mappedDataTypeEVO.setImpEndYearOffset(mappedDataTypeImpl.getImpEndYearOffset());
      mappedDataTypeEVO.setExpStartYearOffset(mappedDataTypeImpl.getExpStartYearOffset());
      mappedDataTypeEVO.setExpEndYearOffset(mappedDataTypeImpl.getExpEndYearOffset());
   }

   private HierarchyCK updateMappedCalendar(MappedModelImpl mappedModelImpl, MappedModelEVO mappedModelEVO) throws ValidationException, Exception {
      HierarchyCK newHierarchyCK = null;
      CalendarImpl srcCalendarImpl;
      CalendarEditorSession session;
      CalendarEditor calendarEditor;
      if(mappedModelImpl.isNew()) {
         srcCalendarImpl = (CalendarImpl)mappedModelImpl.getMappedCalendar().getCalendar();
         session = this.getCPConnection().getHierarchysProcess().getCalendarEditorSession((Object)null);
         calendarEditor = session.getCalendarEditor();
         calendarEditor.setHierarchyVisId(srcCalendarImpl.getVisId());
         calendarEditor.setHierarchyDescription(srcCalendarImpl.getDescription());
         calendarEditor.setExternalSystemRef(Integer.valueOf(this.queryExternalSystemType(mappedModelImpl.getExternalSystemRef())));
         calendarEditor.setSubmitChangeManagementRequest(false);
         int trgCalendarImpl = srcCalendarImpl.getStartYear();

         while(calendarEditor.getCalendar().getStartYear() != trgCalendarImpl) {
            if(calendarEditor.getCalendar().getStartYear() > trgCalendarImpl) {
               calendarEditor.addYear(false);
               calendarEditor.removeYear(true);
            } else {
               calendarEditor.addYear(true);
               calendarEditor.removeYear(false);
            }
         }

         Iterator startYear = srcCalendarImpl.getYearSpecs().iterator();

         while(startYear.hasNext()) {
            CalendarYearSpec endYear = (CalendarYearSpec)startYear.next();
            calendarEditor.setDetail(endYear.getYear(), 1, endYear.get(1));
            calendarEditor.setDetail(endYear.getYear(), 2, endYear.get(2));
            calendarEditor.setDetail(endYear.getYear(), 3, endYear.get(3));
            calendarEditor.setDetail(endYear.getYear(), 4, endYear.get(4));
            calendarEditor.setDetail(endYear.getYear(), 5, endYear.get(5));
            calendarEditor.setDetail(endYear.getYear(), 6, endYear.get(6));
            calendarEditor.setDetail(endYear.getYear(), 7, endYear.get(7));
            calendarEditor.setDetail(endYear.getYear(), 8, endYear.get(8));
            calendarEditor.setDetail(endYear.getYear(), 9, endYear.get(9));
            if(srcCalendarImpl.getYearSpecs().indexOf(endYear) < srcCalendarImpl.getYearSpecs().size() - 1) {
               calendarEditor.addYear(true);
            }
         }

         CalendarPrefsEditor var22 = calendarEditor.getPrefsEditor();
         var22.setYearStartMonth(srcCalendarImpl.getCalendarSpec().getYearStartMonth());
         var22.setYearVisIdFormat(srcCalendarImpl.getCalendarSpecImpl().getYearVisIdFormat());
         var22.setYearDescrFormat(srcCalendarImpl.getCalendarSpecImpl().getYearDescrFormat());
         var22.setHalfYearVisIdFormat(srcCalendarImpl.getCalendarSpecImpl().getMonthVisIdFormat());
         var22.setHalfYearDescrFormat(srcCalendarImpl.getCalendarSpecImpl().getMonthDescrFormat());
         var22.setQuarterVisIdFormat(srcCalendarImpl.getCalendarSpecImpl().getQuarterVisIdFormat());
         var22.setQuarterDescrFormat(srcCalendarImpl.getCalendarSpecImpl().getQuarterDescrFormat());
         var22.setMonthVisIdFormat(srcCalendarImpl.getCalendarSpecImpl().getMonthVisIdFormat());
         var22.setMonthDescrFormat(srcCalendarImpl.getCalendarSpecImpl().getMonthDescrFormat());
         var22.setWeekVisIdFormat(srcCalendarImpl.getCalendarSpecImpl().getWeekVisIdFormat());
         var22.setWeekDescrFormat(srcCalendarImpl.getCalendarSpecImpl().getWeekDescrFormat());
         var22.setOpenVisId(srcCalendarImpl.getCalendarSpecImpl().getOpenVisId());
         var22.setOpenDescr(srcCalendarImpl.getCalendarSpecImpl().getOpenDescr());
         var22.setAdjVisId(srcCalendarImpl.getCalendarSpecImpl().getAdjVisId());
         var22.setAdjDescr(srcCalendarImpl.getCalendarSpecImpl().getAdjDescr());
         var22.setPeriod13VisIdFormat(srcCalendarImpl.getCalendarSpecImpl().getPeriod13VisId());
         var22.setPeriod13DescrFormat(srcCalendarImpl.getCalendarSpecImpl().getPeriod13Descr());
         var22.setPeriod14VisIdFormat(srcCalendarImpl.getCalendarSpecImpl().getPeriod14VisId());
         var22.setPeriod14DescrFormat(srcCalendarImpl.getCalendarSpecImpl().getPeriod14Descr());
         var22.commit();
         calendarEditor.commit();
         newHierarchyCK = (HierarchyCK)session.commit(false);
         session = this.getCPConnection().getHierarchysProcess().getCalendarEditorSession(newHierarchyCK);
         calendarEditor = session.getCalendarEditor();
         Iterator var23 = mappedModelImpl.getMappedCalendar().getMappedCalendarYears().iterator();

         while(var23.hasNext()) {
            MappedCalendarYear changeManagementRequestRequired = (MappedCalendarYear)var23.next();
            int mappedCalendar = this.getNextId();
            int newItems = changeManagementRequestRequired.getYear();
            List existingEVOs = calendarEditor.getCalendar().getLeavesForYear(newItems);
            MappedCalendarYearEVO i$ = new MappedCalendarYearEVO(mappedCalendar, mappedModelEVO.getMappedModelId(), changeManagementRequestRequired.getYear(), String.valueOf(changeManagementRequestRequired.getYear()), this.createMappedCalendarElementEVOs(mappedCalendar, changeManagementRequestRequired, existingEVOs));
            i$.insertPending();
            mappedModelEVO.addMappedCalendarYearsItem(i$);
         }
      } else {
         srcCalendarImpl = (CalendarImpl)mappedModelImpl.getMappedCalendar().getCalendar();
         session = this.getCPConnection().getHierarchysProcess().getCalendarEditorSession(srcCalendarImpl.getPrimaryKey());
         calendarEditor = session.getCalendarEditor();
         calendarEditor.setHierarchyVisId(srcCalendarImpl.getVisId());
         calendarEditor.setHierarchyDescription(srcCalendarImpl.getDescription());
         calendarEditor.setExternalSystemRef(srcCalendarImpl.getExternalSystemRef());
         calendarEditor.setSubmitChangeManagementRequest(false);
         CalendarImpl var20 = (CalendarImpl)calendarEditor.getCalendar();

         while(var20.getStartYear() != srcCalendarImpl.getStartYear() || var20.getEndYear() != srcCalendarImpl.getEndYear()) {
            if(var20.getStartYear() > srcCalendarImpl.getStartYear()) {
               calendarEditor.addYear(false);
            }

            if(var20.getEndYear() < srcCalendarImpl.getEndYear()) {
               calendarEditor.addYear(true);
            }

            if(var20.getStartYear() < srcCalendarImpl.getStartYear()) {
               calendarEditor.removeYear(false);
            }

            if(var20.getEndYear() > srcCalendarImpl.getEndYear()) {
               calendarEditor.removeYear(true);
            }
         }

         int var21 = srcCalendarImpl.getStartYear();
         int var24 = srcCalendarImpl.getEndYear();

         for(int var27 = var21; var27 <= var24; ++var27) {
            CalendarYearSpecImpl var26 = srcCalendarImpl.getYearSpec(var27);
            this.setCalendarYearSpec(calendarEditor, var26);
         }

         calendarEditor.commit();
         newHierarchyCK = (HierarchyCK)session.commit(false);
         boolean var25 = mappedModelImpl.getMappedCalendar().getCalendar().isChangeManagementUpdateRequired();
         MappedCalendar var29 = mappedModelImpl.getMappedCalendar();
         ArrayList var28 = new ArrayList(mappedModelImpl.getMappedCalendar().getMappedCalendarYears());
         Object var30 = mappedModelEVO.getMappedCalendarYears() == null?new ArrayList():mappedModelEVO.getMappedCalendarYears();
         Iterator var32 = ((Collection)var30).iterator();

         while(var32.hasNext()) {
            MappedCalendarYearEVO mcyImpl = (MappedCalendarYearEVO)var32.next();
            MappedCalendarYearImpl leavesForYear = (MappedCalendarYearImpl)var29.findMappedCalendarYear(mcyImpl.getYear());
            if(leavesForYear != null) {
               List mappedCalendarElementId = null;
               if(!var25) {
                  mappedCalendarElementId = calendarEditor.getCalendar().getLeavesForYear(leavesForYear.getYear());
               }

               this.updateMappedCalendarYearEVO(leavesForYear, mcyImpl, mappedCalendarElementId);
               var28.remove(leavesForYear);
            } else {
               mappedModelEVO.deleteMappedCalendarYearsItem(mcyImpl.getPK());
            }
         }

         var32 = var28.iterator();

         while(var32.hasNext()) {
            MappedCalendarYear var31 = (MappedCalendarYear)var32.next();
            List var33 = null;
            int var34 = this.getNextId();
            int year = var31.getYear();
            if(!var25) {
               var33 = calendarEditor.getCalendar().getLeavesForYear(year);
            }

            MappedCalendarYearEVO mcyEVO = new MappedCalendarYearEVO(var34, mappedModelEVO.getMappedModelId(), var31.getYear(), String.valueOf(var31.getYear()), this.createMappedCalendarElementEVOs(var34, var31, var33));
            mcyEVO.insertPending();
            mappedModelEVO.addMappedCalendarYearsItem(mcyEVO);
         }
      }

      return newHierarchyCK;
   }

   private void setCalendarYearSpec(CalendarEditor calendarEditor, CalendarYearSpec srcYearSpec) throws ValidationException {
      calendarEditor.setDetail(srcYearSpec.getYear(), 1, srcYearSpec.get(1));
      calendarEditor.setDetail(srcYearSpec.getYear(), 2, srcYearSpec.get(2));
      calendarEditor.setDetail(srcYearSpec.getYear(), 3, srcYearSpec.get(3));
      calendarEditor.setDetail(srcYearSpec.getYear(), 4, srcYearSpec.get(4));
      calendarEditor.setDetail(srcYearSpec.getYear(), 5, srcYearSpec.get(5));
      calendarEditor.setDetail(srcYearSpec.getYear(), 6, srcYearSpec.get(6));
      calendarEditor.setDetail(srcYearSpec.getYear(), 7, srcYearSpec.get(7));
      calendarEditor.setDetail(srcYearSpec.getYear(), 8, srcYearSpec.get(8));
      calendarEditor.setDetail(srcYearSpec.getYear(), 9, srcYearSpec.get(9));
   }

   private void updateMappedCalendarYearEVO(MappedCalendarYearImpl mcyImpl, MappedCalendarYearEVO mcyEVO, List<EntityRef> leavesForYear) {
      ArrayList newItems = new ArrayList(mcyImpl.getMappedCalendarElements());
      Object existingItems = mcyEVO.getMappedCalendarElements() != null?mcyEVO.getMappedCalendarElements():new ArrayList();
      Iterator i$ = ((Collection)existingItems).iterator();

      Integer dimensionElementId;
      while(i$.hasNext()) {
         MappedCalendarElementEVO mceImpl = (MappedCalendarElementEVO)i$.next();
         MappedCalendarElementImpl tmpDimensionElementIdx = mcyImpl.findMappedCalendarElement(mceImpl.getPK());
         if(tmpDimensionElementIdx != null) {
            mceImpl.setPeriod(tmpDimensionElementIdx.getPeriod());
            dimensionElementId = null;
            Integer mceEVO = null;
            if(tmpDimensionElementIdx.getCalendarElementRef() != null) {
               CalendarLeafElementKey cleKey = (CalendarLeafElementKey)tmpDimensionElementIdx.getCalendarElementRef().getPrimaryKey();
               if(leavesForYear != null) {
                  mceEVO = Integer.valueOf(this.getDimensionElementIdFromRef((EntityRef)leavesForYear.get(cleKey.getIndex())));
               } else {
                  dimensionElementId = Integer.valueOf(cleKey.getIndex());
               }
            }

            mceImpl.setDimensionElementId(mceEVO);
            mceImpl.setTmpDimensionElementIdx(dimensionElementId);
            newItems.remove(tmpDimensionElementIdx);
         } else {
            mcyEVO.deleteMappedCalendarElementsItem(mceImpl.getPK());
         }
      }

      i$ = newItems.iterator();

      while(i$.hasNext()) {
         MappedCalendarElement mceImpl1 = (MappedCalendarElement)i$.next();
         Integer tmpDimensionElementIdx1 = null;
         dimensionElementId = null;
         if(mceImpl1.getCalendarElementRef() != null) {
            CalendarLeafElementKey mceEVO1 = (CalendarLeafElementKey)mceImpl1.getCalendarElementRef().getPrimaryKey();
            if(leavesForYear != null) {
               dimensionElementId = Integer.valueOf(this.getDimensionElementIdFromRef((EntityRef)leavesForYear.get(mceEVO1.getIndex())));
            } else {
               tmpDimensionElementIdx1 = Integer.valueOf(mceEVO1.getIndex());
            }
         }

         MappedCalendarElementEVO mceEVO2 = new MappedCalendarElementEVO(this.getNextId(), mcyEVO.getMappedCalendarYearId(), tmpDimensionElementIdx1, dimensionElementId, mceImpl1.getPeriod(), mceImpl1.getPeriod() != null?String.valueOf(mceImpl1.getPeriod()):null);
         mcyEVO.addMappedCalendarElementsItem(mceEVO2);
         mceEVO2.setInsertPending();
      }

   }

   private Collection<MappedCalendarElementEVO> createMappedCalendarElementEVOs(int parentId, MappedCalendarYear mcy, List<EntityRef> leavesForYear) {
      ArrayList newItems = new ArrayList();
      int idx = 0;

      for(Iterator i$ = mcy.getMappedCalendarElements().iterator(); i$.hasNext(); ++idx) {
         MappedCalendarElement mceImpl = (MappedCalendarElement)i$.next();
         Integer dimensionElementId = null;
         Integer tmpDimensionElementIdx = null;
         if(mceImpl.getCalendarElementRef() != null) {
            CalendarLeafElementKey mceEVO = (CalendarLeafElementKey)mceImpl.getCalendarElementRef().getPrimaryKey();
            if(leavesForYear != null) {
               dimensionElementId = Integer.valueOf(this.getDimensionElementIdFromRef((EntityRef)leavesForYear.get(mceEVO.getIndex())));
            } else {
               tmpDimensionElementIdx = Integer.valueOf(mceEVO.getIndex());
            }
         }

         MappedCalendarElementEVO var11 = new MappedCalendarElementEVO(this.getNextId(), parentId, tmpDimensionElementIdx, dimensionElementId, mceImpl.getPeriod(), mceImpl.getPeriod() != null?String.valueOf(mceImpl.getPeriod()):null);
         newItems.add(var11);
         var11.setInsertPending();
      }

      return newItems;
   }

   private int getDimensionElementIdFromRef(EntityRef ref) {
      if(ref.getPrimaryKey() instanceof DimensionElementPK) {
         return ((DimensionElementPK)ref.getPrimaryKey()).getDimensionElementId();
      } else if(ref.getPrimaryKey() instanceof DimensionElementCK) {
         return ((DimensionElementCK)ref.getPrimaryKey()).getDimensionElementPK().getDimensionElementId();
      } else if(ref.getPrimaryKey() instanceof HierarchyElementFeedPK) {
         return ((HierarchyElementFeedPK)ref.getPrimaryKey()).getDimensionElementId();
      } else {
         throw new IllegalStateException("Unexpected key class for dimension element:" + ref.getPrimaryKey().getClass());
      }
   }

   private void updateMappedDimensionEVOs(MappedModelImpl mappedModelImpl, MappedModelEVO mappedModelEVO) throws ValidationException, Exception {
      List newMappedDimensions = mappedModelImpl.getDimensionMappings();
      Object existingEVOs = mappedModelEVO.getMappedDimensions() == null?new ArrayList():mappedModelEVO.getMappedDimensions();
      Iterator i$ = ((Collection)existingEVOs).iterator();

      while(i$.hasNext()) {
         MappedDimensionEVO mdImpl = (MappedDimensionEVO)i$.next();
         MappedDimensionImpl newDimensionId = mappedModelImpl.findMappedDimension(mdImpl.getPK());
         if(newDimensionId != null) {
            mdImpl.setPathVisId(newDimensionId.getPathVisId());
            this.updateMappedDimensionEVO(new ModelRefImpl(new ModelPK(mappedModelEVO.getModelId()), mappedModelImpl.getModelVisId()), newDimensionId, mdImpl);
            newMappedDimensions.remove(newDimensionId);
         } else {
            mappedModelEVO.deleteMappedDimensionsItem(mdImpl.getPK());
         }
      }

      i$ = newMappedDimensions.iterator();

      while(i$.hasNext()) {
         MappedDimension mdImpl1 = (MappedDimension)i$.next();
         int newDimensionId1 = this.createDimension(mdImpl1, this.queryExternalSystemType(mappedModelImpl.getExternalSystemRef()));
         int newMappedDimensionId = this.getNextId();
         MappedDimensionEVO mappedDimensionEVO = new MappedDimensionEVO(newMappedDimensionId, mappedModelEVO.getMappedModelId(), newDimensionId1, mdImpl1.getPathVisId(), mdImpl1.isDisabledLeafNodesExcluded(), this.createMappedDimensionElementEVOs(newMappedDimensionId, mdImpl1), this.createMappedHierarhyEVOs(newDimensionId1, newMappedDimensionId, mdImpl1));
         ((MappedDimensionImpl)mdImpl1).setKey(mappedDimensionEVO.getPK());
         mappedModelEVO.addMappedDimensionsItem(mappedDimensionEVO);
      }

   }

   private Collection<MappedDimensionElementEVO> createMappedDimensionElementEVOs(int newMappedDimensionId, MappedDimension mappedDimensionImpl) throws ValidationException, Exception {
      ArrayList newItems = new ArrayList();
      Iterator i$ = mappedDimensionImpl.getMappedDimensionElements().iterator();

      while(i$.hasNext()) {
         MappedDimensionElement mde = (MappedDimensionElement)i$.next();
         MappedDimensionElementEVO mdeEVO = new MappedDimensionElementEVO(this.getNextId(), newMappedDimensionId, mde.getMappingType(), mde.getVisId1(), mde.getVisId2(), mde.getVisId3());
         mdeEVO.setInsertPending();
         newItems.add(mdeEVO);
      }

      return newItems;
   }

   private Collection<MappedHierarchyEVO> createMappedHierarhyEVOs(int newDimensionId, int newMappedDimensionId, MappedDimension md) throws ValidationException, Exception {
      ArrayList newItems = new ArrayList();
      Iterator i$ = md.getMappedHierarchies().iterator();

      while(i$.hasNext()) {
         MappedHierarchy mh = (MappedHierarchy)i$.next();
         int newHierarchyId = this.createHierarchy(md, mh);
         MappedHierarchyEVO mhEVO = new MappedHierarchyEVO(this.getNextId(), newMappedDimensionId, newHierarchyId, mh.getHierarchyVisId1(), mh.getHierarchyVisId2(), (String)null);
         HierarchyRefImpl href = new HierarchyRefImpl(new HierarchyCK(new DimensionPK(newDimensionId), new HierarchyPK(newHierarchyId)), mh.getNewHierarchyVisId());
         ((MappedHierarchyImpl)mh).setHierarchyRef(href);
         mhEVO.setInsertPending();
         newItems.add(mhEVO);
      }

      return newItems;
   }

   private int createHierarchy(MappedDimension md, MappedHierarchy mh) throws ValidationException, Exception {
      HierarchysProcess process = this.getCPConnection().getHierarchysProcess();
      HierarchyEditorSession session = process.getHierarchyEditorSession((Object)null);
      HierarchyEditor editor = session.getHierarchyEditor();
      EntityList ownershipRefs = editor.getOwnershipRefs();
      EntityRef dimensionRef = this.findEntityRef(ownershipRefs, "Dimension", md.getDimensionVisId());
      if(dimensionRef == null) {
         throw new ValidationException("Failed to locate dimension ref for :" + md.getDimensionVisId());
      } else {
         editor.setDimensionRef((DimensionRef)dimensionRef);
         editor.setVisId(mh.getNewHierarchyVisId());
         editor.setDescription(mh.getNewHierarchyDescription());
         HierarchyElementEditor heEditor = editor.getElementEditor((HierarchyElement)editor.getHierarchy().getRoot());
         heEditor.setVisId(mh.getNewHierarchyVisId());
         heEditor.setDescription(".");
         heEditor.commit();
         editor.commit();
         HierarchyCK key = (HierarchyCK)session.commit(false);
         if(key != null) {
            this.getDimensionAccessor().flush(key.getDimensionPK());
            return key.getHierarchyPK().getHierarchyId();
         } else {
            return -1;
         }
      }
   }

   private int createDimension(MappedDimension mappedDimension, int extSysType) throws ValidationException, Exception {
      DimensionsProcess process = this.getCPConnection().getDimensionsProcess();
      DimensionEditorSession session = process.getDimensionEditorSession((Object)null);
      DimensionEditor editor = session.getDimensionEditor();
      editor.setSubmitChangeManagementRequest(false);
      editor.setVisId(mappedDimension.getDimensionVisId());
      editor.setDescription(mappedDimension.getDimensionDescription());
      editor.setType(mappedDimension.getDimensionType());
      editor.setExternalSystemRef(Integer.valueOf(extSysType));
      if(mappedDimension.getNullDimensionElementVisId() != null) {
         editor.insertElement(mappedDimension.getNullDimensionElementVisId(), mappedDimension.getNullDimensionElementDescription(), mappedDimension.getNullDimensionElementCreditDebit() != null?mappedDimension.getNullDimensionElementCreditDebit().intValue():2, false, false, 0, true);
      }

      editor.commit();
      DimensionPK pk = (DimensionPK)session.commit(false);
      this.getDimensionAccessor().flush(pk);
      return pk.getDimensionId();
   }

   private ModelPK createModel(MappedModelImpl mappedModelImpl, MappedModelEVO mappedModelEVO, HierarchyCK newCalendarCK) throws ValidationException, Exception {
      this.getCPConnection().getClientCache().clear();
      ModelEditorSession session = this.getCPConnection().getModelsProcess().getModelEditorSession((Object)null);
      ModelEditor modelEditor = session.getModelEditor();
      modelEditor.setVisId(mappedModelImpl.getModelVisId());
      modelEditor.setDescription(mappedModelImpl.getModelDescription());
      Iterator respAreaHierarchy = mappedModelEVO.getMappedDimensions().iterator();

      while(respAreaHierarchy.hasNext()) {
         MappedDimensionEVO hierarchyRefs = (MappedDimensionEVO)respAreaHierarchy.next();
         MappedDimensionImpl selectedRef = mappedModelImpl.findMappedDimension(hierarchyRefs.getPK());
         if(selectedRef == null) {
            throw new IllegalStateException("Unable to locate mapped dimension impl using key:" + hierarchyRefs.getMappedDimensionId());
         }

         switch(selectedRef.getDimensionType()) {
         case 1:
            modelEditor.setAccountRef(new DimensionRefImpl(new DimensionPK(hierarchyRefs.getDimensionId()), "Account Dimension", 1));
            break;
         case 2:
            modelEditor.addSelectedDimensionRef(new DimensionRefImpl(new DimensionPK(hierarchyRefs.getDimensionId()), "Business Dimension", 2));
         }
      }

      modelEditor.setCalendarRef(new DimensionRefImpl(newCalendarCK.getDimensionPK(), "Calendar Dimension", 3));
      MappedHierarchyImpl var13 = mappedModelImpl.getResponisbilityAreaHierarchy();
      if(var13 == null) {
         throw new ValidationException("The responsibility area hierarchy must be selected for the model");
      } else {
         HierarchyRef[] var14 = session.getAvailableBudgetHierarchyRefs();
         HierarchyRef var15 = null;
         HierarchyRef[] arr$ = var14;
         int len$ = var14.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            HierarchyRef hr = arr$[i$];
            if(hr.equals(var13.getHierarchyRef())) {
               var15 = hr;
               break;
            }
         }

         if(var15 != null) {
            modelEditor.setBudgetHierarchyRef(var15);
            modelEditor.commit();
            return (ModelPK)session.commit(false);
         } else {
            throw new ValidationException("Invalid responsibility area hierarchy:" + var13);
         }
      }
   }

   private CalendarImpl loadCalendar(Model model) {
      EntityList allHierarchies = this.getAllHierarchies();
      int rowIndex = this.findRow(allHierarchies, "Dimension", model.getCalendarRef().getPrimaryKey());
      if(rowIndex == -1) {
         throw new IllegalStateException("Failed to locate calendar hierarchy for dimension:" + model.getCalendarRef().getPrimaryKey());
      } else {
         EntityRef calendarRef = (EntityRef)allHierarchies.getValueAt(rowIndex, "Hierarchy");
         HierarchysProcess process = this.getCPConnection().getHierarchysProcess();
         CalendarEditorSession session = process.getCalendarEditorSession(calendarRef.getPrimaryKey());
         CalendarEditor editor = session.getCalendarEditor();
         Calendar calendar = editor.getCalendar();
         process.terminateSession(session);
         return (CalendarImpl)calendar;
      }
   }

   private Model loadModel(int modelId) throws ValidationException {
      ModelsProcess process = this.getCPConnection().getModelsProcess();
      ModelEditorSession session = process.getModelEditorSession(new ModelPK(modelId));
      ModelEditor editor = session.getModelEditor();
      Model model = editor.getModel();
      process.terminateSession(session);
      return model;
   }

   private FinanceCubeCK createFinanceCube(MappedFinanceCube mappedFinanceCubeImpl, ModelPK modelPK) throws ValidationException, Exception {
      FinanceCubesProcess process = this.getCPConnection().getFinanceCubesProcess();
      FinanceCubeEditorSession session = process.getFinanceCubeEditorSession((Object)null);
      FinanceCubeEditor editor = session.getFinanceCubeEditor();
      editor.setModelRef(new ModelRefImpl(modelPK, "Model"));
      editor.setVisId(mappedFinanceCubeImpl.getName());
      editor.setDescription(mappedFinanceCubeImpl.getDescription());
      EntityList dtEl = this.getAllDataTypes();
      Iterator key = mappedFinanceCubeImpl.getMappedDataTypes().iterator();

      while(key.hasNext()) {
         MappedDataType mdt = (MappedDataType)key.next();
         DataTypeRef dtref = null;
         int i = 0;

         while(true) {
            if(i < dtEl.getNumRows()) {
               dtref = (DataTypeRef)dtEl.getValueAt(i, "DataType");
               if(!dtref.getPrimaryKey().equals(mdt.getDataTypeRef().getPrimaryKey())) {
                  dtref = null;
                  ++i;
                  continue;
               }
            }

            editor.addSelectedDataTypeRef(dtref);
            this.mLog.info("createFinanceCube", dtref + " " + dtref.allowsConfigrableRollUp() + " subType=" + dtref.getSubType() + " measureClass=" + dtref.getMeasureClass());
            break;
         }
      }

      editor.commit();
      FinanceCubeCK var11 = (FinanceCubeCK)session.commit(false);
      process.terminateSession(session);
      return var11;
   }

   private void updateFinanceCube(MappedFinanceCubeImpl mappedFinanceCube) throws ValidationException, Exception {
      FinanceCubeCK fcCK = new FinanceCubeCK(new ModelPK(mappedFinanceCube.getMappedModel().getModelId()), mappedFinanceCube.getFinanceCubePK());
      FinanceCubeEditorSession session = this.getCPConnection().getFinanceCubesProcess().getFinanceCubeEditorSession(fcCK);
      FinanceCubeEditor editor = session.getFinanceCubeEditor();
      FinanceCube fc = editor.getFinanceCube();
      Set existingDataTypes = fc.getSelectedDataTypeRefs().keySet();
      Iterator i$ = mappedFinanceCube.getMappedDataTypes().iterator();

      while(i$.hasNext()) {
         MappedDataType mdt = (MappedDataType)i$.next();
         if(!existingDataTypes.contains(mdt.getDataTypeRef())) {
            editor.addSelectedDataTypeRef(mdt.getDataTypeRef());
         }
      }

      editor.commit();
      session.commit(false);
   }

   public EntityRef findEntityRef(EntityList list, String colName, String value) {
      for(int row = 0; row < list.getNumRows(); ++row) {
         EntityRef ref = (EntityRef)list.getValueAt(row, colName);
         if(ref.getNarrative().equals(value)) {
            return ref;
         }
      }

      return null;
   }

   public int findRow(EntityList list, String searchColumn, Object value) {
      for(int row = 0; row < list.getNumRows(); ++row) {
         Object rowValue = list.getValueAt(row, searchColumn);
         if(rowValue != null && value != null && rowValue.equals(value)) {
            return row;
         }
      }

      return -1;
   }

   private void updateMappedDimensionEVO(ModelRefImpl modelRef, MappedDimensionImpl mappedDimensionImpl, MappedDimensionEVO mappedDimensionEVO) throws ValidationException, Exception {
      mappedDimensionEVO.setExcludeDisabledLeafNodes(mappedDimensionImpl.isDisabledLeafNodesExcluded());
      mappedDimensionEVO.setPathVisId(mappedDimensionImpl.getPathVisId());
      this.updateMappedDimensionElementEVOs(mappedDimensionImpl, mappedDimensionEVO);
      this.updateMappedHierarchyEVOs(modelRef, mappedDimensionImpl, mappedDimensionEVO);
      this.updateDimension(mappedDimensionImpl, mappedDimensionEVO);
   }

   private void updateDimension(MappedDimensionImpl mappedDimensionImpl, MappedDimensionEVO mappedDimensionEVO) throws ValidationException, Exception {
      DimensionElementEVO nullElementEVO = (new DimensionElementDAO()).getNullElementEVO(mappedDimensionEVO.getDimensionId());
      if(nullElementEVO == null && mappedDimensionImpl.getNullDimensionElementVisId() != null || nullElementEVO != null && (GeneralUtils.isDifferent(mappedDimensionImpl.getNullDimensionElementVisId(), nullElementEVO.getVisId()) || GeneralUtils.isDifferent(mappedDimensionImpl.getNullDimensionElementDescription(), nullElementEVO.getDescription()) || GeneralUtils.isDifferent(mappedDimensionImpl.getNullDimensionElementCreditDebit(), Integer.valueOf(nullElementEVO.getCreditDebit())))) {
         DimensionsProcess process = this.getCPConnection().getDimensionsProcess();
         DimensionEditorSession session = process.getDimensionEditorSession(mappedDimensionImpl.getDimension().getPrimaryKey());
         DimensionEditor editor = session.getDimensionEditor();
         editor.setSubmitChangeManagementRequest(false);
         DimensionElement nullElement = editor.getDimension().getNullElement();
         if(nullElement != null) {
            if(mappedDimensionImpl.getNullDimensionElementVisId() == null) {
               HierarchyDAO pk = new HierarchyDAO();
               EntityList nullElemHiers = pk.queryDimensionHierarchiesWithElement(mappedDimensionEVO.getDimensionId(), this.queryDimensionElementId(nullElement.getKey()));

               for(int row = 0; row < nullElemHiers.getNumRows(); ++row) {
                  this.removeElementFromHerarchy(mappedDimensionEVO.getDimensionId(), ((Integer)nullElemHiers.getValueAt(row, "hierarchyId")).intValue(), this.queryDimensionElementId(nullElement.getKey()), nullElement.getVisId());
               }

               editor.removeElement(nullElement.getKey(), nullElement.getVisId());
            } else {
               DimensionElementEditor var12 = editor.getElementEditor(nullElement.getKey());
               var12.setVisId(mappedDimensionImpl.getNullDimensionElementVisId());
               var12.setDescription(mappedDimensionImpl.getNullDimensionElementDescription());
               if(mappedDimensionImpl.getNullDimensionElementCreditDebit() != null) {
                  var12.setCreditDebit(mappedDimensionImpl.getNullDimensionElementCreditDebit().intValue());
               }

               var12.commit();
            }
         } else {
            editor.insertElement(mappedDimensionImpl.getNullDimensionElementVisId(), mappedDimensionImpl.getNullDimensionElementDescription(), mappedDimensionImpl.getNullDimensionElementCreditDebit() != null?mappedDimensionImpl.getNullDimensionElementCreditDebit().intValue():2, false, false, 0, true);
         }

         editor.commit();
         DimensionPK var11 = (DimensionPK)session.commit(false);
         this.getDimensionAccessor().flush(var11);
      }

   }

   private void removeElementFromHerarchy(int dimensionId, int hierarchyId, int dimensionElementId, String visId) throws Exception {
      HierarchyCK hKey = new HierarchyCK(new DimensionPK(dimensionId), new HierarchyPK(hierarchyId));
      HierarchysProcess process = this.getCPConnection().getHierarchysProcess();
      HierarchyEditorSession session = process.getHierarchyEditorSession(hKey);
      HierarchyEditor editor = session.getHierarchyEditor();
      editor.setSubmitChangeManagementRequest(false);
      HierarchyNode node = editor.getHierarchy().findElement(visId);
      editor.removeElement(node.getPrimaryKey());
      editor.commit();
      session.commit(false);
      process.terminateSession(session);
      this.getDimensionAccessor().flush(new DimensionPK(dimensionId));
   }

   private void updateMappedDimensionElementEVOs(MappedDimensionImpl mappedDimensionImpl, MappedDimensionEVO mappedDimensionEVO) throws Exception {
      SortedSet newMappedDimensionElements = mappedDimensionImpl.getMappedDimensionElements();
      Object existingMappedDimensionElementEVOs = mappedDimensionEVO.getMappedDimensionElements() == null?new ArrayList():mappedDimensionEVO.getMappedDimensionElements();
      Iterator i$ = ((Collection)existingMappedDimensionElementEVOs).iterator();

      while(i$.hasNext()) {
         MappedDimensionElementEVO mde = (MappedDimensionElementEVO)i$.next();
         MappedDimensionElementImpl mdeEVO = (MappedDimensionElementImpl)mappedDimensionImpl.findMappedDimensionElement(mde.getPK());
         if(mdeEVO != null) {
            this.updateMappedDimensionElementEVO(mdeEVO, mde);
            newMappedDimensionElements.remove(mdeEVO);
         } else {
            mappedDimensionEVO.deleteMappedDimensionElementsItem(mde.getPK());
         }
      }

      i$ = newMappedDimensionElements.iterator();

      while(i$.hasNext()) {
         MappedDimensionElement mde1 = (MappedDimensionElement)i$.next();
         MappedDimensionElementEVO mdeEVO1 = new MappedDimensionElementEVO(this.getNextId(), mappedDimensionEVO.getMappedDimensionId(), mde1.getMappingType(), mde1.getVisId1(), mde1.getVisId2(), mde1.getVisId3());
         mappedDimensionEVO.addMappedDimensionElementsItem(mdeEVO1);
      }

   }

   private void updateMappedDimensionElementEVO(MappedDimensionElementImpl mdeImpl, MappedDimensionElementEVO mdeEVO) throws Exception {
      mdeEVO.setMappingType(mdeImpl.getMappingType());
      mdeEVO.setElementVisId1(mdeImpl.getVisId1());
      mdeEVO.setElementVisId2(mdeImpl.getVisId2());
      mdeEVO.setElementVisId3(mdeImpl.getVisId3());
   }

   private void updateMappedHierarchyEVOs(ModelRefImpl modelRef, MappedDimensionImpl mappedDimensionImpl, MappedDimensionEVO mappedDimensionEVO) throws Exception {
      List newMappedHierarchies = mappedDimensionImpl.getMappedHierarchies();
      Object existingHierarchyEVOs = mappedDimensionEVO.getMappedHierarchys() == null?new ArrayList():mappedDimensionEVO.getMappedHierarchys();
      Iterator i$ = ((Collection)existingHierarchyEVOs).iterator();

      while(i$.hasNext()) {
         MappedHierarchyEVO mh = (MappedHierarchyEVO)i$.next();
         MappedHierarchyImpl newHierarchyId = mappedDimensionImpl.findMappedHierarchy(mh.getPK());
         if(newHierarchyId != null) {
            this.updateMappedHierarchyEVO(newHierarchyId, mh);
            newMappedHierarchies.remove(newHierarchyId);
         } else {
            mappedDimensionEVO.deleteMappedHierarchysItem(mh.getPK());
            HierarchyCK mhEVO = new HierarchyCK(new DimensionPK(mappedDimensionEVO.getDimensionId()), new HierarchyPK(mh.getHierarchyId()));
            this.deleteHierarchy(modelRef, mhEVO, mappedDimensionImpl.getDimensionVisId());
         }
      }

      i$ = newMappedHierarchies.iterator();

      while(i$.hasNext()) {
         MappedHierarchy mh1 = (MappedHierarchy)i$.next();
         int newHierarchyId1 = this.createHierarchy(mappedDimensionImpl, mh1);
         MappedHierarchyEVO mhEVO1 = new MappedHierarchyEVO(this.getNextId(), mappedDimensionEVO.getMappedDimensionId(), newHierarchyId1, mh1.getHierarchyVisId1(), mh1.getHierarchyVisId2(), mh1.getNewHierarchyVisId());
         HierarchyRefImpl href = new HierarchyRefImpl(new HierarchyCK(new DimensionPK(mappedDimensionEVO.getDimensionId()), new HierarchyPK(newHierarchyId1)), mh1.getNewHierarchyVisId());
         ((MappedHierarchyImpl)mh1).setHierarchyRef(href);
         mhEVO1.setInsertPending();
         mappedDimensionEVO.addMappedHierarchysItem(mhEVO1);
      }

   }

   private void updateMappedHierarchyEVO(MappedHierarchyImpl mappedHierarchyImpl, MappedHierarchyEVO mappedHierarchyEVO) {
      mappedHierarchyEVO.setHierarchyVisId1(mappedHierarchyImpl.getHierarchyVisId1());
      mappedHierarchyEVO.setHierarchyVisId2(mappedHierarchyImpl.getHierarchyVisId2());
   }

   private EntityList getAllDimensions() {
      return this.getCPConnection().getDimensionsProcess().getAllDimensions();
   }

   private EntityList getAllHierarchies() {
      return this.getCPConnection().getHierarchysProcess().getAllHierarchys();
   }

   private EntityList getFinanceCubesForModel(int modelId) {
      return this.getCPConnection().getListHelper().getFinanceCubesForModel(modelId);
   }

   private EntityList getAllDataTypes() {
      return this.getCPConnection().getListHelper().getAllDataTypesWeb();
   }

   private void checkForOutstandingChangeManagement(int modelId) throws ValidationException {
      EntityList allCMForModel = this.getCPConnection().getListHelper().getAllChangeMgmtsForModel(modelId);
      if(allCMForModel.getNumRows() > 0) {
         throw new ValidationException("Change management requests are pending for this model. Please run the pending change management requests first.");
      }
   }

   private int getNextId() {
      return this.mNextId--;
   }

   public int issueModelImportTask(int userId, boolean safeMode, int[] mappedModelIds, int issuingTaskId) throws ValidationException, EJBException {
      ImportMappedModelTaskRequest request = new ImportMappedModelTaskRequest(safeMode, mappedModelIds);
      AllChangeMgmtsELO cmList = (new ChangeMgmtDAO()).getAllChangeMgmts();
      byte taskEL = 0;
      if(taskEL < cmList.getNumRows()) {
         throw new ValidationException("The outstanding Change Management(s) must be completed first.");
      } else {
         EntityList var13 = (new TaskDAO()).getTasks();

         int e;
         for(e = 0; e < var13.getNumRows(); ++e) {
            int taskId = ((Integer)var13.getValueAt(e, "TaskId")).intValue();
            String taskName = (String)var13.getValueAt(e, "TaskName");
            int status = ((Integer)var13.getValueAt(e, "Status")).intValue();
            if(taskName.equals("ChangeManagementTask") && status != 5 && status != 10 && status != 9) {
               throw new ValidationException("Change Management task is outstanding - task " + taskId);
            }
         }

         try {
            e = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, issuingTaskId);
            this.mLog.debug("issueModelImportTask", "taskId=" + e);
            return e;
         } catch (Exception var12) {
            var12.printStackTrace();
            throw new EJBException(var12);
         }
      }
   }
   public int multiIssueModelImportTask(int userId, boolean safeMode, int[] mappedModelIds, int issuingTaskId) throws ValidationException, EJBException {
	      ImportMappedModelTaskRequest request = new ImportMappedModelTaskRequest(safeMode, mappedModelIds);
	      AllChangeMgmtsELO cmList = (new ChangeMgmtDAO()).getAllChangeMgmts();
	      byte taskEL = 0;
	         EntityList var13 = (new TaskDAO()).getTasks();

	         int e;
	         for(e = 0; e < var13.getNumRows(); ++e) {
	            int taskId = ((Integer)var13.getValueAt(e, "TaskId")).intValue();
	            String taskName = (String)var13.getValueAt(e, "TaskName");
	            int status = ((Integer)var13.getValueAt(e, "Status")).intValue();
	            if(taskName.equals("ChangeManagementTask") && status != 5 && status != 10 && status != 9) {
	               throw new ValidationException("Change Management task is outstanding - task " + taskId);
	            }
	         }

	         try {
	            e = TaskMessageFactory.multiIssueNewTask(new InitialContext(),0, false, request, userId, issuingTaskId);
	            this.mLog.debug("issueModelImportTask", "taskId=" + e);
	            return e;
	         } catch (Exception var12) {
	            var12.printStackTrace();
	            throw new EJBException(var12);
	         }
	   }

   public int issueMappedModelExportTask(int userId, int mappedModelId, String mappedModelVisId, List<EntityRef> financeCubes) throws ValidationException, EJBException {
      try {
         ExternalSystemExportTaskRequest e = new ExternalSystemExportTaskRequest(mappedModelId, mappedModelVisId, financeCubes);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), false, e, userId);
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new EJBException(var6);
      }
   }

   private DimensionAccessor getDimensionAccessor() throws Exception {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
      }

      return this.mDimensionAccessor;
   }

   private int queryExternalSystemType(ExternalSystemRef extSysRef) throws ValidationException {
      EntityList allExternalSystems = this.getCPConnection().getExternalSystemsProcess().getAllExternalSystems();
      EntityList extSysRow = EntityListImpl.findRow(allExternalSystems, "ExternalSystem", extSysRef);
      if(extSysRow == null) {
         throw new ValidationException("Unable to locate external system entry for refreence:" + extSysRef);
      } else {
         return ((Integer)extSysRow.getValueAt(0, "SystemType")).intValue();
      }
   }

   private int issueCreateAllExternalViews(int userId) throws EJBException {
      try {
         CreateAllExternalViewsTaskRequest e = new CreateAllExternalViewsTaskRequest();
         e.addExclusiveAccess("admin");
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, userId);
      } catch (Exception var3) {
         throw new EJBException(var3);
      }
   }

   private void deleteHierarchy(ModelRefImpl modelRef, HierarchyCK hCK, String dimensionVisId) throws ValidationException, Exception {
      HierarchyDAO hDAO = new HierarchyDAO();
      HierarchyEVO hEVO = hDAO.getDetails(hCK, "");
      if(hEVO == null) {
         throw new ValidationException("Failed to locate hierarchy with key :" + hCK + " - unable to remove");
      } else {
         this.submitDeleteHierarchyCMRequest(modelRef, hCK, dimensionVisId, hEVO.getVisId(), hEVO.getDescription(), false);
      }
   }

   private int submitDeleteHierarchyCMRequest(ModelRefImpl modelRef, HierarchyCK hCK, String dimVisId, String hierVisId, String hierDescription, boolean submit) throws ValidationException, Exception {
      ModelDAO modelDAO = new ModelDAO();
      ModelEVO modelEVO = modelDAO.getDetails(modelRef.getModelPK(), "");
      if(modelEVO.getBudgetHierarchyId() == hCK.getHierarchyPK().getHierarchyId()) {
         throw new ValidationException("You are not allowed to delete the hierarchy defined as the model\'s budget hierarchy.");
      } else {
         EventFactory eventFactory = new EventFactory();
         ChangeManagementType cm = eventFactory.createHierarchyCMRequest("delete", hierVisId, hierDescription, dimVisId, new ArrayList(), false);
         ChangeMgmtEngine engine = new ChangeMgmtEngine(this.getInitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
         engine.registerUpdateRequest(cm);
         return submit?engine.issueUpdateTask(new UserPK(this.getUserId()), modelRef):-1;
      }
   }

   private int queryDimensionElementId(Object key) {
      if(key instanceof DimensionElementPK) {
         return ((DimensionElementPK)key).getDimensionElementId();
      } else if(key instanceof DimensionElementCK) {
         return ((DimensionElementCK)key).getDimensionElementPK().getDimensionElementId();
      } else {
         throw new IllegalStateException("Unknown dimension element key:" + key);
      }
   }

   private void createCM() throws Exception {
      FinanceCubesForModelELO cubesForModel = (new FinanceCubeDAO()).getFinanceCubesForModel(this.mMappedModelEVO.getModelId());
      ModelEVO evoModel = this.getModelAccessor().getDetails(new ModelPK(this.mMappedModelEVO.getModelId()), "");
      new EventFactory();
      ChangeManagementType myCm = null;

      try {
         DatatypeFactory factory = DatatypeFactory.newInstance();
         myCm = new ChangeManagementType();
         myCm.setExtractDateTime(factory.newXMLGregorianCalendar(new GregorianCalendar()));
         myCm.setSourceSystemName("CP");
      } catch (Exception var17) {
         var17.printStackTrace();
      }

      new ArrayList();
      Iterator jc = this.mMappedModelEVO.getMappedFinanceCubes().iterator();

      while(jc.hasNext()) {
         MappedFinanceCubeEVO m = (MappedFinanceCubeEVO)jc.next();
         AllDataTypeForFinanceCubeELO sw = (new DataTypeDAO()).getAllDataTypeForFinanceCube(m.getFinanceCubeId());
         boolean engine = false;
         Iterator i = m.getMappedDataTypes().iterator();

         while(i.hasNext()) {
            MappedDataTypeEVO fcRef = (MappedDataTypeEVO)i.next();
            if(fcRef.isModified() || fcRef.insertPending() || fcRef.deletePending()) {
               engine = true;
            }
         }

         if(engine) {
            if(cubesForModel == null) {
               cubesForModel = (new FinanceCubeDAO()).getFinanceCubesForModel(this.mMappedModelEVO.getModelId());
            }

            for(int var21 = 0; var21 < cubesForModel.getNumRows(); ++var21) {
               EntityRef var23 = (EntityRef)cubesForModel.getValueAt(var21, "FinanceCube");
               if(m.getFinanceCubeId() == ((FinanceCubeCK)var23.getPrimaryKey()).getFinanceCubePK().getFinanceCubeId()) {
                  String financeCubeVisId = var23.getNarrative();
                  String descr = (String)cubesForModel.getValueAt(var21, "Description");
                  EntityRef modelRef = (EntityRef)cubesForModel.getValueAt(var21, "Model");
                  ChangeManagementEvent cmEvent = new ChangeManagementEvent();
                  cmEvent.setAction(ChangeManagementActionType.fromValue("export-views"));
                  cmEvent.setType(ChangeManagementEventType.fromValue("finance-cube"));
                  cmEvent.setModel(evoModel.getVisId());
                  cmEvent.setVisId(financeCubeVisId);
                  myCm.getEvent().add(cmEvent);
               }
            }
         }
      }

      JAXBContext var18 = JAXBContext.newInstance("com.cedar.cp.ejb.impl.cm.xml");
      Marshaller var19 = var18.createMarshaller();
      var19.setProperty("jaxb.formatted.output", Boolean.TRUE);
      StringWriter var20 = new StringWriter();
      var19.marshal(myCm, var20);
      ChangeMgmtEngine var22 = new ChangeMgmtEngine(this.getInitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
      var22.registerUpdateRequest(myCm);
   }
   
   public int getModelId(Object pk) {
	   return new MappedModelDAO().getModelID((MappedModelPK)pk);
   }
   public String getTaskStatus(String taskId){
	   return new MappedModelDAO().getTaskStatus(taskId);
   }
}
