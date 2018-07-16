// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.user.DataEntryProfileImpl;
import java.io.Serializable;

public class DataEntryProfileEditorSessionCSO implements Serializable {

   private int mUserId;
   private DataEntryProfileImpl mEditorData;


   public DataEntryProfileEditorSessionCSO(int userId, DataEntryProfileImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public DataEntryProfileImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
