// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.RTree$LeafElem;
import com.cedar.cp.util.RTree$RTreeNode;
import com.cedar.cp.util.RTree;

class RTree$DirElem extends RTree$LeafElem<RTree$RTreeNode> {

   public RTree$DirElem(RTree.Rect rect, RTree$RTreeNode node) {
      super(rect, node);
      if(node == null) {
         long x = rect.area();
      }

   }

   public RTree$RTreeNode getNode() {
      return (RTree$RTreeNode)this.mObj;
   }
}
