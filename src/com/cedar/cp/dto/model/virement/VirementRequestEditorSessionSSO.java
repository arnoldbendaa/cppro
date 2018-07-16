// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import java.io.Serializable;

public class VirementRequestEditorSessionSSO implements Serializable {

   private VirementRequestImpl mEditorData;


   public VirementRequestEditorSessionSSO() {}

   public VirementRequestEditorSessionSSO(VirementRequestImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(VirementRequestImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public VirementRequestImpl getEditorData() {
      return this.mEditorData;
   }
}
