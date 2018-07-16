// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.api.dimension.HierarchyElementEditor;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl;
import com.cedar.cp.impl.dimension.HierarchyElementData;
import javax.swing.tree.DefaultTreeModel;

public class HierarchyElementEditorImpl extends SubBusinessEditorImpl implements HierarchyElementEditor {

   private HierarchyEditorImpl mEditor;
   private HierarchyElementImpl mElement;
   private HierarchyElementData mOriginalData;


   public HierarchyElementEditorImpl(BusinessSession session, HierarchyEditorImpl editor, HierarchyElementImpl element) {
      super(session, editor);
      this.mEditor = editor;
      this.mElement = element;
      this.mOriginalData = new HierarchyElementData(element);
   }

   public void setVisId(String visId) throws ValidationException {
	   if(visId != null && visId.trim().length() != 0) {
         if(visId.trim().length() > 62) {
            throw new ValidationException("Identifier must be less than 63 characters long - "+visId);
         } else {
            this.mElement.setVisId(visId);
            this.setContentModified();
            this.updateTheTreeModel();
         }
      } else {
         throw new ValidationException("An identifier must be supplied");
      }
   }

   public void setDescription(String description) throws ValidationException {
      if(description != null && description.trim().length() != 0) {
         if(description.trim().length() > 64) {
            throw new ValidationException("Description must be less than 65 characters.");
         } else {
            this.mElement.setDescription(description);
            this.setContentModified();
            this.updateTheTreeModel();
         }
      } else {
         throw new ValidationException("A description must be supplied");
      }
   }

   public void setCreditDebit(int crdr) {
      if(crdr != 1 && crdr != 2) {
         throw new CPException("Credit Debit value muct be 1 or 2");
      } else {
         this.mElement.setCreditDebit(crdr);
         this.setContentModified();
         this.updateTheTreeModel();
      }
   }

   public void setAugCreditDebit(int crdr) throws ValidationException {
      if(crdr != 1 && crdr != 2 && crdr != 0) {
         throw new CPException("Credit Debit value muct be 1 or 2 or0");
      } else {
         if(this.mElement.isAugmentElement()) {
            this.setCreditDebit(crdr);
         }

         this.mElement.setAugCreditDebit(crdr);
         this.setContentModified();
         this.updateTheTreeModel();
      }
   }

   public HierarchyElement getHierarchyElement() {
      return this.mElement;
   }

   public void commit() throws ValidationException {
      if(this.isContentModified()) {
         HierarchyEditorImpl editor = (HierarchyEditorImpl)this.getOwner();
         editor.updateElement(this.mElement.getPrimaryKey(), this.mElement.getVisId(), this.mElement.getDescription(), this.mElement.getCreditDebit(), this.mElement.getAugCreditDebit());
      }

      super.commit();
   }

   public void saveModifications() {}

   public void rollback() {
      if(this.isContentModified()) {
         this.mOriginalData.updateElement(this.mElement);
         DefaultTreeModel model = (DefaultTreeModel)this.mEditor.getHierarchy().getTreeModel();
         model.nodeChanged(this.mElement);
      }

      super.rollback();
   }

   public void undoModifications() throws CPException {}

   private void updateTheTreeModel() {
      DefaultTreeModel model = (DefaultTreeModel)this.mEditor.getHierarchy().getTreeModel();
      model.nodeChanged(this.mElement);
   }
}
