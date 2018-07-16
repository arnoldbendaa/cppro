// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;

public class RuntimeCubeFormulaDeployment extends RuntimeCubeDeployment {

   private String mFormulaText;


   public RuntimeCubeFormulaDeployment(int modelId, int financeCubeId, int ownerId, String formulaText) {
      super(modelId, financeCubeId, ownerId, 1);
      this.mFormulaText = formulaText;
   }

   public String getFormulaText() {
      return this.mFormulaText;
   }
}
