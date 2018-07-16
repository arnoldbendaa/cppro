// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.ExternalEntity;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.api.model.mapping.extsys.FinancePeriod;
import java.util.List;

public interface FinanceCalendarYear extends ExternalEntity {

   int getYear();

   List<FinancePeriod> getFinancePeriods();

   FinanceCompany getFinanceCompany();
}
