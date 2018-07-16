// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;

class ExcelFormulaParser$DFA16 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaParser this$0;


   public ExcelFormulaParser$DFA16(ExcelFormulaParser var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 16;
      this.eot = ExcelFormulaParser.DFA16_eot;
      this.eof = ExcelFormulaParser.DFA16_eof;
      this.min = ExcelFormulaParser.DFA16_min;
      this.max = ExcelFormulaParser.DFA16_max;
      this.accept = ExcelFormulaParser.DFA16_accept;
      this.special = ExcelFormulaParser.DFA16_special;
      this.transition = ExcelFormulaParser.DFA16_transition;
   }

   public String getDescription() {
      return "413:1: area_reference : ( range ( ( \',\' | \' \' ) area_reference )* | vector ( ( \',\' | \' \' ) area_reference )* );";
   }
}
