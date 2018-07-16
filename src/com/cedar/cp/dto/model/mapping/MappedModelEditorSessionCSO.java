// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import java.io.Serializable;

public class MappedModelEditorSessionCSO implements Serializable {

   private int mUserId;
   private MappedModelImpl mEditorData;


   public MappedModelEditorSessionCSO(int userId, MappedModelImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public MappedModelImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
