// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.flatform.model.parser.FunctionValidator;
import java.util.List;

public interface FormulaExecutor extends FunctionValidator {

   Object getWorksheetCell(Object var1, Object var2);

   Object getCell(Object var1, Object var2, Object var3, Object var4);

   Object getColumn(Object var1, Object var2);

   Object getColumnRange(Object var1, Object var2);

   Object getCellRange(Object var1, Object var2);

   Object getNamedRange(Object var1);

   Object getWorksheetNamedRange(Object var1, Object var2);

   Object add(Object var1, Object var2);

   Object sub(Object var1, Object var2);

   Object mul(Object var1, Object var2);

   Object div(Object var1, Object var2);

   Object lt(Object var1, Object var2);

   Object le(Object var1, Object var2);

   Object eq(Object var1, Object var2);

   Object ne(Object var1, Object var2);

   Object ge(Object var1, Object var2);

   Object gt(Object var1, Object var2);

   Object pow(Object var1, Object var2);

   Object concatenate(Object var1, Object var2);

   Object executeFunction(String var1, List var2);

   Object neg(Object var1);

   Object percent(Object var1);

   int getColumn(String var1);

   int getRow(String var1);

   int getColumnOffset(String var1);

   int getRowOffset(String var1);

   boolean isValidFunction(String var1);

   Worksheet getWorksheet();

   Object getNumericCellValue(CellRef var1);

   Object getCellValue(CellRef var1);

   int getCurrentRow();

   int getCurrentColumn();

   boolean isSemanticProcessingEnabled();

   void setSematicProcessingEnabled(boolean var1);

   FunctionExecutor getFunctionExecutor(String var1);
}
