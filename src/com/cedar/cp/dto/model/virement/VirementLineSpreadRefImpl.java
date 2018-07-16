// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementLineSpreadRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementLineSpreadCK;
import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
import java.io.Serializable;

public class VirementLineSpreadRefImpl extends EntityRefImpl implements VirementLineSpreadRef, Serializable {

   public VirementLineSpreadRefImpl(VirementLineSpreadCK key, String narrative) {
      super(key, narrative);
   }

   public VirementLineSpreadRefImpl(VirementLineSpreadPK key, String narrative) {
      super(key, narrative);
   }

   public VirementLineSpreadPK getVirementLineSpreadPK() {
      return this.mKey instanceof VirementLineSpreadCK?((VirementLineSpreadCK)this.mKey).getVirementLineSpreadPK():(VirementLineSpreadPK)this.mKey;
   }
}
