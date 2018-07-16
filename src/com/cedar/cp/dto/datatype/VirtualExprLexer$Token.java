// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;


public class VirtualExprLexer$Token {

   String mStr;
   int mType;


   public VirtualExprLexer$Token(String str, int type) {
      this.mStr = str;
      this.mType = type;
   }

   public String getStr() {
      return this.mStr;
   }

   public int getType() {
      return this.mType;
   }

   public String toString() {
      return "Token(\'" + this.mStr + "\'," + this.mType + ")";
   }
}
