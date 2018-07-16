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

class ExcelFormulaTreeParser$DFA6 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaTreeParser this$0;


   public ExcelFormulaTreeParser$DFA6(ExcelFormulaTreeParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 6;
      this.eot = ExcelFormulaTreeParser.DFA6_eot;
      this.eof = ExcelFormulaTreeParser.DFA6_eof;
      this.min = ExcelFormulaTreeParser.DFA6_min;
      this.max = ExcelFormulaTreeParser.DFA6_max;
      this.accept = ExcelFormulaTreeParser.DFA6_accept;
      this.special = ExcelFormulaTreeParser.DFA6_special;
      this.transition = ExcelFormulaTreeParser.DFA6_transition;
   }

   public String getDescription() {
      return "213:1: reference_expression : ( primitive | const_array | cell_reference | cell_range_reference | column_reference | column_range_reference | named_range_reference | worksheet_named_range_reference | r1c1_reference | function_call | if_statement );";
   }

   public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
      TreeNodeStream input = (TreeNodeStream)_input;
      switch(s) {
      case 0:
         int nvae = input.LA(1);
         int index6_19 = input.index();
         input.rewind();
         s = -1;
         if(this.this$0.synpred24_ExcelFormulaTreeParser()) {
            s = 20;
         } else if(this.this$0.synpred25_ExcelFormulaTreeParser()) {
            s = 21;
         }

         input.seek(index6_19);
         if(s >= 0) {
            return s;
         }
      default:
         if(ExcelFormulaTreeParser.accessMethod000(this.this$0).backtracking > 0) {
            ExcelFormulaTreeParser.accessMethod100(this.this$0).failed = true;
            return -1;
         } else {
            NoViableAltException nvae1 = new NoViableAltException(this.getDescription(), 6, s, input);
            this.error(nvae1);
            throw nvae1;
         }
      }
   }
}
