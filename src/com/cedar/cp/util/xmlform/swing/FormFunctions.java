// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.inputs.LookupTarget;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class FormFunctions implements Serializable {

   public static final String FUNCTION_PROTOTYPES = "import java.lang.String;\nimport java.lang.Double;\nimport java.lang.Boolean;\nimport java.util.Date;\nimport java.util.Calendar;\nimport com.cedar.cp.util.xmlform.PropertiesMap;\n;import com.cedar.cp.util.xmlform.CalendarInfo;\ndouble result = 0.0;\nString stringResult = \"\";\nDate dateResult = null;\nString defaultDateFormat = \"yyyy-MM-dd\";\nint rownum = -1;\nboolean isBlank( colValue ) { return FormFunctions.isBlank(colValue); }\nboolean flagSet( colValue ) { return FormFunctions.isFlagSet(colValue); }\nString left( colValue, colLen ) { return FormFunctions.left(colValue,colLen); }\nString right( colValue, colLen ) { return FormFunctions.right(colValue,colLen); }\ndouble round( colValue, scale ) { return FormFunctions.round(colValue,scale); }\nString sysVariable( varName ) { return FormFunctions.sysVar(varName); }\ndouble sum( colName ) { return FormFunctions.sum(colName); }\nboolean isValidDate( date ) { return FormFunctions.isValidDate(date, defaultDateFormat); }\nboolean isValidDate( date, format ) { return FormFunctions.isValidDate(date, format); }\nDate getContextDate() { return FormFunctions.getContextDate(); }\nDate getContextDate( int yearOffset, int monthOffset, int dayOffset) { return FormFunctions.getContextDate(yearOffset, monthOffset, dayOffset); }\nDate getContextColumnDate() { return FormFunctions.getContextColumnDate(); }\nDate getDate( String date ) { return FormFunctions.getDate(date, defaultDateFormat); }\nDate getDate( String date, String format ) { return FormFunctions.getDate(date, format); }\nint getYear( String date ) { return FormFunctions.getYear(date, defaultDateFormat); }\nint getYear( String date, String format ) { return FormFunctions.getYear(date, format); }\nint getYear( java.util.Date date ) { return FormFunctions.getYear(date); }\nint getMonth( String date ) { return FormFunctions.getMonth(date, defaultDateFormat); }\nint getMonth( String date, String format ) { return FormFunctions.getMonth(date, format); }\nint getMonth( java.util.Date date ) { return FormFunctions.getMonth(date); }\nint getDayOfMonth( String date ) { return FormFunctions.dayOfMonth(date, defaultDateFormat); }\nint getDayOfMonth( String date, String format ) { return FormFunctions.dayOfMonth(date, format); }\nint getDayOfMonth( java.util.Date date ) { return FormFunctions.dayOfMonth(date); }\nint getDayOfYear( String date ) { return FormFunctions.dayOfYear(date, defaultDateFormat); }\nint getDayOfYear( String date, String format ) { return FormFunctions.dayOfYear(date, format); }\nint getDayOfYear( java.util.Date date ) { return FormFunctions.dayOfYear(date); }\nint compareDates( dateOneText, dateTwoText, format ) { return FormFunctions.compareDates(dateOneText, dateTwoText, format); }\nint getDifferenceInMonths( String dateOneText, String dateTwoText) { return FormFunctions.getDifferenceInMonths(dateOneText, dateTwoText, null); }\nint getDifferenceInMonths( String dateOneText, String dateTwoText, String format ) { return FormFunctions.getDifferenceInMonths(dateOneText, dateTwoText, format); }\nint getDifferenceInMonths( java.util.Date startDate, java.util.Date endDate ) { return FormFunctions.getDifferenceInMonths(startDate, endDate); }\nint getDaysInMonth( String date ) { return FormFunctions.getDaysInMonth(date, defaultDateFormat); }\nint getDaysInMonth( String date, String format ) { return FormFunctions.getDaysInMonth(date, format); }\nint getDaysInMonth( java.util.Date date ) { return FormFunctions.getDaysInMonth(date); }\nint getDaysInYear( String date ) { return FormFunctions.getDaysInYear(date, defaultDateFormat); }\nint getDaysInYear( String date, String format ) { return FormFunctions.getDaysInYear(date, format); }\nint getDaysInYear( java.util.Date date ) { return FormFunctions.getDaysInYear(date); }\nint getDifferenceInDays( String dateOneText, String dateTwoText ) { return FormFunctions.getDifferenceInDays(dateOneText, dateTwoText, defaultDateFormat); }\nint getDifferenceInDays( String dateOneText, String dateTwoText, String format ) { return FormFunctions.getDifferenceInDays(dateOneText, dateTwoText, format); }\nint getDifferenceInDays( Date startDate, Date endDate ) { return FormFunctions.getDifferenceInDays(startDate, endDate); }\nString calcNewDate( startDate, format, days ) { return FormFunctions.calcNewDate(startDate, format, days); }\nDate adjustDate( java.util.Date date, int yearOffset, int monthOffset, int dayOffset ) { return FormFunctions.adjustDate(date, yearOffset, monthOffset, dayOffset); }\ndouble lookup( String colName, Object colValue ) { return FormFunctions.lookup(colName,colValue,null, null); }\ndouble lookup( String colName, Object colValue, Date date ) { return FormFunctions.lookup(colName,colValue,null,date); }\ndouble lookup( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookup(colName,colValue,partitionValue, null); }\ndouble lookup( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookup(colName,colValue,partitionValue, date); }\nString lookupString( String colName, Object colValue ) { return FormFunctions.lookupString(colName,colValue,null, null); }\nString lookupString( String colName, Object colValue, Date date ) { return FormFunctions.lookupString(colName,colValue,null, date); }\nString lookupString( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookupString(colName,colValue,partitionValue, null); }\nString lookupString( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookupString(colName,colValue,partitionValue, date); }\nDate lookupDate( String colName, Object colValue ) { return FormFunctions.lookupDate(colName,colValue,null, null); }\nDate lookupDate( String colName, Object colValue, Date date ) { return FormFunctions.lookupDate(colName,colValue,null, date); }\nDate lookupDate( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookupDate(colName,colValue,partitionValue, null); }\nDate lookupDate( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookupDate(colName,colValue,partitionValue, date); }\ndouble level() { return FormFunctions.level(); }\nint rowNumber() { return FormFunctions.rowNumber(); }\nString colDataType( colName ) { return FormFunctions.columnDataType(colName); }\nString colPeriodVisId( colName ) { return FormFunctions.columnPeriodVisId(colName); }\nString colPeriodPathToRoot( colName ) { return FormFunctions.columnPeriodPathToRoot(colName); }\nDate colPeriodDate( colName ) { return FormFunctions.columnPeriodDate(colName); }\nString elementVisId( dimIndex ) { return FormFunctions.elementVisId(dimIndex); }\nboolean isReadOnlyNominal(){ FormFunctions.isReadOnlyNominal(); }\nvoid setColumnHeader( colName, colHeaderText ){ FormFunctions.setColumnHeader( colName, colHeaderText ); }\nvoid setColumnGroupHeader( colName, colHeaderText ){ FormFunctions.setColumnGroupHeader( colName, colHeaderText ); }\nvoid registerValidationMessage( String colName, String message ){ FormFunctions.registerValidationMessage( rownum, colName, message ); }\nvoid clearValidationMessage( String colName ){ FormFunctions.clearValidationMessage( rownum, colName ); }\nvoid clearValidationMessages(){ FormFunctions.clearValidationMessage( rownum ); }\nString getBudgetCycleStateAsString(){ return FormFunctions.getBudgetCycleStateAsString(); }\n String getBudgetCycleStateAsString( String costCentre ){ return FormFunctions.getBudgetCycleStateAsString(costCentre); }\n ";
   private Map mVariables;
   private DefaultMutableTreeNode mProcessingNode;
   private static final String sDefaultDateFormat = "yyyy-MM-dd";
   private Map<Integer, Map<String, String>> mValidationMessages;


   public FormFunctions(Map variables) {
      this.mVariables = variables;
      this.mValidationMessages = new HashMap();
   }

   public void setProcessingNode(DefaultMutableTreeNode node) {
      this.mProcessingNode = node;
   }

   protected DefaultMutableTreeNode getProcessingNode() {
      return this.mProcessingNode;
   }

   public void setContextVariables(Map variables) {
      this.mVariables = variables;
   }

   public void setContextVariable(Object key, Object value) {
      this.mVariables.put(key, value);
   }

   public boolean isBlank(Object colValue) {
      return colValue == null || colValue.toString().trim().length() == 0;
   }

   public boolean isFlagSet(Object colValue) {
      return colValue != null && colValue.toString().trim().toUpperCase().equals("Y");
   }

   public String left(Object colValue, int len) {
      if(len < 1) {
         return "";
      } else {
         String text = colValue.toString();
         return len >= text.length()?text:text.substring(0, len);
      }
   }

   public String right(Object colValue, int len) {
      if(len < 1) {
         return "";
      } else {
         String text = colValue.toString();
         if(len >= text.length()) {
            return text;
         } else {
            int start = text.length() - len;
            return text.substring(start);
         }
      }
   }

   public double round(double value, int scale) {
      double interim = value * Math.pow(10.0D, (double)scale);
      long rounded = Math.round(interim);
      return (double)rounded / Math.pow(10.0D, (double)scale);
   }

   public String sysVar(String varName) {
      Object t = this.mVariables.get(varName);
      if(t == null) {
         t = "";
      }

      return t.toString();
   }

   public String columnDataType(String colName) {
      return "";
   }

   public String columnPeriodVisId(String colName) {
      return "";
   }

   public String columnPeriodPathToRoot(String colName) {
      return "";
   }

   public Date columnPeriodDate(String colName) {
      return null;
   }

   public abstract double sum(String var1);

   public abstract double level();

   public abstract String elementVisId(int var1);

   public abstract boolean isReadOnlyNominal();

   public abstract String getBudgetCycleStateAsString(String var1);

   public abstract String getBudgetCycleStateAsString();

   public int getDaysInMonth(String date, String format) {
      return date != null && date.trim().length() != 0?this.getDaysInMonth(this.getDate(date, format)):-1;
   }

   public int getDaysInMonth(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.getActualMaximum(5);
   }

   public int getDaysInYear(String date, String format) {
      return date != null && date.trim().length() != 0?this.getDaysInYear(this.getDate(date, format)):-1;
   }

   public int getDaysInYear(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.getActualMaximum(6);
   }

   public int dayOfMonth(String date, String format) {
      return date != null && date.trim().length() != 0?this.dayOfMonth(this.getDate(date, format)):-1;
   }

   public int dayOfMonth(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.get(5);
   }

   public int dayOfYear(String date, String format) {
      return date != null && date.trim().length() != 0?this.dayOfYear(this.getDate(date, format)):-1;
   }

   public int dayOfYear(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.get(6);
   }

   public int compareDates(String dateOneText, String dateTwoText, String format) {
      return dateOneText != null && dateOneText.trim().length() != 0 && dateTwoText != null && dateTwoText.trim().length() != 0?this.getDate(dateOneText, format).compareTo(this.getDate(dateTwoText, format)):Integer.MAX_VALUE;
   }

   public int getYear(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.get(1);
   }

   public int getYear(String date, String format) {
      return this.getYear(this.getDate(date, format));
   }

   public int getMonth(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.get(2);
   }

   public int getMonth(String date, String format) {
      return this.getMonth(this.getDate(date, format));
   }

   public int getDifferenceInMonths(String dateOneText, String dateTwoText, String format) {
      return dateOneText != null && dateOneText.trim().length() != 0 && dateTwoText != null && dateTwoText.trim().length() != 0?this.getDifferenceInMonths(this.getDate(dateOneText, format), this.getDate(dateTwoText, format)):-1;
   }

   public int getDifferenceInMonths(Date startDate, Date endDate) {
      int startYear = this.getYear(startDate);
      int endYear = this.getYear(endDate);
      if(startYear == endYear) {
         return this.getMonth(endDate) - this.getMonth(startDate);
      } else {
         int numberOfYears;
         if(startYear + 1 == endYear) {
            numberOfYears = 12 - this.getMonth(startDate);
            return numberOfYears + this.getMonth(endDate);
         } else {
            numberOfYears = endYear - startYear;
            int fullYearMonths = (numberOfYears - 1) * 12;
            int monthsLeftInYear = 12 - this.getMonth(startDate);
            return fullYearMonths + monthsLeftInYear + this.getMonth(endDate);
         }
      }
   }

   public int getDifferenceInDays(String dateOneText, String dateTwoText, String format) {
      return dateOneText != null && dateOneText.trim().length() != 0 && dateTwoText != null && dateTwoText.trim().length() != 0?this.getDifferenceInDays(this.getDate(dateOneText, format), this.getDate(dateTwoText, format)):-1;
   }

   public int getDifferenceInDays(Date startDate, Date endDate) {
      long milliDiff = endDate.getTime() - startDate.getTime();
      long seconds = milliDiff / 1000L;
      int mins = (int)seconds / 60;
      int hours = mins / 60;
      return hours / 24;
   }

   public String calcNewDate(String startDate, String format, int days) {
      if(startDate != null && startDate.trim().length() != 0) {
         Date date = this.adjustDate(this.getDate(startDate, format), 0, 0, days);
         SimpleDateFormat df = new SimpleDateFormat(format == null?"yyyy-MM-dd":format);
         return df.format(date);
      } else {
         return null;
      }
   }

   public Date adjustDate(Date date, int years, int months, int days) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      cal.add(1, years);
      cal.add(2, months);
      cal.add(5, days);
      return cal.getTime();
   }

   public boolean isValidDate(String date, String format) {
      if(date != null && date.trim().length() != 0) {
         try {
            SimpleDateFormat e = new SimpleDateFormat(format == null?"yyyy-MM-dd":format);
            Date realDate = e.parse(date);
            return e.format(realDate).equals(date);
         } catch (ParseException var5) {
            return false;
         }
      } else {
         return false;
      }
   }

   public Date getDate(String date, String format) {
      if(date != null && date.trim().length() != 0) {
         try {
            SimpleDateFormat e = new SimpleDateFormat(format == null?"yyyy-MM-dd":format);
            Date d = e.parse(date);
            if(!e.format(d).equals(date)) {
               throw new ParseException("date does not match format", 0);
            } else {
               return d;
            }
         } catch (ParseException var5) {
            System.err.println("getDate(" + date + "," + format + ") - " + var5.getMessage());
            return null;
         }
      } else {
         return null;
      }
   }

   public Date getContextDate() {
      return (Date)this.mVariables.get("lookupDate");
   }

   public Date getContextDate(int yearOffset, int monthOffset, int dayOffset) {
      Date d = this.getContextDate();
      return d == null?null:this.adjustDate(d, yearOffset, monthOffset, dayOffset);
   }

   public Date getContextColumnDate() {
      Object d = (Date)this.mVariables.get("columnDate");
      if(d != null) {
         return (Date)d;
      } else {
         String calDimId = null;
         calDimId = WorkbookProperties.DIMENSION_2_VISID.toString();
//         Iterator calinfo = this.mVariables.keySet().iterator();
//
//         while(calinfo.hasNext()) {
//            Object k = calinfo.next();
//            if(k.toString().startsWith("%dim") && k.toString().substring(5).equals("%") && (calDimId == null || k.toString().compareTo(calDimId) > 0)) {
//               calDimId = k.toString();
//            }
//         }

         CalendarInfo calinfo1 = (CalendarInfo)this.mVariables.get(WorkbookProperties.CALENDAR_INFO.toString()
                 + this.mVariables.get(WorkbookProperties.MODEL_ID.toString()));
         if(calinfo1 != null && calinfo1.getById(this.mVariables.get(calDimId)) != null) {
            d = calinfo1.getById(this.mVariables.get(calDimId)).getActualDate();
         }

         if(d == null) {
            d = (Date)this.mVariables.get("lookupDate");
         }

         return (Date)d;
      }
   }

   public double lookup(String lookupTable, Object key, Object partitionKey, Date date) throws Exception {
      double result = 0.0D;
      Object res = this.getLookupValue(lookupTable, key, partitionKey, date == null?this.getContextColumnDate():date);
      if(res instanceof Date) {
         result = (double)((Date)res).getTime();
      } else if(res instanceof BigDecimal) {
         result = ((BigDecimal)res).doubleValue();
      }

      return result;
   }

   public String lookupString(String lookupTable, Object key, Object partitionKey, Date date) throws Exception {
      String result = "";
      Object res = this.getLookupValue(lookupTable, key, partitionKey, date == null?this.getContextColumnDate():date);
      if(res != null) {
         result = res.toString();
      }

      return result;
   }

   public Date lookupDate(String lookupTable, Object key, Object partitionKey, Date date) throws Exception {
      Date result = null;
      Object res = this.getLookupValue(lookupTable, key, partitionKey, date == null?this.getContextColumnDate():date);
      if(res instanceof Date) {
         result = (Date)res;
      }

      return result;
   }

   private Object getLookupValue(String lookupTable, Object key, Object partitionKey, Date date) throws Exception {
      LookupTarget lut = (LookupTarget)this.mVariables.get(lookupTable);
      if(lut == null) {
         throw new Exception("lookInput id=" + lookupTable + " not found");
      } else {
         Object retValue = lut.lookup(partitionKey, key, date == null?this.getContextDate():date);
         return retValue;
      }
   }

   public abstract void setColumnHeader(String var1, String var2);

   public abstract void setColumnGroupHeader(String var1, String var2);

   public void registerValidationMessage(Object row, String colName, String validationMessage) {}

   public void clearValidationMessage(Object row, String colName) {}

   public void clearValidationMessage(Object row) {}
}
