// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public class StringLexer {

   private String mInput;
   private String mDelimiter;
   private int mIndex;


   public StringLexer(String input, String delimiter) {
      this.mInput = input;
      this.mDelimiter = delimiter;
      this.mIndex = 0;
   }

   public String nextToken() {
      int nextIndex = this.indexOfNextToken(this.mIndex);
      if(nextIndex == -1) {
         return null;
      } else {
         String token = this.mInput.substring(this.mIndex, nextIndex);
         this.mIndex = nextIndex + (this.isDelimAtIndex(nextIndex)?this.mDelimiter.length():0);
         return token;
      }
   }

   public boolean hasMoreTokens() {
      return this.indexOfNextToken(this.mIndex) != -1;
   }

   private int indexOfNextToken(int index) {
      int length = this.mInput.length();
      if(index != -1 && index < length) {
         int i;
         for(i = index; i < length && Character.isWhitespace(this.mInput.charAt(i)); ++i) {
            ;
         }

         while(i < length && !this.isDelimAtIndex(i)) {
            ++i;
         }

         return i == index?-1:i;
      } else {
         return -1;
      }
   }

   private boolean isDelimAtIndex(int index) {
      return index > 0 && index <= this.mInput.length() - this.mDelimiter.length() && this.mDelimiter.equals(this.mInput.substring(index, index + this.mDelimiter.length()));
   }
}
