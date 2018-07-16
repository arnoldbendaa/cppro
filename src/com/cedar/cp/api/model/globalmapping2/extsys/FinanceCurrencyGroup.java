package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency;
import java.util.List;

public interface FinanceCurrencyGroup {

   EntityRef getEntityRef();

   List<FinanceCurrency> getFinanceCurrencies();

   List<FinanceCurrencyGroup> getFinanceCurrencyGroups();
}
