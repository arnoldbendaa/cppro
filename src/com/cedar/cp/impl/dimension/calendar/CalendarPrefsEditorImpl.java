// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension.calendar;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarPrefsEditor;
import com.cedar.cp.api.dimension.calendar.CalendarSpec;
import com.cedar.cp.dto.dimension.calendar.CalendarNodeDateFormatter;
import com.cedar.cp.dto.dimension.calendar.CalendarSpecImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.dimension.calendar.CalendarEditorImpl;
import java.util.Date;

public class CalendarPrefsEditorImpl extends SubBusinessEditorImpl implements CalendarPrefsEditor {

   private CalendarNodeDateFormatter mFormatter = new CalendarNodeDateFormatter();
   private CalendarSpecImpl mCalendarSpec;


   public CalendarPrefsEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, CalendarSpecImpl specImpl) {
      super(sess, owner);

      try {
         this.mCalendarSpec = (CalendarSpecImpl)specImpl.clone();
      } catch (CloneNotSupportedException var5) {
         throw new IllegalStateException("Failed to clone CalendarSpecImpl:", var5);
      }
   }

   public void setYearStartMonth(int yearStartMonth) throws ValidationException {
      if(this.mCalendarSpec.getYearStartMonth() != yearStartMonth) {
         this.mCalendarSpec.setYearStartMonth(yearStartMonth);
         this.setContentModified();
      }

   }

   public void setYearVisIdFormat(String yearVisIdFormat) throws ValidationException {
      this.validateFieldPresent(yearVisIdFormat);
      if(!yearVisIdFormat.equals(this.mCalendarSpec.getYearVisIdFormat())) {
         this.mFormatter.formatDate(new Date(), 0, 0, yearVisIdFormat);
         this.mCalendarSpec.setYearVisIdFormat(yearVisIdFormat);
         this.setContentModified();
      }

   }

   public void setHalfYearVisIdFormat(String halfYearVisIdFormat) throws ValidationException {
      this.validateFieldPresent(halfYearVisIdFormat);
      if(!halfYearVisIdFormat.equals(this.mCalendarSpec.getHalfYearVisIdFormat())) {
         this.mFormatter.formatDate(new Date(), 1, 0, halfYearVisIdFormat);
         this.mCalendarSpec.setHalfYearVisIdFormat(halfYearVisIdFormat);
         this.setContentModified();
      }

   }

   public void setMonthVisIdFormat(String monthVisIdFormat) throws ValidationException {
      this.validateFieldPresent(monthVisIdFormat);
      if(!monthVisIdFormat.equals(this.mCalendarSpec.getMonthDescrFormat())) {
         this.mFormatter.formatDate(new Date(), 3, 0, monthVisIdFormat);
         this.mCalendarSpec.setMonthVisIdFormat(monthVisIdFormat);
         this.setContentModified();
      }

   }

   public void setQuarterVisIdFormat(String quarterVisIdFormat) throws ValidationException {
      this.validateFieldPresent(quarterVisIdFormat);
      if(!quarterVisIdFormat.equals(this.mCalendarSpec.getQuarterVisIdFormat())) {
         this.mFormatter.formatDate(new Date(), 2, 0, quarterVisIdFormat);
         this.mCalendarSpec.setQuarterVisIdFormat(quarterVisIdFormat);
         this.setContentModified();
      }

   }

   public void setWeekVisIdFormat(String weekVisIdFormat) throws ValidationException {
      this.validateFieldPresent(weekVisIdFormat);
      if(!weekVisIdFormat.equals(this.mCalendarSpec.getWeekVisIdFormat())) {
         this.mFormatter.formatDate(new Date(), 4, 0, weekVisIdFormat);
         this.mCalendarSpec.setWeekVisIdFormat(weekVisIdFormat);
         this.setContentModified();
      }

   }

   public void setDayVisIdFormat(String dayVisIdFormat) throws ValidationException {
      this.validateFieldPresent(dayVisIdFormat);
      if(!dayVisIdFormat.equals(this.mCalendarSpec.getDayVisIdFormat())) {
         this.mFormatter.formatDate(new Date(), 5, 0, dayVisIdFormat);
         this.mCalendarSpec.setDayVisIdFormat(dayVisIdFormat);
         this.setContentModified();
      }

   }

   public void setYearDescrFormat(String yearDescrFormat) throws ValidationException {
      this.validateFieldPresent(yearDescrFormat);
      if(!yearDescrFormat.equals(this.mCalendarSpec.getYearDescrFormat())) {
         this.mFormatter.formatDate(new Date(), 0, 0, yearDescrFormat);
         this.mCalendarSpec.setYearDescrFormat(yearDescrFormat);
         this.setContentModified();
      }

   }

   public void setHalfYearDescrFormat(String halfYearDescrFormat) throws ValidationException {
      this.validateFieldPresent(halfYearDescrFormat);
      if(!halfYearDescrFormat.equals(this.mCalendarSpec.getHalfYearDescrFormat())) {
         this.mFormatter.formatDate(new Date(), 1, 0, halfYearDescrFormat);
         this.mCalendarSpec.setHalfYearDescrFormat(halfYearDescrFormat);
         this.setContentModified();
      }

   }

   public void setMonthDescrFormat(String monthDescrFormat) throws ValidationException {
      this.validateFieldPresent(monthDescrFormat);
      if(!monthDescrFormat.equals(this.mCalendarSpec.getMonthDescrFormat())) {
         this.mFormatter.formatDate(new Date(), 3, 0, monthDescrFormat);
         this.mCalendarSpec.setMonthDescrFormat(monthDescrFormat);
         this.setContentModified();
      }

   }

   public void setQuarterDescrFormat(String quarterDescrFormat) throws ValidationException {
      this.validateFieldPresent(quarterDescrFormat);
      if(!quarterDescrFormat.equals(this.mCalendarSpec.getQuarterDescrFormat())) {
         this.mFormatter.formatDate(new Date(), 2, 0, quarterDescrFormat);
         this.mCalendarSpec.setQuarterDescrFormat(quarterDescrFormat);
         this.setContentModified();
      }

   }

   public void setWeekDescrFormat(String weekDescrFormat) throws ValidationException {
      this.validateFieldPresent(weekDescrFormat);
      if(!weekDescrFormat.equals(this.mCalendarSpec.getWeekDescrFormat())) {
         this.mFormatter.formatDate(new Date(), 4, 0, weekDescrFormat);
         this.mCalendarSpec.setWeekDescrFormat(weekDescrFormat);
         this.setContentModified();
      }

   }

   public void setDayDescrFormat(String dayDescrFormat) throws ValidationException {
      this.validateFieldPresent(dayDescrFormat);
      if(!dayDescrFormat.equals(this.mCalendarSpec.getDayDescrFormat())) {
         this.mFormatter.formatDate(new Date(), 5, 0, dayDescrFormat);
         this.mCalendarSpec.setDayDescrFormat(dayDescrFormat);
         this.setContentModified();
      }

   }

   public void setOpenVisId(String openVisId) throws ValidationException {
      this.validateFieldPresent(openVisId);
      this.mCalendarSpec.setOpenVisId(openVisId);
   }

   public void setOpenDescr(String openDescr) throws ValidationException {
      this.validateFieldPresent(openDescr);
      this.mCalendarSpec.setOpenDescr(openDescr);
   }

   public void setAdjVisId(String adjVisId) throws ValidationException {
      this.validateFieldPresent(adjVisId);
      this.mCalendarSpec.setAdjVisId(adjVisId);
   }

   public void setAdjDescr(String adjDescr) throws ValidationException {
      this.validateFieldPresent(adjDescr);
      this.mCalendarSpec.setAdjDescr(adjDescr);
   }

   public void setPeriod13VisIdFormat(String visIdFormat) throws ValidationException {
      this.validateFieldPresent(visIdFormat);
      this.mCalendarSpec.setPeriod13VisId(visIdFormat);
   }

   public void setPeriod13DescrFormat(String adjDescr) throws ValidationException {
      this.validateFieldPresent(adjDescr);
      this.mCalendarSpec.setPeriod13Descr(adjDescr);
   }

   public void setPeriod14VisIdFormat(String visIdFormat) throws ValidationException {
      this.validateFieldPresent(visIdFormat);
      this.mCalendarSpec.setPeriod14VisId(visIdFormat);
   }

   public void setPeriod14DescrFormat(String adjDescr) throws ValidationException {
      this.validateFieldPresent(adjDescr);
      this.mCalendarSpec.setPeriod14Descr(adjDescr);
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      if(this.isContentModified()) {
         this.getCalendarEditor().setCalendarSpec(this.mCalendarSpec);
      }

   }

   private CalendarEditorImpl getCalendarEditor() {
      return (CalendarEditorImpl)this.getOwner();
   }

   public CalendarSpec getCalendarSpec() {
      return this.mCalendarSpec;
   }
}
