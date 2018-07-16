// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.encryption;

import com.cedar.framework.text.StringUtils;
import java.util.Random;

public final class LongSquirrel {

   private static final int SQRL_OVERHEAD = 10;
   private static final int MAX_SQRL_STRING = 1000;
   private static final int MAX_SQRL_BUFFER = 2020;
   private static Random myRandom;


   private static String decode(String encoded) {
      String result = "";
      int key = encoded.charAt(0);

      for(int index = 1; index < encoded.length(); ++index) {
         char tempCh = encoded.charAt(index);
         int nextKey = (key + 17) % 96;
         int tempBottom = ~tempCh & 15;
         int var7 = tempCh & 240 | tempBottom;
         var7 -= key;
         if(var7 < 32) {
            var7 += 96;
         }

         result = result + (char)var7;
         key = nextKey;
      }

      return result;
   }

   private static String encode(String uncoded) {
      String result = "";
      int seed = myRandom.nextInt() % 96 + 32;
      if(seed < 0) {
         seed *= -1;
      }

      result = result + (char)seed;

      for(int index = 0; index < uncoded.length(); ++index) {
         int tempCh = uncoded.charAt(index) + seed;
         if(tempCh > 127) {
            tempCh -= 96;
         }

         int tempBottom = ~tempCh & 15;
         tempCh = tempCh & 240 | tempBottom;
         result = result + (char)tempCh;
         seed = (seed + 17) % 96;
      }

      return result;
   }

   private static int hexToBin(String hexString) {
      return Integer.parseInt(hexString, 16);
   }

   private static String binToHex(int index) {
      if(index < 0) {
         index *= -1;
      }

      return Integer.toHexString(index).toUpperCase();
   }

   private static String buryText(String unBuried, int maxBufferSize) {
      String result = "";
      if(maxBufferSize < 2 * unBuried.length() + 10 || maxBufferSize > 2020) {
         maxBufferSize = 2020;
      }

      int minBufferSize = 2 * unBuried.length() + 10;
      if(minBufferSize == maxBufferSize) {
         --minBufferSize;
      }

      int randommed = myRandom.nextInt();
      if(randommed < 0) {
         randommed *= -1;
      }

      int randomBufferSize = minBufferSize + randommed % (maxBufferSize - minBufferSize);
      randomBufferSize += randomBufferSize % 2;
      int highStart = randomBufferSize - minBufferSize;

      int actualStart;
      do {
         if(highStart == 0) {
            actualStart = 0;
         } else {
            actualStart = myRandom.nextInt() % highStart;
         }

         if(actualStart < 0) {
            actualStart *= -1;
         }
      } while(actualStart > 300);

      for(int len = 0; len < actualStart; ++len) {
         result = result + binToHex(myRandom.nextInt() % 16);
      }

      String var13 = binToHex(unBuried.length());
      result = result + StringUtils.lpad(var13, '0', 3);

      int curLength;
      String start;
      for(curLength = 0; curLength < unBuried.length(); ++curLength) {
         start = binToHex(unBuried.charAt(curLength));
         result = result + StringUtils.lpad(start, '0', 2);
      }

      curLength = result.length();

      for(int var14 = curLength; var14 < randomBufferSize - 6; ++var14) {
         result = result + binToHex(myRandom.nextInt() % 16);
      }

      start = binToHex(actualStart);
      result = result + StringUtils.lpad(start, '0', 3);
      result = result + binToHex(myRandom.nextInt() % 16);
      int bufferCheckValue = 0;

      for(int index = randomBufferSize - 5; index >= 0; --index) {
         bufferCheckValue = (bufferCheckValue + result.charAt(index)) % 4095;
      }

      result = result + StringUtils.lpad(binToHex(bufferCheckValue), '0', 3);
      return result;
   }

   private static String digUpText(String buried) {
      String result = "";
      int calcCheckValue = 0;

      for(int val = buried.length() - 6; val >= 0; --val) {
         calcCheckValue = (calcCheckValue + buried.charAt(val)) % 4095;
      }

      String var8 = buried.substring(buried.length() - 3);
      int bufferCheckValue = hexToBin(var8);
      if(bufferCheckValue != calcCheckValue) {
         throw new IllegalArgumentException("Squirrel.digUpText: Checksum invalid");
      } else {
         var8 = buried.substring(buried.length() - 7, buried.length() - 4);
         int startOffset = hexToBin(var8);
         var8 = buried.substring(startOffset, startOffset + 3);
         int hexTextLength = hexToBin(var8);

         for(int index = startOffset + 3; index < startOffset + 3 + hexTextLength * 2; index += 2) {
            var8 = buried.substring(index, index + 2);
            result = result + (char)hexToBin(var8);
         }

         return result;
      }
   }

   public static String squirrel(String uncoded, int maxBufferSize) {
      if(uncoded.length() > 1000) {
         throw new IllegalArgumentException("Squirrel.squirrel: Uncoded string too large.");
      } else {
         myRandom = new Random();
         return buryText(encode(uncoded), maxBufferSize);
      }
   }

   public static String unSquirrel(String encoded) {
      if(encoded.length() > 2020) {
         throw new IllegalArgumentException("Squirrel.unSquirrel: Encoded string too large.");
      } else {
         return decode(digUpText(encoded));
      }
   }
}
