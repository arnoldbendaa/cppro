// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.budgetlimit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface BudgetLimitViolation extends Serializable {

   Integer getBudgetLimitId();

   List getStructureElements();

   String getDataType();

   BigDecimal getMinimumValue();

   BigDecimal getMaximumValue();

   BigDecimal getCurrentValue();
}
