// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.virement.VirementQueryParams;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.FinanceCubeDetailsELO;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointImpl;
import com.cedar.cp.dto.model.virement.VirementGroupImpl;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementLineSpreadImpl;
import com.cedar.cp.dto.model.virement.VirementQueryParamsImpl;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionCSO;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.dto.model.virement.VirementTaskRequest;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointLinkEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthorisersEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementLineSpreadEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB$1;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB$2;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB$3;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB$4;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestGroupEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestLineEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.task.UserMailer;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.virement.VirementAuthorisationEngine;
import com.cedar.cp.ejb.impl.virement.VirementUpdateEngine;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class VirementRequestEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<30><31><32><33><34><35>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<30><31><32><33><34><35>";
   private static final String DEPENDANTS_FOR_UPDATE = "<30><31><32><33><34><35>";
   private static final String DEPENDANTS_FOR_DELETE = "<30><31><32><33><34><35>";
   private Map<Integer, Profile> mProfileCache = new HashMap();
   private WeightingProfileDAO mWeightingProfileDAO;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private transient UserAccessor mUserAccessor;
   private VirementRequestEditorSessionSSO mSSO;
   private VirementRequestCK mThisTableKey;
   private ModelEVO mModelEVO;
   private VirementRequestEVO mVirementRequestEVO;


   public VirementRequestEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (VirementRequestCK)paramKey;

      VirementRequestEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<30><31><32><33><34><35>");
         this.mVirementRequestEVO = this.mModelEVO.getVirementRequestsItem(this.mThisTableKey.getVirementRequestPK());
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
      this.mSSO = new VirementRequestEditorSessionSSO();
      VirementRequestImpl editorData = this.buildVirementRequestEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(VirementRequestImpl editorData) throws Exception {
      this.loadSubTables(editorData);
   }

   private VirementRequestImpl buildVirementRequestEditData(Object thisKey) throws Exception {
      VirementRequestImpl editorData = new VirementRequestImpl(thisKey);
      editorData.setFinanceCubeId(this.mVirementRequestEVO.getFinanceCubeId());
      editorData.setBudgetCycleId(this.mVirementRequestEVO.getBudgetCycleId());
      editorData.setRequestStatus(this.mVirementRequestEVO.getRequestStatus());
      editorData.setUserId(this.mVirementRequestEVO.getUserId());
      editorData.setReason(this.mVirementRequestEVO.getReason());
      editorData.setReference(this.mVirementRequestEVO.getReference());
      editorData.setDateSubmitted(this.mVirementRequestEVO.getDateSubmitted());
      editorData.setBudgetActivityId(this.mVirementRequestEVO.getBudgetActivityId());
      editorData.setVersionNum(this.mVirementRequestEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      UserPK key = null;
      if(this.mVirementRequestEVO.getUserId() != 0) {
         key = new UserPK(this.mVirementRequestEVO.getUserId());
      }

      if(key != null) {
         UserEVO evoUser = this.getUserAccessor().getDetails(key, "");
         editorData.setOwningUserRef(new UserRefImpl(evoUser.getPK(), evoUser.getName()));
      }

      this.completeVirementRequestEditData(editorData);
      return editorData;
   }

   private void completeVirementRequestEditData(VirementRequestImpl editorData) throws Exception {}

   public VirementRequestEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      VirementRequestEditorSessionSSO var4;
      try {
         this.mSSO = new VirementRequestEditorSessionSSO();
         VirementRequestImpl e = new VirementRequestImpl((Object)null);
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

   private void completeGetNewItemData(VirementRequestImpl editorData) throws Exception {}

   public VirementRequestCK insert(VirementRequestEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      VirementRequestImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mVirementRequestEVO = new VirementRequestEVO();
         this.mVirementRequestEVO.setFinanceCubeId(editorData.getFinanceCubeId());
         this.mVirementRequestEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mVirementRequestEVO.setRequestStatus(editorData.getRequestStatus());
         this.mVirementRequestEVO.setUserId(editorData.getUserId());
         this.mVirementRequestEVO.setReason(editorData.getReason());
         this.mVirementRequestEVO.setReference(editorData.getReference());
         this.mVirementRequestEVO.setDateSubmitted(editorData.getDateSubmitted());
         this.mVirementRequestEVO.setBudgetActivityId(editorData.getBudgetActivityId());
         this.updateVirementRequestRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addVirementRequestsItem(this.mVirementRequestEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<29>");
         Iterator e = this.mModelEVO.getVirementRequests().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mVirementRequestEVO = (VirementRequestEVO)e.next();
               if(!this.mVirementRequestEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("VirementRequest", this.mVirementRequestEVO.getPK(), 1);
            VirementRequestCK var5 = new VirementRequestCK(this.mModelEVO.getPK(), this.mVirementRequestEVO.getPK());
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

   private void updateVirementRequestRelationships(VirementRequestImpl editorData) throws ValidationException {
      if(editorData.getOwningUserRef() != null) {
         Object key = editorData.getOwningUserRef().getPrimaryKey();
         if(key instanceof UserPK) {
            this.mVirementRequestEVO.setUserId(((UserPK)key).getUserId());
         } else {
            this.mVirementRequestEVO.setUserId(((UserCK)key).getUserPK().getUserId());
         }

         try {
            this.getUserAccessor().getDetails(key, "");
         } catch (Exception var4) {
            var4.printStackTrace();
            throw new ValidationException(editorData.getOwningUserRef() + " no longer exists");
         }
      } else {
         this.mVirementRequestEVO.setUserId(0);
      }

   }

   private void completeInsertSetup(VirementRequestImpl editorData) throws Exception {
      this.updateSubTables(editorData);
   }

   private void insertIntoAdditionalTables(VirementRequestImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public VirementRequestCK copy(VirementRequestEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      VirementRequestImpl editorData = cso.getEditorData();
      this.mThisTableKey = (VirementRequestCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<30><31><32><33><34><35>");
         VirementRequestEVO e = this.mModelEVO.getVirementRequestsItem(this.mThisTableKey.getVirementRequestPK());
         this.mVirementRequestEVO = e.deepClone();
         this.mVirementRequestEVO.setFinanceCubeId(editorData.getFinanceCubeId());
         this.mVirementRequestEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mVirementRequestEVO.setRequestStatus(editorData.getRequestStatus());
         this.mVirementRequestEVO.setUserId(editorData.getUserId());
         this.mVirementRequestEVO.setReason(editorData.getReason());
         this.mVirementRequestEVO.setReference(editorData.getReference());
         this.mVirementRequestEVO.setDateSubmitted(editorData.getDateSubmitted());
         this.mVirementRequestEVO.setBudgetActivityId(editorData.getBudgetActivityId());
         this.mVirementRequestEVO.setVersionNum(0);
         this.updateVirementRequestRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mVirementRequestEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addVirementRequestsItem(this.mVirementRequestEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<29><30><31><32><33><34><35>");
         Iterator iter = this.mModelEVO.getVirementRequests().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mVirementRequestEVO = (VirementRequestEVO)iter.next();
               if(!this.mVirementRequestEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new VirementRequestCK(this.mModelEVO.getPK(), this.mVirementRequestEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("VirementRequest", this.mVirementRequestEVO.getPK(), 1);
            VirementRequestCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(VirementRequestImpl editorData) throws Exception {}

   public void update(VirementRequestEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      VirementRequestImpl editorData = cso.getEditorData();
      this.mThisTableKey = (VirementRequestCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<30><31><32><33><34><35>");
         this.mVirementRequestEVO = this.mModelEVO.getVirementRequestsItem(this.mThisTableKey.getVirementRequestPK());
         this.preValidateUpdate(editorData);
         this.mVirementRequestEVO.setFinanceCubeId(editorData.getFinanceCubeId());
         this.mVirementRequestEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mVirementRequestEVO.setRequestStatus(editorData.getRequestStatus());
         this.mVirementRequestEVO.setUserId(editorData.getUserId());
         this.mVirementRequestEVO.setReason(editorData.getReason());
         this.mVirementRequestEVO.setReference(editorData.getReference());
         this.mVirementRequestEVO.setDateSubmitted(editorData.getDateSubmitted());
         this.mVirementRequestEVO.setBudgetActivityId(editorData.getBudgetActivityId());
         if(editorData.getVersionNum() != this.mVirementRequestEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mVirementRequestEVO.getVersionNum());
         }

         this.updateVirementRequestRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("VirementRequest", this.mVirementRequestEVO.getPK(), 3);
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

   private void preValidateUpdate(VirementRequestImpl editorData) throws ValidationException {
      if(editorData.getRequestStatus() == 1 && this.mVirementRequestEVO.getRequestStatus() == 0) {
         this.sendRequestSubmittedMessage(editorData);
      }

      if(editorData.getRequestStatus() == 2 && this.mVirementRequestEVO.getRequestStatus() == 1) {
         int taskId = this.issueVirementTask(this.mVirementRequestEVO.getUserId(), editorData);
         this.sendRequestAuthorisedMessage(editorData, taskId);
      }

      if(editorData.getRequestStatus() == 0 && this.mVirementRequestEVO.getRequestStatus() == 1) {
         this.sendRequestRejectedMessage(editorData);
      }

   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(VirementRequestImpl editorData) throws Exception {
      this.updateSubTables(editorData);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (VirementRequestCK)paramKey;

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

   private void updateAdditionalTables(VirementRequestImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (VirementRequestCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<30><31><32><33><34><35>");
         this.mVirementRequestEVO = this.mModelEVO.getVirementRequestsItem(this.mThisTableKey.getVirementRequestPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteVirementRequestsItem(this.mThisTableKey.getVirementRequestPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("VirementRequest", this.mThisTableKey.getVirementRequestPK(), 2);
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

   public void ejbActivate() {
      this.mProfileCache.clear();
   }

   public void ejbPassivate() {}

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private UserAccessor getUserAccessor() throws Exception {
      if(this.mUserAccessor == null) {
         this.mUserAccessor = new UserAccessor(this.getInitialContext());
      }

      return this.mUserAccessor;
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

   public int saveAndSubmit(VirementRequestEditorSessionCSO cso) throws ValidationException {
      int var3;
      try {
         this.setUserId(cso.getUserId());
         Object e = cso.getEditorData().getPrimaryKey();
         if(e == null) {
            e = this.insert(cso);
         } else {
            this.update(cso);
         }

         this.getModelAccessor().flush(((ModelCK)e).getModelPK());
         var3 = this.submitVirementRequest(cso.getUserId(), e);
      } catch (ValidationException var9) {
         this.mSessionContext.setRollbackOnly();
         throw var9;
      } catch (EJBException var10) {
         if(var10.getCause() instanceof ValidationException) {
            throw (ValidationException)var10.getCause();
         }

         throw var10;
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new EJBException(var11.getMessage(), var11);
      } finally {
         this.setUserId(0);
      }

      return var3;
   }

   public int submitVirementRequest(int userId, Object primaryKey) throws ValidationException, EJBException {
      VirementRequestEditorSessionSSO sso = this.getItemData(userId, primaryKey);
      this.setUserId(userId);
      if(sso.getEditorData().getRequestStatus() != 0) {
         throw new ValidationException("The transfer request must be in NOT SUBMITTED state");
      } else {
         sso.getEditorData().validate();
         VirementUpdateEngine engine = new VirementUpdateEngine(this.getUserId(), sso.getEditorData());

         try {
            engine.validateVirement();
         } catch (SQLException var7) {
            var7.printStackTrace();
            throw new CPException("Failed to validate virement request:", var7);
         }

         VirementAuthorisationEngine authEngine = new VirementAuthorisationEngine(sso.getEditorData());
         boolean authorisationRequired = authEngine.selectAuthorisers();
         sso.getEditorData().setDateSubmitted(new Timestamp(System.currentTimeMillis()));
         this.update(new VirementRequestEditorSessionCSO(userId, sso.getEditorData()));
         return !authorisationRequired?this.issueVirementTask(userId, sso.getEditorData()):0;
      }
   }

   public boolean haveVirementsWhichRequireAuthorisation(int userId) throws EJBException {
      VirementRequestDAO dao = new VirementRequestDAO();
      return dao.countVirementsRequringUserAuthorisation(userId) > 0;
   }

   public EntityList queryVirementRequests(int userId, boolean includeInhertited) throws EJBException {
      VirementRequestDAO dao = new VirementRequestDAO();
      return dao.queryVirementRequests(userId, includeInhertited);
   }

   public List<DataTypeRef> queryTransferDataTypes(int userId, int financeCubeId) throws ValidationException, EJBException {
      ArrayList var5;
      try {
         this.setUserId(userId);
         Map dataTypes = this.loadVirementDataTypes(financeCubeId);
         ArrayList listOfDataTypes = new ArrayList();
         listOfDataTypes.addAll(dataTypes.values());
         var5 = listOfDataTypes;
      } finally {
         this.setUserId(-1);
      }

      return var5;
   }

   public VirementQueryParams getQueryParams(int userId, int financeCubeId) throws EJBException {
      VirementQueryParamsImpl var4;
      try {
         this.setUserId(userId);
         VirementRequestDAO dao = new VirementRequestDAO();
         var4 = new VirementQueryParamsImpl(dao.queryVirementOriginators(financeCubeId), dao.queryVirementAuthorisers(financeCubeId));
      } finally {
         this.setUserId(-1);
      }

      return var4;
   }

   public List<String> queryVirementRequests(int userId, int modelId, int numDims, Integer creator, Integer authoriser, Integer virementId, Integer status, List<StructureElementKey> structureElements, Double minimumValue, Double maximumValue, Date fromDate, Date toDate) throws EJBException {
      List var13;
      try {
         this.setUserId(userId);
         var13 = (new VirementRequestDAO()).queryVirementRequests(modelId, numDims, creator, authoriser, virementId, status, structureElements, minimumValue, maximumValue, fromDate, toDate);
      } finally {
         this.setUserId(-1);
      }

      return var13;
   }

   public String queryVirementRequest(int userId, int requesrId) throws EJBException {
      String var3;
      try {
         this.setUserId(userId);
         var3 = (new VirementRequestDAO()).queryVirementRequest(requesrId);
      } finally {
         this.setUserId(-1);
      }

      return var3;
   }

   private int issueVirementTask(int userId, VirementRequestImpl virement) {
      try {
         VirementTaskRequest e = new VirementTaskRequest(virement);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, userId);
      } catch (Exception var4) {
         throw new EJBException(var4);
      }
   }

   private Map<Integer, DataTypeRefImpl> loadVirementDataTypes(int financeCubeId) {
      HashMap dataTypes = new HashMap();
      DataTypeDAO dtDAO = new DataTypeDAO();
      AllDataTypeForFinanceCubeELO elo = dtDAO.getAllDataTypeForFinanceCube(financeCubeId);

      for(int row = 0; row < elo.getNumRows(); ++row) {
         int subType = ((Integer)elo.getValueAt(row, "SubType")).intValue();
         if(subType == 2 || subType == 1) {
            DataTypeRefImpl dtRefImpl = (DataTypeRefImpl)elo.getValueAt(row, "DataType");
            dataTypes.put(new Integer(dtRefImpl.getDataTypePK().getDataTypeId()), dtRefImpl);
         }
      }

      return dataTypes;
   }

   private void loadSubTables(VirementRequestImpl impl) throws ValidationException {
      Collection evos = this.getExistingVirementGroups();
      if(impl.getFinanceCubeId() > 0) {
         FinanceCubeDAO systemPropertyDAO = new FinanceCubeDAO();
         FinanceCubeDetailsELO propStr = systemPropertyDAO.getFinanceCubeDetails(impl.getFinanceCubeId());
         propStr.next();
         impl.setFinanceCubeVisId(propStr.getVisId());
      }

      if(impl.getBudgetCycleId() > 0) {
         BudgetCycleDAO systemPropertyDAO1 = new BudgetCycleDAO();
         BudgetCycleEVO propStr1 = systemPropertyDAO1.getDetails(new BudgetCycleCK(new ModelPK(impl.getModelId()), new BudgetCyclePK(impl.getBudgetCycleId())), "<None>");
         impl.setBudgetCycleVisId(propStr1.getVisId());
      }

      SystemPropertyDAO systemPropertyDAO2 = new SystemPropertyDAO();
      String propStr2 = systemPropertyDAO2.getValue("ALLOC: Threshold");
      double allocationThreshold = 100.0D;

      try {
         allocationThreshold = Double.parseDouble(propStr2);
      } catch (NumberFormatException var14) {
         this.mLog.warn("loadSubTables", "Unable to parse ALLOC: Threshold:" + propStr2);
      }

      VirementRequestDAO virementRequestDAO = new VirementRequestDAO();
      Map seMap = virementRequestDAO.queryStructureElementsForRequest(this.getRequestIdFromKey(impl.getPrimaryKey()));
      Map userMap = virementRequestDAO.queryUsersForRequest(this.getRequestIdFromKey(impl.getPrimaryKey()));
      ArrayList groups = new ArrayList();
      groups.addAll(evos);
      Collections.sort(groups, new VirementRequestEditorSessionSEJB$1(this));
      Iterator i = groups.iterator();

      while(i.hasNext()) {
         VirementRequestGroupEVO groupEVO = (VirementRequestGroupEVO)i.next();
         VirementGroupImpl groupImpl = new VirementGroupImpl();
         groupImpl.setPK(groupEVO.getPK());
         groupImpl.setNotes(groupEVO.getNotes());
         groupImpl.setRows(this.loadLines(impl, groupEVO, seMap, allocationThreshold));
         impl.getVirementGroups().add(groupImpl);
      }

      impl.setAuthorisationPoints(this.loadAuthPoints(impl, seMap, userMap));
   }

   private Map loadAuthPoints(VirementRequestImpl impl, Map seMap, Map userMap) {
      HashMap m = new HashMap();
      Iterator apIter = this.mVirementRequestEVO.getAuthPoints().iterator();

      while(apIter.hasNext()) {
         VirementAuthPointEVO authPointEVO = (VirementAuthPointEVO)apIter.next();
         StructureElementEVO seEVO = (StructureElementEVO)seMap.get(new Integer(authPointEVO.getStructureElementId()));
         if(seEVO == null) {
            throw new IllegalStateException("Unable to locate structure element EVO for " + authPointEVO.getStructureElementId());
         }

         UserRefImpl authUserRef = null;
         if(authPointEVO.getAuthUserId() != null) {
            authUserRef = (UserRefImpl)userMap.get(new UserPK(authPointEVO.getAuthUserId().intValue()));
         }

         VirementAuthPointImpl authPoint = new VirementAuthPointImpl(authPointEVO.getPK(), (StructureElementRefImpl)seEVO.getEntityRef(), authPointEVO.getNotes(), Collections.EMPTY_SET, Collections.EMPTY_SET, authUserRef, authPointEVO.getPointStatus(), false);
         authPoint.setLines(this.loadAuthPointLines(authPointEVO, impl));
         authPoint.setAvailableAuthorisers(this.loadAuthPointAuthorisers(authPointEVO, userMap));
         authPoint.setCanUserAuth(authPointEVO.getPointStatus() == 0 && authPoint.getAvailableAuthorisers().contains(new UserRefImpl(new UserPK(this.getUserId()), (String)null)) || this.hasUserWriteAccess(impl.getModelId(), seEVO));
         m.put(authPoint.getRAElement(), authPoint);
      }

      return m;
   }

   private boolean hasUserWriteAccess(int modelId, StructureElementEVO seEVO) {
      return (new StructureElementDAO()).hasUserAccessToRespArea(this.getUserId(), modelId, seEVO.getStructureElementId(), true);
   }

   private Set loadAuthPointAuthorisers(VirementAuthPointEVO authPointEVO, Map userMap) {
      HashSet s = new HashSet();
      Iterator auIter = authPointEVO.getAuthUsers().iterator();

      while(auIter.hasNext()) {
         VirementAuthorisersEVO authorisersEVO = (VirementAuthorisersEVO)auIter.next();
         s.add(userMap.get(new UserPK(authorisersEVO.getUserId())));
      }

      return s;
   }

   private Set loadAuthPointLines(VirementAuthPointEVO authPointEVO, VirementRequestImpl requestImpl) {
      HashSet s = new HashSet();
      Iterator lIter = authPointEVO.getAuthPointLinks().iterator();

      while(lIter.hasNext()) {
         VirementAuthPointLinkEVO linkEVO = (VirementAuthPointLinkEVO)lIter.next();
         VirementLineImpl line = requestImpl.getLineById(linkEVO.getPK().getVirementLineId());
         if(line == null) {
            throw new IllegalStateException("Failed to locate virement line id:" + linkEVO.getPK().getVirementLineId());
         }

         s.add(line);
      }

      return s;
   }

   private int getRequestIdFromKey(Object key) {
      if(key instanceof VirementRequestCK) {
         return ((VirementRequestCK)key).getVirementRequestPK().getRequestId();
      } else if(key instanceof VirementRequestPK) {
         return ((VirementRequestPK)key).getRequestId();
      } else {
         throw new IllegalArgumentException("Unexpected virement request key object:" + key);
      }
   }

   private List loadLines(VirementRequestImpl impl, VirementRequestGroupEVO groupEVO, Map seMap, double allocationThreshold) throws ValidationException {
      Map dataTypes = this.loadVirementDataTypes(impl.getFinanceCubeId());
      ArrayList lines = new ArrayList();
      if(groupEVO.getLines() != null) {
         ArrayList evoLines = new ArrayList();
         evoLines.addAll(groupEVO.getLines());
         Collections.sort(evoLines, new VirementRequestEditorSessionSEJB$2(this));
         Iterator i = evoLines.iterator();

         while(i.hasNext()) {
            VirementRequestLineEVO lineEVO = (VirementRequestLineEVO)i.next();
            VirementLineImpl line = new VirementLineImpl(lineEVO.getPK(), allocationThreshold);
            line.setTo(lineEVO.getTarget());
            line.setTransferValue(lineEVO.getTransferValue());
            DataTypeRef dataTypeRef = (DataTypeRef)dataTypes.get(Integer.valueOf(lineEVO.getDataTypeId()));
            if(dataTypeRef == null) {
               throw new ValidationException("Data type id " + lineEVO.getDataTypeId() + " not attached to financec cube.");
            }

            line.setDataTypeRef((DataTypeRef)dataTypes.get(Integer.valueOf(lineEVO.getDataTypeId())));
            ArrayList address = new ArrayList();
            this.addElementAddress(lineEVO.getDim0(), address, seMap);
            this.addElementAddress(lineEVO.getDim1(), address, seMap);
            this.addElementAddress(lineEVO.getDim2(), address, seMap);
            this.addElementAddress(lineEVO.getDim3(), address, seMap);
            this.addElementAddress(lineEVO.getDim4(), address, seMap);
            this.addElementAddress(lineEVO.getDim5(), address, seMap);
            this.addElementAddress(lineEVO.getDim6(), address, seMap);
            this.addElementAddress(lineEVO.getDim7(), address, seMap);
            this.addElementAddress(lineEVO.getDim8(), address, seMap);
            this.addElementAddress(lineEVO.getDim9(), address, seMap);
            line.setAddress(address);
            line.setSpreadProfileKey(new WeightingProfilePK(lineEVO.getSpreadProfileId()));
            if(lineEVO.getSpreadProfileId() != 0) {
               line.setSpreadProfileVisId(this.queryProfileVisId(lineEVO.getSpreadProfileId()));
            } else {
               line.setSpreadProfileVisId("Custom");
            }

            this.loadSpreadProfiles(line, lineEVO, seMap);
            StructureElementRefImpl dateRef = (StructureElementRefImpl)line.getCalendarElement();
            StructureElementEVO dateElemEVO = (StructureElementEVO)seMap.get(new Integer(dateRef.getStructureElementPK().getStructureElementId()));
            if(dateElemEVO == null) {
               throw new IllegalStateException("Unbale to locate element with id:" + dateRef.getStructureElementPK().getStructureElementId());
            }

            line.setSummaryLine(!dateElemEVO.getLeaf());
            if(!line.isSummaryLine()) {
               line.setSpreadProfileVisId((String)null);
            }

            line.setRepeatValue(lineEVO.getRepeatValue());
            lines.add(line);
         }
      }

      return lines;
   }

   private void loadSpreadProfiles(VirementLineImpl lineImpl, VirementRequestLineEVO lineEVO, Map seMap) {
      Iterator pIter = lineEVO.getSpreads().iterator();

      while(pIter.hasNext()) {
         VirementLineSpreadEVO spreadEVO = (VirementLineSpreadEVO)pIter.next();
         StructureElementEVO seEVO = (StructureElementEVO)seMap.get(new Integer(spreadEVO.getStructureElementId()));
         if(seEVO == null) {
            throw new IllegalStateException("Unable to locate cal leaf element for id:" + spreadEVO.getStructureElementId());
         }

         VirementLineSpreadImpl spread = new VirementLineSpreadImpl(spreadEVO.getPK(), (StructureElementRefImpl)seEVO.getEntityRef(), spreadEVO.getLineIdx(), spreadEVO.getHeld(), spreadEVO.getWeighting(), 0L);
         lineImpl.getSpreadProfile().add(spread);
      }

      Collections.sort(lineImpl.getSpreadProfile(), new VirementRequestEditorSessionSEJB$3(this, seMap));
      lineImpl.spreadValueViaRatios();
   }

   private String queryProfileVisId(int profileId) {
      if(profileId == 0) {
         return null;
      } else {
         Profile profile = this.getProfile(profileId);
         return profile != null?profile.getRef().getNarrative():null;
      }
   }

   private Profile getProfile(int profileId) {
      if(profileId == 0) {
         return null;
      } else {
    	  Profile profile = (Profile)this.mProfileCache.get(Integer.valueOf(profileId));
         if(profile == null) {
            profile = this.getWeightingProfileDAO().getWeightingProfileDetail(profileId);
            this.mProfileCache.put(Integer.valueOf(profileId), profile);
         }

         return (Profile)profile;
      }
   }

   private void addElementAddress(int seId, List address, Map seMap) {
      if(seId > 0) {
         StructureElementEVO seEVO = (StructureElementEVO)seMap.get(new Integer(seId));
         if(seEVO == null) {
            throw new IllegalStateException("Unable to lookup structure element:" + seId);
         }

         address.add(seEVO.getEntityRef());
      }

   }

   private void updateSubTables(VirementRequestImpl editorData) {
      this.updateVirementGroups(editorData);
      this.updateVirementAuthPoints(editorData);
   }

   private void updateVirementGroups(VirementRequestImpl editorData) {
      Collection evos = this.getExistingVirementGroups();
      ArrayList newGroups = new ArrayList(editorData.getVirementGroups());
      Iterator i = evos.iterator();

      VirementGroupImpl impl;
      while(i.hasNext()) {
         VirementRequestGroupEVO parentId = (VirementRequestGroupEVO)i.next();
         impl = editorData.findGroup(parentId.getPK());
         if(impl != null) {
            parentId.setNotes(impl.getNotes());
            this.updateVirementLines(parentId, impl);
            newGroups.remove(impl);
         } else {
            this.mVirementRequestEVO.deleteGroupsItem(parentId.getPK());
         }
      }

      int parentId1 = this.mVirementRequestEVO.getPK() != null?this.mVirementRequestEVO.getPK().getRequestId():-1;
      i = newGroups.iterator();

      while(i.hasNext()) {
         impl = (VirementGroupImpl)i.next();
         VirementRequestGroupEVO groupEVO = new VirementRequestGroupEVO(impl.getPK() != null?impl.getPK().getRequestGroupId():editorData.getNextNewGroupNo(), parentId1, impl.getNotes(), editorData.getGroupIdx(impl), new ArrayList());
         this.updateVirementLines(groupEVO, impl);
         this.mVirementRequestEVO.addGroupsItem(groupEVO);
      }

   }

   /*      */   private void updateVirementAuthPoints(VirementRequestImpl editorData)
   /*      */   {
   /* 1642 */     Collection existingAuthPoints = getExistingAuthPoints();
   /*      */ 
   /* 1646 */     for (Iterator apIter = existingAuthPoints.iterator(); apIter.hasNext(); )
   /*      */     {
   /* 1648 */       VirementAuthPointEVO authPointEVO = (VirementAuthPointEVO)apIter.next();
   /* 1649 */       StructureElementRefImpl seRef = new StructureElementRefImpl(new StructureElementPK(queryRAStructureId(), authPointEVO.getStructureElementId()), null);
   /*      */ 
   /* 1652 */       VirementAuthPointImpl authPoint = editorData.getAuthPoint(seRef);
   /* 1653 */       if (authPoint != null)
   /*      */       {
   /* 1655 */         updateAuthPoint(authPointEVO, authPoint);
   /* 1656 */         editorData.removeAuthPoint(authPoint);
   /*      */       }
   /*      */       else
   /*      */       {
   /* 1660 */         this.mVirementRequestEVO.deleteAuthPointsItem(authPointEVO.getPK());
   /*      */       }
   /*      */     }
   /*      */ 
   /* 1664 */     int apNo = -1;
   /*      */ 
   /* 1667 */     for (Iterator apIter = editorData.getAuthorisationPoints().values().iterator(); apIter.hasNext(); )
   /*      */     {
   /* 1669 */       VirementAuthPointImpl authPoint = (VirementAuthPointImpl)apIter.next();
   /* 1670 */       apNo--; VirementAuthPointEVO authPointEVO = new VirementAuthPointEVO(apNo, editorData.getRequestId(), authPoint.getStatus(), authPoint.getAuthUser() != null ? new Integer(((UserRefImpl)authPoint.getAuthUser()).getUserPK().getUserId()) : null, authPoint.getNotes(), authPoint.getRAElement().getStructureElementPK().getStructureElementId(), new ArrayList(), new ArrayList());
   /*      */ 
   /* 1679 */       updateAuthPoint(authPointEVO, authPoint);
   /* 1680 */       this.mVirementRequestEVO.addAuthPointsItem(authPointEVO);
   /*      */     }
   /*      */   }

   private void updateAuthPoint(VirementAuthPointEVO authPointEVO, VirementAuthPointImpl authPoint) {
      authPointEVO.setNotes(authPoint.getNotes());
      authPointEVO.setAuthUserId(authPoint.getAuthUser() != null?new Integer(((UserRefImpl)authPoint.getAuthUser()).getUserPK().getUserId()):null);
      authPointEVO.setStructureElementId(authPoint.getRAElement().getStructureElementPK().getStructureElementId());
      authPointEVO.setPointStatus(authPoint.getStatus());
      Iterator lIter = authPointEVO.getAuthUsers().iterator();

      while(lIter.hasNext()) {
         VirementAuthorisersEVO virementLine = (VirementAuthorisersEVO)lIter.next();
         UserRefImpl linkEVO = new UserRefImpl(new UserPK(virementLine.getUserId()), "I\'ve no idea");
         if(authPoint.getAvailableAuthorisers().contains(linkEVO)) {
            authPoint.getAvailableAuthorisers().remove(linkEVO);
         } else {
            authPointEVO.deleteAuthUsersItem(virementLine.getPK());
         }
      }

      lIter = authPoint.getAvailableAuthorisers().iterator();

      while(lIter.hasNext()) {
         UserRefImpl virementLine1 = (UserRefImpl)lIter.next();
         VirementAuthorisersEVO linkEVO1 = new VirementAuthorisersEVO(authPointEVO.getAuthPointId(), virementLine1.getUserPK().getUserId());
         authPointEVO.addAuthUsersItem(linkEVO1);
      }

      lIter = authPointEVO.getAuthPointLinks().iterator();

      while(lIter.hasNext()) {
         VirementAuthPointLinkEVO virementLine2 = (VirementAuthPointLinkEVO)lIter.next();
         VirementLineImpl linkEVO3 = authPoint.getLine(virementLine2.getPK().getVirementLineId());
         if(linkEVO3 == null) {
            authPointEVO.deleteAuthPointLinksItem(virementLine2.getPK());
         } else {
            authPoint.getLines().remove(linkEVO3);
         }
      }

      lIter = authPoint.getLines().iterator();

      while(lIter.hasNext()) {
         VirementLineImpl virementLine3 = (VirementLineImpl)lIter.next();
         VirementAuthPointLinkEVO linkEVO2 = new VirementAuthPointLinkEVO(authPointEVO.getAuthPointId(), virementLine3.getPK().getRequestLineId());
         authPointEVO.addAuthPointLinksItem(linkEVO2);
      }

   }

   private int queryRAStructureId() {
      return this.mModelEVO.getBudgetHierarchyId();
   }

   private Collection getExistingAuthPoints() {
      return (Collection)(this.mVirementRequestEVO.getAuthPoints() != null?this.mVirementRequestEVO.getAuthPoints():Collections.EMPTY_LIST);
   }

   private void updateVirementLines(VirementRequestGroupEVO evo, VirementGroupImpl group) {
      ArrayList newLines = new ArrayList(group.getRows());
      if(evo.getLines() != null) {
         Iterator parentId = evo.getLines().iterator();

         while(parentId.hasNext()) {
            VirementRequestLineEVO lineNo = (VirementRequestLineEVO)parentId.next();
            VirementLineImpl i = group.findLine(lineNo.getPK());
            if(i != null) {
               newLines.remove(i);
               if(lineNo.getSpreadProfileId() != 0) {
                  Profile line = this.getProfile(this.getProfileIdFromKey(i.getSpreadProfileKey()));
                  if(line != null && line.getType() == 3 && i.getTransferValue() != 0.0D && i.getRepeatValue() == null) {
                     i.setRepeatValue(Long.valueOf(i.getTransferValueAsLong()));
                     i.spreadValueViaRatios();
                  }
               }

               List var13 = i.getAddress();
               lineNo.setDim0(this.getElementId(0, var13));
               lineNo.setDim1(this.getElementId(1, var13));
               lineNo.setDim2(this.getElementId(2, var13));
               lineNo.setDim3(this.getElementId(3, var13));
               lineNo.setDim4(this.getElementId(4, var13));
               lineNo.setDim5(this.getElementId(5, var13));
               lineNo.setDim6(this.getElementId(6, var13));
               lineNo.setDim7(this.getElementId(7, var13));
               lineNo.setDim8(this.getElementId(8, var13));
               lineNo.setDim9(this.getElementId(9, var13));
               lineNo.setTransferValue(i.getTransferValueAsLong());
               lineNo.setDataTypeId(((DataTypePK)i.getDataTypeRef().getPrimaryKey()).getDataTypeId());
               lineNo.setTarget(i.isTo());
               lineNo.setSpreadProfileId(this.getProfileIdFromKey(i.getSpreadProfileKey()));
               lineNo.setLineIdx(group.getRows().indexOf(i));
               lineNo.setRepeatValue(i.getRepeatValueAsLong());
               this.updateVirementSpreadProfiles(lineNo, i);
            } else {
               evo.deleteLinesItem(lineNo.getPK());
            }
         }
      }

      int var10 = evo.getPK() != null?evo.getPK().getRequestGroupId():-1;
      int var11 = -1;
      Iterator var12 = newLines.iterator();

      while(var12.hasNext()) {
         VirementLineImpl var14 = (VirementLineImpl)var12.next();
         List address = var14.getAddress();
         VirementRequestLineEVO lineEVO = new VirementRequestLineEVO(var14.getPK() != null?var14.getPK().getRequestLineId():var11--, var10, var14.isTo(), this.getProfileIdFromKey(var14.getSpreadProfileKey()), Math.abs(var11) - 2, var14.getTransferValueAsLong(), ((DataTypePK)var14.getDataTypeRef().getPrimaryKey()).getDataTypeId(), this.getElementId(0, address), this.getElementId(1, address), this.getElementId(2, address), this.getElementId(3, address), this.getElementId(4, address), this.getElementId(5, address), this.getElementId(6, address), this.getElementId(7, address), this.getElementId(8, address), this.getElementId(9, address), var14.getRepeatValueAsLong(), new ArrayList());
         this.updateVirementSpreadProfiles(lineEVO, var14);
         evo.addLinesItem(lineEVO);
      }

   }

   private int getProfileIdFromKey(Object key) {
      if(key instanceof String) {
         if(((String)key).trim().length() == 0) {
            return 0;
         }

         key = WeightingProfilePK.getKeyFromTokens((String)key);
      }

      if(key instanceof WeightingProfilePK) {
         return ((WeightingProfilePK)key).getProfileId();
      } else if(key instanceof WeightingProfileRefImpl) {
         return ((WeightingProfileRefImpl)key).getWeightingProfilePK().getProfileId();
      } else if(key instanceof Integer) {
         return ((Integer)key).intValue();
      } else if(key == null) {
         return 0;
      } else {
         throw new IllegalStateException("Unexpected key object for weighting profile:" + key);
      }
   }

   /*      */   private void updateVirementSpreadProfiles(VirementRequestLineEVO lineEVO, VirementLineImpl line)
   /*      */   {
   /* 1888 */     List newList = new ArrayList(line.getSpreadProfile());
   /*      */     Iterator spIter;
   /* 1892 */     if (lineEVO.getSpreads() != null)
   /*      */     {
   /* 1894 */       for (spIter = lineEVO.getSpreads().iterator(); spIter.hasNext(); )
   /*      */       {
   /* 1896 */         VirementLineSpreadEVO lineSpreadEVO = (VirementLineSpreadEVO)spIter.next();
   /* 1897 */         VirementLineSpreadImpl impl = line.findLineSpread(lineSpreadEVO.getPK());
   /* 1898 */         if (impl != null)
   /*      */         {
   /* 1900 */           lineSpreadEVO.setLineIdx(impl.getIndex());
   /* 1901 */           lineSpreadEVO.setHeld(impl.isHeld());
   /* 1902 */           lineSpreadEVO.setWeighting(impl.getWeighting());
   /* 1903 */           newList.remove(impl);
   /*      */         }
   /*      */         else
   /*      */         {
   /* 1907 */           lineSpreadEVO.setDeletePending();
   /*      */         }
   /*      */       }
   /*      */ 
   /*      */     }
   /*      */ 
   /* 1913 */     int newId = 0;
   /* 1914 */     for (Iterator spIter1 = newList.iterator(); spIter1.hasNext(); )
   /*      */     {
   /* 1916 */       VirementLineSpreadImpl virementLineSpread = (VirementLineSpreadImpl)spIter1.next();
   /* 1917 */       StructureElementPK elementPK = (StructureElementPK)virementLineSpread.getStructureElementRef().getPrimaryKey();
   /* 1918 */       newId--; VirementLineSpreadEVO lineSpreadEVO = new VirementLineSpreadEVO(newId, elementPK.getStructureElementId(), lineEVO.getRequestLineId(), virementLineSpread.getIndex(), virementLineSpread.isHeld(), virementLineSpread.getWeighting());
   /*      */ 
   /* 1922 */       lineEVO.addSpreadsItem(lineSpreadEVO);
   /*      */     }
   /*      */   }

   private int getElementId(int index, List address) {
      if(index >= address.size()) {
         return 0;
      } else {
         Object o = address.get(index);
         if(o instanceof Integer) {
            return ((Integer)o).intValue();
         } else if(o instanceof StructureElementRefImpl) {
            return ((StructureElementRefImpl)o).getStructureElementPK().getStructureElementId();
         } else {
            throw new IllegalStateException("Unexpected address element class:" + o);
         }
      }
   }

   private Collection getExistingVirementGroups() {
      return (Collection)(this.mVirementRequestEVO != null && this.mVirementRequestEVO.getGroups() != null?this.mVirementRequestEVO.getGroups():Collections.EMPTY_LIST);
   }

   private void sendRequestRejectedMessage(VirementRequestImpl request) throws EJBException {
      try {
         HashSet e = new HashSet();
         e.addAll(request.getAllAvailableAuthorisers());
         this.sendMessage(e, MessageFormat.format("Budget transfer request {0} has been rejected.", new Object[]{Integer.toString(request.getRequestId())}), "");
         e.clear();
         e.add(request.getOwningUserRef());
         String cpRootURL = SystemPropertyHelper.queryStringSystemProperty((Connection)null, "WEB: Root URL", (String)null);
         String requestKeyStr = ((CompositeKey)request.getPrimaryKey()).toTokens();
         String requestIdStr = String.valueOf(request.getRequestId());
         String href = MessageFormat.format("<a href=\"{0}/virement.do?requestId={1}\" target=\"virementPage\">Edit/View budget transfer {2}</a>", new Object[]{cpRootURL, requestKeyStr, requestIdStr});
         this.sendMessage(e, MessageFormat.format("Your budget transfer request {0} has been rejected.", new Object[]{requestIdStr}), MessageFormat.format("Use the following link to access the budget transfer page:<br>{0}", new Object[]{href}));
      } catch (Exception var7) {
         throw new EJBException(var7);
      }
   }

   private void sendRequestAuthorisedMessage(VirementRequestImpl request, int taskId) throws EJBException {
      try {
         HashSet e = new HashSet();
         e.add(request.getOwningUserRef());
         this.sendMessage(e, MessageFormat.format("Your budget transfer request {0} has been authorised.", new Object[]{Integer.toString(request.getRequestId())}), MessageFormat.format("Task {0} has been created to apply the transfer updates to the budget model.", new Object[]{Integer.toString(taskId)}));
      } catch (Exception var4) {
         throw new EJBException(var4);
      }
   }

   private void sendRequestSubmittedMessage(VirementRequestImpl request) throws EJBException {
      try {
         HashSet e = new HashSet();
         e.addAll(request.getAllAvailableAuthorisers());
         String cpRootURL = SystemPropertyHelper.queryStringSystemProperty((Connection)null, "WEB: Root URL", (String)null);
         String requestKeyStr = ((CompositeKey)request.getPrimaryKey()).toTokens();
         String requestIdStr = String.valueOf(request.getRequestId());
         String href = MessageFormat.format("<a href=\"{0}/showVirementAuth.do?requestId={1}\" target=\"virementPage\">Review budget transfer {2}</a>", new Object[]{cpRootURL, requestKeyStr, requestIdStr});
         this.sendMessage(e, MessageFormat.format("Budget transfer request {0} requires your authorisation.", new Object[]{requestIdStr}), MessageFormat.format("Use the following link to access the authorisation page:<br>{0}", new Object[]{href}));
      } catch (Exception var7) {
         throw new EJBException(var7);
      }
   }

   private void sendMessage(Collection toUsers, String subject, String content) throws Exception {
      UserMailer mailer = null;

      try {
         mailer = new UserMailer(new VirementRequestEditorSessionSEJB$4(this));
         mailer.sendMessage(toUsers, subject, content);
      } finally {
         if(mailer != null) {
            mailer.closeDown();
         }

      }

   }

   private WeightingProfileDAO getWeightingProfileDAO() {
      if(this.mWeightingProfileDAO == null) {
         this.mWeightingProfileDAO = new WeightingProfileDAO();
      }

      return this.mWeightingProfileDAO;
   }

   // $FF: synthetic method
   static int accessMethod000(VirementRequestEditorSessionSEJB x0) {
      return x0.getUserId();
   }
}
