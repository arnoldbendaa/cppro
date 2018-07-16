// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.definition;

import java.util.List;
import java.util.Map;

public interface ReportDefinitionFormRunDetails {

   List getBudgetLocations();

   String getModelId();

   Map getSelection();

   String getDataType();

   int getXMLFormId();

   int getDepth();

   byte[] getTemplate();
}
