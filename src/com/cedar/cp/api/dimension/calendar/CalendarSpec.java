// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension.calendar;

import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.Serializable;

public interface CalendarSpec extends Serializable, XMLWritable {

   int getYearStartMonth();

   String getYearVisIdFormat();

   String getHalfYearVisIdFormat();

   String getQuarterVisIdFormat();

   String getMonthVisIdFormat();

   String getWeekVisIdFormat();

   String getDayVisIdFormat();

   String getOpenVisId();

   String getAdjVisId();

   String getPeriod13VisId();

   String getPeriod14VisId();

   String getYearDescrFormat();

   String getHalfYearDescrFormat();

   String getQuarterDescrFormat();

   String getMonthDescrFormat();

   String getWeekDescrFormat();

   String getDayDescrFormat();

   String getOpenDescr();

   String getAdjDescr();

   String getPeriod13Descr();

   String getPeriod14Descr();
}
