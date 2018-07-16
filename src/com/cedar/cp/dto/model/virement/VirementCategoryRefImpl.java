// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementCategoryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import java.io.Serializable;

public class VirementCategoryRefImpl extends EntityRefImpl implements VirementCategoryRef, Serializable {

   public VirementCategoryRefImpl(VirementCategoryCK key, String narrative) {
      super(key, narrative);
   }

   public VirementCategoryRefImpl(VirementCategoryPK key, String narrative) {
      super(key, narrative);
   }

   public VirementCategoryPK getVirementCategoryPK() {
      return this.mKey instanceof VirementCategoryCK?((VirementCategoryCK)this.mKey).getVirementCategoryPK():(VirementCategoryPK)this.mKey;
   }
}
