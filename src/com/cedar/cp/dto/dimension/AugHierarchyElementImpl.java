// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import java.util.Iterator;
import javax.swing.tree.TreeNode;

public class AugHierarchyElementImpl extends HierarchyElementImpl {

   public AugHierarchyElementImpl(Object paramKey) {
      super(paramKey);
   }

   public boolean isAugmentElement() {
      return true;
   }

   public void detachFromParent() {
      if(this.getParent() != null) {
         HierarchyElementImpl parent = (HierarchyElementImpl)this.getParent();
         int index = parent.getChildren().indexOf(this);
         parent.removeChildElement(this);
         if(this.getChildren() != null) {
            for(Iterator i = this.getChildren().iterator(); i.hasNext(); ++index) {
               TreeNode node = (TreeNode)i.next();
               parent.addChildElement(index, node);
            }
         }
      }

   }
}
