// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import java.io.Serializable;

public class ColumnModelData implements Serializable {

   private String mPeriodVisId;
   private int mYear;
   private int mPeriod;
   private boolean mYtd;
   private String mDataType;
   private boolean mPeriodInherited;
   private boolean mDataTypeInherited;


   public ColumnModelData(String periodVisId, int year, int period, boolean ytd, String dataType) {
      this.mPeriodVisId = periodVisId;
      this.mYear = year;
      this.mPeriod = period;
      this.mYtd = ytd;
      this.mPeriodInherited = periodVisId == null || periodVisId.trim().length() == 0;
      this.mDataType = dataType;
      this.mDataTypeInherited = dataType == null || dataType.trim().length() == 0;
   }

   public String getPeriodVisId() {
      return this.mPeriodVisId;
   }

   public boolean isDataTypeInherited() {
      return this.mDataTypeInherited;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public boolean isPeriodInherited() {
      return this.mPeriodInherited;
   }

   public boolean isYtd() {
      return this.mYtd;
   }

   public int getYear() {
      return this.mYear;
   }

   public void setYear(int year) {
      this.mYear = year;
   }

   public int getPeriod() {
      return this.mPeriod;
   }

   public void setPeriod(int period) {
      this.mPeriod = period;
   }
}
