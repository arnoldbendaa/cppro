package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger;
import java.util.List;

public interface FinanceCompany extends ExternalEntity {

   String getCompanyVisId();

   boolean isDummy();

   List<FinanceLedger> getFinanceLedgers();

   ExternalSystem getExternalSystem();

   String getSuggestedCPModelVisId();

   String getSuggestedCPCalVisId();

   String getSuggestedCPCubeVisId();

   void setSuggestedCPModelVisId(String var1);

   String getAccountTypeText(String var1);

   String getHierarchyTypeText(String var1);
}
