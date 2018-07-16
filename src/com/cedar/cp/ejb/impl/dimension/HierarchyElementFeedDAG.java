// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.HierarchyElementFeedImpl;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedRefImpl;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.util.StringUtils;
import java.util.Collections;
import java.util.List;

public class HierarchyElementFeedDAG extends AbstractDAG implements HierarchyNodeDAG {

   private HierarchyElementDAG mHierarchyElement;
   private DimensionElementDAG mDimensionElement;
   private int mIndex;
   private HierarchyElementDAG mAugHierarchyElement;
   private int mAugIndex;
   private int mCalElemType;
   private transient HierarchyElementFeedPK mPK;
   private transient HierarchyElementFeedRefImpl mEntityRef;


   public HierarchyElementFeedDAG(DAGContext context, HierarchyElementDAG hierarchyElement, DimensionElementDAG dimensionElement, int index, HierarchyElementDAG augHierarchyElement, int augIndex, int calElemType) {
      super(context, false);
      this.mHierarchyElement = hierarchyElement;
      this.mDimensionElement = dimensionElement;
      this.mIndex = index;
      this.mAugHierarchyElement = augHierarchyElement;
      this.mAugIndex = augIndex;
      this.mCalElemType = calElemType;
   }

   public int getId() {
      return this.mDimensionElement.getId();
   }

   public String getVisId() {
      return this.mDimensionElement.getVisId();
   }

   public String getDescription() {
      return this.mDimensionElement.getDescription();
   }

   public List getChildren() {
      return Collections.EMPTY_LIST;
   }

   public HierarchyElementDAG getHierarchyElement() {
      return this.mHierarchyElement;
   }

   public void setHierarchyElement(HierarchyElementDAG hierarchyElement) {
      this.mHierarchyElement = hierarchyElement;
   }

   public HierarchyElementDAG getParent() {
      return this.mHierarchyElement;
   }

   public void setParent(HierarchyElementDAG hierarchyElement) {
      this.mHierarchyElement = hierarchyElement;
   }

   public HierarchyNodeDAG find(String visId) {
      return this.mDimensionElement.getVisId().equals(visId)?this:null;
   }

   public HierarchyNodeDAG getChildAtIndex(int index) {
      return null;
   }

   public HierarchyNodeDAG find(int id) {
      return this.mDimensionElement.getId() == id?this:null;
   }

   public void add(int index, HierarchyNodeDAG node) {
      throw new IllegalStateException("HierarchyElementFeedDAG can\'t have children");
   }

   public boolean isLeaf() {
      return true;
   }

   public int indexOf(HierarchyNodeDAG node) {
      return -1;
   }

   public int augIndexOf(HierarchyNodeDAG node) {
      return -1;
   }

   public boolean isFeeder() {
      return true;
   }

   public void remove(HierarchyNodeDAG node) {}

   public DimensionElementDAG getDimensionElement() {
      return this.mDimensionElement;
   }

   public void setDimensionElement(DimensionElementDAG dimensionElement) {
      this.mDimensionElement = dimensionElement;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public HierarchyElementDAG getAugHierarchyElement() {
      return this.mAugHierarchyElement;
   }

   public int getAugIndex() {
      return this.mAugIndex;
   }

   HierarchyElementFeedEVO createEVO() {
      HierarchyElementFeedEVO hefEVO = new HierarchyElementFeedEVO();
      hefEVO.setDimensionElementId(this.mDimensionElement.getId());
      hefEVO.setHierarchyElementId(this.mHierarchyElement.getId());
      this.updateEVO(hefEVO);
      return hefEVO;
   }

   void updateEVO(HierarchyElementFeedEVO hefEVO) {
      hefEVO.setChildIndex(this.mHierarchyElement.indexOf(this));
      hefEVO.setAugHierarchyElementId(this.mAugHierarchyElement != null?new Integer(this.mAugHierarchyElement.getId()):null);
      hefEVO.setAugChildIndex(this.mAugHierarchyElement != null?new Integer(this.mAugHierarchyElement.augIndexOf(this)):null);
      hefEVO.setCalElemType(this.mCalElemType != -1?new Integer(this.mCalElemType):null);
   }

   HierarchyElementFeedImpl createLightweightDAG(HierarchyElementImpl parent) {
      HierarchyElementFeedImpl hefImpl = new HierarchyElementFeedImpl(this.getPK());
      hefImpl.setChildIndex(this.getActiveIndex());
      hefImpl.setCreditDebit(this.mDimensionElement.getCreditDebit());
      hefImpl.setAugCreditDebit(this.mDimensionElement.getAugCreditDebit());
      hefImpl.setVisId(this.mDimensionElement.getVisId());
      hefImpl.setDescription(this.mDimensionElement.getDescription());
      hefImpl.setDisabled(this.mDimensionElement.isDisabled());
      hefImpl.setParent(parent);
      return hefImpl;
   }

   public HierarchyElementFeedPK getPK() {
      if(this.mPK == null) {
         this.mPK = new HierarchyElementFeedPK(this.mHierarchyElement.getId(), this.mDimensionElement.getId());
      }

      return this.mPK;
   }

   public boolean isNodeAncestor(HierarchyNodeDAG node) {
      if(node == null) {
         return false;
      } else {
         for(Object child = this; child != null; child = ((HierarchyNodeDAG)child).getParent()) {
            if(child.equals(node)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isTwig() {
      return false;
   }

   public void removeFromEVO(HierarchyEVO hEVO) {
      HierarchyElementEVO heEVO = hEVO.getHierarchyElementsItem(this.mHierarchyElement.getPK());
      if(heEVO != null) {
         HierarchyElementFeedEVO hefEVO = heEVO.getFeederElementsItem(this.getPK());
         if(hefEVO != null) {
            heEVO.deleteFeederElementsItem(this.getPK());
         }
      }

   }

   public EntityRef getEntityRef() {
      if(this.mEntityRef == null) {
         this.mEntityRef = new HierarchyElementFeedRefImpl(this.getPK(), this.mDimensionElement.getVisId());
      }

      return this.mEntityRef;
   }

   public HierarchyDAG getHierarchy() {
      return this.mHierarchyElement.getHierarchy();
   }

   public Object getPrimaryKey() {
      return this.getPK();
   }

   public boolean isAugmentElement() {
      return false;
   }

   public void validateAugmentModeMove(HierarchyElementDAG newParent, int newIndex) throws ValidationException {
      HierarchyElementDAG currentRealParent = this.findRealParent();
      if(currentRealParent == null) {
         throw new ValidationException("Unable to find non augment element parent for:" + this.getVisId());
      } else {
         HierarchyElementDAG newRealParent = newParent.findRealNodeInParentage(true);
         if(newRealParent == null) {
            throw new ValidationException("Unable to find non augment element parent for:" + newParent.getVisId());
         } else if(!currentRealParent.equals(newRealParent)) {
            throw new ValidationException("Illegal move of element in hierarchy augmentation mode. Would change real hierarchy.");
         }
      }
   }

   public HierarchyElementDAG findRealParent() {
      HierarchyElementDAG parent = this.mHierarchyElement;
      return parent == null?null:(parent.isAugmentElement()?parent.findRealNodeInParentage(true):parent);
   }

   public int getActiveIndex() {
      return this.mAugIndex != -1?this.mAugIndex:this.mIndex;
   }

   public void addAug(int index, HierarchyNodeDAG child) throws ValidationException {
      throw new ValidationException("Feed elements cannot contain child elements");
   }

   public HierarchyNodeDAG getAugChildAtIndex(int index) {
      return null;
   }

   public List getAugChildren() {
      return null;
   }

   public HierarchyElementDAG getAugParent() {
      return this.mAugHierarchyElement;
   }

   public void setAugParent(HierarchyElementDAG parent) {
      this.mAugHierarchyElement = parent;
   }

   public HierarchyElementDAG getActiveParent() {
      return this.mAugHierarchyElement != null?this.mAugHierarchyElement:this.mHierarchyElement;
   }

   public void add(HierarchyNodeDAG child) throws ValidationException {
      this.add(this.getActiveIndex(), child);
   }

   public int getCalElemType() {
      return this.mCalElemType;
   }

   public void setCalElemType(int calElemType) {
      if(this.mCalElemType != calElemType) {
         this.mCalElemType = calElemType;
         this.setDirty();
      }

   }

   public int getNumChildren() {
      return 0;
   }

   public void displayHierarchy(StringBuilder sb, int level, boolean includeLeaves) {
      if(includeLeaves) {
         sb.append(StringUtils.fill("\t", level)).append(" Id:[").append(this.mDimensionElement.getId()).append("]").append(" VisId:[").append(this.mDimensionElement.getVisId()).append("]\n");
      }

   }

   public int countElements() {
      return 1;
   }
}
