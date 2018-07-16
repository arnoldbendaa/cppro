// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.MassUpdateTask;
import com.cedar.cp.ejb.base.async.XMLReportUtils;

class MassUpdateTask$ActivityWriter extends XMLReportUtils {

   private StringBuffer mXML;
   // $FF: synthetic field
   final MassUpdateTask this$0;


   MassUpdateTask$ActivityWriter(MassUpdateTask var1) {
      this.this$0 = var1;
      this.mXML = new StringBuffer();
   }

   public void add(String txt) {
      this.mXML.append(txt);
   }

   public String getXMLTxt() {
      return this.mXML.toString();
   }
}
