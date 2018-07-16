// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.CellCalcImpl;
import java.io.Serializable;

public class CellCalcEditorSessionSSO implements Serializable {

   private CellCalcImpl mEditorData;


   public CellCalcEditorSessionSSO() {}

   public CellCalcEditorSessionSSO(CellCalcImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(CellCalcImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public CellCalcImpl getEditorData() {
      return this.mEditorData;
   }
}
