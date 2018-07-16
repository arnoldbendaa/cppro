// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import java.util.Comparator;

final class HierarchyElementDAG$2 implements Comparator {

   public int compare(Object o1, Object o2) {
      HierarchyElementFeedDAG he2;
      HierarchyElementDAG he21;
      if(o1 instanceof HierarchyElementDAG) {
         HierarchyElementDAG hef1 = (HierarchyElementDAG)o1;
         if(o2 instanceof HierarchyElementDAG) {
            he21 = (HierarchyElementDAG)o2;
            return hef1.getId() == he21.getId()?0:hef1.getActiveIndex() - he21.getActiveIndex();
         }

         if(o2 instanceof HierarchyElementFeedDAG) {
            he2 = (HierarchyElementFeedDAG)o2;
            return hef1.getActiveIndex() - he2.getActiveIndex();
         }
      } else if(o1 instanceof HierarchyElementFeedDAG) {
         HierarchyElementFeedDAG hef11 = (HierarchyElementFeedDAG)o1;
         if(o2 instanceof HierarchyElementFeedDAG) {
            he2 = (HierarchyElementFeedDAG)o2;
            return hef11.getDimensionElement().getId() == he2.getDimensionElement().getId() && hef11.getHierarchyElement().getId() == he2.getHierarchyElement().getId()?0:hef11.getActiveIndex() - he2.getActiveIndex();
         }

         if(o2 instanceof HierarchyElementDAG) {
            he21 = (HierarchyElementDAG)o2;
            return hef11.getActiveIndex() - he21.getActiveIndex();
         }
      }

      throw new IllegalArgumentException("Unexpected class in HierarchyElementFeedDAG.compare()");
   }
}
