// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.TokenStream;

class ExcelFormulaParser$DFA11 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaParser this$0;


   public ExcelFormulaParser$DFA11(ExcelFormulaParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 11;
      this.eot = ExcelFormulaParser.DFA11_eot;
      this.eof = ExcelFormulaParser.DFA11_eof;
      this.min = ExcelFormulaParser.DFA11_min;
      this.max = ExcelFormulaParser.DFA11_max;
      this.accept = ExcelFormulaParser.DFA11_accept;
      this.special = ExcelFormulaParser.DFA11_special;
      this.transition = ExcelFormulaParser.DFA11_transition;
   }

   public String getDescription() {
      return "383:1: unary_expression : ( \'-\' union_expression -> ^( UNARY_MINUS union_expression ) | \'+\' union_expression | union_expression );";
   }

   public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
      TokenStream input = (TokenStream)_input;
      boolean s1;
      switch(s) {
      case 0:
         int nvae = input.LA(1);
         int index11_5 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred16_ExcelFormula()) {
            s = 4;
         } else {
            s = 3;
         }

         input.seek(index11_5);
         if(s >= 0) {
            return s;
         }
         break;
      case 1:
         int LA11_9 = input.LA(1);
         int index11_9 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred17_ExcelFormula()) {
            s = 7;
         } else {
            s = 3;
         }

         input.seek(index11_9);
         if(s >= 0) {
            return s;
         }
         break;
      case 2:
         int LA11_8 = input.LA(1);
         int index11_8 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred17_ExcelFormula()) {
            s = 7;
         } else {
            s = 3;
         }

         input.seek(index11_8);
         if(s >= 0) {
            return s;
         }
         break;
      case 3:
         int LA11_6 = input.LA(1);
         int index11_6 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred16_ExcelFormula()) {
            s = 4;
         } else {
            s = 3;
         }

         input.seek(index11_6);
         if(s >= 0) {
            return s;
         }
      }

      if(ExcelFormulaParser.accessMethod400(this.this$0).backtracking > 0) {
         ExcelFormulaParser.accessMethod500(this.this$0).failed = true;
         return -1;
      } else {
         NoViableAltException nvae1 = new NoViableAltException(this.getDescription(), 11, s, input);
         this.error(nvae1);
         throw nvae1;
      }
   }
}
