// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserPreference;
import com.cedar.cp.api.user.UserRef;
import java.io.Serializable;

public class UserPreferenceImpl implements UserPreference, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mPrefName;
   private int mPrefType;
   private String mPrefValue;
   private String mPrefHelpID;
   private String mPrefStorage;
   private boolean mContentModified;
   private int mVersionNum;
   private UserRef mUserRef;


   public UserPreferenceImpl() {}

   public UserPreferenceImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mPrefName = "";
      this.mPrefType = -1;
      this.mPrefValue = "";
      this.mPrefHelpID = "";
      this.mPrefStorage = "server";
      this.mContentModified = false;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public UserRef getUserRef() {
      return this.mUserRef;
   }

   public void setUserRef(UserRef ref) {
      this.mUserRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public String getPrefName() {
      return this.mPrefName;
   }

   public int getPrefType() {
      return this.mPrefType;
   }

   public String getPrefValue() {
      return this.mPrefValue;
   }

   public String getHelpId() {
      return this.mPrefHelpID;
   }

   public String getPrefStorage() {
      return this.mPrefStorage;
   }

   public boolean getContentModified() {
      return this.mContentModified;
   }

   public void setPrefName(String prefName) {
      this.mPrefName = prefName;
   }

   public void setPrefType(int prefType) {
      this.mPrefType = prefType;
   }

   public void setPrefValue(String prefValue) {
      this.mPrefValue = prefValue;
   }

   public void setHelpId(String prefHelpID) {
      this.mPrefHelpID = prefHelpID;
   }

   public void setPrefStorage(String prefStorage) {
      this.mPrefStorage = prefStorage;
   }

   public void setContentModified(boolean contentModified) {
      this.mContentModified = contentModified;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("mPrimaryKey=");
      if(this.mPrimaryKey != null) {
         sb.append(this.mPrimaryKey.toString() + "\n");
      } else {
         sb.append("null\n");
      }

      sb.append("mPrefName:" + this.mPrefName + "\n");
      sb.append("mPrefType:" + this.mPrefType + "\n");
      sb.append("mPrefValue:" + this.mPrefValue + "\n");
      sb.append("mPrefHelpID:" + this.mPrefHelpID + "\n");
      sb.append("mPrefStorage:" + this.mPrefStorage + "\n");
      sb.append("mContentModified:" + this.mContentModified + "\n");
      sb.append("mVersionNum:" + this.mVersionNum + "\n");
      sb.append("mUserRef:" + this.mUserRef.toString() + "\n");
      return sb.toString();
   }
}
