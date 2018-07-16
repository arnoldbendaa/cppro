// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.dto.model.virement.VirementCategoryImpl;
import java.io.Serializable;

public class VirementCategoryEditorSessionSSO implements Serializable {

   private VirementCategoryImpl mEditorData;


   public VirementCategoryEditorSessionSSO() {}

   public VirementCategoryEditorSessionSSO(VirementCategoryImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(VirementCategoryImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public VirementCategoryImpl getEditorData() {
      return this.mEditorData;
   }
}
