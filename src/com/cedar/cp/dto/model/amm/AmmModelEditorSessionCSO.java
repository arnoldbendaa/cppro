// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.dto.model.amm.AmmModelImpl;
import java.io.Serializable;

public class AmmModelEditorSessionCSO implements Serializable {

   private int mUserId;
   private AmmModelImpl mEditorData;


   public AmmModelEditorSessionCSO(int userId, AmmModelImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public AmmModelImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
