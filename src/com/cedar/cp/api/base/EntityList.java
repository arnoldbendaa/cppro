// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import java.io.Serializable;
import java.util.List;

public interface EntityList extends Serializable {

   String[] getHeadings();

   List getDataAsList();

   Object[][] getDataAsArray();

   Object getValueAt(int var1, String var2);

   Object getColumnAt(String var1);

   Object[] getValues(String var1);

   EntityList getRowData(int var1);

   EntityList getRowData(Object var1, String var2);

   int getNumRows();

   int getNumColumns();

   boolean includesEntity(String var1);
}
