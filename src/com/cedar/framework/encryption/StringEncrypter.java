// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.encryption;

import java.util.Vector;

public class StringEncrypter {

   static char[] hexChar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   public static final String KEY = "secret";
   public static final String ENCODE = "UTF-8";


   public static String encrypt(String s, String key) throws IllegalArgumentException {
      if(key == null) {
         throw new IllegalArgumentException("Null parameter supplied");
      } else if(s == null) {
         return "";
      } else if(key.length() == 0) {
         throw new IllegalArgumentException("0 length key value supplied");
      } else {
         StringBuffer sb = new StringBuffer();
         int len = s.length();

         for(int encrypted = 0; encrypted < len; ++encrypted) {
            int hexencrypted = getCodeKeyValue(key, s, encrypted);
            sb.append(shiftChar(s.charAt(encrypted), hexencrypted));
         }

         String var8 = sb.toString();
         String var9 = "";

         try {
            var9 = toHexString(var8);
            return var9;
         } catch (Exception var7) {
            throw new IllegalArgumentException("Cant use char type on this machine : UTF-8");
         }
      }
   }

   public static String decrypt(String s, String key) throws IllegalArgumentException {
      if(s != null && s.length() != 0) {
         try {
            s = fromHexString(s);
         } catch (Exception var6) {
            throw new IllegalArgumentException("Cant use char type on this machine : UTF-8");
         }

         if(s != null && key != null) {
            if(key.length() == 0) {
               throw new IllegalArgumentException("0 length key value supplied");
            } else {
               StringBuffer sb = new StringBuffer();
               int len = s.length();

               for(int i = 0; i < len; ++i) {
                  int codekey_val = getCodeKeyValue(key, sb.toString(), i);
                  sb.append(shiftBackChar(s.charAt(i), codekey_val));
               }

               return sb.toString();
            }
         } else {
            throw new IllegalArgumentException("Null parameter supplied");
         }
      } else {
         return "";
      }
   }

   private static char shiftBackChar(char c, int shift) {
      byte ch_min = 0;
      short ch_max = 255;
      int ch_spread = ch_max - ch_min;
      int ch = (c - ch_min - shift) % ch_spread;
      if(ch < 0) {
         ch += ch_max;
      } else {
         ch += ch_min;
      }

      return (char)ch;
   }

   private static char shiftChar(char c, int shift) {
      byte ch_min = 0;
      short ch_max = 255;
      int ch_spread = ch_max - ch_min;
      return (char)(ch_min + (shift + c - ch_min) % ch_spread);
   }

   private static int getCodeKeyValue(String key, String s, int len) {
      int keyval = 0;

      int i;
      for(i = key.length(); i > 0; --i) {
         keyval += key.charAt(i - 1) * i;
      }

      for(i = len; i > 0; --i) {
         keyval += s.charAt(i - 1) * (i % 15);
      }

      return keyval;
   }

   public static Vector toVector(String s) {
      Vector v = new Vector();

      for(int i = 0; i < s.length(); ++i) {
         v.addElement(new Integer(s.charAt(i)));
      }

      return v;
   }

   public static String fromVector(Vector v) {
      String s = "";

      for(int i = 0; i < v.size(); ++i) {
         s = s + (char)((Integer)v.elementAt(i)).intValue();
      }

      return s;
   }

   public static String toHexString(String b) {
      StringBuffer sb = new StringBuffer(b.length() * 2);

      for(int i = 0; i < b.length(); ++i) {
         sb.append(hexChar[(b.charAt(i) & 240) >>> 4]);
         sb.append(hexChar[b.charAt(i) & 15]);
      }

      return sb.toString();
   }

   public static String fromHexString(String hexValue) throws Exception {
      int stringLength = hexValue.length();
      if((stringLength & 1) != 0) {
         throw new IllegalArgumentException("fromHexString requires an even number of hex characters");
      } else {
         StringBuffer sb = new StringBuffer();
         int i = 0;

         for(int j = 0; i < stringLength; ++j) {
            int high = charToNibble(hexValue.charAt(i));
            int low = charToNibble(hexValue.charAt(i + 1));
            sb.append((char)(high << 4 | low));
            i += 2;
         }

         return sb.toString();
      }
   }

   private static int charToNibble(char c) {
      if(48 <= c && c <= 57) {
         return c - 48;
      } else if(97 <= c && c <= 102) {
         return c - 97 + 10;
      } else if(65 <= c && c <= 70) {
         return c - 65 + 10;
      } else {
         throw new IllegalArgumentException("Invalid hex character: " + c);
      }
   }

}
