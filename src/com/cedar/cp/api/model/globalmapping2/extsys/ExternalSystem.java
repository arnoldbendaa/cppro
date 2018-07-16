package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import java.util.List;

public interface ExternalSystem extends ExternalEntity {

   int TYPE_E5 = 5;
   int TYPE_E2 = 2;
   int TYPE_EFINANCIALS = 3;
   int TYPE_OPEN_ACCOUNTS = 10;
   int TYPE_GENERIC = 20;


   EntityRef getEntityRef();

   int getSystemType();

   boolean isEnabled();

   String getDescription();

   String getLocation();

   List<FinanceCompany> getCompanies();
}
