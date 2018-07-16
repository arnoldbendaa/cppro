// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.api.dimension.calendar.CalendarSpec;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class CalendarSpecImpl implements CalendarSpec, Serializable, Cloneable {

   private int mYearStartMonth;
   private String mYearVisIdFormat = "${yyyy}";
   private String mHalfYearVisIdFormat = "HY${idx}";
   private String mQuarterVisIdFormat = "Q${idx}";
   private String mMonthVisIdFormat = "${idx}";
   private String mWeekVisIdFormat = "${idx}";
   private String mDayVisIdFormat = "${dd}-${MM}-${yy}";
   private String mYearDescrFormat = "Financial Year ${yyyy}";
   private String mHalfYearDescrFormat = "${yyyy} ${MMMM} - ${MMMM+5}";
   private String mQuarterDescrFormat = "${MMMM} - ${MMMM+2}";
   private String mMonthDescrFormat = "${MMMM}";
   private String mWeekDescrFormat = "${yyyy}";
   private String mDayDescrFormat = "${EEEE} ${dd} ${MMMM} ${yyyy}";
   private String mOpenVisId = "Open";
   private String mOpenDescr = "Opening Balance";
   private String mAdjVisId = "Adj";
   private String mAdjDescr = "Adjustments";
   private String mPeriod13VisId = "13";
   private String mPeriod13Descr = "Period 13";
   private String mPeriod14VisId = "14";
   private String mPeriod14Descr = "Period 14";


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

   public void setHalfYearVisIdFormat(String halfHearVisIdFormat) {
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

   public Object clone() throws CloneNotSupportedException {
      CalendarSpecImpl clone = new CalendarSpecImpl();
      clone.mYearStartMonth = this.mYearStartMonth;
      clone.mYearVisIdFormat = this.mYearVisIdFormat;
      clone.mHalfYearVisIdFormat = this.mHalfYearVisIdFormat;
      clone.mQuarterVisIdFormat = this.mQuarterVisIdFormat;
      clone.mMonthVisIdFormat = this.mMonthVisIdFormat;
      clone.mWeekVisIdFormat = this.mWeekVisIdFormat;
      clone.mDayVisIdFormat = this.mDayVisIdFormat;
      clone.mYearDescrFormat = this.mYearDescrFormat;
      clone.mHalfYearDescrFormat = this.mHalfYearDescrFormat;
      clone.mQuarterDescrFormat = this.mQuarterDescrFormat;
      clone.mMonthDescrFormat = this.mMonthDescrFormat;
      clone.mWeekDescrFormat = this.mWeekDescrFormat;
      clone.mDayDescrFormat = this.mDayDescrFormat;
      clone.mOpenVisId = this.mOpenVisId;
      clone.mOpenDescr = this.mOpenDescr;
      clone.mAdjVisId = this.mAdjVisId;
      clone.mAdjDescr = this.mAdjDescr;
      clone.mPeriod13VisId = this.mPeriod13VisId;
      clone.mPeriod13Descr = this.mPeriod13Descr;
      clone.mPeriod14VisId = this.mPeriod14VisId;
      clone.mPeriod14Descr = this.mPeriod14Descr;
      return clone;
   }

   public void writeXml(Writer out) throws IOException {
      out.write(" <calendarPreferences ");
      XmlUtils.outputAttribute(out, "yearStartMonth", Integer.valueOf(this.mYearStartMonth));
      XmlUtils.outputAttribute(out, "yearVisIdFormat", this.mYearVisIdFormat);
      XmlUtils.outputAttribute(out, "halfYearVisIdFormat", this.mHalfYearVisIdFormat);
      XmlUtils.outputAttribute(out, "quarterVisIdFormat", this.mQuarterVisIdFormat);
      XmlUtils.outputAttribute(out, "monthVisIdFormat", this.mMonthVisIdFormat);
      XmlUtils.outputAttribute(out, "weekVisIdFormat", this.mWeekVisIdFormat);
      XmlUtils.outputAttribute(out, "dayVisIdFormat", this.mDayVisIdFormat);
      XmlUtils.outputAttribute(out, "yearDescrFormat", this.mYearDescrFormat);
      XmlUtils.outputAttribute(out, "halfYearDescrFormat", this.mHalfYearDescrFormat);
      XmlUtils.outputAttribute(out, "quarterDescrFormat", this.mQuarterDescrFormat);
      XmlUtils.outputAttribute(out, "monthDescrFormat", this.mMonthDescrFormat);
      XmlUtils.outputAttribute(out, "weekDescrFormat", this.mWeekDescrFormat);
      XmlUtils.outputAttribute(out, "dayDescrFormat", this.mDayDescrFormat);
      XmlUtils.outputAttribute(out, "openVisId", this.mOpenVisId);
      XmlUtils.outputAttribute(out, "openDescr", this.mOpenDescr);
      XmlUtils.outputAttribute(out, "adjVisId", this.mAdjVisId);
      XmlUtils.outputAttribute(out, "adjDescr", this.mAdjDescr);
      XmlUtils.outputAttribute(out, "period13VisId", this.mPeriod13VisId);
      XmlUtils.outputAttribute(out, "period13Descr", this.mPeriod13Descr);
      XmlUtils.outputAttribute(out, "period14VisId", this.mPeriod14VisId);
      XmlUtils.outputAttribute(out, "period14Descr", this.mPeriod14Descr);
      out.write(" />\n");
   }
}
