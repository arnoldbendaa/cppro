// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


class CSVFileReader$CSVParser {

   private String mLine;
   private int mPos;
   private StringBuilder mTokenBuffer = new StringBuilder();


   public void init(String line) {
      this.mLine = line;
      this.mPos = 0;
      this.mTokenBuffer.delete(0, this.mTokenBuffer.length());
   }

   public boolean hasMoreTokens() {
      return this.moreChars() && this.peekChar() != 44;
   }

   String nextToken() {
      this.mTokenBuffer.delete(0, this.mTokenBuffer.length());
      boolean startToken = true;
      boolean quoteMode = false;

      for(boolean endToken = false; this.moreChars() && !endToken; startToken = false) {
         char c = this.nextChar();
         switch(c) {
         case 34:
            if(startToken) {
               quoteMode = true;
            } else if(quoteMode) {
               if(this.moreChars()) {
                  if(this.peekChar() == 44) {
                     quoteMode = false;
                  } else if(this.peekChar() == 34) {
                     this.nextChar();
                     this.mTokenBuffer.append('\"');
                  } else {
                     this.mTokenBuffer.append(c);
                  }
               } else {
                  quoteMode = false;
               }
            } else if(this.moreChars()) {
               if(this.peekChar() == 34) {
                  this.nextChar();
                  this.mTokenBuffer.append('\"');
               } else {
                  this.mTokenBuffer.append('\"');
               }
            } else {
               this.mTokenBuffer.append(c);
            }
            break;
         case 44:
            if(!quoteMode) {
               endToken = true;
               break;
            }
         default:
            this.mTokenBuffer.append(c);
         }
      }

      return this.mTokenBuffer.toString();
   }

   boolean moreChars() {
      return this.mPos < (this.mLine != null?this.mLine.length():0);
   }

   char nextChar() {
      return this.mLine.charAt(this.mPos++);
   }

   char peekChar() {
      return this.mLine.charAt(this.mPos);
   }
}
