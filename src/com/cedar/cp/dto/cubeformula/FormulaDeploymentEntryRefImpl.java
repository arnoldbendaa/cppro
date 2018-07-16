// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentEntryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK;
import java.io.Serializable;

public class FormulaDeploymentEntryRefImpl extends EntityRefImpl implements FormulaDeploymentEntryRef, Serializable {

   public FormulaDeploymentEntryRefImpl(FormulaDeploymentEntryCK key, String narrative) {
      super(key, narrative);
   }

   public FormulaDeploymentEntryRefImpl(FormulaDeploymentEntryPK key, String narrative) {
      super(key, narrative);
   }

   public FormulaDeploymentEntryPK getFormulaDeploymentEntryPK() {
      return this.mKey instanceof FormulaDeploymentEntryCK?((FormulaDeploymentEntryCK)this.mKey).getFormulaDeploymentEntryPK():(FormulaDeploymentEntryPK)this.mKey;
   }
}
