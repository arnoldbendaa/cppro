// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import com.coa.lnf.COALnF;

final class COALnF$1 implements Runnable {

   // $FF: synthetic field
   final String val$colorResource;
   // $FF: synthetic field
   final String val$fontResource;


   COALnF$1(String var1, String var2) {
      this.val$colorResource = var1;
      this.val$fontResource = var2;
   }

   public void run() {
      COALnF.accessMethod000(this.val$colorResource);
      COALnF.initialiseFont(this.val$fontResource);
   }
}
