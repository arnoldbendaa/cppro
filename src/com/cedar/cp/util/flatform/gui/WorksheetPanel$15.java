// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.gui.WorksheetPanel$FormatEditor;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.FontNameProperty;
import com.cedar.cp.util.flatform.model.format.FontSizeProperty;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.util.Collection;
import java.util.Map;

class WorksheetPanel$15 implements WorksheetPanel$FormatEditor {

   // $FF: synthetic field
   final String val$name;
   // $FF: synthetic field
   final Integer val$size;
   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$15(WorksheetPanel var1, String var2, Integer var3) {
      this.this$0 = var1;
      this.val$name = var2;
      this.val$size = var3;
   }

   public void updateProps(Map<String, Collection<CellFormatEntry>> existingProps, Map<String, FormatProperty> newProps) {
      FontNameProperty property = new FontNameProperty(this.val$name);
      newProps.put(property.getName(), property);
      FontSizeProperty property1 = new FontSizeProperty(this.val$size.intValue());
      newProps.put(property1.getName(), property1);
   }
}
