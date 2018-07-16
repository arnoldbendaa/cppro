// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dataEntry;

import java.io.Serializable;

public interface CellCalculation2AccountMapping extends Serializable {

   int getAccountId();

   String getDataType();

   int getXmlFormId();

   int getCellCalcId();

   String getSummaryId();

   boolean isForSummaryPeriod();
}
