// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.RTree$RTreeNode;
import java.util.Iterator;

class RTree$Iter implements Iterator {

   private RTree$RTreeNode mNode;
   private int mIndex;
   // $FF: synthetic field
   final RTree this$0;


   RTree$Iter(RTree var1) {
      this.this$0 = var1;

      for(this.mNode = RTree.accessMethod200(var1); this.mNode != null && !this.mNode.isLeaf(); this.mNode = this.mNode.get(0).getNode()) {
         ;
      }

   }

   public boolean hasNext() {
      return this.mNode != null && this.mIndex < this.mNode.size();
   }

   public Object next() {
      Object result = this.mNode.get(this.mIndex).getUserObject();
      ++this.mIndex;
      if(this.mIndex >= this.mNode.size()) {
         this.mNode = this.mNode.getNextSibling();
         this.mIndex = 0;
      }

      return result;
   }

   public void remove() {
      throw new UnsupportedOperationException("Remove is not supported in the rtree interator");
   }
}
