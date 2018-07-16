// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.task;


public class EventDTO {

   private int mType;
   private int mSubType;
   private String mDescription;
   private String mException;
   private String mClientException;


   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public void setSubType(int subType) {
      this.mSubType = subType;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String getException() {
      return this.mException != null?this.mException.trim():this.mException;
   }

   public void setException(String exception) {
      this.mException = exception;
   }

   public String getClientException() {
      return this.mClientException != null?this.mClientException.trim():this.mClientException;
   }

   public void setClientException(String clientException) {
      this.mClientException = clientException;
   }
}
