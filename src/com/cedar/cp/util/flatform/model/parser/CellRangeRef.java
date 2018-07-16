// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;

public interface CellRangeRef extends Cloneable {

   CellRef getStartRef();

   CellRef getEndRef();

   boolean isSingleCell();

   boolean isVector();

   int getWidth();

   int getDepth();

   int getAbsoluteStartRow(int var1);

   int getAbsoluteEndRow(int var1);

   int getAbsoluteStartColumn(int var1);

   int getAbsoluteEndColumn(int var1);

   Worksheet getWorksheet(Worksheet var1);

   boolean renameWorksheetReference(String var1, String var2);

   boolean insertColumn(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   boolean isInsertColumnRelevent(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   boolean insertRow(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   boolean isInsertRowRelevent(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   boolean removeColumn(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   boolean isRemoveColumnRelevent(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   boolean removeRow(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   boolean isRemoveRowRelevent(Worksheet var1, int var2, int var3, Worksheet var4, int var5, int var6);

   Object clone() throws CloneNotSupportedException;
}
