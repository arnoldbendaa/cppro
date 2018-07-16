// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ResultSetInputModel implements FormInputModel, Serializable {

   private int mColumnCount;
   protected Object[][] mData = new Object[0][];
   private Class[] mColumnClasses;
   private String[] mColumnNames;
   private transient Log mLog = new Log(this.getClass());


   public ResultSetInputModel(ResultSet resultSet) {
      if(resultSet != null) {
         try {
            ResultSetMetaData metadata = resultSet.getMetaData();
            this.mColumnCount = metadata.getColumnCount();
            this.mColumnClasses = new Class[this.mColumnCount];
            this.mColumnNames = new String[this.mColumnCount];

            for(int e = 1; e <= this.mColumnCount; ++e) {
               String count = metadata.getColumnClassName(e);
               this.mColumnClasses[e - 1] = Class.forName(count);
               this.mColumnNames[e - 1] = metadata.getColumnName(e);
            }

            ArrayList var9 = new ArrayList();
            int var10 = 0;

            while(resultSet.next()) {
               ++var10;
               Object[] row = new Object[this.mColumnCount];
               var9.add(row);

               for(int i = 0; i < this.mColumnCount; ++i) {
                  int sqlIndex = i + 1;
                  switch(metadata.getColumnType(sqlIndex)) {
                  case 91:
                  case 92:
                  case 93:
                     row[i] = resultSet.getTimestamp(sqlIndex);
                     break;
                  default:
                     row[i] = resultSet.getObject(sqlIndex);
                  }
               }
            }

            this.mData = (Object[][])((Object[][])var9.toArray(this.mData));
         } catch (Exception var8) {
            this.mLog.debug("Warning: " + var8.getMessage());
            var8.printStackTrace();
         }
      } else {
         this.mLog.debug("Warning: No result set to view table");
      }

   }

   public ResultSetInputModel(String[] columnNames, Class[] columnClasses, List<Object[]> rs) {
      this.mColumnNames = columnNames;
      this.mColumnClasses = columnClasses;
      this.mData = new Object[rs.size()][];

      for(int i = 0; i < rs.size(); ++i) {
         this.mData[i] = (Object[])rs.get(i);
      }

      this.mColumnCount = this.mColumnNames.length;
   }

   public int getRowCount() {
      return this.mData.length;
   }

   public String getColumnName(int col) {
      return this.mColumnNames[col];
   }

   public int getColumnCount() {
      return this.mColumnCount;
   }

   public Class getColumnClass(int columnIndex) {
      return this.mColumnClasses[columnIndex];
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      return this.mData[rowIndex][columnIndex];
   }

   public void setValueAt(Object value, int row, int col) {
      this.mData[row][col] = value;
   }

   public int getSheetProtectionLevel() {
      return 0;
   }
}
