// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension.calendar;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.Calendar;

public interface BasicCalendarEditor extends BusinessEditor {

   Calendar getCalendar();

   void setDetail(int var1, int var2, boolean var3) throws ValidationException;
}
