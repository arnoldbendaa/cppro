// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.awt.TableMap;
import com.cedar.cp.util.awt.TableSorter$1;
import com.cedar.cp.util.awt.TableSorter$2;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class TableSorter extends TableMap {

   private KeyAdapter mKeySearchListener = new TableSorter$1(this);
   private int[] mIndexes;
   private int mSortingColumn = -1;
   private boolean mAscending = true;
   private MouseAdapter mListMouseListener;
   private long mLastKeyPressTime = 0L;
   private String mSearchString;
   private int mSearchStartRow = -1;
   private JTable mTableView = null;
   private Map mSortedColumnMap = new HashMap();
   private transient Log mLog = new Log(this.getClass());


   public TableSorter() {
      this.mIndexes = new int[0];
   }

   public TableSorter(TableModel model) {
      this.setModel(model);
   }

   public void setModel(TableModel model) {
      super.setModel(model);
      this.reallocateIndexes();
   }

   public int compareRowsByColumn(int row1, int row2, int column) {
      this.mModel.getColumnClass(column);
      TableModel data = this.mModel;
      Object o1 = data.getValueAt(row1, column);
      Object o2 = data.getValueAt(row2, column);
      if(o1 == null && o2 == null) {
         return 0;
      } else if(o1 == null) {
         return -1;
      } else if(o2 == null) {
         return 1;
      } else if(o1 instanceof Number) {
         Number v13 = (Number)data.getValueAt(row1, column);
         double s13 = v13.doubleValue();
         Number s23 = (Number)data.getValueAt(row2, column);
         double result2 = s23.doubleValue();
         return s13 < result2?-1:(s13 > result2?1:0);
      } else if(o1 instanceof Date) {
         Date v14 = (Date)data.getValueAt(row1, column);
         long s12 = v14.getTime();
         Date s22 = (Date)data.getValueAt(row2, column);
         long result1 = s22.getTime();
         return s12 < result1?-1:(s12 > result1?1:0);
      } else {
         String s1;
         if(o1 instanceof String) {
            String v11 = (String)data.getValueAt(row1, column);
            s1 = (String)data.getValueAt(row2, column);
            int v22 = v11.compareToIgnoreCase(s1);
            return v22 < 0?-1:(v22 > 0?1:0);
         } else if(o1 instanceof Boolean) {
            Boolean v12 = (Boolean)data.getValueAt(row1, column);
            boolean s11 = v12.booleanValue();
            Boolean v21 = (Boolean)data.getValueAt(row2, column);
            boolean s21 = v21.booleanValue();
            return s11 == s21?0:(s11?1:-1);
         } else {
            Object v1 = data.getValueAt(row1, column);
            s1 = v1.toString();
            Object v2 = data.getValueAt(row2, column);
            String s2 = v2.toString();
            int result = s1.compareToIgnoreCase(s2);
            return result < 0?-1:(result > 0?1:0);
         }
      }
   }

   public int compare(int row1, int row2) {
      if(this.mSortingColumn != -1) {
         int result = this.compareRowsByColumn(row1, row2, this.mSortingColumn);
         if(result != 0) {
            return this.mAscending?result:-result;
         }
      }

      return 0;
   }

   public void reallocateIndexes() {
      if(this.mModel != null) {
         int rowCount = this.mModel.getRowCount();
         this.mIndexes = new int[rowCount];

         for(int row = 0; row < rowCount; this.mIndexes[row] = row++) {
            ;
         }
      }

   }

   public void tableChanged(TableModelEvent e) {
      if(e.getType() == 1 || e.getType() == -1) {
         this.reallocateIndexes();
         this.sort(this);
      }

      TableModelEvent tme = new TableModelEvent(this, this.getViewRow(e.getFirstRow()), this.getViewRow(e.getFirstRow()) + (e.getLastRow() - e.getFirstRow()), e.getColumn(), e.getType());
      this.fireTableChanged(tme);
   }

   private String foo(int x) {
      switch(x) {
      case -1:
         return "ALL_COLUMNS|DELETE|HEADRE_ROW";
      case 0:
         return "UPDATE";
      case 1:
         return "INSERT";
      default:
         return "UNKNOWN:" + x;
      }
   }

   public void checkModel() {
      if(this.mModel != null && this.mIndexes.length != this.mModel.getRowCount()) {
         this.mLog.debug("mIndexes.length() = " + this.mIndexes.length + " mModel.getRowCount() = " + this.mModel.getRowCount());
         this.mLog.error("Sorter not informed of a change in model.", "");
         this.reallocateIndexes();
      }

   }

   public void sort(Object sender) {
      this.checkModel();
      this.shuttlesort((int[])((int[])this.mIndexes.clone()), this.mIndexes, 0, this.mIndexes.length);
   }

   public void shuttlesort(int[] from, int[] to, int low, int high) {
      if(high - low >= 2) {
         int middle = (low + high) / 2;
         this.shuttlesort(to, from, low, middle);
         this.shuttlesort(to, from, middle, high);
         int p = low;
         int q = middle;
         int i;
         if(high - low >= 4 && this.compare(from[middle - 1], from[middle]) <= 0) {
            for(i = low; i < high; ++i) {
               to[i] = from[i];
            }

         } else {
            for(i = low; i < high; ++i) {
               if(q < high && (p >= middle || this.compare(from[p], from[q]) > 0)) {
                  to[i] = from[q++];
               } else {
                  to[i] = from[p++];
               }
            }

         }
      }
   }

   public Object getValueAt(int aRow, int aColumn) {
      this.checkModel();
      return this.mModel.getValueAt(this.mIndexes[aRow], aColumn);
   }

   public void setValueAt(Object aValue, int aRow, int aColumn) {
      this.checkModel();
      this.mModel.setValueAt(aValue, this.mIndexes[aRow], aColumn);
   }

   public int getOriginalRow(int aRow) {
      this.checkModel();
      return this.mIndexes[aRow];
   }

   public int getViewRow(int modelRow) {
      for(int row = 0; row < this.mIndexes.length; ++row) {
         if(this.mIndexes[row] == modelRow) {
            return row;
         }
      }

      return -1;
   }

   public void sortByColumn(int column) {
      this.sortByColumn(column, true);
   }

   public void sortByColumn(int column, boolean ascending) {
      if(this.mIndexes.length > 0) {
         this.mAscending = ascending;
         this.mSortingColumn = column;
         this.sort(this);
         super.tableChanged(new TableModelEvent(this));
      }

   }

   public void addMouseListenerToHeaderInTable(JTable table, boolean addKeyToTable) {
      this.mTableView = table;
      this.mTableView.setColumnSelectionAllowed(false);
      JTableHeader th = this.mTableView.getTableHeader();
      this.mListMouseListener = new TableSorter$2(this);
      th.addMouseListener(this.mListMouseListener);
      if(addKeyToTable) {
         this.mTableView.addKeyListener(this.mKeySearchListener);
      } else {
         th.addKeyListener(this.mKeySearchListener);
      }

   }

   public void removeMouseListenerFromTable(JTable table) {
      JTableHeader th = table.getTableHeader();
      th.removeMouseListener(this.mListMouseListener);
      this.mTableView.removeKeyListener(this.mKeySearchListener);
      th.removeKeyListener(this.mKeySearchListener);
      this.mTableView = null;
      this.mListMouseListener = null;
      th = null;
   }

   protected void searchTableContents() {
      int viewColumn = this.mTableView.getSelectedColumn();
      if(viewColumn != -1) {
         int column = this.mTableView.convertColumnIndexToModel(viewColumn);
         int found = -1;

         int searchIndex;
         for(searchIndex = this.mSearchStartRow + 1; found == -1 && searchIndex < this.mIndexes.length; ++searchIndex) {
            found = this.checkSearchRow(searchIndex, column);
         }

         for(searchIndex = 0; found == -1 && searchIndex < this.mSearchStartRow; ++searchIndex) {
            found = this.checkSearchRow(searchIndex, column);
         }

         if(found != -1) {
            this.mTableView.changeSelection(found, viewColumn, false, false);
         }

      }
   }

   private int checkSearchRow(int index, int column) {
      if(index == -1) {
         return -1;
      } else {
         Object value = this.getValueAt(index, column);
         String entry = null;
         if(value instanceof Number) {
            double b = ((Number)value).doubleValue();
            entry = String.valueOf(b);
         } else if(value instanceof String) {
            entry = (String)value;
         } else if(value instanceof Boolean) {
            boolean b1 = ((Boolean)value).booleanValue();
            entry = String.valueOf(b1);
         } else {
            entry = value.toString();
         }

         return entry.toLowerCase().startsWith(this.mSearchString)?index:-1;
      }
   }

   public int getSortingColumn() {
      return this.mSortingColumn;
   }

   public boolean isAscending() {
      return this.mAscending;
   }

   // $FF: synthetic method
   static JTable accessMethod000(TableSorter x0) {
      return x0.mTableView;
   }

   // $FF: synthetic method
   static long accessMethod100(TableSorter x0) {
      return x0.mLastKeyPressTime;
   }

   // $FF: synthetic method
   static int accessMethod202(TableSorter x0, int x1) {
      return x0.mSearchStartRow = x1;
   }

   // $FF: synthetic method
   static String accessMethod302(TableSorter x0, String x1) {
      return x0.mSearchString = x1;
   }

   // $FF: synthetic method
   static String accessMethod300(TableSorter x0) {
      return x0.mSearchString;
   }

   // $FF: synthetic method
   static long accessMethod102(TableSorter x0, long x1) {
      return x0.mLastKeyPressTime = x1;
   }

   // $FF: synthetic method
   static Map accessMethod400(TableSorter x0) {
      return x0.mSortedColumnMap;
   }
}
