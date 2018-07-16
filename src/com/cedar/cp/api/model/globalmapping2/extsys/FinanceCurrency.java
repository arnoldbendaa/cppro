package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;

public interface FinanceCurrency extends ExternalEntity {

   String getValueType();

   String getCurrency();

   String getBalanceType();
}
