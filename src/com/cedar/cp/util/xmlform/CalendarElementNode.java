// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;

public interface CalendarElementNode extends TreeNode {

   int getStructureId();

   int getStructureElementId();

   String getVisId();

   String getFullPathVisId();

   String getDescription();

   int getPosition();

   int getEndPosition();

   int getDepth();

   int getElemType();

   Timestamp getActualDate();

   List<CalendarElementNode> getChildren();

   CalendarElementNode getYearNode();

   Object getKey();

   Object getStructureElementRef();

   Enumeration preorderEnumeration();

   Enumeration postorderEnumeration();

   Enumeration breadthFirstEnumeration();

   Enumeration depthFirstEnumeration();

   CalendarElementNode[] getPath();

   CalendarElementNode getLastLeaf();

   TreeNode getLastChild();

   CalendarElementNode getPreviousLeaf();

   CalendarElementNode getNextSibling();

   CalendarElementNode getPreviousSibling();

   TreeNode getChildAfter(TreeNode var1);

   TreeNode getChildBefore(TreeNode var1);

   boolean isNodeSibling(TreeNode var1);

   boolean isNodeChild(TreeNode var1);

   boolean isNodeDescendant(CalendarElementNode var1);

   boolean isNodeAncestor(TreeNode var1);
}
