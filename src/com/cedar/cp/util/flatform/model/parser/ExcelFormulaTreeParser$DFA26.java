// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaTreeParser$DFA26 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaTreeParser this$0;


   public ExcelFormulaTreeParser$DFA26(ExcelFormulaTreeParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 26;
      this.eot = ExcelFormulaTreeParser.DFA26_eot;
      this.eof = ExcelFormulaTreeParser.DFA26_eof;
      this.min = ExcelFormulaTreeParser.DFA26_min;
      this.max = ExcelFormulaTreeParser.DFA26_max;
      this.accept = ExcelFormulaTreeParser.DFA26_accept;
      this.special = ExcelFormulaTreeParser.DFA26_special;
      this.transition = ExcelFormulaTreeParser.DFA26_transition;
   }

   public String getDescription() {
      return "370:9: ( argument_list )?";
   }
}
