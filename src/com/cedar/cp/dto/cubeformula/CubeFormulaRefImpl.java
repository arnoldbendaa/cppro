// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import java.io.Serializable;

public class CubeFormulaRefImpl extends EntityRefImpl implements CubeFormulaRef, Serializable {

   public CubeFormulaRefImpl(CubeFormulaCK key, String narrative) {
      super(key, narrative);
   }

   public CubeFormulaRefImpl(CubeFormulaPK key, String narrative) {
      super(key, narrative);
   }

   public CubeFormulaPK getCubeFormulaPK() {
      return this.mKey instanceof CubeFormulaCK?((CubeFormulaCK)this.mKey).getCubeFormulaPK():(CubeFormulaPK)this.mKey;
   }
}
