// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.gui.WorksheetPanel$FormatEditor;
import com.cedar.cp.util.flatform.model.format.BoldFontProperty;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.util.Collection;
import java.util.Map;

class WorksheetPanel$12 implements WorksheetPanel$FormatEditor {

   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$12(WorksheetPanel var1) {
      this.this$0 = var1;
   }

   public void updateProps(Map<String, Collection<CellFormatEntry>> existingProps, Map<String, FormatProperty> newProps) {
      boolean bold = true;
      Collection boldFontEntries = (Collection)existingProps.get("boldFont");
      if(boldFontEntries != null && !boldFontEntries.isEmpty()) {
         CellFormatEntry property = CellFormatEntry.selectMostSignificantFormat(boldFontEntries);
         bold = !((BoldFontProperty)property.getFormatProperty()).isBoldFont();
      }

      BoldFontProperty property1 = new BoldFontProperty(bold);
      newProps.put(property1.getName(), property1);
   }
}
