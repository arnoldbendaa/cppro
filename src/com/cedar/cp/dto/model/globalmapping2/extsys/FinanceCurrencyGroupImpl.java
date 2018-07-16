package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
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
