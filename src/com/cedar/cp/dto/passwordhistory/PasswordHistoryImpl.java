// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.passwordhistory;

import com.cedar.cp.api.passwordhistory.PasswordHistory;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import java.io.Serializable;
import java.sql.Timestamp;

public class PasswordHistoryImpl implements PasswordHistory, Serializable, Cloneable {

   private Object mPrimaryKey;
   private int mUserId;
   private String mPasswordBytes;
   private Timestamp mPasswordDate;
   private int mVersionNum;


   public PasswordHistoryImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mUserId = 0;
      this.mPasswordBytes = "";
      this.mPasswordDate = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (PasswordHistoryPK)paramKey;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getPasswordBytes() {
      return this.mPasswordBytes;
   }

   public Timestamp getPasswordDate() {
      return this.mPasswordDate;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setUserId(int paramUserId) {
      this.mUserId = paramUserId;
   }

   public void setPasswordBytes(String paramPasswordBytes) {
      this.mPasswordBytes = paramPasswordBytes;
   }

   public void setPasswordDate(Timestamp paramPasswordDate) {
      this.mPasswordDate = paramPasswordDate;
   }
}
