// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementLocationRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementLocationCK;
import com.cedar.cp.dto.model.virement.VirementLocationPK;
import java.io.Serializable;

public class VirementLocationRefImpl extends EntityRefImpl implements VirementLocationRef, Serializable {

   public VirementLocationRefImpl(VirementLocationCK key, String narrative) {
      super(key, narrative);
   }

   public VirementLocationRefImpl(VirementLocationPK key, String narrative) {
      super(key, narrative);
   }

   public VirementLocationPK getVirementLocationPK() {
      return this.mKey instanceof VirementLocationCK?((VirementLocationCK)this.mKey).getVirementLocationPK():(VirementLocationPK)this.mKey;
   }
}
