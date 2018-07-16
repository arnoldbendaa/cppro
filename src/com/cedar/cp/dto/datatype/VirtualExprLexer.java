// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.dto.datatype.VirtualExprLexer$Token;

public class VirtualExprLexer {

   private String mExpr;
   private int mPos;
   public static final int DELIM = 0;
   public static final int ID = 1;
   public static final int NUM = 2;


   public static void main(String[] args) {
      String value = "AB+CC*++DX/BY-(TV+PV)+abs(XX)";
      VirtualExprLexer el = new VirtualExprLexer(value);

      VirtualExprLexer$Token token;
      while((token = el.nextToken()) != null) {
         System.out.println("TOKEN[" + token + "]");
      }

   }

   public VirtualExprLexer(String expr) {
      this.mExpr = expr;
      this.mPos = 0;
   }

   public VirtualExprLexer$Token nextToken() {
      if(this.mPos >= this.mExpr.length()) {
         return null;
      } else {
         while(this.isWhitespace(this.currChar())) {
            ++this.mPos;
            if(this.mPos >= this.mExpr.length()) {
               return null;
            }
         }

         if(this.isDelim(this.currChar())) {
            String var2 = String.valueOf(this.currChar());
            ++this.mPos;
            return new VirtualExprLexer$Token(var2, 0);
         } else {
            int startPos;
            for(startPos = this.mPos; this.mPos < this.mExpr.length() && !this.isWhitespace(this.currChar()) && !this.isDelim(this.currChar()); ++this.mPos) {
               ;
            }

            return new VirtualExprLexer$Token(this.mExpr.substring(startPos, this.mPos), Character.isDigit(this.mExpr.charAt(startPos))?2:1);
         }
      }
   }

   private char currChar() {
      return this.mExpr.charAt(this.mPos);
   }

   private boolean isDelim(char c) {
      return "()+-*/".indexOf(c) != -1;
   }

   private boolean isWhitespace(char c) {
      return " \t\n\r".indexOf(c) != -1;
   }

   public int getCurrentPos() {
      return this.mPos;
   }
}
