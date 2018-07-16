// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.ra;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.ra.ResponsibilityArea;
import com.cedar.cp.api.model.ra.ResponsibilityAreaEditor;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionSSO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.ra.ResponsibilityAreaAdapter;
import com.cedar.cp.impl.model.ra.ResponsibilityAreaEditorSessionImpl;

public class ResponsibilityAreaEditorImpl extends BusinessEditorImpl implements ResponsibilityAreaEditor {

   private ResponsibilityAreaEditorSessionSSO mServerSessionData;
   private ResponsibilityAreaImpl mEditorData;
   private ResponsibilityAreaAdapter mEditorDataAdapter;


   public ResponsibilityAreaEditorImpl(ResponsibilityAreaEditorSessionImpl session, ResponsibilityAreaEditorSessionSSO serverSessionData, ResponsibilityAreaImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ResponsibilityAreaEditorSessionSSO serverSessionData, ResponsibilityAreaImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setModelId(int newModelId) throws ValidationException {
      this.validateModelId(newModelId);
      if(this.mEditorData.getModelId() != newModelId) {
         this.setContentModified();
         this.mEditorData.setModelId(newModelId);
      }
   }

   public void setStructureId(int newStructureId) throws ValidationException {
      this.validateStructureId(newStructureId);
      if(this.mEditorData.getStructureId() != newStructureId) {
         this.setContentModified();
         this.mEditorData.setStructureId(newStructureId);
      }
   }

   public void setStructureElementId(int newStructureElementId) throws ValidationException {
      this.validateStructureElementId(newStructureElementId);
      if(this.mEditorData.getStructureElementId() != newStructureElementId) {
         this.setContentModified();
         this.mEditorData.setStructureElementId(newStructureElementId);
      }
   }

   public void setVirementAuthStatus(int newVirementAuthStatus) throws ValidationException {
      this.validateVirementAuthStatus(newVirementAuthStatus);
      if(this.mEditorData.getVirementAuthStatus() != newVirementAuthStatus) {
         this.setContentModified();
         this.mEditorData.setVirementAuthStatus(newVirementAuthStatus);
      }
   }

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateStructureId(int newStructureId) throws ValidationException {}

   public void validateStructureElementId(int newStructureElementId) throws ValidationException {}

   public void validateVirementAuthStatus(int newVirementAuthStatus) throws ValidationException {}

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

   public void setOwningStructureElementRef(StructureElementRef ref) throws ValidationException {
      if(this.mEditorData.getOwningStructureElementRef() == null) {
         if(ref == null) {
            return;
         }
      } else if(ref != null && this.mEditorData.getOwningStructureElementRef().getPrimaryKey().equals(ref.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setOwningStructureElementRef(ref);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((ResponsibilityAreaEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public ResponsibilityArea getResponsibilityArea() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ResponsibilityAreaAdapter((ResponsibilityAreaEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public ResponsibilityAreaImpl getRAImpl() {
      return this.mEditorData;
   }
}
