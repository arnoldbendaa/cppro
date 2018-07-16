// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public final class SingleCharTokenizer {

   private char[] mInput = new char[65536];
   private int mPos;
   private int mStartPos;
   private int mLength;
   private char mDeliminator;
   private boolean mHasNextToken;


   public SingleCharTokenizer(char deliminator) {
      this.mDeliminator = deliminator;
      this.mHasNextToken = false;
   }

   public void setString(String input) {
      this.mLength = input.length();
      input.getChars(0, this.mLength, this.mInput, 0);
      if(this.mInput[this.mLength - 1] != this.mDeliminator) {
         this.mInput[this.mLength++] = this.mDeliminator;
      }

      this.mStartPos = 0;
      this.mPos = 0;
      this.mHasNextToken = this.mPos < this.mLength;
   }

   public String nextToken() {
      if(!this.mHasNextToken) {
         return null;
      } else {
         while(this.mInput[this.mPos++] != this.mDeliminator) {
            ;
         }

         String token = new String(this.mInput, this.mStartPos, this.mPos - 1 - this.mStartPos);
         this.mStartPos = this.mPos;
         this.mHasNextToken = this.mPos < this.mLength;
         return token;
      }
   }

   public void nextToken(StringBuffer tok) {
      tok.setLength(0);
      if(this.mHasNextToken) {
         while(this.mInput[this.mPos++] != this.mDeliminator) {
            ;
         }

         tok.append(this.mInput, this.mStartPos, this.mPos - 1 - this.mStartPos);
         this.mStartPos = this.mPos;
         this.mHasNextToken = this.mPos < this.mLength;
      }
   }

   public boolean hasNextToken() {
      return this.mHasNextToken;
   }
}
