// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.xmlform.XmlFormWizardParameters;
import com.cedar.cp.util.ValueMapping;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.Serializable;

public class XmlFormWizardParametersImpl implements XmlFormWizardParameters, Serializable {

   private int mAccountDimensionIndex;
   private ValueMapping mAccountHierarchies;
   private int mSecondaryDimensionIndex;
   private ValueMapping mSecondaryHierarchies;
   private ValueMapping mDataTypes;
   private ValueMapping mPeriodLeaves;
   private CalendarInfo mCalendarInfo;


   public int getAccountDimensionIndex() {
      return this.mAccountDimensionIndex;
   }

   public ValueMapping getAccountHierarchies() {
      return this.mAccountHierarchies;
   }

   public int getSecondaryDimensionIndex() {
      return this.mSecondaryDimensionIndex;
   }

   public ValueMapping getSecondaryHierarchies() {
      return this.mSecondaryHierarchies;
   }

   public ValueMapping getDataTypes() {
      return this.mDataTypes;
   }

   public ValueMapping getPeriodLeaves() {
      return this.mPeriodLeaves;
   }

   public void setAccountDimensionIndex(int index) {
      this.mAccountDimensionIndex = index;
   }

   public void setAccountHierarchies(ValueMapping hiers) {
      this.mAccountHierarchies = hiers;
   }

   public void setSecondaryDimensionIndex(int index) {
      this.mSecondaryDimensionIndex = index;
   }

   public void setSecondaryHierarchies(ValueMapping hiers) {
      this.mSecondaryHierarchies = hiers;
   }

   public void setDataTypes(ValueMapping dataTypes) {
      this.mDataTypes = dataTypes;
   }

   public void setPeriodLeaves(ValueMapping periods) {
      this.mPeriodLeaves = periods;
   }

   public CalendarInfo getCalendarInfo() {
      return this.mCalendarInfo;
   }

   public void setCalendarInfo(CalendarInfo calendarInfo) {
      this.mCalendarInfo = calendarInfo;
   }
}
