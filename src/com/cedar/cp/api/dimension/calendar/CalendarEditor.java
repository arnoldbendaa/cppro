// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension.calendar;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.BasicCalendarEditor;
import com.cedar.cp.api.dimension.calendar.Calendar;
import com.cedar.cp.api.dimension.calendar.CalendarPrefsEditor;

public interface CalendarEditor extends BasicCalendarEditor {

   void setDimensionVisId(String var1) throws ValidationException;

   void setDimensionDescription(String var1);

   void setHierarchyVisId(String var1) throws ValidationException;

   void setHierarchyDescription(String var1);

   void addYear(boolean var1) throws ValidationException;

   void removeYear(boolean var1) throws ValidationException;

   Calendar getCalendar();

   void setSubmitChangeManagementRequest(boolean var1);

   void setExternalSystemRef(Integer var1) throws ValidationException;

   CalendarPrefsEditor getPrefsEditor();
}
