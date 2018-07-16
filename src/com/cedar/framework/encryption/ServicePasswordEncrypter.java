// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.encryption;

import java.util.Random;

public final class ServicePasswordEncrypter {

   private static final String HEXSTRING = "0123456789ABCDEF";
   private static final int SQRL_OVERHEAD = 8;
   private static final int MAX_SQRL_STRING = 1000;
   private static final int MAX_SQRL_BUFFER = 2016;
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

   private static int hexToBin(char character) {
      return "0123456789ABCDEF".indexOf(character);
   }

   private static char binToHex(int index) {
      if(index < 0) {
         index *= -1;
      }

      return "0123456789ABCDEF".charAt(index);
   }

   private static String buryText(String unBuried, int maxBufferSize) {
      String result = "";
      String tmpResult = "";
      if(maxBufferSize < 2 * unBuried.length() + 8 || maxBufferSize > 2016) {
         maxBufferSize = 2016;
      }

      int minBufferSize = 2 * unBuried.length() + 8;
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
      if(highStart == 0) {
         actualStart = 0;
      } else {
         actualStart = myRandom.nextInt() % highStart;
      }

      if(actualStart < 0) {
         actualStart *= -1;
      }

      int bufferCheckValue;
      for(bufferCheckValue = 0; bufferCheckValue < randomBufferSize; ++bufferCheckValue) {
         tmpResult = tmpResult + binToHex(myRandom.nextInt() % 16);
      }

      for(bufferCheckValue = 0; bufferCheckValue < actualStart; ++bufferCheckValue) {
         result = result + tmpResult.charAt(bufferCheckValue);
      }

      result = result + binToHex((unBuried.length() & 240) / 16) + binToHex(unBuried.length() & 15);

      for(bufferCheckValue = 0; bufferCheckValue < unBuried.length(); ++bufferCheckValue) {
         result = result + binToHex((unBuried.charAt(bufferCheckValue) & 240) / 16) + binToHex(unBuried.charAt(bufferCheckValue) & 15);
      }

      for(bufferCheckValue = actualStart + unBuried.length() * 2 + 2; bufferCheckValue < randomBufferSize - 6; ++bufferCheckValue) {
         result = result + tmpResult.charAt(bufferCheckValue);
      }

      result = result + binToHex((actualStart & 240) / 16) + binToHex(actualStart & 15);
      result = result + tmpResult.charAt(randomBufferSize - 4);
      bufferCheckValue = 0;

      for(int index = randomBufferSize - 5; index >= 0; --index) {
         bufferCheckValue = (bufferCheckValue + result.charAt(index)) % 4095;
      }

      result = result + binToHex((bufferCheckValue & 3840) / 256) + binToHex((bufferCheckValue & 240) / 16) + binToHex(bufferCheckValue & 15);
      return result;
   }

   private static String digUpText(String buried) {
      String result = "";
      int calcCheckValue = 0;

      int bufferCheckValue;
      for(bufferCheckValue = buried.length() - 5; bufferCheckValue >= 0; --bufferCheckValue) {
         calcCheckValue = (calcCheckValue + buried.charAt(bufferCheckValue)) % 4095;
      }

      bufferCheckValue = hexToBin(buried.charAt(buried.length() - 1)) + hexToBin(buried.charAt(buried.length() - 2)) * 16 + hexToBin(buried.charAt(buried.length() - 3)) * 256;
      if(bufferCheckValue != calcCheckValue) {
         throw new IllegalArgumentException("Squirrel.digUpText: Checksum invalid");
      } else {
         int startOffset = hexToBin(buried.charAt(buried.length() - 5)) + hexToBin(buried.charAt(buried.length() - 6)) * 16;
         int hexTextLength = hexToBin(buried.charAt(startOffset)) * 16 + hexToBin(buried.charAt(startOffset + 1));

         for(int index = startOffset + 2; index < startOffset + 2 + hexTextLength * 2; index += 2) {
            result = result + (char)(hexToBin(buried.charAt(index)) * 16 + hexToBin(buried.charAt(index + 1)));
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
      if(encoded.length() > 2016) {
         throw new IllegalArgumentException("Squirrel.unSquirrel: Encoded string too large.");
      } else {
         return decode(digUpText(encoded));
      }
   }
}
