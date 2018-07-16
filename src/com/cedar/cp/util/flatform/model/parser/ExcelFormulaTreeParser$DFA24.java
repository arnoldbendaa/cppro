// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaTreeParser$DFA24 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaTreeParser this$0;


   public ExcelFormulaTreeParser$DFA24(ExcelFormulaTreeParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 24;
      this.eot = ExcelFormulaTreeParser.DFA24_eot;
      this.eof = ExcelFormulaTreeParser.DFA24_eof;
      this.min = ExcelFormulaTreeParser.DFA24_min;
      this.max = ExcelFormulaTreeParser.DFA24_max;
      this.accept = ExcelFormulaTreeParser.DFA24_accept;
      this.special = ExcelFormulaTreeParser.DFA24_special;
      this.transition = ExcelFormulaTreeParser.DFA24_transition;
   }

   public String getDescription() {
      return "()+ loopback of 344:26: ( array_row )+";
   }
}
