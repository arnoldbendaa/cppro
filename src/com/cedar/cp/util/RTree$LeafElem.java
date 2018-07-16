// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.RTree$RTElem;
import com.cedar.cp.util.RTree$RTreeNode;
import com.cedar.cp.util.RTree;
import java.io.PrintStream;

class RTree$LeafElem<T extends Object> implements RTree$RTElem<T> {

   protected RTree.Rect mRect;
   protected T mObj;


   public RTree$LeafElem(RTree.Rect rect, T obj) {
      this.mRect = rect;
      this.mObj = obj;
   }

   public RTree.Rect getRect() {
      return this.mRect;
   }

   public RTree$RTreeNode getNode() {
      return null;
   }

   public T getUserObject() {
      return this.mObj;
   }

   public void displayTree(PrintStream pw) {
      pw.print("Rect:" + this.mRect);
      pw.print(" - User Obj:" + this.mObj);
   }
}
