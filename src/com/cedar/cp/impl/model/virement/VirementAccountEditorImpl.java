// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementAccount;
import com.cedar.cp.api.model.virement.VirementAccountEditor;
import com.cedar.cp.dto.model.virement.VirementAccountImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.model.virement.VirementCategoryEditorImpl;
import javax.swing.tree.TreeModel;

public class VirementAccountEditorImpl extends SubBusinessEditorImpl implements VirementAccountEditor {

   private VirementAccountImpl mOldVirementAccount;
   private VirementAccountImpl mVirementAccount;


   public VirementAccountEditorImpl(BusinessSession sess, VirementCategoryEditorImpl owner, VirementAccountImpl virementAccount) {
      super(sess, owner);
      this.mOldVirementAccount = virementAccount;

      try {
         this.mVirementAccount = (VirementAccountImpl)this.mOldVirementAccount.clone();
      } catch (CloneNotSupportedException var5) {
         throw new CPException("Failed to clone VirementAccountImpl");
      }
   }

   public void setTranLimit(double value) throws ValidationException {
      if(value < 0.0D) {
         throw new ValidationException("Transaction limit must be greate than or equal to zero");
      } else {
         if(value != this.mVirementAccount.getTranLimit()) {
            this.mVirementAccount.setTranLimit(value);
            this.setContentModified();
         }

      }
   }

   public void setTotalLimitIn(double value) throws ValidationException {
      if(value < 0.0D) {
         throw new ValidationException("Total limit in must be greate than or equal to zero");
      } else {
         if(value != this.mVirementAccount.getTotalLimitIn()) {
            this.mVirementAccount.setTotalLimitIn(value);
            this.setContentModified();
         }

      }
   }

   public void setTotalLimitOut(double value) throws ValidationException {
      if(value < 0.0D) {
         throw new ValidationException("Total limit out must be greate than or equal to zero");
      } else {
         if(value != this.mVirementAccount.getTotalLimitOut()) {
            this.mVirementAccount.setTotalLimitOut(value);
            this.setContentModified();
         }

      }
   }

   public void setAllowOutput(boolean b) {
      if(b != this.mVirementAccount.isOutputAllowed()) {
         this.mVirementAccount.setOutputAllowed(b);
         this.setContentModified();
      }

   }

   public void setAllowInput(boolean b) {
      if(b != this.mVirementAccount.isInputAllowed()) {
         this.mVirementAccount.setInputAllowed(b);
         this.setContentModified();
      }

   }

   public VirementAccount getVirementAccount() {
      return this.mVirementAccount;
   }

   public TreeModel getAccountTree() {
      return null;
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      if(this.isContentModified()) {
         this.getVirementCategoryEditor().update(this.mVirementAccount);
      }

   }

   protected VirementCategoryEditorImpl getVirementCategoryEditor() {
      return (VirementCategoryEditorImpl)this.getOwner();
   }
}
