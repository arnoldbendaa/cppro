// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl1;
import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl$BreadthFirstEnumeration;
import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl$PostorderEnumeration;
import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl$PreorderEnumeration;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.tree.TreeNode;

public class CalendarElementNodeImpl implements CalendarElementNode, Serializable {

   public static final Enumeration<TreeNode> EMPTY_ENUMERATION = new CalendarElementNodeImpl1();
   private transient Object mKey;
   private int mParentId;
   private CalendarElementNodeImpl mParent;
   private int mStructureId;
   private int mStructureElementId;
   private String mVisId;
   private String mDescription;
   private int mPosition;
   private int mEndPosition;
   private int mDepth;
   private int mElemType;
   private Timestamp mActualDate;
   private List<CalendarElementNode> mChildren;


   public CalendarElementNodeImpl(int parentId, int structureId, int structureElementId, String visId, String description, int position, int endPosition, int depth, int elemType, Timestamp actualDate, List<CalendarElementNode> children) {
      this.mParentId = parentId;
      this.mStructureId = structureId;
      this.mStructureElementId = structureElementId;
      this.mVisId = visId;
      this.mDescription = description;
      this.mPosition = position;
      this.mEndPosition = endPosition;
      this.mDepth = depth;
      this.mElemType = elemType;
      this.mActualDate = actualDate;
      this.mChildren = children;
   }

   public String toString() {
      return this.mVisId + " - " + this.mDescription;
   }

   public void setParent(CalendarElementNodeImpl parent) {
      this.mParent = parent;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getFullPathVisId() {
      StringBuilder pathString = new StringBuilder();
      CalendarElementNode[] path = this.getPath();
      if(path != null) {
         for(int i = 1; i < path.length; ++i) {
            pathString.append("/");
            pathString.append(path[i].getVisId());
         }
      }

      return pathString.toString();
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public int getEndPosition() {
      return this.mEndPosition;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public int getElemType() {
      return this.mElemType;
   }

   public Timestamp getActualDate() {
      return this.mActualDate;
   }

   public List<CalendarElementNode> getChildren() {
      return this.mChildren;
   }

   public void setChildren(List<CalendarElementNode> children) {
      this.mChildren = children;
   }

   public TreeNode getChildAt(int childIndex) {
      return this.mChildren != null?(CalendarElementNode)this.mChildren.get(childIndex):null;
   }

   public int getChildCount() {
      return this.mChildren != null?this.mChildren.size():0;
   }

   public TreeNode getParent() {
      return this.mParent;
   }

   public int getIndex(TreeNode node) {
      return this.mChildren != null?this.mChildren.indexOf(node):-1;
   }

   public boolean getAllowsChildren() {
      return this.mChildren != null;
   }

   public boolean isLeaf() {
      return this.mChildren == null || this.mChildren.size() == 0;
   }

   public Enumeration children() {
      return this.mChildren != null?Collections.enumeration(this.mChildren):Collections.enumeration(Collections.EMPTY_LIST);
   }

   public int getIndexInParentType(int calElemType) {
      CalendarElementNodeImpl parentTypeNode = this.getParentOfType(calElemType);
      if(parentTypeNode == null) {
         return -1;
      } else {
         List siblingsInLevel = this.getChildNodesOfType(this.getElemType(), new ArrayList());
         return siblingsInLevel.indexOf(this);
      }
   }

   public List<CalendarElementNodeImpl> getChildNodesOfType(int calElemType, List<CalendarElementNodeImpl> nodes) {
      if(this.getElemType() == calElemType) {
         nodes.add(this);
      } else if(this.isPeriod(this.getElemType()) && this.isPeriod(calElemType)) {
         nodes.add(this);
      } else if(this.mChildren != null) {
         Iterator i$ = this.mChildren.iterator();

         while(i$.hasNext()) {
            CalendarElementNode node = (CalendarElementNode)i$.next();
            ((CalendarElementNodeImpl)node).getChildNodesOfType(calElemType, nodes);
         }
      }

      return nodes;
   }

   private boolean isPeriod(int calElemType) {
      return calElemType == 3 || calElemType == 8 || calElemType == 9 || calElemType == 7;
   }

   public CalendarElementNodeImpl getParentOfType(int calElemType) {
      return this.mElemType == calElemType?this:(this.mParent != null?this.mParent.getParentOfType(calElemType):null);
   }

   public CalendarElementNode getYearNode() {
      return this.getParentOfType(0);
   }

   public List<CalendarElementNode> findElementsWithVisId(String visId, List<CalendarElementNode> nodes) {
      if(this.getVisId().equals(visId) && !nodes.contains(this)) {
         nodes.add(this);
      }

      return this.findChildElementsWithVisId(visId, nodes);
   }

   public List<CalendarElementNode> findChildElementsWithVisId(String visId, List<CalendarElementNode> nodes) {
      if(this.mChildren != null) {
         Iterator i$ = this.mChildren.iterator();

         CalendarElementNode node;
         while(i$.hasNext()) {
            node = (CalendarElementNode)i$.next();
            if(node.getVisId().equals(visId)) {
               nodes.add(node);
            }
         }

         i$ = this.mChildren.iterator();

         while(i$.hasNext()) {
            node = (CalendarElementNode)i$.next();
            ((CalendarElementNodeImpl)node).findElementsWithVisId(visId, nodes);
         }
      }

      return nodes;
   }

   public Object getKey() {
      if(this.mKey == null) {
         this.mKey = new Integer(this.mStructureElementId);
      }

      return this.mKey;
   }

   public StructureElementRef getStructureElementRef() {
      return new StructureElementRefImpl(new StructureElementPK(this.getStructureId(), this.getStructureElementId()), this.getVisId());
   }

   public Enumeration preorderEnumeration() {
      return new CalendarElementNodeImpl$PreorderEnumeration(this, this);
   }

   public Enumeration postorderEnumeration() {
      return new CalendarElementNodeImpl$PostorderEnumeration(this, this);
   }

   public Enumeration breadthFirstEnumeration() {
      return new CalendarElementNodeImpl$BreadthFirstEnumeration(this, this);
   }

   public Enumeration depthFirstEnumeration() {
      return this.postorderEnumeration();
   }

   public CalendarElementNode[] getPath() {
      return this.getPathToRoot(this, 0);
   }

   protected CalendarElementNode[] getPathToRoot(CalendarElementNode aNode, int depth) {
      CalendarElementNode[] retNodes;
      if(aNode == null) {
         if(depth == 0) {
            return null;
         }

         retNodes = new CalendarElementNode[depth];
      } else {
         ++depth;
         retNodes = this.getPathToRoot((CalendarElementNode)aNode.getParent(), depth);
         retNodes[retNodes.length - depth] = aNode;
      }

      return retNodes;
   }

   public CalendarElementNode getLastLeaf() {
      Object node;
      for(node = this; !((CalendarElementNode)node).isLeaf(); node = (CalendarElementNode)((CalendarElementNode)node).getLastChild()) {
         ;
      }

      return (CalendarElementNode)node;
   }

   public TreeNode getLastChild() {
      if(this.getChildCount() == 0) {
         throw new NoSuchElementException("node has no children");
      } else {
         return this.getChildAt(this.getChildCount() - 1);
      }
   }

   public CalendarElementNode getPreviousLeaf() {
      CalendarElementNode myParent = (CalendarElementNode)this.getParent();
      if(myParent == null) {
         return null;
      } else {
         CalendarElementNode previousSibling = this.getPreviousSibling();
         return previousSibling != null?previousSibling.getLastLeaf():myParent.getPreviousLeaf();
      }
   }

   public CalendarElementNode getNextSibling() {
      CalendarElementNode myParent = (CalendarElementNode)this.getParent();
      CalendarElementNode retval;
      if(myParent == null) {
         retval = null;
      } else {
         retval = (CalendarElementNode)myParent.getChildAfter(this);
      }

      if(retval != null && !this.isNodeSibling(retval)) {
         throw new Error("child of parent is not a sibling");
      } else {
         return retval;
      }
   }

   public CalendarElementNode getPreviousSibling() {
      CalendarElementNode myParent = (CalendarElementNode)this.getParent();
      CalendarElementNode retval;
      if(myParent == null) {
         retval = null;
      } else {
         retval = (CalendarElementNode)myParent.getChildBefore(this);
      }

      if(retval != null && !this.isNodeSibling(retval)) {
         throw new Error("child of parent is not a sibling");
      } else {
         return retval;
      }
   }

   public TreeNode getChildAfter(TreeNode aChild) {
      if(aChild == null) {
         throw new IllegalArgumentException("argument is null");
      } else {
         int index = this.getIndex(aChild);
         if(index == -1) {
            throw new IllegalArgumentException("node is not a child");
         } else {
            return index < this.getChildCount() - 1?this.getChildAt(index + 1):null;
         }
      }
   }

   public TreeNode getChildBefore(TreeNode aChild) {
      if(aChild == null) {
         throw new IllegalArgumentException("argument is null");
      } else {
         int index = this.getIndex(aChild);
         if(index == -1) {
            throw new IllegalArgumentException("argument is not a child");
         } else {
            return index > 0?this.getChildAt(index - 1):null;
         }
      }
   }

   public boolean isNodeSibling(TreeNode anotherNode) {
      boolean retval;
      if(anotherNode == null) {
         retval = false;
      } else if(anotherNode == this) {
         retval = true;
      } else {
         TreeNode myParent = this.getParent();
         retval = myParent != null && myParent == anotherNode.getParent();
         if(retval && !((CalendarElementNode)this.getParent()).isNodeChild(anotherNode)) {
            throw new Error("sibling has different parent");
         }
      }

      return retval;
   }

   public boolean isNodeChild(TreeNode aNode) {
      boolean retval;
      if(aNode == null) {
         retval = false;
      } else if(this.getChildCount() == 0) {
         retval = false;
      } else {
         retval = aNode.getParent() == this;
      }

      return retval;
   }

   public boolean isNodeAncestor(TreeNode anotherNode) {
      if(anotherNode == null) {
         return false;
      } else {
         Object ancestor = this;

         while(ancestor != anotherNode) {
            if((ancestor = ((TreeNode)ancestor).getParent()) == null) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isNodeDescendant(CalendarElementNode anotherNode) {
      return anotherNode == null?false:anotherNode.isNodeAncestor(this);
   }

   public boolean equals(Object o) {
      if(this == o) {
         return true;
      } else if(o != null && this.getClass() == o.getClass()) {
         CalendarElementNodeImpl that = (CalendarElementNodeImpl)o;
         return this.getKey().equals(that.getKey());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getKey().hashCode();
   }

}
