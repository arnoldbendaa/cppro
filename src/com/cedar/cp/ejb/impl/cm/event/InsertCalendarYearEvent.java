// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.ejb.impl.cm.event.CalendarEvent;

public class InsertCalendarYearEvent extends CalendarEvent {

   private CalendarYearSpecImpl mCalendarYearSpec;


   public InsertCalendarYearEvent(CalendarYearSpecImpl calendarYearSpec) {
      this.mCalendarYearSpec = calendarYearSpec;
   }

   public CalendarYearSpecImpl getCalendarYearSpec() {
      return this.mCalendarYearSpec;
   }
}
