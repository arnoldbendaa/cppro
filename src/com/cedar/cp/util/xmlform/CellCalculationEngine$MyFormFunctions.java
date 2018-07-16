// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.CellCalculationEngine;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnGroup;
import com.cedar.cp.util.xmlform.swing.FormFunctions;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import java.math.BigDecimal;
import java.util.Map;

public class CellCalculationEngine$MyFormFunctions extends FormFunctions {

   // $FF: synthetic field
   final CellCalculationEngine this$0;


   public CellCalculationEngine$MyFormFunctions(CellCalculationEngine var1, Map variables) {
      super(variables);
      this.this$0 = var1;
   }

   public double sum(String colName) {
      double result = 0.0D;
      FormTableModel tableModel = CellCalculationEngine.accessMethod000(this.this$0).getFormTableModel();
      int col = tableModel.getLeafLevelColumnIndexFromId(colName);
      if(col > -1) {
         int rowCount = tableModel.getDataRowCount();

         for(int r = 0; r < rowCount; ++r) {
            result += ((BigDecimal)tableModel.getLeafLevelValueAt(r, col)).doubleValue();
         }
      }

      return result;
   }

   public double level() {
      return -3.0D;
   }

   public String elementVisId(int dimIndex) {
      return "";
   }

   public boolean isReadOnlyNominal() {
      return false;
   }

   public void setColumnHeader(String colName, String colHeaderText) {
      Column c = CellCalculationEngine.accessMethod000(this.this$0).getFormTableModel().getLeafLevelColumnFromId(colName);
      if(c != null) {
         c.setHeading(colHeaderText);
      }

   }

   public void setColumnGroupHeader(String colName, String colHeaderText) {
      ColumnGroup columnGroup = CellCalculationEngine.accessMethod000(this.this$0).getFormTableModel().getColumnGroupById(colName);
      if(columnGroup != null) {
         columnGroup.setHeading(colHeaderText);
      }

   }

   public String getBudgetCycleStateAsString(String costCentreVisId) {
      return "Not implemented in cell calculator forms";
   }

   public String getBudgetCycleStateAsString() {
      return "Not implemented in cell calculator forms";
   }
}
