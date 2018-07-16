// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.event;

import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatEvent;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.util.Collection;

public class WorksheetFormatsSelectionEvent extends WorksheetFormatEvent {

   private Collection<FormatProperty> mFormatProperties;


   public WorksheetFormatsSelectionEvent(Worksheet source, int startRow, int startColumn, int endRow, int endColumn, Collection<FormatProperty> properties) {
      super(source, startRow, startColumn, endRow, endColumn);
      this.mFormatProperties = properties;
   }

   public Collection<FormatProperty> getFormatProperties() {
      return this.mFormatProperties;
   }
}
