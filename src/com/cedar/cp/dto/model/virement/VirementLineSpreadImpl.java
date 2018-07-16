// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.virement.VirementLineSpread;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
import com.cedar.cp.util.GeneralUtils;

public class VirementLineSpreadImpl implements VirementLineSpread, Cloneable {

   private VirementLineSpreadPK mKey;
   private StructureElementRefImpl mStructureElementRef;
   private int mIndex;
   private boolean mHeld;
   private int mWeighting;
   private long mTransferValue;


   public VirementLineSpreadImpl(VirementLineSpreadPK key, StructureElementRefImpl ref, int idx, boolean held, int weighting, long transferValue) {
      this.mKey = key;
      this.mStructureElementRef = ref;
      this.mIndex = idx;
      this.mHeld = held;
      this.mWeighting = weighting;
      this.mTransferValue = transferValue;
   }

   public String getKeyAsText() {
      return this.mKey != null?this.mKey.toTokens():null;
   }

   public Object getKey() {
      return this.mKey;
   }

   public VirementLineSpreadPK getVirementLineSpreadPK() {
      return this.mKey;
   }

   public void setKey(VirementLineSpreadPK key) {
      this.mKey = key;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int lineIdx) {
      this.mIndex = lineIdx;
   }

   public boolean isHeld() {
      return this.mHeld;
   }

   public void setHeld(boolean held) {
      this.mHeld = held;
   }

   public int getWeighting() {
      return this.mWeighting;
   }

   public void setWeighting(int weighting) {
      this.mWeighting = weighting;
   }

   public StructureElementRef getStructureElementRef() {
      return this.mStructureElementRef;
   }

   public void setStructureElementRef(StructureElementRefImpl structureElementRef) {
      this.mStructureElementRef = structureElementRef;
   }

   public double getTransferValue() {
      return GeneralUtils.convertDBToFinancialValue(this.mTransferValue);
   }

   public long getTransferValueAsLong() {
      return this.mTransferValue;
   }

   public void setTransferValue(double transferValue) {
      this.mTransferValue = GeneralUtils.convertFinancialValueToDB(transferValue);
   }

   public String getAddressAsCSVString(VirementLineImpl line) {
      StringBuffer sb = new StringBuffer();
      sb.append(line.getAddressAsCSVString(true));
      sb.append(',');
      sb.append(String.valueOf(this.mStructureElementRef.getStructureElementPK().getStructureElementId()));
      return sb.toString();
   }

   public String getAddressAsCSVStringWithDataType(VirementLineImpl line, String dataType) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getAddressAsCSVString(line));
      sb.append(",");
      sb.append(dataType);
      return sb.toString();
   }

   protected Object clone() throws CloneNotSupportedException {
      VirementLineSpreadImpl clone = (VirementLineSpreadImpl)super.clone();
      clone.mKey = this.mKey;
      clone.mStructureElementRef = this.mStructureElementRef;
      clone.mIndex = this.mIndex;
      clone.mHeld = this.mHeld;
      clone.mWeighting = this.mWeighting;
      clone.mTransferValue = this.mTransferValue;
      return clone;
   }
}
