// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension.calendar;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarSpec;

public interface CalendarPrefsEditor extends SubBusinessEditor {

   void setYearStartMonth(int var1) throws ValidationException;

   void setYearVisIdFormat(String var1) throws ValidationException;

   void setHalfYearVisIdFormat(String var1) throws ValidationException;

   void setMonthVisIdFormat(String var1) throws ValidationException;

   void setQuarterVisIdFormat(String var1) throws ValidationException;

   void setWeekVisIdFormat(String var1) throws ValidationException;

   void setDayVisIdFormat(String var1) throws ValidationException;

   void setYearDescrFormat(String var1) throws ValidationException;

   void setHalfYearDescrFormat(String var1) throws ValidationException;

   void setQuarterDescrFormat(String var1) throws ValidationException;

   void setMonthDescrFormat(String var1) throws ValidationException;

   void setWeekDescrFormat(String var1) throws ValidationException;

   void setDayDescrFormat(String var1) throws ValidationException;

   void setOpenVisId(String var1) throws ValidationException;

   void setOpenDescr(String var1) throws ValidationException;

   void setAdjVisId(String var1) throws ValidationException;

   void setAdjDescr(String var1) throws ValidationException;

   void setPeriod13VisIdFormat(String var1) throws ValidationException;

   void setPeriod13DescrFormat(String var1) throws ValidationException;

   void setPeriod14VisIdFormat(String var1) throws ValidationException;

   void setPeriod14DescrFormat(String var1) throws ValidationException;

   CalendarSpec getCalendarSpec();
}
