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

class ExcelFormulaTreeParser$DFA22 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaTreeParser this$0;


   public ExcelFormulaTreeParser$DFA22(ExcelFormulaTreeParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 22;
      this.eot = ExcelFormulaTreeParser.DFA22_eot;
      this.eof = ExcelFormulaTreeParser.DFA22_eof;
      this.min = ExcelFormulaTreeParser.DFA22_min;
      this.max = ExcelFormulaTreeParser.DFA22_max;
      this.accept = ExcelFormulaTreeParser.DFA22_accept;
      this.special = ExcelFormulaTreeParser.DFA22_special;
      this.transition = ExcelFormulaTreeParser.DFA22_transition;
   }

   public String getDescription() {
      return "335:1: r1c1_ref_col : ( \'c\' r1c1_ref_offset | \'c\' );";
   }

   public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
      TreeNodeStream input = (TreeNodeStream)_input;
      switch(s) {
      case 0:
         int nvae = input.LA(1);
         int index22_3 = input.index();
         input.rewind();
         boolean s1 = true;
         if(this.this$0.synpred47_ExcelFormulaTreeParser()) {
            s = 2;
         } else {
            s = 4;
         }

         input.seek(index22_3);
         if(s >= 0) {
            return s;
         }
      default:
         if(ExcelFormulaTreeParser.accessMethod200(this.this$0).backtracking > 0) {
            ExcelFormulaTreeParser.accessMethod300(this.this$0).failed = true;
            return -1;
         } else {
            NoViableAltException nvae1 = new NoViableAltException(this.getDescription(), 22, s, input);
            this.error(nvae1);
            throw nvae1;
         }
      }
   }
}
