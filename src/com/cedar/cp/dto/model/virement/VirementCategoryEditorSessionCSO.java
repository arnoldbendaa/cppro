// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.dto.model.virement.VirementCategoryImpl;
import java.io.Serializable;

public class VirementCategoryEditorSessionCSO implements Serializable {

   private int mUserId;
   private VirementCategoryImpl mEditorData;


   public VirementCategoryEditorSessionCSO(int userId, VirementCategoryImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public VirementCategoryImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
