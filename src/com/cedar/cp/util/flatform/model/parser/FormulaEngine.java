// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$cell_reference_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$translation_unit_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import com.cedar.cp.util.flatform.model.parser.Formula;
import com.cedar.cp.util.flatform.model.parser.FunctionValidator;
import com.cedar.cp.util.flatform.model.parser.WorksheetFormulaExecutor;
import com.cedar.cp.util.flatform.model.undo.CellEdit;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeNodeStream;

public class FormulaEngine implements FunctionValidator {

   private int mColumn;
   private int mRow;
   private StringBuilder mFormulaRewriter;
   private Object mFormulaAST;
   private ExcelFormulaLexer mExcelFormulaLexer = new ExcelFormulaLexer();
   private WorksheetFormulaExecutor mFormulaExecutor;
   private ExcelFormulaParser mExcelFormulaParser;
   private ExcelFormulaTreeParser mExcelFormulaTreeParser;
   private CommonTokenStream mCommonTokenStream;
   private Map<String, Formula> mFormulaCache;
   private Set<CellRangeRef> mRefs;
   private List<Pair<CellRef, int[]>> mRefOffsets;
   private transient Log mLog = new Log(this.getClass());


   public FormulaEngine() {
      this.mCommonTokenStream = new CommonTokenStream(this.mExcelFormulaLexer);
      this.mExcelFormulaParser = new ExcelFormulaParser(this.mCommonTokenStream);
      this.mExcelFormulaTreeParser = new ExcelFormulaTreeParser((TreeNodeStream)null);
      this.mFormulaRewriter = new StringBuilder();
      this.mFormulaCache = new HashMap();
      this.mFormulaExecutor = new WorksheetFormulaExecutor();
      this.mExcelFormulaParser.setFunctionValidator(this.mFormulaExecutor);
      this.mRefs = new HashSet();
      this.mRefOffsets = new ArrayList();
   }

   public CellRangeRef parseCellRef(Worksheet worksheet, String cellRefText, int row, int column) throws ParseException {
      ANTLRStringStream input = new ANTLRStringStream(cellRefText);
      this.mExcelFormulaLexer.setCharStream(input);
      this.mCommonTokenStream.setTokenSource(this.mExcelFormulaLexer);
      this.mExcelFormulaParser.setTokenStream(this.mCommonTokenStream);
      this.mExcelFormulaParser.setCurrentPosition(row, column);
      this.mExcelFormulaParser.setColumnMapping(worksheet);

      try {
         ExcelFormulaParser$cell_reference_return r = this.mExcelFormulaParser.cell_reference();
      } catch (RecognitionException var10) {
         String hdr = this.mExcelFormulaParser.getErrorHeader(var10);
         String msg = this.mExcelFormulaParser.getErrorMessage(var10, this.mExcelFormulaParser.getTokenNames());
         throw new ParseException(hdr + ' ' + msg, var10.index + 1);
      }

      return this.mExcelFormulaParser.getCellRefs().isEmpty()?null:(CellRangeRef)this.mExcelFormulaParser.getCellRefs().iterator().next();
   }

   public String compile(Worksheet worksheet, String formula, int row, int column) throws ParseException {
      ANTLRStringStream input = new ANTLRStringStream(formula);
      this.mExcelFormulaLexer.setCharStream(input);
      this.mCommonTokenStream.setTokenSource(this.mExcelFormulaLexer);
      this.mExcelFormulaParser.setTokenStream(this.mCommonTokenStream);
      this.mExcelFormulaParser.setCurrentPosition(row, column);
      this.mExcelFormulaParser.setColumnMapping(worksheet);

      ExcelFormulaParser$translation_unit_return r;
      try {
         r = this.mExcelFormulaParser.translation_unit();
      } catch (RecognitionException var11) {
         StringBuilder refIndex = new StringBuilder();
         refIndex.append("\'");
         refIndex.append(worksheet.getName());
         refIndex.append(":");
         refIndex.append(worksheet.getColumnName(column));
         refIndex.append(String.valueOf(row + 1));
         String cellRefOffset = this.mExcelFormulaParser.getErrorHeader(var11);
         String i = this.mExcelFormulaParser.getErrorMessage(var11, this.mExcelFormulaParser.getTokenNames());
         throw new ParseException(refIndex.toString() + ' ' + cellRefOffset + ' ' + i, var11.index + 1);
      }

      this.mFormulaAST = r.getTree();
      this.mRefs.clear();
      this.mRefs.addAll(this.mExcelFormulaParser.getCellRefs());
      this.mRefOffsets.clear();
      this.mRefOffsets.addAll(this.mExcelFormulaParser.getCellRefOffsets());
      this.mFormulaRewriter.setLength(0);
      int len = formula.length();
      int var12 = 0;
      Pair var13 = var12 < this.mRefOffsets.size()?(Pair)this.mRefOffsets.get(var12):null;

      for(int var14 = 0; var14 < len; ++var14) {
         if(var13 != null && ((int[])var13.getChild2())[1] == var14) {
            this.mFormulaRewriter.append(((CellRef)var13.getChild1()).getRowColumnText());
            var14 = ((int[])var13.getChild2())[3];
            ++var12;
            var13 = var12 < this.mRefOffsets.size()?(Pair)this.mRefOffsets.get(var12):null;
         } else {
            this.mFormulaRewriter.append(formula.charAt(var14));
         }
      }

      return this.mFormulaRewriter.toString();
   }

   public Object execute(Worksheet worksheet, int row, int column, Formula formula) throws RecognitionException {
      this.mRow = row;
      this.mColumn = column;
      Cell cell = (Cell)worksheet.get(row, column);
      String oldCellText = cell.getCellText();
      CommonTreeNodeStream nodes = new CommonTreeNodeStream(formula.getParseTree());
      this.mExcelFormulaTreeParser.setTreeNodeStream(nodes);
      WorksheetFormulaExecutor fe = this.mFormulaExecutor;
      fe.setWorksheet(worksheet);
      fe.setCurrentColumn(column);
      fe.setCurrentRow(row);
      this.mExcelFormulaTreeParser.setExecutor(fe);
      fe.setExcelFormulaTreeParser(this.mExcelFormulaTreeParser);
      this.mExcelFormulaTreeParser.translation_unit();
      Object value = this.mExcelFormulaTreeParser.pop();
      if(value instanceof CellRef) {
         value = fe.getCellValue((CellRef)value);
      }

      cell.setValue(value);
      if(cell.getOutputMapping() != null && cell.isPostValueChanged()) {
         CellEdit edit = new CellEdit(worksheet, 0, row, column, oldCellText, cell.getCellText());
         worksheet.getWorkbook().postSingleUndoableEdit(edit);
      }

      return value;
   }

   public boolean isFormula(String formula) {
      return formula != null && formula.trim().length() > 0 && formula.trim().charAt(0) == 61;
   }

   public int getColumn() {
      return this.mColumn;
   }

   public Object getFormulaAST() {
      return this.mFormulaAST;
   }

   public void setColumn(int column) {
      this.mColumn = column;
   }

   public int getRow() {
      return this.mRow;
   }

   public void setRow(int row) {
      this.mRow = row;
   }

   public Formula getFormula(String formulaText) {
      return (Formula)this.mFormulaCache.get(formulaText);
   }

   public void setFormula(String formulaText, Formula formula) {
      this.mFormulaCache.put(formulaText, formula);
   }

   public Set<CellRangeRef> getRefs() {
      return this.mRefs;
   }

   public int getFormulaCount() {
      return this.mFormulaCache.size();
   }

   public List<Pair<CellRef, int[]>> getRefOffsets() {
      return this.mRefOffsets;
   }

   public void dumpFormula() {
      Iterator i$ = this.mFormulaCache.values().iterator();

      while(i$.hasNext()) {
         Formula formula = (Formula)i$.next();
         this.mLog.debug(formula.toString());
      }

   }

   public void renameSheetReference(Worksheet worksheet, String newName) {
      Iterator i$ = (new ArrayList(this.mFormulaCache.values())).iterator();

      while(i$.hasNext()) {
         Formula formula = (Formula)i$.next();
         if(formula.renameSheetReference(worksheet, newName)) {
            try {
               String e = this.compile(worksheet, formula.getOriginFormulaText(), formula.getOriginRow(), formula.getOriginColumn());
               this.mFormulaCache.remove(formula.getFormula());
               formula.setFormula(e);
               formula.setParseTree(this.mFormulaAST);
               this.mFormulaCache.put(formula.getFormula(), formula);
            } catch (ParseException var6) {
               throw new IllegalStateException("Formula recompilation error during worksheet rename:" + var6.getMessage());
            }
         }
      }

   }

   public boolean isValidFunction(String functionName) {
      return this.mFormulaExecutor.isValidFunction(functionName);
   }

   private Log getLog() {
      if(this.mLog == null) {
         this.mLog = new Log(this.getClass());
      }

      return this.mLog;
   }
}
