// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.Serializable;
import java.util.List;

public interface SqlResultSet extends Serializable {

   int getNumRows();

   int getNumColumns();

   int getColumnNameIndex(String var1);

   String getColumnName(int var1);

   Class getColumnClass(int var1);

   Object getValueAt(int var1, int var2);

   Object getValueAt(int var1, String var2);

   void setValueAt(int var1, int var2, Object var3);

   List<Object[]> getList();

   Object[][] getData();
}
