// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.AugHierarchyElementImpl;
import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.util.StringUtils;
import java.util.Iterator;

public class AugHierarchyElementDAG extends HierarchyElementDAG {

   static final long serialVersionUID = 1L;


   public AugHierarchyElementDAG(HierarchyElementDAG element) {
      super(element);
   }

   public AugHierarchyElementDAG(DAGContext context, HierarchyDAG dimension, int elementId, String visId, String description, int creditDebit) {
      super(context, dimension, elementId, visId, description, creditDebit, 0, -1);
   }

   public AugHierarchyElementDAG(DAGContext context, AugHierarchyElementEVO elementEVO) {
      super(context, false);
      this.mVisId = elementEVO.getVisId();
      this.mDescription = elementEVO.getDescription();
      this.mHierarchyElementId = elementEVO.getHierarchyElementId();
      this.mCreditDebit = elementEVO.getCreditDebit();
      this.mAugIndex = elementEVO.getChildIndex();
      context.getCache().put(new HierarchyElementPK(this.mHierarchyElementId), this);
   }

   protected Object createEVO() {
      return new AugHierarchyElementEVO(this.mHierarchyElementId, this.mHierarchy.getHierarchyId(), this.mAugParent.getId(), this.mAugParent.augIndexOf(this), this.mVisId, this.mDescription, this.mCreditDebit);
   }

   public void updateEVO(HierarchyEVO hEVO) {
      if(this.isDirty()) {
         if(this.mHierarchyElementId < 1) {
            hEVO.addAugHierarchyElementsItem((AugHierarchyElementEVO)this.createEVO());
         } else {
            AugHierarchyElementEVO i = hEVO.getAugHierarchyElementsItem(new AugHierarchyElementPK(this.mHierarchyElementId));
            this.updateEVO(i);
            hEVO.changeAugHierarchyElementsItem(i);
         }
      }

      if(this.mAugElements != null) {
         Iterator i1 = this.mAugElements.iterator();

         while(i1.hasNext()) {
            Object o = i1.next();
            if(o instanceof HierarchyElementDAG) {
               HierarchyElementDAG child = (HierarchyElementDAG)o;
               child.updateEVO(hEVO);
            }
         }
      }

   }

   protected void updateEVO(AugHierarchyElementEVO aheEVO) {
      aheEVO.setVisId(this.getVisId());
      aheEVO.setDescription(this.getDescription());
      aheEVO.setParentId(this.mAugParent.getId());
      aheEVO.setChildIndex(this.mAugParent.getAugChildren().indexOf(this));
      aheEVO.setCreditDebit(this.mCreditDebit);
   }

   public void removeFromEVO(HierarchyEVO devo) {
      if(this.mHierarchyElementId > 0) {
         devo.deleteAugHierarchyElementsItem(new AugHierarchyElementPK(this.mHierarchyElementId));
      }

   }

   public void removeFromParent() throws ValidationException {
      if(this.mAugParent != null) {
         int index = this.mAugParent.augIndexOf(this);
         this.mAugParent.removeFromAugChildren(this);
         if(this.mAugElements != null) {
            for(Iterator i = this.mAugElements.iterator(); i.hasNext(); ++index) {
               HierarchyNodeDAG nodeDAG = (HierarchyNodeDAG)i.next();
               this.mAugParent.addAug(index, nodeDAG, false);
            }
         }
      }

   }

   public boolean isAugmentElement() {
      return true;
   }

   public HierarchyElementImpl createLightweightElement(HierarchyImpl di) {
      DimensionPK dimensionPK = (DimensionPK)this.getHierarchy().getDimension().getEntityRef().getPrimaryKey();
      HierarchyElementCK key = new HierarchyElementCK(dimensionPK, new HierarchyPK(this.getHierarchy().getHierarchyId()), new HierarchyElementPK(this.mHierarchyElementId));
      AugHierarchyElementImpl dei = new AugHierarchyElementImpl(key);
      dei.setVisId(this.mVisId);
      dei.setDescription(this.mDescription);
      dei.setCreditDebit(this.mCreditDebit);
      return dei;
   }

   public void displayHierarchy(StringBuilder sb, int level, boolean includeLeaves) {
      sb.append(StringUtils.fill("\t", level)).append("AugHierarcyElement Id:[").append(this.mHierarchyElementId).append("]").append(" VisId:[").append(this.mVisId).append("]\n");
      if(this.getActiveChildren() != null) {
         Iterator i$ = this.getActiveChildren().iterator();

         while(i$.hasNext()) {
            HierarchyNodeDAG node = (HierarchyNodeDAG)i$.next();
            node.displayHierarchy(sb, level + 1, includeLeaves);
         }
      }

   }
}
