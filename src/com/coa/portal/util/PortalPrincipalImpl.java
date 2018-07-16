// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.util;

import com.coa.portal.client.Application;
import com.coa.portal.client.PortalPrincipal;

public class PortalPrincipalImpl implements PortalPrincipal {

   private String mPortalUserId;
   private String mApplicationUserId;
   private String mApplicationPassword;
   private Application mApplication;


   public PortalPrincipalImpl(String portalUserId, String applicationUserId, String applicationPassword, Application application) {
      this.mPortalUserId = portalUserId;
      this.mApplicationUserId = applicationUserId;
      this.mApplicationPassword = applicationPassword;
      this.mApplication = application;
   }

   public String getPortalUserId() {
      return this.mPortalUserId;
   }

   public String getApplicationUserId() {
      return this.mApplicationUserId;
   }

   public String getApplicationPassword() {
      return this.mApplicationPassword;
   }

   public Application getApplication() {
      return this.mApplication;
   }

   public String getName() {
      return this.mApplicationUserId != null && this.mApplicationUserId.length() > 0?this.mApplicationUserId:this.mPortalUserId;
   }
}
