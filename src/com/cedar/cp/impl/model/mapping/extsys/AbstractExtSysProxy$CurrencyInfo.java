// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping.extsys;

import com.cedar.cp.impl.model.mapping.extsys.AbstractExtSysProxy;

class AbstractExtSysProxy$CurrencyInfo {

   private String mDescription;
   private boolean mIsNonBase;
   // $FF: synthetic field
   final AbstractExtSysProxy this$0;


   public AbstractExtSysProxy$CurrencyInfo(AbstractExtSysProxy var1, String descr, boolean isNonBase) {
      this.this$0 = var1;
      this.mDescription = descr;
      this.mIsNonBase = isNonBase;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean isNonBase() {
      return this.mIsNonBase;
   }
}
