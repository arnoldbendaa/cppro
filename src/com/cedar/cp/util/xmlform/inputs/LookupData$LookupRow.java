// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.inputs.LookupData;
import java.io.Serializable;
import java.util.Date;

class LookupData$LookupRow implements Serializable {

   Date mStartDate;
   Date mEndDate;
   Object[] mData;
   // $FF: synthetic field
   final LookupData this$0;


   public LookupData$LookupRow(LookupData var1, Date startDate, Date endDate, Object[] data) {
      this.this$0 = var1;
      this.mStartDate = startDate;
      this.mEndDate = endDate;
      this.mData = data;
   }

   public Object getValue(int i) {
      return this.mData[i];
   }

   public long getStartDateDistance(Date paramDate) {
      return paramDate.getTime() - this.mStartDate.getTime();
   }

   public long getEndDateDistance(Date paramDate) {
      return this.mEndDate.getTime() - paramDate.getTime();
   }
}
