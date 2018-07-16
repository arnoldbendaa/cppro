// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import java.io.Serializable;

public class WeightingProfileRefImpl extends EntityRefImpl implements WeightingProfileRef, Serializable {

   public WeightingProfileRefImpl(WeightingProfileCK key, String narrative) {
      super(key, narrative);
   }

   public WeightingProfileRefImpl(WeightingProfilePK key, String narrative) {
      super(key, narrative);
   }

   public WeightingProfilePK getWeightingProfilePK() {
      return this.mKey instanceof WeightingProfileCK?((WeightingProfileCK)this.mKey).getWeightingProfilePK():(WeightingProfilePK)this.mKey;
   }

   public boolean isCustom() {
      return this.mKey instanceof WeightingProfilePK && ((WeightingProfilePK)this.mKey).getProfileId() == 0;
   }
}
