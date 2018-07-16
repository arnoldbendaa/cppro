// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.gui.WorksheetPanel$FormatEditor;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import com.cedar.cp.util.flatform.model.format.HorizontalAlignmentProperty;
import java.util.Collection;
import java.util.Map;

class WorksheetPanel$11 implements WorksheetPanel$FormatEditor {

   // $FF: synthetic field
   final int val$alignment;
   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$11(WorksheetPanel var1, int var2) {
      this.this$0 = var1;
      this.val$alignment = var2;
   }

   public void updateProps(Map<String, Collection<CellFormatEntry>> existingProps, Map<String, FormatProperty> newProps) {
      HorizontalAlignmentProperty property = new HorizontalAlignmentProperty(this.val$alignment);
      newProps.put(property.getName(), property);
   }
}
