// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.MappedHierarchy;
import com.cedar.cp.api.model.mapping.MappedHierarchyEditor;
import com.cedar.cp.api.model.mapping.MappedModel;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.MappedHierarchyImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.mapping.MappedDimensionEditorImpl;

public class MappedHierarchyEditorImpl extends SubBusinessEditorImpl implements MappedHierarchyEditor {

   private MappedHierarchyImpl mMappedHierarchy;


   public MappedHierarchyEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, MappedHierarchyImpl mappedHierarchy) {
      super(sess, owner);

      try {
         this.mMappedHierarchy = (MappedHierarchyImpl)mappedHierarchy.clone();
      } catch (CloneNotSupportedException var5) {
         throw new RuntimeException("Failed to clone MappedHierarchyImpl:", var5);
      }
   }

   public MappedHierarchy getMappedHierarchy() {
      return this.mMappedHierarchy;
   }

   public void setFinanceHierarchyKey(MappingKey financeHierarchyKey) throws ValidationException {
      MappingKeyImpl mk = (MappingKeyImpl)financeHierarchyKey;
      if(mk.length() != 2) {
         throw new ValidationException("Expected mapping key to contain [hierarchy-visid][hierarchy type]");
      } else {
         this.setHierarchyVisId1((String)mk.get(0));
         this.setHierarchyVisId2((String)mk.get(1));
      }
   }

   private void setHierarchyVisId1(String visId1) throws ValidationException {
      if(this.fieldHasChanged(visId1, this.mMappedHierarchy.getHierarchyVisId1())) {
         this.mMappedHierarchy.setHierarchyVisId1(visId1);
         this.setContentModified();
      }

   }

   private void setHierarchyVisId2(String visId2) {
      if(this.fieldHasChanged(visId2, this.mMappedHierarchy.getHierarchyVisId2())) {
         this.mMappedHierarchy.setHierarchyVisId2(visId2);
         this.setContentModified();
      }

   }

   public void setNewHierarchyVisId(String newVisId) throws ValidationException {
      if(this.fieldHasChanged(newVisId, this.mMappedHierarchy.getNewHierarchyVisId())) {
         if(newVisId == null || newVisId.trim().length() == 0) {
            throw new ValidationException("A new hierarchy visual id must be supplied.");
         }

         if(this.mMappedHierarchy.getNewHierarchyVisId() == null || !newVisId.equals(this.mMappedHierarchy.getNewHierarchyVisId())) {
            this.mMappedHierarchy.setNewHierarchyVisId(newVisId);
            this.setContentModified();
         }
      }

   }

   public void setNewHierarchyDescription(String description) throws ValidationException {
      if(this.fieldHasChanged(description, this.mMappedHierarchy.getNewHierarchyDescription())) {
         if(description == null || description.trim().length() == 0) {
            throw new ValidationException("A new hierarchy description must be null.");
         }

         if(this.mMappedHierarchy.getNewHierarchyDescription() == null || !description.equals(this.mMappedHierarchy.getNewHierarchyDescription())) {
            this.mMappedHierarchy.setNewHierarchyDescription(description);
            this.setContentModified();
         }
      }

   }

   public void setResponsibilityAreaHierarchy(boolean b) throws ValidationException {
      if(!this.getMappedModel().isNew()) {
         throw new ValidationException("The responsibility area hierarchy may only be defined when inserting a new mapped model");
      } else {
         if(this.fieldHasChanged(Boolean.valueOf(b), Boolean.valueOf(this.mMappedHierarchy.isResponsibilityAreaHierarchy()))) {
            this.mMappedHierarchy.setResponsibilityAreaHierarchy(true);
            this.setContentModified();
         }

      }
   }

   private MappedModel getMappedModel() {
      return this.getMappedDimensionEditor().getMappedModelEditor().getMappedModel();
   }

   private MappedDimensionEditorImpl getMappedDimensionEditor() {
      return (MappedDimensionEditorImpl)this.getOwner();
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      ((MappedDimensionEditorImpl)this.getOwner()).updateMappedHierarchy(this.mMappedHierarchy);
   }
}
