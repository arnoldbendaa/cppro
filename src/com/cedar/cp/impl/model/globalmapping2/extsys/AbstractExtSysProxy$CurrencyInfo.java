package com.cedar.cp.impl.model.globalmapping2.extsys;

import com.cedar.cp.impl.model.globalmapping2.extsys.AbstractExtSysProxy;

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
