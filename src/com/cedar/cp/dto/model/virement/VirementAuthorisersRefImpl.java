// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementAuthorisersRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthorisersCK;
import com.cedar.cp.dto.model.virement.VirementAuthorisersPK;
import java.io.Serializable;

public class VirementAuthorisersRefImpl extends EntityRefImpl implements VirementAuthorisersRef, Serializable {

   public VirementAuthorisersRefImpl(VirementAuthorisersCK key, String narrative) {
      super(key, narrative);
   }

   public VirementAuthorisersRefImpl(VirementAuthorisersPK key, String narrative) {
      super(key, narrative);
   }

   public VirementAuthorisersPK getVirementAuthorisersPK() {
      return this.mKey instanceof VirementAuthorisersCK?((VirementAuthorisersCK)this.mKey).getVirementAuthorisersPK():(VirementAuthorisersPK)this.mKey;
   }
}
