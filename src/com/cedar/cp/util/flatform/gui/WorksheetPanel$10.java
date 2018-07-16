// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.gui.WorksheetPanel$FormatEditor;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import com.cedar.cp.util.flatform.model.format.TextColorProperty;
import java.awt.Color;
import java.util.Collection;
import java.util.Map;

class WorksheetPanel$10 implements WorksheetPanel$FormatEditor {

   // $FF: synthetic field
   final Color val$color;
   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$10(WorksheetPanel var1, Color var2) {
      this.this$0 = var1;
      this.val$color = var2;
   }

   public void updateProps(Map<String, Collection<CellFormatEntry>> existingProps, Map<String, FormatProperty> newProps) {
      TextColorProperty property = new TextColorProperty(this.val$color);
      newProps.put(property.getName(), property);
   }
}
