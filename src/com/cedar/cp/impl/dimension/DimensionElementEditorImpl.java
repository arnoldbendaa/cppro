// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionElementEditor;
import com.cedar.cp.dto.base.ListModelImpl;
import com.cedar.cp.dto.dimension.DimensionElementImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.dimension.DimensionEditorImpl;
import com.cedar.cp.impl.dimension.DimensionElementData;

public class DimensionElementEditorImpl extends SubBusinessEditorImpl implements DimensionElementEditor {

   private DimensionEditorImpl mEditor;
   private DimensionElementImpl mElement;
   private DimensionElementData mOriginalData;


   public DimensionElementEditorImpl(BusinessSession session, DimensionEditorImpl editor, DimensionElementImpl element) {
      super(session, editor);
      this.mEditor = editor;
      this.mElement = element;
      this.mOriginalData = new DimensionElementData(element);
   }

   public void setVisId(String visId) throws ValidationException {
      if(this.mEditor.isAugmentMode()) {
         throw new ValidationException("Not allowed to update visual id in augment mode.");
      } else if(visId != null && visId.trim().length() != 0) {
         this.mElement.setVisId(visId);
         this.setContentModified();
         this.fireDataUpdated();
      } else {
         throw new ValidationException("An identifier must be supplied");
      }
   }

   public void setDescription(String description) throws ValidationException {
      if(this.mEditor.isAugmentMode()) {
         throw new ValidationException("Not allowed to update description in augment mode.");
      } else if(description != null && description.trim().length() != 0) {
         this.mElement.setDescription(description);
         this.setContentModified();
         this.fireDataUpdated();
      } else {
         throw new ValidationException("A description must be supplied");
      }
   }

   public void setCreditDebit(int creditDebit) throws ValidationException {
      if(this.mEditor.isAugmentMode()) {
         throw new ValidationException("Not allowed to update description in augment mode.");
      } else {
         this.mElement.setCreditDebit(creditDebit);
         this.setContentModified();
         this.fireDataUpdated();
      }
   }

   public void setDisabled(boolean disabled) throws ValidationException {
      if(this.mEditor.isAugmentMode()) {
         throw new ValidationException("Not allowed to update disabled attribute in augment mode.");
      } else if(!this.mElement.isLeaf()) {
         throw new ValidationException("Only leaf nodes may be disabled");
      } else {
         this.mElement.setDisabled(disabled);
         this.setContentModified();
         this.fireDataUpdated();
      }
   }

   public void setAugCreditDebit(int crdeitDebit) {
      if(!this.mEditor.isAugmentMode()) {
         crdeitDebit = 0;
      }

      this.mElement.setAugCreditDebit(crdeitDebit);
      this.setContentModified();
      this.fireDataUpdated();
   }

   public void setNotPlannable(boolean notPlannable) throws ValidationException {
      if(this.mEditor.isAugmentMode()) {
         throw new ValidationException("Not allowed to update \'not plannable\' attribute in augment mode.");
      } else {
         this.mElement.setNotPlannable(notPlannable);
         this.setContentModified();
         this.fireDataUpdated();
      }
   }

   public void setNullElement(boolean nullElement) throws ValidationException {
      if(this.fieldHasChanged(Boolean.valueOf(nullElement), Boolean.valueOf(this.mElement.isNullElement()))) {
         if(nullElement && this.mEditor.getDimension().getNullElement() != null && !this.mEditor.getDimension().getNullElement().getKey().equals(this.mElement.getKey())) {
            throw new ValidationException("Only a single element can be defined at the null element for a dimension.");
         }

         this.mElement.setNullElement(nullElement);
         this.setContentModified();
         this.fireDataUpdated();
      }

   }

   public DimensionElement getElement() {
      return this.mElement;
   }

   private void fireDataUpdated() {
      ListModelImpl model = (ListModelImpl)this.mEditor.getDimension().getListModel();
      if(model != null) {
         model.fireItemChanged(this.mElement);
      }

   }

   public void commit() throws ValidationException {
      if(this.isContentModified()) {
         DimensionEditorImpl editor = (DimensionEditorImpl)this.getOwner();
         editor.updateElement(this.mElement.getKey(), this.mElement.getVisId(), this.mElement.getDescription(), this.mElement.getCreditDebit(), this.mElement.getAugCreditDebit(), this.mElement.isNotPlannable(), this.mElement.isDisabled(), this.mElement.isNullElement());
         this.fireDataUpdated();
      }

      super.commit();
   }

   public void saveModifications() {}

   public void rollback() {
      if(this.isContentModified()) {
         this.mOriginalData.updateElement(this.mElement);
         this.fireDataUpdated();
      }

      super.rollback();
   }

   public void undoModifications() throws CPException {}
}
