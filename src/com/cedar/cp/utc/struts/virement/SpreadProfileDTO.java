// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.model.udwp.WeightingProfileRef;

public class SpreadProfileDTO {

   private WeightingProfileRef mWeightingProfileRef;
   private boolean mGlobal;
   private int mProfileType;
   private int mStartLevel;
   private int mLeafLevel;
   private int mDynamicOffset;
   private String mDynamicDataType;


   public SpreadProfileDTO(WeightingProfileRef weightingProfileRef, boolean global, int profileType, int startLevel, int leafLevel, int dynamicOffset, String dynamicDataType) {
      this.mWeightingProfileRef = weightingProfileRef;
      this.mGlobal = global;
      this.mProfileType = profileType;
      this.mStartLevel = startLevel;
      this.mLeafLevel = leafLevel;
      this.mDynamicOffset = dynamicOffset;
      this.mDynamicDataType = dynamicDataType;
   }

   public WeightingProfileRef getWeightingProfileRef() {
      return this.mWeightingProfileRef;
   }

   public boolean isGlobal() {
      return this.mGlobal;
   }

   public int getProfileType() {
      return this.mProfileType;
   }

   public int getStartLevel() {
      return this.mStartLevel;
   }

   public int getLeafLevel() {
      return this.mLeafLevel;
   }

   public int getDynamicOffset() {
      return this.mDynamicOffset;
   }

   public String getDynamicDataType() {
      return this.mDynamicDataType;
   }
}
