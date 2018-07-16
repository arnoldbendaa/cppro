// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.CalendarElementNode;
import java.io.Serializable;
import java.util.List;

public interface CalendarInfo extends Serializable {

   int getCalendarId();

   CalendarElementNode getElementOffsetByYear(int var1, int var2);

   CalendarElementNode getElementOffsetByYearAndPeriod(int var1, int var2, int var3);

   List<CalendarElementNode> getYtdElements(int var1);

   CalendarElementNode getRoot();

   CalendarElementNode findElement(String var1);

   CalendarElementNode findRelativeElement(CalendarElementNode var1, String var2);

   CalendarElementNode getById(int var1);

   CalendarElementNode getById(Object var1);
}
