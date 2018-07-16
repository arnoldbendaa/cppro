// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension.calendar;

import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.Serializable;

public interface CalendarYearSpec extends Serializable, XMLWritable {

   int YEAR = 0;
   int HALF_YEAR = 1;
   int QUARTER = 2;
   int MONTH = 3;
   int WEEK = 4;
   int DAY = 5;
   int OPENING_BALANCE = 6;
   int ADJUSTMENT_LEVEL = 7;
   int PERIOD_13 = 8;
   int PERIOD_14 = 9;
   int ROOT = 100;
   int SIZE = 10;


   int getYear();

   boolean get(int var1);
}
