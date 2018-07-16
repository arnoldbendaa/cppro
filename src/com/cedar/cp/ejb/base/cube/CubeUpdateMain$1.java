// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CubeUpdateMain;
import java.io.File;
import java.io.FilenameFilter;

class CubeUpdateMain$1 implements FilenameFilter {

   private boolean tapClosed;
   // $FF: synthetic field
   final CubeUpdateMain this$0;


   CubeUpdateMain$1(CubeUpdateMain var1) {
      this.this$0 = var1;
      this.tapClosed = false;
   }

   public boolean accept(File dir, String name) {
      return !this.tapClosed && name.endsWith("xml");
   }
}
