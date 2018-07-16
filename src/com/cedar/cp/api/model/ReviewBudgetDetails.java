// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.ReviewBudgetSelectOption;
import com.cedar.cp.api.model.ReviewBudgetSelection;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.util.flatform.model.Workbook;
import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReviewBudgetDetails extends Serializable {

   int RESTRICTION_DUE_TO_NOT_PREPARING_STATE = 1;
   int RESTRICTION_DUE_TO_NOT_LEAF_NODE = 2;
   int RESTRICTION_DUE_TO_DISABLED_NODE = 3;


   ReviewBudgetSelectOption getBudgetCycle();

   ReviewBudgetSelectOption getModel();

   int getFinanceCubeId();

   List getStructureElements();

   List getColumns();

   List getFormData();

   int getPeriod();

   String getDataType();

   String getOnFormLoadFormula();

   String getDefaultFormatPattern();

   int getAccountDimensionId();

   Map getContextVariables();

   List<RuntimeCubeDeployment> getCubeDeployments();

   ReviewBudgetSelection[] getDimensionSelections();

   int getDimensionCount();

   Set getReadOnlyDataTypes();

   List getBudgetLimitViolations();

   List getBudgetLimitViolationStructureHeadings();

   List getTreeStructureIndexes();

   List getTreeStructureVisIds();

   int getCreditColor();

   int getDebitColor();

   boolean isNegateCreditSign();

   int getRowHeaderColumnCount();

   int getRowHeaderMaxDepth();

   EntityList[] getSelectionLabels();

   int getSheetProtectionLevel();

   int getBudgetCyclePeriodId();

   String getSheetLevelNoteSummary();

   boolean isSheetLevelNoteRead();

   int getFormId();

   int getFormType();

   double getSpreadPrecisionThreshold();

   boolean isPopSpreadDialog();

   boolean isServerSideChartOfAccountValidation();

   boolean isExternalSystemAvailable();

   Map<String, DataType> getDataTypes();

   Map getSecurityAccessDetails();

   Set<String> getFormDataTypes();

   int getGradientDepth();

   Color getGradientColor();

   Color getEditBackground();

   Color getEditForeground();

   Color getModifiedBackground();

   Color getModifiedForeground();

   Workbook getWorkbook();
   
   byte[] getExcelFile();
   
   String getJsonForm();

   int[] getStructureIds();
}
