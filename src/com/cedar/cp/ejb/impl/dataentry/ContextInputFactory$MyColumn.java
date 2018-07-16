// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dataentry;

import com.cedar.cp.ejb.impl.dataentry.ContextInputFactory;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.DataTypeColumnValue;

class ContextInputFactory$MyColumn {

   private DataTypeColumnValue mColumn;
   private CalendarElementNode mCalendarElement;
   // $FF: synthetic field
   final ContextInputFactory this$0;


   public ContextInputFactory$MyColumn(ContextInputFactory var1, DataTypeColumnValue column) {
      this.this$0 = var1;
      this.mColumn = column;
   }

   public int getYearOffset() {
      return this.mColumn.getYear();
   }

   public boolean isFixedPeriod() {
      return this.mColumn.getPeriodVisId() != null && this.mColumn.getPeriodVisId().trim().length() != 0;
   }

   public boolean isRuntimePeriod() {
      return !this.isFixedPeriod()?false:this.getPeriodVisId().indexOf(63) < 0;
   }

   public int getPeriodElemId() {
      return this.mCalendarElement == null?-1:this.mCalendarElement.getStructureElementId();
   }

   public boolean isFixedDataType() {
      return this.mColumn.getValue() != null && this.mColumn.getValue().trim().length() != 0;
   }

   public String getPeriodVisId() {
      return this.mColumn.getPeriodVisId();
   }

   public boolean isYtd() {
      return this.mColumn.getYtd();
   }

   public int getPeriodOffset() {
      return this.mColumn.getPeriod();
   }

   public String getDataType() {
      return this.mColumn.getValue();
   }

   public void setCalendarElement(CalendarElementNode calElem) {
      this.mCalendarElement = calElem;
   }

   public CalendarElementNode getCalendarElement() {
      return this.mCalendarElement;
   }

   public String toString() {
      return "value=\"" + (this.isFixedDataType()?this.getDataType():"") + "\"" + " ytd=\"" + this.isYtd() + "\"" + " year=\"" + this.getYearOffset() + "\"" + " period=\"" + this.getPeriodOffset() + "\"" + " periodVisId=\"" + (this.isFixedPeriod()?this.getPeriodVisId():"") + "\"";
   }
}
