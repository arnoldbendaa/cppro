// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.FinanceCurrency;
import com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FinanceCurrencyGroupImpl extends ExternalEntityImpl implements FinanceCurrencyGroup, Serializable {

   private List<FinanceCurrency> mFinanceCurrencies;
   private List<FinanceCurrencyGroup> mFinanceCurrencyGroups;


   public FinanceCurrencyGroupImpl(String visId, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(visId), descr));
   }

   public void setFinanceCurrencies(List<FinanceCurrency> financeCurrencies) {
      this.mFinanceCurrencies = financeCurrencies;
   }

   public List<FinanceCurrency> getFinanceCurrencies() {
      return this.mFinanceCurrencies;
   }

   public List<FinanceCurrencyGroup> getFinanceCurrencyGroups() {
      return this.mFinanceCurrencyGroups;
   }

   public void setFinanceCurrencyGroups(ArrayList<FinanceCurrencyGroup> financeCurrencyGroups) {
      this.mFinanceCurrencyGroups = financeCurrencyGroups;
   }
}
