// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.Serializable;

public class Interval implements Comparable, Serializable {

   private int mStart;
   private int mEnd;


   public Interval(int start, int end) {
      this.mStart = Math.min(start, end);
      this.mEnd = Math.max(start, end);
   }

   public Interval(Interval interval) {
      this.mStart = interval.getStart();
      this.mEnd = interval.getEnd();
   }

   public int getStart() {
      return this.mStart;
   }

   public void setStart(int start) {
      this.mStart = start;
   }

   public int getEnd() {
      return this.mEnd;
   }

   public void setEnd(int end) {
      this.mEnd = end;
   }

   public void set(Interval src) {
      this.mStart = src.mStart;
      this.mEnd = src.mEnd;
   }

   public int hashCode() {
      return this.mStart * this.mEnd;
   }

   public boolean intersects(Interval other) {
      return this.mStart <= other.mEnd && this.mEnd >= other.mStart;
   }

   public int size() {
      return this.mEnd - this.mStart;
   }

   public boolean inside(Interval other) {
      return this.mStart <= other.mStart && other.mEnd <= this.mEnd;
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof Interval)) {
         return false;
      } else {
         Interval other = (Interval)obj;
         return this.mStart == other.mStart && this.mEnd == other.mEnd;
      }
   }

   public int compareTo(Object o) {
      if(o == this) {
         return 0;
      } else if(o instanceof Interval) {
         Interval other = (Interval)o;
         return this.mStart < other.mStart?-1:(this.mStart == other.mStart?(this.mEnd < other.mEnd?-1:(this.mEnd == other.mEnd?0:1)):1);
      } else {
         return -1;
      }
   }

   public String toString() {
      return "start:" + this.mStart + " end:" + this.mEnd;
   }
}
