// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysCurrencyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysCurrencyCK;
import com.cedar.cp.dto.extsys.ExtSysCurrencyPK;
import java.io.Serializable;

public class ExtSysCurrencyRefImpl extends EntityRefImpl implements ExtSysCurrencyRef, Serializable {

   public ExtSysCurrencyRefImpl(ExtSysCurrencyCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCurrencyRefImpl(ExtSysCurrencyPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCurrencyPK getExtSysCurrencyPK() {
      return this.mKey instanceof ExtSysCurrencyCK?((ExtSysCurrencyCK)this.mKey).getExtSysCurrencyPK():(ExtSysCurrencyPK)this.mKey;
   }
}
