// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.report.user;


public class UserDTO {

   private String mName;
   private String mFullName;
   private String mEmailAddress;
   private String mDisabled;
   private String mRole;


   public UserDTO() {}

   public UserDTO(String name, String fullName, String emailAddress, String role) {
      this.mName = name;
      this.mFullName = fullName;
      this.mEmailAddress = emailAddress;
      this.mRole = role;
   }

   public UserDTO(String name, String fullName, String emailAddress, String disabled, String role) {
      this.mName = name;
      this.mFullName = fullName;
      this.mEmailAddress = emailAddress;
      this.mDisabled = disabled;
      this.mRole = role;
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

   public String getEmailAddress() {
      return this.mEmailAddress;
   }

   public void setEmailAddress(String emailAddress) {
      this.mEmailAddress = emailAddress;
   }

   public String getDisabled() {
      return this.mDisabled;
   }

   public void setDisabled(String disabled) {
      this.mDisabled = disabled;
   }

   public void setDisabled(boolean disabled) {
      if(disabled) {
         this.mDisabled = "Y";
      } else {
         this.mDisabled = "";
      }

   }

   public String getRole() {
      return this.mRole;
   }

   public void setRole(String role) {
      this.mRole = role;
   }
}
