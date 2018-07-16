// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import java.io.Serializable;
import java.util.Properties;

public class ExtSysTransactionQueryParams implements Serializable {

   private String mHeading;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String[] mPathVisIds;
   private String[] mNullElemVisIds;
   private String[] mElemVisIds;
   private String[] mElemDescrs;
   private Integer mYear;
   private String[] mPeriods;
   private Integer mHighestPeriod;
   private String mValueType;
   private String mCurrency;
   private String mCurrencyType;
   private Properties mModelProperties;


   public ExtSysTransactionQueryParams(String heading, String companyVisId, String ledgerVisId, String[] pathVisIds, String[] nullElemVisIds, String[] elemVisIds, String[] elemDescrs, Integer year, String[] periods, Integer highestPeriod, String valueType, String currency, String currencyType) {
      this.mHeading = heading;
      this.mCompanyVisId = companyVisId;
      this.mLedgerVisId = ledgerVisId;
      this.mPathVisIds = pathVisIds;
      this.mNullElemVisIds = nullElemVisIds;
      this.mElemVisIds = elemVisIds;
      this.mElemDescrs = elemDescrs;
      this.mYear = year;
      this.mPeriods = periods;
      this.mHighestPeriod = highestPeriod;
      this.mValueType = valueType;
      this.mCurrency = currency;
      this.mCurrencyType = currencyType;
   }

   public String getHeading() {
      return this.mHeading;
   }

   public String getCompanyVisId() {
      return this.mCompanyVisId;
   }

   public String getLedgerVisId() {
      return this.mLedgerVisId;
   }

   public String[] getPathVisIds() {
      return this.mPathVisIds;
   }

   public String[] getNullElemVisIds() {
      return this.mNullElemVisIds;
   }

   public String[] getElemVisIds() {
      return this.mElemVisIds;
   }

   public String[] getElemDescrs() {
      return this.mElemDescrs;
   }

   public Integer getYear() {
      return this.mYear;
   }

   public String[] getPeriods() {
      return this.mPeriods;
   }

   public Integer getHighestPeriod() {
      return this.mHighestPeriod;
   }

   public String getValueType() {
      return this.mValueType;
   }

   public String getCurrency() {
      return this.mCurrency;
   }

   public String getCurrencyType() {
      return this.mCurrencyType;
   }

   public Properties getModelProperties() {
      return this.mModelProperties;
   }

   public void setModelProperties(Properties modelProperties) {
      this.mModelProperties = modelProperties;
   }
}
