// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrameworkDateFormatter extends SimpleDateFormat {

   public static final String USERS_FORMAT = "dd-MMM-yyyy";


   public FrameworkDateFormatter() {}

   public FrameworkDateFormatter(String arg0) {
      super(arg0);
   }

   public Date toDate(String dateString) throws ParseException {
      return this.toDate(dateString, "dd-MMM-yyyy");
   }

   public String toChar(Date inDate) {
      return this.toChar(inDate, "dd-MMM-yyyy");
   }

   public Date toDate(String dateString, String format) throws ParseException {
      super.applyPattern(format);
      Date rVal = super.parse(dateString);
      return rVal;
   }

   public String toChar(Date inDate, String format) {
      String rVal = null;
      super.applyPattern(format);
      rVal = super.format(inDate);
      return rVal;
   }
}
