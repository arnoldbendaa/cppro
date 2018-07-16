// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementAuthPointRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointCK;
import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
import java.io.Serializable;

public class VirementAuthPointRefImpl extends EntityRefImpl implements VirementAuthPointRef, Serializable {

   public VirementAuthPointRefImpl(VirementAuthPointCK key, String narrative) {
      super(key, narrative);
   }

   public VirementAuthPointRefImpl(VirementAuthPointPK key, String narrative) {
      super(key, narrative);
   }

   public VirementAuthPointPK getVirementAuthPointPK() {
      return this.mKey instanceof VirementAuthPointCK?((VirementAuthPointCK)this.mKey).getVirementAuthPointPK():(VirementAuthPointPK)this.mKey;
   }
}
