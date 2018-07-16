// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.util.Collection;
import java.util.Map;

interface WorksheetPanel$FormatEditor {

   void updateProps(Map<String, Collection<CellFormatEntry>> var1, Map<String, FormatProperty> var2);
}
