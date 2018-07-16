// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.api.model.SecurityGroup;
import com.cedar.cp.api.model.SecurityGroupEditor;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.SecurityGroupEditorSessionSSO;
import com.cedar.cp.dto.model.SecurityGroupImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.SecurityGroupAdapter;
import com.cedar.cp.impl.model.SecurityGroupEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class SecurityGroupEditorImpl extends BusinessEditorImpl implements SecurityGroupEditor {

   private List mAvailableUsers;
   private SecurityGroupEditorSessionSSO mServerSessionData;
   private SecurityGroupImpl mEditorData;
   private SecurityGroupAdapter mEditorDataAdapter;


   public SecurityGroupEditorImpl(SecurityGroupEditorSessionImpl session, SecurityGroupEditorSessionSSO serverSessionData, SecurityGroupImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(SecurityGroupEditorSessionSSO serverSessionData, SecurityGroupImpl editorData) {
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
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a SecurityGroup");
      } else if(newVisId == null || newVisId.trim().length() == 0) {
         throw new ValidationException("An identifier must be defined");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a SecurityGroup");
      } else if(newDescription == null || newDescription.trim().length() == 0) {
         throw new ValidationException("A description must be supplied");
      }
   }

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

   public EntityList getOwnershipRefs() {
      return ((SecurityGroupEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public SecurityGroup getSecurityGroup() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new SecurityGroupAdapter((SecurityGroupEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      this.validateVisId(this.mEditorData.getVisId());
      this.validateDescription(this.mEditorData.getDescription());
      this.validateSecurityAccessDefRef(this.mEditorData.getSecurityAccessDef());
   }

   public List getAvailableUsers() {
      if(this.mAvailableUsers == null) {
         Object[] userRefs = this.getConnection().getUsersProcess().getAllUsers().getValues("User");
         this.mAvailableUsers = new ArrayList();

         for(int i = 0; i < userRefs.length; ++i) {
            this.mAvailableUsers.add(userRefs[i]);
         }

         if(this.getSelectedUsers() != null) {
            this.mAvailableUsers.removeAll(this.getSelectedUsers());
         }
      }

      return this.mAvailableUsers;
   }

   public List getSelectedUsers() {
      return this.mEditorData.getUsers();
   }

   public void addUser(EntityRef user) throws ValidationException {
      if(this.mEditorData.getUsers().contains(user)) {
         throw new ValidationException("Duplicate");
      } else {
         this.mEditorData.getUsers().add(user);
         this.getAvailableUsers().remove(user);
         this.setContentModified();
      }
   }

   public void removeUser(EntityRef user) throws ValidationException {
      if(!this.mEditorData.getUsers().contains(user)) {
         throw new ValidationException("Not Found");
      } else {
         this.mEditorData.getUsers().remove(user);
         this.getAvailableUsers().add(user);
         this.setContentModified();
      }
   }

   public EntityList getSecurityAccessDefs() {
      int modelId = ((ModelRefImpl)this.mEditorData.getModelRef()).getModelPK().getModelId();
      return this.getConnection().getSecurityAccessDefsProcess().getAllSecurityAccessDefsForModel(modelId);
   }

   public void setSecurityAccessDef(SecurityAccessDefRef def) throws ValidationException {
      this.mEditorData.setSecurityAccessDef(def);
      this.setContentModified();
   }

   private void validateSecurityAccessDefRef(SecurityAccessDefRef access) throws ValidationException {
      if(access == null) {
         throw new ValidationException("A security access definition must be selected");
      }
   }
}
