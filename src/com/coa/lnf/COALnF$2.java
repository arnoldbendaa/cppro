// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import com.coa.lnf.COALnF;

final class COALnF$2 implements Runnable {

   // $FF: synthetic field
   final String val$resourceName;


   COALnF$2(String var1) {
      this.val$resourceName = var1;
   }

   public void run() {
      COALnF.accessMethod000(this.val$resourceName);
      COALnF.initialiseFont((String)null);
   }
}
