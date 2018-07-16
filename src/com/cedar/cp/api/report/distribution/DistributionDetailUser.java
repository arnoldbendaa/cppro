// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.distribution;

import java.io.Serializable;

public class DistributionDetailUser implements Serializable {

   private int mDistributionType;
   private int mMessageType;
   private String mId;


   public DistributionDetailUser(int distributionType, int messageType, String id) {
      this.mDistributionType = distributionType;
      this.mMessageType = messageType;
      this.mId = id;
   }

   public int getDistributionType() {
      return this.mDistributionType;
   }

   public void setDistributionType(int distributionType) {
      this.mDistributionType = distributionType;
   }

   public int getMessageType() {
      return this.mMessageType;
   }

   public void setMessageType(int messageType) {
      this.mMessageType = messageType;
   }

   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
   }

   public boolean equals(Object o) {
      if(this == o) {
         return true;
      } else if(o != null && this.getClass() == o.getClass()) {
         DistributionDetailUser that = (DistributionDetailUser)o;
         if(this.mDistributionType != that.mDistributionType) {
            return false;
         } else if(this.mMessageType != that.mMessageType) {
            return false;
         } else {
            if(this.mId != null) {
               if(!this.mId.equals(that.mId)) {
                  return false;
               }
            } else if(that.mId != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.mDistributionType;
      result = 29 * result + this.mMessageType;
      result = 29 * result + (this.mId != null?this.mId.hashCode():0);
      return result;
   }
}
