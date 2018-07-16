// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.hierarchy;

import com.cedar.awt.hierarchy.HierarchyModel;
import com.cedar.awt.hierarchy.HierarchyModelListener;
import com.cedar.awt.hierarchy.HierarchySearchEvent;
import javax.swing.event.EventListenerList;
import javax.swing.tree.TreeNode;

public abstract class AbstractHierarchyModel implements HierarchyModel {

   protected EventListenerList mListenerList = new EventListenerList();


   public void addHierarchyModelListener(HierarchyModelListener l) {
      this.mListenerList.add(HierarchyModelListener.class, l);
   }

   public void removeHierarchyModelListener(HierarchyModelListener l) {
      this.mListenerList.remove(HierarchyModelListener.class, l);
   }

   protected void fireHierarchySearchEvent(TreeNode node) {
      Object[] listeners = this.mListenerList.getListenerList();
      HierarchySearchEvent event = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == HierarchyModelListener.class) {
            if(event == null) {
               event = new HierarchySearchEvent(this, node);
            }

            ((HierarchyModelListener)listeners[i + 1]).hierarchySearchResult(event);
         }
      }

   }
}
