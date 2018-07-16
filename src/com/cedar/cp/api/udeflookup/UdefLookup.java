// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.udeflookup;

import java.sql.Timestamp;
import java.util.List;

public interface UdefLookup {

   int TIME_LVL_NONE = 0;
   int TIME_LVL_YEAR = 1;
   int TIME_LVL_MONTH = 2;
   int TIME_LVL_DAY = 3;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   String getGenTableName();

   boolean isAutoSubmit();

   boolean isScenario();

   int getTimeLvl();

   int getYearStartMonth();

   boolean isTimeRange();

   Timestamp getLastSubmit();

   Timestamp getDataUpdated();

   List getColumnDef();

   List getTableData();
}
