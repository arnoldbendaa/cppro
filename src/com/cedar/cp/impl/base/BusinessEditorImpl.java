// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.impl.base.BusinessSessionImpl;

public abstract class BusinessEditorImpl implements BusinessEditor {

   private BusinessSessionImpl mSession;
   private boolean mDirty;


   public BusinessEditorImpl(BusinessSessionImpl session) {
      this.mSession = session;
   }

   public BusinessSession getBusinessSession() {
      return this.mSession;
   }

   public CPConnection getConnection() {
      try {
         return this.getBusinessSession().getBusinessProcess().getConnection();
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage());
      }
   }

   public void commit() throws ValidationException {
      this.saveModifications();
      this.mDirty = false;
   }

   public abstract void saveModifications() throws ValidationException;

   public boolean isContentModified() {
      return this.mDirty;
   }

   public void setContentModified() {
      this.mDirty = true;
   }

   protected void validateRequiredString(String value, String validationMessage) throws ValidationException {
      if(value == null || value.trim().length() == 0) {
         throw new ValidationException(validationMessage);
      }
   }
}
