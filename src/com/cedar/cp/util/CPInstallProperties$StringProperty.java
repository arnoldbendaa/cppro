// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;

class CPInstallProperties$StringProperty extends CPInstallProperties$InstallProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$StringProperty(CPInstallProperties var1, String prompt) {
      super(var1, prompt, new String[0]);
      this.this$0 = var1;
   }

   public CPInstallProperties$StringProperty(CPInstallProperties var1, String prompt, String ... allowedValues) {
      super(var1, prompt, allowedValues);
      this.this$0 = var1;
   }
}
