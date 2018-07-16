// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaLexer$DFA1 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaLexer this$0;


   public ExcelFormulaLexer$DFA1(ExcelFormulaLexer var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 1;
      this.eot = ExcelFormulaLexer.DFA1_eot;
      this.eof = ExcelFormulaLexer.DFA1_eof;
      this.min = ExcelFormulaLexer.DFA1_min;
      this.max = ExcelFormulaLexer.DFA1_max;
      this.accept = ExcelFormulaLexer.DFA1_accept;
      this.special = ExcelFormulaLexer.DFA1_special;
      this.transition = ExcelFormulaLexer.DFA1_transition;
   }

   public String getDescription() {
      return "543:1: Error : ( \'#div/0!\' | \'#n/a\' | \'#N/A\' | \'#name?\' | \'#null!\' | \'#num!\' | \'#ref!\' | \'#value!\' | \'#####\' );";
   }
}
