// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.internal;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.destination.internal.InternalDestination;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InternalDestinationImpl implements InternalDestination, Serializable, Cloneable {

   private List mUserList;
   EntityList mAvailableUsers;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mMessageType;
   private int mVersionNum;


   public InternalDestinationImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mMessageType = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (InternalDestinationPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getMessageType() {
      return this.mMessageType;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setMessageType(int paramMessageType) {
      this.mMessageType = paramMessageType;
   }

   public void setUserList(List userList) {
      this.mUserList = userList;
   }

   public List getUserList() {
      if(this.mUserList == null) {
         this.setUserList(new ArrayList());
      }

      return this.mUserList;
   }

   public void setAvailableUsers(EntityList availableUsers) {
      this.mAvailableUsers = availableUsers;
   }

   public EntityList getAvailableUsers() {
      return this.mAvailableUsers;
   }
}
