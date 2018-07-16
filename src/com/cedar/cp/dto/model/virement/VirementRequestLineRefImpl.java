// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementRequestLineRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementRequestLineCK;
import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
import java.io.Serializable;

public class VirementRequestLineRefImpl extends EntityRefImpl implements VirementRequestLineRef, Serializable {

   public VirementRequestLineRefImpl(VirementRequestLineCK key, String narrative) {
      super(key, narrative);
   }

   public VirementRequestLineRefImpl(VirementRequestLinePK key, String narrative) {
      super(key, narrative);
   }

   public VirementRequestLinePK getVirementRequestLinePK() {
      return this.mKey instanceof VirementRequestLineCK?((VirementRequestLineCK)this.mKey).getVirementRequestLinePK():(VirementRequestLinePK)this.mKey;
   }
}
