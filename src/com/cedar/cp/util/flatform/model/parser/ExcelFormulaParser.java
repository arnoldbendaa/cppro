// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.Pair;
import com.cedar.cp.util.flatform.model.WorksheetColumnMapping;
import com.cedar.cp.util.flatform.model.parser.CellRange;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.ColumnRange;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$DFA10;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$DFA11;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$DFA12;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$DFA16;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$additive_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$area_reference_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$argument_list_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$argument_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$array_element_list_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$array_element_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$array_formula_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$array_row_list_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$array_row_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$cell_range_ref_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$cell_reference_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$cell_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$column_range_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$column_reference_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$column_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$concat_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$const_array_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$exponentiation_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$formula_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$function_call_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$function_name_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$intersection_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$local_column_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$local_named_range_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$logical_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$multiplicative_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$named_range_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$percent_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$primary_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$primitive_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$range_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$reference_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$row_range_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$row_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$scalar_formula_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$sheet_name_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$sheet_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$string_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$translation_unit_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$unary_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$union_expression_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$vector_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$worksheet_cell_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$worksheet_column_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$worksheet_named_range_return;
import com.cedar.cp.util.flatform.model.parser.FunctionValidator;
import com.cedar.cp.util.flatform.model.parser.WorksheetCellRef;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.DFA;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

public class ExcelFormulaParser extends Parser {

   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "SCALAR_FORMULA", "ARRAY_FORMULA", "BRACKET_EXPR", "LOGICAL_EXPR", "CONCAT_EXPR", "ADDITIVE_EXPR", "FUNCTION_CALL", "RC_CELL_REF", "CELL_REF", "WORKSHEET_CELL_REF", "COLUMN_REF", "WORKSHEET_COLUMN_REF", "ROW_REF", "CELL_RANGE_REF", "COLUMN_RANGE_REF", "IF_FUNCTION", "UNARY_MINUS", "SHEET_REF", "ARRAY_CONST", "ARRAY_ROW", "NAMED_RANGE", "CharacterSequence", "Identifier", "DecimalLiteral", "Error", "FloatingPointLiteral", "BooleanConstant", "CharacterLiteral", "StringLiteral", "HexDigit", "IntegerTypeSuffix", "HexLiteral", "OctalLiteral", "DecDigit", "Exponent", "FloatTypeSuffix", "EscapeSequence", "UnicodeEscape", "OctalEscape", "WS", "Letter", "COMMENT", "LINE_COMMENT", "\'=\'", "\'{\'", "\'}\'", "\'<\'", "\'<=\'", "\'>\'", "\'>=\'", "\'<>\'", "\'!=\'", "\'&\'", "\'+\'", "\'-\'", "\'*\'", "\'/\'", "\'^\'", "\'%\'", "\'(\'", "\')\'", "\'!\'", "\',\'", "\' \'", "\':\'", "\'$\'", "\';\'", "\'if\'", "\'IF\'"};
   public static final int T__68 = 68;
   public static final int CELL_RANGE_REF = 17;
   public static final int T__69 = 69;
   public static final int T__66 = 66;
   public static final int T__67 = 67;
   public static final int T__64 = 64;
   public static final int T__65 = 65;
   public static final int T__62 = 62;
   public static final int ARRAY_FORMULA = 5;
   public static final int NAMED_RANGE = 24;
   public static final int T__63 = 63;
   public static final int FloatTypeSuffix = 39;
   public static final int OctalLiteral = 36;
   public static final int CharacterLiteral = 31;
   public static final int CharacterSequence = 25;
   public static final int ROW_REF = 16;
   public static final int Exponent = 38;
   public static final int WORKSHEET_CELL_REF = 13;
   public static final int IF_FUNCTION = 19;
   public static final int T__61 = 61;
   public static final int T__60 = 60;
   public static final int EOF = -1;
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
   public static final int IntegerTypeSuffix = 34;
   public static final int SCALAR_FORMULA = 4;
   public static final int T__48 = 48;
   public static final int RC_CELL_REF = 11;
   public static final int T__49 = 49;
   public static final int UNARY_MINUS = 20;
   public static final int DecimalLiteral = 27;
   public static final int ARRAY_ROW = 23;
   public static final int DecDigit = 37;
   public static final int StringLiteral = 32;
   public static final int CONCAT_EXPR = 8;
   public static final int WS = 43;
   public static final int T__71 = 71;
   public static final int T__72 = 72;
   public static final int T__70 = 70;
   public static final int CELL_REF = 12;
   public static final int UnicodeEscape = 41;
   public static final int FloatingPointLiteral = 29;
   public static final int Error = 28;
   public static final int ARRAY_CONST = 22;
   public static final int BooleanConstant = 30;
   public static final int BRACKET_EXPR = 6;
   public static final int FUNCTION_CALL = 10;
   public static final int Letter = 44;
   public static final int OctalEscape = 42;
   public static final int EscapeSequence = 40;
   protected TreeAdaptor adaptor;
   private int mCurrentRow;
   private int mCurrentColumn;
   private WorksheetColumnMapping mColumnMapping;
   private Set<CellRangeRef> mCellRefs;
   private List<Pair<CellRef, int[]>> mCellRefOffsets;
   private Stack mErrorContextStack;
   private FunctionValidator mFunctionValidator;
   protected ExcelFormulaParser$DFA10 dfa10;
   protected ExcelFormulaParser$DFA11 dfa11;
   protected ExcelFormulaParser$DFA12 dfa12;
   protected ExcelFormulaParser$DFA16 dfa16;
   static final String DFA10_eotS = "￿";
   static final String DFA10_eofS = "￿";
   static final String DFA10_minS = " ￿";
   static final String DFA10_maxS = "H ￿";
   static final String DFA10_acceptS = "￿";
   static final String DFA10_specialS = "￿ \b\t\n\f\r￿}>";
   static final String[] DFA10_transitionS = new String[]{"\b\f\t\n￿\r\b￿￿￿￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "￿", "", ""};
   static final short[] DFA10_eot = DFA.unpackEncodedString("￿");
   static final short[] DFA10_eof = DFA.unpackEncodedString("￿");
   static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(" ￿");
   static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars("H ￿");
   static final short[] DFA10_accept = DFA.unpackEncodedString("￿");
   static final short[] DFA10_special = DFA.unpackEncodedString("￿ \b\t\n\f\r￿}>");
   static final short[][] DFA10_transition;
   static final String DFA11_eotS = "\n￿";
   static final String DFA11_eofS = "\n￿";
   static final String DFA11_minS = "￿ ￿ ";
   static final String DFA11_maxS = "H￿ ￿ ";
   static final String DFA11_acceptS = "￿￿￿";
   static final String DFA11_specialS = "￿ ￿}>";
   static final String[] DFA11_transitionS;
   static final short[] DFA11_eot;
   static final short[] DFA11_eof;
   static final char[] DFA11_min;
   static final char[] DFA11_max;
   static final short[] DFA11_accept;
   static final short[] DFA11_special;
   static final short[][] DFA11_transition;
   static final String DFA12_eotS = "￿";
   static final String DFA12_eofS = "￿￿￿￿￿￿￿￿";
   static final String DFA12_minS = "A￿/￿/￿￿////";
   static final String DFA12_maxS = "HAF￿F￿EF￿EF￿FFEFF";
   static final String DFA12_acceptS = "￿￿￿￿￿";
   static final String DFA12_specialS = "￿}>";
   static final String[] DFA12_transitionS;
   static final short[] DFA12_eot;
   static final short[] DFA12_eof;
   static final char[] DFA12_min;
   static final char[] DFA12_max;
   static final short[] DFA12_accept;
   static final short[] DFA12_special;
   static final short[][] DFA12_transition;
   static final String DFA16_eotS = "\f￿";
   static final String DFA16_eofS = "￿￿￿";
   static final String DFA16_minS = "A￿A￿";
   static final String DFA16_maxS = "EAF￿ED￿EFE";
   static final String DFA16_acceptS = "￿￿￿";
   static final String DFA16_specialS = "\f￿}>";
   static final String[] DFA16_transitionS;
   static final short[] DFA16_eot;
   static final short[] DFA16_eof;
   static final char[] DFA16_min;
   static final char[] DFA16_max;
   static final short[] DFA16_accept;
   static final short[] DFA16_special;
   static final short[][] DFA16_transition;
   public static final BitSet FOLLOW_formula_in_translation_unit157;
   public static final BitSet FOLLOW_EOF_in_translation_unit159;
   public static final BitSet FOLLOW_scalar_formula_in_formula167;
   public static final BitSet FOLLOW_array_formula_in_formula179;
   public static final BitSet FOLLOW_47_in_scalar_formula188;
   public static final BitSet FOLLOW_primary_expression_in_scalar_formula190;
   public static final BitSet FOLLOW_48_in_array_formula208;
   public static final BitSet FOLLOW_47_in_array_formula209;
   public static final BitSet FOLLOW_primary_expression_in_array_formula211;
   public static final BitSet FOLLOW_49_in_array_formula213;
   public static final BitSet FOLLOW_logical_expression_in_primary_expression231;
   public static final BitSet FOLLOW_concat_expression_in_logical_expression240;
   public static final BitSet FOLLOW_47_in_logical_expression245;
   public static final BitSet FOLLOW_50_in_logical_expression250;
   public static final BitSet FOLLOW_51_in_logical_expression255;
   public static final BitSet FOLLOW_52_in_logical_expression260;
   public static final BitSet FOLLOW_53_in_logical_expression265;
   public static final BitSet FOLLOW_54_in_logical_expression270;
   public static final BitSet FOLLOW_55_in_logical_expression275;
   public static final BitSet FOLLOW_concat_expression_in_logical_expression279;
   public static final BitSet FOLLOW_additive_expression_in_concat_expression291;
   public static final BitSet FOLLOW_56_in_concat_expression295;
   public static final BitSet FOLLOW_additive_expression_in_concat_expression298;
   public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression309;
   public static final BitSet FOLLOW_57_in_additive_expression314;
   public static final BitSet FOLLOW_58_in_additive_expression317;
   public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression321;
   public static final BitSet FOLLOW_exponentiation_expression_in_multiplicative_expression333;
   public static final BitSet FOLLOW_59_in_multiplicative_expression338;
   public static final BitSet FOLLOW_60_in_multiplicative_expression341;
   public static final BitSet FOLLOW_exponentiation_expression_in_multiplicative_expression345;
   public static final BitSet FOLLOW_percent_expression_in_exponentiation_expression358;
   public static final BitSet FOLLOW_61_in_exponentiation_expression362;
   public static final BitSet FOLLOW_percent_expression_in_exponentiation_expression365;
   public static final BitSet FOLLOW_unary_expression_in_percent_expression378;
   public static final BitSet FOLLOW_62_in_percent_expression380;
   public static final BitSet FOLLOW_unary_expression_in_percent_expression401;
   public static final BitSet FOLLOW_58_in_unary_expression411;
   public static final BitSet FOLLOW_union_expression_in_unary_expression414;
   public static final BitSet FOLLOW_57_in_unary_expression443;
   public static final BitSet FOLLOW_union_expression_in_unary_expression446;
   public static final BitSet FOLLOW_union_expression_in_unary_expression471;
   public static final BitSet FOLLOW_intersection_expression_in_union_expression480;
   public static final BitSet FOLLOW_reference_expression_in_intersection_expression490;
   public static final BitSet FOLLOW_cell_reference_in_reference_expression504;
   public static final BitSet FOLLOW_function_call_in_reference_expression533;
   public static final BitSet FOLLOW_primitive_in_reference_expression548;
   public static final BitSet FOLLOW_const_array_in_reference_expression577;
   public static final BitSet FOLLOW_area_reference_in_reference_expression608;
   public static final BitSet FOLLOW_63_in_reference_expression633;
   public static final BitSet FOLLOW_primary_expression_in_reference_expression635;
   public static final BitSet FOLLOW_64_in_reference_expression637;
   public static final BitSet FOLLOW_sheet_name_in_sheet664;
   public static final BitSet FOLLOW_65_in_sheet666;
   public static final BitSet FOLLOW_CharacterSequence_in_sheet_name678;
   public static final BitSet FOLLOW_Identifier_in_sheet_name705;
   public static final BitSet FOLLOW_Identifier_in_sheet_name733;
   public static final BitSet FOLLOW_DecimalLiteral_in_sheet_name737;
   public static final BitSet FOLLOW_range_in_area_reference749;
   public static final BitSet FOLLOW_set_in_area_reference754;
   public static final BitSet FOLLOW_area_reference_in_area_reference761;
   public static final BitSet FOLLOW_vector_in_area_reference780;
   public static final BitSet FOLLOW_set_in_area_reference784;
   public static final BitSet FOLLOW_area_reference_in_area_reference791;
   public static final BitSet FOLLOW_cell_range_ref_in_range803;
   public static final BitSet FOLLOW_local_named_range_in_range815;
   public static final BitSet FOLLOW_worksheet_named_range_in_range827;
   public static final BitSet FOLLOW_named_range_in_local_named_range842;
   public static final BitSet FOLLOW_sheet_in_worksheet_named_range860;
   public static final BitSet FOLLOW_named_range_in_worksheet_named_range862;
   public static final BitSet FOLLOW_Identifier_in_named_range882;
   public static final BitSet FOLLOW_column_range_in_vector891;
   public static final BitSet FOLLOW_row_range_in_vector902;
   public static final BitSet FOLLOW_cell_reference_in_cell_range_ref925;
   public static final BitSet FOLLOW_68_in_cell_range_ref927;
   public static final BitSet FOLLOW_cell_reference_in_cell_range_ref929;
   public static final BitSet FOLLOW_column_reference_in_column_range955;
   public static final BitSet FOLLOW_68_in_column_range957;
   public static final BitSet FOLLOW_column_reference_in_column_range959;
   public static final BitSet FOLLOW_worksheet_column_in_column_reference980;
   public static final BitSet FOLLOW_local_column_in_column_reference1003;
   public static final BitSet FOLLOW_sheet_in_worksheet_column1018;
   public static final BitSet FOLLOW_local_column_in_worksheet_column1020;
   public static final BitSet FOLLOW_column_in_local_column1050;
   public static final BitSet FOLLOW_69_in_local_column1074;
   public static final BitSet FOLLOW_column_in_local_column1076;
   public static final BitSet FOLLOW_row_in_row_range1097;
   public static final BitSet FOLLOW_68_in_row_range1099;
   public static final BitSet FOLLOW_row_in_row_range1101;
   public static final BitSet FOLLOW_worksheet_cell_in_cell_reference1110;
   public static final BitSet FOLLOW_cell_in_cell_reference1127;
   public static final BitSet FOLLOW_sheet_in_worksheet_cell1144;
   public static final BitSet FOLLOW_cell_in_worksheet_cell1146;
   public static final BitSet FOLLOW_column_in_cell1187;
   public static final BitSet FOLLOW_row_in_cell1195;
   public static final BitSet FOLLOW_69_in_cell1224;
   public static final BitSet FOLLOW_column_in_cell1228;
   public static final BitSet FOLLOW_row_in_cell1236;
   public static final BitSet FOLLOW_column_in_cell1270;
   public static final BitSet FOLLOW_69_in_cell1272;
   public static final BitSet FOLLOW_row_in_cell1276;
   public static final BitSet FOLLOW_69_in_cell1304;
   public static final BitSet FOLLOW_column_in_cell1308;
   public static final BitSet FOLLOW_69_in_cell1310;
   public static final BitSet FOLLOW_row_in_cell1314;
   public static final BitSet FOLLOW_Identifier_in_column1353;
   public static final BitSet FOLLOW_DecimalLiteral_in_row1362;
   public static final BitSet FOLLOW_48_in_const_array1379;
   public static final BitSet FOLLOW_array_element_list_in_const_array1382;
   public static final BitSet FOLLOW_49_in_const_array1386;
   public static final BitSet FOLLOW_array_row_in_array_element_list1402;
   public static final BitSet FOLLOW_70_in_array_element_list1407;
   public static final BitSet FOLLOW_array_row_in_array_element_list1411;
   public static final BitSet FOLLOW_array_row_list_in_array_row1423;
   public static final BitSet FOLLOW_array_element_in_array_row_list1441;
   public static final BitSet FOLLOW_66_in_array_row_list1446;
   public static final BitSet FOLLOW_array_element_in_array_row_list1450;
   public static final BitSet FOLLOW_primitive_in_array_element1463;
   public static final BitSet FOLLOW_function_name_in_function_call1474;
   public static final BitSet FOLLOW_63_in_function_call1476;
   public static final BitSet FOLLOW_argument_list_in_function_call1478;
   public static final BitSet FOLLOW_64_in_function_call1481;
   public static final BitSet FOLLOW_71_in_function_call1539;
   public static final BitSet FOLLOW_72_in_function_call1541;
   public static final BitSet FOLLOW_63_in_function_call1544;
   public static final BitSet FOLLOW_primary_expression_in_function_call1546;
   public static final BitSet FOLLOW_66_in_function_call1549;
   public static final BitSet FOLLOW_70_in_function_call1551;
   public static final BitSet FOLLOW_primary_expression_in_function_call1554;
   public static final BitSet FOLLOW_66_in_function_call1557;
   public static final BitSet FOLLOW_70_in_function_call1559;
   public static final BitSet FOLLOW_primary_expression_in_function_call1562;
   public static final BitSet FOLLOW_64_in_function_call1564;
   public static final BitSet FOLLOW_Identifier_in_function_name1643;
   public static final BitSet FOLLOW_Identifier_in_function_name1667;
   public static final BitSet FOLLOW_DecimalLiteral_in_function_name1671;
   public static final BitSet FOLLOW_argument_in_argument_list1719;
   public static final BitSet FOLLOW_set_in_argument_list1723;
   public static final BitSet FOLLOW_argument_in_argument_list1729;
   public static final BitSet FOLLOW_66_in_argument_list1736;
   public static final BitSet FOLLOW_primary_expression_in_argument1748;
   public static final BitSet FOLLOW_Error_in_argument1752;
   public static final BitSet FOLLOW_set_in_primitive1761;
   public static final BitSet FOLLOW_DecimalLiteral_in_primitive1768;
   public static final BitSet FOLLOW_set_in_primitive1782;
   public static final BitSet FOLLOW_FloatingPointLiteral_in_primitive1789;
   public static final BitSet FOLLOW_BooleanConstant_in_primitive1803;
   public static final BitSet FOLLOW_string_in_primitive1817;
   public static final BitSet FOLLOW_Error_in_primitive1831;
   public static final BitSet FOLLOW_set_in_string0;
   public static final BitSet FOLLOW_unary_expression_in_synpred15_ExcelFormula378;
   public static final BitSet FOLLOW_62_in_synpred15_ExcelFormula380;
   public static final BitSet FOLLOW_58_in_synpred16_ExcelFormula411;
   public static final BitSet FOLLOW_union_expression_in_synpred16_ExcelFormula414;
   public static final BitSet FOLLOW_57_in_synpred17_ExcelFormula443;
   public static final BitSet FOLLOW_union_expression_in_synpred17_ExcelFormula446;
   public static final BitSet FOLLOW_set_in_synpred26_ExcelFormula754;
   public static final BitSet FOLLOW_area_reference_in_synpred26_ExcelFormula761;
   public static final BitSet FOLLOW_set_in_synpred29_ExcelFormula784;
   public static final BitSet FOLLOW_area_reference_in_synpred29_ExcelFormula791;
   public static final BitSet FOLLOW_primary_expression_in_synpred51_ExcelFormula1748;


   public ExcelFormulaParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public ExcelFormulaParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.adaptor = new CommonTreeAdaptor();
      this.mCellRefs = new HashSet();
      this.mCellRefOffsets = new ArrayList();
      this.mErrorContextStack = new Stack();
      this.dfa10 = new ExcelFormulaParser$DFA10(this, this);
      this.dfa11 = new ExcelFormulaParser$DFA11(this, this);
      this.dfa12 = new ExcelFormulaParser$DFA12(this, this);
      this.dfa16 = new ExcelFormulaParser$DFA16(this, this);
      this.state.ruleMemo = new HashMap[106];
   }

   public void setTreeAdaptor(TreeAdaptor adaptor) {
      this.adaptor = adaptor;
   }

   public TreeAdaptor getTreeAdaptor() {
      return this.adaptor;
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "C:\\projects\\cp6\\cp\\util\\src\\java\\com\\cedar\\cp\\util\\flatform\\model\\parser\\ExcelFormula.g";
   }

   private void resetUserVariables() {
      this.mCellRefs.clear();
      this.mCellRefOffsets.clear();
      this.mErrorContextStack.clear();
   }

   public void setCurrentPosition(int row, int col) {
      this.mCurrentRow = row;
      this.mCurrentColumn = col;
   }

   public WorksheetColumnMapping getColumnMapping() {
      return this.mColumnMapping;
   }

   public void setColumnMapping(WorksheetColumnMapping columnMapping) {
      this.mColumnMapping = columnMapping;
   }

   public Set<CellRangeRef> getCellRefs() {
      return this.mCellRefs;
   }

   public List<Pair<CellRef, int[]>> getCellRefOffsets() {
      return this.mCellRefOffsets;
   }

   private void registerCellRef(CommonTree tree, Token startToken, Token endToken) {
      CellRef cellRef = new CellRef();
      CommonTree columnTree = null;
      CommonTree rowTree = null;

      for(int i = 0; i < tree.getChildCount(); ++i) {
         CommonTree child = (CommonTree)tree.getChild(i);
         if(child.getToken().getType() == 26) {
            columnTree = child;
            if(this.getColumnMapping() != null) {
               cellRef.setColumn(this.getColumnMapping().getColumn(child.getToken().getText()));
            } else {
               cellRef.setColumn(0);
            }
         } else if(child.getToken().getType() == 27) {
            rowTree = child;
            cellRef.setRow(Integer.parseInt(child.getToken().getText()) - 1);
         } else if(i == 0) {
            cellRef.setColumnAbsolute(true);
         } else {
            cellRef.setRowAbsolute(true);
         }
      }

      this.mCellRefOffsets.add(new Pair(cellRef, new int[]{startToken.getLine(), startToken.getCharPositionInLine(), endToken.getLine(), endToken.getCharPositionInLine() + endToken.getText().length() - 1}));
      if(!cellRef.isColumnAbsolute()) {
         cellRef.setColumn(cellRef.getColumn() - this.mCurrentColumn);
      }

      columnTree.getToken().setText(String.valueOf(cellRef.getColumn()));
      if(!cellRef.isRowAbsolute()) {
         cellRef.setRow(cellRef.getRow() - this.mCurrentRow);
      }

      if(rowTree != null) {
         rowTree.getToken().setText(String.valueOf(cellRef.getRow()));
      }

      this.mCellRefs.add(cellRef);
   }

   private void registerCellRangeRef(CommonTree tree) {
      Pair cellRefEnd = (Pair)this.mCellRefOffsets.get(this.mCellRefOffsets.size() - 1);
      Pair cellRefStart = (Pair)this.mCellRefOffsets.get(this.mCellRefOffsets.size() - 2);
      CellRef cr1 = (CellRef)cellRefStart.getChild1();
      Object cr2 = (CellRef)cellRefEnd.getChild1();
      if(cr1 instanceof WorksheetCellRef && !(cr2 instanceof WorksheetCellRef)) {
         cr2 = new WorksheetCellRef(((WorksheetCellRef)cr1).getWorksheet(), (CellRef)cr2);
         cellRefEnd.setChild1(cr2);
      }

      CellRange cellRange = new CellRange(cr1, (CellRef)cr2);
      this.getCellRefs().remove(cellRefStart.getChild1());
      this.getCellRefs().remove(cellRefEnd.getChild1());
      this.getCellRefs().add(cellRange);
   }

   private void registerWorksheetCellRef(CommonTree tree) {
      Pair pair = (Pair)this.mCellRefOffsets.get(this.mCellRefOffsets.size() - 1);
      String sheetName = tree.getChild(0).getText();
      if(this.countCellRefRefs((CellRef)pair.getChild1()) == 1) {
         this.mCellRefs.remove(pair.getChild1());
      }

      WorksheetCellRef worksheetCellRef = new WorksheetCellRef(sheetName, (CellRef)pair.getChild1());
      this.mCellRefs.add(worksheetCellRef);
      pair.setChild1(worksheetCellRef);
   }

   private void registerColumnRef(CommonTree tree, Token startToken, Token endToken) {
      CellRef columnRef = new CellRef();
      CommonTree columnTree = null;

      for(int i = 0; i < tree.getChildCount(); ++i) {
         CommonTree child = (CommonTree)tree.getChild(i);
         if(child.getToken().getType() == 26) {
            columnTree = child;
            if(this.getColumnMapping() != null) {
               columnRef.setColumn(this.getColumnMapping().getColumn(child.getToken().getText()));
            } else {
               columnRef.setColumn(0);
            }
         } else {
            columnRef.setColumnAbsolute(true);
         }
      }

      this.mCellRefOffsets.add(new Pair(columnRef, new int[]{startToken.getLine(), startToken.getCharPositionInLine(), endToken.getLine(), endToken.getCharPositionInLine() + endToken.getText().length() - 1}));
      if(!columnRef.isColumnAbsolute()) {
         columnRef.setColumn(columnRef.getColumn() - this.mCurrentColumn);
      }

      columnTree.getToken().setText(String.valueOf(columnRef.getColumn()));
      this.mCellRefs.add(columnRef);
   }

   private void registerWorksheetColumnRef(CommonTree tree) {
      Pair pair = (Pair)this.mCellRefOffsets.get(this.mCellRefOffsets.size() - 1);
      String sheetName = tree.getChild(0).getText();
      if(this.countCellRefRefs((CellRef)pair.getChild1()) == 1) {
         this.mCellRefs.remove(pair.getChild1());
      }

      WorksheetCellRef worksheetCellRef = new WorksheetCellRef(sheetName, (CellRef)pair.getChild1());
      this.mCellRefs.add(worksheetCellRef);
      pair.setChild1(worksheetCellRef);
   }

   private void registerColumnRangeRef(CommonTree tree) {
      Pair cellRefEnd = (Pair)this.mCellRefOffsets.get(this.mCellRefOffsets.size() - 1);
      Pair cellRefStart = (Pair)this.mCellRefOffsets.get(this.mCellRefOffsets.size() - 2);
      CellRef cr1 = (CellRef)cellRefStart.getChild1();
      Object cr2 = (CellRef)cellRefEnd.getChild1();
      if(cr1 instanceof WorksheetCellRef && !(cr2 instanceof WorksheetCellRef)) {
         cr2 = new WorksheetCellRef(((WorksheetCellRef)cr1).getWorksheet(), (CellRef)cr2);
         cellRefEnd.setChild1(cr2);
      }

      ColumnRange cellRange = new ColumnRange(cr1, (CellRef)cr2);
      this.getCellRefs().remove(cellRefStart.getChild1());
      this.getCellRefs().remove(cellRefEnd.getChild1());
      this.getCellRefs().add(cellRange);
   }

   private int countCellRefRefs(CellRef cellRef) {
      int count = 0;
      Iterator i$ = this.mCellRefOffsets.iterator();

      while(i$.hasNext()) {
         Pair entry = (Pair)i$.next();
         if(((CellRef)entry.getChild1()).equals(cellRef)) {
            ++count;
         }
      }

      return count;
   }

   private void displayRefs() {
      Iterator i$ = this.mCellRefOffsets.iterator();

      while(i$.hasNext()) {
         Pair pair = (Pair)i$.next();
         System.out.println("CellRef:" + pair.getChild1() + " start:" + ((int[])pair.getChild2())[1] + " end:" + ((int[])pair.getChild2())[3]);
      }

   }

   protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
      throw new MismatchedTokenException(ttype, input);
   }

   public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
      throw e;
   }

   private void pushErrorContext(String s) {
      this.mErrorContextStack.push(s);
   }

   private void popErrorContext() {
      this.mErrorContextStack.pop();
   }

   private void validateFunction(IntStream input, Token functionIdent) throws RecognitionException {
      String functionName = functionIdent.getText();
      if(this.mFunctionValidator != null && !this.mFunctionValidator.isValidFunction(functionName.toUpperCase())) {
         throw new RecognitionException(input);
      }
   }

   private boolean isFunction(String functionName) {
      return this.mFunctionValidator != null?this.mFunctionValidator.isValidFunction(functionName.toUpperCase()):true;
   }

   public void setFunctionValidator(FunctionValidator functionValidator) {
      this.mFunctionValidator = functionValidator;
   }

   private boolean isColumnId(String columnId) {
      for(int i = 0; i < columnId.length(); ++i) {
         if(Character.isDigit(columnId.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public final ExcelFormulaParser$translation_unit_return translation_unit() throws RecognitionException {
      ExcelFormulaParser$translation_unit_return retval = new ExcelFormulaParser$translation_unit_return();
      retval.start = this.input.LT(1);
      int translation_unit_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token EOF2 = null;
      ExcelFormulaParser$formula_return formula1 = null;
      CommonTree EOF2_tree = null;
      this.resetUserVariables();

      ExcelFormulaParser$translation_unit_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 1)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_formula_in_translation_unit157);
         formula1 = this.formula();
         --this.state._fsp;
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, formula1.getTree());
            }

            EOF2 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_translation_unit159);
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               EOF2_tree = (CommonTree)this.adaptor.create(EOF2);
               this.adaptor.addChild(root_0, EOF2_tree);
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if(this.state.backtracking == 0) {
               ;
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var11) {
         this.reportError(var11);
         throw var11;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 1, translation_unit_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$formula_return formula() throws RecognitionException {
      ExcelFormulaParser$formula_return retval = new ExcelFormulaParser$formula_return();
      retval.start = this.input.LT(1);
      int formula_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$scalar_formula_return scalar_formula3 = null;
      ExcelFormulaParser$array_formula_return array_formula4 = null;

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 2)) {
            ExcelFormulaParser$formula_return var15 = retval;
            return var15;
         }

         boolean e = true;
         int LA1_0 = this.input.LA(1);
         ExcelFormulaParser$formula_return nvae;
         byte var14;
         if(LA1_0 == 47) {
            var14 = 1;
         } else {
            if(LA1_0 != 48) {
               if(this.state.backtracking <= 0) {
                  NoViableAltException var16 = new NoViableAltException("", 1, 0, this.input);
                  throw var16;
               }

               this.state.failed = true;
               nvae = retval;
               return nvae;
            }

            var14 = 2;
         }

         switch(var14) {
         case 1:
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_scalar_formula_in_formula167);
            scalar_formula3 = this.scalar_formula();
            --this.state._fsp;
            if(this.state.failed) {
               nvae = retval;
               return nvae;
            }

            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, scalar_formula3.getTree());
            }
            break;
         case 2:
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_array_formula_in_formula179);
            array_formula4 = this.array_formula();
            --this.state._fsp;
            if(this.state.failed) {
               nvae = retval;
               return nvae;
            }

            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, array_formula4.getTree());
            }
         }

         retval.stop = this.input.LT(-1);
         if(this.state.backtracking == 0) {
            retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         throw var12;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 2, formula_StartIndex);
         }

      }

      return retval;
   }

   public final ExcelFormulaParser$scalar_formula_return scalar_formula() throws RecognitionException {
      ExcelFormulaParser$scalar_formula_return retval = new ExcelFormulaParser$scalar_formula_return();
      retval.start = this.input.LT(1);
      int scalar_formula_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal5 = null;
      ExcelFormulaParser$primary_expression_return primary_expression6 = null;
      Object char_literal5_tree = null;
      RewriteRuleTokenStream stream_47 = new RewriteRuleTokenStream(this.adaptor, "token 47");
      RewriteRuleSubtreeStream stream_primary_expression = new RewriteRuleSubtreeStream(this.adaptor, "rule primary_expression");

      try {
         ExcelFormulaParser$scalar_formula_return e;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 3)) {
            e = retval;
            return e;
         }

         char_literal5 = (Token)this.match(this.input, 47, FOLLOW_47_in_scalar_formula188);
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_47.add(char_literal5);
         }

         this.pushFollow(FOLLOW_primary_expression_in_scalar_formula190);
         primary_expression6 = this.primary_expression();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_primary_expression.add(primary_expression6.getTree());
         }

         if(this.state.backtracking == 0) {
            retval.tree = root_0;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
            root_0 = (CommonTree)this.adaptor.nil();
            CommonTree root_1 = (CommonTree)this.adaptor.nil();
            root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(4, "SCALAR_FORMULA"), root_1);
            this.adaptor.addChild(root_1, stream_primary_expression.nextTree());
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
         }

         retval.stop = this.input.LT(-1);
         if(this.state.backtracking == 0) {
            retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         throw var14;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 3, scalar_formula_StartIndex);
         }

      }

      return retval;
   }

   public final ExcelFormulaParser$array_formula_return array_formula() throws RecognitionException {
      ExcelFormulaParser$array_formula_return retval = new ExcelFormulaParser$array_formula_return();
      retval.start = this.input.LT(1);
      int array_formula_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal7 = null;
      Token char_literal8 = null;
      Token char_literal10 = null;
      ExcelFormulaParser$primary_expression_return primary_expression9 = null;
      Object char_literal7_tree = null;
      Object char_literal8_tree = null;
      Object char_literal10_tree = null;
      RewriteRuleTokenStream stream_49 = new RewriteRuleTokenStream(this.adaptor, "token 49");
      RewriteRuleTokenStream stream_48 = new RewriteRuleTokenStream(this.adaptor, "token 48");
      RewriteRuleTokenStream stream_47 = new RewriteRuleTokenStream(this.adaptor, "token 47");
      RewriteRuleSubtreeStream stream_primary_expression = new RewriteRuleSubtreeStream(this.adaptor, "rule primary_expression");

      ExcelFormulaParser$array_formula_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 4)) {
            e = retval;
            return e;
         }

         char_literal7 = (Token)this.match(this.input, 48, FOLLOW_48_in_array_formula208);
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_48.add(char_literal7);
         }

         char_literal8 = (Token)this.match(this.input, 47, FOLLOW_47_in_array_formula209);
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_47.add(char_literal8);
         }

         this.pushFollow(FOLLOW_primary_expression_in_array_formula211);
         primary_expression9 = this.primary_expression();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_primary_expression.add(primary_expression9.getTree());
         }

         char_literal10 = (Token)this.match(this.input, 49, FOLLOW_49_in_array_formula213);
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               stream_49.add(char_literal10);
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(5, "ARRAY_FORMULA"), root_1);
               this.adaptor.addChild(root_1, stream_primary_expression.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var20) {
         this.reportError(var20);
         throw var20;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 4, array_formula_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$primary_expression_return primary_expression() throws RecognitionException {
      ExcelFormulaParser$primary_expression_return retval = new ExcelFormulaParser$primary_expression_return();
      retval.start = this.input.LT(1);
      int primary_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$logical_expression_return logical_expression11 = null;

      ExcelFormulaParser$primary_expression_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 5)) {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_logical_expression_in_primary_expression231);
            logical_expression11 = this.logical_expression();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, logical_expression11.getTree());
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var9) {
         this.reportError(var9);
         throw var9;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 5, primary_expression_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$logical_expression_return logical_expression() throws RecognitionException {
      ExcelFormulaParser$logical_expression_return retval = new ExcelFormulaParser$logical_expression_return();
      retval.start = this.input.LT(1);
      int logical_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal13 = null;
      Token char_literal14 = null;
      Token string_literal15 = null;
      Token char_literal16 = null;
      Token string_literal17 = null;
      Token string_literal18 = null;
      Token string_literal19 = null;
      ExcelFormulaParser$concat_expression_return concat_expression12 = null;
      ExcelFormulaParser$concat_expression_return concat_expression20 = null;
      CommonTree char_literal13_tree = null;
      CommonTree char_literal14_tree = null;
      CommonTree string_literal15_tree = null;
      CommonTree char_literal16_tree = null;
      CommonTree string_literal17_tree = null;
      CommonTree string_literal18_tree = null;
      CommonTree string_literal19_tree = null;

      try {
         ExcelFormulaParser$logical_expression_return var30;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 6)) {
            var30 = retval;
            return var30;
         } else {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_concat_expression_in_logical_expression240);
            concat_expression12 = this.concat_expression();
            --this.state._fsp;
            if(this.state.failed) {
               var30 = retval;
               return var30;
            } else {
               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, concat_expression12.getTree());
               }

               while(true) {
                  byte e = 2;
                  int LA3_0 = this.input.LA(1);
                  if(LA3_0 == 47 || LA3_0 >= 50 && LA3_0 <= 55) {
                     e = 1;
                  }

                  switch(e) {
                  case 1:
                     boolean alt2 = true;
                     ExcelFormulaParser$logical_expression_return nvae;
                     byte var29;
                     switch(this.input.LA(1)) {
                     case 47:
                        var29 = 1;
                        break;
                     case 48:
                     case 49:
                     default:
                        if(this.state.backtracking <= 0) {
                           NoViableAltException var31 = new NoViableAltException("", 2, 0, this.input);
                           throw var31;
                        }

                        this.state.failed = true;
                        nvae = retval;
                        return nvae;
                     case 50:
                        var29 = 2;
                        break;
                     case 51:
                        var29 = 3;
                        break;
                     case 52:
                        var29 = 4;
                        break;
                     case 53:
                        var29 = 5;
                        break;
                     case 54:
                        var29 = 6;
                        break;
                     case 55:
                        var29 = 7;
                     }

                     switch(var29) {
                     case 1:
                        char_literal13 = (Token)this.match(this.input, 47, FOLLOW_47_in_logical_expression245);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           char_literal13_tree = (CommonTree)this.adaptor.create(char_literal13);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal13_tree, root_0);
                        }
                        break;
                     case 2:
                        char_literal14 = (Token)this.match(this.input, 50, FOLLOW_50_in_logical_expression250);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           char_literal14_tree = (CommonTree)this.adaptor.create(char_literal14);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal14_tree, root_0);
                        }
                        break;
                     case 3:
                        string_literal15 = (Token)this.match(this.input, 51, FOLLOW_51_in_logical_expression255);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           string_literal15_tree = (CommonTree)this.adaptor.create(string_literal15);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(string_literal15_tree, root_0);
                        }
                        break;
                     case 4:
                        char_literal16 = (Token)this.match(this.input, 52, FOLLOW_52_in_logical_expression260);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           char_literal16_tree = (CommonTree)this.adaptor.create(char_literal16);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal16_tree, root_0);
                        }
                        break;
                     case 5:
                        string_literal17 = (Token)this.match(this.input, 53, FOLLOW_53_in_logical_expression265);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           string_literal17_tree = (CommonTree)this.adaptor.create(string_literal17);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(string_literal17_tree, root_0);
                        }
                        break;
                     case 6:
                        string_literal18 = (Token)this.match(this.input, 54, FOLLOW_54_in_logical_expression270);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           string_literal18_tree = (CommonTree)this.adaptor.create(string_literal18);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(string_literal18_tree, root_0);
                        }
                        break;
                     case 7:
                        string_literal19 = (Token)this.match(this.input, 55, FOLLOW_55_in_logical_expression275);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           string_literal19_tree = (CommonTree)this.adaptor.create(string_literal19);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(string_literal19_tree, root_0);
                        }
                     }

                     this.pushFollow(FOLLOW_concat_expression_in_logical_expression279);
                     concat_expression20 = this.concat_expression();
                     --this.state._fsp;
                     if(this.state.failed) {
                        nvae = retval;
                        return nvae;
                     }

                     if(this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, concat_expression20.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if(this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var27) {
         this.reportError(var27);
         throw var27;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 6, logical_expression_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$concat_expression_return concat_expression() throws RecognitionException {
      ExcelFormulaParser$concat_expression_return retval = new ExcelFormulaParser$concat_expression_return();
      retval.start = this.input.LT(1);
      int concat_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal22 = null;
      ExcelFormulaParser$additive_expression_return additive_expression21 = null;
      ExcelFormulaParser$additive_expression_return additive_expression23 = null;
      CommonTree char_literal22_tree = null;

      try {
         ExcelFormulaParser$concat_expression_return var16;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 7)) {
            var16 = retval;
            return var16;
         } else {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_additive_expression_in_concat_expression291);
            additive_expression21 = this.additive_expression();
            --this.state._fsp;
            if(this.state.failed) {
               var16 = retval;
               return var16;
            } else {
               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, additive_expression21.getTree());
               }

               while(true) {
                  byte e = 2;
                  int LA4_0 = this.input.LA(1);
                  if(LA4_0 == 56) {
                     e = 1;
                  }

                  switch(e) {
                  case 1:
                     char_literal22 = (Token)this.match(this.input, 56, FOLLOW_56_in_concat_expression295);
                     ExcelFormulaParser$concat_expression_return var10;
                     if(this.state.failed) {
                        var10 = retval;
                        return var10;
                     }

                     if(this.state.backtracking == 0) {
                        char_literal22_tree = (CommonTree)this.adaptor.create(char_literal22);
                        root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal22_tree, root_0);
                     }

                     this.pushFollow(FOLLOW_additive_expression_in_concat_expression298);
                     additive_expression23 = this.additive_expression();
                     --this.state._fsp;
                     if(this.state.failed) {
                        var10 = retval;
                        return var10;
                     }

                     if(this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, additive_expression23.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if(this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         throw var14;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 7, concat_expression_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$additive_expression_return additive_expression() throws RecognitionException {
      ExcelFormulaParser$additive_expression_return retval = new ExcelFormulaParser$additive_expression_return();
      retval.start = this.input.LT(1);
      int additive_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal25 = null;
      Token char_literal26 = null;
      ExcelFormulaParser$multiplicative_expression_return multiplicative_expression24 = null;
      ExcelFormulaParser$multiplicative_expression_return multiplicative_expression27 = null;
      CommonTree char_literal25_tree = null;
      CommonTree char_literal26_tree = null;

      try {
         ExcelFormulaParser$additive_expression_return var20;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 8)) {
            var20 = retval;
            return var20;
         } else {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_multiplicative_expression_in_additive_expression309);
            multiplicative_expression24 = this.multiplicative_expression();
            --this.state._fsp;
            if(this.state.failed) {
               var20 = retval;
               return var20;
            } else {
               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, multiplicative_expression24.getTree());
               }

               while(true) {
                  byte e = 2;
                  int LA6_0 = this.input.LA(1);
                  if(LA6_0 >= 57 && LA6_0 <= 58) {
                     e = 1;
                  }

                  switch(e) {
                  case 1:
                     boolean alt5 = true;
                     int LA5_0 = this.input.LA(1);
                     ExcelFormulaParser$additive_expression_return nvae;
                     byte var21;
                     if(LA5_0 == 57) {
                        var21 = 1;
                     } else {
                        if(LA5_0 != 58) {
                           if(this.state.backtracking <= 0) {
                              NoViableAltException var22 = new NoViableAltException("", 5, 0, this.input);
                              throw var22;
                           }

                           this.state.failed = true;
                           nvae = retval;
                           return nvae;
                        }

                        var21 = 2;
                     }

                     switch(var21) {
                     case 1:
                        char_literal25 = (Token)this.match(this.input, 57, FOLLOW_57_in_additive_expression314);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           char_literal25_tree = (CommonTree)this.adaptor.create(char_literal25);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal25_tree, root_0);
                        }
                        break;
                     case 2:
                        char_literal26 = (Token)this.match(this.input, 58, FOLLOW_58_in_additive_expression317);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           char_literal26_tree = (CommonTree)this.adaptor.create(char_literal26);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal26_tree, root_0);
                        }
                     }

                     this.pushFollow(FOLLOW_multiplicative_expression_in_additive_expression321);
                     multiplicative_expression27 = this.multiplicative_expression();
                     --this.state._fsp;
                     if(this.state.failed) {
                        nvae = retval;
                        return nvae;
                     }

                     if(this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, multiplicative_expression27.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if(this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var18) {
         this.reportError(var18);
         throw var18;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 8, additive_expression_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$multiplicative_expression_return multiplicative_expression() throws RecognitionException {
      ExcelFormulaParser$multiplicative_expression_return retval = new ExcelFormulaParser$multiplicative_expression_return();
      retval.start = this.input.LT(1);
      int multiplicative_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal29 = null;
      Token char_literal30 = null;
      ExcelFormulaParser$exponentiation_expression_return exponentiation_expression28 = null;
      ExcelFormulaParser$exponentiation_expression_return exponentiation_expression31 = null;
      CommonTree char_literal29_tree = null;
      CommonTree char_literal30_tree = null;

      try {
         ExcelFormulaParser$multiplicative_expression_return var20;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 9)) {
            var20 = retval;
            return var20;
         } else {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_exponentiation_expression_in_multiplicative_expression333);
            exponentiation_expression28 = this.exponentiation_expression();
            --this.state._fsp;
            if(this.state.failed) {
               var20 = retval;
               return var20;
            } else {
               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, exponentiation_expression28.getTree());
               }

               while(true) {
                  byte e = 2;
                  int LA8_0 = this.input.LA(1);
                  if(LA8_0 >= 59 && LA8_0 <= 60) {
                     e = 1;
                  }

                  switch(e) {
                  case 1:
                     boolean alt7 = true;
                     int LA7_0 = this.input.LA(1);
                     ExcelFormulaParser$multiplicative_expression_return nvae;
                     byte var21;
                     if(LA7_0 == 59) {
                        var21 = 1;
                     } else {
                        if(LA7_0 != 60) {
                           if(this.state.backtracking > 0) {
                              this.state.failed = true;
                              nvae = retval;
                              return nvae;
                           }

                           NoViableAltException var22 = new NoViableAltException("", 7, 0, this.input);
                           throw var22;
                        }

                        var21 = 2;
                     }

                     switch(var21) {
                     case 1:
                        char_literal29 = (Token)this.match(this.input, 59, FOLLOW_59_in_multiplicative_expression338);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           char_literal29_tree = (CommonTree)this.adaptor.create(char_literal29);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal29_tree, root_0);
                        }
                        break;
                     case 2:
                        char_literal30 = (Token)this.match(this.input, 60, FOLLOW_60_in_multiplicative_expression341);
                        if(this.state.failed) {
                           nvae = retval;
                           return nvae;
                        }

                        if(this.state.backtracking == 0) {
                           char_literal30_tree = (CommonTree)this.adaptor.create(char_literal30);
                           root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal30_tree, root_0);
                        }
                     }

                     this.pushFollow(FOLLOW_exponentiation_expression_in_multiplicative_expression345);
                     exponentiation_expression31 = this.exponentiation_expression();
                     --this.state._fsp;
                     if(this.state.failed) {
                        nvae = retval;
                        return nvae;
                     }

                     if(this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, exponentiation_expression31.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if(this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var18) {
         this.reportError(var18);
         throw var18;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 9, multiplicative_expression_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$exponentiation_expression_return exponentiation_expression() throws RecognitionException {
      ExcelFormulaParser$exponentiation_expression_return retval = new ExcelFormulaParser$exponentiation_expression_return();
      retval.start = this.input.LT(1);
      int exponentiation_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal33 = null;
      ExcelFormulaParser$percent_expression_return percent_expression32 = null;
      ExcelFormulaParser$percent_expression_return percent_expression34 = null;
      CommonTree char_literal33_tree = null;

      ExcelFormulaParser$exponentiation_expression_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 10)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_percent_expression_in_exponentiation_expression358);
         percent_expression32 = this.percent_expression();
         --this.state._fsp;
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, percent_expression32.getTree());
            }

            while(true) {
               byte var16 = 2;
               int LA9_0 = this.input.LA(1);
               if(LA9_0 == 61) {
                  var16 = 1;
               }

               switch(var16) {
               case 1:
                  char_literal33 = (Token)this.match(this.input, 61, FOLLOW_61_in_exponentiation_expression362);
                  ExcelFormulaParser$exponentiation_expression_return var10;
                  if(this.state.failed) {
                     var10 = retval;
                     return var10;
                  }

                  if(this.state.backtracking == 0) {
                     char_literal33_tree = (CommonTree)this.adaptor.create(char_literal33);
                     root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal33_tree, root_0);
                  }

                  this.pushFollow(FOLLOW_percent_expression_in_exponentiation_expression365);
                  percent_expression34 = this.percent_expression();
                  --this.state._fsp;
                  if(this.state.failed) {
                     var10 = retval;
                     return var10;
                  }

                  if(this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, percent_expression34.getTree());
                  }
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  if(this.state.backtracking == 0) {
                     retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
               }
            }
         }

         e = retval;
      } catch (RecognitionException var14) {
         this.reportError(var14);
         throw var14;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 10, exponentiation_expression_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$percent_expression_return percent_expression() throws RecognitionException {
      ExcelFormulaParser$percent_expression_return retval = new ExcelFormulaParser$percent_expression_return();
      retval.start = this.input.LT(1);
      int percent_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal36 = null;
      ExcelFormulaParser$unary_expression_return unary_expression35 = null;
      ExcelFormulaParser$unary_expression_return unary_expression37 = null;
      Object char_literal36_tree = null;
      RewriteRuleTokenStream stream_62 = new RewriteRuleTokenStream(this.adaptor, "token 62");
      RewriteRuleSubtreeStream stream_unary_expression = new RewriteRuleSubtreeStream(this.adaptor, "rule unary_expression");

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 11)) {
            ExcelFormulaParser$percent_expression_return var19 = retval;
            return var19;
         } else {
            boolean e = true;
            int var18 = this.dfa10.predict(this.input);
            ExcelFormulaParser$percent_expression_return stream_retval;
            switch(var18) {
            case 1:
               this.pushFollow(FOLLOW_unary_expression_in_percent_expression378);
               unary_expression35 = this.unary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_unary_expression.add(unary_expression35.getTree());
               }

               char_literal36 = (Token)this.match(this.input, 62, FOLLOW_62_in_percent_expression380);
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_62.add(char_literal36);
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot(stream_62.nextNode(), root_1);
                  this.adaptor.addChild(root_1, stream_unary_expression.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_unary_expression_in_percent_expression401);
               unary_expression37 = this.unary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, unary_expression37.getTree());
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var16) {
         this.reportError(var16);
         throw var16;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 11, percent_expression_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$unary_expression_return unary_expression() throws RecognitionException {
      ExcelFormulaParser$unary_expression_return retval = new ExcelFormulaParser$unary_expression_return();
      retval.start = this.input.LT(1);
      int unary_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal38 = null;
      Token char_literal40 = null;
      ExcelFormulaParser$union_expression_return union_expression39 = null;
      ExcelFormulaParser$union_expression_return union_expression41 = null;
      ExcelFormulaParser$union_expression_return union_expression42 = null;
      Object char_literal38_tree = null;
      Object char_literal40_tree = null;
      RewriteRuleTokenStream stream_58 = new RewriteRuleTokenStream(this.adaptor, "token 58");
      RewriteRuleSubtreeStream stream_union_expression = new RewriteRuleSubtreeStream(this.adaptor, "rule union_expression");

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 12)) {
            ExcelFormulaParser$unary_expression_return var21 = retval;
            return var21;
         } else {
            boolean e = true;
            int var22 = this.dfa11.predict(this.input);
            ExcelFormulaParser$unary_expression_return stream_retval;
            switch(var22) {
            case 1:
               char_literal38 = (Token)this.match(this.input, 58, FOLLOW_58_in_unary_expression411);
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_58.add(char_literal38);
               }

               this.pushFollow(FOLLOW_union_expression_in_unary_expression414);
               union_expression39 = this.union_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_union_expression.add(union_expression39.getTree());
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(20, "UNARY_MINUS"), root_1);
                  this.adaptor.addChild(root_1, stream_union_expression.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               char_literal40 = (Token)this.match(this.input, 57, FOLLOW_57_in_unary_expression443);
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               this.pushFollow(FOLLOW_union_expression_in_unary_expression446);
               union_expression41 = this.union_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, union_expression41.getTree());
               }
               break;
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_union_expression_in_unary_expression471);
               union_expression42 = this.union_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, union_expression42.getTree());
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         throw var19;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 12, unary_expression_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$union_expression_return union_expression() throws RecognitionException {
      ExcelFormulaParser$union_expression_return retval = new ExcelFormulaParser$union_expression_return();
      retval.start = this.input.LT(1);
      int union_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$intersection_expression_return intersection_expression43 = null;

      ExcelFormulaParser$union_expression_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 13)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_intersection_expression_in_union_expression480);
         intersection_expression43 = this.intersection_expression();
         --this.state._fsp;
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, intersection_expression43.getTree());
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var9) {
         this.reportError(var9);
         throw var9;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 13, union_expression_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$intersection_expression_return intersection_expression() throws RecognitionException {
      ExcelFormulaParser$intersection_expression_return retval = new ExcelFormulaParser$intersection_expression_return();
      retval.start = this.input.LT(1);
      int intersection_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$reference_expression_return reference_expression44 = null;

      try {
         ExcelFormulaParser$intersection_expression_return e;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 14)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_reference_expression_in_intersection_expression490);
         reference_expression44 = this.reference_expression();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            this.adaptor.addChild(root_0, reference_expression44.getTree());
         }

         retval.stop = this.input.LT(-1);
         if(this.state.backtracking == 0) {
            retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         throw var9;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 14, intersection_expression_StartIndex);
         }

      }

      return retval;
   }

   public final ExcelFormulaParser$reference_expression_return reference_expression() throws RecognitionException {
      ExcelFormulaParser$reference_expression_return retval = new ExcelFormulaParser$reference_expression_return();
      retval.start = this.input.LT(1);
      int reference_expression_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal50 = null;
      Token char_literal52 = null;
      ExcelFormulaParser$cell_reference_return cell_reference45 = null;
      ExcelFormulaParser$function_call_return function_call46 = null;
      ExcelFormulaParser$primitive_return primitive47 = null;
      ExcelFormulaParser$const_array_return const_array48 = null;
      ExcelFormulaParser$area_reference_return area_reference49 = null;
      ExcelFormulaParser$primary_expression_return primary_expression51 = null;
      Object char_literal50_tree = null;
      Object char_literal52_tree = null;
      RewriteRuleTokenStream stream_64 = new RewriteRuleTokenStream(this.adaptor, "token 64");
      RewriteRuleTokenStream stream_63 = new RewriteRuleTokenStream(this.adaptor, "token 63");
      RewriteRuleSubtreeStream stream_primary_expression = new RewriteRuleSubtreeStream(this.adaptor, "rule primary_expression");

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 15)) {
            ExcelFormulaParser$reference_expression_return var25 = retval;
            return var25;
         } else {
            boolean e = true;
            int var26 = this.dfa12.predict(this.input);
            ExcelFormulaParser$reference_expression_return stream_retval;
            switch(var26) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_cell_reference_in_reference_expression504);
               cell_reference45 = this.cell_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, cell_reference45.getTree());
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_function_call_in_reference_expression533);
               function_call46 = this.function_call();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, function_call46.getTree());
               }
               break;
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_primitive_in_reference_expression548);
               primitive47 = this.primitive();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, primitive47.getTree());
               }
               break;
            case 4:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_const_array_in_reference_expression577);
               const_array48 = this.const_array();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, const_array48.getTree());
               }
               break;
            case 5:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_area_reference_in_reference_expression608);
               area_reference49 = this.area_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, area_reference49.getTree());
               }
               break;
            case 6:
               char_literal50 = (Token)this.match(this.input, 63, FOLLOW_63_in_reference_expression633);
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_63.add(char_literal50);
               }

               this.pushFollow(FOLLOW_primary_expression_in_reference_expression635);
               primary_expression51 = this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_primary_expression.add(primary_expression51.getTree());
               }

               char_literal52 = (Token)this.match(this.input, 64, FOLLOW_64_in_reference_expression637);
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_64.add(char_literal52);
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(6, "BRACKET_EXPR"), root_1);
                  this.adaptor.addChild(root_1, stream_primary_expression.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var23) {
         this.reportError(var23);
         throw var23;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 15, reference_expression_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$sheet_return sheet() throws RecognitionException {
      ExcelFormulaParser$sheet_return retval = new ExcelFormulaParser$sheet_return();
      retval.start = this.input.LT(1);
      int sheet_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal54 = null;
      ExcelFormulaParser$sheet_name_return sheet_name53 = null;
      Object char_literal54_tree = null;

      ExcelFormulaParser$sheet_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 16)) {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_sheet_name_in_sheet664);
            sheet_name53 = this.sheet_name();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, sheet_name53.getTree());
            }

            char_literal54 = (Token)this.match(this.input, 65, FOLLOW_65_in_sheet666);
            if(this.state.failed) {
               e = retval;
               return e;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var11) {
         this.reportError(var11);
         throw var11;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 16, sheet_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$sheet_name_return sheet_name() throws RecognitionException {
      ExcelFormulaParser$sheet_name_return retval = new ExcelFormulaParser$sheet_name_return();
      retval.start = this.input.LT(1);
      int sheet_name_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token c = null;
      Token i1 = null;
      Token i2 = null;
      Token d2 = null;
      Object c_tree = null;
      Object i1_tree = null;
      Object i2_tree = null;
      Object d2_tree = null;
      RewriteRuleTokenStream stream_DecimalLiteral = new RewriteRuleTokenStream(this.adaptor, "token DecimalLiteral");
      RewriteRuleTokenStream stream_Identifier = new RewriteRuleTokenStream(this.adaptor, "token Identifier");
      RewriteRuleTokenStream stream_CharacterSequence = new RewriteRuleTokenStream(this.adaptor, "token CharacterSequence");

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 17)) {
            ExcelFormulaParser$sheet_name_return e2 = retval;
            return e2;
         } else {
            boolean e = true;
            int LA13_0 = this.input.LA(1);
            byte e1;
            ExcelFormulaParser$sheet_name_return stream_retval1;
            if(LA13_0 == 25) {
               e1 = 1;
            } else {
               if(LA13_0 != 26) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     stream_retval1 = retval;
                     return stream_retval1;
                  }

                  NoViableAltException stream_retval2 = new NoViableAltException("", 13, 0, this.input);
                  throw stream_retval2;
               }

               int stream_retval = this.input.LA(2);
               if(stream_retval == 27) {
                  e1 = 3;
               } else {
                  if(stream_retval != 65) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        ExcelFormulaParser$sheet_name_return nvae1 = retval;
                        return nvae1;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 13, 2, this.input);
                     throw nvae;
                  }

                  e1 = 2;
               }
            }

            switch(e1) {
            case 1:
               c = (Token)this.match(this.input, 25, FOLLOW_CharacterSequence_in_sheet_name678);
               if(this.state.failed) {
                  stream_retval1 = retval;
                  return stream_retval1;
               }

               if(this.state.backtracking == 0) {
                  stream_CharacterSequence.add(c);
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.adaptor.addChild(root_0, new CommonTree(new CommonToken(21, c != null?c.getText():null)));
                  retval.tree = root_0;
               }
               break;
            case 2:
               i1 = (Token)this.match(this.input, 26, FOLLOW_Identifier_in_sheet_name705);
               if(this.state.failed) {
                  stream_retval1 = retval;
                  return stream_retval1;
               }

               if(this.state.backtracking == 0) {
                  stream_Identifier.add(i1);
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.adaptor.addChild(root_0, new CommonTree(new CommonToken(21, i1 != null?i1.getText():null)));
                  retval.tree = root_0;
               }
               break;
            case 3:
               i2 = (Token)this.match(this.input, 26, FOLLOW_Identifier_in_sheet_name733);
               if(this.state.failed) {
                  stream_retval1 = retval;
                  return stream_retval1;
               }

               if(this.state.backtracking == 0) {
                  stream_Identifier.add(i2);
               }

               d2 = (Token)this.match(this.input, 27, FOLLOW_DecimalLiteral_in_sheet_name737);
               if(this.state.failed) {
                  stream_retval1 = retval;
                  return stream_retval1;
               }

               if(this.state.backtracking == 0) {
                  stream_DecimalLiteral.add(d2);
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.adaptor.addChild(root_0, new CommonTree(new CommonToken(21, (i2 != null?i2.getText():null) + (d2 != null?d2.getText():null))));
                  retval.tree = root_0;
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var22) {
         this.reportError(var22);
         throw var22;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 17, sheet_name_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$area_reference_return area_reference() throws RecognitionException {
      ExcelFormulaParser$area_reference_return retval = new ExcelFormulaParser$area_reference_return();
      retval.start = this.input.LT(1);
      int area_reference_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token set56 = null;
      Token set59 = null;
      ExcelFormulaParser$range_return range55 = null;
      ExcelFormulaParser$area_reference_return area_reference57 = null;
      ExcelFormulaParser$vector_return vector58 = null;
      ExcelFormulaParser$area_reference_return area_reference60 = null;
      Object set56_tree = null;
      Object set59_tree = null;

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 18)) {
            ExcelFormulaParser$area_reference_return var22 = retval;
            return var22;
         } else {
            boolean e = true;
            int var21 = this.dfa16.predict(this.input);
            byte alt15;
            int LA15_0;
            int mse;
            ExcelFormulaParser$area_reference_return var23;
            MismatchedSetException var25;
            ExcelFormulaParser$area_reference_return var24;
            label369:
            switch(var21) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_range_in_area_reference749);
               range55 = this.range();
               --this.state._fsp;
               if(this.state.failed) {
                  var23 = retval;
                  return var23;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, range55.getTree());
               }

               while(true) {
                  alt15 = 2;
                  LA15_0 = this.input.LA(1);
                  if(LA15_0 == 66) {
                     mse = this.input.LA(2);
                     if(this.synpred26_ExcelFormula()) {
                        alt15 = 1;
                     }
                  } else if(LA15_0 == 67) {
                     mse = this.input.LA(2);
                     if(this.synpred26_ExcelFormula()) {
                        alt15 = 1;
                     }
                  }

                  switch(alt15) {
                  case 1:
                     set56 = this.input.LT(1);
                     if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
                        this.input.consume();
                        if(this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set56));
                        }

                        this.state.errorRecovery = false;
                        this.state.failed = false;
                        this.pushFollow(FOLLOW_area_reference_in_area_reference761);
                        area_reference57 = this.area_reference();
                        --this.state._fsp;
                        if(this.state.failed) {
                           var24 = retval;
                           return var24;
                        }

                        if(this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, area_reference57.getTree());
                        }
                        break;
                     }

                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        var24 = retval;
                        return var24;
                     }

                     var25 = new MismatchedSetException((BitSet)null, this.input);
                     throw var25;
                  default:
                     break label369;
                  }
               }
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_vector_in_area_reference780);
               vector58 = this.vector();
               --this.state._fsp;
               if(this.state.failed) {
                  var23 = retval;
                  return var23;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, vector58.getTree());
               }

               label357:
               while(true) {
                  alt15 = 2;
                  LA15_0 = this.input.LA(1);
                  if(LA15_0 == 66) {
                     mse = this.input.LA(2);
                     if(this.synpred29_ExcelFormula()) {
                        alt15 = 1;
                     }
                  } else if(LA15_0 == 67) {
                     mse = this.input.LA(2);
                     if(this.synpred29_ExcelFormula()) {
                        alt15 = 1;
                     }
                  }

                  switch(alt15) {
                  case 1:
                     set59 = this.input.LT(1);
                     if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
                        this.input.consume();
                        if(this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set59));
                        }

                        this.state.errorRecovery = false;
                        this.state.failed = false;
                        this.pushFollow(FOLLOW_area_reference_in_area_reference791);
                        area_reference60 = this.area_reference();
                        --this.state._fsp;
                        if(this.state.failed) {
                           var24 = retval;
                           return var24;
                        }

                        if(this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, area_reference60.getTree());
                        }
                        break;
                     }

                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        var24 = retval;
                        return var24;
                     }

                     var25 = new MismatchedSetException((BitSet)null, this.input);
                     throw var25;
                  default:
                     break label357;
                  }
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         throw var19;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 18, area_reference_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$range_return range() throws RecognitionException {
      ExcelFormulaParser$range_return retval = new ExcelFormulaParser$range_return();
      retval.start = this.input.LT(1);
      int range_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$cell_range_ref_return cell_range_ref61 = null;
      ExcelFormulaParser$local_named_range_return local_named_range62 = null;
      ExcelFormulaParser$worksheet_named_range_return worksheet_named_range63 = null;

      ExcelFormulaParser$range_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 19)) {
            byte var18;
            ExcelFormulaParser$range_return var21;
            boolean var17 = true;
            int nvae;
            int nvae1;
            NoViableAltException nvae2;
            ExcelFormulaParser$range_return nvae3;
            NoViableAltException var19;
            ExcelFormulaParser$range_return var20;
            NoViableAltException var23;
            NoViableAltException var22;
            int var25;
            ExcelFormulaParser$range_return var24;
            label648:
            switch(this.input.LA(1)) {
            case 25:
               nvae = this.input.LA(2);
               if(nvae != 65) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     var20 = retval;
                     return var20;
                  }

                  var22 = new NoViableAltException("", 17, 1, this.input);
                  throw var22;
               }

               nvae1 = this.input.LA(3);
               if(nvae1 == 26) {
                  var25 = this.input.LA(4);
                  if(var25 != 27 && var25 != 69) {
                     if(var25 != -1 && var25 != 47 && (var25 < 49 || var25 > 62) && var25 != 64 && (var25 < 66 || var25 > 67) && var25 != 70) {
                        if(this.state.backtracking > 0) {
                           this.state.failed = true;
                           nvae3 = retval;
                           return nvae3;
                        }

                        var23 = new NoViableAltException("", 17, 7, this.input);
                        throw var23;
                     }

                     var18 = 3;
                  } else {
                     var18 = 1;
                  }
               } else {
                  if(nvae1 != 69) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        var24 = retval;
                        return var24;
                     }

                     nvae2 = new NoViableAltException("", 17, 4, this.input);
                     throw nvae2;
                  }

                  var18 = 1;
               }
               break;
            case 26:
               switch(this.input.LA(2)) {
               case -1:
               case 47:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
               case 58:
               case 59:
               case 60:
               case 61:
               case 62:
               case 64:
               case 66:
               case 67:
               case 70:
                  var18 = 2;
                  break label648;
               case 0:
               case 1:
               case 2:
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
               case 39:
               case 40:
               case 41:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 48:
               case 63:
               case 68:
               default:
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     var21 = retval;
                     return var21;
                  }

                  var19 = new NoViableAltException("", 17, 2, this.input);
                  throw var19;
               case 27:
                  nvae = this.input.LA(3);
                  if(nvae == 68) {
                     var18 = 1;
                  } else {
                     if(nvae != 65) {
                        if(this.state.backtracking <= 0) {
                           var22 = new NoViableAltException("", 17, 5, this.input);
                           throw var22;
                        }

                        this.state.failed = true;
                        var20 = retval;
                        return var20;
                     }

                     nvae1 = this.input.LA(4);
                     if(nvae1 == 26) {
                        var25 = this.input.LA(5);
                        if(var25 != 27 && var25 != 69) {
                           if(var25 != -1 && var25 != 47 && (var25 < 49 || var25 > 62) && var25 != 64 && (var25 < 66 || var25 > 67) && var25 != 70) {
                              if(this.state.backtracking <= 0) {
                                 var23 = new NoViableAltException("", 17, 7, this.input);
                                 throw var23;
                              }

                              this.state.failed = true;
                              nvae3 = retval;
                              return nvae3;
                           }

                           var18 = 3;
                        } else {
                           var18 = 1;
                        }
                     } else {
                        if(nvae1 != 69) {
                           if(this.state.backtracking <= 0) {
                              nvae2 = new NoViableAltException("", 17, 4, this.input);
                              throw nvae2;
                           }

                           this.state.failed = true;
                           var24 = retval;
                           return var24;
                        }

                        var18 = 1;
                     }
                  }
                  break label648;
               case 65:
                  nvae = this.input.LA(3);
                  if(nvae == 26) {
                     nvae1 = this.input.LA(4);
                     if(nvae1 != 27 && nvae1 != 69) {
                        if(nvae1 != -1 && nvae1 != 47 && (nvae1 < 49 || nvae1 > 62) && nvae1 != 64 && (nvae1 < 66 || nvae1 > 67) && nvae1 != 70) {
                           if(this.state.backtracking > 0) {
                              this.state.failed = true;
                              var24 = retval;
                              return var24;
                           }

                           nvae2 = new NoViableAltException("", 17, 7, this.input);
                           throw nvae2;
                        }

                        var18 = 3;
                     } else {
                        var18 = 1;
                     }
                  } else {
                     if(nvae != 69) {
                        if(this.state.backtracking <= 0) {
                           var22 = new NoViableAltException("", 17, 4, this.input);
                           throw var22;
                        }

                        this.state.failed = true;
                        var20 = retval;
                        return var20;
                     }

                     var18 = 1;
                  }
                  break label648;
               case 69:
                  var18 = 1;
                  break label648;
               }
            case 69:
               var18 = 1;
               break;
            default:
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  var21 = retval;
                  return var21;
               }

               var19 = new NoViableAltException("", 17, 0, this.input);
               throw var19;
            }

            switch(var18) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_cell_range_ref_in_range803);
               cell_range_ref61 = this.cell_range_ref();
               --this.state._fsp;
               if(this.state.failed) {
                  var21 = retval;
                  return var21;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, cell_range_ref61.getTree());
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_local_named_range_in_range815);
               local_named_range62 = this.local_named_range();
               --this.state._fsp;
               if(this.state.failed) {
                  var21 = retval;
                  return var21;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, local_named_range62.getTree());
               }
               break;
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_worksheet_named_range_in_range827);
               worksheet_named_range63 = this.worksheet_named_range();
               --this.state._fsp;
               if(this.state.failed) {
                  var21 = retval;
                  return var21;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, worksheet_named_range63.getTree());
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var15) {
         this.reportError(var15);
         throw var15;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 19, range_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$local_named_range_return local_named_range() throws RecognitionException {
      ExcelFormulaParser$local_named_range_return retval = new ExcelFormulaParser$local_named_range_return();
      retval.start = this.input.LT(1);
      int local_named_range_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$named_range_return named_range64 = null;
      RewriteRuleSubtreeStream stream_named_range = new RewriteRuleSubtreeStream(this.adaptor, "rule named_range");

      ExcelFormulaParser$local_named_range_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 20)) {
            this.pushFollow(FOLLOW_named_range_in_local_named_range842);
            named_range64 = this.named_range();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               stream_named_range.add(named_range64.getTree());
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(24, "NAMED_RANGE"), root_1);
               this.adaptor.addChild(root_1, stream_named_range.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var11) {
         this.reportError(var11);
         throw var11;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 20, local_named_range_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$worksheet_named_range_return worksheet_named_range() throws RecognitionException {
      ExcelFormulaParser$worksheet_named_range_return retval = new ExcelFormulaParser$worksheet_named_range_return();
      retval.start = this.input.LT(1);
      int worksheet_named_range_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$sheet_return sheet65 = null;
      ExcelFormulaParser$named_range_return named_range66 = null;
      RewriteRuleSubtreeStream stream_named_range = new RewriteRuleSubtreeStream(this.adaptor, "rule named_range");
      RewriteRuleSubtreeStream stream_sheet = new RewriteRuleSubtreeStream(this.adaptor, "rule sheet");

      ExcelFormulaParser$worksheet_named_range_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 21)) {
            e = retval;
            return e;
         }

         this.pushFollow(FOLLOW_sheet_in_worksheet_named_range860);
         sheet65 = this.sheet();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_sheet.add(sheet65.getTree());
         }

         this.pushFollow(FOLLOW_named_range_in_worksheet_named_range862);
         named_range66 = this.named_range();
         --this.state._fsp;
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               stream_named_range.add(named_range66.getTree());
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(24, "NAMED_RANGE"), root_1);
               this.adaptor.addChild(root_1, stream_sheet.nextTree());
               this.adaptor.addChild(root_1, stream_named_range.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var13) {
         this.reportError(var13);
         throw var13;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 21, worksheet_named_range_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$named_range_return named_range() throws RecognitionException {
      ExcelFormulaParser$named_range_return retval = new ExcelFormulaParser$named_range_return();
      retval.start = this.input.LT(1);
      int named_range_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token Identifier67 = null;
      CommonTree Identifier67_tree = null;

      ExcelFormulaParser$named_range_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 22)) {
            root_0 = (CommonTree)this.adaptor.nil();
            Identifier67 = (Token)this.match(this.input, 26, FOLLOW_Identifier_in_named_range882);
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               Identifier67_tree = (CommonTree)this.adaptor.create(Identifier67);
               this.adaptor.addChild(root_0, Identifier67_tree);
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var10) {
         this.reportError(var10);
         throw var10;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 22, named_range_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$vector_return vector() throws RecognitionException {
      ExcelFormulaParser$vector_return retval = new ExcelFormulaParser$vector_return();
      retval.start = this.input.LT(1);
      int vector_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$column_range_return column_range68 = null;
      ExcelFormulaParser$row_range_return row_range69 = null;

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 23)) {
            ExcelFormulaParser$vector_return var15 = retval;
            return var15;
         } else {
            boolean e = true;
            int LA18_0 = this.input.LA(1);
            ExcelFormulaParser$vector_return nvae;
            byte var14;
            if((LA18_0 < 25 || LA18_0 > 26) && LA18_0 != 69) {
               if(LA18_0 != 27) {
                  if(this.state.backtracking <= 0) {
                     NoViableAltException var16 = new NoViableAltException("", 18, 0, this.input);
                     throw var16;
                  }

                  this.state.failed = true;
                  nvae = retval;
                  return nvae;
               }

               var14 = 2;
            } else {
               var14 = 1;
            }

            switch(var14) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_column_range_in_vector891);
               column_range68 = this.column_range();
               --this.state._fsp;
               if(this.state.failed) {
                  nvae = retval;
                  return nvae;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, column_range68.getTree());
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_row_range_in_vector902);
               row_range69 = this.row_range();
               --this.state._fsp;
               if(this.state.failed) {
                  nvae = retval;
                  return nvae;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, row_range69.getTree());
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         throw var12;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 23, vector_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$cell_range_ref_return cell_range_ref() throws RecognitionException {
      ExcelFormulaParser$cell_range_ref_return retval = new ExcelFormulaParser$cell_range_ref_return();
      retval.start = this.input.LT(1);
      int cell_range_ref_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal71 = null;
      ExcelFormulaParser$cell_reference_return cell_reference70 = null;
      ExcelFormulaParser$cell_reference_return cell_reference72 = null;
      Object char_literal71_tree = null;
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleSubtreeStream stream_cell_reference = new RewriteRuleSubtreeStream(this.adaptor, "rule cell_reference");

      ExcelFormulaParser$cell_range_ref_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 24)) {
            this.pushFollow(FOLLOW_cell_reference_in_cell_range_ref925);
            cell_reference70 = this.cell_reference();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               stream_cell_reference.add(cell_reference70.getTree());
            }

            char_literal71 = (Token)this.match(this.input, 68, FOLLOW_68_in_cell_range_ref927);
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               stream_68.add(char_literal71);
            }

            this.pushFollow(FOLLOW_cell_reference_in_cell_range_ref929);
            cell_reference72 = this.cell_reference();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               stream_cell_reference.add(cell_reference72.getTree());
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(17, "CELL_RANGE_REF"), root_1);
               this.adaptor.addChild(root_1, stream_cell_reference.nextTree());
               this.adaptor.addChild(root_1, stream_cell_reference.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if(this.state.backtracking == 0) {
               this.registerCellRangeRef(retval.tree);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var15) {
         this.reportError(var15);
         throw var15;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 24, cell_range_ref_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$column_range_return column_range() throws RecognitionException {
      ExcelFormulaParser$column_range_return retval = new ExcelFormulaParser$column_range_return();
      retval.start = this.input.LT(1);
      int column_range_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal74 = null;
      ExcelFormulaParser$column_reference_return column_reference73 = null;
      ExcelFormulaParser$column_reference_return column_reference75 = null;
      Object char_literal74_tree = null;
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleSubtreeStream stream_column_reference = new RewriteRuleSubtreeStream(this.adaptor, "rule column_reference");

      try {
         ExcelFormulaParser$column_range_return e;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 25)) {
            e = retval;
            return e;
         }

         this.pushFollow(FOLLOW_column_reference_in_column_range955);
         column_reference73 = this.column_reference();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_column_reference.add(column_reference73.getTree());
         }

         char_literal74 = (Token)this.match(this.input, 68, FOLLOW_68_in_column_range957);
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_68.add(char_literal74);
         }

         this.pushFollow(FOLLOW_column_reference_in_column_range959);
         column_reference75 = this.column_reference();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_column_reference.add(column_reference75.getTree());
         }

         if(this.state.backtracking == 0) {
            retval.tree = root_0;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
            root_0 = (CommonTree)this.adaptor.nil();
            CommonTree root_1 = (CommonTree)this.adaptor.nil();
            root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(18, "COLUMN_RANGE_REF"), root_1);
            this.adaptor.addChild(root_1, stream_column_reference.nextTree());
            this.adaptor.addChild(root_1, stream_column_reference.nextTree());
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
         }

         retval.stop = this.input.LT(-1);
         if(this.state.backtracking == 0) {
            retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }

         if(this.state.backtracking == 0) {
            this.registerColumnRangeRef(retval.tree);
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         throw var15;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 25, column_range_StartIndex);
         }

      }

      return retval;
   }

   public final ExcelFormulaParser$column_reference_return column_reference() throws RecognitionException {
      ExcelFormulaParser$column_reference_return retval = new ExcelFormulaParser$column_reference_return();
      retval.start = this.input.LT(1);
      int column_reference_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$worksheet_column_return worksheet_column76 = null;
      ExcelFormulaParser$local_column_return local_column77 = null;

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 26)) {
            ExcelFormulaParser$column_reference_return var16 = retval;
            return var16;
         } else {
            boolean e = true;
            byte var14;
            ExcelFormulaParser$column_reference_return var15;
            switch(this.input.LA(1)) {
            case 25:
               var14 = 1;
               break;
            case 26:
               int nvae = this.input.LA(2);
               if(nvae != 27 && nvae != 65) {
                  if(nvae != -1 && nvae != 47 && (nvae < 49 || nvae > 62) && nvae != 64 && (nvae < 66 || nvae > 68) && nvae != 70) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        ExcelFormulaParser$column_reference_return var18 = retval;
                        return var18;
                     }

                     NoViableAltException nvae1 = new NoViableAltException("", 19, 2, this.input);
                     throw nvae1;
                  }

                  var14 = 2;
               } else {
                  var14 = 1;
               }
               break;
            case 69:
               var14 = 2;
               break;
            default:
               if(this.state.backtracking <= 0) {
                  NoViableAltException var17 = new NoViableAltException("", 19, 0, this.input);
                  throw var17;
               }

               this.state.failed = true;
               var15 = retval;
               return var15;
            }

            switch(var14) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_worksheet_column_in_column_reference980);
               worksheet_column76 = this.worksheet_column();
               --this.state._fsp;
               if(this.state.failed) {
                  var15 = retval;
                  return var15;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, worksheet_column76.getTree());
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_local_column_in_column_reference1003);
               local_column77 = this.local_column();
               --this.state._fsp;
               if(this.state.failed) {
                  var15 = retval;
                  return var15;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, local_column77.getTree());
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         throw var12;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 26, column_reference_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$worksheet_column_return worksheet_column() throws RecognitionException {
      ExcelFormulaParser$worksheet_column_return retval = new ExcelFormulaParser$worksheet_column_return();
      retval.start = this.input.LT(1);
      int worksheet_column_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$sheet_return sheet78 = null;
      ExcelFormulaParser$local_column_return local_column79 = null;
      RewriteRuleSubtreeStream stream_local_column = new RewriteRuleSubtreeStream(this.adaptor, "rule local_column");
      RewriteRuleSubtreeStream stream_sheet = new RewriteRuleSubtreeStream(this.adaptor, "rule sheet");

      ExcelFormulaParser$worksheet_column_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 27)) {
            e = retval;
            return e;
         }

         this.pushFollow(FOLLOW_sheet_in_worksheet_column1018);
         sheet78 = this.sheet();
         --this.state._fsp;
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               stream_sheet.add(sheet78.getTree());
            }

            this.pushFollow(FOLLOW_local_column_in_worksheet_column1020);
            local_column79 = this.local_column();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               stream_local_column.add(local_column79.getTree());
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(15, "WORKSHEET_COLUMN_REF"), root_1);
               this.adaptor.addChild(root_1, stream_sheet.nextTree());
               this.adaptor.addChild(root_1, stream_local_column.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if(this.state.backtracking == 0) {
               this.registerWorksheetColumnRef(retval.tree);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var13) {
         this.reportError(var13);
         throw var13;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 27, worksheet_column_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$local_column_return local_column() throws RecognitionException {
      ExcelFormulaParser$local_column_return retval = new ExcelFormulaParser$local_column_return();
      retval.start = this.input.LT(1);
      int local_column_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal81 = null;
      ExcelFormulaParser$column_return column80 = null;
      ExcelFormulaParser$column_return column82 = null;
      Object char_literal81_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleSubtreeStream stream_column = new RewriteRuleSubtreeStream(this.adaptor, "rule column");

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 28)) {
            ExcelFormulaParser$local_column_return var19 = retval;
            return var19;
         } else {
            boolean e = true;
            int LA20_0 = this.input.LA(1);
            ExcelFormulaParser$local_column_return stream_retval;
            byte var20;
            if(LA20_0 == 26) {
               var20 = 1;
            } else {
               if(LA20_0 != 69) {
                  if(this.state.backtracking <= 0) {
                     NoViableAltException var21 = new NoViableAltException("", 20, 0, this.input);
                     throw var21;
                  }

                  this.state.failed = true;
                  stream_retval = retval;
                  return stream_retval;
               }

               var20 = 2;
            }

            CommonTree root_1;
            switch(var20) {
            case 1:
               this.pushFollow(FOLLOW_column_in_local_column1050);
               column80 = this.column();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_column.add(column80.getTree());
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(14, "COLUMN_REF"), root_1);
                  this.adaptor.addChild(root_1, stream_column.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
               }
               break;
            case 2:
               char_literal81 = (Token)this.match(this.input, 69, FOLLOW_69_in_local_column1074);
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_69.add(char_literal81);
               }

               this.pushFollow(FOLLOW_column_in_local_column1076);
               column82 = this.column();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_column.add(column82.getTree());
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(14, "COLUMN_REF"), root_1);
                  this.adaptor.addChild(root_1, stream_69.nextNode());
                  this.adaptor.addChild(root_1, stream_column.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if(this.state.backtracking == 0) {
               this.registerColumnRef(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         throw var17;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 28, local_column_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$row_range_return row_range() throws RecognitionException {
      ExcelFormulaParser$row_range_return retval = new ExcelFormulaParser$row_range_return();
      retval.start = this.input.LT(1);
      int row_range_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal84 = null;
      ExcelFormulaParser$row_return row83 = null;
      ExcelFormulaParser$row_return row85 = null;
      CommonTree char_literal84_tree = null;

      ExcelFormulaParser$row_range_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 29)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_row_in_row_range1097);
         row83 = this.row();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            this.adaptor.addChild(root_0, row83.getTree());
         }

         char_literal84 = (Token)this.match(this.input, 68, FOLLOW_68_in_row_range1099);
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               char_literal84_tree = (CommonTree)this.adaptor.create(char_literal84);
               this.adaptor.addChild(root_0, char_literal84_tree);
            }

            this.pushFollow(FOLLOW_row_in_row_range1101);
            row85 = this.row();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, row85.getTree());
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var12) {
         this.reportError(var12);
         throw var12;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 29, row_range_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$cell_reference_return cell_reference() throws RecognitionException {
      ExcelFormulaParser$cell_reference_return retval = new ExcelFormulaParser$cell_reference_return();
      retval.start = this.input.LT(1);
      int cell_reference_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$worksheet_cell_return worksheet_cell86 = null;
      ExcelFormulaParser$cell_return cell87 = null;

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 30)) {
            ExcelFormulaParser$cell_reference_return var17 = retval;
            return var17;
         } else {
            byte var14;
            ExcelFormulaParser$cell_reference_return var16;
            boolean e = true;
            NoViableAltException var15;
            label281:
            switch(this.input.LA(1)) {
            case 25:
               var14 = 1;
               break;
            case 26:
               switch(this.input.LA(2)) {
               case 27:
                  int nvae = this.input.LA(3);
                  if(nvae != -1 && nvae != 47 && (nvae < 49 || nvae > 62) && nvae != 64 && (nvae < 66 || nvae > 68) && nvae != 70) {
                     if(nvae != 65) {
                        if(this.state.backtracking > 0) {
                           this.state.failed = true;
                           ExcelFormulaParser$cell_reference_return var18 = retval;
                           return var18;
                        }

                        NoViableAltException nvae1 = new NoViableAltException("", 21, 4, this.input);
                        throw nvae1;
                     }

                     var14 = 1;
                  } else {
                     var14 = 2;
                  }
                  break label281;
               case 65:
                  var14 = 1;
                  break label281;
               case 69:
                  var14 = 2;
                  break label281;
               default:
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     var16 = retval;
                     return var16;
                  }

                  var15 = new NoViableAltException("", 21, 2, this.input);
                  throw var15;
               }
            case 69:
               var14 = 2;
               break;
            default:
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  var16 = retval;
                  return var16;
               }

               var15 = new NoViableAltException("", 21, 0, this.input);
               throw var15;
            }

            switch(var14) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_worksheet_cell_in_cell_reference1110);
               worksheet_cell86 = this.worksheet_cell();
               --this.state._fsp;
               if(this.state.failed) {
                  var16 = retval;
                  return var16;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, worksheet_cell86.getTree());
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_cell_in_cell_reference1127);
               cell87 = this.cell();
               --this.state._fsp;
               if(this.state.failed) {
                  var16 = retval;
                  return var16;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, cell87.getTree());
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         throw var12;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 30, cell_reference_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$worksheet_cell_return worksheet_cell() throws RecognitionException {
      ExcelFormulaParser$worksheet_cell_return retval = new ExcelFormulaParser$worksheet_cell_return();
      retval.start = this.input.LT(1);
      int worksheet_cell_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$sheet_return sheet88 = null;
      ExcelFormulaParser$cell_return cell89 = null;
      RewriteRuleSubtreeStream stream_cell = new RewriteRuleSubtreeStream(this.adaptor, "rule cell");
      RewriteRuleSubtreeStream stream_sheet = new RewriteRuleSubtreeStream(this.adaptor, "rule sheet");

      ExcelFormulaParser$worksheet_cell_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 31)) {
            e = retval;
            return e;
         }

         this.pushFollow(FOLLOW_sheet_in_worksheet_cell1144);
         sheet88 = this.sheet();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            stream_sheet.add(sheet88.getTree());
         }

         this.pushFollow(FOLLOW_cell_in_worksheet_cell1146);
         cell89 = this.cell();
         --this.state._fsp;
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               stream_cell.add(cell89.getTree());
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(13, "WORKSHEET_CELL_REF"), root_1);
               this.adaptor.addChild(root_1, stream_sheet.nextTree());
               this.adaptor.addChild(root_1, stream_cell.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if(this.state.backtracking == 0) {
               this.registerWorksheetCellRef(retval.tree);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var13) {
         this.reportError(var13);
         throw var13;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 31, worksheet_cell_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$cell_return cell() throws RecognitionException {
      ExcelFormulaParser$cell_return retval = new ExcelFormulaParser$cell_return();
      retval.start = this.input.LT(1);
      int cell_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal90 = null;
      Token char_literal91 = null;
      Token char_literal92 = null;
      Token char_literal93 = null;
      ExcelFormulaParser$column_return c = null;
      ExcelFormulaParser$row_return r = null;
      Object char_literal90_tree = null;
      Object char_literal91_tree = null;
      Object char_literal92_tree = null;
      Object char_literal93_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleSubtreeStream stream_column = new RewriteRuleSubtreeStream(this.adaptor, "rule column");
      RewriteRuleSubtreeStream stream_row = new RewriteRuleSubtreeStream(this.adaptor, "rule row");

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 32)) {
            ExcelFormulaParser$cell_return var28 = retval;
            return var28;
         } else {
            boolean e = true;
            int LA22_0 = this.input.LA(1);
            int stream_retval;
            ExcelFormulaParser$cell_return root_1;
            byte var27;
            ExcelFormulaParser$cell_return var31;
            NoViableAltException var30;
            if(LA22_0 == 26) {
               stream_retval = this.input.LA(2);
               if(stream_retval == 27) {
                  var27 = 1;
               } else {
                  if(stream_retval != 69) {
                     if(this.state.backtracking <= 0) {
                        var30 = new NoViableAltException("", 22, 1, this.input);
                        throw var30;
                     }

                     this.state.failed = true;
                     root_1 = retval;
                     return root_1;
                  }

                  var27 = 3;
               }
            } else {
               if(LA22_0 != 69) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     var31 = retval;
                     return var31;
                  }

                  NoViableAltException var34 = new NoViableAltException("", 22, 0, this.input);
                  throw var34;
               }

               stream_retval = this.input.LA(2);
               if(stream_retval != 26) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     root_1 = retval;
                     return root_1;
                  }

                  var30 = new NoViableAltException("", 22, 2, this.input);
                  throw var30;
               }

               int var29 = this.input.LA(3);
               if(var29 == 69) {
                  var27 = 4;
               } else {
                  if(var29 != 27) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        ExcelFormulaParser$cell_return var33 = retval;
                        return var33;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 22, 5, this.input);
                     throw nvae;
                  }

                  var27 = 2;
               }
            }

            CommonTree var32;
            switch(var27) {
            case 1:
               this.pushFollow(FOLLOW_column_in_cell1187);
               c = this.column();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_column.add(c.getTree());
               }

               this.pushFollow(FOLLOW_row_in_cell1195);
               r = this.row();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_row.add(r.getTree());
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(12, "CELL_REF"), var32);
                  this.adaptor.addChild(var32, stream_column.nextTree());
                  this.adaptor.addChild(var32, stream_row.nextTree());
                  this.adaptor.addChild(root_0, var32);
                  retval.tree = root_0;
               }
               break;
            case 2:
               char_literal90 = (Token)this.match(this.input, 69, FOLLOW_69_in_cell1224);
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_69.add(char_literal90);
               }

               this.pushFollow(FOLLOW_column_in_cell1228);
               c = this.column();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_column.add(c.getTree());
               }

               this.pushFollow(FOLLOW_row_in_cell1236);
               r = this.row();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_row.add(r.getTree());
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(12, "CELL_REF"), var32);
                  this.adaptor.addChild(var32, stream_69.nextNode());
                  this.adaptor.addChild(var32, stream_column.nextTree());
                  this.adaptor.addChild(var32, stream_row.nextTree());
                  this.adaptor.addChild(root_0, var32);
                  retval.tree = root_0;
               }
               break;
            case 3:
               this.pushFollow(FOLLOW_column_in_cell1270);
               c = this.column();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_column.add(c.getTree());
               }

               char_literal91 = (Token)this.match(this.input, 69, FOLLOW_69_in_cell1272);
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_69.add(char_literal91);
               }

               this.pushFollow(FOLLOW_row_in_cell1276);
               r = this.row();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_row.add(r.getTree());
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(12, "CELL_REF"), var32);
                  this.adaptor.addChild(var32, stream_column.nextTree());
                  this.adaptor.addChild(var32, stream_69.nextNode());
                  this.adaptor.addChild(var32, stream_row.nextTree());
                  this.adaptor.addChild(root_0, var32);
                  retval.tree = root_0;
               }
               break;
            case 4:
               char_literal92 = (Token)this.match(this.input, 69, FOLLOW_69_in_cell1304);
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_69.add(char_literal92);
               }

               this.pushFollow(FOLLOW_column_in_cell1308);
               c = this.column();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_column.add(c.getTree());
               }

               char_literal93 = (Token)this.match(this.input, 69, FOLLOW_69_in_cell1310);
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_69.add(char_literal93);
               }

               this.pushFollow(FOLLOW_row_in_cell1314);
               r = this.row();
               --this.state._fsp;
               if(this.state.failed) {
                  var31 = retval;
                  return var31;
               }

               if(this.state.backtracking == 0) {
                  stream_row.add(r.getTree());
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.nil();
                  var32 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(12, "CELL_REF"), var32);
                  this.adaptor.addChild(var32, stream_69.nextNode());
                  this.adaptor.addChild(var32, stream_column.nextTree());
                  this.adaptor.addChild(var32, stream_69.nextNode());
                  this.adaptor.addChild(var32, stream_row.nextTree());
                  this.adaptor.addChild(root_0, var32);
                  retval.tree = root_0;
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if(this.state.backtracking == 0) {
               this.registerCellRef(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var25) {
         this.reportError(var25);
         throw var25;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 32, cell_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$column_return column() throws RecognitionException {
      ExcelFormulaParser$column_return retval = new ExcelFormulaParser$column_return();
      retval.start = this.input.LT(1);
      int column_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token Identifier94 = null;
      CommonTree Identifier94_tree = null;

      ExcelFormulaParser$column_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 33)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         if(!this.isColumnId(this.input.LT(1).getText())) {
            if(this.state.backtracking > 0) {
               this.state.failed = true;
               e = retval;
               return e;
            }

            throw new FailedPredicateException(this.input, "column", " isColumnId(input.LT(1).getText()) ");
         }

         Identifier94 = (Token)this.match(this.input, 26, FOLLOW_Identifier_in_column1353);
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               Identifier94_tree = (CommonTree)this.adaptor.create(Identifier94);
               this.adaptor.addChild(root_0, Identifier94_tree);
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var10) {
         this.reportError(var10);
         throw var10;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 33, column_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$row_return row() throws RecognitionException {
      ExcelFormulaParser$row_return retval = new ExcelFormulaParser$row_return();
      retval.start = this.input.LT(1);
      int row_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token DecimalLiteral95 = null;
      CommonTree DecimalLiteral95_tree = null;

      try {
         ExcelFormulaParser$row_return e;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 34)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         DecimalLiteral95 = (Token)this.match(this.input, 27, FOLLOW_DecimalLiteral_in_row1362);
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            DecimalLiteral95_tree = (CommonTree)this.adaptor.create(DecimalLiteral95);
            this.adaptor.addChild(root_0, DecimalLiteral95_tree);
         }

         retval.stop = this.input.LT(-1);
         if(this.state.backtracking == 0) {
            retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }
      } catch (RecognitionException var10) {
         this.reportError(var10);
         throw var10;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 34, row_StartIndex);
         }

      }

      return retval;
   }

   public final ExcelFormulaParser$const_array_return const_array() throws RecognitionException {
      ExcelFormulaParser$const_array_return retval = new ExcelFormulaParser$const_array_return();
      retval.start = this.input.LT(1);
      int const_array_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal96 = null;
      Token char_literal98 = null;
      ExcelFormulaParser$array_element_list_return array_element_list97 = null;
      Object char_literal96_tree = null;
      Object char_literal98_tree = null;
      RewriteRuleTokenStream stream_49 = new RewriteRuleTokenStream(this.adaptor, "token 49");
      RewriteRuleTokenStream stream_48 = new RewriteRuleTokenStream(this.adaptor, "token 48");
      RewriteRuleSubtreeStream stream_array_element_list = new RewriteRuleSubtreeStream(this.adaptor, "rule array_element_list");

      ExcelFormulaParser$const_array_return stream_retval;
      try {
         ExcelFormulaParser$const_array_return var21;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 35)) {
            var21 = retval;
            return var21;
         }

         char_literal96 = (Token)this.match(this.input, 48, FOLLOW_48_in_const_array1379);
         if(this.state.failed) {
            var21 = retval;
            return var21;
         }

         if(this.state.backtracking == 0) {
            stream_48.add(char_literal96);
         }

         byte e = 2;
         int LA23_0 = this.input.LA(1);
         if(LA23_0 >= 27 && LA23_0 <= 32 || LA23_0 >= 57 && LA23_0 <= 58) {
            e = 1;
         }

         switch(e) {
         case 1:
            this.pushFollow(FOLLOW_array_element_list_in_const_array1382);
            array_element_list97 = this.array_element_list();
            --this.state._fsp;
            if(this.state.failed) {
               stream_retval = retval;
               return stream_retval;
            }

            if(this.state.backtracking == 0) {
               stream_array_element_list.add(array_element_list97.getTree());
            }
         }

         char_literal98 = (Token)this.match(this.input, 49, FOLLOW_49_in_const_array1386);
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               stream_49.add(char_literal98);
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot(new CommonTree(new CommonToken(22, "ARRAY_CONST")), root_1);
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         stream_retval = retval;
      } catch (RecognitionException var19) {
         this.reportError(var19);
         throw var19;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 35, const_array_StartIndex);
         }

      }

      return stream_retval;
   }

   public final ExcelFormulaParser$array_element_list_return array_element_list() throws RecognitionException {
      ExcelFormulaParser$array_element_list_return retval = new ExcelFormulaParser$array_element_list_return();
      retval.start = this.input.LT(1);
      int array_element_list_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal100 = null;
      ExcelFormulaParser$array_row_return array_row99 = null;
      ExcelFormulaParser$array_row_return array_row101 = null;
      Object char_literal100_tree = null;

      try {
         ExcelFormulaParser$array_element_list_return var16;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 36)) {
            var16 = retval;
            return var16;
         } else {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_array_row_in_array_element_list1402);
            array_row99 = this.array_row();
            --this.state._fsp;
            if(this.state.failed) {
               var16 = retval;
               return var16;
            } else {
               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, array_row99.getTree());
               }

               while(true) {
                  byte e = 2;
                  int LA24_0 = this.input.LA(1);
                  if(LA24_0 == 70) {
                     e = 1;
                  }

                  switch(e) {
                  case 1:
                     char_literal100 = (Token)this.match(this.input, 70, FOLLOW_70_in_array_element_list1407);
                     ExcelFormulaParser$array_element_list_return var10;
                     if(this.state.failed) {
                        var10 = retval;
                        return var10;
                     }

                     this.pushFollow(FOLLOW_array_row_in_array_element_list1411);
                     array_row101 = this.array_row();
                     --this.state._fsp;
                     if(this.state.failed) {
                        var10 = retval;
                        return var10;
                     }

                     if(this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, array_row101.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if(this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         throw var14;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 36, array_element_list_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$array_row_return array_row() throws RecognitionException {
      ExcelFormulaParser$array_row_return retval = new ExcelFormulaParser$array_row_return();
      retval.start = this.input.LT(1);
      int array_row_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$array_row_list_return array_row_list102 = null;
      RewriteRuleSubtreeStream stream_array_row_list = new RewriteRuleSubtreeStream(this.adaptor, "rule array_row_list");

      ExcelFormulaParser$array_row_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 37)) {
            this.pushFollow(FOLLOW_array_row_list_in_array_row1423);
            array_row_list102 = this.array_row_list();
            --this.state._fsp;
            if(this.state.failed) {
               e = retval;
               return e;
            }

            if(this.state.backtracking == 0) {
               stream_array_row_list.add(array_row_list102.getTree());
            }

            if(this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(23, "ARRAY_ROW"), root_1);
               this.adaptor.addChild(root_1, stream_array_row_list.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var11) {
         this.reportError(var11);
         throw var11;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 37, array_row_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$array_row_list_return array_row_list() throws RecognitionException {
      ExcelFormulaParser$array_row_list_return retval = new ExcelFormulaParser$array_row_list_return();
      retval.start = this.input.LT(1);
      int array_row_list_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal104 = null;
      ExcelFormulaParser$array_element_return array_element103 = null;
      ExcelFormulaParser$array_element_return array_element105 = null;
      Object char_literal104_tree = null;

      try {
         ExcelFormulaParser$array_row_list_return var16;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 38)) {
            var16 = retval;
            return var16;
         } else {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_array_element_in_array_row_list1441);
            array_element103 = this.array_element();
            --this.state._fsp;
            if(this.state.failed) {
               var16 = retval;
               return var16;
            } else {
               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, array_element103.getTree());
               }

               while(true) {
                  byte e = 2;
                  int LA25_0 = this.input.LA(1);
                  if(LA25_0 == 66) {
                     e = 1;
                  }

                  switch(e) {
                  case 1:
                     char_literal104 = (Token)this.match(this.input, 66, FOLLOW_66_in_array_row_list1446);
                     ExcelFormulaParser$array_row_list_return var10;
                     if(this.state.failed) {
                        var10 = retval;
                        return var10;
                     }

                     this.pushFollow(FOLLOW_array_element_in_array_row_list1450);
                     array_element105 = this.array_element();
                     --this.state._fsp;
                     if(this.state.failed) {
                        var10 = retval;
                        return var10;
                     }

                     if(this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, array_element105.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if(this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         throw var14;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 38, array_row_list_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$array_element_return array_element() throws RecognitionException {
      ExcelFormulaParser$array_element_return retval = new ExcelFormulaParser$array_element_return();
      retval.start = this.input.LT(1);
      int array_element_StartIndex = this.input.index();
      CommonTree root_0 = null;
      ExcelFormulaParser$primitive_return primitive106 = null;

      try {
         ExcelFormulaParser$array_element_return e;
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 39)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_primitive_in_array_element1463);
         primitive106 = this.primitive();
         --this.state._fsp;
         if(this.state.failed) {
            e = retval;
            return e;
         }

         if(this.state.backtracking == 0) {
            this.adaptor.addChild(root_0, primitive106.getTree());
         }

         retval.stop = this.input.LT(-1);
         if(this.state.backtracking == 0) {
            retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         throw var9;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 39, array_element_StartIndex);
         }

      }

      return retval;
   }

   public final ExcelFormulaParser$function_call_return function_call() throws RecognitionException {
      ExcelFormulaParser$function_call_return retval = new ExcelFormulaParser$function_call_return();
      retval.start = this.input.LT(1);
      int function_call_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token char_literal107 = null;
      Token char_literal109 = null;
      Token string_literal110 = null;
      Token string_literal111 = null;
      Token char_literal112 = null;
      Token char_literal114 = null;
      Token char_literal115 = null;
      Token char_literal117 = null;
      Token char_literal118 = null;
      Token char_literal120 = null;
      ExcelFormulaParser$function_name_return id = null;
      ExcelFormulaParser$argument_list_return argument_list108 = null;
      ExcelFormulaParser$primary_expression_return primary_expression113 = null;
      ExcelFormulaParser$primary_expression_return primary_expression116 = null;
      ExcelFormulaParser$primary_expression_return primary_expression119 = null;
      Object char_literal107_tree = null;
      Object char_literal109_tree = null;
      Object string_literal110_tree = null;
      Object string_literal111_tree = null;
      Object char_literal112_tree = null;
      Object char_literal114_tree = null;
      Object char_literal115_tree = null;
      Object char_literal117_tree = null;
      Object char_literal118_tree = null;
      Object char_literal120_tree = null;
      RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
      RewriteRuleTokenStream stream_64 = new RewriteRuleTokenStream(this.adaptor, "token 64");
      RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");
      RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
      RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
      RewriteRuleTokenStream stream_63 = new RewriteRuleTokenStream(this.adaptor, "token 63");
      RewriteRuleSubtreeStream stream_argument_list = new RewriteRuleSubtreeStream(this.adaptor, "rule argument_list");
      RewriteRuleSubtreeStream stream_primary_expression = new RewriteRuleSubtreeStream(this.adaptor, "rule primary_expression");
      RewriteRuleSubtreeStream stream_function_name = new RewriteRuleSubtreeStream(this.adaptor, "rule function_name");

      ExcelFormulaParser$function_call_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 40)) {
            boolean var54 = true;
            int LA30_0 = this.input.LA(1);
            byte var53;
            ExcelFormulaParser$function_call_return var63;
            if(LA30_0 == 26) {
               var53 = 1;
            } else {
               if(LA30_0 < 71 || LA30_0 > 72) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     var63 = retval;
                     return var63;
                  }

                  NoViableAltException var65 = new NoViableAltException("", 30, 0, this.input);
                  throw var65;
               }

               var53 = 2;
            }

            ExcelFormulaParser$function_call_return alt28;
            int LA27_0;
            byte var55;
            switch(var53) {
            case 1:
               this.pushFollow(FOLLOW_function_name_in_function_call1474);
               id = this.function_name();
               --this.state._fsp;
               if(this.state.failed) {
                  var63 = retval;
                  return var63;
               }

               if(this.state.backtracking == 0) {
                  stream_function_name.add(id.getTree());
               }

               char_literal107 = (Token)this.match(this.input, 63, FOLLOW_63_in_function_call1476);
               if(this.state.failed) {
                  var63 = retval;
                  return var63;
               }

               if(this.state.backtracking == 0) {
                  stream_63.add(char_literal107);
               }

               var55 = 2;
               LA27_0 = this.input.LA(1);
               if(LA27_0 >= 25 && LA27_0 <= 32 || LA27_0 == 48 || LA27_0 >= 57 && LA27_0 <= 58 || LA27_0 == 63 || LA27_0 == 69 || LA27_0 >= 71 && LA27_0 <= 72) {
                  var55 = 1;
               }

               switch(var55) {
               case 1:
                  this.pushFollow(FOLLOW_argument_list_in_function_call1478);
                  argument_list108 = this.argument_list();
                  --this.state._fsp;
                  if(this.state.failed) {
                     alt28 = retval;
                     return alt28;
                  }

                  if(this.state.backtracking == 0) {
                     stream_argument_list.add(argument_list108.getTree());
                  }
               }

               char_literal109 = (Token)this.match(this.input, 64, FOLLOW_64_in_function_call1481);
               if(this.state.failed) {
                  alt28 = retval;
                  return alt28;
               }

               if(this.state.backtracking == 0) {
                  stream_64.add(char_literal109);
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  RewriteRuleSubtreeStream var58 = new RewriteRuleSubtreeStream(this.adaptor, "rule id", id != null?id.tree:null);
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree var61 = (CommonTree)this.adaptor.nil();
                  var61 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(10, "FUNCTION_CALL"), var61);
                  this.adaptor.addChild(var61, var58.nextTree());
                  this.adaptor.addChild(var61, stream_63.nextNode());
                  if(stream_argument_list.hasNext()) {
                     this.adaptor.addChild(var61, stream_argument_list.nextTree());
                  }

                  stream_argument_list.reset();
                  this.adaptor.addChild(var61, stream_64.nextNode());
                  this.adaptor.addChild(root_0, var61);
                  retval.tree = root_0;
               }
               break;
            case 2:
               boolean alt27 = true;
               LA27_0 = this.input.LA(1);
               if(LA27_0 == 71) {
                  var55 = 1;
               } else {
                  if(LA27_0 != 72) {
                     if(this.state.backtracking <= 0) {
                        NoViableAltException var57 = new NoViableAltException("", 27, 0, this.input);
                        throw var57;
                     }

                     this.state.failed = true;
                     alt28 = retval;
                     return alt28;
                  }

                  var55 = 2;
               }

               switch(var55) {
               case 1:
                  string_literal110 = (Token)this.match(this.input, 71, FOLLOW_71_in_function_call1539);
                  if(this.state.failed) {
                     alt28 = retval;
                     return alt28;
                  }

                  if(this.state.backtracking == 0) {
                     stream_71.add(string_literal110);
                  }
                  break;
               case 2:
                  string_literal111 = (Token)this.match(this.input, 72, FOLLOW_72_in_function_call1541);
                  if(this.state.failed) {
                     alt28 = retval;
                     return alt28;
                  }

                  if(this.state.backtracking == 0) {
                     stream_72.add(string_literal111);
                  }
               }

               char_literal112 = (Token)this.match(this.input, 63, FOLLOW_63_in_function_call1544);
               if(this.state.failed) {
                  alt28 = retval;
                  return alt28;
               }

               if(this.state.backtracking == 0) {
                  stream_63.add(char_literal112);
               }

               this.pushFollow(FOLLOW_primary_expression_in_function_call1546);
               primary_expression113 = this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  alt28 = retval;
                  return alt28;
               }

               if(this.state.backtracking == 0) {
                  stream_primary_expression.add(primary_expression113.getTree());
               }

               boolean var62 = true;
               int LA28_0 = this.input.LA(1);
               ExcelFormulaParser$function_call_return alt29;
               byte var64;
               if(LA28_0 == 66) {
                  var64 = 1;
               } else {
                  if(LA28_0 != 70) {
                     if(this.state.backtracking <= 0) {
                        NoViableAltException var59 = new NoViableAltException("", 28, 0, this.input);
                        throw var59;
                     }

                     this.state.failed = true;
                     alt29 = retval;
                     return alt29;
                  }

                  var64 = 2;
               }

               switch(var64) {
               case 1:
                  char_literal114 = (Token)this.match(this.input, 66, FOLLOW_66_in_function_call1549);
                  if(this.state.failed) {
                     alt29 = retval;
                     return alt29;
                  }

                  if(this.state.backtracking == 0) {
                     stream_66.add(char_literal114);
                  }
                  break;
               case 2:
                  char_literal115 = (Token)this.match(this.input, 70, FOLLOW_70_in_function_call1551);
                  if(this.state.failed) {
                     alt29 = retval;
                     return alt29;
                  }

                  if(this.state.backtracking == 0) {
                     stream_70.add(char_literal115);
                  }
               }

               this.pushFollow(FOLLOW_primary_expression_in_function_call1554);
               primary_expression116 = this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  alt29 = retval;
                  return alt29;
               }

               if(this.state.backtracking == 0) {
                  stream_primary_expression.add(primary_expression116.getTree());
               }

               boolean var66 = true;
               int LA29_0 = this.input.LA(1);
               ExcelFormulaParser$function_call_return stream_retval;
               byte var56;
               if(LA29_0 == 66) {
                  var56 = 1;
               } else {
                  if(LA29_0 != 70) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        stream_retval = retval;
                        return stream_retval;
                     }

                     NoViableAltException var60 = new NoViableAltException("", 29, 0, this.input);
                     throw var60;
                  }

                  var56 = 2;
               }

               switch(var56) {
               case 1:
                  char_literal117 = (Token)this.match(this.input, 66, FOLLOW_66_in_function_call1557);
                  if(this.state.failed) {
                     stream_retval = retval;
                     return stream_retval;
                  }

                  if(this.state.backtracking == 0) {
                     stream_66.add(char_literal117);
                  }
                  break;
               case 2:
                  char_literal118 = (Token)this.match(this.input, 70, FOLLOW_70_in_function_call1559);
                  if(this.state.failed) {
                     stream_retval = retval;
                     return stream_retval;
                  }

                  if(this.state.backtracking == 0) {
                     stream_70.add(char_literal118);
                  }
               }

               this.pushFollow(FOLLOW_primary_expression_in_function_call1562);
               primary_expression119 = this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_primary_expression.add(primary_expression119.getTree());
               }

               char_literal120 = (Token)this.match(this.input, 64, FOLLOW_64_in_function_call1564);
               if(this.state.failed) {
                  stream_retval = retval;
                  return stream_retval;
               }

               if(this.state.backtracking == 0) {
                  stream_64.add(char_literal120);
               }

               if(this.state.backtracking == 0) {
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(19, "IF_FUNCTION"), root_1);
                  this.adaptor.addChild(root_1, stream_primary_expression.nextTree());
                  this.adaptor.addChild(root_1, stream_primary_expression.nextTree());
                  this.adaptor.addChild(root_1, stream_primary_expression.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }

         e = retval;
      } catch (RecognitionException var51) {
         this.reportError(var51);
         throw var51;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 40, function_call_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$function_name_return function_name() throws RecognitionException {
      ExcelFormulaParser$function_name_return retval = new ExcelFormulaParser$function_name_return();
      retval.start = this.input.LT(1);
      int function_name_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token i1 = null;
      Token i2 = null;
      Token Identifier121 = null;
      Object i1_tree = null;
      Object i2_tree = null;
      CommonTree Identifier121_tree = null;
      RewriteRuleTokenStream stream_DecimalLiteral = new RewriteRuleTokenStream(this.adaptor, "token DecimalLiteral");
      RewriteRuleTokenStream stream_Identifier = new RewriteRuleTokenStream(this.adaptor, "token Identifier");

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 41)) {
            ExcelFormulaParser$function_name_return e2 = retval;
            return e2;
         } else {
            boolean e = true;
            int LA31_0 = this.input.LA(1);
            ExcelFormulaParser$function_name_return stream_retval1;
            if(LA31_0 != 26) {
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  stream_retval1 = retval;
                  return stream_retval1;
               } else {
                  NoViableAltException stream_retval2 = new NoViableAltException("", 31, 0, this.input);
                  throw stream_retval2;
               }
            } else {
               int stream_retval = this.input.LA(2);
               byte e1;
               if(stream_retval == 27) {
                  e1 = 2;
               } else {
                  if(stream_retval != 63) {
                     if(this.state.backtracking <= 0) {
                        NoViableAltException nvae1 = new NoViableAltException("", 31, 1, this.input);
                        throw nvae1;
                     }

                     this.state.failed = true;
                     ExcelFormulaParser$function_name_return nvae = retval;
                     return nvae;
                  }

                  e1 = 1;
               }

               switch(e1) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  if(!this.isFunction(this.input.LT(1).getText())) {
                     if(this.state.backtracking <= 0) {
                        throw new FailedPredicateException(this.input, "function_name", "isFunction(input.LT(1).getText())");
                     }

                     this.state.failed = true;
                     stream_retval1 = retval;
                     return stream_retval1;
                  }

                  Identifier121 = (Token)this.match(this.input, 26, FOLLOW_Identifier_in_function_name1643);
                  if(this.state.failed) {
                     stream_retval1 = retval;
                     return stream_retval1;
                  }

                  if(this.state.backtracking == 0) {
                     Identifier121_tree = (CommonTree)this.adaptor.create(Identifier121);
                     this.adaptor.addChild(root_0, Identifier121_tree);
                  }
                  break;
               case 2:
                  if(!this.isFunction(this.input.LT(1).getText() + this.input.LT(2).getText())) {
                     if(this.state.backtracking <= 0) {
                        throw new FailedPredicateException(this.input, "function_name", "isFunction(input.LT(1).getText()+input.LT(2).getText())");
                     }

                     this.state.failed = true;
                     stream_retval1 = retval;
                     return stream_retval1;
                  }

                  i1 = (Token)this.match(this.input, 26, FOLLOW_Identifier_in_function_name1667);
                  if(this.state.failed) {
                     stream_retval1 = retval;
                     return stream_retval1;
                  }

                  if(this.state.backtracking == 0) {
                     stream_Identifier.add(i1);
                  }

                  i2 = (Token)this.match(this.input, 27, FOLLOW_DecimalLiteral_in_function_name1671);
                  if(this.state.failed) {
                     stream_retval1 = retval;
                     return stream_retval1;
                  }

                  if(this.state.backtracking == 0) {
                     stream_DecimalLiteral.add(i2);
                  }

                  if(this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null?retval.tree:null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, new CommonTree(new CommonToken(26, (i1 != null?i1.getText():null) + (i2 != null?i2.getText():null))));
                     retval.tree = root_0;
                  }
               }

               retval.stop = this.input.LT(-1);
               if(this.state.backtracking == 0) {
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
               }

               return retval;
            }
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         throw var19;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 41, function_name_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$argument_list_return argument_list() throws RecognitionException {
      ExcelFormulaParser$argument_list_return retval = new ExcelFormulaParser$argument_list_return();
      retval.start = this.input.LT(1);
      int argument_list_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token set123 = null;
      Token char_literal125 = null;
      ExcelFormulaParser$argument_return argument122 = null;
      ExcelFormulaParser$argument_return argument124 = null;
      Object set123_tree = null;
      Object char_literal125_tree = null;

      ExcelFormulaParser$argument_list_return e;
      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 42)) {
            e = retval;
            return e;
         }

         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_argument_in_argument_list1719);
         argument122 = this.argument();
         --this.state._fsp;
         if(!this.state.failed) {
            if(this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, argument122.getTree());
            }

            while(true) {
               byte var18 = 2;
               int LA33_0 = this.input.LA(1);
               if(LA33_0 == 66) {
                  int mse = this.input.LA(2);
                  if(mse >= 25 && mse <= 32 || mse == 48 || mse >= 57 && mse <= 58 || mse == 63 || mse == 69 || mse >= 71 && mse <= 72) {
                     var18 = 1;
                  }
               } else if(LA33_0 == 70) {
                  var18 = 1;
               }

               ExcelFormulaParser$argument_list_return var19;
               switch(var18) {
               case 1:
                  set123 = this.input.LT(1);
                  if(this.input.LA(1) != 66 && this.input.LA(1) != 70) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        var19 = retval;
                        return var19;
                     }

                     MismatchedSetException var20 = new MismatchedSetException((BitSet)null, this.input);
                     throw var20;
                  }

                  this.input.consume();
                  if(this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set123));
                  }

                  this.state.errorRecovery = false;
                  this.state.failed = false;
                  this.pushFollow(FOLLOW_argument_in_argument_list1729);
                  argument124 = this.argument();
                  --this.state._fsp;
                  if(this.state.failed) {
                     var19 = retval;
                     return var19;
                  }

                  if(this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, argument124.getTree());
                  }
                  break;
               default:
                  var18 = 2;
                  LA33_0 = this.input.LA(1);
                  if(LA33_0 == 66) {
                     var18 = 1;
                  }

                  switch(var18) {
                  case 1:
                     char_literal125 = (Token)this.match(this.input, 66, FOLLOW_66_in_argument_list1736);
                     if(this.state.failed) {
                        var19 = retval;
                        return var19;
                     }
                  default:
                     retval.stop = this.input.LT(-1);
                     if(this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
                  }
               }
            }
         }

         e = retval;
      } catch (RecognitionException var16) {
         this.reportError(var16);
         throw var16;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 42, argument_list_StartIndex);
         }

      }

      return e;
   }

   public final ExcelFormulaParser$argument_return argument() throws RecognitionException {
      ExcelFormulaParser$argument_return retval = new ExcelFormulaParser$argument_return();
      retval.start = this.input.LT(1);
      int argument_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token Error127 = null;
      ExcelFormulaParser$primary_expression_return primary_expression126 = null;
      CommonTree Error127_tree = null;

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 43)) {
            ExcelFormulaParser$argument_return var16 = retval;
            return var16;
         } else {
            boolean e = true;
            int LA34_0 = this.input.LA(1);
            byte var15;
            ExcelFormulaParser$argument_return var17;
            if((LA34_0 < 25 || LA34_0 > 27) && (LA34_0 < 29 || LA34_0 > 32) && LA34_0 != 48 && (LA34_0 < 57 || LA34_0 > 58) && LA34_0 != 63 && LA34_0 != 69 && (LA34_0 < 71 || LA34_0 > 72)) {
               if(LA34_0 != 28) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     var17 = retval;
                     return var17;
                  }

                  NoViableAltException var18 = new NoViableAltException("", 34, 0, this.input);
                  throw var18;
               }

               int nvae = this.input.LA(2);
               if(this.synpred51_ExcelFormula()) {
                  var15 = 1;
               } else {
                  var15 = 2;
               }
            } else {
               var15 = 1;
            }

            switch(var15) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_primary_expression_in_argument1748);
               primary_expression126 = this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  var17 = retval;
                  return var17;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, primary_expression126.getTree());
               }
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               Error127 = (Token)this.match(this.input, 28, FOLLOW_Error_in_argument1752);
               if(this.state.failed) {
                  var17 = retval;
                  return var17;
               }

               if(this.state.backtracking == 0) {
                  Error127_tree = (CommonTree)this.adaptor.create(Error127);
                  this.adaptor.addChild(root_0, Error127_tree);
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         throw var13;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 43, argument_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$primitive_return primitive() throws RecognitionException {
      ExcelFormulaParser$primitive_return retval = new ExcelFormulaParser$primitive_return();
      retval.start = this.input.LT(1);
      int primitive_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token set128 = null;
      Token DecimalLiteral129 = null;
      Token set130 = null;
      Token FloatingPointLiteral131 = null;
      Token BooleanConstant132 = null;
      Token Error134 = null;
      ExcelFormulaParser$string_return string133 = null;
      Object set128_tree = null;
      CommonTree DecimalLiteral129_tree = null;
      Object set130_tree = null;
      CommonTree FloatingPointLiteral131_tree = null;
      CommonTree BooleanConstant132_tree = null;
      CommonTree Error134_tree = null;

      try {
         if(this.state.backtracking > 0 && this.alreadyParsedRule(this.input, 44)) {
            ExcelFormulaParser$primitive_return var31 = retval;
            return var31;
         } else {
            boolean e = true;
            byte var26;
            ExcelFormulaParser$primitive_return var29;
            switch(this.input.LA(1)) {
            case 27:
               var26 = 1;
               break;
            case 28:
               var26 = 5;
               break;
            case 29:
               var26 = 2;
               break;
            case 30:
               var26 = 3;
               break;
            case 31:
            case 32:
               var26 = 4;
               break;
            case 57:
            case 58:
               int alt36 = this.input.LA(2);
               if(alt36 == 29) {
                  var26 = 2;
               } else {
                  if(alt36 != 27) {
                     if(this.state.backtracking <= 0) {
                        NoViableAltException var27 = new NoViableAltException("", 37, 1, this.input);
                        throw var27;
                     }

                     this.state.failed = true;
                     ExcelFormulaParser$primitive_return LA36_0 = retval;
                     return LA36_0;
                  }

                  var26 = 1;
               }
               break;
            default:
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  var29 = retval;
                  return var29;
               }

               NoViableAltException var33 = new NoViableAltException("", 37, 0, this.input);
               throw var33;
            }

            MismatchedSetException mse;
            byte var28;
            ExcelFormulaParser$primitive_return var30;
            int var32;
            label424:
            switch(var26) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               var28 = 2;
               var32 = this.input.LA(1);
               if(var32 >= 57 && var32 <= 58) {
                  var28 = 1;
               }

               switch(var28) {
               case 1:
                  set128 = this.input.LT(1);
                  if(this.input.LA(1) < 57 || this.input.LA(1) > 58) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        var30 = retval;
                        return var30;
                     }

                     mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  if(this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set128));
                  }

                  this.state.errorRecovery = false;
                  this.state.failed = false;
               default:
                  DecimalLiteral129 = (Token)this.match(this.input, 27, FOLLOW_DecimalLiteral_in_primitive1768);
                  if(this.state.failed) {
                     var30 = retval;
                     return var30;
                  }

                  if(this.state.backtracking == 0) {
                     DecimalLiteral129_tree = (CommonTree)this.adaptor.create(DecimalLiteral129);
                     this.adaptor.addChild(root_0, DecimalLiteral129_tree);
                  }
                  break label424;
               }
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               var28 = 2;
               var32 = this.input.LA(1);
               if(var32 >= 57 && var32 <= 58) {
                  var28 = 1;
               }

               switch(var28) {
               case 1:
                  set130 = this.input.LT(1);
                  if(this.input.LA(1) < 57 || this.input.LA(1) > 58) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        var30 = retval;
                        return var30;
                     }

                     mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  if(this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set130));
                  }

                  this.state.errorRecovery = false;
                  this.state.failed = false;
               default:
                  FloatingPointLiteral131 = (Token)this.match(this.input, 29, FOLLOW_FloatingPointLiteral_in_primitive1789);
                  if(this.state.failed) {
                     var30 = retval;
                     return var30;
                  }

                  if(this.state.backtracking == 0) {
                     FloatingPointLiteral131_tree = (CommonTree)this.adaptor.create(FloatingPointLiteral131);
                     this.adaptor.addChild(root_0, FloatingPointLiteral131_tree);
                  }
                  break label424;
               }
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               BooleanConstant132 = (Token)this.match(this.input, 30, FOLLOW_BooleanConstant_in_primitive1803);
               if(this.state.failed) {
                  var29 = retval;
                  return var29;
               }

               if(this.state.backtracking == 0) {
                  BooleanConstant132_tree = (CommonTree)this.adaptor.create(BooleanConstant132);
                  this.adaptor.addChild(root_0, BooleanConstant132_tree);
               }
               break;
            case 4:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_string_in_primitive1817);
               string133 = this.string();
               --this.state._fsp;
               if(this.state.failed) {
                  var29 = retval;
                  return var29;
               }

               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, string133.getTree());
               }
               break;
            case 5:
               root_0 = (CommonTree)this.adaptor.nil();
               Error134 = (Token)this.match(this.input, 28, FOLLOW_Error_in_primitive1831);
               if(this.state.failed) {
                  var29 = retval;
                  return var29;
               }

               if(this.state.backtracking == 0) {
                  Error134_tree = (CommonTree)this.adaptor.create(Error134);
                  this.adaptor.addChild(root_0, Error134_tree);
               }
            }

            retval.stop = this.input.LT(-1);
            if(this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            return retval;
         }
      } catch (RecognitionException var24) {
         this.reportError(var24);
         throw var24;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 44, primitive_StartIndex);
         }

      }
   }

   public final ExcelFormulaParser$string_return string() throws RecognitionException {
      ExcelFormulaParser$string_return retval = new ExcelFormulaParser$string_return();
      retval.start = this.input.LT(1);
      int string_StartIndex = this.input.index();
      CommonTree root_0 = null;
      Token set135 = null;
      Object set135_tree = null;

      ExcelFormulaParser$string_return e;
      try {
         if(this.state.backtracking <= 0 || !this.alreadyParsedRule(this.input, 45)) {
            root_0 = (CommonTree)this.adaptor.nil();
            set135 = this.input.LT(1);
            if(this.input.LA(1) >= 31 && this.input.LA(1) <= 32) {
               this.input.consume();
               if(this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set135));
               }

               this.state.errorRecovery = false;
               this.state.failed = false;
               retval.stop = this.input.LT(-1);
               if(this.state.backtracking == 0) {
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
               }

               return retval;
            }

            if(this.state.backtracking > 0) {
               this.state.failed = true;
               e = retval;
               return e;
            }

            MismatchedSetException e1 = new MismatchedSetException((BitSet)null, this.input);
            throw e1;
         }

         e = retval;
      } catch (RecognitionException var10) {
         this.reportError(var10);
         throw var10;
      } finally {
         if(this.state.backtracking > 0) {
            this.memoize(this.input, 45, string_StartIndex);
         }

      }

      return e;
   }

   public final void synpred15_ExcelFormula_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_unary_expression_in_synpred15_ExcelFormula378);
      this.unary_expression();
      --this.state._fsp;
      if(!this.state.failed) {
         this.match(this.input, 62, FOLLOW_62_in_synpred15_ExcelFormula380);
         if(!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred16_ExcelFormula_fragment() throws RecognitionException {
      this.match(this.input, 58, FOLLOW_58_in_synpred16_ExcelFormula411);
      if(!this.state.failed) {
         this.pushFollow(FOLLOW_union_expression_in_synpred16_ExcelFormula414);
         this.union_expression();
         --this.state._fsp;
         if(!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred17_ExcelFormula_fragment() throws RecognitionException {
      this.match(this.input, 57, FOLLOW_57_in_synpred17_ExcelFormula443);
      if(!this.state.failed) {
         this.pushFollow(FOLLOW_union_expression_in_synpred17_ExcelFormula446);
         this.union_expression();
         --this.state._fsp;
         if(!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred26_ExcelFormula_fragment() throws RecognitionException {
      if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
         this.input.consume();
         this.state.errorRecovery = false;
         this.state.failed = false;
         this.pushFollow(FOLLOW_area_reference_in_synpred26_ExcelFormula761);
         this.area_reference();
         --this.state._fsp;
         if(!this.state.failed) {
            ;
         }
      } else if(this.state.backtracking > 0) {
         this.state.failed = true;
      } else {
         MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
         throw mse;
      }
   }

   public final void synpred29_ExcelFormula_fragment() throws RecognitionException {
      if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
         this.input.consume();
         this.state.errorRecovery = false;
         this.state.failed = false;
         this.pushFollow(FOLLOW_area_reference_in_synpred29_ExcelFormula791);
         this.area_reference();
         --this.state._fsp;
         if(!this.state.failed) {
            ;
         }
      } else if(this.state.backtracking > 0) {
         this.state.failed = true;
      } else {
         MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
         throw mse;
      }
   }

   public final void synpred51_ExcelFormula_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_primary_expression_in_synpred51_ExcelFormula1748);
      this.primary_expression();
      --this.state._fsp;
      if(!this.state.failed) {
         ;
      }
   }

   public final boolean synpred26_ExcelFormula() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred26_ExcelFormula_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred51_ExcelFormula() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred51_ExcelFormula_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred29_ExcelFormula() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred29_ExcelFormula_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred16_ExcelFormula() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred16_ExcelFormula_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred15_ExcelFormula() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred15_ExcelFormula_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred17_ExcelFormula() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred17_ExcelFormula_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   // $FF: synthetic method
   static boolean accessMethod000(ExcelFormulaParser x0, String x1) {
      return x0.isFunction(x1);
   }

   // $FF: synthetic method
   static boolean accessMethod100(ExcelFormulaParser x0, String x1) {
      return x0.isColumnId(x1);
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod200(ExcelFormulaParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod300(ExcelFormulaParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod400(ExcelFormulaParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod500(ExcelFormulaParser x0) {
      return x0.state;
   }

   static {
      int numStates = DFA10_transitionS.length;
      DFA10_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
      }

      DFA11_transitionS = new String[]{"\b￿\b￿￿￿￿", "￿\b￿￿￿￿", "\b\t￿\b￿￿￿￿", "", "", "￿", "￿", "", "￿", "￿"};
      DFA11_eot = DFA.unpackEncodedString("\n￿");
      DFA11_eof = DFA.unpackEncodedString("\n￿");
      DFA11_min = DFA.unpackEncodedStringToUnsignedChars("￿ ￿ ");
      DFA11_max = DFA.unpackEncodedStringToUnsignedChars("H￿ ￿ ");
      DFA11_accept = DFA.unpackEncodedString("￿￿￿");
      DFA11_special = DFA.unpackEncodedString("￿ ￿}>");
      numStates = DFA11_transitionS.length;
      DFA11_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
      }

      DFA12_transitionS = new String[]{"￿\b￿￿\b￿￿", "\t", "\n￿￿\t\f", "\r", "", "", "￿￿￿￿￿", "", "", "*￿", "￿\t￿￿", "", "", "(￿", "￿￿￿￿", "", "", "￿￿￿￿￿", "", "￿￿￿￿￿", "￿￿￿￿￿", "", "(￿", "￿￿￿￿￿", "￿￿￿￿￿", "￿￿￿￿￿", "", "￿￿￿￿￿"};
      DFA12_eot = DFA.unpackEncodedString("￿");
      DFA12_eof = DFA.unpackEncodedString("￿￿￿￿￿￿￿￿");
      DFA12_min = DFA.unpackEncodedStringToUnsignedChars("A￿/￿/￿￿////");
      DFA12_max = DFA.unpackEncodedStringToUnsignedChars("HAF￿F￿EF￿EF￿FFEFF");
      DFA12_accept = DFA.unpackEncodedString("￿￿￿￿￿");
      DFA12_special = DFA.unpackEncodedString("￿}>");
      numStates = DFA12_transitionS.length;
      DFA12_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
      }

      DFA16_transitionS = new String[]{")￿", "", "￿￿￿", "\b", "", "\t*￿\n", "￿", "", "(￿", "￿￿￿￿", "", "(￿"};
      DFA16_eot = DFA.unpackEncodedString("\f￿");
      DFA16_eof = DFA.unpackEncodedString("￿￿￿");
      DFA16_min = DFA.unpackEncodedStringToUnsignedChars("A￿A￿");
      DFA16_max = DFA.unpackEncodedStringToUnsignedChars("EAF￿ED￿EFE");
      DFA16_accept = DFA.unpackEncodedString("￿￿￿");
      DFA16_special = DFA.unpackEncodedString("\f￿}>");
      numStates = DFA16_transitionS.length;
      DFA16_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
      }

      FOLLOW_formula_in_translation_unit157 = new BitSet(new long[]{0L});
      FOLLOW_EOF_in_translation_unit159 = new BitSet(new long[]{2L});
      FOLLOW_scalar_formula_in_formula167 = new BitSet(new long[]{2L});
      FOLLOW_array_formula_in_formula179 = new BitSet(new long[]{2L});
      FOLLOW_47_in_scalar_formula188 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_primary_expression_in_scalar_formula190 = new BitSet(new long[]{2L});
      FOLLOW_48_in_array_formula208 = new BitSet(new long[]{140737488355328L});
      FOLLOW_47_in_array_formula209 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_primary_expression_in_array_formula211 = new BitSet(new long[]{562949953421312L});
      FOLLOW_49_in_array_formula213 = new BitSet(new long[]{2L});
      FOLLOW_logical_expression_in_primary_expression231 = new BitSet(new long[]{2L});
      FOLLOW_concat_expression_in_logical_expression240 = new BitSet(new long[]{71072431619440642L});
      FOLLOW_47_in_logical_expression245 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_50_in_logical_expression250 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_51_in_logical_expression255 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_52_in_logical_expression260 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_53_in_logical_expression265 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_54_in_logical_expression270 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_55_in_logical_expression275 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_concat_expression_in_logical_expression279 = new BitSet(new long[]{71072431619440642L});
      FOLLOW_additive_expression_in_concat_expression291 = new BitSet(new long[]{72057594037927938L});
      FOLLOW_56_in_concat_expression295 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_additive_expression_in_concat_expression298 = new BitSet(new long[]{72057594037927938L});
      FOLLOW_multiplicative_expression_in_additive_expression309 = new BitSet(new long[]{432345564227567618L});
      FOLLOW_57_in_additive_expression314 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_58_in_additive_expression317 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_multiplicative_expression_in_additive_expression321 = new BitSet(new long[]{432345564227567618L});
      FOLLOW_exponentiation_expression_in_multiplicative_expression333 = new BitSet(new long[]{1729382256910270466L});
      FOLLOW_59_in_multiplicative_expression338 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_60_in_multiplicative_expression341 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_exponentiation_expression_in_multiplicative_expression345 = new BitSet(new long[]{1729382256910270466L});
      FOLLOW_percent_expression_in_exponentiation_expression358 = new BitSet(new long[]{2305843009213693954L});
      FOLLOW_61_in_exponentiation_expression362 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_percent_expression_in_exponentiation_expression365 = new BitSet(new long[]{2305843009213693954L});
      FOLLOW_unary_expression_in_percent_expression378 = new BitSet(new long[]{4611686018427387904L});
      FOLLOW_62_in_percent_expression380 = new BitSet(new long[]{2L});
      FOLLOW_unary_expression_in_percent_expression401 = new BitSet(new long[]{2L});
      FOLLOW_58_in_unary_expression411 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_union_expression_in_unary_expression414 = new BitSet(new long[]{2L});
      FOLLOW_57_in_unary_expression443 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_union_expression_in_unary_expression446 = new BitSet(new long[]{2L});
      FOLLOW_union_expression_in_unary_expression471 = new BitSet(new long[]{2L});
      FOLLOW_intersection_expression_in_union_expression480 = new BitSet(new long[]{2L});
      FOLLOW_reference_expression_in_intersection_expression490 = new BitSet(new long[]{2L});
      FOLLOW_cell_reference_in_reference_expression504 = new BitSet(new long[]{2L});
      FOLLOW_function_call_in_reference_expression533 = new BitSet(new long[]{2L});
      FOLLOW_primitive_in_reference_expression548 = new BitSet(new long[]{2L});
      FOLLOW_const_array_in_reference_expression577 = new BitSet(new long[]{2L});
      FOLLOW_area_reference_in_reference_expression608 = new BitSet(new long[]{2L});
      FOLLOW_63_in_reference_expression633 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_primary_expression_in_reference_expression635 = new BitSet(new long[]{0L, 1L});
      FOLLOW_64_in_reference_expression637 = new BitSet(new long[]{2L});
      FOLLOW_sheet_name_in_sheet664 = new BitSet(new long[]{0L, 2L});
      FOLLOW_65_in_sheet666 = new BitSet(new long[]{2L});
      FOLLOW_CharacterSequence_in_sheet_name678 = new BitSet(new long[]{2L});
      FOLLOW_Identifier_in_sheet_name705 = new BitSet(new long[]{2L});
      FOLLOW_Identifier_in_sheet_name733 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_sheet_name737 = new BitSet(new long[]{2L});
      FOLLOW_range_in_area_reference749 = new BitSet(new long[]{2L, 12L});
      FOLLOW_set_in_area_reference754 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_area_reference_in_area_reference761 = new BitSet(new long[]{2L, 12L});
      FOLLOW_vector_in_area_reference780 = new BitSet(new long[]{2L, 12L});
      FOLLOW_set_in_area_reference784 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_area_reference_in_area_reference791 = new BitSet(new long[]{2L, 12L});
      FOLLOW_cell_range_ref_in_range803 = new BitSet(new long[]{2L});
      FOLLOW_local_named_range_in_range815 = new BitSet(new long[]{2L});
      FOLLOW_worksheet_named_range_in_range827 = new BitSet(new long[]{2L});
      FOLLOW_named_range_in_local_named_range842 = new BitSet(new long[]{2L});
      FOLLOW_sheet_in_worksheet_named_range860 = new BitSet(new long[]{67108864L});
      FOLLOW_named_range_in_worksheet_named_range862 = new BitSet(new long[]{2L});
      FOLLOW_Identifier_in_named_range882 = new BitSet(new long[]{2L});
      FOLLOW_column_range_in_vector891 = new BitSet(new long[]{2L});
      FOLLOW_row_range_in_vector902 = new BitSet(new long[]{2L});
      FOLLOW_cell_reference_in_cell_range_ref925 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_cell_range_ref927 = new BitSet(new long[]{100663296L, 32L});
      FOLLOW_cell_reference_in_cell_range_ref929 = new BitSet(new long[]{2L});
      FOLLOW_column_reference_in_column_range955 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_column_range957 = new BitSet(new long[]{100663296L, 32L});
      FOLLOW_column_reference_in_column_range959 = new BitSet(new long[]{2L});
      FOLLOW_worksheet_column_in_column_reference980 = new BitSet(new long[]{2L});
      FOLLOW_local_column_in_column_reference1003 = new BitSet(new long[]{2L});
      FOLLOW_sheet_in_worksheet_column1018 = new BitSet(new long[]{100663296L, 32L});
      FOLLOW_local_column_in_worksheet_column1020 = new BitSet(new long[]{2L});
      FOLLOW_column_in_local_column1050 = new BitSet(new long[]{2L});
      FOLLOW_69_in_local_column1074 = new BitSet(new long[]{67108864L});
      FOLLOW_column_in_local_column1076 = new BitSet(new long[]{2L});
      FOLLOW_row_in_row_range1097 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_row_range1099 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_row_in_row_range1101 = new BitSet(new long[]{2L});
      FOLLOW_worksheet_cell_in_cell_reference1110 = new BitSet(new long[]{2L});
      FOLLOW_cell_in_cell_reference1127 = new BitSet(new long[]{2L});
      FOLLOW_sheet_in_worksheet_cell1144 = new BitSet(new long[]{100663296L, 32L});
      FOLLOW_cell_in_worksheet_cell1146 = new BitSet(new long[]{2L});
      FOLLOW_column_in_cell1187 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_row_in_cell1195 = new BitSet(new long[]{2L});
      FOLLOW_69_in_cell1224 = new BitSet(new long[]{67108864L});
      FOLLOW_column_in_cell1228 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_row_in_cell1236 = new BitSet(new long[]{2L});
      FOLLOW_column_in_cell1270 = new BitSet(new long[]{0L, 32L});
      FOLLOW_69_in_cell1272 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_row_in_cell1276 = new BitSet(new long[]{2L});
      FOLLOW_69_in_cell1304 = new BitSet(new long[]{67108864L});
      FOLLOW_column_in_cell1308 = new BitSet(new long[]{0L, 32L});
      FOLLOW_69_in_cell1310 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_row_in_cell1314 = new BitSet(new long[]{2L});
      FOLLOW_Identifier_in_column1353 = new BitSet(new long[]{2L});
      FOLLOW_DecimalLiteral_in_row1362 = new BitSet(new long[]{2L});
      FOLLOW_48_in_const_array1379 = new BitSet(new long[]{432908522636705792L});
      FOLLOW_array_element_list_in_const_array1382 = new BitSet(new long[]{562949953421312L});
      FOLLOW_49_in_const_array1386 = new BitSet(new long[]{2L});
      FOLLOW_array_row_in_array_element_list1402 = new BitSet(new long[]{2L, 64L});
      FOLLOW_70_in_array_element_list1407 = new BitSet(new long[]{432345572683284480L});
      FOLLOW_array_row_in_array_element_list1411 = new BitSet(new long[]{2L, 64L});
      FOLLOW_array_row_list_in_array_row1423 = new BitSet(new long[]{2L});
      FOLLOW_array_element_in_array_row_list1441 = new BitSet(new long[]{2L, 4L});
      FOLLOW_66_in_array_row_list1446 = new BitSet(new long[]{432345572683284480L});
      FOLLOW_array_element_in_array_row_list1450 = new BitSet(new long[]{2L, 4L});
      FOLLOW_primitive_in_array_element1463 = new BitSet(new long[]{2L});
      FOLLOW_function_name_in_function_call1474 = new BitSet(new long[]{Long.MIN_VALUE});
      FOLLOW_63_in_function_call1476 = new BitSet(new long[]{-8790744989094117376L, 417L});
      FOLLOW_argument_list_in_function_call1478 = new BitSet(new long[]{0L, 1L});
      FOLLOW_64_in_function_call1481 = new BitSet(new long[]{2L});
      FOLLOW_71_in_function_call1539 = new BitSet(new long[]{Long.MIN_VALUE});
      FOLLOW_72_in_function_call1541 = new BitSet(new long[]{Long.MIN_VALUE});
      FOLLOW_63_in_function_call1544 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_primary_expression_in_function_call1546 = new BitSet(new long[]{0L, 68L});
      FOLLOW_66_in_function_call1549 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_70_in_function_call1551 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_primary_expression_in_function_call1554 = new BitSet(new long[]{0L, 68L});
      FOLLOW_66_in_function_call1557 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_70_in_function_call1559 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_primary_expression_in_function_call1562 = new BitSet(new long[]{0L, 1L});
      FOLLOW_64_in_function_call1564 = new BitSet(new long[]{2L});
      FOLLOW_Identifier_in_function_name1643 = new BitSet(new long[]{2L});
      FOLLOW_Identifier_in_function_name1667 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_function_name1671 = new BitSet(new long[]{2L});
      FOLLOW_argument_in_argument_list1719 = new BitSet(new long[]{2L, 68L});
      FOLLOW_set_in_argument_list1723 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_argument_in_argument_list1729 = new BitSet(new long[]{2L, 68L});
      FOLLOW_66_in_argument_list1736 = new BitSet(new long[]{2L});
      FOLLOW_primary_expression_in_argument1748 = new BitSet(new long[]{2L});
      FOLLOW_Error_in_argument1752 = new BitSet(new long[]{2L});
      FOLLOW_set_in_primitive1761 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_primitive1768 = new BitSet(new long[]{2L});
      FOLLOW_set_in_primitive1782 = new BitSet(new long[]{536870912L});
      FOLLOW_FloatingPointLiteral_in_primitive1789 = new BitSet(new long[]{2L});
      FOLLOW_BooleanConstant_in_primitive1803 = new BitSet(new long[]{2L});
      FOLLOW_string_in_primitive1817 = new BitSet(new long[]{2L});
      FOLLOW_Error_in_primitive1831 = new BitSet(new long[]{2L});
      FOLLOW_set_in_string0 = new BitSet(new long[]{2L});
      FOLLOW_unary_expression_in_synpred15_ExcelFormula378 = new BitSet(new long[]{4611686018427387904L});
      FOLLOW_62_in_synpred15_ExcelFormula380 = new BitSet(new long[]{2L});
      FOLLOW_58_in_synpred16_ExcelFormula411 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_union_expression_in_synpred16_ExcelFormula414 = new BitSet(new long[]{2L});
      FOLLOW_57_in_synpred17_ExcelFormula443 = new BitSet(new long[]{-8790744989094117376L, 416L});
      FOLLOW_union_expression_in_synpred17_ExcelFormula446 = new BitSet(new long[]{2L});
      FOLLOW_set_in_synpred26_ExcelFormula754 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_area_reference_in_synpred26_ExcelFormula761 = new BitSet(new long[]{2L});
      FOLLOW_set_in_synpred29_ExcelFormula784 = new BitSet(new long[]{234881024L, 32L});
      FOLLOW_area_reference_in_synpred29_ExcelFormula791 = new BitSet(new long[]{2L});
      FOLLOW_primary_expression_in_synpred51_ExcelFormula1748 = new BitSet(new long[]{2L});
   }
}
