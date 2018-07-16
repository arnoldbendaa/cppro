// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.api.model.virement.VirementGroupEditor;
import com.cedar.cp.api.model.virement.VirementRequest;
import com.cedar.cp.api.model.virement.VirementRequestEditor;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
import com.cedar.cp.dto.model.virement.VirementGroupImpl;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.api.model.virement.VirementRequestEditorSessionServer;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.virement.VirementGroupEditorImpl;
import com.cedar.cp.impl.model.virement.VirementRequestAdapter;
import com.cedar.cp.impl.model.virement.VirementRequestEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;
import java.util.List;

public class VirementRequestEditorImpl extends BusinessEditorImpl implements VirementRequestEditor, SubBusinessEditorOwner {

   private VirementRequestEditorSessionSSO mServerSessionData;
   private VirementRequestImpl mEditorData;
   private VirementRequestAdapter mEditorDataAdapter;


   public VirementRequestEditorImpl(VirementRequestEditorSessionImpl session, VirementRequestEditorSessionSSO serverSessionData, VirementRequestImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(VirementRequestEditorSessionSSO serverSessionData, VirementRequestImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setFinanceCubeId(int newFinanceCubeId) throws ValidationException {
      this.validateFinanceCubeId(newFinanceCubeId);
      if(this.mEditorData.getFinanceCubeId() != newFinanceCubeId) {
         this.setContentModified();
         this.mEditorData.setFinanceCubeId(newFinanceCubeId);
      }
   }

   public void setBudgetCycleId(int newBudgetCycleId) throws ValidationException {
      this.validateBudgetCycleId(newBudgetCycleId);
      if(this.mEditorData.getBudgetCycleId() != newBudgetCycleId) {
         this.setContentModified();
         this.mEditorData.setBudgetCycleId(newBudgetCycleId);
      }
   }

   public void setRequestStatus(int newRequestStatus) throws ValidationException {
      this.validateRequestStatus(newRequestStatus);
      if(this.mEditorData.getRequestStatus() != newRequestStatus) {
         this.setContentModified();
         this.mEditorData.setRequestStatus(newRequestStatus);
      }
   }

   public void setUserId(int newUserId) throws ValidationException {
      this.validateUserId(newUserId);
      if(this.mEditorData.getUserId() != newUserId) {
         this.setContentModified();
         this.mEditorData.setUserId(newUserId);
      }
   }

   public void setBudgetActivityId(int newBudgetActivityId) throws ValidationException {
      this.validateBudgetActivityId(newBudgetActivityId);
      if(this.mEditorData.getBudgetActivityId() != newBudgetActivityId) {
         this.setContentModified();
         this.mEditorData.setBudgetActivityId(newBudgetActivityId);
      }
   }

   public void setReason(String newReason) throws ValidationException {
      if(newReason != null) {
         newReason = StringUtils.rtrim(newReason);
      }

      this.validateReason(newReason);
      if(this.mEditorData.getReason() == null || !this.mEditorData.getReason().equals(newReason)) {
         this.setContentModified();
         this.mEditorData.setReason(newReason);
      }
   }

   public void setReference(String newReference) throws ValidationException {
      if(newReference != null) {
         newReference = StringUtils.rtrim(newReference);
      }

      this.validateReference(newReference);
      if(this.mEditorData.getReference() == null || !this.mEditorData.getReference().equals(newReference)) {
         this.setContentModified();
         this.mEditorData.setReference(newReference);
      }
   }

   public void setDateSubmitted(Timestamp newDateSubmitted) throws ValidationException {
      this.validateDateSubmitted(newDateSubmitted);
      if(this.mEditorData.getDateSubmitted() == null || !this.mEditorData.getDateSubmitted().equals(newDateSubmitted)) {
         this.setContentModified();
         this.mEditorData.setDateSubmitted(newDateSubmitted);
      }
   }

   public void validateFinanceCubeId(int newFinanceCubeId) throws ValidationException {}

   public void validateBudgetCycleId(int newBudgetCycleId) throws ValidationException {}

   public void validateRequestStatus(int newRequestStatus) throws ValidationException {}

   public void validateUserId(int newUserId) throws ValidationException {}

   public void validateReason(String newReason) throws ValidationException {
      if(newReason != null && newReason.length() > 2048) {
         throw new ValidationException("length (" + newReason.length() + ") of Reason must not exceed 2048 on a VirementRequest");
      }
   }

   public void validateReference(String newReference) throws ValidationException {
      if(newReference != null && newReference.length() > 80) {
         throw new ValidationException("length (" + newReference.length() + ") of Reference must not exceed 80 on a VirementRequest");
      }
   }

   public void validateDateSubmitted(Timestamp newDateSubmitted) throws ValidationException {}

   public void validateBudgetActivityId(int newBudgetActivityId) throws ValidationException {}

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setModelRef(actualRef);
      this.setContentModified();
   }

   public void setOwningUserRef(UserRef ref) throws ValidationException {
      UserRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getUserEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getOwningUserRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getOwningUserRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setOwningUserRef(actualRef);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((VirementRequestEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public VirementRequest getVirementRequest() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new VirementRequestAdapter((VirementRequestEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.getVirementRequest().getReason() != null && this.getVirementRequest().getReason().trim().length() != 0) {
         if(this.getVirementRequest().getReference() == null || this.getVirementRequest().getReference().trim().length() == 0) {
            throw new ValidationException("A reference for the transfer must be supplied");
         }
      } else {
         throw new ValidationException("A reason for the transfer must be supplied");
      }
   }

   public void setBudgetCycleRef(BudgetCycleRef budgetCycleRef) throws ValidationException {
      this.setBudgetCycleId(((BudgetCycleRefImpl)budgetCycleRef).getBudgetCyclePK().getBudgetCycleId());
   }

   public void setFinanceCubeRef(FinanceCubeRef financeCubeRef) throws ValidationException {
      this.setFinanceCubeId(((FinanceCubeRefImpl)financeCubeRef).getFinanceCubePK().getFinanceCubeId());
   }

   public VirementGroupEditor getVirementRequestGroupEditor(Object key) throws ValidationException {
      VirementGroupImpl group = null;
      if(key != null && (!(key instanceof Number) || ((Number)key).intValue() >= 0)) {
         group = this.mEditorData.findGroup(key);
      } else {
         group = new VirementGroupImpl();
         group.setPK(new VirementRequestGroupPK(this.mEditorData.getNextNewGroupNo()));
      }

      return new VirementGroupEditorImpl(this.getBusinessSession(), this, group);
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   public void remove(VirementGroup group) throws ValidationException {
      if(!this.mEditorData.getVirementGroups().contains(group)) {
         throw new ValidationException("Unable to remove group not found");
      } else {
         this.mEditorData.getVirementGroups().remove(group);
         this.setContentModified();
      }
   }

   public void setBudgetCycleRef(int budgetCycleId, String budgetCycleVisId) throws ValidationException {
      this.setBudgetCycleRef(new BudgetCycleRefImpl(new BudgetCyclePK(budgetCycleId), budgetCycleVisId));
   }

   public void setFinanceCubeRef(int financeCubeId, String financeCubeVisId) throws ValidationException {
      this.setFinanceCubeRef(new FinanceCubeRefImpl(new FinanceCubePK(financeCubeId), financeCubeVisId));
   }

   public void setModelRef(int modelId, String modelVisId) throws ValidationException {
      this.setModelRef(new ModelRefImpl(new ModelPK(modelId), modelVisId));
   }

   public void setOwningUserRef(int userId, String name) throws ValidationException {
      this.setOwningUserRef(new UserRefImpl(new UserPK(userId), name));
   }

   public Object decodeGroupKey(String keyStr) {
      return keyStr != null && keyStr.trim().length() != 0?VirementRequestGroupPK.getKeyFromTokens(keyStr):null;
   }

   public boolean authorise(Object authPointKey, String notes) throws ValidationException {
      if(authPointKey instanceof String) {
         authPointKey = VirementAuthPointPK.getKeyFromTokens((String)authPointKey);
      }

      if(this.mEditorData.getRequestStatus() == 0) {
         throw new ValidationException("The request has not been submitted for approval.");
      } else if(this.mEditorData.getRequestStatus() == 2) {
         throw new ValidationException("The request has already been authorised.");
      } else {
         VirementAuthPointImpl authPoint = this.mEditorData.findAuthPoint(authPointKey);
         if(!authPoint.isCanUserAuth()) {
            throw new ValidationException("You do not have the required access rights to authorise this line");
         } else if(authPoint == null) {
            throw new ValidationException("Auth point not found in request");
         } else if(authPoint.getStatus() == 1) {
            throw new ValidationException("The authorisation point for " + authPoint.getRAElement() + " has already been authorised.");
         } else if(authPoint.getStatus() == 2) {
            throw new ValidationException("The authorisation point for " + authPoint.getRAElement() + " has already been rejected.");
         } else {
            authPoint.setNotes(notes);
            authPoint.setAuthUser((UserRefImpl)this.getConnection().getUserContext().getUserRef());
            authPoint.setStatus(1);
            boolean allAuthorised = this.mEditorData.allAuthPointAuthorised();
            if(allAuthorised) {
               this.mEditorData.setRequestStatus(2);
            }

            this.setContentModified();
            return allAuthorised;
         }
      }
   }

   public void reject(Object authPointKey, String notes) throws ValidationException {
      if(authPointKey instanceof String) {
         authPointKey = VirementAuthPointPK.getKeyFromTokens((String)authPointKey);
      }

      if(this.mEditorData.getRequestStatus() == 0) {
         throw new ValidationException("The request has not been submitted for approval or has already been rejected");
      } else if(this.mEditorData.getRequestStatus() == 2) {
         throw new ValidationException("The request has already been authorised.");
      } else {
         VirementAuthPointImpl authPoint = this.mEditorData.findAuthPoint(authPointKey);
         if(authPoint == null) {
            throw new ValidationException("Auth point not found in request");
         } else if(!authPoint.isCanUserAuth()) {
            throw new ValidationException("You do not have the required access rights to reject this line");
         } else if(authPoint.getStatus() == 1) {
            throw new ValidationException("The auth authPointKey as already been authorised.");
         } else if(authPoint.getStatus() == 2) {
            throw new ValidationException("The auth authPointKey has already been rejected.");
         } else if(notes != null && notes.trim().length() != 0) {
            authPoint.setNotes(notes);
            authPoint.setAuthUser((UserRefImpl)this.getConnection().getUserContext().getUserRef());
            authPoint.setStatus(2);
            this.mEditorData.setRequestStatus(0);
            this.setContentModified();
         } else {
            throw new ValidationException("Somes notes must be supplied when rejecting a transfer.");
         }
      }
   }

   public List<DataTypeRef> getTransferDataTypes() throws ValidationException {
      VirementRequestEditorSessionServer server = new VirementRequestEditorSessionServer(this.getConnection());
      return server.queryTransferDataTypes(this.mEditorData.getFinanceCubeId());
   }

   public VirementRequest getRequestForDTO() {
      return this.mEditorData;
   }
}
