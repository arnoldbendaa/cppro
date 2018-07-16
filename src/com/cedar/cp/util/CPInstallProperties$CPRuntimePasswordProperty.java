// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$OraclePasswordProperty;
import com.cedar.cp.util.CPInstallProperties$OracleServiceProperty;

class CPInstallProperties$CPRuntimePasswordProperty extends CPInstallProperties$OraclePasswordProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$CPRuntimePasswordProperty(CPInstallProperties var1, String prompt) {
      super(var1, prompt, "cp.db.service", "cp.db.runtime.user", "cp.db.admin.user");
      this.this$0 = var1;
   }

   public boolean checkProperty(String newValue) {
      if(((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty(this.mServiceNameProperty)).getHost() == null) {
         return true;
      } else {
         this.this$0.checkSchema(this, newValue, "MODEL");
         return true;
      }
   }
}
