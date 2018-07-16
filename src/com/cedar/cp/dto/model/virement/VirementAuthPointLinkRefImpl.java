// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementAuthPointLinkRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkCK;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkPK;
import java.io.Serializable;

public class VirementAuthPointLinkRefImpl extends EntityRefImpl implements VirementAuthPointLinkRef, Serializable {

   public VirementAuthPointLinkRefImpl(VirementAuthPointLinkCK key, String narrative) {
      super(key, narrative);
   }

   public VirementAuthPointLinkRefImpl(VirementAuthPointLinkPK key, String narrative) {
      super(key, narrative);
   }

   public VirementAuthPointLinkPK getVirementAuthPointLinkPK() {
      return this.mKey instanceof VirementAuthPointLinkCK?((VirementAuthPointLinkCK)this.mKey).getVirementAuthPointLinkPK():(VirementAuthPointLinkPK)this.mKey;
   }
}
