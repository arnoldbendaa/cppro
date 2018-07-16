// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cm;

import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import java.io.Serializable;

public class ChangeMgmtEditorSessionSSO implements Serializable {

   private ChangeMgmtImpl mEditorData;


   public ChangeMgmtEditorSessionSSO() {}

   public ChangeMgmtEditorSessionSSO(ChangeMgmtImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ChangeMgmtImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ChangeMgmtImpl getEditorData() {
      return this.mEditorData;
   }
}
