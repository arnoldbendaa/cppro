// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.gui.WorksheetPanel$FormatEditor;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import com.cedar.cp.util.flatform.model.format.UnderlineFontProperty;
import java.util.Collection;
import java.util.Map;

class WorksheetPanel$14 implements WorksheetPanel$FormatEditor {

   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$14(WorksheetPanel var1) {
      this.this$0 = var1;
   }

   public void updateProps(Map<String, Collection<CellFormatEntry>> existingProps, Map<String, FormatProperty> newProps) {
      boolean underline = true;
      Collection underlineFormatEntries = (Collection)existingProps.get("underlineFont");
      if(underlineFormatEntries != null && !underlineFormatEntries.isEmpty()) {
         CellFormatEntry property = CellFormatEntry.selectMostSignificantFormat(underlineFormatEntries);
         underline = !((UnderlineFontProperty)property.getFormatProperty()).isUnderlineFont();
      }

      UnderlineFontProperty property1 = new UnderlineFontProperty(underline);
      newProps.put(property1.getName(), property1);
   }
}
