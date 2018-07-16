// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.dto.report.definition.ReportDefinitionCalculationRunDetailsImpl;
import java.io.Serializable;

public class ReportDefinitionCalculationSummaryRunDetailsImpl extends ReportDefinitionCalculationRunDetailsImpl implements ReportDefinitionCalculationSummaryRunDetails, Serializable {

   public String mColumnSettings;


   public String getColumnSettings() {
      return this.mColumnSettings;
   }

   public void setColumnSettings(String columnSettings) {
      this.mColumnSettings = columnSettings;
   }
}
