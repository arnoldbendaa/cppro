// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.RTree$DirElem;
import com.cedar.cp.util.RTree$RTElem;
import com.cedar.cp.util.RTree$RTreeNode;
import com.cedar.cp.util.RTree;
import java.io.PrintStream;
import java.util.ArrayList;

class RTree$DirectoryNode extends ArrayList<RTree$DirElem> implements RTree$RTreeNode {

   private RTree$DirectoryNode mParent;
   private int mId = RTree.accessMethod000();


   public boolean isLeaf() {
      return false;
   }

   public RTree$DirectoryNode getParent() {
      return this.mParent;
   }

   public void setParent(RTree$DirectoryNode parent) {
      this.mParent = parent;
   }

   public int indexOfNode(RTree$RTreeNode node) {
      for(int i = 0; i < this.size(); ++i) {
         RTree$RTElem rtElem = (RTree$RTElem)this.get(i);
         if(rtElem.getNode() == node) {
            return i;
         }
      }

      return -1;
   }

   public boolean add(RTree$RTElem elem) {
      return super.add((RTree$DirElem)elem);
   }

   public void enlarge(RTree.Rect rect) {
      if(this.getParent() != null) {
         RTree$DirectoryNode parent = this.getParent();
         int indexInParent = parent.indexOfNode(this);
         RTree$RTElem elem = (RTree$RTElem)parent.get(indexInParent);
         elem.getRect().enlarge(rect);
      }

   }

   public RTree.Rect calcMBR() {
      RTree.Rect mbr = new RTree.Rect();
      mbr.set(((RTree$DirElem)this.get(0)).getRect());

      for(int i = 0; i < this.size(); ++i) {
         mbr.enlarge(((RTree$DirElem)this.get(i)).getRect());
      }

      return mbr;
   }

   public RTree.Rect calcMBR(RTree.Rect result) {
      result.set(((RTree$DirElem)this.get(0)).getRect());

      for(int i = 0; i < this.size(); ++i) {
         result.enlarge(((RTree$DirElem)this.get(i)).getRect());
      }

      return result;
   }

   public void displayTree(PrintStream ps) {
      if(this.getParent() != null) {
         RTree.accessMethod100(this.getParent().indexOfNode(this) != -1);
      }

      RTree.os(this.getLevel(), ps);
      ps.println("DirectoryNode Id:" + this.getId());

      int i;
      for(i = 0; i < this.size(); ++i) {
         RTree.os(this.getLevel(), ps);
         ((RTree$DirElem)this.get(i)).displayTree(ps);
         ps.println();
      }

      ps.println();

      for(i = 0; i < this.size(); ++i) {
         ((RTree$DirElem)this.get(i)).getNode().displayTree(ps);
      }

   }

   public int getLevel() {
      return this.mParent != null?this.mParent.getLevel() + 1:0;
   }

   public int getId() {
      return this.mId;
   }

   public RTree$RTreeNode getNextSibling() {
      if(this.getParent() != null) {
         RTree$DirectoryNode parent = this.getParent();
         int index = parent.indexOfNode(this);
         if(index < parent.size() - 1) {
            return parent.get(index + 1).getNode();
         } else {
            RTree$RTreeNode parentSibling = parent.getNextSibling();
            return parentSibling != null?parentSibling.get(0).getNode():null;
         }
      } else {
         return null;
      }
   }
}
