// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer$DFA1;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer$DFA19;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer$DFA32;
import com.cedar.cp.util.flatform.model.parser.FunctionValidator;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class ExcelFormulaLexer extends Lexer {

   public static final int T__68 = 68;
   public static final int CELL_RANGE_REF = 17;
   public static final int T__69 = 69;
   public static final int T__66 = 66;
   public static final int T__67 = 67;
   public static final int T__64 = 64;
   public static final int T__65 = 65;
   public static final int ARRAY_FORMULA = 5;
   public static final int T__62 = 62;
   public static final int T__63 = 63;
   public static final int NAMED_RANGE = 24;
   public static final int FloatTypeSuffix = 39;
   public static final int OctalLiteral = 36;
   public static final int CharacterLiteral = 31;
   public static final int CharacterSequence = 25;
   public static final int ROW_REF = 16;
   public static final int Exponent = 38;
   public static final int WORKSHEET_CELL_REF = 13;
   public static final int IF_FUNCTION = 19;
   public static final int T__61 = 61;
   public static final int EOF = -1;
   public static final int T__60 = 60;
   public static final int HexDigit = 33;
   public static final int Identifier = 26;
   public static final int LOGICAL_EXPR = 7;
   public static final int WORKSHEET_COLUMN_REF = 15;
   public static final int T__55 = 55;
   public static final int T__56 = 56;
   public static final int T__57 = 57;
   public static final int T__58 = 58;
   public static final int T__51 = 51;
   public static final int T__52 = 52;
   public static final int T__53 = 53;
   public static final int T__54 = 54;
   public static final int T__59 = 59;
   public static final int COLUMN_RANGE_REF = 18;
   public static final int COMMENT = 45;
   public static final int COLUMN_REF = 14;
   public static final int T__50 = 50;
   public static final int SHEET_REF = 21;
   public static final int ADDITIVE_EXPR = 9;
   public static final int HexLiteral = 35;
   public static final int T__47 = 47;
   public static final int LINE_COMMENT = 46;
   public static final int SCALAR_FORMULA = 4;
   public static final int IntegerTypeSuffix = 34;
   public static final int RC_CELL_REF = 11;
   public static final int T__48 = 48;
   public static final int T__49 = 49;
   public static final int UNARY_MINUS = 20;
   public static final int DecimalLiteral = 27;
   public static final int ARRAY_ROW = 23;
   public static final int DecDigit = 37;
   public static final int CONCAT_EXPR = 8;
   public static final int StringLiteral = 32;
   public static final int T__71 = 71;
   public static final int WS = 43;
   public static final int T__72 = 72;
   public static final int CELL_REF = 12;
   public static final int T__70 = 70;
   public static final int UnicodeEscape = 41;
   public static final int FloatingPointLiteral = 29;
   public static final int ARRAY_CONST = 22;
   public static final int Error = 28;
   public static final int BooleanConstant = 30;
   public static final int BRACKET_EXPR = 6;
   public static final int FUNCTION_CALL = 10;
   public static final int EscapeSequence = 40;
   public static final int OctalEscape = 42;
   public static final int Letter = 44;
   private FunctionValidator mFunctionValidator;
   protected ExcelFormulaLexer$DFA1 dfa1;
   protected ExcelFormulaLexer$DFA19 dfa19;
   protected ExcelFormulaLexer$DFA32 dfa32;
   static final String DFA1_eotS = "\r￿";
   static final String DFA1_eofS = "\r￿";
   static final String DFA1_minS = "#￿/￿l￿";
   static final String DFA1_maxS = "#v￿u￿m￿";
   static final String DFA1_acceptS = "￿￿\b\t￿";
   static final String DFA1_specialS = "\r￿}>";
   static final String[] DFA1_transitionS = new String[]{"", "*￿￿\t￿￿￿", "", "\b1￿\t￿\n", "", "", "", "", "", "", "\f", "", ""};
   static final short[] DFA1_eot = DFA.unpackEncodedString("\r￿");
   static final short[] DFA1_eof = DFA.unpackEncodedString("\r￿");
   static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars("#￿/￿l￿");
   static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars("#v￿u￿m￿");
   static final short[] DFA1_accept = DFA.unpackEncodedString("￿￿\b\t￿");
   static final short[] DFA1_special = DFA.unpackEncodedString("\r￿}>");
   static final short[][] DFA1_transition;
   static final String DFA19_eotS = "￿";
   static final String DFA19_eofS = "￿";
   static final String DFA19_minS = ".￿";
   static final String DFA19_maxS = "9f￿";
   static final String DFA19_acceptS = "￿";
   static final String DFA19_specialS = "￿}>";
   static final String[] DFA19_transitionS;
   static final short[] DFA19_eot;
   static final short[] DFA19_eof;
   static final char[] DFA19_min;
   static final char[] DFA19_max;
   static final short[] DFA19_accept;
   static final short[] DFA19_special;
   static final short[][] DFA19_transition;
   static final String DFA32_eotS = "￿#%\'￿*\t￿ ￿ 2￿78 ￿;￿2￿ ￿G ￿G￿";
   static final String DFA32_eofS = "M￿";
   static final String DFA32_minS = "\t￿=￿*\t￿fF￿RA.￿ ￿AUL￿.￿.\" ￿ES￿ 0 ￿AE0 ￿A0 0 ";
   static final String DFA32_maxS = "}￿>=￿/\t￿fF￿RAxf￿￿￿zUL￿f￿fu￿￿ES￿￿f￿￿zEf￿￿zf￿f￿";
   static final String DFA32_acceptS = "￿￿\n\f\r￿￿￿ ￿#$%\b\t&\'￿￿￿\"￿￿!￿!￿";
   static final String DFA32_specialS = "￿￿￿￿ ￿￿￿\b}>";
   static final String[] DFA32_transitionS;
   static final short[] DFA32_eot;
   static final short[] DFA32_eof;
   static final char[] DFA32_min;
   static final char[] DFA32_max;
   static final short[] DFA32_accept;
   static final short[] DFA32_special;
   static final short[][] DFA32_transition;


   private boolean isFunction(String functionName) {
      return this.mFunctionValidator != null?this.mFunctionValidator.isValidFunction(functionName):functionName.equalsIgnoreCase("days360");
   }

   public void setFunctionValidator(FunctionValidator functionValidator) {
      this.mFunctionValidator = functionValidator;
   }

   public ExcelFormulaLexer() {
      this.dfa1 = new ExcelFormulaLexer$DFA1(this, this);
      this.dfa19 = new ExcelFormulaLexer$DFA19(this, this);
      this.dfa32 = new ExcelFormulaLexer$DFA32(this, this);
   }

   public ExcelFormulaLexer(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public ExcelFormulaLexer(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.dfa1 = new ExcelFormulaLexer$DFA1(this, this);
      this.dfa19 = new ExcelFormulaLexer$DFA19(this, this);
      this.dfa32 = new ExcelFormulaLexer$DFA32(this, this);
   }

   public String getGrammarFileName() {
      return "C:\\projects\\cp6\\cp\\util\\src\\java\\com\\cedar\\cp\\util\\flatform\\model\\parser\\ExcelFormula.g";
   }

   public final void mT__47() throws RecognitionException {
      try {
         byte _type = 47;
         byte _channel = 0;
         this.match(61);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__48() throws RecognitionException {
      try {
         byte _type = 48;
         byte _channel = 0;
         this.match(123);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__49() throws RecognitionException {
      try {
         byte _type = 49;
         byte _channel = 0;
         this.match(125);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__50() throws RecognitionException {
      try {
         byte _type = 50;
         byte _channel = 0;
         this.match(60);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__51() throws RecognitionException {
      try {
         byte _type = 51;
         byte _channel = 0;
         this.match("<=");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__52() throws RecognitionException {
      try {
         byte _type = 52;
         byte _channel = 0;
         this.match(62);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__53() throws RecognitionException {
      try {
         byte _type = 53;
         byte _channel = 0;
         this.match(">=");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__54() throws RecognitionException {
      try {
         byte _type = 54;
         byte _channel = 0;
         this.match("<>");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__55() throws RecognitionException {
      try {
         byte _type = 55;
         byte _channel = 0;
         this.match("!=");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__56() throws RecognitionException {
      try {
         byte _type = 56;
         byte _channel = 0;
         this.match(38);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__57() throws RecognitionException {
      try {
         byte _type = 57;
         byte _channel = 0;
         this.match(43);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__58() throws RecognitionException {
      try {
         byte _type = 58;
         byte _channel = 0;
         this.match(45);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__59() throws RecognitionException {
      try {
         byte _type = 59;
         byte _channel = 0;
         this.match(42);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__60() throws RecognitionException {
      try {
         byte _type = 60;
         byte _channel = 0;
         this.match(47);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__61() throws RecognitionException {
      try {
         byte _type = 61;
         byte _channel = 0;
         this.match(94);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__62() throws RecognitionException {
      try {
         byte _type = 62;
         byte _channel = 0;
         this.match(37);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__63() throws RecognitionException {
      try {
         byte _type = 63;
         byte _channel = 0;
         this.match(40);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__64() throws RecognitionException {
      try {
         byte _type = 64;
         byte _channel = 0;
         this.match(41);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__65() throws RecognitionException {
      try {
         byte _type = 65;
         byte _channel = 0;
         this.match(33);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__66() throws RecognitionException {
      try {
         byte _type = 66;
         byte _channel = 0;
         this.match(44);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__67() throws RecognitionException {
      try {
         byte _type = 67;
         byte _channel = 0;
         this.match(32);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__68() throws RecognitionException {
      try {
         byte _type = 68;
         byte _channel = 0;
         this.match(58);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__69() throws RecognitionException {
      try {
         byte _type = 69;
         byte _channel = 0;
         this.match(36);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__70() throws RecognitionException {
      try {
         byte _type = 70;
         byte _channel = 0;
         this.match(59);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__71() throws RecognitionException {
      try {
         byte _type = 71;
         byte _channel = 0;
         this.match("if");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__72() throws RecognitionException {
      try {
         byte _type = 72;
         byte _channel = 0;
         this.match("IF");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mError() throws RecognitionException {
      try {
         byte _type = 28;
         byte _channel = 0;
         boolean alt1 = true;
         int alt11 = this.dfa1.predict(this.input);
         switch(alt11) {
         case 1:
            this.match("#div/0!");
            break;
         case 2:
            this.match("#n/a");
            break;
         case 3:
            this.match("#N/A");
            break;
         case 4:
            this.match("#name?");
            break;
         case 5:
            this.match("#null!");
            break;
         case 6:
            this.match("#num!");
            break;
         case 7:
            this.match("#ref!");
            break;
         case 8:
            this.match("#value!");
            break;
         case 9:
            this.match("#####");
         }

         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mBooleanConstant() throws RecognitionException {
      try {
         byte _type = 30;
         byte _channel = 0;
         boolean alt2 = true;
         int LA2_0 = this.input.LA(1);
         byte alt21;
         if(LA2_0 == 84) {
            alt21 = 1;
         } else {
            if(LA2_0 != 70) {
               NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
               throw nvae;
            }

            alt21 = 2;
         }

         switch(alt21) {
         case 1:
            this.match("TRUE");
            break;
         case 2:
            this.match("FALSE");
         }

         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mHexLiteral() throws RecognitionException {
      try {
         byte _type = 35;
         byte _channel = 0;
         this.match(48);
         if(this.input.LA(1) != 88 && this.input.LA(1) != 120) {
            MismatchedSetException var10 = new MismatchedSetException((BitSet)null, this.input);
            this.recover(var10);
            throw var10;
         } else {
            this.input.consume();
            int cnt3 = 0;

            while(true) {
               byte alt4 = 2;
               int LA4_0 = this.input.LA(1);
               if(LA4_0 >= 48 && LA4_0 <= 57 || LA4_0 >= 65 && LA4_0 <= 70 || LA4_0 >= 97 && LA4_0 <= 102) {
                  alt4 = 1;
               }

               switch(alt4) {
               case 1:
                  this.mHexDigit();
                  ++cnt3;
                  break;
               default:
                  if(cnt3 < 1) {
                     EarlyExitException eee = new EarlyExitException(3, this.input);
                     throw eee;
                  }

                  alt4 = 2;
                  LA4_0 = this.input.LA(1);
                  if(LA4_0 == 76 || LA4_0 == 108) {
                     alt4 = 1;
                  }

                  switch(alt4) {
                  case 1:
                     this.mIntegerTypeSuffix();
                  default:
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mDecimalLiteral() throws RecognitionException {
      try {
         byte _type = 27;
         byte _channel = 0;
         boolean alt6 = true;
         int LA6_0 = this.input.LA(1);
         byte alt61;
         if(LA6_0 == 48) {
            alt61 = 1;
         } else {
            if(LA6_0 < 49 || LA6_0 > 57) {
               NoViableAltException alt71 = new NoViableAltException("", 6, 0, this.input);
               throw alt71;
            }

            alt61 = 2;
         }

         byte alt7;
         int LA7_0;
         switch(alt61) {
         case 1:
            this.match(48);
            break;
         case 2:
            this.matchRange(49, 57);

            label97:
            while(true) {
               alt7 = 2;
               LA7_0 = this.input.LA(1);
               if(LA7_0 >= 48 && LA7_0 <= 57) {
                  alt7 = 1;
               }

               switch(alt7) {
               case 1:
                  this.matchRange(48, 57);
                  break;
               default:
                  break label97;
               }
            }
         }

         alt7 = 2;
         LA7_0 = this.input.LA(1);
         if(LA7_0 == 76 || LA7_0 == 108) {
            alt7 = 1;
         }

         switch(alt7) {
         case 1:
            this.mIntegerTypeSuffix();
         default:
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mOctalLiteral() throws RecognitionException {
      try {
         byte _type = 36;
         byte _channel = 0;
         this.match(48);
         int cnt8 = 0;

         while(true) {
            byte alt9 = 2;
            int LA9_0 = this.input.LA(1);
            if(LA9_0 >= 48 && LA9_0 <= 55) {
               alt9 = 1;
            }

            switch(alt9) {
            case 1:
               this.matchRange(48, 55);
               ++cnt8;
               break;
            default:
               if(cnt8 < 1) {
                  EarlyExitException eee = new EarlyExitException(8, this.input);
                  throw eee;
               }

               alt9 = 2;
               LA9_0 = this.input.LA(1);
               if(LA9_0 == 76 || LA9_0 == 108) {
                  alt9 = 1;
               }

               switch(alt9) {
               case 1:
                  this.mIntegerTypeSuffix();
               default:
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mDecDigit() throws RecognitionException {
      try {
         this.matchRange(48, 57);
      } finally {
         ;
      }
   }

   public final void mHexDigit() throws RecognitionException {
      try {
         if((this.input.LA(1) < 48 || this.input.LA(1) > 57) && (this.input.LA(1) < 65 || this.input.LA(1) > 70) && (this.input.LA(1) < 97 || this.input.LA(1) > 102)) {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            this.recover(mse);
            throw mse;
         } else {
            this.input.consume();
         }
      } finally {
         ;
      }
   }

   public final void mIntegerTypeSuffix() throws RecognitionException {
      try {
         if(this.input.LA(1) != 76 && this.input.LA(1) != 108) {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            this.recover(mse);
            throw mse;
         } else {
            this.input.consume();
         }
      } finally {
         ;
      }
   }

   public final void mFloatingPointLiteral() throws RecognitionException {
      try {
         byte _type;
         byte _channel;
         _type = 29;
         _channel = 0;
         boolean alt19 = true;
         int var12 = this.dfa19.predict(this.input);
         int cnt17;
         byte alt18;
         int LA18_0;
         EarlyExitException eee;
         int LA16_0;
         byte var13;
         label336:
         switch(var12) {
         case 1:
            cnt17 = 0;

            while(true) {
               alt18 = 2;
               LA18_0 = this.input.LA(1);
               if(LA18_0 >= 48 && LA18_0 <= 57) {
                  alt18 = 1;
               }

               switch(alt18) {
               case 1:
                  this.matchRange(48, 57);
                  ++cnt17;
                  break;
               default:
                  if(cnt17 < 1) {
                     eee = new EarlyExitException(10, this.input);
                     throw eee;
                  }

                  this.match(46);

                  while(true) {
                     alt18 = 2;
                     LA18_0 = this.input.LA(1);
                     if(LA18_0 >= 48 && LA18_0 <= 57) {
                        alt18 = 1;
                     }

                     switch(alt18) {
                     case 1:
                        this.matchRange(48, 57);
                        break;
                     default:
                        alt18 = 2;
                        LA18_0 = this.input.LA(1);
                        if(LA18_0 == 69 || LA18_0 == 101) {
                           alt18 = 1;
                        }

                        switch(alt18) {
                        case 1:
                           this.mExponent();
                        }

                        var13 = 2;
                        LA16_0 = this.input.LA(1);
                        if(LA16_0 == 68 || LA16_0 == 70 || LA16_0 == 100 || LA16_0 == 102) {
                           var13 = 1;
                        }

                        switch(var13) {
                        case 1:
                           this.mFloatTypeSuffix();
                        default:
                           break label336;
                        }
                     }
                  }
               }
            }
         case 2:
            this.match(46);
            cnt17 = 0;

            while(true) {
               alt18 = 2;
               LA18_0 = this.input.LA(1);
               if(LA18_0 >= 48 && LA18_0 <= 57) {
                  alt18 = 1;
               }

               switch(alt18) {
               case 1:
                  this.matchRange(48, 57);
                  ++cnt17;
                  break;
               default:
                  if(cnt17 < 1) {
                     eee = new EarlyExitException(14, this.input);
                     throw eee;
                  }

                  alt18 = 2;
                  LA18_0 = this.input.LA(1);
                  if(LA18_0 == 69 || LA18_0 == 101) {
                     alt18 = 1;
                  }

                  switch(alt18) {
                  case 1:
                     this.mExponent();
                  }

                  var13 = 2;
                  LA16_0 = this.input.LA(1);
                  if(LA16_0 == 68 || LA16_0 == 70 || LA16_0 == 100 || LA16_0 == 102) {
                     var13 = 1;
                  }

                  switch(var13) {
                  case 1:
                     this.mFloatTypeSuffix();
                  default:
                     break label336;
                  }
               }
            }
         case 3:
            cnt17 = 0;

            label333:
            while(true) {
               alt18 = 2;
               LA18_0 = this.input.LA(1);
               if(LA18_0 >= 48 && LA18_0 <= 57) {
                  alt18 = 1;
               }

               switch(alt18) {
               case 1:
                  this.matchRange(48, 57);
                  ++cnt17;
                  break;
               default:
                  if(cnt17 < 1) {
                     eee = new EarlyExitException(17, this.input);
                     throw eee;
                  }

                  alt18 = 2;
                  LA18_0 = this.input.LA(1);
                  if(LA18_0 == 69 || LA18_0 == 101) {
                     alt18 = 1;
                  }

                  switch(alt18) {
                  case 1:
                     this.mExponent();
                  default:
                     this.mFloatTypeSuffix();
                     break label333;
                  }
               }
            }
         }

         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mExponent() throws RecognitionException {
      try {
         if(this.input.LA(1) != 69 && this.input.LA(1) != 101) {
            MismatchedSetException var10 = new MismatchedSetException((BitSet)null, this.input);
            this.recover(var10);
            throw var10;
         } else {
            this.input.consume();
            byte alt20 = 2;
            int LA20_0 = this.input.LA(1);
            if(LA20_0 == 43 || LA20_0 == 45) {
               alt20 = 1;
            }

            switch(alt20) {
            case 1:
               if(this.input.LA(1) != 43 && this.input.LA(1) != 45) {
                  MismatchedSetException cnt21 = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(cnt21);
                  throw cnt21;
               } else {
                  this.input.consume();
               }
            default:
               int var11 = 0;

               while(true) {
                  byte alt21 = 2;
                  int LA21_0 = this.input.LA(1);
                  if(LA21_0 >= 48 && LA21_0 <= 57) {
                     alt21 = 1;
                  }

                  switch(alt21) {
                  case 1:
                     this.matchRange(48, 57);
                     ++var11;
                     break;
                  default:
                     if(var11 >= 1) {
                        return;
                     }

                     EarlyExitException eee = new EarlyExitException(21, this.input);
                     throw eee;
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mFloatTypeSuffix() throws RecognitionException {
      try {
         if(this.input.LA(1) != 68 && this.input.LA(1) != 70 && this.input.LA(1) != 100 && this.input.LA(1) != 102) {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            this.recover(mse);
            throw mse;
         } else {
            this.input.consume();
         }
      } finally {
         ;
      }
   }

   public final void mCharacterLiteral() throws RecognitionException {
      try {
         byte _type = 31;
         byte _channel = 0;
         this.match(39);
         boolean alt22 = true;
         int LA22_0 = this.input.LA(1);
         byte alt221;
         if(LA22_0 == 92) {
            alt221 = 1;
         } else {
            if((LA22_0 < 0 || LA22_0 > 38) && (LA22_0 < 40 || LA22_0 > 91) && (LA22_0 < 93 || LA22_0 > '\uffff')) {
               NoViableAltException mse1 = new NoViableAltException("", 22, 0, this.input);
               throw mse1;
            }

            alt221 = 2;
         }

         switch(alt221) {
         case 1:
            this.mEscapeSequence();
            break;
         case 2:
            if((this.input.LA(1) < 0 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > '\uffff')) {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               this.recover(mse);
               throw mse;
            }

            this.input.consume();
         }

         this.match(39);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mCharacterSequence() throws RecognitionException {
      try {
         byte _type = 25;
         byte _channel = 0;
         this.match(39);

         while(true) {
            byte alt23 = 3;
            int LA23_0 = this.input.LA(1);
            if(LA23_0 == 92) {
               alt23 = 1;
            } else if(LA23_0 >= 0 && LA23_0 <= 38 || LA23_0 >= 40 && LA23_0 <= 91 || LA23_0 >= 93 && LA23_0 <= '\uffff') {
               alt23 = 2;
            }

            switch(alt23) {
            case 1:
               this.mEscapeSequence();
               break;
            case 2:
               if((this.input.LA(1) < 0 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > '\uffff')) {
                  MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse);
                  throw mse;
               }

               this.input.consume();
               break;
            default:
               this.match(39);
               this.setText(this.getText().substring(1, this.getText().length() - 1));
               this.state.type = _type;
               this.state.channel = _channel;
               return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mStringLiteral() throws RecognitionException {
      try {
         byte _type = 32;
         byte _channel = 0;
         this.match(34);

         while(true) {
            byte alt24 = 3;
            int LA24_0 = this.input.LA(1);
            if(LA24_0 == 92) {
               alt24 = 1;
            } else if(LA24_0 >= 0 && LA24_0 <= 33 || LA24_0 >= 35 && LA24_0 <= 91 || LA24_0 >= 93 && LA24_0 <= '\uffff') {
               alt24 = 2;
            }

            switch(alt24) {
            case 1:
               this.mEscapeSequence();
               break;
            case 2:
               if((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > '\uffff')) {
                  MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse);
                  throw mse;
               }

               this.input.consume();
               break;
            default:
               this.match(34);
               this.setText(this.getText().substring(1, this.getText().length() - 1));
               this.state.type = _type;
               this.state.channel = _channel;
               return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mEscapeSequence() throws RecognitionException {
      try {
         boolean alt25 = true;
         int LA25_0 = this.input.LA(1);
         NoViableAltException mse;
         if(LA25_0 == 92) {
            byte alt251;
            switch(this.input.LA(2)) {
            case 34:
            case 39:
            case 92:
            case 98:
            case 102:
            case 110:
            case 114:
            case 116:
               alt251 = 1;
               break;
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
               alt251 = 3;
               break;
            case 117:
               alt251 = 2;
               break;
            default:
               mse = new NoViableAltException("", 25, 1, this.input);
               throw mse;
            }

            switch(alt251) {
            case 1:
               this.match(92);
               if(this.input.LA(1) != 34 && this.input.LA(1) != 39 && this.input.LA(1) != 92 && this.input.LA(1) != 98 && this.input.LA(1) != 102 && this.input.LA(1) != 110 && this.input.LA(1) != 114 && this.input.LA(1) != 116) {
                  MismatchedSetException mse1 = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse1);
                  throw mse1;
               }

               this.input.consume();
               break;
            case 2:
               this.mUnicodeEscape();
               break;
            case 3:
               this.mOctalEscape();
            }

         } else {
            mse = new NoViableAltException("", 25, 0, this.input);
            throw mse;
         }
      } finally {
         ;
      }
   }

   public final void mOctalEscape() throws RecognitionException {
      try {
         boolean alt26 = true;
         int LA26_0 = this.input.LA(1);
         if(LA26_0 != 92) {
            NoViableAltException nvae2 = new NoViableAltException("", 26, 0, this.input);
            throw nvae2;
         } else {
            int nvae = this.input.LA(2);
            int nvae1;
            byte alt261;
            if(nvae >= 48 && nvae <= 51) {
               nvae1 = this.input.LA(3);
               if(nvae1 >= 48 && nvae1 <= 55) {
                  int LA26_4 = this.input.LA(4);
                  if(LA26_4 >= 48 && LA26_4 <= 55) {
                     alt261 = 1;
                  } else {
                     alt261 = 2;
                  }
               } else {
                  alt261 = 3;
               }
            } else {
               if(nvae < 52 || nvae > 55) {
                  NoViableAltException nvae3 = new NoViableAltException("", 26, 1, this.input);
                  throw nvae3;
               }

               nvae1 = this.input.LA(3);
               if(nvae1 >= 48 && nvae1 <= 55) {
                  alt261 = 2;
               } else {
                  alt261 = 3;
               }
            }

            switch(alt261) {
            case 1:
               this.match(92);
               this.matchRange(48, 51);
               this.matchRange(48, 55);
               this.matchRange(48, 55);
               break;
            case 2:
               this.match(92);
               this.matchRange(48, 55);
               this.matchRange(48, 55);
               break;
            case 3:
               this.match(92);
               this.matchRange(48, 55);
            }

         }
      } finally {
         ;
      }
   }

   public final void mUnicodeEscape() throws RecognitionException {
      try {
         this.match(92);
         this.match(117);
         this.mHexDigit();
         this.mHexDigit();
         this.mHexDigit();
         this.mHexDigit();
      } finally {
         ;
      }
   }

   public final void mWS() throws RecognitionException {
      try {
         byte _type = 43;
         boolean _channel = false;
         if((this.input.LA(1) < 9 || this.input.LA(1) > 10) && (this.input.LA(1) < 12 || this.input.LA(1) > 13) && this.input.LA(1) != 32) {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            this.recover(mse);
            throw mse;
         } else {
            this.input.consume();
            byte _channel1 = 99;
            this.state.type = _type;
            this.state.channel = _channel1;
         }
      } finally {
         ;
      }
   }

   public final void mIdentifier() throws RecognitionException {
      try {
         byte _type = 26;
         byte _channel = 0;
         int cnt27 = 0;

         while(true) {
            byte alt27 = 2;
            int LA27_0 = this.input.LA(1);
            if(LA27_0 >= 65 && LA27_0 <= 90 || LA27_0 == 95 || LA27_0 >= 97 && LA27_0 <= 122) {
               alt27 = 1;
            }

            switch(alt27) {
            case 1:
               this.mLetter();
               ++cnt27;
               break;
            default:
               if(cnt27 >= 1) {
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
               }

               EarlyExitException eee = new EarlyExitException(27, this.input);
               throw eee;
            }
         }
      } finally {
         ;
      }
   }

   public final void mLetter() throws RecognitionException {
      try {
         boolean alt28 = true;
         byte alt281;
         switch(this.input.LA(1)) {
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
            alt281 = 2;
            break;
         case 91:
         case 92:
         case 93:
         case 94:
         case 96:
         default:
            NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
            throw nvae;
         case 95:
            alt281 = 3;
            break;
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
            alt281 = 1;
         }

         switch(alt281) {
         case 1:
            this.matchRange(97, 122);
            break;
         case 2:
            this.matchRange(65, 90);
            break;
         case 3:
            this.match(95);
         }

      } finally {
         ;
      }
   }

   public final void mCOMMENT() throws RecognitionException {
      try {
         byte _type = 45;
         boolean _channel = false;
         this.match("/*");

         while(true) {
            byte alt29 = 2;
            int LA29_0 = this.input.LA(1);
            if(LA29_0 == 42) {
               int LA29_1 = this.input.LA(2);
               if(LA29_1 == 47) {
                  alt29 = 2;
               } else if(LA29_1 >= 0 && LA29_1 <= 46 || LA29_1 >= 48 && LA29_1 <= '\uffff') {
                  alt29 = 1;
               }
            } else if(LA29_0 >= 0 && LA29_0 <= 41 || LA29_0 >= 43 && LA29_0 <= '\uffff') {
               alt29 = 1;
            }

            switch(alt29) {
            case 1:
               this.matchAny();
               break;
            default:
               this.match("*/");
               byte _channel1 = 99;
               this.state.type = _type;
               this.state.channel = _channel1;
               return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mLINE_COMMENT() throws RecognitionException {
      try {
         byte _type = 46;
         boolean _channel = false;
         this.match("//");

         while(true) {
            byte alt31 = 2;
            int LA31_0 = this.input.LA(1);
            if(LA31_0 >= 0 && LA31_0 <= 9 || LA31_0 >= 11 && LA31_0 <= 12 || LA31_0 >= 14 && LA31_0 <= '\uffff') {
               alt31 = 1;
            }

            switch(alt31) {
            case 1:
               if((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 12) && (this.input.LA(1) < 14 || this.input.LA(1) > '\uffff')) {
                  MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse);
                  throw mse;
               }

               this.input.consume();
               break;
            default:
               alt31 = 2;
               LA31_0 = this.input.LA(1);
               if(LA31_0 == 13) {
                  alt31 = 1;
               }

               switch(alt31) {
               case 1:
                  this.match(13);
               default:
                  this.match(10);
                  byte _channel1 = 99;
                  this.state.type = _type;
                  this.state.channel = _channel1;
                  return;
               }
            }
         }
      } finally {
         ;
      }
   }

   public void mTokens() throws RecognitionException {
      boolean alt32 = true;
      int alt321 = this.dfa32.predict(this.input);
      switch(alt321) {
      case 1:
         this.mT__47();
         break;
      case 2:
         this.mT__48();
         break;
      case 3:
         this.mT__49();
         break;
      case 4:
         this.mT__50();
         break;
      case 5:
         this.mT__51();
         break;
      case 6:
         this.mT__52();
         break;
      case 7:
         this.mT__53();
         break;
      case 8:
         this.mT__54();
         break;
      case 9:
         this.mT__55();
         break;
      case 10:
         this.mT__56();
         break;
      case 11:
         this.mT__57();
         break;
      case 12:
         this.mT__58();
         break;
      case 13:
         this.mT__59();
         break;
      case 14:
         this.mT__60();
         break;
      case 15:
         this.mT__61();
         break;
      case 16:
         this.mT__62();
         break;
      case 17:
         this.mT__63();
         break;
      case 18:
         this.mT__64();
         break;
      case 19:
         this.mT__65();
         break;
      case 20:
         this.mT__66();
         break;
      case 21:
         this.mT__67();
         break;
      case 22:
         this.mT__68();
         break;
      case 23:
         this.mT__69();
         break;
      case 24:
         this.mT__70();
         break;
      case 25:
         this.mT__71();
         break;
      case 26:
         this.mT__72();
         break;
      case 27:
         this.mError();
         break;
      case 28:
         this.mBooleanConstant();
         break;
      case 29:
         this.mHexLiteral();
         break;
      case 30:
         this.mDecimalLiteral();
         break;
      case 31:
         this.mOctalLiteral();
         break;
      case 32:
         this.mFloatingPointLiteral();
         break;
      case 33:
         this.mCharacterLiteral();
         break;
      case 34:
         this.mCharacterSequence();
         break;
      case 35:
         this.mStringLiteral();
         break;
      case 36:
         this.mWS();
         break;
      case 37:
         this.mIdentifier();
         break;
      case 38:
         this.mCOMMENT();
         break;
      case 39:
         this.mLINE_COMMENT();
      }

   }

   static {
      int numStates = DFA1_transitionS.length;
      DFA1_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
      }

      DFA19_transitionS = new String[]{"￿\n", "￿\n\n￿￿", "", "", ""};
      DFA19_eot = DFA.unpackEncodedString("￿");
      DFA19_eof = DFA.unpackEncodedString("￿");
      DFA19_min = DFA.unpackEncodedStringToUnsignedChars(".￿");
      DFA19_max = DFA.unpackEncodedStringToUnsignedChars("9f￿");
      DFA19_accept = DFA.unpackEncodedString("￿");
      DFA19_special = DFA.unpackEncodedString("￿}>");
      numStates = DFA19_transitionS.length;
      DFA19_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
      }

      DFA32_transitionS = new String[]{"￿￿\r\n\b\t\t￿  \n  ￿\f ￿\b  ￿", "", "", "", "!\"", "$", "&", "", "", "", "", "(￿)", "", "", "", "", "", "", "", "", "", ",", "-", "", ".", "/", "￿\b1\n￿￿0￿￿0", "￿\n3\n￿￿", "", "\'56454ﾣ5", "", "", "", "", "", "", "", "", "", "", "", "", "", "", " ￿ ￿ ", " ￿ ￿ ", "9", ":", "", "￿\b1\n￿￿", "", "￿\n3\n￿￿", "<￿<\b￿>?$￿<￿<￿<￿<￿<￿<=", "\'6@￘6", "", "", "", "A", "B", "", "\'6@￘6", "\nC￿C￿C", "\'6@\b6\bD￈6", "\'6@\b6\bE￈6", "", " ￿ ￿ ", "H", "\nI￿I￿I", "\'6@\b6\bJ￈6", "\'6@￘6", "", "", " ￿ ￿ ", "\nK￿K￿K", "\'6@￘6", "\nL￿L￿L", "\'6@￘6"};
      DFA32_eot = DFA.unpackEncodedString("￿#%\'￿*\t￿ ￿ 2￿78 ￿;￿2￿ ￿G ￿G￿");
      DFA32_eof = DFA.unpackEncodedString("M￿");
      DFA32_min = DFA.unpackEncodedStringToUnsignedChars("\t￿=￿*\t￿fF￿RA.￿ ￿AUL￿.￿.\" ￿ES￿ 0 ￿AE0 ￿A0 0 ");
      DFA32_max = DFA.unpackEncodedStringToUnsignedChars("}￿>=￿/\t￿fF￿RAxf￿￿￿zUL￿f￿fu￿￿ES￿￿f￿￿zEf￿￿zf￿f￿");
      DFA32_accept = DFA.unpackEncodedString("￿￿\n\f\r￿￿￿ ￿#$%\b\t&\'￿￿￿\"￿￿!￿!￿");
      DFA32_special = DFA.unpackEncodedString("￿￿￿￿ ￿￿￿\b}>");
      numStates = DFA32_transitionS.length;
      DFA32_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
      }

   }
}
