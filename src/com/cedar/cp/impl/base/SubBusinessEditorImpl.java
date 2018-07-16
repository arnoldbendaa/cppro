// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.util.Log;

public abstract class SubBusinessEditorImpl implements SubBusinessEditor {

   private SubBusinessEditorOwner mOwner;
   private BusinessSession mSession;
   private boolean mDirty;
   private transient Log mLog = new Log(this.getClass());


   public SubBusinessEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner) {
      this.mSession = sess;
      this.mOwner = owner;
   }

   public SubBusinessEditorOwner getOwner() {
      return this.mOwner;
   }

   public BusinessSession getBusinessSession() {
      return this.mSession;
   }

   public CPConnection getConnection() {
      return this.mSession.getBusinessProcess().getConnection();
   }

   public void commit() throws ValidationException {
      if(this.isContentModified()) {
         this.saveModifications();
         if(this.mOwner instanceof BusinessEditorImpl) {
            ((BusinessEditorImpl)this.mOwner).setContentModified();
         } else if(this.mOwner instanceof SubBusinessEditorImpl) {
            ((SubBusinessEditorImpl)this.mOwner).setContentModified();
         }
      }

      this.mDirty = false;

      try {
         this.mOwner.removeSubBusinessEditor(this);
      } catch (CPException var2) {
         var2.printStackTrace();
         throw new RuntimeException("unable to commit", var2);
      }
   }

   public void rollback() {
      this.mLog.debug("rollback in subBusinessEditor()");

      try {
         this.undoModifications();
      } catch (Exception var3) {
         throw new RuntimeException("unexpected exception", var3);
      }

      this.mDirty = false;

      try {
         this.mOwner.removeSubBusinessEditor(this);
      } catch (CPException var2) {
         var2.printStackTrace();
         throw new RuntimeException("rollback failed", var2);
      }
   }

   protected abstract void undoModifications() throws CPException;

   protected abstract void saveModifications() throws ValidationException;

   protected void validateFieldPresent(String fieldValue) throws ValidationException {
      if(fieldValue == null || fieldValue.trim().length() == 0) {
         throw new ValidationException("A value must be supplied");
      }
   }

   protected boolean fieldHasChanged(Object f1, Object f2) {
      return f1 != null && f2 == null || f1 == null && f2 != null || f1 != null && f2 != null && !f1.equals(f2);
   }

   protected boolean isFieldBlank(String value) {
      return value == null || value.trim().length() == 0;
   }

   public boolean isContentModified() {
      return this.mDirty;
   }

   protected void setContentModified() {
      this.mDirty = true;
   }
}
