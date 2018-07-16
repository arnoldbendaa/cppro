// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.SecurityAccessExpressionLexer;
import com.cedar.cp.dto.model.SecurityAccessParserListener;
import com.cedar.cp.dto.model.SecurityAccessTokenTypes;
import java.text.ParseException;

public class SecurityAccessExpressionParser extends SecurityAccessTokenTypes {

   private String mToken;
   private int mTokenType;
   private SecurityAccessExpressionLexer mLexer;
   private SecurityAccessParserListener mListener;


   public SecurityAccessExpressionParser(String text) {
      this.mLexer = new SecurityAccessExpressionLexer(text);
   }

   public void parse() throws ParseException {
      if(this.nextToken() == null) {
         throw new ParseException("Empty expression", 0);
      } else {
         this.expression();
         this.match(6);
      }
   }

   private final void expression() throws ParseException {
      this.logical_and_expression();

      while(this.getCurrentTokenType() == 3) {
         this.match(3);
         this.logical_and_expression();
      }

   }

   private final void logical_and_expression() throws ParseException {
      this.unary_expression();

      while(this.getCurrentTokenType() == 2) {
         this.match(2);
         this.unary_expression();
      }

   }

   private final void unary_expression() throws ParseException {
      switch(this.getCurrentTokenType()) {
      case 0:
      case 4:
         this.primary();
         break;
      case 1:
         this.match(1);
         this.primary();
         break;
      case 2:
      case 3:
      default:
         throw new ParseException("Syntax error at : " + this.getCurrentToken(), 0);
      }

   }

   private final void primary() throws ParseException {
      switch(this.getCurrentTokenType()) {
      case 0:
         String rangeRef = this.getCurrentToken();
         this.match(0);
         if(this.mListener != null) {
            this.mListener.registerRangeRefernce(rangeRef);
         }
         break;
      case 4:
         this.match(4);
         this.expression();
         this.match(5);
         break;
      default:
         throw new ParseException("Syntax error at : " + this.getCurrentToken(), 0);
      }

   }

   private String getCurrentToken() {
      return this.mToken == null?this.getTokenText(6):this.mToken;
   }

   private int getCurrentTokenType() {
      return this.mTokenType;
   }

   private String nextToken() {
      this.mToken = this.mLexer.next();
      this.mTokenType = this.mLexer.getTokenType();
      return this.mToken;
   }

   private void match(int tokenType) throws ParseException {
      if(this.mTokenType != tokenType) {
         throw new ParseException("Expected:" + this.getTokenText(tokenType) + " found " + this.getTokenText(this.mTokenType), 0);
      } else {
         if(this.mTokenType != 6) {
            this.nextToken();
         }

      }
   }

   public void addListener(SecurityAccessParserListener lister) {
      this.mListener = lister;
   }
}
