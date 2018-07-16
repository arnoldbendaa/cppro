// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.dto.model.recharge.RechargeGroupImpl;
import java.io.Serializable;

public class RechargeGroupEditorSessionSSO implements Serializable {

   private RechargeGroupImpl mEditorData;


   public RechargeGroupEditorSessionSSO() {}

   public RechargeGroupEditorSessionSSO(RechargeGroupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(RechargeGroupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public RechargeGroupImpl getEditorData() {
      return this.mEditorData;
   }
}
