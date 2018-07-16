// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter.providers;

import java.util.HashMap;
import java.util.Map;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

public class COAConfigurationEntry extends AppConfigurationEntry {

   public COAConfigurationEntry() {
      super("org.jboss.security.ClientLoginModule", LoginModuleControlFlag.REQUIRED, createOptions());
   }

   private static Map<String, String> createOptions() {
      HashMap options = new HashMap();
      options.put("restore-login-identity", "true");
      return options;
   }
}
