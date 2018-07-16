// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.util.SqlResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlResultSetImpl implements SqlResultSet {

   private int mNumColumns;
   private String[] mColumnName;
   private Class[] mColumnClass;
   private List<Object[]> mData = new ArrayList();


   public SqlResultSetImpl(int numColumns) {
      this.mNumColumns = numColumns;
      this.mColumnName = new String[this.mNumColumns];
      this.mColumnClass = new Class[this.mNumColumns];
   }

   public void setColumnName(int colIndex, String columnName) {
      this.mColumnName[colIndex] = columnName;
   }

   public void setColumnClass(int colIndex, String columnClassName) {
      try {
         this.mColumnClass[colIndex] = Class.forName(columnClassName);
      } catch (ClassNotFoundException var4) {
         throw new IllegalArgumentException("class " + columnClassName + " not found", var4);
      }
   }

   public void addRow(Object[] row) {
      this.mData.add(row);
   }

   public void setValueAt(int rowIndex, int colIndex, Object value) {
      ((Object[])this.mData.get(rowIndex))[colIndex] = value;
   }

   public List<Object[]> getList() {
      return this.mData;
   }

   public Object[][] getData() {
      Object[][] returnArray = new Object[this.getNumRows()][this.getNumColumns()];

      for(int i = 0; i < this.getNumRows(); ++i) {
         returnArray[i] = (Object[])this.mData.get(i);
      }

      return returnArray;
   }

   public int getNumRows() {
      return this.mData.size();
   }

   public int getNumColumns() {
      return this.mNumColumns;
   }

   public int getColumnNameIndex(String columnName) {
      int colIndex = -1;

      for(int i = 0; i < this.mColumnName.length; ++i) {
         if(this.mColumnName[i].equalsIgnoreCase(columnName)) {
            colIndex = i;
            break;
         }
      }

      if(colIndex == -1) {
         throw new IllegalArgumentException("column name " + columnName + " not found");
      } else {
         return colIndex;
      }
   }

   public String getColumnName(int colIndex) {
      return this.mColumnName[colIndex];
   }

   public Class getColumnClass(int colIndex) {
      return this.mColumnClass[colIndex];
   }

   public Object getValueAt(int rowIndex, int colIndex) {
      return ((Object[])this.mData.get(rowIndex))[colIndex];
   }

   public Object getValueAt(int rowIndex, String columnName) {
      return this.getValueAt(rowIndex, this.getColumnNameIndex(columnName));
   }
}
