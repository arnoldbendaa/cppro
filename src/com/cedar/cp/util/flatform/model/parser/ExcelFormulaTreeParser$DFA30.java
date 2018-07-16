// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaTreeParser$DFA30 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaTreeParser this$0;


   public ExcelFormulaTreeParser$DFA30(ExcelFormulaTreeParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 30;
      this.eot = ExcelFormulaTreeParser.DFA30_eot;
      this.eof = ExcelFormulaTreeParser.DFA30_eof;
      this.min = ExcelFormulaTreeParser.DFA30_min;
      this.max = ExcelFormulaTreeParser.DFA30_max;
      this.accept = ExcelFormulaTreeParser.DFA30_accept;
      this.special = ExcelFormulaTreeParser.DFA30_special;
      this.transition = ExcelFormulaTreeParser.DFA30_transition;
   }

   public String getDescription() {
      return "449:1: primitive : ( ( \'+\' | \'-\' )? dl= DecimalLiteral | ( \'+\' | \'-\' )? fpl= FloatingPointLiteral | bc= BooleanConstant | cl= CharacterLiteral | sl= StringLiteral | Error );";
   }
}
