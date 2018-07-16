// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementRequestGroupRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementRequestGroupCK;
import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
import java.io.Serializable;

public class VirementRequestGroupRefImpl extends EntityRefImpl implements VirementRequestGroupRef, Serializable {

   public VirementRequestGroupRefImpl(VirementRequestGroupCK key, String narrative) {
      super(key, narrative);
   }

   public VirementRequestGroupRefImpl(VirementRequestGroupPK key, String narrative) {
      super(key, narrative);
   }

   public VirementRequestGroupPK getVirementRequestGroupPK() {
      return this.mKey instanceof VirementRequestGroupCK?((VirementRequestGroupCK)this.mKey).getVirementRequestGroupPK():(VirementRequestGroupPK)this.mKey;
   }
}
