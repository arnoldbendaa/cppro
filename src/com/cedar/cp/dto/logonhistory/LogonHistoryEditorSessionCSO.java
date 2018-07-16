// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.logonhistory;

import com.cedar.cp.dto.logonhistory.LogonHistoryImpl;
import java.io.Serializable;

public class LogonHistoryEditorSessionCSO implements Serializable {

   private int mUserId;
   private LogonHistoryImpl mEditorData;


   public LogonHistoryEditorSessionCSO(int userId, LogonHistoryImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public LogonHistoryImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
