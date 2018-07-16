// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.RTree$RTreeNode;
import com.cedar.cp.util.RTree;
import java.io.PrintStream;
import java.io.Serializable;

interface RTree$RTElem<T extends Object> extends Serializable {

   RTree.Rect getRect();

   RTree$RTreeNode<T> getNode();

   T getUserObject();

   void displayTree(PrintStream var1);
}
