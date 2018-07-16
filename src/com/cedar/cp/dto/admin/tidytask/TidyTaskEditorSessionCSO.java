// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.admin.tidytask;

import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import java.io.Serializable;

public class TidyTaskEditorSessionCSO implements Serializable {

   private int mUserId;
   private TidyTaskImpl mEditorData;


   public TidyTaskEditorSessionCSO(int userId, TidyTaskImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public TidyTaskImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
