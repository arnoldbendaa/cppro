// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.CalendarYearSpecRef;
import com.cedar.cp.dto.dimension.CalendarYearSpecCK;
import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
import com.cedar.cp.dto.dimension.CalendarYearSpecRefImpl;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import java.io.Serializable;

public class CalendarYearSpecEVO implements Serializable {

   private transient CalendarYearSpecPK mPK;
   private int mCalendarYearSpecId;
   private int mDimensionId;
   private int mCalendarYear;
   private boolean mYearInd;
   private boolean mHalfYearInd;
   private boolean mQuarterInd;
   private boolean mMonthInd;
   private boolean mWeekInd;
   private boolean mDayInd;
   private boolean mOpeningBalanceInd;
   private boolean mAdjustmentInd;
   private boolean mPeriod13Ind;
   private boolean mPeriod14Ind;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CalendarYearSpecEVO() {}

   public CalendarYearSpecEVO(int newCalendarYearSpecId, int newDimensionId, int newCalendarYear, boolean newYearInd, boolean newHalfYearInd, boolean newQuarterInd, boolean newMonthInd, boolean newWeekInd, boolean newDayInd, boolean newOpeningBalanceInd, boolean newAdjustmentInd, boolean newPeriod13Ind, boolean newPeriod14Ind) {
      this.mCalendarYearSpecId = newCalendarYearSpecId;
      this.mDimensionId = newDimensionId;
      this.mCalendarYear = newCalendarYear;
      this.mYearInd = newYearInd;
      this.mHalfYearInd = newHalfYearInd;
      this.mQuarterInd = newQuarterInd;
      this.mMonthInd = newMonthInd;
      this.mWeekInd = newWeekInd;
      this.mDayInd = newDayInd;
      this.mOpeningBalanceInd = newOpeningBalanceInd;
      this.mAdjustmentInd = newAdjustmentInd;
      this.mPeriod13Ind = newPeriod13Ind;
      this.mPeriod14Ind = newPeriod14Ind;
   }

   public CalendarYearSpecPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CalendarYearSpecPK(this.mCalendarYearSpecId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCalendarYearSpecId() {
      return this.mCalendarYearSpecId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public int getCalendarYear() {
      return this.mCalendarYear;
   }

   public boolean getYearInd() {
      return this.mYearInd;
   }

   public boolean getHalfYearInd() {
      return this.mHalfYearInd;
   }

   public boolean getQuarterInd() {
      return this.mQuarterInd;
   }

   public boolean getMonthInd() {
      return this.mMonthInd;
   }

   public boolean getWeekInd() {
      return this.mWeekInd;
   }

   public boolean getDayInd() {
      return this.mDayInd;
   }

   public boolean getOpeningBalanceInd() {
      return this.mOpeningBalanceInd;
   }

   public boolean getAdjustmentInd() {
      return this.mAdjustmentInd;
   }

   public boolean getPeriod13Ind() {
      return this.mPeriod13Ind;
   }

   public boolean getPeriod14Ind() {
      return this.mPeriod14Ind;
   }

   public void setCalendarYearSpecId(int newCalendarYearSpecId) {
      if(this.mCalendarYearSpecId != newCalendarYearSpecId) {
         this.mModified = true;
         this.mCalendarYearSpecId = newCalendarYearSpecId;
         this.mPK = null;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
      }
   }

   public void setCalendarYear(int newCalendarYear) {
      if(this.mCalendarYear != newCalendarYear) {
         this.mModified = true;
         this.mCalendarYear = newCalendarYear;
      }
   }

   public void setYearInd(boolean newYearInd) {
      if(this.mYearInd != newYearInd) {
         this.mModified = true;
         this.mYearInd = newYearInd;
      }
   }

   public void setHalfYearInd(boolean newHalfYearInd) {
      if(this.mHalfYearInd != newHalfYearInd) {
         this.mModified = true;
         this.mHalfYearInd = newHalfYearInd;
      }
   }

   public void setQuarterInd(boolean newQuarterInd) {
      if(this.mQuarterInd != newQuarterInd) {
         this.mModified = true;
         this.mQuarterInd = newQuarterInd;
      }
   }

   public void setMonthInd(boolean newMonthInd) {
      if(this.mMonthInd != newMonthInd) {
         this.mModified = true;
         this.mMonthInd = newMonthInd;
      }
   }

   public void setWeekInd(boolean newWeekInd) {
      if(this.mWeekInd != newWeekInd) {
         this.mModified = true;
         this.mWeekInd = newWeekInd;
      }
   }

   public void setDayInd(boolean newDayInd) {
      if(this.mDayInd != newDayInd) {
         this.mModified = true;
         this.mDayInd = newDayInd;
      }
   }

   public void setOpeningBalanceInd(boolean newOpeningBalanceInd) {
      if(this.mOpeningBalanceInd != newOpeningBalanceInd) {
         this.mModified = true;
         this.mOpeningBalanceInd = newOpeningBalanceInd;
      }
   }

   public void setAdjustmentInd(boolean newAdjustmentInd) {
      if(this.mAdjustmentInd != newAdjustmentInd) {
         this.mModified = true;
         this.mAdjustmentInd = newAdjustmentInd;
      }
   }

   public void setPeriod13Ind(boolean newPeriod13Ind) {
      if(this.mPeriod13Ind != newPeriod13Ind) {
         this.mModified = true;
         this.mPeriod13Ind = newPeriod13Ind;
      }
   }

   public void setPeriod14Ind(boolean newPeriod14Ind) {
      if(this.mPeriod14Ind != newPeriod14Ind) {
         this.mModified = true;
         this.mPeriod14Ind = newPeriod14Ind;
      }
   }

   public void setDetails(CalendarYearSpecEVO newDetails) {
      this.setCalendarYearSpecId(newDetails.getCalendarYearSpecId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setCalendarYear(newDetails.getCalendarYear());
      this.setYearInd(newDetails.getYearInd());
      this.setHalfYearInd(newDetails.getHalfYearInd());
      this.setQuarterInd(newDetails.getQuarterInd());
      this.setMonthInd(newDetails.getMonthInd());
      this.setWeekInd(newDetails.getWeekInd());
      this.setDayInd(newDetails.getDayInd());
      this.setOpeningBalanceInd(newDetails.getOpeningBalanceInd());
      this.setAdjustmentInd(newDetails.getAdjustmentInd());
      this.setPeriod13Ind(newDetails.getPeriod13Ind());
      this.setPeriod14Ind(newDetails.getPeriod14Ind());
   }

   public CalendarYearSpecEVO deepClone() {
      CalendarYearSpecEVO cloned = new CalendarYearSpecEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mCalendarYearSpecId = this.mCalendarYearSpecId;
      cloned.mDimensionId = this.mDimensionId;
      cloned.mCalendarYear = this.mCalendarYear;
      cloned.mYearInd = this.mYearInd;
      cloned.mHalfYearInd = this.mHalfYearInd;
      cloned.mQuarterInd = this.mQuarterInd;
      cloned.mMonthInd = this.mMonthInd;
      cloned.mWeekInd = this.mWeekInd;
      cloned.mDayInd = this.mDayInd;
      cloned.mOpeningBalanceInd = this.mOpeningBalanceInd;
      cloned.mAdjustmentInd = this.mAdjustmentInd;
      cloned.mPeriod13Ind = this.mPeriod13Ind;
      cloned.mPeriod14Ind = this.mPeriod14Ind;
      return cloned;
   }

   public void prepareForInsert(DimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCalendarYearSpecId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCalendarYearSpecId(-this.mCalendarYearSpecId);
         } else {
            parent.changeKey(this, -this.mCalendarYearSpecId);
         }
      } else if(this.mCalendarYearSpecId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCalendarYearSpecId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(DimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCalendarYearSpecId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public CalendarYearSpecRef getEntityRef(DimensionEVO evoDimension, String entityText) {
      return new CalendarYearSpecRefImpl(new CalendarYearSpecCK(evoDimension.getPK(), this.getPK()), entityText);
   }

   public CalendarYearSpecCK getCK(DimensionEVO evoDimension) {
      return new CalendarYearSpecCK(evoDimension.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CalendarYearSpecId=");
      sb.append(String.valueOf(this.mCalendarYearSpecId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("CalendarYear=");
      sb.append(String.valueOf(this.mCalendarYear));
      sb.append(' ');
      sb.append("YearInd=");
      sb.append(String.valueOf(this.mYearInd));
      sb.append(' ');
      sb.append("HalfYearInd=");
      sb.append(String.valueOf(this.mHalfYearInd));
      sb.append(' ');
      sb.append("QuarterInd=");
      sb.append(String.valueOf(this.mQuarterInd));
      sb.append(' ');
      sb.append("MonthInd=");
      sb.append(String.valueOf(this.mMonthInd));
      sb.append(' ');
      sb.append("WeekInd=");
      sb.append(String.valueOf(this.mWeekInd));
      sb.append(' ');
      sb.append("DayInd=");
      sb.append(String.valueOf(this.mDayInd));
      sb.append(' ');
      sb.append("OpeningBalanceInd=");
      sb.append(String.valueOf(this.mOpeningBalanceInd));
      sb.append(' ');
      sb.append("AdjustmentInd=");
      sb.append(String.valueOf(this.mAdjustmentInd));
      sb.append(' ');
      sb.append("Period13Ind=");
      sb.append(String.valueOf(this.mPeriod13Ind));
      sb.append(' ');
      sb.append("Period14Ind=");
      sb.append(String.valueOf(this.mPeriod14Ind));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("CalendarYearSpec: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
