// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.datatype.VirtualExprLexer;
import com.cedar.cp.dto.datatype.VirtualExprLexer$Token;
import java.util.ArrayList;
import java.util.List;

public class VirtualExprParser {

   private List mIDs = new ArrayList();
   private List mFunctions = new ArrayList();
   private VirtualExprLexer$Token mCurrentToken;
   private VirtualExprLexer mLexer;


   public static void main(String[] args) throws Exception {
      new VirtualExprParser("AB+XX");
   }

   public VirtualExprParser(String expr) throws ValidationException {
      this.mLexer = new VirtualExprLexer(expr);
      this.additiveExpr();
      if(this.currentToken() != null) {
         throw new ValidationException("Unexpected additional token:\'" + this.currentToken().getStr() + "\'");
      }
   }

   private boolean isDelimTypeOf(VirtualExprLexer$Token t, String types) {
      return t == null?false:t.getType() == 0 && types.indexOf(t.getStr().charAt(0)) != -1;
   }

   private void additiveExpr() throws ValidationException {
      this.mulsExpr();

      while(this.isDelimTypeOf(this.currentToken(), "-+")) {
         this.matchDelim("-+");
         this.mulsExpr();
      }

   }

   private void mulsExpr() throws ValidationException {
      this.atom();

      while(this.isDelimTypeOf(this.currentToken(), "*/")) {
         this.matchDelim("/*");
         this.atom();
      }

   }

   private void atom() throws ValidationException {
      if(this.currentToken() == null) {
         this.nextToken();
      }

      if(this.currentToken() == null) {
         throw new ValidationException("Unexpected end of expression");
      } else {
         switch(this.currentToken().getType()) {
         case 0:
            if(!this.isDelimTypeOf(this.currentToken(), "(")) {
               throw new ValidationException("Unexpected delimter:" + this.currentToken().getStr());
            }

            this.bracketedExpr();
            break;
         case 1:
            String id = this.currentToken().getStr();
            this.nextToken();
            if(this.isDelimTypeOf(this.currentToken(), "(")) {
               this.mFunctions.add(id);
               this.matchDelim("(");
               this.additiveExpr();
               if(!this.isDelimTypeOf(this.currentToken(), ")")) {
                  throw new ValidationException("Missing closing \')\'");
               }

               this.matchDelim(")");
            } else {
               this.mIDs.add(id);
            }
            break;
         case 2:
            this.nextToken();
         }

      }
   }

   private void bracketedExpr() throws ValidationException {
      this.matchDelim("(");
      this.additiveExpr();
      if(!this.isDelimTypeOf(this.currentToken(), ")")) {
         throw new ValidationException("Missing closing \')\'");
      } else {
         this.matchDelim(")");
      }
   }

   private void matchDelim(String delims) throws ValidationException {
      if(this.currentToken() == null) {
         throw new ValidationException("Expected delimters:" + delims);
      } else {
         this.nextToken();
      }
   }

   private VirtualExprLexer$Token nextToken() {
      return this.mCurrentToken = this.mLexer.nextToken();
   }

   private VirtualExprLexer$Token currentToken() {
      return this.mCurrentToken;
   }

   public List getIDs() {
      return this.mIDs;
   }

   public List getFunctions() {
      return this.mFunctions;
   }
}
