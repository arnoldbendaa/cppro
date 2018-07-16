// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.report.form;


public class FormReportDTO {

   private String mName;
   private String mFullName;
   private String mForm;
   private String mFormDescription;
   private String mProfile;
   private String mProfileDescription;


   public FormReportDTO() {}

   public FormReportDTO(String name, String fullName, String form, String formDescription, String profile, String profileDescription) {
      this.mName = name;
      this.mFullName = fullName;
      this.mForm = form;
      this.mFormDescription = formDescription;
      this.mProfile = profile;
      this.mProfileDescription = profileDescription;
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

   public String getForm() {
      return this.mForm;
   }

   public void setForm(String form) {
      this.mForm = form;
   }

   public String getFormDescription() {
      return this.mFormDescription;
   }

   public void setFormDescription(String formDescription) {
      this.mFormDescription = formDescription;
   }

   public String getProfile() {
      return this.mProfile;
   }

   public void setProfile(String profile) {
      this.mProfile = profile;
   }

   public String getProfileDescription() {
      return this.mProfileDescription;
   }

   public void setProfileDescription(String profileDescription) {
      this.mProfileDescription = profileDescription;
   }
}
