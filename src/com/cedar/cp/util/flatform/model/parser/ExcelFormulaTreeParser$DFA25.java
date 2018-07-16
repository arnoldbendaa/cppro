// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.tree.TreeNodeStream;

class ExcelFormulaTreeParser$DFA25 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaTreeParser this$0;


   public ExcelFormulaTreeParser$DFA25(ExcelFormulaTreeParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 25;
      this.eot = ExcelFormulaTreeParser.DFA25_eot;
      this.eof = ExcelFormulaTreeParser.DFA25_eof;
      this.min = ExcelFormulaTreeParser.DFA25_min;
      this.max = ExcelFormulaTreeParser.DFA25_max;
      this.accept = ExcelFormulaTreeParser.DFA25_accept;
      this.special = ExcelFormulaTreeParser.DFA25_special;
      this.transition = ExcelFormulaTreeParser.DFA25_transition;
   }

   public String getDescription() {
      return "()+ loopback of 348:22: ( array_element )+";
   }

   public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
      TreeNodeStream input = (TreeNodeStream)_input;
      boolean s1;
      switch(s) {
      case 0:
         int nvae = input.LA(1);
         int index25_16 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_16);
         if(s >= 0) {
            return s;
         }
         break;
      case 1:
         int LA25_17 = input.LA(1);
         int index25_17 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_17);
         if(s >= 0) {
            return s;
         }
         break;
      case 2:
         int LA25_18 = input.LA(1);
         int index25_18 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_18);
         if(s >= 0) {
            return s;
         }
         break;
      case 3:
         int LA25_19 = input.LA(1);
         int index25_19 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_19);
         if(s >= 0) {
            return s;
         }
         break;
      case 4:
         int LA25_20 = input.LA(1);
         int index25_20 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_20);
         if(s >= 0) {
            return s;
         }
         break;
      case 5:
         int LA25_21 = input.LA(1);
         int index25_21 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_21);
         if(s >= 0) {
            return s;
         }
         break;
      case 6:
         int LA25_40 = input.LA(1);
         int index25_40 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_40);
         if(s >= 0) {
            return s;
         }
         break;
      case 7:
         int LA25_41 = input.LA(1);
         int index25_41 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_41);
         if(s >= 0) {
            return s;
         }
         break;
      case 8:
         int LA25_43 = input.LA(1);
         int index25_43 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_43);
         if(s >= 0) {
            return s;
         }
         break;
      case 9:
         int LA25_44 = input.LA(1);
         int index25_44 = input.index();
         input.rewind();
         s1 = true;
         if(this.this$0.synpred52_ExcelFormulaTreeParser()) {
            s = 83;
         } else {
            s = 1;
         }

         input.seek(index25_44);
         if(s >= 0) {
            return s;
         }
      }

      if(ExcelFormulaTreeParser.accessMethod400(this.this$0).backtracking > 0) {
         ExcelFormulaTreeParser.accessMethod500(this.this$0).failed = true;
         return -1;
      } else {
         NoViableAltException nvae1 = new NoViableAltException(this.getDescription(), 25, s, input);
         this.error(nvae1);
         throw nvae1;
      }
   }
}
