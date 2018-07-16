// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import java.util.ArrayList;
import java.util.List;

public class CPInstallProperties$SqlQueryList {

   private String[] mColumnNames;
   private List<Object[]> mList;
   private Object[] mCurrentRow;
   private int mSqlCode;
   private String mSqlMessage;
   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$SqlQueryList(CPInstallProperties var1) {
      this.this$0 = var1;
      this.mList = new ArrayList();
      this.mSqlCode = 0;
      this.mSqlMessage = null;
   }

   public void setColumnNames(String[] colNames) {
      this.mColumnNames = colNames;
   }

   public String getColumnName(int row) {
      return this.mColumnNames[row];
   }

   public void addRow() {
      this.mCurrentRow = new Object[this.mColumnNames.length];
      this.mList.add(this.mCurrentRow);
   }

   public void addColumn(int colIndex, Object value) {
      this.mCurrentRow[colIndex] = value;
   }

   public void setCurrentRow(int row) {
      this.mCurrentRow = (Object[])this.mList.get(row);
   }

   public Object getColumn(String columnName) {
      for(int i = 0; i < this.mColumnNames.length; ++i) {
         if(this.mColumnNames[i].equals(columnName)) {
            return this.mCurrentRow[i];
         }
      }

      throw new RuntimeException("column name <" + columnName + "> not found");
   }

   public Object getColumn(int col) {
      return this.mCurrentRow[col];
   }

   public int getNumRows() {
      return this.mList.size();
   }

   public int getNumColumns() {
      return this.mColumnNames.length;
   }

   public void setSqlCode(int errorCode) {
      this.mSqlCode = errorCode;
   }

   public int getSqlCode() {
      return this.mSqlCode;
   }

   public void setSqlMessage(String message) {
      this.mSqlMessage = message;
   }

   public String getSqlMessage() {
      return this.mSqlMessage;
   }
}
