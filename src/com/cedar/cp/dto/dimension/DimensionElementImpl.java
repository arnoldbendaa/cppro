// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
import com.cedar.cp.dto.dimension.DimensionImpl;
import java.io.Serializable;

public class DimensionElementImpl implements DimensionElement, Serializable, Comparable {

   protected static final byte sNotPlannable = 1;
   protected static final byte sCeditDebit = 2;
   protected static final byte sNullElement = 4;
   protected static final byte sDisabled = 8;
   private Object mKey;
   private String mVisId;
   private String mDescription;
   private int mCreditDebit = 2;
   private int mAugCreditDebit = 0;
   private byte mFlags;
   private DimensionImpl mDimension;
   private transient DimensionElementRef mEntityRef;


   public boolean isLeaf() {
      return true;
   }

   public DimensionElementRef getEntityRef() {
      if(this.mEntityRef == null) {
         if(this.mKey instanceof DimensionElementPK) {
            this.mEntityRef = new DimensionElementRefImpl((DimensionElementPK)this.mKey, this.mVisId, this.getActiveCreditDebit());
         } else {
            this.mEntityRef = new DimensionElementRefImpl((DimensionElementCK)this.mKey, this.mVisId, this.getActiveCreditDebit());
         }
      }

      return this.mEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Object getKey() {
      return this.mKey;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public Dimension getDimension() {
      return this.mDimension;
   }

   public void setDimension(DimensionImpl d) {
      this.mDimension = d;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public void setCreditDebit(int creditDebit) {
      this.mCreditDebit = creditDebit;
   }

   public boolean isDisabled() {
      return (this.mFlags & 8) == 8;
   }

   public void setDisabled(boolean disabled) {
      if(disabled) {
         this.mFlags = (byte)(this.mFlags | 8);
      } else {
         this.mFlags &= -9;
      }

   }

   public String toString() {
      return this.mVisId;
   }

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public void setAugCreditDebit(int augCreditDebit) {
      this.mAugCreditDebit = augCreditDebit;
   }

   public int getActiveCreditDebit() {
      return this.mAugCreditDebit != 0?this.mAugCreditDebit:this.getCreditDebit();
   }

   public boolean isNotPlannable() {
      return (this.mFlags & 1) == 1;
   }

   public void setNotPlannable(boolean notPlannable) {
      if(notPlannable) {
         this.mFlags = (byte)(this.mFlags | 1);
      } else {
         this.mFlags &= -2;
      }

   }

   public void setNullElement(boolean isNullElement) {
      if(isNullElement) {
         this.mFlags = (byte)(this.mFlags | 4);
      } else {
         this.mFlags &= -5;
      }

   }

   public boolean isNullElement() {
      return (this.mFlags & 4) == 4;
   }

	@Override
	public int compareTo(Object o) {
		return toString().compareTo(o.toString());
	}
}
