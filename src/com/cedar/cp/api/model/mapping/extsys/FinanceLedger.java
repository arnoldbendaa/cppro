// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping.extsys;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.model.mapping.extsys.ExternalEntity;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimension;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueType;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner;
import java.util.List;

public interface FinanceLedger extends ExternalEntity {

   String getLedgerVisId();

   boolean isDummy();

   List<FinanceDimension> getFinanceDimensions();

   FinanceDimension findFinanceDimension(String var1);

   FinanceDimension findFinanceDimension(MappingKey var1);

   FinanceCompany getFinanceCompany();

   List<FinanceDimension> getSelectedDimensions();

   void setSelectedDimensions(List<FinanceDimension> var1);

   void setFirstMappedYear(int var1);

   List<FinanceValueType> getFinanceValueTypes();

   List<FinanceValueTypeOwner> getFinanceValueTypeOwners();
}
