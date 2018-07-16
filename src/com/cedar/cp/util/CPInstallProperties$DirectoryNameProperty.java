// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import java.io.File;

class CPInstallProperties$DirectoryNameProperty extends CPInstallProperties$InstallProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$DirectoryNameProperty(CPInstallProperties var1, String prompt) {
      super(var1, prompt, new String[0]);
      this.this$0 = var1;
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      File d = new File(newValue);
      if(d.exists() && d.isDirectory()) {
         return true;
      } else {
         this.addFeedback("<" + newValue + "> is not a valid directory");
         return false;
      }
   }
}
