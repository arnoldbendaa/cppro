// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform.rebuild;

import com.cedar.cp.dto.xmlform.rebuild.FormRebuildImpl;
import java.io.Serializable;

public class FormRebuildEditorSessionSSO implements Serializable {

   private FormRebuildImpl mEditorData;


   public FormRebuildEditorSessionSSO() {}

   public FormRebuildEditorSessionSSO(FormRebuildImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(FormRebuildImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public FormRebuildImpl getEditorData() {
      return this.mEditorData;
   }
}
