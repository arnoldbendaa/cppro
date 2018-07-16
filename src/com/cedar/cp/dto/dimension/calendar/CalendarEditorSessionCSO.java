// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import java.io.Serializable;

public class CalendarEditorSessionCSO implements Serializable {

   private CalendarImpl mEditorData;
   private int mUserId;


   public CalendarEditorSessionCSO(int userId, CalendarImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public CalendarImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
