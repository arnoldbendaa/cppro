// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.Cell;

public class CellPosting extends Cell {

   private String mDelta;
   private String mStringValue;
   private String mNumberValue;
   private String mTimeValue;
   private String mDateValue;
   private String mDateTimeValue;
   private String mBooleanValue;
   private String mCompany;


   public String getDelta() {
      return this.mDelta;
   }

   public void setDelta(String delta) {
      this.mDelta = delta;
   }

   public String getStringValue() {
      return this.mStringValue;
   }

   public void setStringValue(String stringValue) {
      this.mStringValue = stringValue;
   }

   public String getNumberValue() {
      return this.mNumberValue;
   }

   public void setNumberValue(String numberValue) {
      this.mNumberValue = numberValue;
   }

   public String getTimeValue() {
      return this.mTimeValue;
   }

   public void setTimeValue(String timeValue) {
      this.mTimeValue = timeValue;
   }

   public String getDateValue() {
      return this.mDateValue;
   }

   public void setDateValue(String dateValue) {
      this.mDateValue = dateValue;
   }

   public String getDateTimeValue() {
      return this.mDateTimeValue;
   }

   public void setDateTimeValue(String dateTime) {
      this.mDateTimeValue = dateTime;
   }

   public String getBooleanValue() {
      return this.mBooleanValue;
   }

   public void setBooleanValue(String booleanValue) {
      this.mBooleanValue = booleanValue;
   }

   public boolean isNumericPosting() {
      return this.mDelta != null || this.mNumberValue != null;
   }

   public String getValue() {
      return this.mDelta != null?this.mDelta:(this.mStringValue != null?this.mStringValue:(this.mNumberValue != null?this.mNumberValue:(this.mTimeValue != null?this.mTimeValue:(this.mDateValue != null?this.mDateValue:(this.mDateTimeValue != null?this.mDateTimeValue:(this.mBooleanValue != null?this.mBooleanValue:"No Value?"))))));
   }

	public String getCompany() {
		return mCompany;
	}
	
	public void setCompany(String mCompany) {
		this.mCompany = mCompany;
	}
}
