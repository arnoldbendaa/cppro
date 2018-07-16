// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HierarchyDAG extends AbstractDAG {

   private DimensionDAG mDimension;
   private String mVisId;
   private String mDescription;
   private HierarchyElementDAG mRoot;
   private int mHierarchyId;
   private int mVersionNum;
   private transient HierarchyRefImpl mEntityRef;
   private boolean mMaintMode;
   private boolean mRequiresRebuild;
   private Map mCellMap;
   private Map mVisIdMap;
   private Log mLog = new Log(this.getClass());


   public HierarchyDAG(DAGContext context) {
      super(context, true);
      this.mVisId = "";
      this.mDescription = "";
      this.mHierarchyId = -1;
      this.mVersionNum = -1;
      this.mCellMap = new HashMap();
      this.mVisIdMap = new HashMap();
   }

   public HierarchyDAG(DAGContext context, DimensionDAG dimension, HierarchyEVO dimEVO) throws ValidationException {
      super(context, false);
      Timer t = new Timer(this.mLog);
      this.mDimension = dimension;
      this.mVisId = dimEVO.getVisId();
      this.mDescription = dimEVO.getDescription();
      this.mHierarchyId = dimEVO.getHierarchyId();
      this.mVersionNum = dimEVO.getVersionNum();
      this.mCellMap = new HashMap();
      this.mVisIdMap = new HashMap();
      this.mMaintMode = true;
      if(dimEVO.getHierarchyElements() != null) {
         Iterator i = dimEVO.getHierarchyElements().iterator();

         HierarchyElementEVO heEVO;
         HierarchyElementDAG parent;
         while(i.hasNext()) {
            heEVO = (HierarchyElementEVO)i.next();
            parent = new HierarchyElementDAG(context, heEVO);
            if(heEVO.getParentId() == 0) {
               this.mRoot = parent;
            }

            this.mCellMap.put(new Integer(heEVO.getHierarchyElementId()), parent);
            this.mVisIdMap.put(parent.getVisId(), parent);
            parent.setHierarchy(this);
         }

         AugHierarchyElementEVO heEVO1;
         if(dimEVO.getAugHierarchyElements() != null) {
            i = dimEVO.getAugHierarchyElements().iterator();

            while(i.hasNext()) {
               heEVO1 = (AugHierarchyElementEVO)i.next();
               AugHierarchyElementDAG parent1 = new AugHierarchyElementDAG(context, heEVO1);
               this.mCellMap.put(new Integer(heEVO1.getHierarchyElementId()), parent1);
               this.mVisIdMap.put(parent1.getVisId(), parent1);
               parent1.setHierarchy(this);
            }
         }

         i = dimEVO.getAugHierarchyElements().iterator();

         HierarchyElementDAG he;
         while(i.hasNext()) {
            heEVO1 = (AugHierarchyElementEVO)i.next();
            if(heEVO1.getParentId() >= 1) {
               parent = (HierarchyElementDAG)this.find(heEVO1.getParentId());
               if(parent == null) {
                  throw new RuntimeException("Unable to locate parent : " + heEVO1.getParentId());
               }

               he = (HierarchyElementDAG)this.find(heEVO1.getHierarchyElementId());
               parent.addAugFailSafe(he.getIndex(), he);
               he.setDirty(false);
            }
         }

         i = dimEVO.getHierarchyElements().iterator();

         while(i.hasNext()) {
            heEVO = (HierarchyElementEVO)i.next();
            if(heEVO.getFeederElements() == null) {
               this.mLog.debug("no feeders: " + heEVO);
            } else {
               Iterator parent2 = heEVO.getFeederElements().iterator();

               while(parent2.hasNext()) {
                  this.attachFeeds(context, (HierarchyElementFeedEVO)parent2.next(), heEVO.getHierarchyElementId(), heEVO.getVisId());
               }
            }

            if(heEVO.getParentId() >= 1) {
               parent = (HierarchyElementDAG)this.find(heEVO.getParentId());
               if(parent == null) {
                  throw new RuntimeException("Unable to locate parent : " + heEVO.getParentId());
               }

               he = (HierarchyElementDAG)this.find(heEVO.getHierarchyElementId());
               if(heEVO.getAugParentId() != null) {
                  HierarchyElementDAG augParent = (HierarchyElementDAG)this.find(heEVO.getAugParentId().intValue());
                  augParent.addAugFailSafe(heEVO.getAugChildIndex().intValue(), he);
               }

               parent.addFailSafe(he.getIndex(), he);
               he.setDirty(false);
            }
         }
      }

      context.getCache().put(new HierarchyPK(this.mHierarchyId), this);
      this.mMaintMode = false;
      t.logInfo("<init>", this.mVisId);
   }

   private void attachFeeds(DAGContext context, HierarchyElementFeedEVO hefEVO, int heId, String heVisId) throws ValidationException {
      DimensionElementPK dimensionElementPK = new DimensionElementPK(hefEVO.getDimensionElementId());
      DimensionElementDAG dimensionElement = (DimensionElementDAG)context.getCache().get(DimensionElementDAG.class, dimensionElementPK);
      HierarchyElementPK hierarchyElementPK = new HierarchyElementPK(hefEVO.getHierarchyElementId());
      HierarchyElementDAG hierarchyElement = (HierarchyElementDAG)this.find(hierarchyElementPK.getHierarchyElementId());
      HierarchyElementDAG augHierarchyElement = hefEVO.getAugHierarchyElementId() != null?(HierarchyElementDAG)this.find(hefEVO.getAugHierarchyElementId().intValue()):null;
      int augIndex = hefEVO.getAugChildIndex() != null?hefEVO.getAugChildIndex().intValue():-1;
      int calElemType = hefEVO.getCalElemType() != null?hefEVO.getCalElemType().intValue():-1;
      HierarchyElementFeedDAG hefDAG = new HierarchyElementFeedDAG(context, hierarchyElement, dimensionElement, hefEVO.getChildIndex(), augHierarchyElement, augIndex, calElemType);
      if(augHierarchyElement != null) {
         augHierarchyElement.addAugFailSafe(augIndex, hefDAG);
         augHierarchyElement.setDirty(false);
      }

      if(hierarchyElement == null) {
         throw new RuntimeException("Unable to locate parent for : " + heId + " - " + heVisId);
      } else {
         hierarchyElement.addFailSafe(hefDAG.getIndex(), hefDAG);
         hierarchyElement.setDirty(false);
         this.mCellMap.put(new Integer(dimensionElement.getPK().getDimensionElementId()), hefDAG);
         this.mVisIdMap.put(dimensionElement.getVisId(), hefDAG);
      }
   }

   public HierarchyImpl createLightweightDAG() {
      return this.createLightweightDAG(new HierarchyImpl(new HierarchyPK(this.mHierarchyId)));
   }

   public HierarchyImpl createLightweightDAG(HierarchyImpl hierarchyImpl) {
      hierarchyImpl.setVisId(this.mVisId);
      hierarchyImpl.setDescription(this.mDescription);
      hierarchyImpl.setDimensionId(this.mDimension.getDimensionId());
      hierarchyImpl.setHierarchyId(this.mHierarchyId);
      if(this.mRoot != null) {
         hierarchyImpl.setRoot(this.mRoot.createLightweightDAG(hierarchyImpl));
      }

      return hierarchyImpl;
   }

   public HierarchyEVO createEVO() {
      ArrayList children = new ArrayList();
      if(this.mRoot != null) {
         this.mRoot.createEVO(children);
      }

      HierarchyEVO devo = new HierarchyEVO(this.mHierarchyId, this.mDimension.getDimensionId(), this.mVisId, this.mDescription, this.mVersionNum, new ArrayList(), new ArrayList());
      Iterator i = children.iterator();

      while(i.hasNext()) {
         HierarchyElementEVO deevo = (HierarchyElementEVO)i.next();
         devo.addHierarchyElementsItem(deevo);
      }

      return devo;
   }

   public void updateEVO(HierarchyEVO hevo) {
      if(this.mRoot != null) {
         this.mRoot.updateEVO(hevo);
      }

      hevo.setVisId(this.mVisId);
      hevo.setDescription(this.mDescription);
      hevo.setVersionNum(this.mVersionNum);
      if(this.isRequiresRebuild()) {
         hevo.setModified(true);
      }

   }

   public HierarchyNodeDAG findElement(String visId) {
      return (HierarchyNodeDAG)this.mVisIdMap.get(visId);
   }

   public HierarchyNodeDAG find(int Id) {
      return (HierarchyNodeDAG)this.mCellMap.get(new Integer(Id));
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
      this.setDirty();
   }

   public HierarchyElementDAG getRoot() {
      return this.mRoot;
   }

   public void setRoot(HierarchyElementDAG root) throws ValidationException {
      this.mRoot = root;
      this.addToMaps(root);
      this.setDirty();
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
      this.setDirty();
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public boolean equals(HierarchyDAG anotherDim) {
      return anotherDim == null?false:this.mHierarchyId == anotherDim.mHierarchyId;
   }

   public HierarchyRef getEntityRef() {
      if(this.mEntityRef == null) {
         this.mEntityRef = new HierarchyRefImpl(new HierarchyPK(this.mHierarchyId), this.mVisId);
      }

      return this.mEntityRef;
   }

   public DimensionDAG getDimension() {
      return this.mDimension;
   }

   public void setDimension(DimensionDAG dimension) {
      this.mDimension = dimension;
   }

   public List getAssignedDimensionElements() {
      ArrayList refs = new ArrayList();
      this.mRoot.findDimensionElements(refs);
      return refs;
   }

   public void addToMaps(HierarchyNodeDAG element) throws ValidationException {
      if(!this.mMaintMode && (this.mDimension == null || this.mDimension.getType() != 3)) {
         if(this.trackVisIds() && this.mVisIdMap.get(element.getVisId()) != null) {
            throw new ValidationException("Duplicate hierarchy element visid:[" + element.getVisId() + "] pk[" + element.getPrimaryKey() + "]");
         }

         this.mCellMap.put(new Integer(element.getId()), element);
         if(this.trackVisIds()) {
            this.mVisIdMap.put(element.getVisId(), element);
         }
      }

   }

   public void removeFromMaps(HierarchyNodeDAG element) {
      if(!this.mMaintMode) {
         if(element.getChildren() != null) {
            Iterator cIter = element.getChildren().iterator();

            while(cIter.hasNext()) {
               HierarchyNodeDAG node = (HierarchyNodeDAG)cIter.next();
               this.removeFromMaps(node);
            }
         }

         this.mCellMap.remove(new Integer(element.getId()));
         if(this.trackVisIds()) {
            this.mVisIdMap.remove(element.getVisId());
         }
      }

   }

   public void updateVisIdMap(HierarchyNodeDAG element, String newName) throws ValidationException {
      if(this.trackVisIds() && this.mVisIdMap.get(newName) != null) {
         throw new ValidationException("Duplicate element name[" + newName + "]");
      } else {
         if(this.trackVisIds()) {
            this.mVisIdMap.remove(element.getVisId());
         }

         this.mVisIdMap.put(newName, element);
      }
   }

   public boolean isMaintMode() {
      return this.mMaintMode;
   }

   public void setMaintMode(boolean maintMode) {
      this.mMaintMode = maintMode;
   }

   private boolean trackVisIds() {
      return this.mDimension == null || this.mDimension.getType() != 3;
   }

   public boolean isRequiresRebuild() {
      return this.mRequiresRebuild;
   }

   public void setRequiresRebuild(boolean requiresRebuild) {
      if(this.mRequiresRebuild != requiresRebuild) {
         this.mRequiresRebuild = requiresRebuild;
         this.setDirty();
      }

   }

   public String displayHierarchy(boolean includeLeaves) {
      StringBuilder sb = new StringBuilder();
      sb.append("Hierarchy: Id:[").append(this.mHierarchyId).append("] VisId:[").append(this.mVisId).append("]");
      this.mRoot.displayHierarchy(sb, 0, includeLeaves);
      return sb.toString();
   }

   public int countElements() {
      return this.mRoot.countElements();
   }
}
