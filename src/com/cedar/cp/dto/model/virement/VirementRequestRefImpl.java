// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import java.io.Serializable;

public class VirementRequestRefImpl extends EntityRefImpl implements VirementRequestRef, Serializable {

   public VirementRequestRefImpl(VirementRequestCK key, String narrative) {
      super(key, narrative);
   }

   public VirementRequestRefImpl(VirementRequestPK key, String narrative) {
      super(key, narrative);
   }

   public VirementRequestPK getVirementRequestPK() {
      return this.mKey instanceof VirementRequestCK?((VirementRequestCK)this.mKey).getVirementRequestPK():(VirementRequestPK)this.mKey;
   }
}
