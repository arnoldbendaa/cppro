// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform.rebuild;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.DataTypeDetailsForVisIDELO;
import com.cedar.cp.dto.dimension.LeavesForParentELO;
import com.cedar.cp.dto.dimension.StructureElementForIdsELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetCycleDetailedForIdELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildEditorSessionCSO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildEditorSessionSSO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildImpl;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildTaskRequest;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.task.group.TaskRIChecker;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import com.cedar.cp.ejb.impl.xmlform.rebuild.FormRebuildDAO;
import com.cedar.cp.ejb.impl.xmlform.rebuild.FormRebuildEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class FormRebuildEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private FormRebuildEditorSessionSSO mSSO;
   private FormRebuildCK mThisTableKey;
   private ModelEVO mModelEVO;
   private FormRebuildEVO mFormRebuildEVO;


   public FormRebuildEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (FormRebuildCK)paramKey;

      FormRebuildEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mFormRebuildEVO = this.mModelEVO.getFormRebuildsItem(this.mThisTableKey.getFormRebuildPK());
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
      this.mSSO = new FormRebuildEditorSessionSSO();
      FormRebuildImpl editorData = this.buildFormRebuildEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(FormRebuildImpl editorData) throws Exception {
      if(editorData.getBudgetCycleId() != 0) {
         BudgetCycleDAO noOfDims = new BudgetCycleDAO();
         BudgetCycleDetailedForIdELO selection = noOfDims.getBudgetCycleDetailedForId(editorData.getBudgetCycleId());
         if(selection.hasNext()) {
            selection.next();
            editorData.setBudgetCycleRef(selection.getBudgetCycleEntityRef());
         }
      }

      if(editorData.getXmlformId() != 0) {
         XmlFormDAO var17 = new XmlFormDAO();
         XmlFormPK var19 = new XmlFormPK(editorData.getXmlformId());
         XmlFormEVO strucDAO = var17.getDetails(var19, "");
         editorData.setXmlFormRef(strucDAO.getEntityRef());
      }

      int var18 = 0;

      String strucMethod;
      for(int var20 = 0; var20 < 9; ++var20) {
         Class var21 = editorData.getClass();
         strucMethod = "getStructureElementId" + var20;
         Method structureElementMethod = var21.getMethod(strucMethod, new Class[0]);
         Object dtDao = structureElementMethod.invoke(editorData, new Object[0]);
         if(dtDao instanceof Integer) {
            if(((Integer)dtDao).intValue() <= 0) {
               break;
            }

            ++var18;
         }
      }

      if(var18 > 0) {
         EntityRef[] var22 = new EntityRef[var18 + 1];
         StructureElementDAO var23 = new StructureElementDAO();
         strucMethod = "getStructureId";
         String var24 = "getStructureElementId";

         for(int var25 = 0; var25 < var18; ++var25) {
            Class c = editorData.getClass();
            Method m1 = c.getMethod(strucMethod + var25, new Class[0]);
            Method m2 = c.getMethod(var24 + var25, new Class[0]);
            Integer dtELO = (Integer)m1.invoke(editorData, new Object[0]);
            Integer structureElementId = (Integer)m2.invoke(editorData, new Object[0]);
            if(var25 == var18 - 1) {
               CalendarInfoImpl strucELO = var23.getCalendarInfo(dtELO.intValue());
               CalendarElementNode cn = strucELO.getById(structureElementId);
               EntityRef ref = (EntityRef)cn.getStructureElementRef();
               StructureElementRefImpl calRef = new StructureElementRefImpl((StructureElementPK)ref.getPrimaryKey(), cn.getFullPathVisId());
               var22[var25] = calRef;
            } else {
               StructureElementForIdsELO var28 = var23.getStructureElementForIds(dtELO.intValue(), structureElementId.intValue());
               if(var28.hasNext()) {
                  var28.next();
                  var22[var25] = var28.getStructureElementEntityRef();
               }
            }
         }

         DataTypeDAO var26 = new DataTypeDAO();
         DataTypeDetailsForVisIDELO var27 = var26.getDataTypeDetailsForVisID(editorData.getDataType());
         if(var27.hasNext()) {
            var27.next();
            var22[var18] = var27.getDataTypeEntityRef();
            editorData.setSelection(var22);
         }
      }

   }

   private FormRebuildImpl buildFormRebuildEditData(Object thisKey) throws Exception {
      FormRebuildImpl editorData = new FormRebuildImpl(thisKey);
      editorData.setVisId(this.mFormRebuildEVO.getVisId());
      editorData.setDescription(this.mFormRebuildEVO.getDescription());
      editorData.setLastSubmit(this.mFormRebuildEVO.getLastSubmit());
      editorData.setXmlformId(this.mFormRebuildEVO.getXmlformId());
      editorData.setBudgetCycleId(this.mFormRebuildEVO.getBudgetCycleId());
      editorData.setStructureId0(this.mFormRebuildEVO.getStructureId0());
      editorData.setStructureId1(this.mFormRebuildEVO.getStructureId1());
      editorData.setStructureId2(this.mFormRebuildEVO.getStructureId2());
      editorData.setStructureId3(this.mFormRebuildEVO.getStructureId3());
      editorData.setStructureId4(this.mFormRebuildEVO.getStructureId4());
      editorData.setStructureId5(this.mFormRebuildEVO.getStructureId5());
      editorData.setStructureId6(this.mFormRebuildEVO.getStructureId6());
      editorData.setStructureId7(this.mFormRebuildEVO.getStructureId7());
      editorData.setStructureId8(this.mFormRebuildEVO.getStructureId8());
      editorData.setStructureElementId0(this.mFormRebuildEVO.getStructureElementId0());
      editorData.setStructureElementId1(this.mFormRebuildEVO.getStructureElementId1());
      editorData.setStructureElementId2(this.mFormRebuildEVO.getStructureElementId2());
      editorData.setStructureElementId3(this.mFormRebuildEVO.getStructureElementId3());
      editorData.setStructureElementId4(this.mFormRebuildEVO.getStructureElementId4());
      editorData.setStructureElementId5(this.mFormRebuildEVO.getStructureElementId5());
      editorData.setStructureElementId6(this.mFormRebuildEVO.getStructureElementId6());
      editorData.setStructureElementId7(this.mFormRebuildEVO.getStructureElementId7());
      editorData.setStructureElementId8(this.mFormRebuildEVO.getStructureElementId8());
      editorData.setDataType(this.mFormRebuildEVO.getDataType());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeFormRebuildEditData(editorData);
      return editorData;
   }

   private void completeFormRebuildEditData(FormRebuildImpl editorData) throws Exception {}

   public FormRebuildEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      FormRebuildEditorSessionSSO var4;
      try {
         this.mSSO = new FormRebuildEditorSessionSSO();
         FormRebuildImpl e = new FormRebuildImpl((Object)null);
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

   private void completeGetNewItemData(FormRebuildImpl editorData) throws Exception {}

   public FormRebuildCK insert(FormRebuildEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      FormRebuildImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mFormRebuildEVO = new FormRebuildEVO();
         this.mFormRebuildEVO.setVisId(editorData.getVisId());
         this.mFormRebuildEVO.setDescription(editorData.getDescription());
         this.mFormRebuildEVO.setLastSubmit(editorData.getLastSubmit());
         this.mFormRebuildEVO.setXmlformId(editorData.getXmlformId());
         this.mFormRebuildEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mFormRebuildEVO.setStructureId0(editorData.getStructureId0());
         this.mFormRebuildEVO.setStructureId1(editorData.getStructureId1());
         this.mFormRebuildEVO.setStructureId2(editorData.getStructureId2());
         this.mFormRebuildEVO.setStructureId3(editorData.getStructureId3());
         this.mFormRebuildEVO.setStructureId4(editorData.getStructureId4());
         this.mFormRebuildEVO.setStructureId5(editorData.getStructureId5());
         this.mFormRebuildEVO.setStructureId6(editorData.getStructureId6());
         this.mFormRebuildEVO.setStructureId7(editorData.getStructureId7());
         this.mFormRebuildEVO.setStructureId8(editorData.getStructureId8());
         this.mFormRebuildEVO.setStructureElementId0(editorData.getStructureElementId0());
         this.mFormRebuildEVO.setStructureElementId1(editorData.getStructureElementId1());
         this.mFormRebuildEVO.setStructureElementId2(editorData.getStructureElementId2());
         this.mFormRebuildEVO.setStructureElementId3(editorData.getStructureElementId3());
         this.mFormRebuildEVO.setStructureElementId4(editorData.getStructureElementId4());
         this.mFormRebuildEVO.setStructureElementId5(editorData.getStructureElementId5());
         this.mFormRebuildEVO.setStructureElementId6(editorData.getStructureElementId6());
         this.mFormRebuildEVO.setStructureElementId7(editorData.getStructureElementId7());
         this.mFormRebuildEVO.setStructureElementId8(editorData.getStructureElementId8());
         this.mFormRebuildEVO.setDataType(editorData.getDataType());
         this.updateFormRebuildRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addFormRebuildsItem(this.mFormRebuildEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<47>");
         Iterator e = this.mModelEVO.getFormRebuilds().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mFormRebuildEVO = (FormRebuildEVO)e.next();
               if(!this.mFormRebuildEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("FormRebuild", this.mFormRebuildEVO.getPK(), 1);
            FormRebuildCK var5 = new FormRebuildCK(this.mModelEVO.getPK(), this.mFormRebuildEVO.getPK());
            return var5;
         }
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

   private void updateFormRebuildRelationships(FormRebuildImpl editorData) throws ValidationException {}

   private void completeInsertSetup(FormRebuildImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(FormRebuildImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public FormRebuildCK copy(FormRebuildEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      FormRebuildImpl editorData = cso.getEditorData();
      this.mThisTableKey = (FormRebuildCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         FormRebuildEVO e = this.mModelEVO.getFormRebuildsItem(this.mThisTableKey.getFormRebuildPK());
         this.mFormRebuildEVO = e.deepClone();
         this.mFormRebuildEVO.setVisId(editorData.getVisId());
         this.mFormRebuildEVO.setDescription(editorData.getDescription());
         this.mFormRebuildEVO.setLastSubmit(editorData.getLastSubmit());
         this.mFormRebuildEVO.setXmlformId(editorData.getXmlformId());
         this.mFormRebuildEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mFormRebuildEVO.setStructureId0(editorData.getStructureId0());
         this.mFormRebuildEVO.setStructureId1(editorData.getStructureId1());
         this.mFormRebuildEVO.setStructureId2(editorData.getStructureId2());
         this.mFormRebuildEVO.setStructureId3(editorData.getStructureId3());
         this.mFormRebuildEVO.setStructureId4(editorData.getStructureId4());
         this.mFormRebuildEVO.setStructureId5(editorData.getStructureId5());
         this.mFormRebuildEVO.setStructureId6(editorData.getStructureId6());
         this.mFormRebuildEVO.setStructureId7(editorData.getStructureId7());
         this.mFormRebuildEVO.setStructureId8(editorData.getStructureId8());
         this.mFormRebuildEVO.setStructureElementId0(editorData.getStructureElementId0());
         this.mFormRebuildEVO.setStructureElementId1(editorData.getStructureElementId1());
         this.mFormRebuildEVO.setStructureElementId2(editorData.getStructureElementId2());
         this.mFormRebuildEVO.setStructureElementId3(editorData.getStructureElementId3());
         this.mFormRebuildEVO.setStructureElementId4(editorData.getStructureElementId4());
         this.mFormRebuildEVO.setStructureElementId5(editorData.getStructureElementId5());
         this.mFormRebuildEVO.setStructureElementId6(editorData.getStructureElementId6());
         this.mFormRebuildEVO.setStructureElementId7(editorData.getStructureElementId7());
         this.mFormRebuildEVO.setStructureElementId8(editorData.getStructureElementId8());
         this.mFormRebuildEVO.setDataType(editorData.getDataType());
         this.updateFormRebuildRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mFormRebuildEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addFormRebuildsItem(this.mFormRebuildEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<47>");
         Iterator iter = this.mModelEVO.getFormRebuilds().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mFormRebuildEVO = (FormRebuildEVO)iter.next();
               if(!this.mFormRebuildEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new FormRebuildCK(this.mModelEVO.getPK(), this.mFormRebuildEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("FormRebuild", this.mFormRebuildEVO.getPK(), 1);
            FormRebuildCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(FormRebuildImpl editorData) throws Exception {}

   public void update(FormRebuildEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      FormRebuildImpl editorData = cso.getEditorData();
      this.mThisTableKey = (FormRebuildCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mFormRebuildEVO = this.mModelEVO.getFormRebuildsItem(this.mThisTableKey.getFormRebuildPK());
         this.preValidateUpdate(editorData);
         this.mFormRebuildEVO.setVisId(editorData.getVisId());
         this.mFormRebuildEVO.setDescription(editorData.getDescription());
         this.mFormRebuildEVO.setLastSubmit(editorData.getLastSubmit());
         this.mFormRebuildEVO.setXmlformId(editorData.getXmlformId());
         this.mFormRebuildEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mFormRebuildEVO.setStructureId0(editorData.getStructureId0());
         this.mFormRebuildEVO.setStructureId1(editorData.getStructureId1());
         this.mFormRebuildEVO.setStructureId2(editorData.getStructureId2());
         this.mFormRebuildEVO.setStructureId3(editorData.getStructureId3());
         this.mFormRebuildEVO.setStructureId4(editorData.getStructureId4());
         this.mFormRebuildEVO.setStructureId5(editorData.getStructureId5());
         this.mFormRebuildEVO.setStructureId6(editorData.getStructureId6());
         this.mFormRebuildEVO.setStructureId7(editorData.getStructureId7());
         this.mFormRebuildEVO.setStructureId8(editorData.getStructureId8());
         this.mFormRebuildEVO.setStructureElementId0(editorData.getStructureElementId0());
         this.mFormRebuildEVO.setStructureElementId1(editorData.getStructureElementId1());
         this.mFormRebuildEVO.setStructureElementId2(editorData.getStructureElementId2());
         this.mFormRebuildEVO.setStructureElementId3(editorData.getStructureElementId3());
         this.mFormRebuildEVO.setStructureElementId4(editorData.getStructureElementId4());
         this.mFormRebuildEVO.setStructureElementId5(editorData.getStructureElementId5());
         this.mFormRebuildEVO.setStructureElementId6(editorData.getStructureElementId6());
         this.mFormRebuildEVO.setStructureElementId7(editorData.getStructureElementId7());
         this.mFormRebuildEVO.setStructureElementId8(editorData.getStructureElementId8());
         this.mFormRebuildEVO.setDataType(editorData.getDataType());
         this.updateFormRebuildRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("FormRebuild", this.mFormRebuildEVO.getPK(), 3);
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

   private void preValidateUpdate(FormRebuildImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(FormRebuildImpl editorData) throws Exception {}

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (FormRebuildCK)paramKey;

      AllModelsELO e;
      try {
         e = this.getModelAccessor().getAllModels();
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

   private void updateAdditionalTables(FormRebuildImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (FormRebuildCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mFormRebuildEVO = this.mModelEVO.getFormRebuildsItem(this.mThisTableKey.getFormRebuildPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteFormRebuildsItem(this.mThisTableKey.getFormRebuildPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("FormRebuild", this.mThisTableKey.getFormRebuildPK(), 2);
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
      try {
         TaskRIChecker.isInUseTaskGroup(this.getCPConnection(), this.mFormRebuildEVO.getPK(), 8);
      } catch (ValidationException var2) {
         throw new ValidationException("Form " + var2.getMessage() + " is in use in TaskGroup ");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
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

   public List submit(EntityRef ref, int userId, int issuingTaskId) {
      ArrayList ids = new ArrayList();

      try {
         this.setUserId(userId);
         Object e = ref.getPrimaryKey();
         FormRebuildPK pk = ((FormRebuildCK)e).getFormRebuildPK();
         ModelAccessor mAccessor = new ModelAccessor(new InitialContext());
         ModelEVO mEVO = mAccessor.getDetails(e, "<47>");
         FormRebuildEVO rebuildEVO = mEVO.getFormRebuildsItem(pk);
         StructureElementDAO strucDAO = new StructureElementDAO();
         LeavesForParentELO leafELO = strucDAO.getLeavesForParent(rebuildEVO.getStructureId0(), rebuildEVO.getStructureId0(), rebuildEVO.getStructureElementId0(), rebuildEVO.getStructureId0(), rebuildEVO.getStructureElementId0());
         HashMap selection = new HashMap();
         int noOfDims = 0;
         Class c = rebuildEVO.getClass();

         String structureElementMethod;
         for(int strucMethod = 0; strucMethod < 9; ++strucMethod) {
            structureElementMethod = "getStructureElementId" + strucMethod;
            Method batchCount = c.getMethod(structureElementMethod, new Class[0]);
            Object size = batchCount.invoke(rebuildEVO, new Object[0]);
            if(size instanceof Integer) {
               if(((Integer)size).intValue() <= 0) {
                  break;
               }

               ++noOfDims;
            }
         }

         String var29 = "getStructureId";
         structureElementMethod = "getStructureElementId";

         int var30;
         for(var30 = 0; var30 < noOfDims; ++var30) {
            if(var30 == 0) {
               selection.put(Integer.valueOf(var30), (Object)null);
            } else {
               Method batchSize = c.getMethod(var29 + var30, new Class[0]);
               Method respArea = c.getMethod(structureElementMethod + var30, new Class[0]);
               Integer var33 = (Integer)batchSize.invoke(rebuildEVO, new Object[0]);
               Integer maxJobs = (Integer)respArea.invoke(rebuildEVO, new Object[0]);
               StructureElementForIdsELO doa = strucDAO.getStructureElementForIds(var33.intValue(), maxJobs.intValue());
               if(doa.hasNext()) {
                  doa.next();
                  selection.put(Integer.valueOf(var30), Integer.valueOf(doa.getStructureElementId()));
               }
            }
         }

         var30 = 0;
         int var32 = leafELO.getNumRows();
         int var31 = SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "SYS: Form Rebuild max jobs", 2);
         int var34 = var32 / var31;
         if(var34 * var31 < var32) {
            ++var34;
         }

         ArrayList var37 = new ArrayList();

         for(int var35 = 0; var35 < var32; ++var35) {
            ++var30;
            leafELO.next();
            var37.add(Integer.valueOf(leafELO.getStructureElementId()));
            if(var30 == var34 || var35 == var32 - 1) {
               FormRebuildTaskRequest request = new FormRebuildTaskRequest();
               request.setModelId(rebuildEVO.getModelId());
               request.setBudgetCycleId(rebuildEVO.getBudgetCycleId());
               request.setFormId(rebuildEVO.getXmlformId());
               request.setDataType(rebuildEVO.getDataType());
               request.setSelectionCriteria(selection);
               request.setRespArea(var37);
               ids.add(Integer.valueOf(TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, issuingTaskId)));
               var30 = 0;
               var37 = new ArrayList();
            }
         }

         FormRebuildDAO var36 = new FormRebuildDAO();
         var36.setLastSubmit(rebuildEVO.getFormRebuildId());
      } catch (Exception var27) {
         var27.printStackTrace();
      } finally {
         this.setUserId(0);
      }

      return ids;
   }
}
