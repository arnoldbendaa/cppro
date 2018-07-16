// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public class CcDeploymentValidationException extends ValidationException {

   private EntityList mOverlapDetails;


   public CcDeploymentValidationException(EntityList overlapDetails) {
      super("Overlapping deployment detected.");
      this.mOverlapDetails = overlapDetails;
   }

   public EntityList getOverlapDetails() {
      return this.mOverlapDetails;
   }
}
