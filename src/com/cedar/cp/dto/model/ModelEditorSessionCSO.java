// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.ModelImpl;
import java.io.Serializable;

public class ModelEditorSessionCSO implements Serializable {

   private int mUserId;
   private ModelImpl mEditorData;


   public ModelEditorSessionCSO(int userId, ModelImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ModelImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
