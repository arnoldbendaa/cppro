// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.CalendarSpecRef;
import com.cedar.cp.dto.dimension.CalendarSpecCK;
import com.cedar.cp.dto.dimension.CalendarSpecPK;
import com.cedar.cp.dto.dimension.CalendarSpecRefImpl;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import java.io.Serializable;

public class CalendarSpecEVO implements Serializable {

   private transient CalendarSpecPK mPK;
   private int mCalendarSpecId;
   private int mDimensionId;
   private int mYearStartMonth;
   private String mYearVisIdFormat;
   private String mYearDescrFormat;
   private String mHalfYearVisIdFormat;
   private String mHalfYearDescrFormat;
   private String mQuarterVisIdFormat;
   private String mQuarterDescrFormat;
   private String mMonthVisIdFormat;
   private String mMonthDescrFormat;
   private String mWeekVisIdFormat;
   private String mWeekDescrFormat;
   private String mDayVisIdFormat;
   private String mDayDescrFormat;
   private String mOpenVisIdFormat;
   private String mOpenDescrFormat;
   private String mAdjVisIdFormat;
   private String mAdjDescrFormat;
   private String mP13VisIdFormat;
   private String mP13DescrFormat;
   private String mP14VisIdFormat;
   private String mP14DescrFormat;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CalendarSpecEVO() {}

   public CalendarSpecEVO(int newCalendarSpecId, int newDimensionId, int newYearStartMonth, String newYearVisIdFormat, String newYearDescrFormat, String newHalfYearVisIdFormat, String newHalfYearDescrFormat, String newQuarterVisIdFormat, String newQuarterDescrFormat, String newMonthVisIdFormat, String newMonthDescrFormat, String newWeekVisIdFormat, String newWeekDescrFormat, String newDayVisIdFormat, String newDayDescrFormat, String newOpenVisIdFormat, String newOpenDescrFormat, String newAdjVisIdFormat, String newAdjDescrFormat, String newP13VisIdFormat, String newP13DescrFormat, String newP14VisIdFormat, String newP14DescrFormat) {
      this.mCalendarSpecId = newCalendarSpecId;
      this.mDimensionId = newDimensionId;
      this.mYearStartMonth = newYearStartMonth;
      this.mYearVisIdFormat = newYearVisIdFormat;
      this.mYearDescrFormat = newYearDescrFormat;
      this.mHalfYearVisIdFormat = newHalfYearVisIdFormat;
      this.mHalfYearDescrFormat = newHalfYearDescrFormat;
      this.mQuarterVisIdFormat = newQuarterVisIdFormat;
      this.mQuarterDescrFormat = newQuarterDescrFormat;
      this.mMonthVisIdFormat = newMonthVisIdFormat;
      this.mMonthDescrFormat = newMonthDescrFormat;
      this.mWeekVisIdFormat = newWeekVisIdFormat;
      this.mWeekDescrFormat = newWeekDescrFormat;
      this.mDayVisIdFormat = newDayVisIdFormat;
      this.mDayDescrFormat = newDayDescrFormat;
      this.mOpenVisIdFormat = newOpenVisIdFormat;
      this.mOpenDescrFormat = newOpenDescrFormat;
      this.mAdjVisIdFormat = newAdjVisIdFormat;
      this.mAdjDescrFormat = newAdjDescrFormat;
      this.mP13VisIdFormat = newP13VisIdFormat;
      this.mP13DescrFormat = newP13DescrFormat;
      this.mP14VisIdFormat = newP14VisIdFormat;
      this.mP14DescrFormat = newP14DescrFormat;
   }

   public CalendarSpecPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CalendarSpecPK(this.mCalendarSpecId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCalendarSpecId() {
      return this.mCalendarSpecId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public int getYearStartMonth() {
      return this.mYearStartMonth;
   }

   public String getYearVisIdFormat() {
      return this.mYearVisIdFormat;
   }

   public String getYearDescrFormat() {
      return this.mYearDescrFormat;
   }

   public String getHalfYearVisIdFormat() {
      return this.mHalfYearVisIdFormat;
   }

   public String getHalfYearDescrFormat() {
      return this.mHalfYearDescrFormat;
   }

   public String getQuarterVisIdFormat() {
      return this.mQuarterVisIdFormat;
   }

   public String getQuarterDescrFormat() {
      return this.mQuarterDescrFormat;
   }

   public String getMonthVisIdFormat() {
      return this.mMonthVisIdFormat;
   }

   public String getMonthDescrFormat() {
      return this.mMonthDescrFormat;
   }

   public String getWeekVisIdFormat() {
      return this.mWeekVisIdFormat;
   }

   public String getWeekDescrFormat() {
      return this.mWeekDescrFormat;
   }

   public String getDayVisIdFormat() {
      return this.mDayVisIdFormat;
   }

   public String getDayDescrFormat() {
      return this.mDayDescrFormat;
   }

   public String getOpenVisIdFormat() {
      return this.mOpenVisIdFormat;
   }

   public String getOpenDescrFormat() {
      return this.mOpenDescrFormat;
   }

   public String getAdjVisIdFormat() {
      return this.mAdjVisIdFormat;
   }

   public String getAdjDescrFormat() {
      return this.mAdjDescrFormat;
   }

   public String getP13VisIdFormat() {
      return this.mP13VisIdFormat;
   }

   public String getP13DescrFormat() {
      return this.mP13DescrFormat;
   }

   public String getP14VisIdFormat() {
      return this.mP14VisIdFormat;
   }

   public String getP14DescrFormat() {
      return this.mP14DescrFormat;
   }

   public void setCalendarSpecId(int newCalendarSpecId) {
      if(this.mCalendarSpecId != newCalendarSpecId) {
         this.mModified = true;
         this.mCalendarSpecId = newCalendarSpecId;
         this.mPK = null;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
      }
   }

   public void setYearStartMonth(int newYearStartMonth) {
      if(this.mYearStartMonth != newYearStartMonth) {
         this.mModified = true;
         this.mYearStartMonth = newYearStartMonth;
      }
   }

   public void setYearVisIdFormat(String newYearVisIdFormat) {
      if(this.mYearVisIdFormat != null && newYearVisIdFormat == null || this.mYearVisIdFormat == null && newYearVisIdFormat != null || this.mYearVisIdFormat != null && newYearVisIdFormat != null && !this.mYearVisIdFormat.equals(newYearVisIdFormat)) {
         this.mYearVisIdFormat = newYearVisIdFormat;
         this.mModified = true;
      }

   }

   public void setYearDescrFormat(String newYearDescrFormat) {
      if(this.mYearDescrFormat != null && newYearDescrFormat == null || this.mYearDescrFormat == null && newYearDescrFormat != null || this.mYearDescrFormat != null && newYearDescrFormat != null && !this.mYearDescrFormat.equals(newYearDescrFormat)) {
         this.mYearDescrFormat = newYearDescrFormat;
         this.mModified = true;
      }

   }

   public void setHalfYearVisIdFormat(String newHalfYearVisIdFormat) {
      if(this.mHalfYearVisIdFormat != null && newHalfYearVisIdFormat == null || this.mHalfYearVisIdFormat == null && newHalfYearVisIdFormat != null || this.mHalfYearVisIdFormat != null && newHalfYearVisIdFormat != null && !this.mHalfYearVisIdFormat.equals(newHalfYearVisIdFormat)) {
         this.mHalfYearVisIdFormat = newHalfYearVisIdFormat;
         this.mModified = true;
      }

   }

   public void setHalfYearDescrFormat(String newHalfYearDescrFormat) {
      if(this.mHalfYearDescrFormat != null && newHalfYearDescrFormat == null || this.mHalfYearDescrFormat == null && newHalfYearDescrFormat != null || this.mHalfYearDescrFormat != null && newHalfYearDescrFormat != null && !this.mHalfYearDescrFormat.equals(newHalfYearDescrFormat)) {
         this.mHalfYearDescrFormat = newHalfYearDescrFormat;
         this.mModified = true;
      }

   }

   public void setQuarterVisIdFormat(String newQuarterVisIdFormat) {
      if(this.mQuarterVisIdFormat != null && newQuarterVisIdFormat == null || this.mQuarterVisIdFormat == null && newQuarterVisIdFormat != null || this.mQuarterVisIdFormat != null && newQuarterVisIdFormat != null && !this.mQuarterVisIdFormat.equals(newQuarterVisIdFormat)) {
         this.mQuarterVisIdFormat = newQuarterVisIdFormat;
         this.mModified = true;
      }

   }

   public void setQuarterDescrFormat(String newQuarterDescrFormat) {
      if(this.mQuarterDescrFormat != null && newQuarterDescrFormat == null || this.mQuarterDescrFormat == null && newQuarterDescrFormat != null || this.mQuarterDescrFormat != null && newQuarterDescrFormat != null && !this.mQuarterDescrFormat.equals(newQuarterDescrFormat)) {
         this.mQuarterDescrFormat = newQuarterDescrFormat;
         this.mModified = true;
      }

   }

   public void setMonthVisIdFormat(String newMonthVisIdFormat) {
      if(this.mMonthVisIdFormat != null && newMonthVisIdFormat == null || this.mMonthVisIdFormat == null && newMonthVisIdFormat != null || this.mMonthVisIdFormat != null && newMonthVisIdFormat != null && !this.mMonthVisIdFormat.equals(newMonthVisIdFormat)) {
         this.mMonthVisIdFormat = newMonthVisIdFormat;
         this.mModified = true;
      }

   }

   public void setMonthDescrFormat(String newMonthDescrFormat) {
      if(this.mMonthDescrFormat != null && newMonthDescrFormat == null || this.mMonthDescrFormat == null && newMonthDescrFormat != null || this.mMonthDescrFormat != null && newMonthDescrFormat != null && !this.mMonthDescrFormat.equals(newMonthDescrFormat)) {
         this.mMonthDescrFormat = newMonthDescrFormat;
         this.mModified = true;
      }

   }

   public void setWeekVisIdFormat(String newWeekVisIdFormat) {
      if(this.mWeekVisIdFormat != null && newWeekVisIdFormat == null || this.mWeekVisIdFormat == null && newWeekVisIdFormat != null || this.mWeekVisIdFormat != null && newWeekVisIdFormat != null && !this.mWeekVisIdFormat.equals(newWeekVisIdFormat)) {
         this.mWeekVisIdFormat = newWeekVisIdFormat;
         this.mModified = true;
      }

   }

   public void setWeekDescrFormat(String newWeekDescrFormat) {
      if(this.mWeekDescrFormat != null && newWeekDescrFormat == null || this.mWeekDescrFormat == null && newWeekDescrFormat != null || this.mWeekDescrFormat != null && newWeekDescrFormat != null && !this.mWeekDescrFormat.equals(newWeekDescrFormat)) {
         this.mWeekDescrFormat = newWeekDescrFormat;
         this.mModified = true;
      }

   }

   public void setDayVisIdFormat(String newDayVisIdFormat) {
      if(this.mDayVisIdFormat != null && newDayVisIdFormat == null || this.mDayVisIdFormat == null && newDayVisIdFormat != null || this.mDayVisIdFormat != null && newDayVisIdFormat != null && !this.mDayVisIdFormat.equals(newDayVisIdFormat)) {
         this.mDayVisIdFormat = newDayVisIdFormat;
         this.mModified = true;
      }

   }

   public void setDayDescrFormat(String newDayDescrFormat) {
      if(this.mDayDescrFormat != null && newDayDescrFormat == null || this.mDayDescrFormat == null && newDayDescrFormat != null || this.mDayDescrFormat != null && newDayDescrFormat != null && !this.mDayDescrFormat.equals(newDayDescrFormat)) {
         this.mDayDescrFormat = newDayDescrFormat;
         this.mModified = true;
      }

   }

   public void setOpenVisIdFormat(String newOpenVisIdFormat) {
      if(this.mOpenVisIdFormat != null && newOpenVisIdFormat == null || this.mOpenVisIdFormat == null && newOpenVisIdFormat != null || this.mOpenVisIdFormat != null && newOpenVisIdFormat != null && !this.mOpenVisIdFormat.equals(newOpenVisIdFormat)) {
         this.mOpenVisIdFormat = newOpenVisIdFormat;
         this.mModified = true;
      }

   }

   public void setOpenDescrFormat(String newOpenDescrFormat) {
      if(this.mOpenDescrFormat != null && newOpenDescrFormat == null || this.mOpenDescrFormat == null && newOpenDescrFormat != null || this.mOpenDescrFormat != null && newOpenDescrFormat != null && !this.mOpenDescrFormat.equals(newOpenDescrFormat)) {
         this.mOpenDescrFormat = newOpenDescrFormat;
         this.mModified = true;
      }

   }

   public void setAdjVisIdFormat(String newAdjVisIdFormat) {
      if(this.mAdjVisIdFormat != null && newAdjVisIdFormat == null || this.mAdjVisIdFormat == null && newAdjVisIdFormat != null || this.mAdjVisIdFormat != null && newAdjVisIdFormat != null && !this.mAdjVisIdFormat.equals(newAdjVisIdFormat)) {
         this.mAdjVisIdFormat = newAdjVisIdFormat;
         this.mModified = true;
      }

   }

   public void setAdjDescrFormat(String newAdjDescrFormat) {
      if(this.mAdjDescrFormat != null && newAdjDescrFormat == null || this.mAdjDescrFormat == null && newAdjDescrFormat != null || this.mAdjDescrFormat != null && newAdjDescrFormat != null && !this.mAdjDescrFormat.equals(newAdjDescrFormat)) {
         this.mAdjDescrFormat = newAdjDescrFormat;
         this.mModified = true;
      }

   }

   public void setP13VisIdFormat(String newP13VisIdFormat) {
      if(this.mP13VisIdFormat != null && newP13VisIdFormat == null || this.mP13VisIdFormat == null && newP13VisIdFormat != null || this.mP13VisIdFormat != null && newP13VisIdFormat != null && !this.mP13VisIdFormat.equals(newP13VisIdFormat)) {
         this.mP13VisIdFormat = newP13VisIdFormat;
         this.mModified = true;
      }

   }

   public void setP13DescrFormat(String newP13DescrFormat) {
      if(this.mP13DescrFormat != null && newP13DescrFormat == null || this.mP13DescrFormat == null && newP13DescrFormat != null || this.mP13DescrFormat != null && newP13DescrFormat != null && !this.mP13DescrFormat.equals(newP13DescrFormat)) {
         this.mP13DescrFormat = newP13DescrFormat;
         this.mModified = true;
      }

   }

   public void setP14VisIdFormat(String newP14VisIdFormat) {
      if(this.mP14VisIdFormat != null && newP14VisIdFormat == null || this.mP14VisIdFormat == null && newP14VisIdFormat != null || this.mP14VisIdFormat != null && newP14VisIdFormat != null && !this.mP14VisIdFormat.equals(newP14VisIdFormat)) {
         this.mP14VisIdFormat = newP14VisIdFormat;
         this.mModified = true;
      }

   }

   public void setP14DescrFormat(String newP14DescrFormat) {
      if(this.mP14DescrFormat != null && newP14DescrFormat == null || this.mP14DescrFormat == null && newP14DescrFormat != null || this.mP14DescrFormat != null && newP14DescrFormat != null && !this.mP14DescrFormat.equals(newP14DescrFormat)) {
         this.mP14DescrFormat = newP14DescrFormat;
         this.mModified = true;
      }

   }

   public void setDetails(CalendarSpecEVO newDetails) {
      this.setCalendarSpecId(newDetails.getCalendarSpecId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setYearStartMonth(newDetails.getYearStartMonth());
      this.setYearVisIdFormat(newDetails.getYearVisIdFormat());
      this.setYearDescrFormat(newDetails.getYearDescrFormat());
      this.setHalfYearVisIdFormat(newDetails.getHalfYearVisIdFormat());
      this.setHalfYearDescrFormat(newDetails.getHalfYearDescrFormat());
      this.setQuarterVisIdFormat(newDetails.getQuarterVisIdFormat());
      this.setQuarterDescrFormat(newDetails.getQuarterDescrFormat());
      this.setMonthVisIdFormat(newDetails.getMonthVisIdFormat());
      this.setMonthDescrFormat(newDetails.getMonthDescrFormat());
      this.setWeekVisIdFormat(newDetails.getWeekVisIdFormat());
      this.setWeekDescrFormat(newDetails.getWeekDescrFormat());
      this.setDayVisIdFormat(newDetails.getDayVisIdFormat());
      this.setDayDescrFormat(newDetails.getDayDescrFormat());
      this.setOpenVisIdFormat(newDetails.getOpenVisIdFormat());
      this.setOpenDescrFormat(newDetails.getOpenDescrFormat());
      this.setAdjVisIdFormat(newDetails.getAdjVisIdFormat());
      this.setAdjDescrFormat(newDetails.getAdjDescrFormat());
      this.setP13VisIdFormat(newDetails.getP13VisIdFormat());
      this.setP13DescrFormat(newDetails.getP13DescrFormat());
      this.setP14VisIdFormat(newDetails.getP14VisIdFormat());
      this.setP14DescrFormat(newDetails.getP14DescrFormat());
   }

   public CalendarSpecEVO deepClone() {
      CalendarSpecEVO cloned = new CalendarSpecEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mCalendarSpecId = this.mCalendarSpecId;
      cloned.mDimensionId = this.mDimensionId;
      cloned.mYearStartMonth = this.mYearStartMonth;
      if(this.mYearVisIdFormat != null) {
         cloned.mYearVisIdFormat = this.mYearVisIdFormat;
      }

      if(this.mYearDescrFormat != null) {
         cloned.mYearDescrFormat = this.mYearDescrFormat;
      }

      if(this.mHalfYearVisIdFormat != null) {
         cloned.mHalfYearVisIdFormat = this.mHalfYearVisIdFormat;
      }

      if(this.mHalfYearDescrFormat != null) {
         cloned.mHalfYearDescrFormat = this.mHalfYearDescrFormat;
      }

      if(this.mQuarterVisIdFormat != null) {
         cloned.mQuarterVisIdFormat = this.mQuarterVisIdFormat;
      }

      if(this.mQuarterDescrFormat != null) {
         cloned.mQuarterDescrFormat = this.mQuarterDescrFormat;
      }

      if(this.mMonthVisIdFormat != null) {
         cloned.mMonthVisIdFormat = this.mMonthVisIdFormat;
      }

      if(this.mMonthDescrFormat != null) {
         cloned.mMonthDescrFormat = this.mMonthDescrFormat;
      }

      if(this.mWeekVisIdFormat != null) {
         cloned.mWeekVisIdFormat = this.mWeekVisIdFormat;
      }

      if(this.mWeekDescrFormat != null) {
         cloned.mWeekDescrFormat = this.mWeekDescrFormat;
      }

      if(this.mDayVisIdFormat != null) {
         cloned.mDayVisIdFormat = this.mDayVisIdFormat;
      }

      if(this.mDayDescrFormat != null) {
         cloned.mDayDescrFormat = this.mDayDescrFormat;
      }

      if(this.mOpenVisIdFormat != null) {
         cloned.mOpenVisIdFormat = this.mOpenVisIdFormat;
      }

      if(this.mOpenDescrFormat != null) {
         cloned.mOpenDescrFormat = this.mOpenDescrFormat;
      }

      if(this.mAdjVisIdFormat != null) {
         cloned.mAdjVisIdFormat = this.mAdjVisIdFormat;
      }

      if(this.mAdjDescrFormat != null) {
         cloned.mAdjDescrFormat = this.mAdjDescrFormat;
      }

      if(this.mP13VisIdFormat != null) {
         cloned.mP13VisIdFormat = this.mP13VisIdFormat;
      }

      if(this.mP13DescrFormat != null) {
         cloned.mP13DescrFormat = this.mP13DescrFormat;
      }

      if(this.mP14VisIdFormat != null) {
         cloned.mP14VisIdFormat = this.mP14VisIdFormat;
      }

      if(this.mP14DescrFormat != null) {
         cloned.mP14DescrFormat = this.mP14DescrFormat;
      }

      return cloned;
   }

   public void prepareForInsert(DimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCalendarSpecId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCalendarSpecId(-this.mCalendarSpecId);
         } else {
            parent.changeKey(this, -this.mCalendarSpecId);
         }
      } else if(this.mCalendarSpecId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCalendarSpecId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(DimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCalendarSpecId < 1) {
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

   public CalendarSpecRef getEntityRef(DimensionEVO evoDimension, String entityText) {
      return new CalendarSpecRefImpl(new CalendarSpecCK(evoDimension.getPK(), this.getPK()), entityText);
   }

   public CalendarSpecCK getCK(DimensionEVO evoDimension) {
      return new CalendarSpecCK(evoDimension.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CalendarSpecId=");
      sb.append(String.valueOf(this.mCalendarSpecId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("YearStartMonth=");
      sb.append(String.valueOf(this.mYearStartMonth));
      sb.append(' ');
      sb.append("YearVisIdFormat=");
      sb.append(String.valueOf(this.mYearVisIdFormat));
      sb.append(' ');
      sb.append("YearDescrFormat=");
      sb.append(String.valueOf(this.mYearDescrFormat));
      sb.append(' ');
      sb.append("HalfYearVisIdFormat=");
      sb.append(String.valueOf(this.mHalfYearVisIdFormat));
      sb.append(' ');
      sb.append("HalfYearDescrFormat=");
      sb.append(String.valueOf(this.mHalfYearDescrFormat));
      sb.append(' ');
      sb.append("QuarterVisIdFormat=");
      sb.append(String.valueOf(this.mQuarterVisIdFormat));
      sb.append(' ');
      sb.append("QuarterDescrFormat=");
      sb.append(String.valueOf(this.mQuarterDescrFormat));
      sb.append(' ');
      sb.append("MonthVisIdFormat=");
      sb.append(String.valueOf(this.mMonthVisIdFormat));
      sb.append(' ');
      sb.append("MonthDescrFormat=");
      sb.append(String.valueOf(this.mMonthDescrFormat));
      sb.append(' ');
      sb.append("WeekVisIdFormat=");
      sb.append(String.valueOf(this.mWeekVisIdFormat));
      sb.append(' ');
      sb.append("WeekDescrFormat=");
      sb.append(String.valueOf(this.mWeekDescrFormat));
      sb.append(' ');
      sb.append("DayVisIdFormat=");
      sb.append(String.valueOf(this.mDayVisIdFormat));
      sb.append(' ');
      sb.append("DayDescrFormat=");
      sb.append(String.valueOf(this.mDayDescrFormat));
      sb.append(' ');
      sb.append("OpenVisIdFormat=");
      sb.append(String.valueOf(this.mOpenVisIdFormat));
      sb.append(' ');
      sb.append("OpenDescrFormat=");
      sb.append(String.valueOf(this.mOpenDescrFormat));
      sb.append(' ');
      sb.append("AdjVisIdFormat=");
      sb.append(String.valueOf(this.mAdjVisIdFormat));
      sb.append(' ');
      sb.append("AdjDescrFormat=");
      sb.append(String.valueOf(this.mAdjDescrFormat));
      sb.append(' ');
      sb.append("P13VisIdFormat=");
      sb.append(String.valueOf(this.mP13VisIdFormat));
      sb.append(' ');
      sb.append("P13DescrFormat=");
      sb.append(String.valueOf(this.mP13DescrFormat));
      sb.append(' ');
      sb.append("P14VisIdFormat=");
      sb.append(String.valueOf(this.mP14VisIdFormat));
      sb.append(' ');
      sb.append("P14DescrFormat=");
      sb.append(String.valueOf(this.mP14DescrFormat));
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

      sb.append("CalendarSpec: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
