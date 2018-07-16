// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.dataEntry.CellCalculation2AccountMapping;

public class CellCalculation2AccountMappingImpl implements CellCalculation2AccountMapping {

   private int mAccountElement;
   private String mDataType;
   private int mXmlFormId;
   private int mCellCalcId;
   private String mSummaryId;
   private boolean mIsForSummaryPeriod;


   public CellCalculation2AccountMappingImpl(int account, String dataType, int cellCalcId, int xmlForm, String summaryId, boolean summaryPeriod) {
      this.mAccountElement = account;
      this.mDataType = dataType;
      this.mCellCalcId = cellCalcId;
      this.mXmlFormId = xmlForm;
      this.mSummaryId = summaryId;
      this.mIsForSummaryPeriod = summaryPeriod;
   }

   public int getAccountId() {
      return this.mAccountElement;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public int getCellCalcId() {
      return this.mCellCalcId;
   }

   public int getXmlFormId() {
      return this.mXmlFormId;
   }

   public String getSummaryId() {
      return this.mSummaryId;
   }

   public boolean isForSummaryPeriod() {
      return this.mIsForSummaryPeriod;
   }
}
