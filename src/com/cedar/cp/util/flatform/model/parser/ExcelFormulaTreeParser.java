// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$DFA2;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$DFA22;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$DFA24;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$DFA25;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$DFA26;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$DFA30;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$DFA6;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$function_call_scope;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser$if_statement_scope;
import com.cedar.cp.util.flatform.model.parser.FormulaExecutor;
import java.util.ArrayList;
import java.util.Stack;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;

public class ExcelFormulaTreeParser extends TreeParser {

   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "SCALAR_FORMULA", "ARRAY_FORMULA", "BRACKET_EXPR", "LOGICAL_EXPR", "CONCAT_EXPR", "ADDITIVE_EXPR", "FUNCTION_CALL", "RC_CELL_REF", "CELL_REF", "WORKSHEET_CELL_REF", "COLUMN_REF", "WORKSHEET_COLUMN_REF", "ROW_REF", "CELL_RANGE_REF", "COLUMN_RANGE_REF", "IF_FUNCTION", "UNARY_MINUS", "SHEET_REF", "ARRAY_CONST", "ARRAY_ROW", "NAMED_RANGE", "CharacterSequence", "Identifier", "DecimalLiteral", "Error", "FloatingPointLiteral", "BooleanConstant", "CharacterLiteral", "StringLiteral", "HexDigit", "IntegerTypeSuffix", "HexLiteral", "OctalLiteral", "DecDigit", "Exponent", "FloatTypeSuffix", "EscapeSequence", "UnicodeEscape", "OctalEscape", "WS", "Letter", "COMMENT", "LINE_COMMENT", "\'=\'", "\'{\'", "\'}\'", "\'<\'", "\'<=\'", "\'>\'", "\'>=\'", "\'<>\'", "\'!=\'", "\'&\'", "\'+\'", "\'-\'", "\'*\'", "\'/\'", "\'^\'", "\'%\'", "\'(\'", "\')\'", "\'!\'", "\',\'", "\' \'", "\':\'", "\'$\'", "\';\'", "\'if\'", "\'IF\'", "ARRAY_COST", "\'[workbook_path/filename.xls]\'", "\'strsq\'", "\'c\'", "\'r\'", "\'[\'", "\']\'"};
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
   public static final int ARRAY_COST = 73;
   public static final int BRACKET_EXPR = 6;
   public static final int T__76 = 76;
   public static final int T__75 = 75;
   public static final int FUNCTION_CALL = 10;
   public static final int T__74 = 74;
   public static final int EscapeSequence = 40;
   public static final int OctalEscape = 42;
   public static final int Letter = 44;
   public static final int T__79 = 79;
   public static final int T__78 = 78;
   public static final int T__77 = 77;
   private Stack mStack;
   private FormulaExecutor mExecutor;
   private boolean mSemanticProcessingEnabled;
   protected Stack function_call_stack;
   protected Stack if_statement_stack;
   protected ExcelFormulaTreeParser$DFA2 dfa2;
   protected ExcelFormulaTreeParser$DFA6 dfa6;
   protected ExcelFormulaTreeParser$DFA22 dfa22;
   protected ExcelFormulaTreeParser$DFA24 dfa24;
   protected ExcelFormulaTreeParser$DFA25 dfa25;
   protected ExcelFormulaTreeParser$DFA26 dfa26;
   protected ExcelFormulaTreeParser$DFA30 dfa30;
   static final String DFA2_eotS = "(￿";
   static final String DFA2_eofS = "(￿";
   static final String DFA2_minS = "\b￿￿";
   static final String DFA2_maxS = "M\b￿￿";
   static final String DFA2_acceptS = "￿￿￿￿￿\b\t￿";
   static final String DFA2_specialS = "(￿}>";
   static final String[] DFA2_transitionS = new String[]{" ￿￿￿￿￿￿￿\b\n\t\r!\n￿￿", "", "", "", "", "", "", "", "", "\"￿￿", "\"￿￿", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
   static final short[] DFA2_eot = DFA.unpackEncodedString("(￿");
   static final short[] DFA2_eof = DFA.unpackEncodedString("(￿");
   static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars("\b￿￿");
   static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars("M\b￿￿");
   static final short[] DFA2_accept = DFA.unpackEncodedString("￿￿￿￿￿\b\t￿");
   static final short[] DFA2_special = DFA.unpackEncodedString("(￿}>");
   static final short[][] DFA2_transition;
   static final String DFA6_eotS = "￿";
   static final String DFA6_eofS = "￿";
   static final String DFA6_minS = "\n￿￿ ￿";
   static final String DFA6_maxS = "M￿￿ ￿";
   static final String DFA6_acceptS = "￿￿￿￿￿\t\n￿\b";
   static final String DFA6_specialS = "￿ ￿}>";
   static final String[] DFA6_transitionS;
   static final short[] DFA6_eot;
   static final short[] DFA6_eof;
   static final char[] DFA6_min;
   static final char[] DFA6_max;
   static final short[] DFA6_accept;
   static final short[] DFA6_special;
   static final short[][] DFA6_transition;
   static final String DFA22_eotS = "(￿";
   static final String DFA22_eofS = "￿&￿";
   static final String DFA22_minS = "L￿ $￿";
   static final String DFA22_maxS = "LN￿ $￿";
   static final String DFA22_acceptS = "￿￿#￿";
   static final String DFA22_specialS = "￿ $￿}>";
   static final String[] DFA22_transitionS;
   static final short[] DFA22_eot;
   static final short[] DFA22_eof;
   static final char[] DFA22_min;
   static final char[] DFA22_max;
   static final short[] DFA22_accept;
   static final short[] DFA22_special;
   static final short[][] DFA22_transition;
   static final String DFA24_eotS = "\'￿";
   static final String DFA24_eofS = "&￿";
   static final String DFA24_minS = "&￿";
   static final String DFA24_maxS = "M&￿";
   static final String DFA24_acceptS = "￿$￿";
   static final String DFA24_specialS = "\'￿}>";
   static final String[] DFA24_transitionS;
   static final short[] DFA24_eot;
   static final short[] DFA24_eof;
   static final char[] DFA24_min;
   static final char[] DFA24_max;
   static final short[] DFA24_accept;
   static final short[] DFA24_special;
   static final short[][] DFA24_transition;
   static final String DFA25_eotS = "Ē￿";
   static final String DFA25_eofS = "đ￿";
   static final String DFA25_minS = "\t￿￿ ￿ ￿ å￿";
   static final String DFA25_maxS = "M\t￿￿ ￿ ￿ å￿";
   static final String DFA25_acceptS = "￿Q￿¾￿";
   static final String DFA25_specialS = "￿ ￿￿\b\tå￿}>";
   static final String[] DFA25_transitionS;
   static final short[] DFA25_eot;
   static final short[] DFA25_eof;
   static final char[] DFA25_min;
   static final char[] DFA25_max;
   static final short[] DFA25_accept;
   static final short[] DFA25_special;
   static final short[][] DFA25_transition;
   static final String DFA26_eotS = "#￿";
   static final String DFA26_eofS = "#￿";
   static final String DFA26_minS = "\"￿";
   static final String DFA26_maxS = "M\"￿";
   static final String DFA26_acceptS = "￿ ￿";
   static final String DFA26_specialS = "#￿}>";
   static final String[] DFA26_transitionS;
   static final short[] DFA26_eot;
   static final short[] DFA26_eof;
   static final char[] DFA26_min;
   static final char[] DFA26_max;
   static final short[] DFA26_accept;
   static final short[] DFA26_special;
   static final short[][] DFA26_transition;
   static final String DFA30_eotS = "\n￿";
   static final String DFA30_eofS = "\n￿";
   static final String DFA30_minS = "\b￿";
   static final String DFA30_maxS = ":\b￿";
   static final String DFA30_acceptS = "￿￿";
   static final String DFA30_specialS = "\n￿}>";
   static final String[] DFA30_transitionS;
   static final short[] DFA30_eot;
   static final short[] DFA30_eof;
   static final char[] DFA30_min;
   static final char[] DFA30_max;
   static final short[] DFA30_accept;
   static final short[] DFA30_special;
   static final short[][] DFA30_transition;
   public static final BitSet FOLLOW_formula_in_translation_unit59;
   public static final BitSet FOLLOW_EOF_in_translation_unit61;
   public static final BitSet FOLLOW_scalar_formula_in_formula69;
   public static final BitSet FOLLOW_array_formula_in_formula81;
   public static final BitSet FOLLOW_SCALAR_FORMULA_in_scalar_formula97;
   public static final BitSet FOLLOW_primary_expression_in_scalar_formula99;
   public static final BitSet FOLLOW_ARRAY_FORMULA_in_array_formula109;
   public static final BitSet FOLLOW_primary_expression_in_array_formula111;
   public static final BitSet FOLLOW_logical_expression_in_primary_expression120;
   public static final BitSet FOLLOW_concat_expression_in_primary_expression144;
   public static final BitSet FOLLOW_additive_expression_in_primary_expression168;
   public static final BitSet FOLLOW_multiplicative_expression_in_primary_expression191;
   public static final BitSet FOLLOW_exponentiation_expression_in_primary_expression215;
   public static final BitSet FOLLOW_unary_expression_in_primary_expression238;
   public static final BitSet FOLLOW_reference_expression_in_primary_expression261;
   public static final BitSet FOLLOW_bracketed_expression_in_primary_expression285;
   public static final BitSet FOLLOW_percent_expression_in_primary_expression309;
   public static final BitSet FOLLOW_47_in_logical_expression319;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression322;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression324;
   public static final BitSet FOLLOW_50_in_logical_expression360;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression363;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression365;
   public static final BitSet FOLLOW_51_in_logical_expression407;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression409;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression411;
   public static final BitSet FOLLOW_54_in_logical_expression453;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression455;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression457;
   public static final BitSet FOLLOW_53_in_logical_expression499;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression501;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression503;
   public static final BitSet FOLLOW_52_in_logical_expression545;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression548;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression550;
   public static final BitSet FOLLOW_55_in_logical_expression592;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression594;
   public static final BitSet FOLLOW_primary_expression_in_logical_expression596;
   public static final BitSet FOLLOW_56_in_concat_expression643;
   public static final BitSet FOLLOW_primary_expression_in_concat_expression645;
   public static final BitSet FOLLOW_primary_expression_in_concat_expression648;
   public static final BitSet FOLLOW_58_in_additive_expression669;
   public static final BitSet FOLLOW_primary_expression_in_additive_expression671;
   public static final BitSet FOLLOW_primary_expression_in_additive_expression673;
   public static final BitSet FOLLOW_57_in_additive_expression705;
   public static final BitSet FOLLOW_primary_expression_in_additive_expression707;
   public static final BitSet FOLLOW_primary_expression_in_additive_expression709;
   public static final BitSet FOLLOW_59_in_multiplicative_expression732;
   public static final BitSet FOLLOW_primary_expression_in_multiplicative_expression734;
   public static final BitSet FOLLOW_primary_expression_in_multiplicative_expression736;
   public static final BitSet FOLLOW_60_in_multiplicative_expression779;
   public static final BitSet FOLLOW_primary_expression_in_multiplicative_expression781;
   public static final BitSet FOLLOW_primary_expression_in_multiplicative_expression783;
   public static final BitSet FOLLOW_61_in_exponentiation_expression824;
   public static final BitSet FOLLOW_primary_expression_in_exponentiation_expression826;
   public static final BitSet FOLLOW_primary_expression_in_exponentiation_expression828;
   public static final BitSet FOLLOW_62_in_percent_expression851;
   public static final BitSet FOLLOW_primary_expression_in_percent_expression853;
   public static final BitSet FOLLOW_UNARY_MINUS_in_unary_expression875;
   public static final BitSet FOLLOW_primary_expression_in_unary_expression878;
   public static final BitSet FOLLOW_intersection_expression_in_union_expression896;
   public static final BitSet FOLLOW_reference_expression_in_intersection_expression906;
   public static final BitSet FOLLOW_BRACKET_EXPR_in_bracketed_expression917;
   public static final BitSet FOLLOW_primary_expression_in_bracketed_expression919;
   public static final BitSet FOLLOW_primitive_in_reference_expression934;
   public static final BitSet FOLLOW_const_array_in_reference_expression963;
   public static final BitSet FOLLOW_cell_reference_in_reference_expression992;
   public static final BitSet FOLLOW_cell_range_reference_in_reference_expression1021;
   public static final BitSet FOLLOW_column_reference_in_reference_expression1050;
   public static final BitSet FOLLOW_column_range_reference_in_reference_expression1079;
   public static final BitSet FOLLOW_named_range_reference_in_reference_expression1108;
   public static final BitSet FOLLOW_worksheet_named_range_reference_in_reference_expression1137;
   public static final BitSet FOLLOW_r1c1_reference_in_reference_expression1166;
   public static final BitSet FOLLOW_function_call_in_reference_expression1195;
   public static final BitSet FOLLOW_if_statement_in_reference_expression1225;
   public static final BitSet FOLLOW_workbook_in_reference_prefix1233;
   public static final BitSet FOLLOW_sheet_in_reference_prefix1237;
   public static final BitSet FOLLOW_workbook_name_in_workbook1246;
   public static final BitSet FOLLOW_sheet_in_workbook1248;
   public static final BitSet FOLLOW_WORKSHEET_CELL_REF_in_worksheet_cell_reference1259;
   public static final BitSet FOLLOW_SHEET_REF_in_worksheet_cell_reference1263;
   public static final BitSet FOLLOW_local_cell_reference_in_worksheet_cell_reference1265;
   public static final BitSet FOLLOW_CELL_REF_in_local_cell_reference1282;
   public static final BitSet FOLLOW_69_in_local_cell_reference1286;
   public static final BitSet FOLLOW_Identifier_in_local_cell_reference1291;
   public static final BitSet FOLLOW_69_in_local_cell_reference1296;
   public static final BitSet FOLLOW_DecimalLiteral_in_local_cell_reference1301;
   public static final BitSet FOLLOW_worksheet_cell_reference_in_cell_reference1323;
   public static final BitSet FOLLOW_local_cell_reference_in_cell_reference1343;
   public static final BitSet FOLLOW_CELL_RANGE_REF_in_cell_range_reference1357;
   public static final BitSet FOLLOW_cell_reference_in_cell_range_reference1359;
   public static final BitSet FOLLOW_cell_reference_in_cell_range_reference1361;
   public static final BitSet FOLLOW_worksheet_column_reference_in_column_reference1383;
   public static final BitSet FOLLOW_local_column_reference_in_column_reference1406;
   public static final BitSet FOLLOW_WORKSHEET_COLUMN_REF_in_worksheet_column_reference1417;
   public static final BitSet FOLLOW_SHEET_REF_in_worksheet_column_reference1421;
   public static final BitSet FOLLOW_local_column_reference_in_worksheet_column_reference1423;
   public static final BitSet FOLLOW_COLUMN_REF_in_local_column_reference1439;
   public static final BitSet FOLLOW_69_in_local_column_reference1443;
   public static final BitSet FOLLOW_Identifier_in_local_column_reference1448;
   public static final BitSet FOLLOW_COLUMN_RANGE_REF_in_column_range_reference1464;
   public static final BitSet FOLLOW_column_reference_in_column_range_reference1466;
   public static final BitSet FOLLOW_column_reference_in_column_range_reference1468;
   public static final BitSet FOLLOW_NAMED_RANGE_in_named_range_reference1484;
   public static final BitSet FOLLOW_Identifier_in_named_range_reference1488;
   public static final BitSet FOLLOW_NAMED_RANGE_in_worksheet_named_range_reference1504;
   public static final BitSet FOLLOW_SHEET_REF_in_worksheet_named_range_reference1508;
   public static final BitSet FOLLOW_Identifier_in_worksheet_named_range_reference1512;
   public static final BitSet FOLLOW_set_in_workbook_name0;
   public static final BitSet FOLLOW_sheetname_in_sheet1556;
   public static final BitSet FOLLOW_65_in_sheet1558;
   public static final BitSet FOLLOW_set_in_sheetname0;
   public static final BitSet FOLLOW_range_in_area_reference1580;
   public static final BitSet FOLLOW_set_in_area_reference1584;
   public static final BitSet FOLLOW_area_reference_in_area_reference1590;
   public static final BitSet FOLLOW_vector_in_area_reference1605;
   public static final BitSet FOLLOW_set_in_area_reference1609;
   public static final BitSet FOLLOW_area_reference_in_area_reference1615;
   public static final BitSet FOLLOW_cell_in_range1627;
   public static final BitSet FOLLOW_68_in_range1629;
   public static final BitSet FOLLOW_cell_in_range1631;
   public static final BitSet FOLLOW_sheet_in_range1641;
   public static final BitSet FOLLOW_68_in_range1643;
   public static final BitSet FOLLOW_cell_in_range1645;
   public static final BitSet FOLLOW_column_range_in_vector1654;
   public static final BitSet FOLLOW_row_range_in_vector1665;
   public static final BitSet FOLLOW_column_in_column_range1674;
   public static final BitSet FOLLOW_68_in_column_range1676;
   public static final BitSet FOLLOW_column_in_column_range1678;
   public static final BitSet FOLLOW_row_in_row_range1687;
   public static final BitSet FOLLOW_68_in_row_range1689;
   public static final BitSet FOLLOW_row_in_row_range1691;
   public static final BitSet FOLLOW_69_in_cell1700;
   public static final BitSet FOLLOW_column_in_cell1703;
   public static final BitSet FOLLOW_69_in_cell1707;
   public static final BitSet FOLLOW_row_in_cell1710;
   public static final BitSet FOLLOW_Identifier_in_column1722;
   public static final BitSet FOLLOW_DecimalLiteral_in_row1732;
   public static final BitSet FOLLOW_r1c1_ref_row_in_r1c1_reference1741;
   public static final BitSet FOLLOW_r1c1_ref_col_in_r1c1_reference1743;
   public static final BitSet FOLLOW_76_in_r1c1_ref_col1752;
   public static final BitSet FOLLOW_r1c1_ref_offset_in_r1c1_ref_col1754;
   public static final BitSet FOLLOW_76_in_r1c1_ref_col1758;
   public static final BitSet FOLLOW_77_in_r1c1_ref_row1767;
   public static final BitSet FOLLOW_r1c1_ref_offset_in_r1c1_ref_row1769;
   public static final BitSet FOLLOW_78_in_r1c1_ref_offset1778;
   public static final BitSet FOLLOW_58_in_r1c1_ref_offset1780;
   public static final BitSet FOLLOW_DecimalLiteral_in_r1c1_ref_offset1782;
   public static final BitSet FOLLOW_79_in_r1c1_ref_offset1784;
   public static final BitSet FOLLOW_78_in_r1c1_ref_offset1791;
   public static final BitSet FOLLOW_57_in_r1c1_ref_offset1793;
   public static final BitSet FOLLOW_DecimalLiteral_in_r1c1_ref_offset1795;
   public static final BitSet FOLLOW_79_in_r1c1_ref_offset1797;
   public static final BitSet FOLLOW_78_in_r1c1_ref_offset1804;
   public static final BitSet FOLLOW_DecimalLiteral_in_r1c1_ref_offset1810;
   public static final BitSet FOLLOW_79_in_r1c1_ref_offset1812;
   public static final BitSet FOLLOW_DecimalLiteral_in_r1c1_ref_offset1827;
   public static final BitSet FOLLOW_ARRAY_COST_in_const_array1836;
   public static final BitSet FOLLOW_array_row_in_const_array1840;
   public static final BitSet FOLLOW_ARRAY_ROW_in_array_row1852;
   public static final BitSet FOLLOW_array_element_list_in_array_row1854;
   public static final BitSet FOLLOW_array_element_in_array_element_list1865;
   public static final BitSet FOLLOW_primitive_in_array_element1877;
   public static final BitSet FOLLOW_FUNCTION_CALL_in_function_call1906;
   public static final BitSet FOLLOW_Identifier_in_function_call1910;
   public static final BitSet FOLLOW_63_in_function_call1918;
   public static final BitSet FOLLOW_argument_list_in_function_call1920;
   public static final BitSet FOLLOW_64_in_function_call1923;
   public static final BitSet FOLLOW_IF_FUNCTION_in_if_statement1962;
   public static final BitSet FOLLOW_primary_expression_in_if_statement1964;
   public static final BitSet FOLLOW_primary_expression_in_if_statement1988;
   public static final BitSet FOLLOW_primary_expression_in_if_statement2012;
   public static final BitSet FOLLOW_argument_in_argument_list2033;
   public static final BitSet FOLLOW_66_in_argument_list2037;
   public static final BitSet FOLLOW_argument_in_argument_list2039;
   public static final BitSet FOLLOW_primary_expression_in_argument2052;
   public static final BitSet FOLLOW_set_in_primitive2079;
   public static final BitSet FOLLOW_DecimalLiteral_in_primitive2088;
   public static final BitSet FOLLOW_set_in_primitive2108;
   public static final BitSet FOLLOW_FloatingPointLiteral_in_primitive2117;
   public static final BitSet FOLLOW_BooleanConstant_in_primitive2148;
   public static final BitSet FOLLOW_CharacterLiteral_in_primitive2177;
   public static final BitSet FOLLOW_StringLiteral_in_primitive2218;
   public static final BitSet FOLLOW_Error_in_primitive2256;
   public static final BitSet FOLLOW_named_range_reference_in_synpred24_ExcelFormulaTreeParser1108;
   public static final BitSet FOLLOW_worksheet_named_range_reference_in_synpred25_ExcelFormulaTreeParser1137;
   public static final BitSet FOLLOW_set_in_synpred38_ExcelFormulaTreeParser1584;
   public static final BitSet FOLLOW_area_reference_in_synpred38_ExcelFormulaTreeParser1590;
   public static final BitSet FOLLOW_range_in_synpred39_ExcelFormulaTreeParser1580;
   public static final BitSet FOLLOW_set_in_synpred39_ExcelFormulaTreeParser1584;
   public static final BitSet FOLLOW_area_reference_in_synpred39_ExcelFormulaTreeParser1590;
   public static final BitSet FOLLOW_set_in_synpred41_ExcelFormulaTreeParser1609;
   public static final BitSet FOLLOW_area_reference_in_synpred41_ExcelFormulaTreeParser1615;
   public static final BitSet FOLLOW_76_in_synpred47_ExcelFormulaTreeParser1752;
   public static final BitSet FOLLOW_r1c1_ref_offset_in_synpred47_ExcelFormulaTreeParser1754;
   public static final BitSet FOLLOW_array_element_in_synpred52_ExcelFormulaTreeParser1865;


   public ExcelFormulaTreeParser(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public ExcelFormulaTreeParser(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.mStack = new Stack();
      this.mSemanticProcessingEnabled = true;
      this.function_call_stack = new Stack();
      this.if_statement_stack = new Stack();
      this.dfa2 = new ExcelFormulaTreeParser$DFA2(this, this);
      this.dfa6 = new ExcelFormulaTreeParser$DFA6(this, this);
      this.dfa22 = new ExcelFormulaTreeParser$DFA22(this, this);
      this.dfa24 = new ExcelFormulaTreeParser$DFA24(this, this);
      this.dfa25 = new ExcelFormulaTreeParser$DFA25(this, this);
      this.dfa26 = new ExcelFormulaTreeParser$DFA26(this, this);
      this.dfa30 = new ExcelFormulaTreeParser$DFA30(this, this);
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "C:\\projects\\cp6\\cp\\util\\src\\java\\com\\cedar\\cp\\util\\flatform\\model\\parser\\ExcelFormulaTreeParser.g";
   }

   public String getSourceName() {
      return "ExcelFormulaTreeParser";
   }

   public Object pop() {
      return this.mStack.pop();
   }

   public void push(Object o) {
      this.mStack.push(o);
   }

   public Object peek() {
      return this.mStack.peek();
   }

   public void setExecutor(FormulaExecutor executor) {
      this.mExecutor = executor;
   }

   public FormulaExecutor getExecutor() {
      return this.mExecutor;
   }

   public void setSemanticProcessingEnabled(boolean b) {
      this.mSemanticProcessingEnabled = b;
   }

   public boolean isSemanticProcessingEnabled() {
      return this.mSemanticProcessingEnabled;
   }

   public final void translation_unit() throws RecognitionException {
      this.mStack.clear();

      try {
         try {
            this.pushFollow(FOLLOW_formula_in_translation_unit59);
            this.formula();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, -1, FOLLOW_EOF_in_translation_unit61);
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void formula() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA1_0 = this.input.LA(1);
            byte var9;
            if(LA1_0 == 4) {
               var9 = 1;
            } else {
               if(LA1_0 != 5) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 1, 0, this.input);
                  throw nvae;
               }

               var9 = 2;
            }

            switch(var9) {
            case 1:
               this.pushFollow(FOLLOW_scalar_formula_in_formula69);
               this.scalar_formula();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_array_formula_in_formula81);
               this.array_formula();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void scalar_formula() throws RecognitionException {
      try {
         try {
            this.match(this.input, 4, FOLLOW_SCALAR_FORMULA_in_scalar_formula97);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_scalar_formula99);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void array_formula() throws RecognitionException {
      try {
         try {
            this.match(this.input, 5, FOLLOW_ARRAY_FORMULA_in_array_formula109);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_array_formula111);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void primary_expression() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int var7 = this.dfa2.predict(this.input);
            switch(var7) {
            case 1:
               this.pushFollow(FOLLOW_logical_expression_in_primary_expression120);
               this.logical_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_concat_expression_in_primary_expression144);
               this.concat_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.pushFollow(FOLLOW_additive_expression_in_primary_expression168);
               this.additive_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.pushFollow(FOLLOW_multiplicative_expression_in_primary_expression191);
               this.multiplicative_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.pushFollow(FOLLOW_exponentiation_expression_in_primary_expression215);
               this.exponentiation_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 6:
               this.pushFollow(FOLLOW_unary_expression_in_primary_expression238);
               this.unary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 7:
               this.pushFollow(FOLLOW_reference_expression_in_primary_expression261);
               this.reference_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 8:
               this.pushFollow(FOLLOW_bracketed_expression_in_primary_expression285);
               this.bracketed_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 9:
               this.pushFollow(FOLLOW_percent_expression_in_primary_expression309);
               this.percent_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void logical_expression() throws RecognitionException {
      try {
         try {
            boolean re = true;
            byte var9;
            switch(this.input.LA(1)) {
            case 47:
               var9 = 1;
               break;
            case 48:
            case 49:
            default:
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException var10 = new NoViableAltException("", 3, 0, this.input);
               throw var10;
            case 50:
               var9 = 2;
               break;
            case 51:
               var9 = 3;
               break;
            case 52:
               var9 = 6;
               break;
            case 53:
               var9 = 5;
               break;
            case 54:
               var9 = 4;
               break;
            case 55:
               var9 = 7;
            }

            Object operand2;
            Object operand1;
            switch(var9) {
            case 1:
               this.match(this.input, 47, FOLLOW_47_in_logical_expression319);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression322);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression324);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().eq(operand1, operand2));
               }
               break;
            case 2:
               this.match(this.input, 50, FOLLOW_50_in_logical_expression360);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression363);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression365);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().lt(operand1, operand2));
               }
               break;
            case 3:
               this.match(this.input, 51, FOLLOW_51_in_logical_expression407);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression409);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression411);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().le(operand1, operand2));
               }
               break;
            case 4:
               this.match(this.input, 54, FOLLOW_54_in_logical_expression453);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression455);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression457);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().ne(operand1, operand2));
               }
               break;
            case 5:
               this.match(this.input, 53, FOLLOW_53_in_logical_expression499);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression501);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression503);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().ge(operand1, operand2));
               }
               break;
            case 6:
               this.match(this.input, 52, FOLLOW_52_in_logical_expression545);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression548);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression550);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().gt(operand1, operand2));
               }
               break;
            case 7:
               this.match(this.input, 55, FOLLOW_55_in_logical_expression592);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression594);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_logical_expression596);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().ne(operand1, operand2));
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void concat_expression() throws RecognitionException {
      try {
         try {
            this.match(this.input, 56, FOLLOW_56_in_concat_expression643);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_concat_expression645);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_concat_expression648);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               Object re = this.pop();
               Object operand1 = this.pop();
               this.push(this.getExecutor().concatenate(operand1, re));
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void additive_expression() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA4_0 = this.input.LA(1);
            byte var10;
            if(LA4_0 == 58) {
               var10 = 1;
            } else {
               if(LA4_0 != 57) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException var11 = new NoViableAltException("", 4, 0, this.input);
                  throw var11;
               }

               var10 = 2;
            }

            Object operand2;
            Object operand1;
            switch(var10) {
            case 1:
               this.match(this.input, 58, FOLLOW_58_in_additive_expression669);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_additive_expression671);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_additive_expression673);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().sub(operand1, operand2));
               }
               break;
            case 2:
               this.match(this.input, 57, FOLLOW_57_in_additive_expression705);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_additive_expression707);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_additive_expression709);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().add(operand1, operand2));
               }
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void multiplicative_expression() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA5_0 = this.input.LA(1);
            byte var10;
            if(LA5_0 == 59) {
               var10 = 1;
            } else {
               if(LA5_0 != 60) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException var11 = new NoViableAltException("", 5, 0, this.input);
                  throw var11;
               }

               var10 = 2;
            }

            Object operand2;
            Object operand1;
            switch(var10) {
            case 1:
               this.match(this.input, 59, FOLLOW_59_in_multiplicative_expression732);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_multiplicative_expression734);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_multiplicative_expression736);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().mul(operand1, operand2));
               }
               break;
            case 2:
               this.match(this.input, 60, FOLLOW_60_in_multiplicative_expression779);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 2, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_multiplicative_expression781);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_primary_expression_in_multiplicative_expression783);
               this.primary_expression();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  operand2 = this.pop();
                  operand1 = this.pop();
                  this.push(this.getExecutor().div(operand1, operand2));
               }
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void exponentiation_expression() throws RecognitionException {
      try {
         try {
            this.match(this.input, 61, FOLLOW_61_in_exponentiation_expression824);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_exponentiation_expression826);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_exponentiation_expression828);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               Object re = this.pop();
               Object operand1 = this.pop();
               this.push(this.getExecutor().pow(operand1, re));
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void percent_expression() throws RecognitionException {
      try {
         try {
            this.match(this.input, 62, FOLLOW_62_in_percent_expression851);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_percent_expression853);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               this.push(this.getExecutor().percent(this.pop()));
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void unary_expression() throws RecognitionException {
      try {
         try {
            this.match(this.input, 20, FOLLOW_UNARY_MINUS_in_unary_expression875);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_unary_expression878);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               this.push(this.getExecutor().neg(this.pop()));
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void union_expression() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_intersection_expression_in_union_expression896);
            this.intersection_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void intersection_expression() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_reference_expression_in_intersection_expression906);
            this.reference_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void bracketed_expression() throws RecognitionException {
      try {
         try {
            this.match(this.input, 6, FOLLOW_BRACKET_EXPR_in_bracketed_expression917);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_primary_expression_in_bracketed_expression919);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void reference_expression() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int var7 = this.dfa6.predict(this.input);
            switch(var7) {
            case 1:
               this.pushFollow(FOLLOW_primitive_in_reference_expression934);
               this.primitive();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_const_array_in_reference_expression963);
               this.const_array();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.pushFollow(FOLLOW_cell_reference_in_reference_expression992);
               this.cell_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.pushFollow(FOLLOW_cell_range_reference_in_reference_expression1021);
               this.cell_range_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.pushFollow(FOLLOW_column_reference_in_reference_expression1050);
               this.column_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 6:
               this.pushFollow(FOLLOW_column_range_reference_in_reference_expression1079);
               this.column_range_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 7:
               this.pushFollow(FOLLOW_named_range_reference_in_reference_expression1108);
               this.named_range_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 8:
               this.pushFollow(FOLLOW_worksheet_named_range_reference_in_reference_expression1137);
               this.worksheet_named_range_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 9:
               this.pushFollow(FOLLOW_r1c1_reference_in_reference_expression1166);
               this.r1c1_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 10:
               this.pushFollow(FOLLOW_function_call_in_reference_expression1195);
               this.function_call();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 11:
               this.pushFollow(FOLLOW_if_statement_in_reference_expression1225);
               this.if_statement();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void reference_prefix() throws RecognitionException {
      try {
         try {
            boolean re = true;
            byte var9;
            switch(this.input.LA(1)) {
            case 26:
               var9 = 2;
               break;
            case 74:
               var9 = 1;
               break;
            case 75:
               int nvae = this.input.LA(2);
               if(nvae != 26 && nvae != 75) {
                  if(nvae != 65) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae1 = new NoViableAltException("", 7, 1, this.input);
                     throw nvae1;
                  }

                  var9 = 2;
               } else {
                  var9 = 1;
               }
               break;
            default:
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException var10 = new NoViableAltException("", 7, 0, this.input);
               throw var10;
            }

            switch(var9) {
            case 1:
               this.pushFollow(FOLLOW_workbook_in_reference_prefix1233);
               this.workbook();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_sheet_in_reference_prefix1237);
               this.sheet();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void workbook() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_workbook_name_in_workbook1246);
            this.workbook_name();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_sheet_in_workbook1248);
            this.sheet();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void worksheet_cell_reference() throws RecognitionException {
      CommonTree sn = null;

      try {
         try {
            this.match(this.input, 13, FOLLOW_WORKSHEET_CELL_REF_in_worksheet_cell_reference1259);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            sn = (CommonTree)this.match(this.input, 21, FOLLOW_SHEET_REF_in_worksheet_cell_reference1263);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_local_cell_reference_in_worksheet_cell_reference1265);
            this.local_cell_reference();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               this.push(this.getExecutor().getWorksheetCell(sn, this.pop()));
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void local_cell_reference() throws RecognitionException {
      CommonTree colInd = null;
      CommonTree colId = null;
      CommonTree rowInd = null;
      CommonTree rowId = null;

      try {
         try {
            this.match(this.input, 12, FOLLOW_CELL_REF_in_local_cell_reference1282);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            byte re = 2;
            int LA8_0 = this.input.LA(1);
            if(LA8_0 == 69) {
               re = 1;
            }

            switch(re) {
            case 1:
               colInd = (CommonTree)this.match(this.input, 69, FOLLOW_69_in_local_cell_reference1286);
               if(this.state.failed) {
                  return;
               }
            default:
               colId = (CommonTree)this.match(this.input, 26, FOLLOW_Identifier_in_local_cell_reference1291);
               if(this.state.failed) {
                  return;
               }

               byte alt10 = 2;
               int LA10_0 = this.input.LA(1);
               if(LA10_0 == 27 || LA10_0 == 69) {
                  alt10 = 1;
               }

               switch(alt10) {
               case 1:
                  byte alt9 = 2;
                  int LA9_0 = this.input.LA(1);
                  if(LA9_0 == 69) {
                     alt9 = 1;
                  }

                  switch(alt9) {
                  case 1:
                     rowInd = (CommonTree)this.match(this.input, 69, FOLLOW_69_in_local_cell_reference1296);
                     if(this.state.failed) {
                        return;
                     }
                  default:
                     rowId = (CommonTree)this.match(this.input, 27, FOLLOW_DecimalLiteral_in_local_cell_reference1301);
                     if(this.state.failed) {
                        return;
                     }
                  }
               default:
                  this.match(this.input, 3, (BitSet)null);
                  if(this.state.failed) {
                     return;
                  }

                  if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                     this.push(this.getExecutor().getCell(colInd, colId, rowInd, rowId));
                  }
               }
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
         }

      } finally {
         ;
      }
   }

   public final void cell_reference() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA11_0 = this.input.LA(1);
            byte var9;
            if(LA11_0 == 13) {
               var9 = 1;
            } else {
               if(LA11_0 != 12) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
                  throw nvae;
               }

               var9 = 2;
            }

            switch(var9) {
            case 1:
               this.pushFollow(FOLLOW_worksheet_cell_reference_in_cell_reference1323);
               this.worksheet_cell_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_local_cell_reference_in_cell_reference1343);
               this.local_cell_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void cell_range_reference() throws RecognitionException {
      try {
         try {
            this.match(this.input, 17, FOLLOW_CELL_RANGE_REF_in_cell_range_reference1357);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_cell_reference_in_cell_range_reference1359);
            this.cell_reference();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_cell_reference_in_cell_range_reference1361);
            this.cell_reference();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               Object re = this.pop();
               Object cellRef1 = this.pop();
               this.push(this.getExecutor().getCellRange(cellRef1, re));
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void column_reference() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA12_0 = this.input.LA(1);
            byte var9;
            if(LA12_0 == 15) {
               var9 = 1;
            } else {
               if(LA12_0 != 14) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                  throw nvae;
               }

               var9 = 2;
            }

            switch(var9) {
            case 1:
               this.pushFollow(FOLLOW_worksheet_column_reference_in_column_reference1383);
               this.worksheet_column_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_local_column_reference_in_column_reference1406);
               this.local_column_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void worksheet_column_reference() throws RecognitionException {
      CommonTree sn = null;

      try {
         try {
            this.match(this.input, 15, FOLLOW_WORKSHEET_COLUMN_REF_in_worksheet_column_reference1417);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            sn = (CommonTree)this.match(this.input, 21, FOLLOW_SHEET_REF_in_worksheet_column_reference1421);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_local_column_reference_in_worksheet_column_reference1423);
            this.local_column_reference();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               this.push(this.getExecutor().getWorksheetCell(sn, this.pop()));
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void local_column_reference() throws RecognitionException {
      CommonTree colInd = null;
      CommonTree colId = null;

      try {
         try {
            this.match(this.input, 14, FOLLOW_COLUMN_REF_in_local_column_reference1439);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            byte re = 2;
            int LA13_0 = this.input.LA(1);
            if(LA13_0 == 69) {
               re = 1;
            }

            switch(re) {
            case 1:
               colInd = (CommonTree)this.match(this.input, 69, FOLLOW_69_in_local_column_reference1443);
               if(this.state.failed) {
                  return;
               }
            default:
               colId = (CommonTree)this.match(this.input, 26, FOLLOW_Identifier_in_local_column_reference1448);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 3, (BitSet)null);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  this.push(this.getExecutor().getColumn(colInd, colId));
               }
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void column_range_reference() throws RecognitionException {
      try {
         try {
            this.match(this.input, 18, FOLLOW_COLUMN_RANGE_REF_in_column_range_reference1464);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_column_reference_in_column_range_reference1466);
            this.column_reference();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_column_reference_in_column_range_reference1468);
            this.column_reference();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               Object re = this.pop();
               Object cellRef1 = this.pop();
               this.push(this.getExecutor().getColumnRange(cellRef1, re));
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void named_range_reference() throws RecognitionException {
      CommonTree name = null;

      try {
         try {
            this.match(this.input, 24, FOLLOW_NAMED_RANGE_in_named_range_reference1484);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            name = (CommonTree)this.match(this.input, 26, FOLLOW_Identifier_in_named_range_reference1488);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               this.push(this.getExecutor().getNamedRange(name));
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void worksheet_named_range_reference() throws RecognitionException {
      CommonTree sr = null;
      CommonTree name = null;

      try {
         try {
            this.match(this.input, 24, FOLLOW_NAMED_RANGE_in_worksheet_named_range_reference1504);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            sr = (CommonTree)this.match(this.input, 21, FOLLOW_SHEET_REF_in_worksheet_named_range_reference1508);
            if(this.state.failed) {
               return;
            }

            name = (CommonTree)this.match(this.input, 26, FOLLOW_Identifier_in_worksheet_named_range_reference1512);
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               this.push(this.getExecutor().getWorksheetNamedRange(sr, name));
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void workbook_name() throws RecognitionException {
      try {
         try {
            if(this.input.LA(1) < 74 || this.input.LA(1) > 75) {
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               MismatchedSetException re = new MismatchedSetException((BitSet)null, this.input);
               throw re;
            }

            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void sheet() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_sheetname_in_sheet1556);
            this.sheetname();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 65, FOLLOW_65_in_sheet1558);
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void sheetname() throws RecognitionException {
      try {
         try {
            if(this.input.LA(1) != 26 && this.input.LA(1) != 75) {
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               MismatchedSetException re = new MismatchedSetException((BitSet)null, this.input);
               throw re;
            }

            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void area_reference() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA15_0;
            byte var10;
            switch(this.input.LA(1)) {
            case 26:
               int alt15 = this.input.LA(2);
               if(alt15 != 27 && alt15 != 65 && alt15 != 69) {
                  if(alt15 != 68) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException var12 = new NoViableAltException("", 16, 2, this.input);
                     throw var12;
                  }

                  LA15_0 = this.input.LA(3);
                  if(this.synpred39_ExcelFormulaTreeParser()) {
                     var10 = 1;
                  } else {
                     var10 = 2;
                  }
               } else {
                  var10 = 1;
               }
               break;
            case 27:
               var10 = 2;
               break;
            case 69:
            case 75:
               var10 = 1;
               break;
            default:
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException var13 = new NoViableAltException("", 16, 0, this.input);
               throw var13;
            }

            int mse;
            byte var11;
            MismatchedSetException var14;
            switch(var10) {
            case 1:
               this.pushFollow(FOLLOW_range_in_area_reference1580);
               this.range();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               while(true) {
                  var11 = 2;
                  LA15_0 = this.input.LA(1);
                  if(LA15_0 >= 66 && LA15_0 <= 67) {
                     mse = this.input.LA(2);
                     if(this.synpred38_ExcelFormulaTreeParser()) {
                        var11 = 1;
                     }
                  }

                  switch(var11) {
                  case 1:
                     if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
                        this.input.consume();
                        this.state.errorRecovery = false;
                        this.state.failed = false;
                        this.pushFollow(FOLLOW_area_reference_in_area_reference1590);
                        this.area_reference();
                        --this.state._fsp;
                        if(this.state.failed) {
                           return;
                        }
                        break;
                     }

                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     var14 = new MismatchedSetException((BitSet)null, this.input);
                     throw var14;
                  default:
                     return;
                  }
               }
            case 2:
               this.pushFollow(FOLLOW_vector_in_area_reference1605);
               this.vector();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               while(true) {
                  var11 = 2;
                  LA15_0 = this.input.LA(1);
                  if(LA15_0 >= 66 && LA15_0 <= 67) {
                     mse = this.input.LA(2);
                     if(this.synpred41_ExcelFormulaTreeParser()) {
                        var11 = 1;
                     }
                  }

                  switch(var11) {
                  case 1:
                     if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
                        this.input.consume();
                        this.state.errorRecovery = false;
                        this.state.failed = false;
                        this.pushFollow(FOLLOW_area_reference_in_area_reference1615);
                        this.area_reference();
                        --this.state._fsp;
                        if(this.state.failed) {
                           return;
                        }
                        break;
                     }

                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     var14 = new MismatchedSetException((BitSet)null, this.input);
                     throw var14;
                  default:
                     return;
                  }
               }
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void range() throws RecognitionException {
      try {
         try {
            boolean re = true;
            byte var9;
            switch(this.input.LA(1)) {
            case 26:
               int nvae = this.input.LA(2);
               if(nvae == 65) {
                  var9 = 2;
               } else {
                  if(nvae != 27 && (nvae < 68 || nvae > 69)) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae1 = new NoViableAltException("", 17, 2, this.input);
                     throw nvae1;
                  }

                  var9 = 1;
               }
               break;
            case 69:
               var9 = 1;
               break;
            case 75:
               var9 = 2;
               break;
            default:
               if(this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException var10 = new NoViableAltException("", 17, 0, this.input);
               throw var10;
            }

            switch(var9) {
            case 1:
               this.pushFollow(FOLLOW_cell_in_range1627);
               this.cell();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 68, FOLLOW_68_in_range1629);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_cell_in_range1631);
               this.cell();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_sheet_in_range1641);
               this.sheet();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 68, FOLLOW_68_in_range1643);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_cell_in_range1645);
               this.cell();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void vector() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA18_0 = this.input.LA(1);
            byte var9;
            if(LA18_0 == 26) {
               var9 = 1;
            } else {
               if(LA18_0 != 27) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 18, 0, this.input);
                  throw nvae;
               }

               var9 = 2;
            }

            switch(var9) {
            case 1:
               this.pushFollow(FOLLOW_column_range_in_vector1654);
               this.column_range();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_row_range_in_vector1665);
               this.row_range();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void column_range() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_column_in_column_range1674);
            this.column();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 68, FOLLOW_68_in_column_range1676);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_column_in_column_range1678);
            this.column();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void row_range() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_row_in_row_range1687);
            this.row();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 68, FOLLOW_68_in_row_range1689);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_row_in_row_range1691);
            this.row();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void cell() throws RecognitionException {
      try {
         try {
            byte re = 2;
            int LA19_0 = this.input.LA(1);
            if(LA19_0 == 69) {
               re = 1;
            }

            switch(re) {
            case 1:
               this.match(this.input, 69, FOLLOW_69_in_cell1700);
               if(this.state.failed) {
                  return;
               }
            default:
               this.pushFollow(FOLLOW_column_in_cell1703);
               this.column();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               byte alt21 = 2;
               int LA21_0 = this.input.LA(1);
               if(LA21_0 == 27 || LA21_0 == 69) {
                  alt21 = 1;
               }

               switch(alt21) {
               case 1:
                  byte alt20 = 2;
                  int LA20_0 = this.input.LA(1);
                  if(LA20_0 == 69) {
                     alt20 = 1;
                  }

                  switch(alt20) {
                  case 1:
                     this.match(this.input, 69, FOLLOW_69_in_cell1707);
                     if(this.state.failed) {
                        return;
                     }
                  default:
                     this.pushFollow(FOLLOW_row_in_cell1710);
                     this.row();
                     --this.state._fsp;
                     if(this.state.failed) {
                        return;
                     }
                  }
               }
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

      } finally {
         ;
      }
   }

   public final void column() throws RecognitionException {
      try {
         try {
            this.match(this.input, 26, FOLLOW_Identifier_in_column1722);
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void row() throws RecognitionException {
      try {
         try {
            this.match(this.input, 27, FOLLOW_DecimalLiteral_in_row1732);
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void r1c1_reference() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_r1c1_ref_row_in_r1c1_reference1741);
            this.r1c1_ref_row();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_r1c1_ref_col_in_r1c1_reference1743);
            this.r1c1_ref_col();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void r1c1_ref_col() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int var7 = this.dfa22.predict(this.input);
            switch(var7) {
            case 1:
               this.match(this.input, 76, FOLLOW_76_in_r1c1_ref_col1752);
               if(this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_r1c1_ref_offset_in_r1c1_ref_col1754);
               this.r1c1_ref_offset();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 76, FOLLOW_76_in_r1c1_ref_col1758);
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void r1c1_ref_row() throws RecognitionException {
      try {
         try {
            this.match(this.input, 77, FOLLOW_77_in_r1c1_ref_row1767);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_r1c1_ref_offset_in_r1c1_ref_row1769);
            this.r1c1_ref_offset();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void r1c1_ref_offset() throws RecognitionException {
      try {
         try {
            boolean re = true;
            int LA23_0 = this.input.LA(1);
            NoViableAltException nvae;
            byte re1;
            if(LA23_0 == 78) {
               switch(this.input.LA(2)) {
               case 27:
                  re1 = 3;
                  break;
               case 57:
                  re1 = 2;
                  break;
               case 58:
                  re1 = 1;
                  break;
               default:
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  nvae = new NoViableAltException("", 23, 1, this.input);
                  throw nvae;
               }
            } else {
               if(LA23_0 != 27) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  nvae = new NoViableAltException("", 23, 0, this.input);
                  throw nvae;
               }

               re1 = 4;
            }

            switch(re1) {
            case 1:
               this.match(this.input, 78, FOLLOW_78_in_r1c1_ref_offset1778);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 58, FOLLOW_58_in_r1c1_ref_offset1780);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 27, FOLLOW_DecimalLiteral_in_r1c1_ref_offset1782);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 79, FOLLOW_79_in_r1c1_ref_offset1784);
               if(this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 78, FOLLOW_78_in_r1c1_ref_offset1791);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 57, FOLLOW_57_in_r1c1_ref_offset1793);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 27, FOLLOW_DecimalLiteral_in_r1c1_ref_offset1795);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 79, FOLLOW_79_in_r1c1_ref_offset1797);
               if(this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.match(this.input, 78, FOLLOW_78_in_r1c1_ref_offset1804);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 27, FOLLOW_DecimalLiteral_in_r1c1_ref_offset1810);
               if(this.state.failed) {
                  return;
               }

               this.match(this.input, 79, FOLLOW_79_in_r1c1_ref_offset1812);
               if(this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.match(this.input, 27, FOLLOW_DecimalLiteral_in_r1c1_ref_offset1827);
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void const_array() throws RecognitionException {
      try {
         this.match(this.input, 73, FOLLOW_ARRAY_COST_in_const_array1836);
         if(!this.state.failed) {
            int re = 0;

            while(true) {
               boolean alt24 = true;
               int var9 = this.dfa24.predict(this.input);
               switch(var9) {
               case 1:
                  this.pushFollow(FOLLOW_array_row_in_const_array1840);
                  this.array_row();
                  --this.state._fsp;
                  if(this.state.failed) {
                     return;
                  }

                  ++re;
                  break;
               default:
                  if(re < 1) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     EarlyExitException eee = new EarlyExitException(24, this.input);
                     throw eee;
                  }

                  return;
               }
            }
         }
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.recover(this.input, var7);
      } finally {
         ;
      }
   }

   public final void array_row() throws RecognitionException {
      try {
         try {
            this.match(this.input, 23, FOLLOW_ARRAY_ROW_in_array_row1852);
            if(this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_array_element_list_in_array_row1854);
            this.array_element_list();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void array_element_list() throws RecognitionException {
      try {
         int re = 0;

         while(true) {
            boolean alt25 = true;
            int var9 = this.dfa25.predict(this.input);
            switch(var9) {
            case 1:
               this.pushFollow(FOLLOW_array_element_in_array_element_list1865);
               this.array_element();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }

               ++re;
               break;
            default:
               if(re < 1) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(25, this.input);
                  throw eee;
               }

               return;
            }
         }
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.recover(this.input, var7);
      } finally {
         ;
      }
   }

   public final void array_element() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_primitive_in_array_element1877);
            this.primitive();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void function_call() throws RecognitionException {
      this.function_call_stack.push(new ExcelFormulaTreeParser$function_call_scope());
      CommonTree fname = null;
      ((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).activeOnEntry = this.isSemanticProcessingEnabled();

      try {
         this.match(this.input, 10, FOLLOW_FUNCTION_CALL_in_function_call1906);
         if(this.state.failed) {
            return;
         }

         this.match(this.input, 2, (BitSet)null);
         if(this.state.failed) {
            return;
         }

         fname = (CommonTree)this.match(this.input, 26, FOLLOW_Identifier_in_function_call1910);
         if(this.state.failed) {
            return;
         }

         if(this.state.backtracking == 0) {
            ((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).fe = this.getExecutor().getFunctionExecutor(fname != null?fname.getText():null);
         }

         this.match(this.input, 63, FOLLOW_63_in_function_call1918);
         if(this.state.failed) {
            return;
         }

         boolean re = true;
         int var8 = this.dfa26.predict(this.input);
         switch(var8) {
         case 1:
            this.pushFollow(FOLLOW_argument_list_in_function_call1920);
            this.argument_list();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }
         }

         this.match(this.input, 64, FOLLOW_64_in_function_call1923);
         if(!this.state.failed) {
            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && ((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).activeOnEntry) {
               this.push(this.getExecutor().executeFunction(fname != null?fname.getText():null, ((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).params));
            }

            if(this.state.backtracking == 0) {
               this.setSemanticProcessingEnabled(((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).activeOnEntry);
            }

            return;
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.recover(this.input, var6);
         return;
      } finally {
         this.function_call_stack.pop();
      }

   }

   public final void if_statement() throws RecognitionException {
      this.if_statement_stack.push(new ExcelFormulaTreeParser$if_statement_scope());

      try {
         this.match(this.input, 19, FOLLOW_IF_FUNCTION_in_if_statement1962);
         if(this.state.failed) {
            return;
         }

         this.match(this.input, 2, (BitSet)null);
         if(!this.state.failed) {
            this.pushFollow(FOLLOW_primary_expression_in_if_statement1964);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               Object re = this.pop();
               if(re instanceof Boolean) {
                  ((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).ifCondition = (Boolean)re;
                  this.setSemanticProcessingEnabled(((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).ifCondition.booleanValue());
               } else if(re instanceof CellErrorValue) {
                  ((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).errorCondition = true;
                  this.setSemanticProcessingEnabled(false);
               }

               ((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).activeOnEntry = true;
            }

            this.pushFollow(FOLLOW_primary_expression_in_if_statement1988);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && ((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).activeOnEntry) {
               if(((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).errorCondition) {
                  this.push(CellErrorValue.VALUE);
               } else {
                  this.setSemanticProcessingEnabled(!((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).ifCondition.booleanValue());
               }
            }

            this.pushFollow(FOLLOW_primary_expression_in_if_statement2012);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && ((ExcelFormulaTreeParser$if_statement_scope)this.if_statement_stack.peek()).activeOnEntry) {
               this.setSemanticProcessingEnabled(true);
            }

            return;
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(this.input, var5);
         return;
      } finally {
         this.if_statement_stack.pop();
      }

   }

   public final void argument_list() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_argument_in_argument_list2033);
         this.argument();
         --this.state._fsp;
         if(!this.state.failed) {
            while(true) {
               byte re = 2;
               int LA27_0 = this.input.LA(1);
               if(LA27_0 == 66) {
                  re = 1;
               }

               switch(re) {
               case 1:
                  this.match(this.input, 66, FOLLOW_66_in_argument_list2037);
                  if(this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_argument_in_argument_list2039);
                  this.argument();
                  --this.state._fsp;
                  if(this.state.failed) {
                     return;
                  }
                  break;
               default:
                  return;
               }
            }
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.recover(this.input, var6);
      } finally {
         ;
      }
   }

   public final void argument() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_primary_expression_in_argument2052);
            this.primary_expression();
            --this.state._fsp;
            if(this.state.failed) {
               return;
            }

            if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
               Object re = this.pop();
               if(((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).params == null) {
                  ((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).params = new ArrayList();
               }

               ((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).fe.preProcessParameter(re);
               ((ExcelFormulaTreeParser$function_call_scope)this.function_call_stack.peek()).params.add(re);
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void primitive() throws RecognitionException {
      CommonTree dl = null;
      CommonTree fpl = null;
      CommonTree bc = null;
      CommonTree cl = null;
      CommonTree sl = null;

      try {
         try {
            boolean re = true;
            int re1 = this.dfa30.predict(this.input);
            byte alt29;
            int LA29_0;
            MismatchedSetException mse;
            switch(re1) {
            case 1:
               alt29 = 2;
               LA29_0 = this.input.LA(1);
               if(LA29_0 >= 57 && LA29_0 <= 58) {
                  alt29 = 1;
               }

               switch(alt29) {
               case 1:
                  if(this.input.LA(1) < 57 || this.input.LA(1) > 58) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.errorRecovery = false;
                  this.state.failed = false;
               default:
                  dl = (CommonTree)this.match(this.input, 27, FOLLOW_DecimalLiteral_in_primitive2088);
                  if(this.state.failed) {
                     return;
                  }

                  if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                     this.push(Double.valueOf(Double.parseDouble(dl != null?dl.getText():null)));
                  }

                  return;
               }
            case 2:
               alt29 = 2;
               LA29_0 = this.input.LA(1);
               if(LA29_0 >= 57 && LA29_0 <= 58) {
                  alt29 = 1;
               }

               switch(alt29) {
               case 1:
                  if(this.input.LA(1) < 57 || this.input.LA(1) > 58) {
                     if(this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.errorRecovery = false;
                  this.state.failed = false;
               default:
                  fpl = (CommonTree)this.match(this.input, 29, FOLLOW_FloatingPointLiteral_in_primitive2117);
                  if(this.state.failed) {
                     return;
                  }

                  if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                     this.push(Double.valueOf(Double.parseDouble(fpl != null?fpl.getText():null)));
                  }

                  return;
               }
            case 3:
               bc = (CommonTree)this.match(this.input, 30, FOLLOW_BooleanConstant_in_primitive2148);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  this.push(Boolean.valueOf((bc != null?bc.getText():null).equalsIgnoreCase("true")));
               }
               break;
            case 4:
               cl = (CommonTree)this.match(this.input, 31, FOLLOW_CharacterLiteral_in_primitive2177);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  this.push(Character.valueOf((bc != null?bc.getText():null).charAt(0)));
               }
               break;
            case 5:
               sl = (CommonTree)this.match(this.input, 32, FOLLOW_StringLiteral_in_primitive2218);
               if(this.state.failed) {
                  return;
               }

               if(this.state.backtracking == 0 && this.isSemanticProcessingEnabled()) {
                  this.push(sl != null?sl.getText():null);
               }
               break;
            case 6:
               this.match(this.input, 28, FOLLOW_Error_in_primitive2256);
               if(this.state.failed) {
                  return;
               }
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
         }

      } finally {
         ;
      }
   }

   public final void synpred24_ExcelFormulaTreeParser_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_named_range_reference_in_synpred24_ExcelFormulaTreeParser1108);
      this.named_range_reference();
      --this.state._fsp;
      if(!this.state.failed) {
         ;
      }
   }

   public final void synpred25_ExcelFormulaTreeParser_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_worksheet_named_range_reference_in_synpred25_ExcelFormulaTreeParser1137);
      this.worksheet_named_range_reference();
      --this.state._fsp;
      if(!this.state.failed) {
         ;
      }
   }

   public final void synpred38_ExcelFormulaTreeParser_fragment() throws RecognitionException {
      if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
         this.input.consume();
         this.state.errorRecovery = false;
         this.state.failed = false;
         this.pushFollow(FOLLOW_area_reference_in_synpred38_ExcelFormulaTreeParser1590);
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

   public final void synpred39_ExcelFormulaTreeParser_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_range_in_synpred39_ExcelFormulaTreeParser1580);
      this.range();
      --this.state._fsp;
      if(!this.state.failed) {
         while(true) {
            byte alt32 = 2;
            int LA32_0 = this.input.LA(1);
            if(LA32_0 >= 66 && LA32_0 <= 67) {
               alt32 = 1;
            }

            switch(alt32) {
            case 1:
               if(this.input.LA(1) < 66 || this.input.LA(1) > 67) {
                  if(this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }
               }

               this.input.consume();
               this.state.errorRecovery = false;
               this.state.failed = false;
               this.pushFollow(FOLLOW_area_reference_in_synpred39_ExcelFormulaTreeParser1590);
               this.area_reference();
               --this.state._fsp;
               if(this.state.failed) {
                  return;
               }
               break;
            default:
               return;
            }
         }
      }
   }

   public final void synpred41_ExcelFormulaTreeParser_fragment() throws RecognitionException {
      if(this.input.LA(1) >= 66 && this.input.LA(1) <= 67) {
         this.input.consume();
         this.state.errorRecovery = false;
         this.state.failed = false;
         this.pushFollow(FOLLOW_area_reference_in_synpred41_ExcelFormulaTreeParser1615);
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

   public final void synpred47_ExcelFormulaTreeParser_fragment() throws RecognitionException {
      this.match(this.input, 76, FOLLOW_76_in_synpred47_ExcelFormulaTreeParser1752);
      if(!this.state.failed) {
         this.pushFollow(FOLLOW_r1c1_ref_offset_in_synpred47_ExcelFormulaTreeParser1754);
         this.r1c1_ref_offset();
         --this.state._fsp;
         if(!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred52_ExcelFormulaTreeParser_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_array_element_in_synpred52_ExcelFormulaTreeParser1865);
      this.array_element();
      --this.state._fsp;
      if(!this.state.failed) {
         ;
      }
   }

   public final boolean synpred39_ExcelFormulaTreeParser() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred39_ExcelFormulaTreeParser_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred41_ExcelFormulaTreeParser() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred41_ExcelFormulaTreeParser_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred25_ExcelFormulaTreeParser() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred25_ExcelFormulaTreeParser_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred47_ExcelFormulaTreeParser() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred47_ExcelFormulaTreeParser_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred24_ExcelFormulaTreeParser() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred24_ExcelFormulaTreeParser_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred52_ExcelFormulaTreeParser() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred52_ExcelFormulaTreeParser_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred38_ExcelFormulaTreeParser() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred38_ExcelFormulaTreeParser_fragment();
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
   static RecognizerSharedState accessMethod000(ExcelFormulaTreeParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod100(ExcelFormulaTreeParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod200(ExcelFormulaTreeParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod300(ExcelFormulaTreeParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod400(ExcelFormulaTreeParser x0) {
      return x0.state;
   }

   // $FF: synthetic method
   static RecognizerSharedState accessMethod500(ExcelFormulaTreeParser x0) {
      return x0.state;
   }

   static {
      int numStates = DFA2_transitionS.length;
      DFA2_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
      }

      DFA6_transitionS = new String[]{"￿\t\f￿￿￿￿￿\b￿", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "￿", "", ""};
      DFA6_eot = DFA.unpackEncodedString("￿");
      DFA6_eof = DFA.unpackEncodedString("￿");
      DFA6_min = DFA.unpackEncodedStringToUnsignedChars("\n￿￿ ￿");
      DFA6_max = DFA.unpackEncodedStringToUnsignedChars("M￿￿ ￿");
      DFA6_accept = DFA.unpackEncodedString("￿￿￿￿￿\t\n￿\b");
      DFA6_special = DFA.unpackEncodedString("￿ ￿}>");
      numStates = DFA6_transitionS.length;
      DFA6_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
      }

      DFA22_transitionS = new String[]{"", "￿￿￿￿￿￿￿￿\r￿￿￿￿", "", "￿", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA22_eot = DFA.unpackEncodedString("(￿");
      DFA22_eof = DFA.unpackEncodedString("￿&￿");
      DFA22_min = DFA.unpackEncodedStringToUnsignedChars("L￿ $￿");
      DFA22_max = DFA.unpackEncodedStringToUnsignedChars("LN￿ $￿");
      DFA22_accept = DFA.unpackEncodedString("￿￿#￿");
      DFA22_special = DFA.unpackEncodedString("￿ $￿}>");
      numStates = DFA22_transitionS.length;
      DFA22_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
      }

      DFA24_transitionS = new String[]{"￿￿￿￿￿&￿￿￿\r￿￿￿￿", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA24_eot = DFA.unpackEncodedString("\'￿");
      DFA24_eof = DFA.unpackEncodedString("&￿");
      DFA24_min = DFA.unpackEncodedStringToUnsignedChars("&￿");
      DFA24_max = DFA.unpackEncodedStringToUnsignedChars("M&￿");
      DFA24_accept = DFA.unpackEncodedString("￿$￿");
      DFA24_special = DFA.unpackEncodedString("\'￿}>");
      numStates = DFA24_transitionS.length;
      DFA24_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA24_transition[i] = DFA.unpackEncodedString(DFA24_transitionS[i]);
      }

      DFA25_transitionS = new String[]{"￿￿￿￿￿￿￿￿\n￿￿￿￿", "", "", "", "", "", "", "", "", "", "￿(￿)", "￿+￿,", "", "", "", "", "￿", "￿", "￿", "￿", "￿", "￿", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "￿", "￿", "", "￿", "￿", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA25_eot = DFA.unpackEncodedString("Ē￿");
      DFA25_eof = DFA.unpackEncodedString("đ￿");
      DFA25_min = DFA.unpackEncodedStringToUnsignedChars("\t￿￿ ￿ ￿ å￿");
      DFA25_max = DFA.unpackEncodedStringToUnsignedChars("M\t￿￿ ￿ ￿ å￿");
      DFA25_accept = DFA.unpackEncodedString("￿Q￿¾￿");
      DFA25_special = DFA.unpackEncodedString("￿ ￿￿\b\tå￿}>");
      numStates = DFA25_transitionS.length;
      DFA25_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA25_transition[i] = DFA.unpackEncodedString(DFA25_transitionS[i]);
      }

      DFA26_transitionS = new String[]{"￿￿￿￿￿￿￿\r￿\"\b￿￿", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA26_eot = DFA.unpackEncodedString("#￿");
      DFA26_eof = DFA.unpackEncodedString("#￿");
      DFA26_min = DFA.unpackEncodedStringToUnsignedChars("\"￿");
      DFA26_max = DFA.unpackEncodedStringToUnsignedChars("M\"￿");
      DFA26_accept = DFA.unpackEncodedString("￿ ￿");
      DFA26_special = DFA.unpackEncodedString("#￿}>");
      numStates = DFA26_transitionS.length;
      DFA26_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
      }

      DFA30_transitionS = new String[]{"￿", "￿", "", "", "", "", "", "", "", ""};
      DFA30_eot = DFA.unpackEncodedString("\n￿");
      DFA30_eof = DFA.unpackEncodedString("\n￿");
      DFA30_min = DFA.unpackEncodedStringToUnsignedChars("\b￿");
      DFA30_max = DFA.unpackEncodedStringToUnsignedChars(":\b￿");
      DFA30_accept = DFA.unpackEncodedString("￿￿");
      DFA30_special = DFA.unpackEncodedString("\n￿}>");
      numStates = DFA30_transitionS.length;
      DFA30_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
      }

      FOLLOW_formula_in_translation_unit59 = new BitSet(new long[]{0L});
      FOLLOW_EOF_in_translation_unit61 = new BitSet(new long[]{2L});
      FOLLOW_scalar_formula_in_formula69 = new BitSet(new long[]{2L});
      FOLLOW_array_formula_in_formula81 = new BitSet(new long[]{2L});
      FOLLOW_SCALAR_FORMULA_in_scalar_formula97 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_scalar_formula99 = new BitSet(new long[]{8L});
      FOLLOW_ARRAY_FORMULA_in_array_formula109 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_array_formula111 = new BitSet(new long[]{8L});
      FOLLOW_logical_expression_in_primary_expression120 = new BitSet(new long[]{2L});
      FOLLOW_concat_expression_in_primary_expression144 = new BitSet(new long[]{2L});
      FOLLOW_additive_expression_in_primary_expression168 = new BitSet(new long[]{2L});
      FOLLOW_multiplicative_expression_in_primary_expression191 = new BitSet(new long[]{2L});
      FOLLOW_exponentiation_expression_in_primary_expression215 = new BitSet(new long[]{2L});
      FOLLOW_unary_expression_in_primary_expression238 = new BitSet(new long[]{2L});
      FOLLOW_reference_expression_in_primary_expression261 = new BitSet(new long[]{2L});
      FOLLOW_bracketed_expression_in_primary_expression285 = new BitSet(new long[]{2L});
      FOLLOW_percent_expression_in_primary_expression309 = new BitSet(new long[]{2L});
      FOLLOW_47_in_logical_expression319 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_logical_expression322 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_logical_expression324 = new BitSet(new long[]{8L});
      FOLLOW_50_in_logical_expression360 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_logical_expression363 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_logical_expression365 = new BitSet(new long[]{8L});
      FOLLOW_51_in_logical_expression407 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_logical_expression409 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_logical_expression411 = new BitSet(new long[]{8L});
      FOLLOW_54_in_logical_expression453 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_logical_expression455 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_logical_expression457 = new BitSet(new long[]{8L});
      FOLLOW_53_in_logical_expression499 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_logical_expression501 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_logical_expression503 = new BitSet(new long[]{8L});
      FOLLOW_52_in_logical_expression545 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_logical_expression548 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_logical_expression550 = new BitSet(new long[]{8L});
      FOLLOW_55_in_logical_expression592 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_logical_expression594 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_logical_expression596 = new BitSet(new long[]{8L});
      FOLLOW_56_in_concat_expression643 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_concat_expression645 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_concat_expression648 = new BitSet(new long[]{8L});
      FOLLOW_58_in_additive_expression669 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_additive_expression671 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_additive_expression673 = new BitSet(new long[]{8L});
      FOLLOW_57_in_additive_expression705 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_additive_expression707 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_additive_expression709 = new BitSet(new long[]{8L});
      FOLLOW_59_in_multiplicative_expression732 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_multiplicative_expression734 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_multiplicative_expression736 = new BitSet(new long[]{8L});
      FOLLOW_60_in_multiplicative_expression779 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_multiplicative_expression781 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_multiplicative_expression783 = new BitSet(new long[]{8L});
      FOLLOW_61_in_exponentiation_expression824 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_exponentiation_expression826 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_exponentiation_expression828 = new BitSet(new long[]{8L});
      FOLLOW_62_in_percent_expression851 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_percent_expression853 = new BitSet(new long[]{8L});
      FOLLOW_UNARY_MINUS_in_unary_expression875 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_unary_expression878 = new BitSet(new long[]{8L});
      FOLLOW_intersection_expression_in_union_expression896 = new BitSet(new long[]{2L});
      FOLLOW_reference_expression_in_intersection_expression906 = new BitSet(new long[]{2L});
      FOLLOW_BRACKET_EXPR_in_bracketed_expression917 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_bracketed_expression919 = new BitSet(new long[]{8L});
      FOLLOW_primitive_in_reference_expression934 = new BitSet(new long[]{2L});
      FOLLOW_const_array_in_reference_expression963 = new BitSet(new long[]{2L});
      FOLLOW_cell_reference_in_reference_expression992 = new BitSet(new long[]{2L});
      FOLLOW_cell_range_reference_in_reference_expression1021 = new BitSet(new long[]{2L});
      FOLLOW_column_reference_in_reference_expression1050 = new BitSet(new long[]{2L});
      FOLLOW_column_range_reference_in_reference_expression1079 = new BitSet(new long[]{2L});
      FOLLOW_named_range_reference_in_reference_expression1108 = new BitSet(new long[]{2L});
      FOLLOW_worksheet_named_range_reference_in_reference_expression1137 = new BitSet(new long[]{2L});
      FOLLOW_r1c1_reference_in_reference_expression1166 = new BitSet(new long[]{2L});
      FOLLOW_function_call_in_reference_expression1195 = new BitSet(new long[]{2L});
      FOLLOW_if_statement_in_reference_expression1225 = new BitSet(new long[]{2L});
      FOLLOW_workbook_in_reference_prefix1233 = new BitSet(new long[]{2L});
      FOLLOW_sheet_in_reference_prefix1237 = new BitSet(new long[]{2L});
      FOLLOW_workbook_name_in_workbook1246 = new BitSet(new long[]{67108864L, 2048L});
      FOLLOW_sheet_in_workbook1248 = new BitSet(new long[]{2L});
      FOLLOW_WORKSHEET_CELL_REF_in_worksheet_cell_reference1259 = new BitSet(new long[]{4L});
      FOLLOW_SHEET_REF_in_worksheet_cell_reference1263 = new BitSet(new long[]{12288L});
      FOLLOW_local_cell_reference_in_worksheet_cell_reference1265 = new BitSet(new long[]{8L});
      FOLLOW_CELL_REF_in_local_cell_reference1282 = new BitSet(new long[]{4L});
      FOLLOW_69_in_local_cell_reference1286 = new BitSet(new long[]{67108864L});
      FOLLOW_Identifier_in_local_cell_reference1291 = new BitSet(new long[]{134217736L, 32L});
      FOLLOW_69_in_local_cell_reference1296 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_local_cell_reference1301 = new BitSet(new long[]{8L});
      FOLLOW_worksheet_cell_reference_in_cell_reference1323 = new BitSet(new long[]{2L});
      FOLLOW_local_cell_reference_in_cell_reference1343 = new BitSet(new long[]{2L});
      FOLLOW_CELL_RANGE_REF_in_cell_range_reference1357 = new BitSet(new long[]{4L});
      FOLLOW_cell_reference_in_cell_range_reference1359 = new BitSet(new long[]{12288L});
      FOLLOW_cell_reference_in_cell_range_reference1361 = new BitSet(new long[]{8L});
      FOLLOW_worksheet_column_reference_in_column_reference1383 = new BitSet(new long[]{2L});
      FOLLOW_local_column_reference_in_column_reference1406 = new BitSet(new long[]{2L});
      FOLLOW_WORKSHEET_COLUMN_REF_in_worksheet_column_reference1417 = new BitSet(new long[]{4L});
      FOLLOW_SHEET_REF_in_worksheet_column_reference1421 = new BitSet(new long[]{49152L});
      FOLLOW_local_column_reference_in_worksheet_column_reference1423 = new BitSet(new long[]{8L});
      FOLLOW_COLUMN_REF_in_local_column_reference1439 = new BitSet(new long[]{4L});
      FOLLOW_69_in_local_column_reference1443 = new BitSet(new long[]{67108864L});
      FOLLOW_Identifier_in_local_column_reference1448 = new BitSet(new long[]{8L});
      FOLLOW_COLUMN_RANGE_REF_in_column_range_reference1464 = new BitSet(new long[]{4L});
      FOLLOW_column_reference_in_column_range_reference1466 = new BitSet(new long[]{49152L});
      FOLLOW_column_reference_in_column_range_reference1468 = new BitSet(new long[]{8L});
      FOLLOW_NAMED_RANGE_in_named_range_reference1484 = new BitSet(new long[]{4L});
      FOLLOW_Identifier_in_named_range_reference1488 = new BitSet(new long[]{8L});
      FOLLOW_NAMED_RANGE_in_worksheet_named_range_reference1504 = new BitSet(new long[]{4L});
      FOLLOW_SHEET_REF_in_worksheet_named_range_reference1508 = new BitSet(new long[]{67108864L});
      FOLLOW_Identifier_in_worksheet_named_range_reference1512 = new BitSet(new long[]{8L});
      FOLLOW_set_in_workbook_name0 = new BitSet(new long[]{2L});
      FOLLOW_sheetname_in_sheet1556 = new BitSet(new long[]{0L, 2L});
      FOLLOW_65_in_sheet1558 = new BitSet(new long[]{2L});
      FOLLOW_set_in_sheetname0 = new BitSet(new long[]{2L});
      FOLLOW_range_in_area_reference1580 = new BitSet(new long[]{2L, 12L});
      FOLLOW_set_in_area_reference1584 = new BitSet(new long[]{201326592L, 2080L});
      FOLLOW_area_reference_in_area_reference1590 = new BitSet(new long[]{2L, 12L});
      FOLLOW_vector_in_area_reference1605 = new BitSet(new long[]{2L, 12L});
      FOLLOW_set_in_area_reference1609 = new BitSet(new long[]{201326592L, 2080L});
      FOLLOW_area_reference_in_area_reference1615 = new BitSet(new long[]{2L, 12L});
      FOLLOW_cell_in_range1627 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_range1629 = new BitSet(new long[]{67108864L, 32L});
      FOLLOW_cell_in_range1631 = new BitSet(new long[]{2L});
      FOLLOW_sheet_in_range1641 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_range1643 = new BitSet(new long[]{67108864L, 32L});
      FOLLOW_cell_in_range1645 = new BitSet(new long[]{2L});
      FOLLOW_column_range_in_vector1654 = new BitSet(new long[]{2L});
      FOLLOW_row_range_in_vector1665 = new BitSet(new long[]{2L});
      FOLLOW_column_in_column_range1674 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_column_range1676 = new BitSet(new long[]{67108864L, 32L});
      FOLLOW_column_in_column_range1678 = new BitSet(new long[]{2L});
      FOLLOW_row_in_row_range1687 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_row_range1689 = new BitSet(new long[]{201326592L, 2080L});
      FOLLOW_row_in_row_range1691 = new BitSet(new long[]{2L});
      FOLLOW_69_in_cell1700 = new BitSet(new long[]{67108864L, 32L});
      FOLLOW_column_in_cell1703 = new BitSet(new long[]{201326594L, 2080L});
      FOLLOW_69_in_cell1707 = new BitSet(new long[]{201326592L, 2080L});
      FOLLOW_row_in_cell1710 = new BitSet(new long[]{2L});
      FOLLOW_Identifier_in_column1722 = new BitSet(new long[]{2L});
      FOLLOW_DecimalLiteral_in_row1732 = new BitSet(new long[]{2L});
      FOLLOW_r1c1_ref_row_in_r1c1_reference1741 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_r1c1_ref_col_in_r1c1_reference1743 = new BitSet(new long[]{2L});
      FOLLOW_76_in_r1c1_ref_col1752 = new BitSet(new long[]{134217728L, 16384L});
      FOLLOW_r1c1_ref_offset_in_r1c1_ref_col1754 = new BitSet(new long[]{2L});
      FOLLOW_76_in_r1c1_ref_col1758 = new BitSet(new long[]{2L});
      FOLLOW_77_in_r1c1_ref_row1767 = new BitSet(new long[]{134217728L, 16384L});
      FOLLOW_r1c1_ref_offset_in_r1c1_ref_row1769 = new BitSet(new long[]{2L});
      FOLLOW_78_in_r1c1_ref_offset1778 = new BitSet(new long[]{288230376151711744L});
      FOLLOW_58_in_r1c1_ref_offset1780 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_r1c1_ref_offset1782 = new BitSet(new long[]{0L, 32768L});
      FOLLOW_79_in_r1c1_ref_offset1784 = new BitSet(new long[]{2L});
      FOLLOW_78_in_r1c1_ref_offset1791 = new BitSet(new long[]{144115188075855872L});
      FOLLOW_57_in_r1c1_ref_offset1793 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_r1c1_ref_offset1795 = new BitSet(new long[]{0L, 32768L});
      FOLLOW_79_in_r1c1_ref_offset1797 = new BitSet(new long[]{2L});
      FOLLOW_78_in_r1c1_ref_offset1804 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_r1c1_ref_offset1810 = new BitSet(new long[]{0L, 32768L});
      FOLLOW_79_in_r1c1_ref_offset1812 = new BitSet(new long[]{2L});
      FOLLOW_DecimalLiteral_in_r1c1_ref_offset1827 = new BitSet(new long[]{2L});
      FOLLOW_ARRAY_COST_in_const_array1836 = new BitSet(new long[]{8388608L});
      FOLLOW_array_row_in_const_array1840 = new BitSet(new long[]{8388610L});
      FOLLOW_ARRAY_ROW_in_array_row1852 = new BitSet(new long[]{432345572683284480L});
      FOLLOW_array_element_list_in_array_row1854 = new BitSet(new long[]{2L});
      FOLLOW_array_element_in_array_element_list1865 = new BitSet(new long[]{432345572683284482L});
      FOLLOW_primitive_in_array_element1877 = new BitSet(new long[]{2L});
      FOLLOW_FUNCTION_CALL_in_function_call1906 = new BitSet(new long[]{4L});
      FOLLOW_Identifier_in_function_call1910 = new BitSet(new long[]{Long.MIN_VALUE});
      FOLLOW_63_in_function_call1918 = new BitSet(new long[]{9222386882910811200L, 8705L});
      FOLLOW_argument_list_in_function_call1920 = new BitSet(new long[]{0L, 1L});
      FOLLOW_64_in_function_call1923 = new BitSet(new long[]{8L});
      FOLLOW_IF_FUNCTION_in_if_statement1962 = new BitSet(new long[]{4L});
      FOLLOW_primary_expression_in_if_statement1964 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_if_statement1988 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_primary_expression_in_if_statement2012 = new BitSet(new long[]{8L});
      FOLLOW_argument_in_argument_list2033 = new BitSet(new long[]{2L, 4L});
      FOLLOW_66_in_argument_list2037 = new BitSet(new long[]{9222386882910811200L, 8704L});
      FOLLOW_argument_in_argument_list2039 = new BitSet(new long[]{2L, 4L});
      FOLLOW_primary_expression_in_argument2052 = new BitSet(new long[]{2L});
      FOLLOW_set_in_primitive2079 = new BitSet(new long[]{134217728L});
      FOLLOW_DecimalLiteral_in_primitive2088 = new BitSet(new long[]{2L});
      FOLLOW_set_in_primitive2108 = new BitSet(new long[]{536870912L});
      FOLLOW_FloatingPointLiteral_in_primitive2117 = new BitSet(new long[]{2L});
      FOLLOW_BooleanConstant_in_primitive2148 = new BitSet(new long[]{2L});
      FOLLOW_CharacterLiteral_in_primitive2177 = new BitSet(new long[]{2L});
      FOLLOW_StringLiteral_in_primitive2218 = new BitSet(new long[]{2L});
      FOLLOW_Error_in_primitive2256 = new BitSet(new long[]{2L});
      FOLLOW_named_range_reference_in_synpred24_ExcelFormulaTreeParser1108 = new BitSet(new long[]{2L});
      FOLLOW_worksheet_named_range_reference_in_synpred25_ExcelFormulaTreeParser1137 = new BitSet(new long[]{2L});
      FOLLOW_set_in_synpred38_ExcelFormulaTreeParser1584 = new BitSet(new long[]{201326592L, 2080L});
      FOLLOW_area_reference_in_synpred38_ExcelFormulaTreeParser1590 = new BitSet(new long[]{2L});
      FOLLOW_range_in_synpred39_ExcelFormulaTreeParser1580 = new BitSet(new long[]{2L, 12L});
      FOLLOW_set_in_synpred39_ExcelFormulaTreeParser1584 = new BitSet(new long[]{201326592L, 2080L});
      FOLLOW_area_reference_in_synpred39_ExcelFormulaTreeParser1590 = new BitSet(new long[]{2L, 12L});
      FOLLOW_set_in_synpred41_ExcelFormulaTreeParser1609 = new BitSet(new long[]{201326592L, 2080L});
      FOLLOW_area_reference_in_synpred41_ExcelFormulaTreeParser1615 = new BitSet(new long[]{2L});
      FOLLOW_76_in_synpred47_ExcelFormulaTreeParser1752 = new BitSet(new long[]{134217728L, 16384L});
      FOLLOW_r1c1_ref_offset_in_synpred47_ExcelFormulaTreeParser1754 = new BitSet(new long[]{2L});
      FOLLOW_array_element_in_synpred52_ExcelFormulaTreeParser1865 = new BitSet(new long[]{2L});
   }
}
