// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import java.io.Serializable;

public class ExternalSystemEditorSessionSSO implements Serializable {

   private ExternalSystemImpl mEditorData;


   public ExternalSystemEditorSessionSSO() {}

   public ExternalSystemEditorSessionSSO(ExternalSystemImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ExternalSystemImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ExternalSystemImpl getEditorData() {
      return this.mEditorData;
   }
}
