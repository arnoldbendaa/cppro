// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;


public class ExtSysImportRow {

   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mCalendarYearVisId;
   private String[] mVisId = new String[10];
   private String mCurrencyVisId;
   private String mValueTypeVisId;
   private long mValue;


   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }

   public void setExternalSystemId(int externalSystemId) {
      this.mExternalSystemId = externalSystemId;
   }

   public String getCompanyVisId() {
      return this.mCompanyVisId;
   }

   public void setCompanyVisId(String companyVisId) {
      this.mCompanyVisId = companyVisId;
   }

   public String getLedgerVisId() {
      return this.mLedgerVisId;
   }

   public void setLedgerVisId(String ledgerVisId) {
      this.mLedgerVisId = ledgerVisId;
   }

   public String[] getVisId() {
      return this.mVisId;
   }

   public String getCurrencyVisId() {
      return this.mCurrencyVisId;
   }

   public void setCurrencyVisId(String currencyVisId) {
      this.mCurrencyVisId = currencyVisId;
   }

   public String getValueTypeVisId() {
      return this.mValueTypeVisId;
   }

   public void setValueTypeVisId(String valueTypeVisId) {
      this.mValueTypeVisId = valueTypeVisId;
   }

   public String getCalendarYearVisId() {
      return this.mCalendarYearVisId;
   }

   public void setCalendarYearVisId(String calendarYearVisId) {
      this.mCalendarYearVisId = calendarYearVisId;
   }

   public long getValue() {
      return this.mValue;
   }

   public void setValue(long value) {
      this.mValue = value;
   }
}
