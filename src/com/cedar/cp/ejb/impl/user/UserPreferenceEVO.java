// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.user.UserPreferenceRef;
import com.cedar.cp.dto.user.UserPreferenceCK;
import com.cedar.cp.dto.user.UserPreferencePK;
import com.cedar.cp.dto.user.UserPreferenceRefImpl;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class UserPreferenceEVO implements Serializable {

   private transient UserPreferencePK mPK;
   private int mUserPrefId;
   private int mUserId;
   private String mPrefName;
   private String mPrefValue;
   private int mPrefType;
   private String mHelpId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public UserPreferenceEVO() {}

   public UserPreferenceEVO(int newUserPrefId, int newUserId, String newPrefName, String newPrefValue, int newPrefType, String newHelpId, int newVersionNum) {
      this.mUserPrefId = newUserPrefId;
      this.mUserId = newUserId;
      this.mPrefName = newPrefName;
      this.mPrefValue = newPrefValue;
      this.mPrefType = newPrefType;
      this.mHelpId = newHelpId;
      this.mVersionNum = newVersionNum;
   }

   public UserPreferencePK getPK() {
      if(this.mPK == null) {
         this.mPK = new UserPreferencePK(this.mUserPrefId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getUserPrefId() {
      return this.mUserPrefId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getPrefName() {
      return this.mPrefName;
   }

   public String getPrefValue() {
      return this.mPrefValue;
   }

   public int getPrefType() {
      return this.mPrefType;
   }

   public String getHelpId() {
      return this.mHelpId;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setUserPrefId(int newUserPrefId) {
      if(this.mUserPrefId != newUserPrefId) {
         this.mModified = true;
         this.mUserPrefId = newUserPrefId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
      }
   }

   public void setPrefType(int newPrefType) {
      if(this.mPrefType != newPrefType) {
         this.mModified = true;
         this.mPrefType = newPrefType;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setPrefName(String newPrefName) {
      if(this.mPrefName != null && newPrefName == null || this.mPrefName == null && newPrefName != null || this.mPrefName != null && newPrefName != null && !this.mPrefName.equals(newPrefName)) {
         this.mPrefName = newPrefName;
         this.mModified = true;
      }

   }

   public void setPrefValue(String newPrefValue) {
      if(this.mPrefValue != null && newPrefValue == null || this.mPrefValue == null && newPrefValue != null || this.mPrefValue != null && newPrefValue != null && !this.mPrefValue.equals(newPrefValue)) {
         this.mPrefValue = newPrefValue;
         this.mModified = true;
      }

   }

   public void setHelpId(String newHelpId) {
      if(this.mHelpId != null && newHelpId == null || this.mHelpId == null && newHelpId != null || this.mHelpId != null && newHelpId != null && !this.mHelpId.equals(newHelpId)) {
         this.mHelpId = newHelpId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(UserPreferenceEVO newDetails) {
      this.setUserPrefId(newDetails.getUserPrefId());
      this.setUserId(newDetails.getUserId());
      this.setPrefName(newDetails.getPrefName());
      this.setPrefValue(newDetails.getPrefValue());
      this.setPrefType(newDetails.getPrefType());
      this.setHelpId(newDetails.getHelpId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public UserPreferenceEVO deepClone() {
      UserPreferenceEVO cloned = new UserPreferenceEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mUserPrefId = this.mUserPrefId;
      cloned.mUserId = this.mUserId;
      cloned.mPrefType = this.mPrefType;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mPrefName != null) {
         cloned.mPrefName = this.mPrefName;
      }

      if(this.mPrefValue != null) {
         cloned.mPrefValue = this.mPrefValue;
      }

      if(this.mHelpId != null) {
         cloned.mHelpId = this.mHelpId;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(UserEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mUserPrefId > 0) {
         newKey = true;
         if(parent == null) {
            this.setUserPrefId(-this.mUserPrefId);
         } else {
            parent.changeKey(this, -this.mUserPrefId);
         }
      } else if(this.mUserPrefId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mUserPrefId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(UserEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mUserPrefId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public UserPreferenceRef getEntityRef(UserEVO evoUser, String entityText) {
      return new UserPreferenceRefImpl(new UserPreferenceCK(evoUser.getPK(), this.getPK()), entityText);
   }

   public UserPreferenceCK getCK(UserEVO evoUser) {
      return new UserPreferenceCK(evoUser.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("UserPrefId=");
      sb.append(String.valueOf(this.mUserPrefId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("PrefName=");
      sb.append(String.valueOf(this.mPrefName));
      sb.append(' ');
      sb.append("PrefValue=");
      sb.append(String.valueOf(this.mPrefValue));
      sb.append(' ');
      sb.append("PrefType=");
      sb.append(String.valueOf(this.mPrefType));
      sb.append(' ');
      sb.append("HelpId=");
      sb.append(String.valueOf(this.mHelpId));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("UserPreference: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
