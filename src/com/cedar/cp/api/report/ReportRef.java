// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report;

import com.cedar.cp.api.base.EntityRef;

public interface ReportRef extends EntityRef {

   int COST_ALLOCATION = 1;
   int TOP_DOWN_BUDGETING = 2;
   int VIREMENT_TRANSFER = 3;
   int IMPORT_DIMENSIONS = 4;
   int CHANGE_MANAGEMENT = 5;
   int CUBE_IMPORT = 6;
   int CELL_CALC_REBUILD = 7;
   int TIDY_DB = 8;
   int REPORT_UPDATE = 9;
   int EXTERNAL_SYSTEM_IMPORT = 10;
   int E5_DB2_PUSH_TASK = 11;
   int CUBE_FORMULA_REBUILD = 12;
   int CELL_CALC_IMPORT = 13;
   int DYNAMIC_CELL_CALC_IMPORT = 14;

}
