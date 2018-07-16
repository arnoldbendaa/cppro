// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.BasicCalendarEditor;
import com.cedar.cp.api.dimension.calendar.CalendarPrefsEditor;
import com.cedar.cp.api.model.mapping.MappedCalendar;
import com.cedar.cp.api.model.mapping.MappedCalendarYearEditor;
import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;

public interface MappedCalendarEditor extends BasicCalendarEditor {

   void setHierarchyVisId(String var1) throws ValidationException;

   void setHierarchyDescription(String var1);

   void addYear(boolean var1) throws ValidationException;

   void removeYear(boolean var1) throws ValidationException;

   MappedCalendar getMappedCalendar();

   CalendarPrefsEditor getPrefsEditor();

   MappedCalendarYearEditor getMappedCalendarYearEditor(int var1) throws ValidationException;

   FinanceCalendarYear findFinanceCalendarYear(int var1);

   FinanceCompany getFinanceCompany();
}
