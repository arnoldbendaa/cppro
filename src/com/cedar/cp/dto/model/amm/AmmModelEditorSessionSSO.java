// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.dto.model.amm.AmmModelImpl;
import java.io.Serializable;

public class AmmModelEditorSessionSSO implements Serializable {

   private AmmModelImpl mEditorData;


   public AmmModelEditorSessionSSO() {}

   public AmmModelEditorSessionSSO(AmmModelImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(AmmModelImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public AmmModelImpl getEditorData() {
      return this.mEditorData;
   }
}
