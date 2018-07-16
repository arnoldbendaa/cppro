// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.ResponsibilityAreaNodeEditor;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.virement.ResponsibilityAreaNodeImpl;
import com.cedar.cp.impl.model.virement.ResponsibilityAreasEditorImpl;

public class ResponsibilityAreaNodeEditorImpl extends SubBusinessEditorImpl implements ResponsibilityAreaNodeEditor {

   private ResponsibilityAreaNodeImpl mNode;


   public ResponsibilityAreaNodeEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, ResponsibilityAreaNodeImpl node) {
      super(sess, owner);

      try {
         this.mNode = (ResponsibilityAreaNodeImpl)node.clone();
      } catch (CloneNotSupportedException var5) {
         throw new CPException("");
      }
   }

   protected void saveModifications() throws ValidationException {
      if(this.isContentModified()) {
         ((ResponsibilityAreasEditorImpl)this.getOwner()).updateNode(this.mNode);
      }

      this.getOwner().removeSubBusinessEditor(this);
   }

   protected void undoModifications() throws CPException {}

   public void setVirementAuthStatus(int newStatus) throws ValidationException {
      if(newStatus != 0 && newStatus != 2 && newStatus != 1) {
         throw new ValidationException("Invalid status supplied");
      } else {
         if(this.mNode.getAuthStatus() != null) {
            if(newStatus == 0) {
               this.mNode.setAuthStatus((Integer)null);
               this.setContentModified();
            } else if(this.mNode.getAuthStatus().intValue() != newStatus) {
               this.mNode.setAuthStatus(new Integer(newStatus));
               this.setContentModified();
            }
         } else if(newStatus == 2 || newStatus == 1) {
            this.mNode.setAuthStatus(new Integer(newStatus));
            this.setContentModified();
         }

      }
   }
}
