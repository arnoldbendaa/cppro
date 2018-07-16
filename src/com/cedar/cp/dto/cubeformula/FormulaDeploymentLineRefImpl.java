// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentLineRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
import java.io.Serializable;

public class FormulaDeploymentLineRefImpl extends EntityRefImpl implements FormulaDeploymentLineRef, Serializable {

   public FormulaDeploymentLineRefImpl(FormulaDeploymentLineCK key, String narrative) {
      super(key, narrative);
   }

   public FormulaDeploymentLineRefImpl(FormulaDeploymentLinePK key, String narrative) {
      super(key, narrative);
   }

   public FormulaDeploymentLinePK getFormulaDeploymentLinePK() {
      return this.mKey instanceof FormulaDeploymentLineCK?((FormulaDeploymentLineCK)this.mKey).getFormulaDeploymentLinePK():(FormulaDeploymentLinePK)this.mKey;
   }
}
