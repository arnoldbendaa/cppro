// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaLexer$DFA19 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaLexer this$0;


   public ExcelFormulaLexer$DFA19(ExcelFormulaLexer var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 19;
      this.eot = ExcelFormulaLexer.DFA19_eot;
      this.eof = ExcelFormulaLexer.DFA19_eof;
      this.min = ExcelFormulaLexer.DFA19_min;
      this.max = ExcelFormulaLexer.DFA19_max;
      this.accept = ExcelFormulaLexer.DFA19_accept;
      this.special = ExcelFormulaLexer.DFA19_special;
      this.transition = ExcelFormulaLexer.DFA19_transition;
   }

   public String getDescription() {
      return "570:1: FloatingPointLiteral : ( ( \'0\' .. \'9\' )+ \'.\' ( \'0\' .. \'9\' )* ( Exponent )? ( FloatTypeSuffix )? | \'.\' ( \'0\' .. \'9\' )+ ( Exponent )? ( FloatTypeSuffix )? | ( \'0\' .. \'9\' )+ ( Exponent )? FloatTypeSuffix );";
   }
}
