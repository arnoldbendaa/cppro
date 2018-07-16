// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.definition;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionEditor;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.api.report.definition.WebReportOption;
import com.cedar.cp.util.ValueMapping;

public interface ReportDefinitionEditorSession extends BusinessSession {

   ReportDefinitionEditor getReportDefinitionEditor();

   ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails();

   ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails();

   ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails();

   ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails();

   ValueMapping buildReportDefValueMapping();

   ValueMapping buildAutoExpendDepthValueMapping();

   int issueReport(int var1, WebReportOption var2);
}