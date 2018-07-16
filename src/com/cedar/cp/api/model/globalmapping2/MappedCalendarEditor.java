package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.BasicCalendarEditor;
import com.cedar.cp.api.dimension.calendar.CalendarPrefsEditor;
import com.cedar.cp.api.model.globalmapping2.MappedCalendar;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYearEditor;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;

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
   
   void setCompanies(String companies);
}
