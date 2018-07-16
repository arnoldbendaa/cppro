// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.act;

import com.cedar.cp.api.base.EntityRef;

public interface BudgetActivityRef extends EntityRef {

   int MANUAL_DATA_ENTRY = 0;
   int MASS_UPDATE = 1;
   int ALLOCATION = 2;
   int VIREMENT = 3;
   int CELL_CALCULATION_REBUILD = 4;
   int STATUS_CHANGE = 5;
   int EXCEL_IMPORT = 6;

}
