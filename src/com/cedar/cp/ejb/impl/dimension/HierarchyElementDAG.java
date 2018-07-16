// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyElementRefImpl;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG$1;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG$2;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class HierarchyElementDAG extends AbstractDAG implements HierarchyNodeDAG {

   private static transient Comparator sElementComparator;
   private static transient Comparator sAugElementComparator;
   protected int mHierarchyElementId;
   protected String mVisId;
   protected String mDescription;
   protected HierarchyDAG mHierarchy;
   protected HierarchyElementDAG mParent;
   protected List mElements;
   protected int mIndex;
   protected HierarchyElementDAG mAugParent;
   protected int mAugIndex;
   protected List mAugElements;
   protected int mCreditDebit;
   protected int mAugCreditDebit;
   protected int mCalElemType;
   protected transient HierarchyElementPK mPK;
   protected transient HierarchyElementRefImpl mEntityRef;
   static final long serialVersionUID = 1L;


   public HierarchyElementDAG(HierarchyElementDAG element) {
      super((DAGContext)null, false);
      this.mVisId = element.mVisId;
      this.mDescription = element.mDescription;
      this.mHierarchyElementId = element.mHierarchyElementId;
      this.mParent = null;
      this.mHierarchy = null;
      this.mElements = null;
      this.mCreditDebit = element.mCreditDebit;
      this.mAugIndex = element.mAugIndex;
      this.mAugCreditDebit = element.mAugCreditDebit;
      this.mCalElemType = element.mCalElemType;
   }

   public HierarchyElementDAG(DAGContext context, HierarchyDAG dimension, int elementId, String visId, String description, int creditDebit, int augCreditDebit, int calElemType) {
      super(context, true);
      this.mVisId = visId;
      this.mDescription = description;
      this.mHierarchyElementId = elementId;
      this.mParent = null;
      this.mHierarchy = dimension;
      this.mElements = null;
      this.mCreditDebit = creditDebit;
      this.mAugIndex = -1;
      this.mAugParent = null;
      this.mAugCreditDebit = augCreditDebit;
      this.mCalElemType = calElemType;
   }

   public HierarchyElementDAG(DAGContext context, boolean dirty) {
      super(context, dirty);
   }

   public HierarchyElementDAG(DAGContext context, HierarchyElementEVO elementEVO) {
      super(context, false);
      this.mVisId = elementEVO.getVisId();
      this.mDescription = elementEVO.getDescription();
      this.mHierarchyElementId = elementEVO.getHierarchyElementId();
      this.mCreditDebit = elementEVO.getCreditDebit();
      this.mIndex = elementEVO.getChildIndex();
      this.mAugIndex = elementEVO.getAugChildIndex() != null?elementEVO.getAugChildIndex().intValue():-1;
      this.mAugCreditDebit = elementEVO.getAugCreditDebit() != null?elementEVO.getAugCreditDebit().intValue():0;
      this.mCalElemType = elementEVO.getCalElemType() != null?elementEVO.getCalElemType().intValue():-1;
      context.getCache().put(new HierarchyElementPK(this.mHierarchyElementId), this);
   }

   void createEVO(List elements) {
      elements.add(this.createEVO());
      if(this.getActiveChildren() != null) {
         Iterator i = this.getActiveChildren().iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if(o instanceof HierarchyElementDAG) {
               HierarchyElementDAG child = (HierarchyElementDAG)o;
               child.createEVO(elements);
            }
         }
      }

   }

   protected List createFeedEVOs() {
      ArrayList hefs = null;
      if(this.getActiveChildren() != null) {
         hefs = new ArrayList();
         Iterator i = this.getFeederElements().iterator();

         while(i.hasNext()) {
            HierarchyElementFeedDAG hef = (HierarchyElementFeedDAG)i.next();
            HierarchyElementFeedEVO hefEVO = hef.createEVO();
            hefEVO.setInsertPending();
            hefs.add(hefEVO);
         }
      }

      return hefs;
   }

   protected Object createEVO() {
      List hefs = this.createFeedEVOs();
      return new HierarchyElementEVO(this.mHierarchyElementId, this.mHierarchy.getHierarchyId(), this.mParent != null?this.mParent.getId():0, this.mParent != null?this.mParent.indexOf(this):0, this.mVisId, this.mDescription, this.mCreditDebit, this.mAugParent != null?new Integer(this.mAugParent.getId()):null, this.mAugParent != null?new Integer(this.mAugParent.augIndexOf(this)):null, this.mAugCreditDebit == 0?null:new Integer(this.mAugCreditDebit), this.mCalElemType != -1?new Integer(this.mCalElemType):null, (Collection)(hefs == null?new ArrayList():hefs));
   }

   public void updateEVO(HierarchyEVO hEVO) {
      if(this.isDirty()) {
         if(this.mHierarchyElementId < 1) {
            hEVO.addHierarchyElementsItem((HierarchyElementEVO)this.createEVO());
         } else {
            HierarchyElementEVO i = hEVO.getHierarchyElementsItem(new HierarchyElementPK(this.mHierarchyElementId));
            this.updateEVO(i);
            hEVO.changeHierarchyElementsItem(i);
         }
      }

      if(this.getActiveChildren() != null) {
         Iterator i1 = this.getActiveChildren().iterator();

         while(i1.hasNext()) {
            Object o = i1.next();
            if(o instanceof HierarchyElementDAG) {
               HierarchyElementDAG child = (HierarchyElementDAG)o;
               child.updateEVO(hEVO);
            }
         }
      }

   }

   protected void updateEVO(HierarchyElementEVO heEVO) {
      heEVO.setVisId(this.getVisId());
      if(this.getDescription() != null && this.getDescription().length() > 0) {
         heEVO.setDescription(this.getDescription());
      }

      heEVO.setParentId(this.mParent != null?this.mParent.getId():0);
      heEVO.setChildIndex(this.mParent != null?this.mParent.indexOf(this):0);
      heEVO.setCreditDebit(this.mCreditDebit);
      heEVO.setAugParentId(this.mAugParent != null?new Integer(this.mAugParent.getId()):null);
      heEVO.setAugChildIndex(this.mAugParent != null?new Integer(this.mAugParent.augIndexOf(this)):null);
      heEVO.setAugCreditDebit(this.mAugCreditDebit == 0?null:new Integer(this.mAugCreditDebit));
      heEVO.setCalElemType(this.mCalElemType != -1?new Integer(this.mCalElemType):null);
      this.updateFeedEVOs(heEVO);
   }

   protected List getFeederElements() {
      ArrayList feeders = new ArrayList();
      if(this.getActiveChildren() != null) {
         Iterator i = this.getActiveChildren().iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if(o instanceof AugHierarchyElementDAG) {
               feeders.addAll(((AugHierarchyElementDAG)o).getFeederElements());
            } else if(o instanceof HierarchyElementFeedDAG) {
               feeders.add(o);
            }
         }
      }

      return feeders;
   }

   protected void updateFeedEVOs(HierarchyElementEVO heEVO) {
      if(heEVO.getFeederElements() != null) {
         ArrayList itemsToDelete = new ArrayList(heEVO.getFeederElements());
         Iterator i = this.getFeederElements().iterator();

         while(i.hasNext()) {
            HierarchyElementFeedDAG hefEVO = (HierarchyElementFeedDAG)i.next();
            HierarchyElementFeedPK hefPK = hefEVO.getPK();
            HierarchyElementFeedEVO hefEVO1 = heEVO.getFeederElementsItem(hefPK);
            if(hefEVO1 != null) {
               hefEVO.updateEVO(hefEVO1);
               itemsToDelete.remove(hefEVO1);
            } else {
               heEVO.addFeederElementsItem(hefEVO.createEVO());
            }
         }

         i = itemsToDelete.iterator();

         while(i.hasNext()) {
            HierarchyElementFeedEVO hefEVO2 = (HierarchyElementFeedEVO)i.next();
            heEVO.deleteFeederElementsItem(hefEVO2.getPK());
         }
      }

   }

   public void removeFromEVO(HierarchyEVO devo) {
      if(this.mHierarchyElementId > 0) {
         devo.deleteHierarchyElementsItem(new HierarchyElementPK(this.mHierarchyElementId));
      }

      if(this.getActiveChildren() != null) {
         Iterator i = this.getActiveChildren().iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if(o instanceof HierarchyElementDAG) {
               HierarchyElementDAG child = (HierarchyElementDAG)o;
               child.removeFromEVO(devo);
            }
         }
      }

   }

   public int indexOf(HierarchyNodeDAG child) {
      return this.mElements != null?this.mElements.indexOf(child):-1;
   }

   public int augIndexOf(HierarchyNodeDAG child) {
      return this.mAugElements != null?this.mAugElements.indexOf(child):-1;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) throws ValidationException {
      if(!this.mVisId.equals(visId)) {
         if(this.mHierarchy != null) {
            this.mHierarchy.updateVisIdMap(this, visId);
         }

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

   public HierarchyElementDAG getParent() {
      return this.mParent;
   }

   public void setParent(HierarchyElementDAG parent) {
      if(this.mParent != parent) {
         this.mParent = parent;
         this.setDirty();
      }

   }

   public HierarchyDAG getHierarchy() {
      return this.mHierarchy;
   }

   public void setHierarchy(HierarchyDAG h) {
      if(this.mHierarchy != h) {
         this.mHierarchy = h;
         this.setDirty();
      }

   }

   public HierarchyNodeDAG getChildAtIndex(int index) {
      return this.mElements != null?(HierarchyNodeDAG)this.mElements.get(index):null;
   }

   public void addFailSafe(int index, HierarchyNodeDAG node) throws ValidationException {
      if(this.mHierarchy != null) {
         this.mHierarchy.addToMaps(node);
      }

      if(this.mElements == null) {
         this.mElements = new ArrayList();
         this.mElements.add(node);
      } else {
         int physIndex = Collections.binarySearch(this.mElements, node, getElementComparator());
         if(physIndex >= 0) {
            this.mHierarchy.removeFromMaps(node);
            throw new IllegalStateException("Duplicate hierarchy element " + node.getVisId() + " index : " + node.getIndex() + " under parent:" + this.mVisId);
         }

         this.mElements.add(-(physIndex + 1), node);
      }

      node.setParent(this);
      this.setDirty();
   }

   public void addAugFailSafe(int index, HierarchyNodeDAG node) throws ValidationException {
      if(node.isAugmentElement() && this.mHierarchy != null) {
         this.mHierarchy.addToMaps(node);
      }

      if(this.mAugElements == null) {
         this.initAugmentedChildList();
      }

      int physIndex = Collections.binarySearch(this.mAugElements, node, getAugElementComparator());
      if(physIndex >= 0) {
         if(node.isAugmentElement()) {
            this.mHierarchy.removeFromMaps(node);
         }

         throw new IllegalStateException("Duplicate hierarchy element " + node.getVisId() + " index:" + node.getIndex() + " under parent:" + this.mVisId);
      } else {
         this.mAugElements.add(-(physIndex + 1), node);
         node.setAugParent(this);
         this.setDirty();
      }
   }

   private void initAugmentedChildList() {
      this.mAugElements = new ArrayList(this.mElements != null?this.mElements:Collections.EMPTY_LIST);
      Iterator cIter = this.mAugElements.iterator();

      while(cIter.hasNext()) {
         HierarchyNodeDAG nodeDAG = (HierarchyNodeDAG)cIter.next();
         nodeDAG.setAugParent(this);
      }

      this.setDirty();
   }

   public void add(HierarchyNodeDAG node) throws ValidationException {
      this.add(this.mElements != null?this.mElements.size():0, node);
   }

   public void add(int index, HierarchyNodeDAG node) throws ValidationException {
      if(this.mHierarchy != null) {
         this.mHierarchy.addToMaps(node);
      }

      if(this.mElements == null) {
         this.mElements = new ArrayList();
      }

      this.mElements.add(index, node);
      node.setParent(this);
      this.setDirty();
   }

   public void remove(HierarchyNodeDAG node) {
      if(this.mElements != null) {
         if(this.mHierarchy != null) {
            this.mHierarchy.removeFromMaps(node);
         }

         this.mElements.remove(node);
         this.setDirty();
      }

   }

   public void removeFromAugChildren(HierarchyNodeDAG node) {
      if(this.mAugElements == null) {
         this.initAugmentedChildList();
      }

      if(node.isAugmentElement() && this.mHierarchy != null) {
         this.mHierarchy.removeFromMaps(node);
      }

      this.mAugElements.remove(node);
      this.setDirty();
   }

   public boolean isLeaf() {
      return this.mElements == null || this.mElements.isEmpty();
   }

   public HierarchyElementImpl createLightweightElement(HierarchyImpl di) {
      DimensionPK dimensionPK = (DimensionPK)this.getHierarchy().getDimension().getEntityRef().getPrimaryKey();
      HierarchyElementCK key = new HierarchyElementCK(dimensionPK, new HierarchyPK(this.getHierarchy().getHierarchyId()), new HierarchyElementPK(this.mHierarchyElementId));
      HierarchyElementImpl dei = new HierarchyElementImpl(key);
      dei.setVisId(this.mVisId);
      dei.setDescription(this.mDescription);
      dei.setCreditDebit(this.mCreditDebit);
      dei.setAugCreditDebit(this.mAugCreditDebit);
      return dei;
   }

   public HierarchyElementImpl createLightweightDAG(HierarchyImpl di) {
      HierarchyElementImpl dei = this.createLightweightElement(di);
      if(this.getActiveChildren() != null) {
         Iterator i = this.getActiveChildren().iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if(o instanceof HierarchyElementFeedDAG) {
               HierarchyElementFeedDAG child = (HierarchyElementFeedDAG)o;
               dei.addChildElement(child.createLightweightDAG(dei));
            } else {
               HierarchyElementDAG child1 = (HierarchyElementDAG)o;
               dei.addChildElement(child1.createLightweightDAG(di));
            }
         }
      }

      return dei;
   }

   public int getId() {
      return this.mHierarchyElementId;
   }

   public List getChildren() {
      return this.mElements;
   }

   public List getActiveChildren() {
      return this.mAugElements != null?this.mAugElements:this.mElements;
   }

   public List getAllChildren(boolean depthFirst) {
      ArrayList result = new ArrayList();
      this.getAllChildren(result, depthFirst);
      return result;
   }

   private void getAllChildren(List l, boolean depthFirst) {
      if(!depthFirst) {
         l.add(this);
      }

      if(this.getActiveChildren() != null) {
         Iterator iter = this.getActiveChildren().iterator();

         while(iter.hasNext()) {
            Object o = iter.next();
            if(o instanceof HierarchyElementDAG) {
               HierarchyElementDAG child = (HierarchyElementDAG)o;
               child.getAllChildren(l, depthFirst);
            } else {
               l.add(o);
            }
         }
      }

      if(depthFirst) {
         l.add(this);
      }

   }

   public boolean isNodeAncestor(HierarchyNodeDAG node) {
      if(node == null) {
         return false;
      } else {
         for(HierarchyElementDAG child = this; child != null; child = child.getActiveParent()) {
            if(child.equals(node)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean equals(HierarchyElementDAG anotherNode) {
      return anotherNode == null?false:this.mHierarchyElementId == anotherNode.mHierarchyElementId;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int index) {
      if(this.mIndex != index) {
         this.setDirty();
         this.mIndex = index;
      }

   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean isFeeder() {
      return false;
   }

   public void findDimensionRefs(List refs) {
      if(this.mElements != null) {
         Iterator i = this.mElements.iterator();

         while(i.hasNext()) {
            HierarchyNodeDAG hn = (HierarchyNodeDAG)i.next();
            if(hn.isFeeder()) {
               HierarchyElementFeedDAG he = (HierarchyElementFeedDAG)hn;
               refs.add(he.getDimensionElement().getEntityRef());
            } else {
               HierarchyElementDAG he1 = (HierarchyElementDAG)hn;
               he1.findDimensionRefs(refs);
            }
         }
      }

   }

   public void findDimensionElements(List<DimensionElement> dimensionElements) {
      if(this.mElements != null) {
         Iterator i = this.mElements.iterator();

         while(i.hasNext()) {
            HierarchyNodeDAG hn = (HierarchyNodeDAG)i.next();
            if(hn.isFeeder()) {
               HierarchyElementFeedDAG he = (HierarchyElementFeedDAG)hn;
               dimensionElements.add(he.getDimensionElement().createLightweightElement((DimensionImpl)null));
            } else {
               HierarchyElementDAG he1 = (HierarchyElementDAG)hn;
               he1.findDimensionElements(dimensionElements);
            }
         }
      }

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

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public void setAugCreditDebit(int augCreditDebit) {
      if(this.mAugCreditDebit != augCreditDebit) {
         this.mAugCreditDebit = augCreditDebit;
         this.setDirty();
      }

   }

   public HierarchyElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new HierarchyElementPK(this.mHierarchyElementId);
      }

      return this.mPK;
   }

   public HierarchyElementRefImpl getHierarchyElementRef() {
      DimensionPK dimensionPK = (DimensionPK)this.getHierarchy().getDimension().getEntityRef().getPrimaryKey();
      HierarchyElementCK key = new HierarchyElementCK(dimensionPK, new HierarchyPK(this.mHierarchy.getHierarchyId()), new HierarchyElementPK(this.mHierarchyElementId));
      return new HierarchyElementRefImpl(key, this.mVisId);
   }

   public EntityRef getEntityRef() {
      return this.getHierarchyElementRef();
   }

   public Object getPrimaryKey() {
      return this.getPK();
   }

   public HierarchyElementDAG getActiveParent() {
      return this.mAugParent != null?this.mAugParent:this.mParent;
   }

   public HierarchyElementDAG getAugParent() {
      return this.mAugParent;
   }

   public void setAugParent(HierarchyElementDAG augParent) {
      if(this.mAugParent != augParent) {
         this.mAugParent = augParent;
         this.setDirty();
      }

   }

   public int getAugIndex() {
      return this.mAugIndex;
   }

   public void setAugIndex(int augIndex) {
      if(this.mAugIndex != augIndex) {
         this.mAugIndex = augIndex;
         this.setDirty();
      }

   }

   public int getActiveIndex() {
      return this.mAugIndex != -1?this.mAugIndex:this.mIndex;
   }

   public void validateAugmentModeMove(HierarchyElementDAG newParent, int newIndex) throws ValidationException {
      HierarchyElementDAG currentRealParent = this.findRealNodeInParentage(false);
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

   public HierarchyElementDAG findRealNodeInParentage(boolean includeThis) {
      if(includeThis && !this.isAugmentElement()) {
         return this;
      } else {
         HierarchyElementDAG parent = this.getActiveParent();
         return parent == null?null:(parent.isAugmentElement()?parent.findRealNodeInParentage(false):parent);
      }
   }

   public boolean isAugmentElement() {
      return false;
   }

   public void addAug(int index, HierarchyNodeDAG child) throws ValidationException {
      this.addAug(index, child, true);
   }

   public void addAug(int index, HierarchyNodeDAG child, boolean addToMaps) throws ValidationException {
      if(this.mAugElements == null) {
         this.initAugmentedChildList();
      }

      if(addToMaps && child.isAugmentElement() && this.mHierarchy != null) {
         this.mHierarchy.addToMaps(child);
      }

      this.mAugElements.add(index, child);
      child.setAugParent(this);
   }

   public HierarchyNodeDAG getAugChildAtIndex(int index) {
      return (HierarchyNodeDAG)this.mAugElements.get(index);
   }

   public List getAugChildren() {
      return this.mAugElements;
   }

   public boolean hasImmediateAugmentedChildren() {
      if(this.mAugElements == null) {
         return false;
      } else {
         Iterator cIter = this.mAugElements.iterator();

         HierarchyNodeDAG nodeDAG;
         do {
            if(!cIter.hasNext()) {
               return false;
            }

            nodeDAG = (HierarchyNodeDAG)cIter.next();
         } while(!nodeDAG.isAugmentElement());

         return true;
      }
   }

   public void revertToNonAugmentedChildList() {
      if(this.mAugElements != null) {
         Iterator cIter = this.mAugElements.iterator();

         while(cIter.hasNext()) {
            HierarchyNodeDAG nodeDAG = (HierarchyNodeDAG)cIter.next();
            nodeDAG.setAugParent((HierarchyElementDAG)null);
         }

         this.mAugElements = null;
      }

   }

   public Object[] getChildKeys() {
      int numChildren = this.getActiveChildren() != null?this.getActiveChildren().size():0;
      if(numChildren == 0) {
         return new Object[0];
      } else {
         Object[] childKeys = new Object[numChildren];
         int idx = 0;

         for(Iterator cIter = this.getActiveChildren().iterator(); cIter.hasNext(); ++idx) {
            HierarchyNodeDAG nodeDAG = (HierarchyNodeDAG)cIter.next();
            childKeys[idx] = nodeDAG.getPrimaryKey();
         }

         return childKeys;
      }
   }

   private static Comparator getElementComparator() {
      if(sElementComparator == null) {
         sElementComparator = new HierarchyElementDAG$1();
      }

      return sElementComparator;
   }

   private static Comparator getAugElementComparator() {
      if(sAugElementComparator == null) {
         sAugElementComparator = new HierarchyElementDAG$2();
      }

      return sAugElementComparator;
   }

   public boolean isDirty() {
      return super.isDirty() || this.isIndexChanged() || this.isAugIndexChanged();
   }

   private boolean isIndexChanged() {
      return this.mParent != null && this.mParent.indexOf(this) != this.mIndex;
   }

   private boolean isAugIndexChanged() {
      return this.mAugParent != null && this.mAugParent.augIndexOf(this) != this.mAugIndex;
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
      return this.mElements != null?this.mElements.size():0;
   }

   public void displayHierarchy(StringBuilder sb, int level, boolean includeLeaves) {
      sb.append(StringUtils.fill("\t", level)).append("HierarcyElement Id:[").append(this.mHierarchyElementId).append("]").append(" VisId:[").append(this.mVisId).append("]\n");
      if(this.getActiveChildren() != null) {
         Iterator i$ = this.getActiveChildren().iterator();

         while(i$.hasNext()) {
            HierarchyNodeDAG node = (HierarchyNodeDAG)i$.next();
            node.displayHierarchy(sb, level + 1, includeLeaves);
         }
      }

   }

   public int countElements() {
      int count = 1;
      HierarchyNodeDAG node;
      if(this.getActiveChildren() != null) {
         for(Iterator i$ = this.getActiveChildren().iterator(); i$.hasNext(); count += node.countElements()) {
            node = (HierarchyNodeDAG)i$.next();
         }
      }

      return count;
   }
}
