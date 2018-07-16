// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import java.io.Serializable;

public class VirementLineSpreadDTO implements Serializable {

   private String mKey;
   private int mIndex;
   private boolean mHeld;
   private String mStructureElementKey;
   private String mStructureElementVisId;
   private String mWeighting;
   private String mTransferValue;


   public VirementLineSpreadDTO() {}

   public VirementLineSpreadDTO(String key, int index, boolean held, String weighting, String structureElementKey, String structureElementVisId, String temporaryValue) {
      this.mKey = key;
      this.mIndex = index;
      this.mHeld = held;
      this.mWeighting = weighting;
      this.mStructureElementKey = structureElementKey;
      this.mStructureElementVisId = structureElementVisId;
      this.mTransferValue = temporaryValue;
   }

   public String getKey() {
      return this.mKey != null && this.mKey.trim().length() == 0?null:this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int index) {
      this.mIndex = index;
   }

   public boolean isHeld() {
      return this.mHeld;
   }

   public void setHeld(boolean held) {
      this.mHeld = held;
   }

   public int getWeightingAsInt() {
      try {
         return Integer.parseInt(this.getWeighting());
      } catch (NumberFormatException var2) {
         return 0;
      }
   }

   public String getWeighting() {
      return this.mWeighting;
   }

   public void setWeighting(String weighting) {
      this.mWeighting = weighting;
   }

   public String getTransferValue() {
      return this.mTransferValue;
   }

   public void setTransferValue(String transferValue) {
      this.mTransferValue = transferValue;
   }

   public String getStructureElementKey() {
      return this.mStructureElementKey;
   }

   public void setStructureElementKey(String structureElementKey) {
      this.mStructureElementKey = structureElementKey;
   }

   public String getStructureElementVisId() {
      return this.mStructureElementVisId;
   }

   public void setStructureElementVisId(String structureElementVisId) {
      this.mStructureElementVisId = structureElementVisId;
   }

   public void update(VirementLineSpreadDTO src) {
      this.mHeld = src.mHeld;
      this.mWeighting = src.mWeighting;
      this.mTransferValue = src.mTransferValue;
   }
}
