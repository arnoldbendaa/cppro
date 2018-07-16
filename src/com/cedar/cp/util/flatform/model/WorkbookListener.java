// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.flatform.model.event.WorkbookEvent;
import com.cedar.cp.util.flatform.model.event.WorkbookReorderWorksheetsEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatsSelectionEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetRenamedEvent;
import java.util.EventListener;

public interface WorkbookListener extends EventListener {

   void worksheetAdded(WorkbookEvent var1);

   void worksheetRemoved(WorkbookEvent var1);

   void worksheetRenamed(WorksheetRenamedEvent var1);

   void worksheetMoved(WorkbookReorderWorksheetsEvent var1);

   void worsheetFormatsSelection(WorksheetFormatsSelectionEvent var1);
}
