// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.RTree$DirectoryNode;
import com.cedar.cp.util.RTree$RTElem;
import com.cedar.cp.util.RTree;
import java.io.PrintStream;
import java.io.Serializable;

interface RTree$RTreeNode<T extends Object> extends Serializable {

   int size();

   RTree$RTElem<T> get(int var1);

   boolean add(RTree$RTElem<T> var1);

   int indexOfNode(RTree$RTreeNode<T> var1);

   boolean isLeaf();

   RTree$DirectoryNode getParent();

   void setParent(RTree$DirectoryNode var1);

   void clear();

   void enlarge(RTree.Rect var1);

   RTree.Rect calcMBR();

   RTree.Rect calcMBR(RTree.Rect var1);

   void displayTree(PrintStream var1);

   Object remove(int var1);

   int getLevel();

   int getId();

   RTree$RTreeNode getNextSibling();
}
