// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.dto.model.recharge.RechargeImpl;
import java.io.Serializable;

public class RechargeEditorSessionCSO implements Serializable {

   private int mUserId;
   private RechargeImpl mEditorData;


   public RechargeEditorSessionCSO(int userId, RechargeImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public RechargeImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
