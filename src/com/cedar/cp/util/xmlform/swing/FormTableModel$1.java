// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnGroup;
import com.cedar.cp.util.xmlform.swing.FormFunctions;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import java.math.BigDecimal;
import java.util.Map;

class FormTableModel$1 extends FormFunctions {

   // $FF: synthetic field
   final FormTableModel this$0;


   FormTableModel$1(FormTableModel var1, Map x0) {
      super(x0);
      this.this$0 = var1;
   }

   public double sum(String colName) {
      double result = 0.0D;
      int leafCol = this.this$0.getLeafLevelColumnIndexFromId(colName);
      if(leafCol > -1) {
         int rowCount = this.this$0.getDataRowCount();

         for(int r = 0; r < rowCount; ++r) {
            result += ((BigDecimal)this.this$0.getLeafLevelValueAt(r, leafCol)).doubleValue();
         }
      }

      return result;
   }

   public double level() {
      return this.this$0.mFormulaProcessingRow < 0?-1.0D:(this.this$0.mFormulaProcessingRow >= this.this$0.mRowLevels.size()?0.0D:((BigDecimal)this.this$0.mRowLevels.get(this.this$0.mFormulaProcessingRow)).doubleValue() + 1.0D);
   }

   public String elementVisId(int dimIndex) {
      return "";
   }

   public boolean isReadOnlyNominal() {
      return false;
   }

   public void setColumnHeader(String colName, String colHeaderText) {
      Column column = this.this$0.getColumnById(colName);
      if(column != null) {
         column.setHeading(colHeaderText);
      }

   }

   public void setColumnGroupHeader(String colName, String colHeaderText) {
      ColumnGroup columnGroup = this.this$0.getColumnGroupById(colName);
      if(columnGroup != null) {
         columnGroup.setHeading(colHeaderText);
      }

   }

   public void registerValidationMessage(Object row, String colName, String validationMessage) {
      this.this$0.registerValidationMessage(row, colName, validationMessage);
   }

   public void clearValidationMessage(Object row, String colName) {
      this.this$0.clearValidationMessage(row, colName);
   }

   public void clearValidationMessage(Object row) {
      this.this$0.clearValidationMessage(row);
   }

   public String getBudgetCycleStateAsString(String cc) {
      return "Not implemented in cell calculators";
   }

   public String getBudgetCycleStateAsString() {
      return "Not implemented in cell calculators";
   }
}
