// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.passwordhistory;

import com.cedar.cp.api.passwordhistory.PasswordHistory;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryImpl;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.impl.passwordhistory.PasswordHistoryEditorSessionImpl;
import java.sql.Timestamp;

public class PasswordHistoryAdapter implements PasswordHistory {

   private PasswordHistoryImpl mEditorData;
   private PasswordHistoryEditorSessionImpl mEditorSessionImpl;


   public PasswordHistoryAdapter(PasswordHistoryEditorSessionImpl e, PasswordHistoryImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected PasswordHistoryEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected PasswordHistoryImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(PasswordHistoryPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getUserId() {
      return this.mEditorData.getUserId();
   }

   public String getPasswordBytes() {
      return this.mEditorData.getPasswordBytes();
   }

   public Timestamp getPasswordDate() {
      return this.mEditorData.getPasswordDate();
   }

   public void setUserId(int p) {
      this.mEditorData.setUserId(p);
   }

   public void setPasswordBytes(String p) {
      this.mEditorData.setPasswordBytes(p);
   }

   public void setPasswordDate(Timestamp p) {
      this.mEditorData.setPasswordDate(p);
   }
}
