// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.InputColumnValue;
import com.cedar.cp.util.xmlform.Row;
import com.cedar.cp.util.xmlform.RowInput;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;

public class RowInputModel implements FormInputModel {

   private RowInput mRowInput;


   public RowInputModel(RowInput rowInput) {
      this.mRowInput = rowInput;
   }

   public int getRowCount() {
      return this.mRowInput.getRows().size();
   }

   public int getColumnCount() {
      return ((Row)this.mRowInput.getRows().get(0)).getInputColumnValues().size();
   }

   public Object getValueAt(int row, int col) {
      Row r = (Row)this.mRowInput.getRows().get(row);
      InputColumnValue c = (InputColumnValue)r.getInputColumnValues().get(col);
      return c;
   }

   public void setValueAt(Object value, int row, int col) {
      Row r = (Row)this.mRowInput.getRows().get(row);
      r.getInputColumnValues().set(col, value);
   }

   public int getSheetProtectionLevel() {
      return 0;
   }
}
