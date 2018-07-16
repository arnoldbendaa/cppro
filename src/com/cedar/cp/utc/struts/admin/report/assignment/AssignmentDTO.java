// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.report.assignment;


public class AssignmentDTO {

   private String mUserId;
   private String mFullName;
   private String mModelId;
   private String mElementId;
   private String mElementDescription;
   private String mRead;


   public AssignmentDTO(String userId, String fullName, String modelId, String elementId, String elementDescription, String read) {
      this.mUserId = userId;
      this.mFullName = fullName;
      this.mModelId = modelId;
      this.mElementId = elementId;
      this.mElementDescription = elementDescription;
      this.mRead = read;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public void setUserId(String userId) {
      this.mUserId = userId;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public void setFullName(String fullName) {
      this.mFullName = fullName;
   }

   public String getModelId() {
      return this.mModelId;
   }

   public void setModelId(String modelId) {
      this.mModelId = modelId;
   }

   public String getElementId() {
      return this.mElementId;
   }

   public void setElementId(String elementId) {
      this.mElementId = elementId;
   }

   public String getElementDescription() {
      return this.mElementDescription;
   }

   public void setElementDescription(String elementDescription) {
      this.mElementDescription = elementDescription;
   }

   public String getRead() {
      return this.mRead;
   }

   public void setRead(String read) {
      this.mRead = read;
   }
}
