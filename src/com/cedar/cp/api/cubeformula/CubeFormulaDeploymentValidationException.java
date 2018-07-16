// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cubeformula;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public class CubeFormulaDeploymentValidationException extends ValidationException {

   private EntityList mOverlapDetails;


   public CubeFormulaDeploymentValidationException(EntityList overlapDetails) {
      super("Overlapping deployment detected.");
      this.mOverlapDetails = overlapDetails;
   }

   public EntityList getOverlapDetails() {
      return this.mOverlapDetails;
   }
}
