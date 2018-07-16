// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.definition;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.api.report.definition.WebReportOption;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionCSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionSSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.util.ValueMapping;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ReportDefinitionEditorSessionLocal extends EJBLocalObject {

   ReportDefinitionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ReportDefinitionEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ReportDefinitionPK insert(ReportDefinitionEditorSessionCSO var1) throws ValidationException, EJBException;

   ReportDefinitionPK copy(ReportDefinitionEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ReportDefinitionEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails() throws ValidationException, EJBException;

   ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails() throws ValidationException, EJBException;

   ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails() throws ValidationException, EJBException;

   ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails() throws ValidationException, EJBException;

   ValueMapping buildReportDefValueMapping() throws EJBException;

   ValueMapping buildAutoExpendDepthValueMapping() throws EJBException;

   int issueReport(int var1, WebReportOption var2) throws EJBException;

   List<Integer> issueWebReport(int var1, WebReportOption var2, int var3) throws EJBException;
}
