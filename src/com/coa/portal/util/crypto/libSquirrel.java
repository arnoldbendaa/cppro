// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.util.crypto;

import java.util.Random;

public class libSquirrel {

   private static String HEXSTRING = "0123456789ABCDEF";
   private static int SQRL_OVERHEAD = 8;
   private static int MAX_SQRL_STRING = 120;
   private static int MAX_SQRL_BUFFER = (MAX_SQRL_STRING + SQRL_OVERHEAD) * 2;
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
      return HEXSTRING.indexOf(character);
   }

   private static char binToHex(int index) {
      if(index < 0) {
         index *= -1;
      }

      return HEXSTRING.charAt(index);
   }

   private static String buryText(String unBuried, int maxBufferSize) {
      String result = "";
      String tmpResult = "";
      if(maxBufferSize < 2 * unBuried.length() + SQRL_OVERHEAD || maxBufferSize > MAX_SQRL_BUFFER) {
         maxBufferSize = MAX_SQRL_BUFFER;
      }

      int minBufferSize = 2 * unBuried.length() + SQRL_OVERHEAD;
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

   private static String digUpText(String buried) throws IllegalArgumentException {
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

   public static String squirrel(String uncoded, int maxBufferSize) throws IllegalArgumentException {
      if(uncoded.length() > MAX_SQRL_STRING) {
         throw new IllegalArgumentException("Squirrel.squirrel: Uncoded string too large.");
      } else {
         myRandom = new Random();
         return buryText(encode(uncoded), maxBufferSize);
      }
   }

   public static String unSquirrel(String encoded) throws IllegalArgumentException {
      if(encoded.length() > MAX_SQRL_BUFFER) {
         throw new IllegalArgumentException("Squirrel.unSquirrel: Encoded string too large.");
      } else {
         return decode(digUpText(encoded));
      }
   }

   public static void main(String[] args) {
      System.out.println("No. args: " + args.length);
      if(args.length != 2) {
         System.out.println("Usage: Squirrel -e|-d <String>");
      } else if(args[0].compareTo("-e") == 0 | args[0].compareTo("-E") == 0) {
         System.out.println(squirrel(args[1], 256));
      } else if(args[0].compareTo("-d") == 0 | args[0].compareTo("-D") == 0) {
         System.out.println(unSquirrel(args[1]));
      } else {
         System.out.println("Usage: Squirrel -e|-d <String>");
      }

   }

}
