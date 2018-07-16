// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.budgetlimit;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;

public interface BudgetLimit {

   Object getPrimaryKey();

   int getBudgetLocationElementId();

   int getDim0();

   int getDim1();

   int getDim2();

   int getDim3();

   int getDim4();

   int getDim5();

   int getDim6();

   int getDim7();

   int getDim8();

   int getDim9();

   String getDataType();

   Long getMinValue();

   Long getMaxValue();

   FinanceCubeRef getFinanceCubeRef();

   ModelRef getModelRef();

   String getDim0Txt();

   String getDim1Txt();

   String getDim2Txt();

   String getDim3Txt();

   String getDim4Txt();

   String getDim5Txt();

   String getDim6Txt();

   String getDim7Txt();

   String getDim8Txt();

   String getDim9Txt();
}
