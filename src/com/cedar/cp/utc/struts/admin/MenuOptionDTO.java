// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin;

import com.cedar.cp.api.base.UserContext;

public class MenuOptionDTO implements Comparable {

   private String mAction;
   private String mTitle;
   private String mDescription;
   private String mSecurityString;
   private Boolean mPreventCache;


   public MenuOptionDTO(String action, String title, String description, String securityString) {
      this(action, title, description, securityString, false);
   }

   public MenuOptionDTO(String action, String title, String description, String securityString, boolean preventCache) {
      this.mAction = action;
      this.mTitle = title;
      this.mDescription = description;
      this.mSecurityString = securityString;
      this.mPreventCache = Boolean.valueOf(preventCache);
   }

   public String getAction() {
      return this.mAction;
   }

   public void setAction(String action) {
      this.mAction = action;
   }

   public String getLabel() {
      return this.mTitle + " - " + this.mDescription;
   }

   public String getSecurityString() {
      return this.mSecurityString;
   }

   public void setSecurityString(String securityString) {
      this.mSecurityString = securityString;
   }

   public String toString() {
      return this.getLabel();
   }

   public int compareTo(Object o) {
      return this.toString().compareTo(o.toString());
   }

   public boolean hasSecurity(UserContext conn) {
      return this.mSecurityString != null && this.mSecurityString.length() != 0?conn.hasSecurity(this.mSecurityString):true;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public void setTitle(String title) {
      this.mTitle = title;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public Boolean getPreventCache() {
      return this.mPreventCache;
   }

   public void setPreventCache(Boolean preventCache) {
      this.mPreventCache = preventCache;
   }
}
