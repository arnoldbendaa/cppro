// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.ra;

import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import java.io.Serializable;

public class ResponsibilityAreaEditorSessionSSO implements Serializable {

   private ResponsibilityAreaImpl mEditorData;


   public ResponsibilityAreaEditorSessionSSO() {}

   public ResponsibilityAreaEditorSessionSSO(ResponsibilityAreaImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ResponsibilityAreaImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ResponsibilityAreaImpl getEditorData() {
      return this.mEditorData;
   }
}
