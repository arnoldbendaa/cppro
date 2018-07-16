// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.FinanceCurrency;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueType;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;

public class FinanceCurrencyImpl extends ExternalEntityImpl implements FinanceCurrency, Serializable {

   private FinanceValueType mFinanceValueType;


   public FinanceCurrencyImpl(String valueTypeVisId, String currencyVisId, String balanceTypeVisId, String descr, FinanceValueType fvt) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(valueTypeVisId, currencyVisId, balanceTypeVisId), descr));
   }

   public String getValueType() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public String getCurrency() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(1);
   }

   public String getBalanceType() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(2);
   }
}
