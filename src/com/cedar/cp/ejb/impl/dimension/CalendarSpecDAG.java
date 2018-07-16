// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.calendar.CalendarSpec;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.CalendarSpecEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import java.io.IOException;
import java.io.Writer;

public class CalendarSpecDAG extends AbstractDAG implements CalendarSpec {

   private DimensionDAG mDimension;
   private int mCalendarSpecId;
   private int mYearStartMonth;
   private String mYearVisIdFormat;
   private String mHalfYearVisIdFormat;
   private String mQuarterVisIdFormat;
   private String mMonthVisIdFormat;
   private String mWeekVisIdFormat;
   private String mDayVisIdFormat;
   private String mYearDescrFormat;
   private String mHalfYearDescrFormat;
   private String mQuarterDescrFormat;
   private String mMonthDescrFormat;
   private String mWeekDescrFormat;
   private String mDayDescrFormat;
   private String mOpenVisId;
   private String mOpenDescr;
   private String mAdjVisId;
   private String mAdjDescr;
   private String mPeriod13VisId;
   private String mPeriod13Descr;
   private String mPeriod14VisId;
   private String mPeriod14Descr;


   public CalendarSpecDAG(DAGContext context, DimensionDAG dimension, int yearStartMonth, String yearVisIdFormat, String halfYearVisIdFormat, String quarterVisIdFormat, String monthVisIdFormat, String weekVisIdFormat, String dayVisIdFormat, String yearDescrFormat, String halfYearDescrFormat, String quarterDescrFormat, String monthDescrFormat, String weekDescrFormat, String dayDescrFormat, String openVisId, String openDescr, String adjVisId, String adjDescr, String period13VisId, String period13Descr, String period14VisId, String period14Descr) {
      super(context, true);
      this.mDimension = dimension;
      this.mYearStartMonth = yearStartMonth;
      this.mYearVisIdFormat = yearVisIdFormat;
      this.mHalfYearVisIdFormat = halfYearVisIdFormat;
      this.mQuarterVisIdFormat = quarterVisIdFormat;
      this.mMonthVisIdFormat = monthVisIdFormat;
      this.mWeekVisIdFormat = weekVisIdFormat;
      this.mDayVisIdFormat = dayVisIdFormat;
      this.mYearDescrFormat = yearDescrFormat;
      this.mHalfYearDescrFormat = halfYearDescrFormat;
      this.mQuarterDescrFormat = quarterDescrFormat;
      this.mMonthDescrFormat = monthDescrFormat;
      this.mWeekDescrFormat = weekDescrFormat;
      this.mDayDescrFormat = dayDescrFormat;
      this.mOpenVisId = openVisId;
      this.mOpenDescr = openDescr;
      this.mAdjVisId = adjVisId;
      this.mAdjDescr = adjDescr;
      this.mPeriod13VisId = period13VisId;
      this.mPeriod13Descr = period13Descr;
      this.mPeriod14VisId = period14VisId;
      this.mPeriod14Descr = period14Descr;
   }

   public CalendarSpecDAG(DAGContext context, DimensionDAG dimension) {
      super(context, true);
      this.mDimension = dimension;
      this.mYearVisIdFormat = "FY${yy}";
      this.mYearDescrFormat = "Financial Year ${yy}-${yy+1}";
      this.mHalfYearVisIdFormat = "HY${idx}";
      this.mHalfYearDescrFormat = "${yyyy} ${MMMM} - ${MMMM+5}";
      this.mQuarterVisIdFormat = "Q{idx}";
      this.mQuarterDescrFormat = "MMMM - {MMMM+2}";
      this.mMonthVisIdFormat = "yyyy.{idx}";
      this.mMonthDescrFormat = "yyyy";
      this.mWeekVisIdFormat = "{idx}";
      this.mWeekDescrFormat = "yyyy";
      this.mDayVisIdFormat = "dd/MM/yy";
      this.mDayDescrFormat = "EEEE dd MMMM yyyy";
      this.mOpenVisId = "Open";
      this.mOpenDescr = "Opening Balance";
      this.mAdjVisId = "Adj";
      this.mAdjDescr = "Adjustments";
      this.mPeriod13VisId = "13.${yy}";
      this.mPeriod13Descr = "Period 13 ${yy}";
      this.mPeriod14VisId = "14.${yy}";
      this.mPeriod14Descr = "Period 14 ${yy}";
   }

   public int getYearStartMonth() {
      return this.mYearStartMonth;
   }

   public void setYearStartMonth(int yearStartMonth) {
      this.mYearStartMonth = yearStartMonth;
   }

   public String getYearVisIdFormat() {
      return this.mYearVisIdFormat;
   }

   public void setYearVisIdFormat(String yearVisIdFormat) {
      this.mYearVisIdFormat = yearVisIdFormat;
   }

   public String getHalfYearVisIdFormat() {
      return this.mHalfYearVisIdFormat;
   }

   public void setHalfYearVisId(String halfHearVisIdFormat) {
      this.mHalfYearVisIdFormat = halfHearVisIdFormat;
   }

   public String getQuarterVisIdFormat() {
      return this.mQuarterVisIdFormat;
   }

   public void setQuarterVisIdFormat(String quarterVisIdFormat) {
      this.mQuarterVisIdFormat = quarterVisIdFormat;
   }

   public String getMonthVisIdFormat() {
      return this.mMonthVisIdFormat;
   }

   public void setMonthVisIdFormat(String monthVisIdFormat) {
      this.mMonthVisIdFormat = monthVisIdFormat;
   }

   public String getWeekVisIdFormat() {
      return this.mWeekVisIdFormat;
   }

   public void setWeekVisIdFormat(String weekVisIdFormat) {
      this.mWeekVisIdFormat = weekVisIdFormat;
   }

   public String getDayVisIdFormat() {
      return this.mDayVisIdFormat;
   }

   public void setDayVisIdFormat(String dayVisIdFormat) {
      this.mDayVisIdFormat = dayVisIdFormat;
   }

   public String getYearDescrFormat() {
      return this.mYearDescrFormat;
   }

   public void setYearDescrFormat(String yearDescrFormat) {
      this.mYearDescrFormat = yearDescrFormat;
   }

   public String getHalfYearDescrFormat() {
      return this.mHalfYearDescrFormat;
   }

   public void setHalfYearDescrFormat(String halfYearDescrFormat) {
      this.mHalfYearDescrFormat = halfYearDescrFormat;
   }

   public String getQuarterDescrFormat() {
      return this.mQuarterDescrFormat;
   }

   public void setQuarterDescrFormat(String quarterDescrFormat) {
      this.mQuarterDescrFormat = quarterDescrFormat;
   }

   public String getMonthDescrFormat() {
      return this.mMonthDescrFormat;
   }

   public void setMonthDescrFormat(String monthDescrFormat) {
      this.mMonthDescrFormat = monthDescrFormat;
   }

   public String getWeekDescrFormat() {
      return this.mWeekDescrFormat;
   }

   public void setWeekDescrFormat(String weekDescrFormat) {
      this.mWeekDescrFormat = weekDescrFormat;
   }

   public String getDayDescrFormat() {
      return this.mDayDescrFormat;
   }

   public void setDayDescrFormat(String dayDescrFormat) {
      this.mDayDescrFormat = dayDescrFormat;
   }

   public String getOpenVisId() {
      return this.mOpenVisId;
   }

   public void setOpenVisId(String openVisId) {
      this.mOpenVisId = openVisId;
   }

   public String getOpenDescr() {
      return this.mOpenDescr;
   }

   public void setOpenDescr(String openDescr) {
      this.mOpenDescr = openDescr;
   }

   public String getAdjVisId() {
      return this.mAdjVisId;
   }

   public void setAdjVisId(String adjVisId) {
      this.mAdjVisId = adjVisId;
   }

   public String getAdjDescr() {
      return this.mAdjDescr;
   }

   public void setAdjDescr(String adjDescr) {
      this.mAdjDescr = adjDescr;
   }

   public String getPeriod13VisId() {
      return this.mPeriod13VisId;
   }

   public void setPeriod13VisId(String period13VisId) {
      this.mPeriod13VisId = period13VisId;
   }

   public String getPeriod13Descr() {
      return this.mPeriod13Descr;
   }

   public void setPeriod13Descr(String period13Descr) {
      this.mPeriod13Descr = period13Descr;
   }

   public String getPeriod14VisId() {
      return this.mPeriod14VisId;
   }

   public void setPeriod14VisId(String period14VisId) {
      this.mPeriod14VisId = period14VisId;
   }

   public String getPeriod14Descr() {
      return this.mPeriod14Descr;
   }

   public void setPeriod14Descr(String period14Descr) {
      this.mPeriod14Descr = period14Descr;
   }

   public CalendarSpecEVO createEVO() {
      CalendarSpecEVO csEVO = new CalendarSpecEVO();
      csEVO.setCalendarSpecId(-1);
      this.updateEVO(csEVO);
      return csEVO;
   }

   public void updateEVO(CalendarSpecEVO csEVO) {
      csEVO.setDimensionId(this.getDimension().getDimensionId());
      csEVO.setYearStartMonth(this.mYearStartMonth);
      csEVO.setYearVisIdFormat(this.mYearVisIdFormat);
      csEVO.setHalfYearVisIdFormat(this.mHalfYearVisIdFormat);
      csEVO.setQuarterVisIdFormat(this.mQuarterVisIdFormat);
      csEVO.setMonthVisIdFormat(this.mMonthVisIdFormat);
      csEVO.setWeekVisIdFormat(this.mWeekVisIdFormat);
      csEVO.setDayVisIdFormat(this.mDayVisIdFormat);
      csEVO.setYearDescrFormat(this.mYearDescrFormat);
      csEVO.setHalfYearDescrFormat(this.mHalfYearDescrFormat);
      csEVO.setQuarterDescrFormat(this.mQuarterDescrFormat);
      csEVO.setMonthDescrFormat(this.mMonthDescrFormat);
      csEVO.setWeekDescrFormat(this.mWeekDescrFormat);
      csEVO.setDayDescrFormat(this.mDayDescrFormat);
      csEVO.setOpenVisIdFormat(this.mOpenVisId);
      csEVO.setOpenDescrFormat(this.mOpenDescr);
      csEVO.setAdjVisIdFormat(this.mAdjVisId);
      csEVO.setAdjDescrFormat(this.mAdjDescr);
      csEVO.setP13VisIdFormat(this.mPeriod13VisId);
      csEVO.setP13DescrFormat(this.mPeriod13Descr);
      csEVO.setP14VisIdFormat(this.mPeriod14VisId);
      csEVO.setP14DescrFormat(this.mPeriod14Descr);
   }

   public DimensionDAG getDimension() {
      return this.mDimension;
   }

   public void setDimension(DimensionDAG dimension) {
      this.mDimension = dimension;
   }

   public int getCalendarSpecId() {
      return this.mCalendarSpecId;
   }

   public void setCalendarSpecId(int calendarSpecId) {
      this.mCalendarSpecId = calendarSpecId;
   }

   public void writeXml(Writer out) throws IOException {}
}
