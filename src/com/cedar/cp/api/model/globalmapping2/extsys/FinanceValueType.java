package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup;
import java.util.List;

public interface FinanceValueType extends ExternalEntity {

   List<FinanceCurrencyGroup> getFinanceCurrencyGroups();

   List<FinanceCurrency> getFinanceCurrencies();

   String getValueTypeVisId();
}
