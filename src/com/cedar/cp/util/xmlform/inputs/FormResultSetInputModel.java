// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FormResultSetInputModel implements FormInputModel, Serializable {

   private int mColumnCount;
   private Object[][] mData = new Object[0][];
   private int mSqlRowCount = 0;
   private int mSheetProtectionLevel = 0;
   private transient Log mLog = new Log(this.getClass());
   private static final int PROTECTION_ELEMENT_CUBE_FORMULA_TARGET = 22;
   private static final int OPTIONAL_COLUMNS_SIZE = 3;
   private static final int COLUMNS_PER_FORM_COLUMN = 4;


   public void setSheetProtectionLevel(int protectionLevel) {
      this.mSheetProtectionLevel = protectionLevel;
   }

   public int getSheetProtectionLevel() {
      return this.mSheetProtectionLevel;
   }

   public int storeSqlRows(int numCols, ResultSet resultSet) {
      this.mColumnCount = 12 + numCols * 6;

      try {
         Integer e = null;
         Integer prevAxisDimIndex = null;
         Integer prevColumn = null;
         ArrayList data = new ArrayList();
         this.mSqlRowCount = 0;
         Object[] row = null;
         Object[] optionalData = null;

         while(resultSet.next()) {
            ++this.mSqlRowCount;
            Integer dim = Integer.valueOf(resultSet.getInt(10));
            Integer axisDimIndex = Integer.valueOf(resultSet.getInt(12));
            int col = resultSet.getInt("COL");
            BigDecimal noteInd = resultSet.getBigDecimal("I");
            String note = resultSet.getString("T");
            BigDecimal calcShortId = resultSet.getBigDecimal("C");
            Object val = resultSet.getObject("N");
            if(val == null) {
               val = resultSet.getObject("S");
               if(val == null) {
                  val = resultSet.getTimestamp("D");
               }
            }

            if(e == null || !e.equals(dim) || axisDimIndex == null || !prevAxisDimIndex.equals(axisDimIndex)) {
               e = dim;
               prevAxisDimIndex = axisDimIndex;
               row = new Object[13 + 2 * numCols];
               data.add(row);
            }

            if(row[0] == null) {
               row[0] = resultSet.getObject(1);
            }

            if(row[1] == null) {
               row[1] = resultSet.getObject(2);
            }

            if(row[2] == null) {
               row[2] = resultSet.getObject(3);
            }

            if(row[3] == null) {
               row[3] = resultSet.getObject(4);
            }

            if(row[4] == null) {
               row[4] = resultSet.getObject(5);
            }

            if(row[5] == null) {
               row[5] = resultSet.getObject(6);
            }

            if(row[6] == null) {
               row[6] = resultSet.getObject(7);
            }

            if(row[7] == null) {
               row[7] = resultSet.getObject(8);
            }

            if(row[8] == null) {
               row[8] = resultSet.getObject(9);
            }

            if(row[9] == null) {
               row[9] = resultSet.getObject(10);
            }

            if(row[10] == null) {
               row[10] = resultSet.getObject(11);
            }

            if(row[11] == null) {
               row[11] = resultSet.getObject(12);
            }

            int index = 12 + (col - 1) * 2;
            if(prevColumn == null || prevColumn.intValue() != col) {
               prevColumn = Integer.valueOf(col);
               optionalData = new Object[3];
            }

            if(row[index] == null) {
               row[index] = val;
            }

            ++index;
            if(optionalData[0] == null) {
               optionalData[0] = noteInd;
            }

            if(optionalData[1] == null) {
               optionalData[1] = note;
            }

            if(optionalData[2] == null) {
               optionalData[2] = calcShortId;
            }

            if(noteInd != null || note != null || calcShortId != null) {
               row[index] = optionalData;
               optionalData = new Object[3];
            }
         }

         this.mData = (Object[][])((Object[][])data.toArray(this.mData));
      } catch (Exception var17) {
         this.mLog.debug("Warning: " + var17.getMessage());
         var17.printStackTrace();
      }

      return this.mSqlRowCount;
   }

   public int getSqlRowCount() {
      return this.mSqlRowCount;
   }

   public int getRowCount() {
      return this.mData.length;
   }

   public int getColumnCount() {
      return this.mColumnCount;
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      if(columnIndex < 12) {
         return this.mData[rowIndex][columnIndex];
      } else {
         int colNum = (columnIndex - 12) / 4;
         int colOffset = (columnIndex - 12) % 4;
         if(colOffset == 0) {
            return this.mData[rowIndex][12 + colNum * 2];
         } else {
            Object[] optionalData = (Object[])((Object[])this.mData[rowIndex][12 + colNum * 2 + 1]);
            return optionalData == null?null:optionalData[colOffset - 1];
         }
      }
   }

   public void setValueAt(Object value, int rowIndex, int columnIndex) {
      if(columnIndex < 12) {
         this.mData[rowIndex][columnIndex] = value;
      } else {
         int colNum = (columnIndex - 12) / 4;
         int colOffset = (columnIndex - 12) % 4;
         if(colOffset == 0) {
            this.mData[rowIndex][12 + colNum * 2] = value;
         } else {
            Object[] optionalData = this.mData[12 + colNum * 2 + 1];
            if(optionalData == null) {
               optionalData = new Object[3];
            }

            optionalData[colOffset - 1] = value;
            this.mData[rowIndex][colNum * 2 + 1] = optionalData;
         }

      }
   }
}
