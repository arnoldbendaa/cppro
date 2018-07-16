// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaParser$DFA12 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaParser this$0;


   public ExcelFormulaParser$DFA12(ExcelFormulaParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 12;
      this.eot = ExcelFormulaParser.DFA12_eot;
      this.eof = ExcelFormulaParser.DFA12_eof;
      this.min = ExcelFormulaParser.DFA12_min;
      this.max = ExcelFormulaParser.DFA12_max;
      this.accept = ExcelFormulaParser.DFA12_accept;
      this.special = ExcelFormulaParser.DFA12_special;
      this.transition = ExcelFormulaParser.DFA12_transition;
   }

   public String getDescription() {
      return "391:1: reference_expression : ( cell_reference | function_call | primitive | const_array | area_reference | \'(\' primary_expression \')\' -> ^( BRACKET_EXPR primary_expression ) );";
   }
}
