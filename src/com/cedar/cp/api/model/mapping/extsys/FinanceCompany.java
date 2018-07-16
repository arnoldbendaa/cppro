// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.ExternalEntity;
import com.cedar.cp.api.model.mapping.extsys.ExternalSystem;
import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
import java.util.List;

public interface FinanceCompany extends ExternalEntity {

   String getCompanyVisId();

   boolean isDummy();

   List<FinanceLedger> getFinanceLedgers();

   List<FinanceCalendarYear> getFinanceCalendarYears();

   ExternalSystem getExternalSystem();

   String getSuggestedCPModelVisId();

   String getSuggestedCPCalVisId();

   String getSuggestedCPCubeVisId(int var1);

   void setSuggestedCPModelVisId(String var1);

   String getAccountTypeText(String var1);

   String getHierarchyTypeText(String var1);
}
