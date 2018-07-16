// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.external;

import com.cedar.cp.api.report.destination.external.ExternalDestination;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExternalDestinationImpl implements ExternalDestination, Serializable, Cloneable {

   private List mUserList;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;


   public ExternalDestinationImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ExternalDestinationPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
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

   public void setUserList(List userList) {
      this.mUserList = userList;
   }

   public List getUserList() {
      if(this.mUserList == null) {
         this.mUserList = new ArrayList();
      }

      return this.mUserList;
   }
}
