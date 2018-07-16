// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import java.io.Serializable;

public class MappedModelEditorSessionSSO implements Serializable {

   private MappedModelImpl mEditorData;


   public MappedModelEditorSessionSSO() {}

   public MappedModelEditorSessionSSO(MappedModelImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(MappedModelImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public MappedModelImpl getEditorData() {
      return this.mEditorData;
   }
}
