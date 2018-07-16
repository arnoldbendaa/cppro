package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2Impl;
import java.io.Serializable;

public class GlobalMappedModel2EditorSessionSSO implements Serializable {

   private GlobalMappedModel2Impl mEditorData;

   
   public GlobalMappedModel2EditorSessionSSO() {}

   public GlobalMappedModel2EditorSessionSSO(GlobalMappedModel2Impl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(GlobalMappedModel2Impl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public GlobalMappedModel2Impl getEditorData() {
      return this.mEditorData;
   }
}
