/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.api.cpfunctionsevaluator;

import java.util.Map;
import java.util.regex.Pattern;

import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingValidator;

public class DateUtil {
    private static Pattern patternSlash = Pattern.compile("/");

    public static String[] fillDateFromContextIfIsEmpty(MappingValidator mv, String dateFromContext) {

        Map<String, String> args = mv.getListOfArguments();
        String actualDate = null;
        if (args.containsKey(MappingArguments.DIM2.toString())) {
            actualDate = args.get(MappingArguments.DIM2.toString());
        } else if (args.containsKey(MappingArguments.DATE.toString())) {
            actualDate = args.get(MappingArguments.DATE.toString());
        }
        return fillDateFromContextIfIsEmpty(actualDate, dateFromContext);
    }

    public static String[] fillDateFromContextIfIsEmpty(String actualDate, String dateFromContext) {
        String[] a = parseStringDateToArray(actualDate);
        String[] b = parseStringDateToArray(dateFromContext);
        if (a.length == 0 && b.length == 0) {
            return new String[2];
        } else if (a.length == 0) {
            return fillDateFromContextIfIsEmpty(null, null, b[0], b[1]);
        } else if (b.length == 0) {
            return fillDateFromContextIfIsEmpty(a[0], a[1], null, null);
        } else {
            return fillDateFromContextIfIsEmpty(a[0], a[1], b[0], b[1]);
        }
    }

    public static String[] fillDateFromContextIfIsEmpty(String year, String month, String cyear, String cmonth) {
        String[] actualDateTable = new String[2];
        if ((year == null) || (year.length() == 0)) {
            actualDateTable[0] = cyear;
            actualDateTable[1] = cmonth;
            return actualDateTable;
        } else if (year.startsWith("?")) {
            actualDateTable[0] = cyear;
            if (month != null && month.length() > 0) {
                actualDateTable[1] = month;
            } else {
                actualDateTable[1] = cmonth;
            }
            return actualDateTable;
        }
        actualDateTable[0] = year;
        actualDateTable[1] = month;
        return actualDateTable;
    }

    public static String[] parseStringDateToArray(String date) {
        String[] t = new String[2];
        if (date == null || date.trim().length() == 0) {
            return new String[0];
        } else if (!date.matches("^/?([?]{1}|[0-9]{4})(/([0-9]{1,2}|[A-Za-z]+)){0,1}$")) {
            return new String[0];
        } else {
            String temp = null;
            if (date.startsWith("/")) {
                temp = date.substring(1);
            } else {
                temp = date;
            }
            String[] p = patternSlash.split(temp);
            for (int i = 0; i < p.length; i++) {
                t[i] = p[i];
            }
        }
        return t;
    }

    public static int[] parseStringToIntDate(Map<String, String> parsedArgMap, String[] date) {
        String year = date[0];
        String yearShift = parsedArgMap.get("year");
        String month = date[1];
        String monthShift = parsedArgMap.get("period");
        return parseStringToIntDate(year, month, yearShift, monthShift);
    }
    
    public static int[] parseStringToIntDate(String year, String month, String yearShift, String monthShift) {
        int y = 0;
        int ys = 0;
        if (year == null || year.length() == 0 || year.matches("^[0-9]{4}$") == false) {
            return new int[4];
        } else {
            y = Integer.parseInt(year);
        }
        if (yearShift != null && yearShift.matches("^[+-]?[0-9]{1,3}$")) {
            ys = Integer.parseInt(yearShift);
        }
        int m = 0;
        int ms = 0;
        if (month == null || month.length() == 0 || month.matches("^[0-9]{1,2}$") == false) {
            // m = 0;
        } else {
            m = Integer.parseInt(month);
            if (monthShift != null && monthShift.matches("^[+-]?[0-9]{1,2}$")) {
                ms = Integer.parseInt(monthShift);
            }
        }
        int[] date = { y, m, ys, ms };
        return date;
    }

    public static String[] calculateDateForZeroFifteenRange(Map<String, String> parsedArgMap, String[] date) {
        return calculateDate(0, 15, parsedArgMap, date);
    }

    public static String[] calculateDateForOneTwelveRange(Map<String, String> parsedArgMap, String[] date) {
        return calculateDate(1, 12, parsedArgMap, date);
    }

    public static String[] calculateDate(int startsWith, int count, String yearShift, String monthShift, String year, String month){
        int[] d = parseStringToIntDate(year, month, yearShift, monthShift);
      //if month is out of range set some default values
        if(d[1] < startsWith || d[1] > startsWith + count - 1){
            d[1] = startsWith - 1;
            d[3] = 0;
        }
        d = calculateDate(startsWith, count, d[0], d[1], d[2], d[3]);

        String[] t = new String[2];
        t[0] = String.valueOf(d[0]);
        t[1] = String.valueOf(d[1]);
        if(month != null && month.equalsIgnoreCase("open") && monthShift == null){
            t[1] = month;
        }
        return t;
    }
    
    public static String[] calculateDate(int startsWith, int count, Map<String, String> parsedArgMap, String[] date) {
        int[] d = parseStringToIntDate(parsedArgMap, date);
        //if month is out of range set some default values
        if(d[1] < startsWith || d[1] > startsWith + count - 1){
            d[1] = startsWith - 1;
            d[3] = 0;
        }
        d = calculateDate(startsWith, count, d[0], d[1], d[2], d[3]);

        String[] t = new String[2];
        t[0] = String.valueOf(d[0]);
        t[1] = String.valueOf(d[1]);
        return t;
    }

    public static int[] calculateDate(int startsWith, int count, int year, int month, int yearShift, int monthShift) {
        int m = month - startsWith + monthShift;
        double y = year + yearShift + Math.floor(m / (double) count);
        m = m % count;
        if (m < 0) {
            m = count + m;
        }
        m += startsWith;
        int[] date = { (int) y, m };
        return date;
    }
    
    public static String dateToString(String[] date){
        if(date == null || date.length != 2 || date[0] == null){
            return "";
        }
        if (!date[1].equals("0")) {
            return "/" + date[0] + "/" + date[1];
        } else {
            return "/" + date[0];
        }
    }
}
