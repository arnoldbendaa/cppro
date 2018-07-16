// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.SecurityRangeRow;

public class SecurityRangeRowImpl implements SecurityRangeRow {

   private String mFrom = "";
   private String mTo = "";


   public String getFrom() {
      return this.mFrom;
   }

   public void setFrom(String from) {
      this.mFrom = from;
   }

   public String getTo() {
      return this.mTo;
   }

   public void setTo(String to) {
      this.mTo = to;
   }

   public int compareTo(Object o) {
      if(o instanceof SecurityRangeRow) {
         SecurityRangeRow other = (SecurityRangeRow)o;
         int fcmp = this.mFrom.compareTo(other.getFrom());
         int tcmp = this.mTo.compareTo(other.getTo());
         return fcmp != 0?fcmp:tcmp;
      } else {
         throw new IllegalArgumentException("SecurityRangeRowImpl:compateTo()-Expected SecurityRangeRow");
      }
   }
}
