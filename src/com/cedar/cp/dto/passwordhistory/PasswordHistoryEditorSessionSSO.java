// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.passwordhistory;

import com.cedar.cp.dto.passwordhistory.PasswordHistoryImpl;
import java.io.Serializable;

public class PasswordHistoryEditorSessionSSO implements Serializable {

   private PasswordHistoryImpl mEditorData;


   public PasswordHistoryEditorSessionSSO() {}

   public PasswordHistoryEditorSessionSSO(PasswordHistoryImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(PasswordHistoryImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public PasswordHistoryImpl getEditorData() {
      return this.mEditorData;
   }
}
