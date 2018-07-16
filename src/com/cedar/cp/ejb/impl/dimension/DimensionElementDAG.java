// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementImpl;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementEVO;
import java.util.List;

public class DimensionElementDAG extends AbstractDAG {

   protected static final byte sNotPlannable = 1;
   protected static final byte sCeditDebit = 2;
   protected static final byte sNullElement = 4;
   protected static final byte sDisabled = 8;
   private String mVisId;
   private String mDescription;
   private int mDimensionElementId;
   private int mAugCreditDebit;
   private transient DimensionElementPK mPK;
   private DimensionDAG mDimension;
   private int mCreditDebit;
   private byte mFlags;


   public DimensionElementDAG(DimensionElementDAG element) {
      super((DAGContext)null, false);
      this.mVisId = element.mVisId;
      this.mDescription = element.mDescription;
      this.mDimensionElementId = element.mDimensionElementId;
      this.mDimension = null;
      this.mCreditDebit = element.mCreditDebit;
      this.mFlags = element.mFlags;
      this.mAugCreditDebit = element.mAugCreditDebit;
   }

   public DimensionElementDAG(DAGContext context, DimensionDAG dimension, int elementId, String visId, String description, int creditDebit, int augCerditDebit, boolean notPlannable, boolean nullElement) {
      super(context, true);
      this.mVisId = visId;
      this.mDescription = description;
      this.mDimensionElementId = elementId;
      this.mDimension = dimension;
      this.mCreditDebit = creditDebit;
      this.mFlags = (byte)(this.mFlags | (notPlannable?1:0));
      this.mFlags = (byte)(this.mFlags | (nullElement?4:0));
      this.setNullElement(nullElement);
      this.mAugCreditDebit = augCerditDebit;
      context.getCache().put(new DimensionElementPK(this.mDimensionElementId), this);
   }

   public DimensionElementDAG(DAGContext context, DimensionElementEVO elementEVO) {
      super(context, false);
      this.mVisId = elementEVO.getVisId();
      this.mDescription = elementEVO.getDescription();
      this.mDimensionElementId = elementEVO.getDimensionElementId();
      this.mCreditDebit = elementEVO.getCreditDebit();
      this.setDisabled(elementEVO.getDisabled());
      this.setNotPlannable(elementEVO.getNotPlannable());
      this.mAugCreditDebit = elementEVO.getAugCreditDebit() == null?0:elementEVO.getAugCreditDebit().intValue();
      if(context != null) {
         context.getCache().put(new DimensionElementPK(this.mDimensionElementId), this);
      }

   }

   public void createEVO(List elements) {
      elements.add(this.createEVO());
   }

   public DimensionElementEVO createEVO() {
      return new DimensionElementEVO(this.mDimensionElementId, this.mDimension.getDimensionId(), this.mVisId, this.mDescription, this.mCreditDebit, this.isDisabled(), this.isNotPlannable(), this.mAugCreditDebit == 0?null:new Integer(this.mAugCreditDebit));
   }

   public void updateEVO(DimensionEVO devo) {
      if(this.isDirty()) {
         if(this.mDimensionElementId < 1) {
            devo.addElementsItem(this.createEVO());
         } else {
            DimensionElementEVO deevo = devo.getElementsItem(new DimensionElementPK(this.mDimensionElementId));
            this.updateEVO(deevo);
            devo.changeElementsItem(deevo);
         }
      }

   }

   public void updateEVO(DimensionElementEVO deevo) {
      deevo.setVisId(this.getVisId());
      deevo.setDescription(this.getDescription());
      deevo.setDisabled(this.isDisabled());
      deevo.setCreditDebit(this.getCreditDebit());
      deevo.setNotPlannable(this.isNotPlannable());
      deevo.setAugCreditDebit(this.mAugCreditDebit == 0?null:new Integer(this.mAugCreditDebit));
   }

   public void removeFromEVO(DimensionEVO devo) {
      if(this.mDimensionElementId > 0) {
         devo.deleteElementsItem(new DimensionElementPK(this.mDimensionElementId));
      }

   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      if(this.mVisId != null && visId == null || this.mVisId == null && visId != null || this.mVisId != null && visId != null && !this.mVisId.equals(visId)) {
         this.mVisId = visId;
         this.setDirty();
      }

   }

   public void setDescription(String description) {
      if(this.mDescription != null && description == null || this.mDescription == null && description != null || this.mDescription != null && description != null && !this.mDescription.equals(description)) {
         this.mDescription = description;
         this.setDirty();
      }

   }

   public boolean isDisabled() {
      return (this.mFlags & 8) == 8;
   }

   public void setDisabled(boolean disabled) {
      if(this.isDisabled() != disabled) {
         if(disabled) {
            this.mFlags = (byte)(this.mFlags | 8);
         } else {
            this.mFlags &= -9;
         }

         this.setDirty();
      }

   }

   public DimensionDAG getDimension() {
      return this.mDimension;
   }

   public void setDimension(DimensionDAG d) {
      this.mDimension = d;
   }

   public DimensionElementImpl createLightweightElement(DimensionImpl di) {
      DimensionElementImpl dei = new DimensionElementImpl();
      dei.setVisId(this.mVisId);
      dei.setDescription(this.mDescription);
      dei.setKey(new DimensionElementCK(new DimensionPK(this.getDimension().getDimensionId()), new DimensionElementPK(this.mDimensionElementId)));
      dei.setDimension(di);
      dei.setCreditDebit(this.mCreditDebit);
      dei.setAugCreditDebit(this.mAugCreditDebit);
      dei.setNotPlannable(this.isNotPlannable());
      dei.setDisabled(this.isDisabled());
      dei.setNullElement(this.isNullElement());
      return dei;
   }

   public DimensionElementImpl createLightweightDAG(DimensionImpl di) {
      return this.createLightweightElement(di);
   }

   public int getId() {
      return this.mDimensionElementId;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public void setCreditDebit(int creditDebit) {
      if(this.mCreditDebit != creditDebit) {
         this.mCreditDebit = creditDebit;
         this.setDirty();
      }

   }

   public void setAugCreditDebit(int creditDebit) {
      if(this.mAugCreditDebit != creditDebit) {
         this.mAugCreditDebit = creditDebit;
         this.setDirty();
      }

   }

   public boolean isCredit() {
      return this.mCreditDebit == 1;
   }

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public DimensionElementRefImpl getEntityRef() {
      DimensionElementCK key = new DimensionElementCK(new DimensionPK(this.mDimension.getDimensionId()), new DimensionElementPK(this.mDimensionElementId));
      return new DimensionElementRefImpl(key, this.mVisId, this.mCreditDebit);
   }

   public boolean equals(DimensionElementDAG anotherNode) {
      return anotherNode == null?false:this.mDimensionElementId == anotherNode.mDimensionElementId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public DimensionElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DimensionElementPK(this.mDimensionElementId);
      }

      return this.mPK;
   }

   public DimensionElementCK getCK() {
      return new DimensionElementCK(this.mDimension.getPK(), this.getPK());
   }

   public boolean isNotPlannable() {
      return (this.mFlags & 1) == 1;
   }

   public void setNotPlannable(boolean notPlannable) {
      if(this.isNotPlannable() != notPlannable) {
         if(notPlannable) {
            this.mFlags = (byte)(this.mFlags | 1);
         } else {
            this.mFlags &= -2;
         }

         this.setDirty();
      }

   }

   public void setNullElement(boolean nullElement) {
      if(this.isNullElement() != nullElement) {
         if(nullElement) {
            this.mFlags = (byte)(this.mFlags | 4);
         } else {
            this.mFlags &= -5;
         }

         this.setDirty();
      }

   }

   public boolean isNullElement() {
      return (this.mFlags & 4) == 4;
   }
}
