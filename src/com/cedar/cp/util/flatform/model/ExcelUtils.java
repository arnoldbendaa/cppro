// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import java.util.Calendar;
import java.util.Date;

public class ExcelUtils {

   private static final Calendar sCalendar = Calendar.getInstance();
   private static final int sMillisPerDay = 86400000;


   public static synchronized Date excel2JavaDate(double excelDate) {
      sCalendar.clear();
      sCalendar.set(1900, 0, 1);
      int daysPart = (int)excelDate;
      sCalendar.add(6, daysPart - 2);
      int millisToAdd = (int)((excelDate - (double)daysPart) * 8.64E7D);
      sCalendar.add(14, millisToAdd);
      return sCalendar.getTime();
   }

}
