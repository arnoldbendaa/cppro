// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter.providers;

import com.coa.idm.filter.providers.BaseProvider;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

class BaseProvider$1 extends Configuration {

   // $FF: synthetic field
   final AppConfigurationEntry[] val$newCoaAppConfigurationEntries;
   // $FF: synthetic field
   final Configuration val$currentConfiguration;
   // $FF: synthetic field
   final BaseProvider this$0;


   BaseProvider$1(BaseProvider var1, AppConfigurationEntry[] var2, Configuration var3) {
      this.this$0 = var1;
      this.val$newCoaAppConfigurationEntries = var2;
      this.val$currentConfiguration = var3;
   }

   public AppConfigurationEntry[] getAppConfigurationEntry(String entryName) {
      return "AMRealm".equals(entryName)?this.val$newCoaAppConfigurationEntries:this.val$currentConfiguration.getAppConfigurationEntry(entryName);
   }

   public void refresh() {}
}
