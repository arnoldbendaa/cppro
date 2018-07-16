// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.admin.tidytask;

import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import java.io.Serializable;

public class TidyTaskEditorSessionSSO implements Serializable {

   private TidyTaskImpl mEditorData;


   public TidyTaskEditorSessionSSO() {}

   public TidyTaskEditorSessionSSO(TidyTaskImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(TidyTaskImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public TidyTaskImpl getEditorData() {
      return this.mEditorData;
   }
}
