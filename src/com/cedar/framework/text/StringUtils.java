// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.text;

import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

   private static DecimalFormat decimalFormatter = new DecimalFormat("###,##0.00");
   private static DecimalFormat quantityFormatter = new DecimalFormat("#0.00");
   private static DecimalFormat percentageFormatter = new DecimalFormat("#0.00");


   public static String createStringFromListOfStrings(List pReqNos) {
      StringBuffer result = new StringBuffer();
      if(pReqNos != null) {
         for(int loop = 0; loop < pReqNos.size(); ++loop) {
            if(loop > 0) {
               if(loop != pReqNos.size() - 1) {
                  result.append(", ");
               } else {
                  result.append(" and ");
               }
            }

            result.append(pReqNos.get(loop));
         }
      }

      return result.toString();
   }

   public static boolean isInternetExplorer(String browserString) {
      boolean ie = false;
      if(browserString != null && browserString.toUpperCase().indexOf("MSIE") != -1) {
         ie = true;
      }

      return ie;
   }

   public static String escapeStringForAlert(String s, String browser) {
      if(s == null) {
         return "";
      } else {
         boolean internetExplorer = isInternetExplorer(browser);
         StringCharacterIterator i = new StringCharacterIterator(s);
         StringBuffer sb = new StringBuffer();

         for(char c = i.first(); c != '\uffff'; c = i.next()) {
            if(c == 13) {
               if(internetExplorer) {
                  sb.append("\\n");
               } else {
                  sb.append("\\n");
               }
            } else if(c == 10) {
               if(internetExplorer) {
                  sb.append("\\n");
               } else {
                  sb.append("\\n");
               }
            } else if(c != 34 && c != 39) {
               sb.append(c);
            }
         }

         return sb.toString();
      }
   }

   public static String escapeStringForTooltip(String s, String browser) {
      if(s == null) {
         return "";
      } else {
         boolean internetExplorer = isInternetExplorer(browser);
         StringCharacterIterator i = new StringCharacterIterator(s);
         StringBuffer sb = new StringBuffer();

         for(char c = i.first(); c != '\uffff'; c = i.next()) {
            if(c == 13) {
               if(internetExplorer) {
                  sb.append("&#10;");
               } else {
                  sb.append(" ");
               }
            } else if(c == 10) {
               if(internetExplorer) {
                  sb.append("&#13;");
               } else {
                  sb.append(" ");
               }
            } else if(c != 34) {
               sb.append(c);
            }
         }

         return sb.toString();
      }
   }

   public static String escapeStringForHTML(String s) {
      if(s == null) {
         return null;
      } else {
         StringCharacterIterator i = new StringCharacterIterator(s);
         StringBuffer sb = new StringBuffer();

         for(char c = i.first(); c != '\uffff'; c = i.next()) {
            if(c > 127) {
               sb.append("&#" + c + ";");
            } else {
               switch(c) {
               case 34:
                  sb.append("&quot;");
                  break;
               case 38:
                  sb.append("&amp;");
                  break;
               case 60:
                  sb.append("&lt;");
                  break;
               case 62:
                  sb.append("&gt;");
                  break;
               default:
                  sb.append(c);
               }
            }
         }

         return sb.toString();
      }
   }

   public static String escapeStringForXML(String s) {
      if(s == null) {
         return null;
      } else {
         StringCharacterIterator i = new StringCharacterIterator(s);
         StringBuffer sb = new StringBuffer();

         for(char c = i.first(); c != '\uffff'; c = i.next()) {
            switch(c) {
            case 34:
               sb.append("&quot;");
               break;
            case 38:
               sb.append("&amp;");
               break;
            case 39:
               sb.append("&apos;");
               break;
            case 60:
               sb.append("&lt;");
               break;
            case 62:
               sb.append("&gt;");
               break;
            case 8364:
               sb.append("&#8364;");
               break;
            default:
               sb.append(c);
            }
         }

         return sb.toString();
      }
   }

   public static String repeatString(String s, int num) {
      StringBuffer sb = new StringBuffer();

      for(int index = 0; index < num; ++index) {
         sb.append(s);
      }

      return sb.toString();
   }

   public static String formatDoubleAsCurrency(Double dub) {
      return dub == null?"0.00":formatAsCurrency(dub.doubleValue());
   }

   public static String formatAsCurrency(double dub) {
      return decimalFormatter.format(dub);
   }

   public static String formatDoubleAsQuantity(Double dub) {
      return dub == null?"0.00":quantityFormatter.format(dub.doubleValue());
   }

   public static String formatDoubleAsPercentage(Double dub) {
      return dub == null?"0.00%":percentageFormatter.format(dub.doubleValue()) + " %";
   }

   public static String commaAndAndCollection(Collection col) {
      StringBuffer buf = new StringBuffer("");
      int count = 0;
      int invSize = col.size();

      for(Iterator itr = col.iterator(); itr.hasNext(); ++count) {
         String sup = itr.next().toString();
         if(count == 0) {
            buf.append(sup);
         } else if(count == invSize - 1) {
            buf.append(" & " + sup);
         } else {
            buf.append(", " + sup);
         }
      }

      return buf.toString();
   }

   public static String simpleReplaceFirst(String value, String search, String replace) {
      if(value != null && search != null) {
         String localValue = new String(value);
         new StringBuffer();
         boolean pos = true;
         int pos1 = localValue.indexOf(search);
         StringBuffer rVal;
         if(pos1 == -1) {
            rVal = new StringBuffer(localValue);
         } else {
            rVal = new StringBuffer(localValue.substring(0, pos1));
            rVal.append(replace);
            rVal.append(localValue.substring(pos1 + search.length()));
         }

         return rVal.toString();
      } else {
         return value;
      }
   }

   public static String simpleReplaceAll(String value, String search, String replace) {
      if(value != null && search != null) {
         String localValue = new String(value);
         StringBuffer rVal = new StringBuffer();
         boolean pos = true;

         int pos1;
         do {
            pos1 = localValue.indexOf(search);
            if(pos1 == -1) {
               rVal.append(localValue);
            } else {
               localValue = simpleReplaceFirst(localValue, search, replace);
            }
         } while(pos1 != -1);

         return rVal.toString();
      } else {
         return value;
      }
   }

   public static void main(String[] args) {
      System.out.println(formatDoubleAsCurrency((Double)null));
      System.out.println(formatDoubleAsCurrency(new Double(0.0D)));
      System.out.println(formatDoubleAsCurrency(new Double(0.01D)));
      System.out.println(formatDoubleAsCurrency(new Double(0.178D)));
      System.out.println(formatDoubleAsCurrency(new Double(1.0D)));
      System.out.println(formatDoubleAsCurrency(new Double(5.99D)));
      System.out.println(formatDoubleAsCurrency(new Double(5.5D)));
      System.out.println(formatDoubleAsCurrency(new Double(1.147634D)));
      System.out.println(formatDoubleAsCurrency(new Double(9876.147634D)));
      System.out.println(formatDoubleAsCurrency(new Double(9.876543210147635E9D)));
      System.out.println(formatDoubleAsQuantity((Double)null));
      System.out.println(formatDoubleAsQuantity(new Double(0.0D)));
      System.out.println(formatDoubleAsQuantity(new Double(0.01D)));
      System.out.println(formatDoubleAsQuantity(new Double(0.178D)));
      System.out.println(formatDoubleAsQuantity(new Double(1.0D)));
      System.out.println(formatDoubleAsQuantity(new Double(5.99D)));
      System.out.println(formatDoubleAsQuantity(new Double(5.5D)));
      System.out.println(formatDoubleAsQuantity(new Double(1.147634D)));
      System.out.println(formatDoubleAsQuantity(new Double(9876.147634D)));
      System.out.println(formatDoubleAsQuantity(new Double(9.876543210147635E9D)));
      System.out.println(simpleReplaceAll("Hello <Return> for all <Return>", "<Return>", "Leave blank"));
   }

   public static String getOrdinalFor(int value) {
      int hundredRemainder = value % 100;
      int tenRemainder = value % 10;
      if(hundredRemainder - tenRemainder == 10) {
         return "th";
      } else {
         switch(tenRemainder) {
         case 1:
            return "st";
         case 2:
            return "nd";
         case 3:
            return "rd";
         default:
            return "th";
         }
      }
   }

   public static String toHexString(String s) {
      byte[] in = s.getBytes();
      boolean ch = false;
      int i = 0;
      if(in != null && in.length > 0) {
         String[] pseudo = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

         StringBuffer out;
         for(out = new StringBuffer(in.length * 2); i < in.length; ++i) {
            byte var7 = (byte)(in[i] & 240);
            var7 = (byte)(var7 >>> 4);
            var7 = (byte)(var7 & 15);
            out.append(pseudo[var7]);
            var7 = (byte)(in[i] & 15);
            out.append(pseudo[var7]);
         }

         String rslt = new String(out);
         return rslt;
      } else {
         return null;
      }
   }

   public static String lpad(String s, char c, int l) {
      for(int i = s.length(); i < l; ++i) {
         s = c + s;
      }

      return s;
   }

   public static String[] wordWrap(String str, int maxLength) {
      Pattern wrapRE = Pattern.compile(".{0," + Integer.toString(maxLength - 1) + "}(?:\\S(?:-| |$)|$)");
      ArrayList list = new ArrayList();
      Matcher m = wrapRE.matcher(str);

      while(m.find()) {
         list.add(m.group());
      }

      return (String[])((String[])list.toArray(new String[list.size()]));
   }

}
