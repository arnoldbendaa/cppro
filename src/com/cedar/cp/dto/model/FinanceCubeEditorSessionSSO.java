// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.FinanceCubeImpl;
import java.io.Serializable;

public class FinanceCubeEditorSessionSSO implements Serializable {

   private FinanceCubeImpl mEditorData;


   public FinanceCubeEditorSessionSSO() {}

   public FinanceCubeEditorSessionSSO(FinanceCubeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(FinanceCubeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public FinanceCubeImpl getEditorData() {
      return this.mEditorData;
   }
}
