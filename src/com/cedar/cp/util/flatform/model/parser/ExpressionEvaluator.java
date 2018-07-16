// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

   private static SimpleDateFormat mDateFormat = new SimpleDateFormat();
   public static final String ASTERISK = "*";
   public static final String NOT_EQUAL = "<>";
   public static final String GREATER_EQUAL = ">=";
   public static final String LESS_EQUAL = "<=";
   public static final String EQUAL = "=";
   public static final String GREATER = ">";
   public static final String LESS = "<";


   public ExpressionEvaluator() {
      mDateFormat.setLenient(false);
   }

   public boolean evaluate(String criteria, String value) {
      boolean result = false;
      String exp = "";
      String compareValue = "";
      if(criteria.indexOf("<>") > -1) {
         exp = "<>";
         compareValue = criteria.substring(2);
         if(compareValue.trim().equals("*")) {
            return true;
         }
      } else if(criteria.indexOf(">=") > -1) {
         exp = ">=";
         compareValue = criteria.substring(2);
      } else if(criteria.indexOf("<=") > -1) {
         exp = "<=";
         compareValue = criteria.substring(2);
      } else if(criteria.indexOf("=") > -1) {
         exp = "=";
         compareValue = criteria.substring(1);
      } else if(criteria.indexOf(">") > -1) {
         exp = ">";
         compareValue = criteria.substring(1);
      } else if(criteria.indexOf("<") > -1) {
         exp = "<";
         compareValue = criteria.substring(1);
      }

      if(this.isNumber(compareValue)) {
         result = this.evaluateNumber(exp, compareValue, value);
      } else if(this.isDateTime(compareValue)) {
         result = this.evaluateDateTime(exp, compareValue, value);
      } else {
         result = this.evaluateString(exp, compareValue, value);
      }

      return result;
   }

   private boolean evaluateDateTime(String exp, String compareValue, String value) {
      boolean result = false;
      if(this.isNumber(value)) {
         compareValue = String.valueOf(this.convertDate2Number(compareValue));
         result = this.evaluateNumber(exp, compareValue, value);
      } else if(this.isDateTime(value)) {
         Calendar c1 = Calendar.getInstance();
         Date d1 = this.convert2Date(value);
         if(d1 == null) {
            throw new IllegalStateException("Wrong date time format.");
         }

         c1.setTime(d1);
         Calendar c2 = Calendar.getInstance();
         Date d2 = this.convert2Date(compareValue);
         if(d2 == null) {
            throw new IllegalStateException("Wrong date time format.");
         }

         c2.setTime(d2);
         if(exp.equals("<>")) {
            if(c1.compareTo(c2) != 0) {
               result = true;
            }
         } else if(exp.equals(">=")) {
            if(c1.compareTo(c2) >= 0) {
               result = true;
            }
         } else if(exp.equals("<=")) {
            if(c1.compareTo(c2) <= 0) {
               result = true;
            }
         } else if(exp.equals("=")) {
            if(c1.compareTo(c2) == 0) {
               result = true;
            }
         } else if(exp.equals(">")) {
            if(c1.compareTo(c2) > 0) {
               result = true;
            }
         } else if(exp.equals("<") && c1.compareTo(c2) < 0) {
            result = true;
         }
      }

      return result;
   }

   private boolean evaluateNumber(String exp, String compareValue, String value) {
      boolean result = false;
      if(!this.isNumber(value)) {
         if(this.isDateTime(value)) {
            value = String.valueOf(this.convertDate2Number(value));
         } else if(!this.isNumber(value) && !exp.equals("<>")) {
            return result;
         }
      }

      if(exp.equals("<>")) {
         if(value.compareToIgnoreCase(compareValue) != 0) {
            result = true;
         }
      } else if(exp.equals(">=")) {
         if(Double.valueOf(value).doubleValue() >= Double.valueOf(compareValue).doubleValue()) {
            result = true;
         }
      } else if(exp.equals("<=")) {
         if(Double.valueOf(value).doubleValue() <= Double.valueOf(compareValue).doubleValue()) {
            result = true;
         }
      } else if(exp.equals("=")) {
         if(Double.valueOf(value) == Double.valueOf(compareValue)) {
            result = true;
         }
      } else if(exp.equals(">")) {
         if(Double.valueOf(value).doubleValue() > Double.valueOf(compareValue).doubleValue()) {
            result = true;
         }
      } else if(exp.equals("<") && Double.valueOf(value).doubleValue() < Double.valueOf(compareValue).doubleValue()) {
         result = true;
      }

      return result;
   }

   private boolean evaluateString(String exp, String compareValue, String value) {
      boolean result = false;
      if((this.isNumber(value) || this.isDateTime(value)) && !exp.equals("<>")) {
         return result;
      } else {
         if(exp.equals("<>")) {
            if(!this.evaluateWildCard(compareValue, value)) {
               result = true;
            }
         } else if(exp.equals(">=")) {
            if(compareValue.compareToIgnoreCase(value) <= 0) {
               result = true;
            }
         } else if(exp.equals("<=")) {
            if(compareValue.compareToIgnoreCase(value) >= 0) {
               result = true;
            }
         } else if(exp.equals("=")) {
            if(this.evaluateWildCard(compareValue, value)) {
               result = true;
            }
         } else if(exp.equals(">")) {
            if(compareValue.compareToIgnoreCase(value) < 0) {
               result = true;
            }
         } else if(exp.equals("<") && compareValue.compareToIgnoreCase(value) > 0) {
            result = true;
         }

         return result;
      }
   }

   public boolean evaluateWildCard(String criteria, String value) {
      boolean result = false;
      int len = criteria.length();
      StringBuffer sb = new StringBuffer(len);

      for(int i = 0; i < len; ++i) {
         char ch = criteria.charAt(i);
         switch(ch) {
         case 36:
         case 40:
         case 41:
         case 46:
         case 91:
         case 93:
         case 94:
            sb.append("\\").append(ch);
            break;
         case 42:
            sb.append(".*");
            break;
         case 63:
            sb.append('.');
            break;
         case 126:
            if(i + 1 < len) {
               ch = criteria.charAt(i + 1);
               switch(ch) {
               case 42:
               case 63:
                  sb.append('[').append(ch).append(']');
                  ++i;
                  continue;
               }
            }

            sb.append('~');
            break;
         default:
            sb.append(ch);
         }
      }

      if(Pattern.matches(sb.toString().toLowerCase(), value.toLowerCase())) {
         result = true;
      }

      if(this.isInteger(value)) {
         value = value + ".0";
         if(Pattern.matches(sb.toString().toLowerCase(), value.toLowerCase())) {
            result = true;
         }
      }

      return result;
   }

   private Date convert2Date(String value) {
      Date d = null;
      d = DateUtils.parseDateTime(mDateFormat, value);
      if(d == null) {
         d = DateUtils.parseTime(mDateFormat, value);
      }

      return d;
   }

   public boolean isExp(String value) {
      boolean result = false;
      if(Pattern.matches("=.*", value) || Pattern.matches(">.*", value) || Pattern.matches(">=.*", value) || Pattern.matches("<.*", value) || Pattern.matches("<=.*", value) || Pattern.matches("<>.*", value)) {
         result = true;
      }

      return result;
   }

   public boolean isNumber(String value) {
      return Pattern.matches("[0-9]+", value) || Pattern.matches("[0-9]*.[0-9]+", value);
   }

   public boolean isInteger(String value) {
      boolean result = true;
      if(value != null && value.trim().length() != 0) {
         if(!Pattern.matches("[-]?[0-9]+", value)) {
            return false;
         } else {
            try {
               Integer.parseInt(value);
            } catch (Exception var4) {
               result = false;
            }

            return result;
         }
      } else {
         return false;
      }
   }

   public boolean isDateTime(String value) {
      boolean result = false;
      if(DateUtils.parseDateTime(mDateFormat, value) != null) {
         result = true;
      } else if(DateUtils.parseTime(mDateFormat, value) != null) {
         result = true;
      }

      return result;
   }

   public boolean isBoolean(String value) {
      boolean result = false;
      if("TRUE".equalsIgnoreCase(value)) {
         result = true;
      } else if("FALSE".equalsIgnoreCase(value)) {
         result = true;
      }

      return result;
   }

   public double convertDate2Number(String value) {
      Calendar c = Calendar.getInstance();
      Date d = DateUtils.parseDateTime(mDateFormat, value);
      if(d == null) {
         d = DateUtils.parseTime(mDateFormat, value);
         if(d != null) {
            c.setTime(d);
            return this.toExcelSerialDate((double)c.get(11), (double)c.get(12), (double)c.get(13));
         } else {
            throw new IllegalStateException("Wrong date time format.");
         }
      } else if(d != null) {
         c.setTime(d);
         return this.toExcelSerialDate(c.get(5), c.get(2) + 1, c.get(1), (double)c.get(11), (double)c.get(12), (double)c.get(13));
      } else {
         throw new IllegalStateException("Wrong date time format.");
      }
   }

   private double toExcelSerialDate(int day, int month, int year, double hour, double minute, double second) {
      if(day == 29 && month == 2 && year == 1900) {
         return 60.0D;
      } else {
         double serialDate = (double)(1461 * (year + 4800 + (month - 14) / 12) / 4 + 367 * (month - 2 - 12 * ((month - 14) / 12)) / 12 - 3 * ((year + 4900 + (month - 14) / 12) / 100) / 4 + day - 2415019 - 32075);
         if(serialDate < 60.0D) {
            --serialDate;
         }

         serialDate += (hour + minute / 60.0D + second / 3600.0D) / 24.0D;
         return serialDate;
      }
   }

   private double toExcelSerialDate(double hour, double minute, double second) {
      double serialDate = (hour + minute / 60.0D + second / 3600.0D) / 24.0D;
      return serialDate;
   }

   public boolean isNotEqualExp(String value) {
      boolean result = false;
      if(Pattern.matches("<>.*", value)) {
         result = true;
      }

      return result;
   }

   public boolean isEqualExp(String value) {
      boolean result = false;
      if(Pattern.matches("=*", value) || Pattern.matches("\\*", value)) {
         result = true;
      }

      return result;
   }

}
