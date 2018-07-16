// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.InterpreterException;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.xmlform.AutoPopulate;
import com.cedar.cp.util.xmlform.Body;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnTotal;
import com.cedar.cp.util.xmlform.InputColumnValue;
import com.cedar.cp.util.xmlform.Totals;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import com.cedar.cp.util.xmlform.inputs.LookupTarget;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import com.cedar.cp.util.xmlform.swing.FormulaParseException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class NonTableFormTableModel extends FormTableModel {

   private Totals mTotals;
   private Map<String, ColumnTotal> mColumnTotals;
   private Map<String, Object> mColumnTotalValues;
   private transient Log mLog = new Log(this.getClass());


   public List<Object> getUserEdits() {
      ArrayList nonProtectedColumns = new ArrayList();

      for(int result = 0; result < this.mAllLeafLevelColumns.size(); ++result) {
         Column r = (Column)this.mAllLeafLevelColumns.get(result);
         if(!r.getIsProtected()) {
            nonProtectedColumns.add(Integer.valueOf(result));
         }
      }

      ArrayList var6 = new ArrayList(this.mRows.size());

      for(int var7 = 0; var7 < this.mRows.size(); ++var7) {
         Object[] row = new Object[nonProtectedColumns.size()];

         for(int c = 0; c < row.length; ++c) {
            row[c] = this.getLeafLevelValueAt(var7, ((Integer)nonProtectedColumns.get(c)).intValue());
         }

         var6.add(row);
      }

      return var6;
   }

   protected void processBody() throws FormulaParseException {
      Body body = this.mFormConfig.getBody();
      this.recursivelyProcessColumns(body.getColumns(), 0);
      String inputName = body.getInput();
      if(inputName != null) {
         Object e = this.mVariables.get(inputName);
         if(e == null) {
            throw new IllegalStateException("Cannot find input of name \'" + inputName + "\'");
         }

         FormInputModel iter = (FormInputModel)e;

         for(int total = 0; total < iter.getRowCount(); ++total) {
            Object[] columnId = new Object[iter.getColumnCount()];

            for(int j = 0; j < columnId.length; ++j) {
               Column c = (Column)this.mAllLeafLevelColumns.get(j);
               InputColumnValue value = (InputColumnValue)iter.getValueAt(total, j);
               if(c.isTypeString()) {
                  columnId[j] = value.getValue();
               } else if(c.isTypeDataType()) {
                  columnId[j] = value.getBigDecimal();
               } else if(c.isTypeNumber()) {
                  columnId[j] = value.getBigDecimal();
               } else if(c.isTypeFormula()) {
                  columnId[j] = new BigDecimal(0);
               } else if(c.isTypeStringFormula()) {
                  columnId[j] = "";
               } else if(c.isTypeCellNote()) {
                  columnId[j] = "";
               }
            }

            this.mRows.add(columnId);
         }
      }

      this.mTotals = body.getTotals();
      if(this.mTotals != null) {
         this.mColumnTotals = new HashMap();
         this.mColumnTotalValues = new HashMap();
         List var11 = this.mTotals.getColumnTotals();
         Iterator var12 = var11.iterator();

         while(var12.hasNext()) {
            ColumnTotal var13 = (ColumnTotal)var12.next();
            String var14 = var13.getColumnId();
            this.mColumnTotals.put(var14, var13);
            if(var13.getText() != null) {
               this.mColumnTotalValues.put(var14, var13.getText());
            }
         }

         this.updateAllTotals();
      }

      try {
         this.mEngine.eval(this.mColumnFormulae.toString());
      } catch (Exception var10) {
         var10.printStackTrace();
      }

      this.updateAllFormulae();
   }

   private void updateAllTotals() {
      TreeSet totalRelatedColumns = new TreeSet();

      for(int i = 0; i < this.mAllLeafLevelColumns.size(); ++i) {
         this.updateTotalForColumn(totalRelatedColumns, i);
      }

   }

   public void setPreviousData(FormDataInputModel prevData) throws FormulaParseException {
      this.clearAllValidationMessages();
      if(this.mFormConfig.getType() == 2) {
         this.mRows.clear();
      } else {
         this.resetAllEnterableFields();
      }

      TreeSet totalRelatedColumns = new TreeSet();
      if(prevData != null) {
         this.mDisableEngine = true;
         FormDataInputModel i = prevData;

         for(int e = 0; e < i.getRowCount(); ++e) {
            this.addNewRow();

            for(int j = 0; j < i.getColumnCount(); ++j) {
               String colId = i.getColumnId(j);
               int leafLevelIndex = this.getLeafLevelColumnIndexFromId(colId);
               if(leafLevelIndex > -1) {
                  Object value = i.getValueAt(e, j);
                  if(value != null) {
                     this.setLeafLevelValueAt(totalRelatedColumns, value, e, leafLevelIndex);
                  }
               }
            }
         }

         if(this.mFormConfig.getType() == 2) {
            AutoPopulate var11 = this.mFormConfig.getBody().getAutoPopulate();
            if(var11 != null) {
               this.mLog.debug("Processing auto populate");
               this.processAutoPopulate(var11);
            }
         }

         this.mDisableEngine = false;
      }

      this.resetLastEngineRow();

      for(int var10 = 0; var10 < this.mRows.size(); ++var10) {
         try {
            this.updateEngineForRow(var10);
         } catch (InterpreterException var9) {
            ;
         }

         this.runFormulaeForRow(totalRelatedColumns, var10, -1);
      }

      this.processTotalRelatedColumns(totalRelatedColumns);
   }

   private void processAutoPopulate(AutoPopulate autoPopulate) {
      String columnId = autoPopulate.getColumn();
      int leafColumnIndex = this.getLeafLevelColumnIndexFromId(columnId);
      Date lookupDate = this.getContextColumnDate();
      String lookupName = autoPopulate.getLookup();
      List keys = null;
      LookupTarget lut = (LookupTarget)this.mVariables.get(lookupName);
      if(lut != null) {
         try {
            keys = lut.getKeysInUserSeq(lookupDate);
         } catch (Exception var17) {
            throw new IllegalStateException(var17);
         }
      }

      if(keys == null) {
         throw new IllegalStateException("Cannot find lookup with name \'" + lookupName + "\'");
      } else {
         ArrayList lookupKeysNotOnForm = new ArrayList();
         lookupKeysNotOnForm.addAll(keys);
         TreeSet totalRelatedColumns = new TreeSet();

         int row;
         for(row = 0; row < this.mRows.size(); ++row) {
            Object userSequenced = this.getLeafLevelValueAt(row, leafColumnIndex);
            if(userSequenced instanceof BigDecimal) {
               userSequenced = new Double((new Double(((BigDecimal)userSequenced).doubleValue())).doubleValue());
            }

            if(!keys.contains(userSequenced)) {
               if(!autoPopulate.isAddAllowed()) {
                  this.setLeafLevelValueAt(totalRelatedColumns, new BigDecimal(-1), row, 0);
               }

               if(autoPopulate.isAutoDelete()) {
                  this.deleteRow(row);
                  --row;
               }
            } else {
               if(autoPopulate.isDeleteAddedRowsAllowed()) {
                  this.setLeafLevelValueAt(totalRelatedColumns, new BigDecimal(2), row, 0);
               }

               lookupKeysNotOnForm.remove(userSequenced);
            }
         }

         row = this.mRows.size();

         for(Iterator var20 = lookupKeysNotOnForm.iterator(); var20.hasNext(); ++row) {
            Object i = var20.next();
            this.mDisableEngine = true;
            this.addNewRow();
            this.setLeafLevelValueAt(totalRelatedColumns, new BigDecimal(1), row, 0);
            this.setLeafLevelValueAt(totalRelatedColumns, i, row, leafColumnIndex);
            this.mDisableEngine = false;

            try {
               this.updateEngineForRow(row);
            } catch (InterpreterException var16) {
               ;
            }

            this.runFormulaeForRow(totalRelatedColumns, row, -1);
         }

         this.processTotalRelatedColumns(totalRelatedColumns);
         TreeMap var19 = new TreeMap();

         for(int var18 = 0; var18 < this.mRows.size(); ++var18) {
            Object value = this.getLeafLevelValueAt(var18, leafColumnIndex);
            int seqNum = var18;

            for(int j = 0; j < keys.size(); ++j) {
               if(value.equals(keys.get(j))) {
                  seqNum = this.mRows.size() + j;
               }
            }

            var19.put(Integer.valueOf(seqNum), this.mRows.get(var18));
         }

         this.mRows.clear();
         this.mRows.addAll(var19.values());
      }
   }

   public Date getContextColumnDate() {
       Object d = (Date)this.mVariables.get("columnDate");
       if(d != null) {
          return (Date)d;
       } else {
          String calDimId = null;
          calDimId = WorkbookProperties.DIMENSION_2_VISID.toString();
//          Iterator calinfo = this.mVariables.keySet().iterator();
 //
//          while(calinfo.hasNext()) {
//             Object k = calinfo.next();
//             if(k.toString().startsWith("%dim") && k.toString().substring(5).equals("%") && (calDimId == null || k.toString().compareTo(calDimId) > 0)) {
//                calDimId = k.toString();
//             }
//          }

          CalendarInfo calinfo1 = (CalendarInfo)this.mVariables.get(WorkbookProperties.CALENDAR_INFO.toString()
                  + this.mVariables.get(WorkbookProperties.MODEL_ID.toString()));
          if(calinfo1 != null && calinfo1.getById(this.mVariables.get(calDimId)) != null) {
             d = calinfo1.getById(this.mVariables.get(calDimId)).getActualDate();
          }

          if(d == null) {
             d = (Date)this.mVariables.get("lookupDate");
          }

          return (Date)d;
      }
   }

   public void addNewRow() {
      this.addNewRow(false);
   }

   public void addNewRow(boolean manual) {
      if(this.mFormConfig.getType() == 2) {
         Object[] newRow = new Object[this.getLeafLevelColumnCount()];

         int index;
         for(index = 0; index < newRow.length; ++index) {
            newRow[index] = this.createNewLeafLevelValue(index);
         }

         index = this.mRows.size();
         this.mRows.add(newRow);
         this.adjustValidationMessagesForRowsInserted(index, 1);

         try {
            TreeSet e = new TreeSet();
            int r = this.mRows.size() - 1;
            this.updateEngineForRow(r);
            this.runFormulaeForRow(e, r, -1);
            this.processTotalRelatedColumns(e);
         } catch (Exception var6) {
            this.mLog.debug("Formula error: " + var6.getMessage());
            var6.printStackTrace();
         }

         if(manual) {
            this.getAddedRows().add(Integer.valueOf(index));
         }

         this.fireTableRowsInserted(index, index);
      }

   }

   public void deleteRow(int rowIndex) {
      if(this.mFormConfig.getType() == 2) {
         if(rowIndex < 0) {
            return;
         }

         this.mRows.remove(rowIndex);
         this.adjustValidationMessagesForRowsDelete(rowIndex, 1);
         this.fireTableRowsDeleted(rowIndex, rowIndex);
      }

   }

   public Object getLeafLevelValueAt(int rowIndex, int columnIndex) {
      return rowIndex == this.mRows.size()?null:((Object[])((Object[])this.mRows.get(rowIndex)))[columnIndex];
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      if(rowIndex == this.mRows.size()) {
         Column leafIndex1 = (Column)this.mVisibleColumns.get(columnIndex);
         return this.mColumnTotalValues.get(leafIndex1.getId());
      } else {
         int leafIndex = this.getLeafLevelColumnIndexFromVisibleColumnIndex(columnIndex);
         return ((Object[])((Object[])this.mRows.get(rowIndex)))[leafIndex];
      }
   }

   public void setValueAt(Object value, int rowIndex, int colIndex) {
      int leafIndex = this.getLeafLevelColumnIndexFromVisibleColumnIndex(colIndex);
      TreeSet totalRelatedColumns = new TreeSet();
      this.setLeafLevelValueAt(totalRelatedColumns, value, rowIndex, leafIndex);
      this.processTotalRelatedColumns(totalRelatedColumns);
   }

   protected void setLeafLevelValueAt(Set<Integer> totalRelatedColumnIndexes, Object value, int rowIndex, int leafColIndex) {
      Object curValue = ((Object[])((Object[])this.mRows.get(rowIndex)))[leafColIndex];
      if(value != null && !value.equals(curValue)) {
         this.internalSetValueAt(totalRelatedColumnIndexes, value, rowIndex, leafColIndex);
         this.fireTableRowsUpdated(rowIndex, rowIndex);
      }
   }

   public int getRowCount() {
      return this.mTotals != null?this.mRows.size() + 1:this.mRows.size();
   }

   public ColumnTotal getTotalColumn(int column) {
      String id = this.getColumnId(column);
      return (ColumnTotal)this.mColumnTotals.get(id);
   }

   protected boolean internalSetValueAndParents(Set<Integer> totalRelatedColumnIndexes, Object value, int rowIndex, int leafIndex, boolean updateParent) {
      boolean updated = super.internalSetValueAndParents(totalRelatedColumnIndexes, value, rowIndex, leafIndex, updateParent);
      this.updateTotalForColumn(totalRelatedColumnIndexes, leafIndex);
      return updated;
   }

   private void updateTotalForColumn(Set<Integer> totalRelatedColumnIndexes, int leafIndex) {
      if(this.mColumnTotals != null) {
         Column c = (Column)this.mAllLeafLevelColumns.get(leafIndex);
         ColumnTotal total = (ColumnTotal)this.mColumnTotals.get(c.getId());
         if(total != null) {
            String formula = total.getFormula();
            if(total.getText() == null && formula != null && formula.trim().length() > 0) {
               try {
                  this.mEngine.eval(formula);
                  Object e = this.mEngine.getVariable("result");
                  if(e != null && e instanceof Double) {
                     double rounded = this.mFormFunctions.round(((Double)e).doubleValue(), 4);
                     BigDecimal current = (BigDecimal)this.mColumnTotalValues.get(c.getId());
                     if(current == null || current.doubleValue() != rounded) {
                        BigDecimal e1 = new BigDecimal(String.valueOf(rounded));
                        this.mColumnTotalValues.put(c.getId(), e1);
                        this.mEngine.setVariable(total.getId(), Double.valueOf(rounded));
                        List targets = (List)this.mFormulaDependencies.get(total.getId());
                        if(targets != null) {
                           totalRelatedColumnIndexes.addAll(targets);
                        }

                        this.fireTableRowsUpdated(this.mRows.size(), this.mRows.size());
                     }
                  }
               } catch (InterpreterException var11) {
                  throw new FormulaParseException(var11, c);
               }
            }
         }
      }

   }
}
