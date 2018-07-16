package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceValueTypeImpl extends ExternalEntityImpl implements FinanceValueType, Serializable {

   private List<FinanceCurrencyGroup> mFinanceCurrencyGroups;
   private List<FinanceCurrency> mFinanceCurrencies;
   private String mBalanceType;


   public FinanceValueTypeImpl(String valueTypeVisId, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(valueTypeVisId), descr));
   }

   public String getValueTypeVisId() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public List<FinanceCurrencyGroup> getFinanceCurrencyGroups() {
      return this.mFinanceCurrencyGroups;
   }

   public void setFinanceCurrencyGroups(List<FinanceCurrencyGroup> financeCurrencyGroups) {
      this.mFinanceCurrencyGroups = financeCurrencyGroups;
   }

   public List<FinanceCurrency> getFinanceCurrencies() {
      return this.mFinanceCurrencies;
   }

   public void setFinanceCurrencies(List<FinanceCurrency> financeCurrencies) {
      this.mFinanceCurrencies = financeCurrencies;
   }

   public String getBalanceType() {
      return this.mBalanceType;
   }

   public void setBalanceType(String balanceType) {
      this.mBalanceType = balanceType;
   }
}
