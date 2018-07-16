// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import java.io.Serializable;

public class VirementRequestEditorSessionCSO implements Serializable {

   private int mUserId;
   private VirementRequestImpl mEditorData;


   public VirementRequestEditorSessionCSO(int userId, VirementRequestImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public VirementRequestImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
