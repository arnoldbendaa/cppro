// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.recharge;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.recharge.RechargeGroup;
import com.cedar.cp.api.model.recharge.RechargeGroupEditor;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.recharge.RechargeGroupAdapter;
import com.cedar.cp.impl.model.recharge.RechargeGroupEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.List;

public class RechargeGroupEditorImpl extends BusinessEditorImpl implements RechargeGroupEditor {

   private RechargeGroupEditorSessionSSO mServerSessionData;
   private RechargeGroupImpl mEditorData;
   private RechargeGroupAdapter mEditorDataAdapter;


   public RechargeGroupEditorImpl(RechargeGroupEditorSessionImpl session, RechargeGroupEditorSessionSSO serverSessionData, RechargeGroupImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(RechargeGroupEditorSessionSSO serverSessionData, RechargeGroupImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a RechargeGroup");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a RechargeGroup");
      }
   }

   public RechargeGroup getRechargeGroup() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new RechargeGroupAdapter((RechargeGroupEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void addSelectedRecharge(List row) throws ValidationException {
      this.mEditorData.addSelectedRecharge(row);
      this.setContentModified();
   }

   public void removeSelectedRecharge(int row) throws ValidationException {
      this.mEditorData.removeSelectedRecharge(row);
      this.setContentModified();
   }

   public void setModelId(int id) {
      this.mEditorData.setModelId(id);
   }

   public void setModelId(EntityRef ref) {
      ModelPK pk = (ModelPK)ref.getPrimaryKey();
      this.mEditorData.setModelId(pk.getModelId());
   }

   public boolean isSameModel(EntityRef ref) {
      boolean result = false;
      ModelPK pk = (ModelPK)ref.getPrimaryKey();
      if(this.mEditorData.getModelId() == pk.getModelId()) {
         result = true;
      }

      return result;
   }
}
