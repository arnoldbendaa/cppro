// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.currency;

import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.currency.CurrencyPK;
import java.io.Serializable;

public class CurrencyRefImpl extends EntityRefImpl implements CurrencyRef, Serializable {

   public CurrencyRefImpl(CurrencyPK key, String narrative) {
      super(key, narrative);
   }

   public CurrencyPK getCurrencyPK() {
      return (CurrencyPK)this.mKey;
   }
}
