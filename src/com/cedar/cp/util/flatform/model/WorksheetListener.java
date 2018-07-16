// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.flatform.model.event.WorksheetEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatEvent;
import java.util.EventListener;

public interface WorksheetListener extends EventListener {

   void worksheetStructureChange(WorksheetEvent var1);

   void worksheetStructureChanged(WorksheetEvent var1);

   void worksheetFormatChanged(WorksheetFormatEvent var1);
}
