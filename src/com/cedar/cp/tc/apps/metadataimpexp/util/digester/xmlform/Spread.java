// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;

public class Spread extends CommonElement {

   private String mPeriod;
   private String mFormula;


   public String getPeriod() {
      return this.mPeriod;
   }

   public void setPeriod(String period) {
      this.mPeriod = period;
   }

   public void setFormula(String formula) {
      this.mFormula = formula;
   }

   public String getFormula() {
      return this.mFormula;
   }
}
