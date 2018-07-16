// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionDetail;
import com.cedar.cp.api.report.distribution.DistributionDetailUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DistributionDetailImpl implements DistributionDetail, Serializable {

   private int mDistributionType;
   private int mMessageType;
   private List mUsers;


   public int getDistributionType() {
      return this.mDistributionType;
   }

   public int getMessageType() {
      return this.getDistributionType() == 1?1:(this.getDistributionType() == 2?2:this.mMessageType);
   }

   public List getUsers() {
      if(this.mUsers == null) {
         this.mUsers = new ArrayList();
      }

      return this.mUsers;
   }

   public String getListAsString() {
      return this.getListAsString(";");
   }

   public String getListAsString(String delim) {
      StringBuffer sb = new StringBuffer();
      Iterator iter = this.getUsers().iterator();

      while(iter.hasNext()) {
         sb.append(iter.next().toString());
         if(iter.hasNext()) {
            sb.append(delim);
         }
      }

      return sb.toString();
   }

   public void setDistributionType(int distributionType) {
      this.mDistributionType = distributionType;
   }

   public void setMessageType(int messageType) {
      this.mMessageType = messageType;
   }

   public void setUsers(List users) {
      this.mUsers = users;
   }

   public void addUser(Object user) {
      this.getUsers().add(user);
   }

   public DistributionDetailUser getDistributionDetailUser(int id) {
      return new DistributionDetailUser(this.mDistributionType, this.mMessageType, this.mUsers.get(id).toString());
   }
}
