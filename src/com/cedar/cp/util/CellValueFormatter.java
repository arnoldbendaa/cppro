// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.NumberFormatter;
import java.io.Serializable;
import java.text.ParseException;

public class CellValueFormatter implements Serializable {

   private long mUnits;
   private int mScale;
   private double mMultiplier;


   public CellValueFormatter(long units, int scale) {
      this.mUnits = units;
      this.mScale = scale;
      this.mMultiplier = Math.pow(10.0D, (double)scale);
   }

   public String format(long value, long units) {
      long val = units == 0L?value:value * this.mUnits / units;
      return this.mScale == 0?NumberFormatter.format(val):NumberFormatter.format(val, this.mScale);
   }

   public String format(long value, long units, boolean useGrouping) {
      long val = units == 0L?value:value * this.mUnits / units;
      return this.mScale == 0?NumberFormatter.format(val, useGrouping):NumberFormatter.format(val, this.mScale, useGrouping);
   }

   public Long parse(String input, long units) throws ParseException {
      Long l;
      if(this.mScale == 0) {
         l = NumberFormatter.parse(input);
      } else {
         l = NumberFormatter.parse(input, this.mScale);
      }

      if(units != this.mUnits) {
         l = new Long(l.longValue() * units / this.mUnits);
      }

      return l;
   }

   public long longValue(double d) {
      double interim = d / (double)this.mUnits;
      return Math.round(Math.rint(interim * this.mMultiplier));
   }

   public double doubleValue(long l) {
      return (double)l * (double)this.mUnits / this.mMultiplier;
   }

   public String toString() {
      return "CellValueFormatter mUnits=" + this.mUnits + ",Scale=" + this.mScale;
   }
}
