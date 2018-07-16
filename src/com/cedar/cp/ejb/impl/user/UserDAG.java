// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.sql.Timestamp;

public class UserDAG extends AbstractDAG {

   private int mUserId;
   private String mName;
   private String mFullName;
   private String mEMailAddress;
   private Timestamp mPasswordDate;
   private boolean mChangePassword;
   private boolean mUserDisabled;
   private boolean mPasswordNeverExpires;
   private int mDataAccessLevel;


   public UserDAG(DAGContext context, UserEVO userEvo) {
      super(context, false);
      this.mUserId = userEvo.getUserId();
      this.mName = userEvo.getName();
      this.mFullName = userEvo.getFullName();
      this.mEMailAddress = userEvo.getEMailAddress();
      this.mPasswordDate = userEvo.getPasswordDate();
      this.mChangePassword = userEvo.getChangePassword();
      this.mUserDisabled = userEvo.getUserDisabled();
      this.mPasswordNeverExpires = userEvo.getPasswordNeverExpires();
      context.getCache().put(userEvo.getPK(), this);
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public void setFullName(String fullName) {
      this.mFullName = fullName;
   }

   public String getEMailAddress() {
      return this.mEMailAddress;
   }

   public void setEMailAddress(String EMailAddress) {
      this.mEMailAddress = EMailAddress;
   }

   public Timestamp getPasswordDate() {
      return this.mPasswordDate;
   }

   public void setPasswordDate(Timestamp passwordDate) {
      this.mPasswordDate = passwordDate;
   }

   public boolean isChangePassword() {
      return this.mChangePassword;
   }

   public void setChangePassword(boolean changePassword) {
      this.mChangePassword = changePassword;
   }

   public boolean isUserDisabled() {
      return this.mUserDisabled;
   }

   public void setUserDisabled(boolean userDisabled) {
      this.mUserDisabled = userDisabled;
   }

   public boolean isPasswordNeverExpires() {
      return this.mPasswordNeverExpires;
   }

   public void setPasswordNeverExpires(boolean passwordNeverExpires) {
      this.mPasswordNeverExpires = passwordNeverExpires;
   }

   public int getDataAccessLevel() {
      return this.mDataAccessLevel;
   }

   public void setDataAccessLevel(int dataAccessLevel) {
      this.mDataAccessLevel = dataAccessLevel;
   }
}
