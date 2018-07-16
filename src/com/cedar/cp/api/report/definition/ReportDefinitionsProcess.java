// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.definition;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionEditorSession;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.util.ValueMapping;

public interface ReportDefinitionsProcess extends BusinessProcess {

   EntityList getAllReportDefinitions();
   
   EntityList getAllReportDefinitionsForLoggedUser();

   EntityList getAllPublicReportByType(int var1);

   EntityList getReportDefinitionForVisId(String var1);

   ReportDefinitionEditorSession getReportDefinitionEditorSession(Object var1) throws ValidationException;

   ReportDefinitionEditorSession getReportDefinitionEditorSession(String var1) throws ValidationException;

   ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails(String var1) throws ValidationException;

   ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails(String var1) throws ValidationException;

   ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails(String var1) throws ValidationException;

   ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails(String var1) throws ValidationException;

   ValueMapping buildReportDefValueMapping() throws ValidationException;

   ValueMapping buildAutoExpendDepthValueMapping() throws ValidationException;
}
