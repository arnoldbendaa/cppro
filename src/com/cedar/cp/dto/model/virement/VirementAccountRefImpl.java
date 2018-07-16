// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementAccountRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementAccountCK;
import com.cedar.cp.dto.model.virement.VirementAccountPK;
import java.io.Serializable;

public class VirementAccountRefImpl extends EntityRefImpl implements VirementAccountRef, Serializable {

   public VirementAccountRefImpl(VirementAccountCK key, String narrative) {
      super(key, narrative);
   }

   public VirementAccountRefImpl(VirementAccountPK key, String narrative) {
      super(key, narrative);
   }

   public VirementAccountPK getVirementAccountPK() {
      return this.mKey instanceof VirementAccountCK?((VirementAccountCK)this.mKey).getVirementAccountPK():(VirementAccountPK)this.mKey;
   }
}
