// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import java.io.Serializable;

public class CubeFormulaEditorSessionSSO implements Serializable {

   private CubeFormulaImpl mEditorData;


   public CubeFormulaEditorSessionSSO() {}

   public CubeFormulaEditorSessionSSO(CubeFormulaImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(CubeFormulaImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public CubeFormulaImpl getEditorData() {
      return this.mEditorData;
   }
}
