// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.gui.WorksheetPanel$FormatEditor;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import com.cedar.cp.util.flatform.model.format.ItalicFontProperty;
import java.util.Collection;
import java.util.Map;

class WorksheetPanel$13 implements WorksheetPanel$FormatEditor {

   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$13(WorksheetPanel var1) {
      this.this$0 = var1;
   }

   public void updateProps(Map<String, Collection<CellFormatEntry>> existingProps, Map<String, FormatProperty> newProps) {
      boolean italic = true;
      Collection italicFontEntries = (Collection)existingProps.get("italicFont");
      if(italicFontEntries != null && !italicFontEntries.isEmpty()) {
         CellFormatEntry property = CellFormatEntry.selectMostSignificantFormat(italicFontEntries);
         italic = !((ItalicFontProperty)property.getFormatProperty()).isItalicFont();
      }

      ItalicFontProperty property1 = new ItalicFontProperty(italic);
      newProps.put(property1.getName(), property1);
   }
}
