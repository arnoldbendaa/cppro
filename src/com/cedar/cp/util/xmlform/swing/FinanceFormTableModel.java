// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.Body;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.StructureColumnValue;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import com.cedar.cp.util.xmlform.swing.FormulaParseException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class FinanceFormTableModel extends FormTableModel {

   private List mCellNotes = new ArrayList();
   private List mCellCalcs = new ArrayList();


   public List getUserEdits() {
      ArrayList nonProtectedColumns = new ArrayList();

      for(int result = 0; result < this.mAllLeafLevelColumns.size(); ++result) {
         Column r = (Column)this.mAllLeafLevelColumns.get(result);
         if(!r.getIsProtected()) {
            nonProtectedColumns.add(new Integer(result));
         }
      }

      ArrayList var6 = new ArrayList(this.mRows.size());

      for(int var7 = 0; var7 < this.mRows.size(); ++var7) {
         Object[] row = new Object[nonProtectedColumns.size()];

         for(int c = 0; c < row.length; ++c) {
            row[c] = this.getValueAt(var7, ((Integer)nonProtectedColumns.get(c)).intValue());
         }

         var6.add(row);
      }

      return var6;
   }

   protected void processBody() throws FormulaParseException {
      Body body = this.mFormConfig.getBody();
      this.mRowLevels.clear();
      this.recursivelyProcessColumns(body.getColumns(), 0);
      String inputName = body.getInput();
      ArrayList structureColumnValues = new ArrayList();
      boolean hierarchyColumnIndex = false;
      FinanceCubeInput fci = null;
      Iterator iter = this.mFormConfig.getInputs();

      while(iter.hasNext()) {
         Object n = iter.next();
         if(n instanceof FinanceCubeInput) {
            fci = (FinanceCubeInput)n;
            if(fci.getId().equals(inputName)) {
               List colValues = fci.getColumnValues();
               Iterator iter2 = colValues.iterator();

               for(int index = 0; iter2.hasNext(); ++index) {
                  Object v = iter2.next();
                  if(v instanceof StructureColumnValue) {
                     StructureColumnValue scv = (StructureColumnValue)v;
                     structureColumnValues.add(scv);
                  }
               }

               return;
            }
         }
      }

   }

   public BigDecimal getLeafLevelCellNoteAt(int row, int col) {
      Object[] aRow = (Object[])((Object[])this.mCellNotes.get(row));
      return (BigDecimal)aRow[col];
   }

   public BigDecimal getLeafLevelCellCalcShortIdAt(int row, int col) {
      Object[] aRow = (Object[])((Object[])this.mCellCalcs.get(row));
      return (BigDecimal)aRow[col];
   }

   public void setPreviousData(FormDataInputModel prevData) throws FormulaParseException {
      throw new IllegalStateException("setPreviousData not supported for finance form tables");
   }

   public void addNewRow() {
      this.addNewRow(false);
   }

   public void addNewRow(boolean manual) {
      throw new IllegalStateException("addNewRow not implemented");
   }

   public void deleteRow(int rowIndex) {
      throw new IllegalStateException("deleteRow not implemented");
   }

   public Object getLeafLevelValueAt(int rowIndex, int columnIndex) {
      return ((Object[])((Object[])this.mRows.get(rowIndex)))[columnIndex];
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      int leafIndex = this.getLeafLevelColumnIndexFromVisibleColumnIndex(columnIndex);
      return ((Object[])((Object[])this.mRows.get(rowIndex)))[leafIndex];
   }

   public void setValueAt(Object value, int rowIndex, int colIndex) throws FormulaParseException {
      TreeSet totalRelatedColumns = new TreeSet();
      int leafIndex = this.getLeafLevelColumnIndexFromVisibleColumnIndex(colIndex);
      Object curValue = ((Object[])((Object[])this.mRows.get(rowIndex)))[leafIndex];
      if(!curValue.equals(value)) {
         this.internalSetValueAt(totalRelatedColumns, value, rowIndex, leafIndex);
         this.processTotalRelatedColumns(totalRelatedColumns);
         this.fireTableRowsUpdated(rowIndex, rowIndex);
      }
   }
}
