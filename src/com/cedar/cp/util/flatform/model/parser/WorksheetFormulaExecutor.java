// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRange;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.ColumnRange;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import com.cedar.cp.util.flatform.model.parser.FormulaExecutor;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.flatform.model.parser.ValueUtils;
import com.cedar.cp.util.flatform.model.parser.WorksheetCellRef;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunction;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionCategory;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionDetail;
import com.cedar.cp.util.flatform.model.parser.xml.FunctionExecutorLoader;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.tree.CommonTree;
import org.apache.commons.digester.Digester;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class WorksheetFormulaExecutor implements FormulaExecutor {

   private FlatformFunction mFlatFormFunction = null;
   private Map<String, FunctionExecutorLoader> mFunctionMap;
   private Worksheet mWorksheet;
   private int mCurrentRow;
   private int mCurrentColumn;
   private static final Double sDoubleZero = Double.valueOf(0.0D);
   private Log mLog = new Log(this.getClass());
   private ExcelFormulaTreeParser mExcelFormulaTreeParser;
   private Calendar mCalendar = new GregorianCalendar();


   public WorksheetFormulaExecutor() {
      this.loadFunctions();
   }

   public Object getCell(Object colInd, Object colId, Object rowInd, Object rowId) {
      CommonTree treeColId = (CommonTree)colId;
      CommonTree treeRowId = (CommonTree)rowId;
      CellRef cellRef = new CellRef();
      cellRef.setColumn(Integer.parseInt(treeColId.getText()));
      cellRef.setColumnAbsolute(colInd != null);
      if(treeRowId != null) {
         cellRef.setRow(Integer.parseInt(treeRowId.getText()));
         cellRef.setRowAbsolute(rowInd != null);
      }

      return cellRef;
   }

   public Object getColumn(Object colInd, Object colId) {
      CommonTree treeColId = (CommonTree)colId;
      CellRef cellRef = new CellRef();
      cellRef.setColumn(Integer.parseInt(treeColId.getText()));
      cellRef.setColumnAbsolute(colInd != null);
      cellRef.setRow(0);
      return cellRef;
   }

   public Object getWorksheetCell(Object sheetObj, Object cellObj) {
      CommonTree sheetNode = (CommonTree)sheetObj;
      CellRef cellRef = (CellRef)cellObj;
      return new WorksheetCellRef(sheetNode.getToken().getText(), cellRef);
   }

   public Object getCellRange(Object operandA, Object operandB) {
      CellRef startRef = (CellRef)operandA;
      CellRef endRef = (CellRef)operandB;
      return new CellRange(startRef, endRef);
   }

   public Object getNamedRange(Object operandA) {
      return CellErrorValue.NAME;
   }

   public Object getWorksheetNamedRange(Object sheetId, Object rangeId) {
      return CellErrorValue.NAME;
   }

   public Object getColumnRange(Object operandA, Object operandB) {
      CellRef startRef = (CellRef)operandA;
      startRef.setRow(1);
      startRef.setRowAbsolute(true);
      CellRef endRef = (CellRef)operandB;
      endRef.setRow(this.getWorksheet().getMaxRows());
      endRef.setRowAbsolute(true);
      return new ColumnRange(startRef, endRef);
   }

   public Object executeFunction(String name, List params) {
      FunctionExecutorLoader functionLoader = (FunctionExecutorLoader)this.mFunctionMap.get(name.toUpperCase());
      FunctionExecutor fe = functionLoader.getExecutor();
      if(fe != null) {
         return fe.execute(params);
      } else {
         throw new IllegalStateException("Unexpected function \'" + name + "\'");
      }
   }

   public int getColumn(String columnName) {
      return this.getWorksheet().getColumn(columnName);
   }

   public int getRow(String rowNumber) {
      return Integer.parseInt(rowNumber) - 1;
   }

   public Object getNumericCellValue(CellRef cellRef) {
      Cell cell = cellRef.getCell(this.getWorksheet(), this.mCurrentRow, this.mCurrentColumn);
      return cell != null?cell.getNumericValue():(cellRef.getWorksheet(this.getWorksheet()) != null?sDoubleZero:CellErrorValue.REF);
   }

   public Object getCellValue(CellRef cellRef) {
      Cell cell = cellRef.getCell(this.getWorksheet(), this.mCurrentRow, this.mCurrentColumn);
      return cell != null?cell.getObjectValue():(cellRef.getWorksheet(this.getWorksheet()) != null?"":CellErrorValue.REF);
   }

   public Object add(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         if(operandA instanceof Number && operandB instanceof Number) {
            return Double.valueOf(((Number)operandA).doubleValue() + ((Number)operandB).doubleValue());
         } else if(!(operandA instanceof Date) && !(operandB instanceof Date)) {
            return CellErrorValue.VALUE;
         } else if(operandA instanceof Number) {
            this.mCalendar.setTime((Date)operandB);
            this.mCalendar.add(6, ((Number)operandA).intValue());
            return this.mCalendar.getTime();
         } else if(operandB instanceof Number) {
            this.mCalendar.setTime((Date)operandA);
            this.mCalendar.add(6, ((Number)operandB).intValue());
            return this.mCalendar.getTime();
         } else {
            double dateValue = HSSFDateUtil.getExcelDate((Date)operandA) + HSSFDateUtil.getExcelDate((Date)operandB);
            return HSSFDateUtil.getJavaDate(dateValue);
         }
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object sub(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         if(operandA instanceof Number && operandB instanceof Number) {
            return Double.valueOf(((Number)operandA).doubleValue() - ((Number)operandB).doubleValue());
         } else if(!(operandA instanceof Date) && !(operandB instanceof Date)) {
            return CellErrorValue.VALUE;
         } else if(operandA instanceof Number) {
            this.mCalendar.setTime((Date)operandB);
            this.mCalendar.add(6, -((Number)operandA).intValue());
            return this.mCalendar.getTime();
         } else if(operandB instanceof Number) {
            this.mCalendar.setTime((Date)operandA);
            this.mCalendar.add(6, -((Number)operandB).intValue());
            return this.mCalendar.getTime();
         } else {
            return new Date(((Date)operandA).getTime() - ((Date)operandB).getTime());
         }
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object mul(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      return !(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)?Double.valueOf(this.getDoubleValue(operandA) * this.getDoubleValue(operandB)):(operandA instanceof CellErrorValue?operandA:operandB);
   }

   public Object div(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         if(operandB != null && ((Double)operandB).doubleValue() != 0.0D) {
            if(operandA instanceof Number && operandB instanceof Number) {
               return Double.valueOf(this.getDoubleValue(operandA) / this.getDoubleValue(operandB));
            } else if(!(operandA instanceof Date) && !(operandB instanceof Date)) {
               throw new IllegalStateException("Unexpected operands for \'/\' " + operandA + " and " + operandB);
            } else if(operandA instanceof Number) {
               this.mCalendar.setTime((Date)operandB);
               this.mCalendar.add(6, -((Number)operandA).intValue());
               return this.mCalendar.getTime();
            } else if(operandB instanceof Number) {
               this.mCalendar.setTime((Date)operandA);
               this.mCalendar.add(6, -((Number)operandB).intValue());
               return this.mCalendar.getTime();
            } else {
               return new Date(((Date)operandA).getTime() - ((Date)operandB).getTime());
            }
         } else {
            return CellErrorValue.DIV_ZERO;
         }
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object lt(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         operandA = this.coerceValueForComparison(operandA);
         operandB = this.coerceValueForComparison(operandB);
         return operandA instanceof Comparable && operandB instanceof Comparable?Boolean.valueOf(((Comparable)operandA).compareTo(operandB) < 0):Boolean.valueOf(this.getDoubleValue(operandA) < this.getDoubleValue(operandB));
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object le(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         operandA = this.coerceValueForComparison(operandA);
         operandB = this.coerceValueForComparison(operandB);
         return operandA instanceof Comparable && operandB instanceof Comparable?Boolean.valueOf(((Comparable)operandA).compareTo(operandB) <= 0):Boolean.valueOf(this.getDoubleValue(operandA) <= this.getDoubleValue(operandB));
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object eq(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         operandA = this.coerceValueForComparison(operandA);
         operandB = this.coerceValueForComparison(operandB);
         return Boolean.valueOf(!GeneralUtils.isDifferent(operandA, operandB));
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object ne(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         operandA = this.coerceValueForComparison(operandA);
         operandB = this.coerceValueForComparison(operandB);
         return Boolean.valueOf(GeneralUtils.isDifferent(operandA, operandB));
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object ge(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         operandA = this.coerceValueForComparison(operandA);
         operandB = this.coerceValueForComparison(operandB);
         return operandA instanceof Comparable && operandB instanceof Comparable?Boolean.valueOf(((Comparable)operandA).compareTo(operandB) >= 0):Boolean.valueOf(this.getDoubleValue(operandA) >= this.getDoubleValue(operandB));
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object gt(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      if(!(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)) {
         operandA = this.coerceValueForComparison(operandA);
         operandB = this.coerceValueForComparison(operandB);
         return !(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)?(operandA instanceof Comparable && operandB instanceof Comparable?Boolean.valueOf(((Comparable)operandA).compareTo(operandB) > 0):Boolean.valueOf(this.getDoubleValue(operandA) > this.getDoubleValue(operandB))):(operandA instanceof CellErrorValue?operandA:operandB);
      } else {
         return operandA instanceof CellErrorValue?operandA:operandB;
      }
   }

   public Object pow(Object operandA, Object operandB) {
      if(operandA instanceof CellRef) {
         operandA = this.getNumericCellValue((CellRef)operandA);
      }

      if(operandB instanceof CellRef) {
         operandB = this.getNumericCellValue((CellRef)operandB);
      }

      return !(operandA instanceof CellErrorValue) && !(operandB instanceof CellErrorValue)?Double.valueOf(Math.pow(this.getDoubleValue(operandA), this.getDoubleValue(operandB))):(operandA instanceof CellErrorValue?operandA:operandB);
   }

   public Object concatenate(Object operandA, Object operandB) {
      return this.getFunctionExecutor("CONCATENATE").execute(Arrays.asList(new Object[]{operandA, operandB}));
   }

   public Object percent(Object operand) {
      if(operand instanceof CellRef) {
         operand = this.getNumericCellValue((CellRef)operand);
      }

      return operand instanceof CellErrorValue?operand:Double.valueOf(this.getDoubleValue(operand) / 100.0D);
   }

   public Object neg(Object operand) {
      if(operand instanceof CellRef) {
         operand = this.getNumericCellValue((CellRef)operand);
      }

      return operand instanceof CellErrorValue?operand:(operand instanceof Number?Double.valueOf(-this.getDoubleValue(operand)):operand);
   }

   private Object coerceValueForComparison(Object operand) {
      if(operand instanceof Number) {
         return Double.valueOf(((Number)operand).doubleValue());
      } else if(!(operand instanceof String)) {
         return CellErrorValue.VALUE;
      } else {
         if(ValueUtils.isNumberChars((String)operand)) {
            try {
               return Double.valueOf(ValueUtils.getDoubleValue((String)operand));
            } catch (ParseException var3) {
               ;
            }
         } else if(this.looksLikeADate((String)operand)) {
            Date value = ValueUtils.convert2Date((String)operand, false);
            if(value != null) {
               return value;
            }
         }

         return (String)operand;
      }
   }

   private boolean looksLikeADate(String s) {
      if(s != null && s.trim().length() != 0) {
         int len = s.length();

         for(int i = 0; i < len; ++i) {
            if(Character.isDigit(s.charAt(i))) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private double getDoubleValue(Object value) {
      if(value instanceof CellRef) {
         value = this.getNumericCellValue((CellRef)value);
      }

      if(value instanceof Number) {
         return ((Number)value).doubleValue();
      } else if(value == null) {
         this.mLog.info("getDouvleValue", "FOUND NULL REF");
         return 0.0D;
      } else {
         throw new IllegalStateException("Unexpected object when expecting number:" + value);
      }
   }

   public int getColumnOffset(String columnName) {
      return this.getColumn(columnName) - this.mCurrentColumn;
   }

   public int getRowOffset(String rowNumber) {
      return this.getRow(rowNumber) - this.mCurrentRow;
   }

   public boolean isValidFunction(String functionName) {
      return this.mFunctionMap.get(functionName.toUpperCase()) != null;
   }

   public int getCurrentRow() {
      return this.mCurrentRow;
   }

   public void setCurrentRow(int currentRow) {
      this.mCurrentRow = currentRow;
   }

   public int getCurrentColumn() {
      return this.mCurrentColumn;
   }

   public void setCurrentColumn(int currentColumn) {
      this.mCurrentColumn = currentColumn;
   }

   public Worksheet getWorksheet() {
      return this.mWorksheet;
   }

   public void setWorksheet(Worksheet worksheet) {
      this.mWorksheet = worksheet;
   }

   private void loadFunctions() {
      this.loadFunctionsDetailFromXMLDefinition();
      if(this.mFlatFormFunction != null) {
         List categoryList = this.mFlatFormFunction.getFunctionCategories();
         if(categoryList == null || categoryList.size() <= 0) {
            throw new IllegalStateException("No Flatform functions definition");
         }

         this.mFunctionMap = new HashMap();
         Iterator i$ = categoryList.iterator();

         while(i$.hasNext()) {
            FlatformFunctionCategory flatformFunctionCategory = (FlatformFunctionCategory)i$.next();
            List functionDetails = flatformFunctionCategory.getFunctionList();
            Iterator i$1 = functionDetails.iterator();

            while(i$1.hasNext()) {
               FlatformFunctionDetail flatformFunctionDetail = (FlatformFunctionDetail)i$1.next();
               FunctionExecutorLoader loader = new FunctionExecutorLoader(flatformFunctionDetail, this);
               this.mFunctionMap.put(flatformFunctionDetail.getName().toUpperCase(), loader);
            }
         }
      }

   }

   public FlatformFunction getFlatFormFunction() {
      return this.mFlatFormFunction;
   }

   public void setFlatFormFunction(FlatformFunction mFlatFormFunction) {
      this.mFlatFormFunction = mFlatFormFunction;
   }

   private void loadFunctionsDetailFromXMLDefinition() {
      Digester digester = new Digester();
      digester.setValidating(false);
      digester.addObjectCreate("flatform-function", FlatformFunction.class);
      digester.addObjectCreate("flatform-function/category", FlatformFunctionCategory.class);
      digester.addSetProperties("flatform-function/category", "name", "name");
      digester.addSetProperties("flatform-function/category", "CategoryId", "categoryId");
      digester.addSetNext("flatform-function/category", "addFunctionCategoryList");
      digester.addObjectCreate("flatform-function/category/function", FlatformFunctionDetail.class);
      digester.addSetProperties("flatform-function/category/function", "name", "name");
      digester.addBeanPropertySetter("flatform-function/category/function/short-desc", "shortDesc");
      digester.addBeanPropertySetter("flatform-function/category/function/detailed-desc", "detailedDesc");
      digester.addBeanPropertySetter("flatform-function/category/function/class", "functionClass");
      digester.addSetNext("flatform-function/category/function", "addFunction");
      InputStream inputStream = FlatformFunctionCategory.class.getResourceAsStream("functions.xml");

      try {
         this.mFlatFormFunction = (FlatformFunction)digester.parse(inputStream);
      } catch (Exception var4) {
         throw new IllegalStateException("No FlatForm functions definition found");
      }
   }

   public boolean isSemanticProcessingEnabled() {
      return this.mExcelFormulaTreeParser.isSemanticProcessingEnabled();
   }

   public void setSematicProcessingEnabled(boolean enable) {
      this.mExcelFormulaTreeParser.setSemanticProcessingEnabled(enable);
   }

   public void setExcelFormulaTreeParser(ExcelFormulaTreeParser treeParser) {
      this.mExcelFormulaTreeParser = treeParser;
   }

   public FunctionExecutor getFunctionExecutor(String functionName) {
      FunctionExecutorLoader loader = (FunctionExecutorLoader)this.mFunctionMap.get(functionName.toUpperCase());
      return loader.getExecutor();
   }

}
