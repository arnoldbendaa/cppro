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

class ExcelFormulaParser$DFA10 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaParser this$0;


   public ExcelFormulaParser$DFA10(ExcelFormulaParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 10;
      this.eot = ExcelFormulaParser.DFA10_eot;
      this.eof = ExcelFormulaParser.DFA10_eof;
      this.min = ExcelFormulaParser.DFA10_min;
      this.max = ExcelFormulaParser.DFA10_max;
      this.accept = ExcelFormulaParser.DFA10_accept;
      this.special = ExcelFormulaParser.DFA10_special;
      this.transition = ExcelFormulaParser.DFA10_transition;
   }

   public String getDescription() {
      return "380:1: percent_expression : ( unary_expression \'%\' -> ^( \'%\' unary_expression ) | unary_expression );";
   }

   public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
      TokenStream input = (TokenStream)_input;
      boolean s1;
      switch(s) {
      case 0:
         int nvae = input.LA(1);
         int index10_1 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_1);
         if(s >= 0) {
            return s;
         }
         break;
      case 1:
         int LA10_2 = input.LA(1);
         int index10_2 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_2);
         if(s >= 0) {
            return s;
         }
         break;
      case 2:
         int LA10_3 = input.LA(1);
         int index10_3 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_3);
         if(s >= 0) {
            return s;
         }
         break;
      case 3:
         int LA10_4 = input.LA(1);
         int index10_4 = input.index();
         input.rewind();
         s1 = true;
         if((!this.this$0.synpred15_ExcelFormula() || !ExcelFormulaParser.accessMethod000(this.this$0, input.LT(1).getText() + input.LT(2).getText())) && (!this.this$0.synpred15_ExcelFormula() || !ExcelFormulaParser.accessMethod100(this.this$0, input.LT(1).getText())) && (!this.this$0.synpred15_ExcelFormula() || !ExcelFormulaParser.accessMethod000(this.this$0, input.LT(1).getText())) && (!this.this$0.synpred15_ExcelFormula() || !ExcelFormulaParser.accessMethod100(this.this$0, input.LT(1).getText())) && !this.this$0.synpred15_ExcelFormula() && (!this.this$0.synpred15_ExcelFormula() || !ExcelFormulaParser.accessMethod100(this.this$0, input.LT(1).getText())) && (!this.this$0.synpred15_ExcelFormula() || !ExcelFormulaParser.accessMethod100(this.this$0, input.LT(1).getText())) && (!this.this$0.synpred15_ExcelFormula() || !ExcelFormulaParser.accessMethod100(this.this$0, input.LT(1).getText()))) {
            s = 16;
         } else {
            s = 15;
         }

         input.seek(index10_4);
         if(s >= 0) {
            return s;
         }
         break;
      case 4:
         int LA10_5 = input.LA(1);
         int index10_5 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_5);
         if(s >= 0) {
            return s;
         }
         break;
      case 5:
         int LA10_6 = input.LA(1);
         int index10_6 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_6);
         if(s >= 0) {
            return s;
         }
         break;
      case 6:
         int LA10_7 = input.LA(1);
         int index10_7 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_7);
         if(s >= 0) {
            return s;
         }
         break;
      case 7:
         int LA10_8 = input.LA(1);
         int index10_8 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_8);
         if(s >= 0) {
            return s;
         }
         break;
      case 8:
         int LA10_9 = input.LA(1);
         int index10_9 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_9);
         if(s >= 0) {
            return s;
         }
         break;
      case 9:
         int LA10_10 = input.LA(1);
         int index10_10 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_10);
         if(s >= 0) {
            return s;
         }
         break;
      case 10:
         int LA10_11 = input.LA(1);
         int index10_11 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_11);
         if(s >= 0) {
            return s;
         }
         break;
      case 11:
         int LA10_12 = input.LA(1);
         int index10_12 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_12);
         if(s >= 0) {
            return s;
         }
         break;
      case 12:
         int LA10_13 = input.LA(1);
         int index10_13 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_13);
         if(s >= 0) {
            return s;
         }
         break;
      case 13:
         int LA10_14 = input.LA(1);
         int index10_14 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred15_ExcelFormula()) {
            s = 15;
         } else {
            s = 16;
         }

         input.seek(index10_14);
         if(s >= 0) {
            return s;
         }
      }

      if(ExcelFormulaParser.accessMethod200(this.this$0).backtracking > 0) {
         ExcelFormulaParser.accessMethod300(this.this$0).failed = true;
         return -1;
      } else {
         NoViableAltException nvae1 = new NoViableAltException(this.getDescription(), 10, s, input);
         this.error(nvae1);
         throw nvae1;
      }
   }
}
