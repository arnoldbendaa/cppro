// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentDtRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK;
import java.io.Serializable;

public class FormulaDeploymentDtRefImpl extends EntityRefImpl implements FormulaDeploymentDtRef, Serializable {

   public FormulaDeploymentDtRefImpl(FormulaDeploymentDtCK key, String narrative) {
      super(key, narrative);
   }

   public FormulaDeploymentDtRefImpl(FormulaDeploymentDtPK key, String narrative) {
      super(key, narrative);
   }

   public FormulaDeploymentDtPK getFormulaDeploymentDtPK() {
      return this.mKey instanceof FormulaDeploymentDtCK?((FormulaDeploymentDtCK)this.mKey).getFormulaDeploymentDtPK():(FormulaDeploymentDtPK)this.mKey;
   }
}
