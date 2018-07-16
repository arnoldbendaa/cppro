package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2Impl;
import java.io.Serializable;

public class GlobalMappedModel2EditorSessionCSO implements Serializable {

   private int mUserId;
   private GlobalMappedModel2Impl mEditorData;


   public GlobalMappedModel2EditorSessionCSO(int userId, GlobalMappedModel2Impl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public GlobalMappedModel2Impl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
