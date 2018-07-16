// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormulaPackageRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
import java.io.Serializable;

public class CubeFormulaPackageRefImpl extends EntityRefImpl implements CubeFormulaPackageRef, Serializable {

   public CubeFormulaPackageRefImpl(CubeFormulaPackageCK key, String narrative) {
      super(key, narrative);
   }

   public CubeFormulaPackageRefImpl(CubeFormulaPackagePK key, String narrative) {
      super(key, narrative);
   }

   public CubeFormulaPackagePK getCubeFormulaPackagePK() {
      return this.mKey instanceof CubeFormulaPackageCK?((CubeFormulaPackageCK)this.mKey).getCubeFormulaPackagePK():(CubeFormulaPackagePK)this.mKey;
   }
}
