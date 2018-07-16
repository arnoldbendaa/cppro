// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn;

import java.io.Serializable;

public class ColumnMappingEntry implements Serializable {

   private String mImportColumn;
   private String mCalcColumn;
   private String mConversionCode;


   public ColumnMappingEntry(String importColumnName, String calcColumn, String conversionCode) {
      this.mImportColumn = importColumnName;
      this.mCalcColumn = calcColumn;
      this.mConversionCode = conversionCode;
   }

   public String getImportColumn() {
      return this.mImportColumn;
   }

   public String getCalcColumn() {
      return this.mCalcColumn;
   }

   public String getConversionCode() {
      return this.mConversionCode;
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(!(obj instanceof ColumnMappingEntry)) {
         return false;
      } else {
         ColumnMappingEntry other = (ColumnMappingEntry)obj;
         return other.getImportColumn().equals(this.getImportColumn()) && other.getCalcColumn().equals(this.getCalcColumn()) && (other.getConversionCode() == null && this.getConversionCode() == null || other.getConversionCode() != null && this.getConversionCode() != null && other.getConversionCode().equals(this.getConversionCode()));
      }
   }

   public int hashCode() {
      return this.mImportColumn != null?this.mImportColumn.hashCode():0;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("importColumn:").append(this.mImportColumn).append(" calcColumn:").append(this.getCalcColumn());
      if(this.mConversionCode != null) {
         sb.append("conversionCode:").append(this.mConversionCode);
      }

      return sb.toString();
   }
}
