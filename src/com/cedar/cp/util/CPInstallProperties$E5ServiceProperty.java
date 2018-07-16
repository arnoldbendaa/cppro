// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$OracleServiceProperty;

class CPInstallProperties$E5ServiceProperty extends CPInstallProperties$OracleServiceProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$E5ServiceProperty(CPInstallProperties var1) {
      super(var1);
      this.this$0 = var1;
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      return !this.this$0.getPropertyValue("e5.db.type").equalsIgnoreCase("oracle") || super.checkProperty(newValue);
   }
}
