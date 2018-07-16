// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.api.base.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class CalendarNodeDateFormatter {

   private static String[][] sDateFormatStrings = new String[][]{{"y", String.valueOf(1)}, {"M", String.valueOf(2)}, {"w", String.valueOf(3)}, {"W", String.valueOf(4)}, {"D", String.valueOf(6)}, {"d", String.valueOf(5)}, {"F", String.valueOf(8)}, {"E", String.valueOf(7)}};


   public static void main(String[] args) throws Exception {
      String[][] dates = new String[][]{{"${yy-1}-${yy}", "yyyy"}, {"${yyyy}-${yyyy+1}", "yyyy"}, {"MMMM - {MMMM+5}", "Half Year MMMM - {MMMM+5}"}, {"${MMMM} - ${MMMM+5}", "Half Year ${MMMM} - ${MMMM+5}"}, {"MMM - {MMM+5}", "Half Year MMM - {MMM+5}"}, {"${MMM} - ${MMM+5}", "Half Year ${MMM} - ${MMM+5}"}, {"Q{idx}", "Q{idx} MMMM - {MMMM+2}"}, {"Q${idx}", "Q${idx} ${MMMM} - ${MMMM+2}"}, {"MMMM", "MMMM - yyyy"}, {"${MMMM}", "${MMMM} - ${yyyy}"}, {"Wk:ww", "Wk:ww Yr:yyyy"}, {"Wk:${ww}", "Wk:${ww} Yr:${yyyy}"}, {"dd-MM-yyyy", "EEEE dd MMMM yyyy"}, {"${dd}-${MM}-${yyyy}", "${EEEE} ${dd} ${MMMM} ${yyyy}"}};
      CalendarNodeDateFormatter formatter = new CalendarNodeDateFormatter();
      byte nodeType = 0;
      String[][] arr$ = dates;
      int len$ = dates.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String[] s = arr$[i$];
         formatter.createNodeNames((new SimpleDateFormat("dd/MM/yyyy")).parse("01/01/2008"), nodeType, 0, s[0], s[1]);
      }

   }

   public String[] createNodeNames(Date date, int nodeType, int index, String visIdFormat, String descrFormat) throws ValidationException {
      String[] ans = new String[]{this.formatDate(new Date(date.getTime()), nodeType, index, visIdFormat), this.formatDate(new Date(date.getTime()), nodeType, index, descrFormat)};
      return ans;
   }

   public String formatDate(Date date, int nodeType, int index, String format) throws ValidationException {
      StringBuffer sb = new StringBuffer();
      if(format.indexOf(47) != -1) {
         throw new ValidationException("The character \'/\' is not allowed in a calendar element format string.");
      } else {
         StringTokenizer st = new StringTokenizer(format, "${}/:;. +-\'", true);

         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            this.processToken(date, token, nodeType, index, st, sb);
         }

         return sb.toString();
      }
   }

   private void processToken(Date date, String token, int nodeType, int index, StringTokenizer st, StringBuffer sb) throws ValidationException {
      if(token.trim().length() == 0) {
         sb.append(token);
      } else if(this.isDateFormatToken(token)) {
         SimpleDateFormat sdf = new SimpleDateFormat(token);
         sb.append(sdf.format(date));
      } else if(token.equals("$")) {
         if(!st.hasMoreTokens()) {
            sb.append("$");
         }

         token = st.nextToken();
         this.processToken(date, token, nodeType, index, st, sb);
      } else if(token.equals("{")) {
         this.parseCurlyBraceExpr(new Date(date.getTime()), nodeType, index, st, sb);
      } else {
         sb.append(token);
      }

   }

   private void parseCurlyBraceExpr(Date date, int nodeType, int index, StringTokenizer st, StringBuffer sb) throws ValidationException {
      if(!st.hasMoreTokens()) {
         throw new ValidationException("Unbalanced {}");
      } else {
         String token = st.nextToken();
         if(this.isDateFormatToken(token)) {
            String offset = token;
            if(!st.hasMoreTokens()) {
               throw new ValidationException("Unbalanaced {}");
            }

            token = st.nextToken();
            if(this.isAdditiveOp(token)) {
               String nfe = token;
               if(!st.hasMoreTokens()) {
                  throw new ValidationException("Expected numeric expression after \'+\' or \'-\'");
               }

               token = st.nextToken();

               int offset1;
               try {
                  offset1 = Integer.parseInt(token);
               } catch (NumberFormatException var14) {
                  throw new ValidationException("Unrecognised numeric expression after \'+\' or \'-\':[" + token + "]");
               }

               int calType = this.queryCalendarOffsetType(offset);
               GregorianCalendar cal = new GregorianCalendar();
               cal.setTime(date);
               cal.add(calType, nfe.equals("+")?offset1:-offset1);
               date.setTime(cal.getTime().getTime());
               SimpleDateFormat sdf = new SimpleDateFormat(offset);
               sb.append(sdf.format(date));
               if(!st.hasMoreTokens()) {
                  throw new ValidationException("Unbalanaced {}");
               }

               token = st.nextToken();
               if(!token.equals("}")) {
                  throw new ValidationException("Unbalanaced {}");
               }
            } else if(token.equalsIgnoreCase("}")) {
               SimpleDateFormat nfe1 = new SimpleDateFormat(offset);
               sb.append(nfe1.format(date));
            }
         } else if(this.isIndexExpr(token)) {
            if(!st.hasMoreTokens()) {
               throw new ValidationException("Unbalanaced {}");
            }

            token = st.nextToken();
            if(this.isAdditiveOp(token)) {
               if(!st.hasMoreTokens()) {
                  throw new ValidationException("Unbalanaced {}");
               }

               token = st.nextToken();

               int offset2;
               try {
                  offset2 = Integer.parseInt(token);
               } catch (NumberFormatException var13) {
                  throw new ValidationException("Unrecognised numeric expression after \'+\' or \'-\':[" + token + "]");
               }

               sb.append(index + 1 + (token.equals("+")?offset2:-offset2));
               if(!st.hasMoreTokens()) {
                  throw new ValidationException("Unbalanaced {}");
               }

               token = st.nextToken();
               if(!token.equals("}")) {
                  throw new ValidationException("Unbalanaced {}");
               }
            } else {
               sb.append(index + 1);
            }
         }

      }
   }

   private boolean isAdditiveOp(String expr) {
      return expr.equals("+") || expr.equals("-");
   }

   private boolean isIndexExpr(String expr) {
      return expr.equalsIgnoreCase("idx");
   }

   private int queryCalendarOffsetType(String token) throws ValidationException {
      String[][] arr$ = sDateFormatStrings;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String[] s = arr$[i$];
         if(this.isStrAll(token, s[0].charAt(0))) {
            return Integer.parseInt(s[1]);
         }
      }

      throw new ValidationException("Unexpected Java date format expr:" + token);
   }

   private boolean isDateFormatToken(String token) {
      String[][] arr$ = sDateFormatStrings;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String[] s = arr$[i$];
         if(this.isStrAll(token, s[0].charAt(0))) {
            return true;
         }
      }

      return false;
   }

   private boolean isStrAll(String s, char c) {
      for(int i = 0; i < s.length(); ++i) {
         if(s.charAt(i) != c) {
            return false;
         }
      }

      return true;
   }

}
