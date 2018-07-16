// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaTreeParser$DFA2 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaTreeParser this$0;


   public ExcelFormulaTreeParser$DFA2(ExcelFormulaTreeParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 2;
      this.eot = ExcelFormulaTreeParser.DFA2_eot;
      this.eof = ExcelFormulaTreeParser.DFA2_eof;
      this.min = ExcelFormulaTreeParser.DFA2_min;
      this.max = ExcelFormulaTreeParser.DFA2_max;
      this.accept = ExcelFormulaTreeParser.DFA2_accept;
      this.special = ExcelFormulaTreeParser.DFA2_special;
      this.transition = ExcelFormulaTreeParser.DFA2_transition;
   }

   public String getDescription() {
      return "59:1: primary_expression : ( logical_expression | concat_expression | additive_expression | multiplicative_expression | exponentiation_expression | unary_expression | reference_expression | bracketed_expression | percent_expression );";
   }
}
