// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.passwordhistory;

import com.cedar.cp.dto.passwordhistory.PasswordHistoryImpl;
import java.io.Serializable;

public class PasswordHistoryEditorSessionCSO implements Serializable {

   private int mUserId;
   private PasswordHistoryImpl mEditorData;


   public PasswordHistoryEditorSessionCSO(int userId, PasswordHistoryImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public PasswordHistoryImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
