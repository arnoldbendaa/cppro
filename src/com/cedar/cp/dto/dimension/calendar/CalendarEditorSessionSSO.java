// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import java.io.Serializable;

public class CalendarEditorSessionSSO implements Serializable {

   private CalendarImpl mEditorData;


   public CalendarImpl getEditorData() {
      return this.mEditorData;
   }

   public void setEditorData(CalendarImpl editorData) {
      this.mEditorData = editorData;
   }
}
