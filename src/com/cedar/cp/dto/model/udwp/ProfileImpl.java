// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import java.io.Serializable;

public class ProfileImpl implements Profile, Serializable {

   private WeightingProfileRefImpl mWeightingProfileRef;
   private int mType;
   private int[] mWeightings;


   public ProfileImpl(WeightingProfileRefImpl weightingProfileRef, int type, int[] weightings) {
      this.mWeightingProfileRef = weightingProfileRef;
      this.mType = type;
      this.mWeightings = weightings;
   }

   public WeightingProfileRef getRef() {
      return this.mWeightingProfileRef;
   }

   public int[] getWeightings() {
      return this.mWeightings;
   }

   public int getType() {
      return this.mType;
   }
}
