// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.mapping.extsys.ExternalEntity;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
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
