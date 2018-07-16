// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.logonhistory;

import com.cedar.cp.dto.logonhistory.LogonHistoryImpl;
import java.io.Serializable;

public class LogonHistoryEditorSessionSSO implements Serializable {

   private LogonHistoryImpl mEditorData;


   public LogonHistoryEditorSessionSSO() {}

   public LogonHistoryEditorSessionSSO(LogonHistoryImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(LogonHistoryImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public LogonHistoryImpl getEditorData() {
      return this.mEditorData;
   }
}
