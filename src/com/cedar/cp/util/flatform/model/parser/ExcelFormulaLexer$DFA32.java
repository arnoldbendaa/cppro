// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.DFA;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.NoViableAltException;

class ExcelFormulaLexer$DFA32 extends DFA {

   // $FF: synthetic field
   final ExcelFormulaLexer this$0;


   public ExcelFormulaLexer$DFA32(ExcelFormulaLexer var1, BaseRecognizer recognizer) {
      this.this$0 = var1;
      this.recognizer = recognizer;
      this.decisionNumber = 32;
      this.eot = ExcelFormulaLexer.DFA32_eot;
      this.eof = ExcelFormulaLexer.DFA32_eof;
      this.min = ExcelFormulaLexer.DFA32_min;
      this.max = ExcelFormulaLexer.DFA32_max;
      this.accept = ExcelFormulaLexer.DFA32_accept;
      this.special = ExcelFormulaLexer.DFA32_special;
      this.transition = ExcelFormulaLexer.DFA32_transition;
   }

   public String getDescription() {
      return "1:1: Tokens : ( T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | Error | BooleanConstant | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | CharacterLiteral | CharacterSequence | StringLiteral | WS | Identifier | COMMENT | LINE_COMMENT );";
   }

   public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
      switch(s) {
      case 0:
         int nvae = _input.LA(1);
         s = -1;
         if(nvae >= 48 && nvae <= 55) {
            s = 68;
         } else if(nvae == 39) {
            s = 64;
         } else if(nvae >= 0 && nvae <= 38 || nvae >= 40 && nvae <= 47 || nvae >= 56 && nvae <= '\uffff') {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 1:
         int LA32_29 = _input.LA(1);
         s = -1;
         if(LA32_29 == 92) {
            s = 52;
         } else if((LA32_29 < 0 || LA32_29 > 38) && (LA32_29 < 40 || LA32_29 > 91) && (LA32_29 < 93 || LA32_29 > '\uffff')) {
            if(LA32_29 == 39) {
               s = 54;
            }
         } else {
            s = 53;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 2:
         int LA32_63 = _input.LA(1);
         s = -1;
         if(LA32_63 >= 48 && LA32_63 <= 55) {
            s = 69;
         } else if(LA32_63 == 39) {
            s = 64;
         } else if(LA32_63 >= 0 && LA32_63 <= 38 || LA32_63 >= 40 && LA32_63 <= 47 || LA32_63 >= 56 && LA32_63 <= '\uffff') {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 3:
         int LA32_53 = _input.LA(1);
         s = -1;
         if(LA32_53 == 39) {
            s = 64;
         } else if(LA32_53 >= 0 && LA32_53 <= 38 || LA32_53 >= 40 && LA32_53 <= '\uffff') {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 4:
         int LA32_74 = _input.LA(1);
         s = -1;
         if(LA32_74 == 39) {
            s = 64;
         } else if(LA32_74 >= 0 && LA32_74 <= 38 || LA32_74 >= 40 && LA32_74 <= '\uffff') {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 5:
         int LA32_68 = _input.LA(1);
         s = -1;
         if(LA32_68 == 39) {
            s = 64;
         } else if((LA32_68 < 0 || LA32_68 > 38) && (LA32_68 < 40 || LA32_68 > 47) && (LA32_68 < 56 || LA32_68 > '\uffff')) {
            if(LA32_68 >= 48 && LA32_68 <= 55) {
               s = 74;
            }
         } else {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 6:
         int LA32_69 = _input.LA(1);
         s = -1;
         if(LA32_69 == 39) {
            s = 64;
         } else if(LA32_69 >= 0 && LA32_69 <= 38 || LA32_69 >= 40 && LA32_69 <= '\uffff') {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 7:
         int LA32_60 = _input.LA(1);
         s = -1;
         if(LA32_60 == 39) {
            s = 64;
         } else if(LA32_60 >= 0 && LA32_60 <= 38 || LA32_60 >= 40 && LA32_60 <= '\uffff') {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
         break;
      case 8:
         int LA32_76 = _input.LA(1);
         s = -1;
         if(LA32_76 == 39) {
            s = 64;
         } else if(LA32_76 >= 0 && LA32_76 <= 38 || LA32_76 >= 40 && LA32_76 <= '\uffff') {
            s = 54;
         }

         if(s >= 0) {
            return s;
         }
      }

      NoViableAltException nvae1 = new NoViableAltException(this.getDescription(), 32, s, _input);
      this.error(nvae1);
      throw nvae1;
   }
}
