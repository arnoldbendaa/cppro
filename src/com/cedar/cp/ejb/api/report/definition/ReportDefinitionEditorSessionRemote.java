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
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ReportDefinitionEditorSessionRemote extends EJBObject {

   ReportDefinitionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ReportDefinitionEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ReportDefinitionPK insert(ReportDefinitionEditorSessionCSO var1) throws ValidationException, RemoteException;

   ReportDefinitionPK copy(ReportDefinitionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ReportDefinitionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails() throws ValidationException, RemoteException;

   ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails() throws ValidationException, RemoteException;

   ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails() throws ValidationException, RemoteException;

   ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails() throws ValidationException, RemoteException;

   ValueMapping buildReportDefValueMapping() throws RemoteException;

   ValueMapping buildAutoExpendDepthValueMapping() throws RemoteException;

   int issueReport(int var1, WebReportOption var2) throws RemoteException;

   List<Integer> issueWebReport(int var1, WebReportOption var2, int var3) throws RemoteException;
}
