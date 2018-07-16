// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.Pair;
import com.cedar.cp.util.RTree$DirElem;
import com.cedar.cp.util.RTree$DirectoryNode;
import com.cedar.cp.util.RTree$Iter;
import com.cedar.cp.util.RTree$LeafElem;
import com.cedar.cp.util.RTree$RTElem;
import com.cedar.cp.util.RTree$RTreeNode;
import com.cedar.cp.util.RTree$ValueNode;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RTree<T extends Object> implements Serializable {

   private Rect mPickSeedsWorkRect1 = new Rect();
   private Rect mPickSeedsWorkRect2 = new Rect();
   private Rect mPickNextWorkRect = new Rect();
   private Rect mSplitNodeLeftWorkRect = new Rect();
   private Rect mSplitNodeRightWorkRect = new Rect();
   private int[] mSplitNodeResultArray = new int[]{0, 0};
   private List<RTree$RTElem> mSplitNodeAllElems = new ArrayList(30);
   private Rect mInsWorkRect = new Rect();
   private int mLeafLevel;
   private static int sId = 0;
   private int mMaxNodeSize;
   private RTree$RTreeNode mRoot = new RTree$ValueNode(this);


   private boolean isNodeFull(RTree$RTreeNode node) {
      return node.size() >= this.mMaxNodeSize;
   }

   public RTree(int maxNodeSize) {
      this.mMaxNodeSize = maxNodeSize;
   }

   private int[] pickSeeds(List<RTree$RTElem> elems, int[] result) {
      long bestRating = 0L;

      for(int i = 0; i < elems.size(); ++i) {
         RTree$RTElem e1 = (RTree$RTElem)elems.get(i);

         for(int j = 0; j < elems.size(); ++j) {
            if(j != i) {
               RTree$RTElem e2 = (RTree$RTElem)elems.get(j);
               this.mPickSeedsWorkRect1.set(e1.getRect());
               this.mPickSeedsWorkRect1.enlarge(e2.getRect());
               long rating = this.mPickSeedsWorkRect1.area() - e1.getRect().area() - e2.getRect().area();
               if(e1.getRect().intersects(e2.getRect())) {
                  rating += Rect.intersection(e1.getRect(), e2.getRect(), this.mPickSeedsWorkRect2).area();
               }

               if(rating > bestRating) {
                  bestRating = rating;
                  result[0] = i;
                  result[1] = j;
               }
            }
         }
      }

      if(result[0] == result[1]) {
         result[0] = 0;
         result[1] = 1;
      }

      return result;
   }

   private int pickNext(List<RTree$RTElem> elems, Rect r1, Rect r2) {
      long biggestDiff = -1L;
      int result = -1;

      for(int i = 0; i < elems.size(); ++i) {
         RTree$RTElem elem = (RTree$RTElem)elems.get(i);
         long startArea = r1.area();
         this.mPickNextWorkRect.set(r1);
         this.mPickNextWorkRect.enlarge(elem.getRect());
         long r1Enlargement = this.mPickNextWorkRect.area() - startArea;
         startArea = r2.area();
         this.mPickNextWorkRect.set(r2);
         this.mPickNextWorkRect.enlarge(elem.getRect());
         long r2Enlargement = this.mPickNextWorkRect.area() - startArea;
         long diff = Math.max(r1Enlargement, r2Enlargement) - Math.min(r1Enlargement, r2Enlargement);
         if(diff > biggestDiff) {
            biggestDiff = diff;
            result = i;
         }
      }

      return result;
   }

   private void add(Rect rect) {
      this.add(rect, rect.toString());
   }

   public void add(Rect rect, Object o) {
      this.insertNode(rect, o, -1);
   }

   private void insertNode(Rect rect, Object o, int level) {
      RTree$RTreeNode startNode = this.findInsertNode(this.mRoot, 0, rect, level);
      RTree$RTreeNode newNode = null;
      if(!this.isNodeFull(startNode)) {
         Object newElem;
         if(!startNode.isLeaf()) {
            newElem = new RTree$DirElem(rect, (RTree$RTreeNode)o);
            ((RTree$RTreeNode)o).setParent((RTree$DirectoryNode)startNode);
         } else {
            newElem = new RTree$LeafElem(rect, o);
         }

         startNode.add((RTree$RTElem)newElem);
      } else {
         newNode = this.splitNode(startNode, rect, o);
      }

      this.adjustTree(startNode, newNode);
   }

   private RTree$RTreeNode splitNode(RTree$RTreeNode<T> targetNode, Rect rect, Object o) {
      List allElems = this.mSplitNodeAllElems;
      this.mSplitNodeAllElems.clear();
      allElems.addAll((List)targetNode);
      allElems.add(o instanceof RTree$RTreeNode?new RTree$DirElem(rect, (RTree$RTreeNode)o):new RTree$LeafElem(rect, o));
      int[] seedIdx = this.pickSeeds(allElems, this.mSplitNodeResultArray);
      targetNode.clear();
      Rect leftRect = new Rect(((RTree$RTElem)allElems.get(seedIdx[0])).getRect());
      RTree$RTreeNode leftNode = targetNode;
      RTree$RTElem leftSeedElem = (RTree$RTElem)allElems.get(seedIdx[0]);
      targetNode.add(leftSeedElem);
      if(!targetNode.isLeaf()) {
         leftSeedElem.getNode().setParent((RTree$DirectoryNode)targetNode);
      }

      Rect rightRect = new Rect(((RTree$RTElem)allElems.get(seedIdx[1])).getRect());
      Object rightNode = targetNode.isLeaf()?new RTree$ValueNode(this):new RTree$DirectoryNode();
      RTree$RTElem rightSeedElem = (RTree$RTElem)allElems.get(seedIdx[1]);
      ((RTree$RTreeNode)rightNode).add(rightSeedElem);
      if(!((RTree$RTreeNode)rightNode).isLeaf()) {
         rightSeedElem.getNode().setParent((RTree$DirectoryNode)rightNode);
      }

      allElems.remove(Math.max(seedIdx[0], seedIdx[1]));
      allElems.remove(Math.min(seedIdx[0], seedIdx[1]));

      int targetIdx;
      for(; !allElems.isEmpty(); allElems.remove(targetIdx)) {
         RTree$RTElem targetElem;
         Iterator targetIdx1;
         if(this.getMinimumElementCount() - leftNode.size() == allElems.size()) {
            for(targetIdx1 = allElems.iterator(); targetIdx1.hasNext(); leftRect.enlarge(targetElem.getRect())) {
               targetElem = (RTree$RTElem)targetIdx1.next();
               leftNode.add(targetElem);
               if(!leftNode.isLeaf()) {
                  targetElem.getNode().setParent((RTree$DirectoryNode)leftNode);
               }
            }

            return (RTree$RTreeNode)rightNode;
         }

         if(this.getMinimumElementCount() - ((RTree$RTreeNode)rightNode).size() == allElems.size()) {
            for(targetIdx1 = allElems.iterator(); targetIdx1.hasNext(); rightRect.enlarge(targetElem.getRect())) {
               targetElem = (RTree$RTElem)targetIdx1.next();
               ((RTree$RTreeNode)rightNode).add(targetElem);
               if(!((RTree$RTreeNode)rightNode).isLeaf()) {
                  targetElem.getNode().setParent((RTree$DirectoryNode)rightNode);
               }
            }

            return (RTree$RTreeNode)rightNode;
         }

         targetIdx = allElems.size() > 1?this.pickNext(allElems, leftRect, rightRect):0;
         targetElem = (RTree$RTElem)allElems.get(targetIdx);
         this.mSplitNodeLeftWorkRect.set(leftRect);
         this.mSplitNodeLeftWorkRect.enlarge(targetElem.getRect());
         long leftEnlarement = this.mSplitNodeLeftWorkRect.area() - leftRect.area();
         this.mSplitNodeRightWorkRect.set(rightRect);
         this.mSplitNodeRightWorkRect.enlarge(targetElem.getRect());
         long rightEnlargement = this.mSplitNodeRightWorkRect.area() - rightRect.area();
         if(leftEnlarement < rightEnlargement) {
            leftRect.enlarge(this.mSplitNodeLeftWorkRect);
            leftNode.add(targetElem);
            if(!leftNode.isLeaf()) {
               targetElem.getNode().setParent((RTree$DirectoryNode)leftNode);
            }
         } else {
            rightRect.enlarge(this.mSplitNodeRightWorkRect);
            ((RTree$RTreeNode)rightNode).add(targetElem);
            if(!((RTree$RTreeNode)rightNode).isLeaf()) {
               targetElem.getNode().setParent((RTree$DirectoryNode)rightNode);
            }
         }
      }

      return (RTree$RTreeNode)rightNode;
   }

   private void adjustTree(RTree$RTreeNode leftNode, RTree$RTreeNode rightNode) {
      RTree$DirectoryNode parentNode = leftNode.getParent();
      if(parentNode != null) {
         Rect leftNodeMBR = leftNode.calcMBR();
         int leftIndexInParent = parentNode.indexOfNode(leftNode);
         RTree$RTElem elem = (RTree$RTElem)parentNode.get(leftIndexInParent);
         elem.getRect().set(leftNodeMBR);
         RTree$RTreeNode newNode = null;
         if(rightNode != null) {
            Rect rightNodeMBR = rightNode.calcMBR();
            if(this.isNodeFull(parentNode)) {
               newNode = this.splitNode(parentNode, rightNodeMBR, rightNode);
            } else {
               rightNode.setParent(parentNode);
               parentNode.add(new RTree$DirElem(rightNodeMBR, rightNode));
            }
         }

         this.adjustTree(parentNode, newNode);
      } else if(rightNode != null) {
         this.mRoot = parentNode = new RTree$DirectoryNode();
         parentNode.add(new RTree$DirElem(leftNode.calcMBR(), leftNode));
         parentNode.add(new RTree$DirElem(rightNode.calcMBR(), rightNode));
         leftNode.setParent(parentNode);
         rightNode.setParent(parentNode);
         ++this.mLeafLevel;
      }

   }

   public boolean remove(Rect rect) {
      return this.remove(rect, rect.toString());
   }

   public boolean remove(Rect rect, Object userObject) {
      Pair leafNodePair = this.findNodeForDelete(this.mRoot, rect, userObject);
      if(leafNodePair == null) {
         return false;
      } else {
         ((RTree$RTreeNode)leafNodePair.getChild1()).remove(((Integer)leafNodePair.getChild2()).intValue());
         this.condenseTree((RTree$RTreeNode)leafNodePair.getChild1());
         if(this.mRoot.size() == 1 && !this.mRoot.isLeaf()) {
            this.mRoot = this.mRoot.get(0).getNode();
            this.mRoot.setParent((RTree$DirectoryNode)null);
            --this.mLeafLevel;
         }

         return true;
      }
   }

   private void condenseTree(RTree$RTreeNode node) {
      if(this.mRoot != node) {
         ArrayList deletedNodes = null;

         int origLeafLevel;
         for(RTree$DirectoryNode parentNode = ((RTree$RTreeNode)node).getParent(); node != this.mRoot; parentNode = parentNode.getParent()) {
            origLeafLevel = parentNode.indexOfNode((RTree$RTreeNode)node);
            RTree$RTElem i$ = (RTree$RTElem)parentNode.get(origLeafLevel);
            if(((RTree$RTreeNode)node).size() < this.getMinimumElementCount()) {
               parentNode.remove(i$);
               if(deletedNodes == null) {
                  deletedNodes = new ArrayList();
               }

               deletedNodes.add(new Pair(node, Integer.valueOf(((RTree$RTreeNode)node).getLevel())));
            } else {
               ((RTree$RTreeNode)node).calcMBR(i$.getRect());
            }

            node = parentNode;
         }

         if(deletedNodes != null) {
            origLeafLevel = this.getLeafLevel();
            Iterator var11 = deletedNodes.iterator();

            while(var11.hasNext()) {
               Pair deleteNodePair = (Pair)var11.next();
               RTree$RTreeNode deletedNode = (RTree$RTreeNode)deleteNodePair.getChild1();
               int deletedLevel = ((Integer)deleteNodePair.getChild2()).intValue();

               for(int i = 0; i < deletedNode.size(); ++i) {
                  RTree$RTElem elm = deletedNode.get(i);
                  if(elm.getNode() != null) {
                     elm.getNode().setParent((RTree$DirectoryNode)null);
                  }

                  this.insertNode(elm.getRect(), elm.getUserObject(), deletedLevel + (this.getLeafLevel() - origLeafLevel));
               }
            }
         }

      }
   }

   private Pair<RTree$RTreeNode, Integer> findNodeForDelete(RTree$RTreeNode node, Rect searchRect, Object userObject) {
      for(int i = 0; i < node.size(); ++i) {
         RTree$RTElem elem = node.get(i);
         if(elem.getRect().intersects(searchRect)) {
            if(node.isLeaf()) {
               if(elem.getRect().equals(searchRect) && elem.getUserObject().equals(userObject)) {
                  return new Pair(node, Integer.valueOf(i));
               }
            } else {
               Pair result = this.findNodeForDelete(elem.getNode(), searchRect, userObject);
               if(result != null) {
                  return result;
               }
            }
         }
      }

      return null;
   }

   public Collection<T> find(Rect rect, Collection<T> result) {
      if(result == null) {
         result = new ArrayList();
      }

      this.find(this.mRoot, rect, (Collection)result);
      return (Collection)result;
   }

   private void find(RTree$RTreeNode<T> node, Rect searchRect, Collection<T> result) {
      for(int i = 0; i < node.size(); ++i) {
         RTree$RTElem<T> elem = node.get(i);
         if(elem.getRect().intersects(searchRect)) {
            if(node.isLeaf()) {
               result.add(elem.getUserObject());
            } else {
               this.find(elem.getNode(), searchRect, result);
            }
         }
      }

   }

   private RTree$RTreeNode findInsertNode(RTree$RTreeNode node, int currentLevel, Rect searchRect, int searchLevel) {
      if((searchLevel != -1 || !node.isLeaf()) && currentLevel != searchLevel) {
         RTree$RTElem bestElem = null;
         long bestEnlargement = 0L;
         long bestArea = 0L;

         for(int i = 0; i < node.size(); ++i) {
            RTree$RTElem elem = node.get(i);
            if(bestElem == null) {
               bestElem = elem;
               bestArea = elem.getRect().area();
               bestEnlargement = elem.getRect().inside(searchRect)?0L:elem.getRect().areaOfMBR(searchRect, this.mInsWorkRect);
            } else {
               long enlargement = elem.getRect().inside(searchRect)?0L:elem.getRect().areaOfMBR(searchRect, this.mInsWorkRect);
               long area = elem.getRect().area();
               if(enlargement < bestEnlargement || enlargement == bestEnlargement && area < bestArea) {
                  bestElem = elem;
                  bestEnlargement = enlargement;
                  bestArea = area;
               }
            }
         }

         if(bestElem != null) {
            return this.findInsertNode(bestElem.getNode(), currentLevel + 1, searchRect, searchLevel);
         } else {
            return node;
         }
      } else {
         return node;
      }
   }

   public void displayTree(PrintStream ps) {
      this.mRoot.displayTree(ps);
   }

   public Iterator<T> interator() {
      return new RTree$Iter(this);
   }

   public static void main(String[] args) {
      testInsertDelete();
   }

   private static void tests() {
      RTree rtree = new RTree(4);
      rtree.add(new Rect(0, 0, 10, 10));
      rtree.add(new Rect(100, 100, 150, 150));
      rtree.add(new Rect(500, 500, 600, 600));
      rtree.add(new Rect(650, 650, 700, 700));
      rtree.add(new Rect(750, 750, 800, 800));
      rtree.add(new Rect(850, 850, 900, 900));
      rtree.add(new Rect(950, 950, 1000, 1000));
      rtree.add(new Rect(350, 350, 450, 450));
      rtree.add(new Rect(150, 150, 200, 200));
      rtree.add(new Rect(100, 100, 600, 600));
      rtree.add(new Rect(0, 0, 0, 0));
      rtree.add(new Rect(10000, 10000, 10010, 10010));
      rtree.add(new Rect(50, 50, 60, 60));
      rtree.add(new Rect(890, 890, 900, 900));
      rtree.displayTree(System.out);
      Iterator iter = rtree.interator();

      while(iter.hasNext()) {
         System.out.println("Iter gives:" + (String)iter.next());
      }

   }

   private static void testInsertDelete() {
      long t = System.currentTimeMillis();
      RTree testRTree = new RTree(10);
      ArrayList rects = new ArrayList();
      int seq = 0;
      Random rGen = new Random(System.currentTimeMillis());
      boolean interSize = true;
      boolean rectPosRange = true;
      boolean rectSizeRange = true;
      ArrayList result = new ArrayList();

      int i;
      for(i = 0; i < 10000; ++i) {
         int pair = rGen.nextInt(10000);
         int i$ = rGen.nextInt(10000);
         Rect entry = new Rect(i$, pair, i$ + rGen.nextInt(100), pair + rGen.nextInt(100));
         Pair searchResults = new Pair(entry, Integer.valueOf(seq++));
         rects.add(searchResults);
         testRTree.add((Rect)searchResults.getChild1(), searchResults.getChild2());
         if(i % 500 == 0) {
            System.out.println("Count:" + i + " Rect:" + entry);
            Iterator i$1 = rects.iterator();

            while(i$1.hasNext()) {
               Pair entry1 = (Pair)i$1.next();
               result.clear();
               Collection searchResults1 = testRTree.find((Rect)entry1.getChild1(), result);
               if(!searchResults1.contains(entry1.getChild2())) {
                  throw new IllegalStateException("Value " + entry1.getChild2() + " not found in rtree");
               }
            }
         }
      }

      System.out.println("Insert 10000 time in millis:" + (System.currentTimeMillis() - t));

      for(i = 0; i < 10000; ++i) {
         Pair var18 = (Pair)rects.get(0);
         rects.remove(0);
         if(!testRTree.remove((Rect)var18.getChild1(), var18.getChild2())) {
            throw new IllegalStateException("Failed to remove:" + var18);
         }

         if(i % 500 == 0) {
            System.out.println("Count:" + i + " Rect:" + var18.getChild1());
            Iterator var19 = rects.iterator();

            while(var19.hasNext()) {
               Pair var20 = (Pair)var19.next();
               result.clear();
               Collection var21 = testRTree.find((Rect)var20.getChild1(), result);
               if(!var21.contains(var20.getChild2())) {
                  throw new IllegalStateException("Value " + var20.getChild2() + " not found in rtree");
               }
            }
         }
      }

      System.out.println("Delete 10000 time in millis:" + (System.currentTimeMillis() - t));
      testRTree.displayTree(System.out);
   }

   protected static void os(int c, PrintStream ps) {
      for(int i = 0; i < c * 4; ++i) {
         ps.print(' ');
      }

   }

   private static void testTrue(boolean b) {
      if(!b) {
         throw new IllegalStateException("Its fecking false");
      }
   }

   private int getLeafLevel() {
      return this.mLeafLevel;
   }

   private int getMinimumElementCount() {
      return this.mMaxNodeSize / 2;
   }

   private static int getNextId() {
      return sId++;
   }

   // $FF: synthetic method
   static int accessMethod000() {
      return getNextId();
   }

   // $FF: synthetic method
   static void accessMethod100(boolean x0) {
      testTrue(x0);
   }

   // $FF: synthetic method
   static RTree$RTreeNode accessMethod200(RTree x0) {
      return x0.mRoot;
   }

   public static class Rect implements Serializable {

	   public int mStartColumn;
	   public int mStartRow;
	   public int mEndColumn;
	   public int mEndRow;


	   public Rect() {}

	   public Rect(int startColumn, int startRow) {
	      this.mStartColumn = this.mEndColumn = startColumn;
	      this.mStartRow = this.mEndRow = startRow;
	   }

	   public Rect(int startColumn, int startRow, int endColumn, int endRow) {
	      this.mStartColumn = Math.min(startColumn, endColumn);
	      this.mStartRow = Math.min(startRow, endRow);
	      this.mEndColumn = Math.max(startColumn, endColumn);
	      this.mEndRow = Math.max(startRow, endRow);
	   }

	   public Rect(Rect src) {
	      this.mStartColumn = src.mStartColumn;
	      this.mStartRow = src.mStartRow;
	      this.mEndColumn = src.mEndColumn;
	      this.mEndRow = src.mEndRow;
	   }

	   public boolean intersects(Rect other) {
	      return other.mStartColumn <= this.mEndColumn && other.mEndColumn >= this.mStartColumn && other.mStartRow <= this.mEndRow && other.mEndRow >= this.mStartRow;
	   }

	   public void set(Rect other) {
	      this.mStartColumn = other.mStartColumn;
	      this.mEndColumn = other.mEndColumn;
	      this.mStartRow = other.mStartRow;
	      this.mEndRow = other.mEndRow;
	   }

	   public long area() {
	      return (long)((this.mEndColumn - this.mStartColumn + 1) * (this.mEndRow - this.mStartRow + 1));
	   }

	   public static Rect mbr(Rect r1, Rect r2, Rect r3) {
	      r3.mStartColumn = Math.min(r1.mStartColumn, r2.mStartColumn);
	      r3.mStartRow = Math.min(r1.mStartRow, r2.mStartRow);
	      r3.mEndColumn = Math.max(r1.mEndColumn, r2.mEndColumn);
	      r3.mEndRow = Math.max(r1.mEndRow, r2.mEndRow);
	      return r3;
	   }

	   public boolean inside(Rect other) {
	      return this.mStartColumn <= other.mStartColumn && other.mEndColumn <= this.mEndColumn && this.mStartRow <= other.mStartRow && other.mEndRow <= this.mEndRow;
	   }

	   public static Rect intersection(Rect r1, Rect r2, Rect r3) {
	      r3.mStartColumn = Math.max(r1.mStartColumn, r2.mStartColumn);
	      r3.mStartRow = Math.max(r1.mStartRow, r2.mStartRow);
	      r3.mEndColumn = Math.min(r1.mEndColumn, r2.mEndColumn);
	      r3.mEndRow = Math.min(r1.mEndRow, r2.mEndRow);
	      return r3;
	   }

	   public long areaOfMBR(Rect other, Rect workRect) {
	      return mbr(this, other, workRect).area();
	   }

	   public void enlarge(Rect other) {
	      this.mStartColumn = Math.min(this.mStartColumn, other.mStartColumn);
	      this.mEndColumn = Math.max(this.mEndColumn, other.mEndColumn);
	      this.mStartRow = Math.min(this.mStartRow, other.mStartRow);
	      this.mEndRow = Math.max(this.mEndRow, other.mEndRow);
	   }

	   public boolean equals(Object obj) {
	      if(obj == this) {
	         return true;
	      } else if(!(obj instanceof RTree.Rect)) {
	         return super.equals(obj);
	      } else {
	         Rect other = (Rect)obj;
	         return this.mStartColumn == other.mStartColumn && this.mEndColumn == other.mEndColumn && this.mStartRow == other.mStartRow && this.mEndRow == other.mEndRow;
	      }
	   }

	   public String toString() {
	      StringBuilder sb = (new StringBuilder()).append("[sC:").append(this.mStartColumn).append(" sR:").append(this.mStartRow).append(" eC:").append(this.mEndColumn).append(" eR:").append(this.mEndRow).append(']');
	      return sb.toString();
	   }

	   public int getStartColumn() {
	      return this.mStartColumn;
	   }

	   public void setStartColumn(int startColumn) {
	      this.mStartColumn = startColumn;
	   }

	   public int getStartRow() {
	      return this.mStartRow;
	   }

	   public void setStartRow(int startRow) {
	      this.mStartRow = startRow;
	   }

	   public int getEndColumn() {
	      return this.mEndColumn;
	   }

	   public void setEndColumn(int endColumn) {
	      this.mEndColumn = endColumn;
	   }

	   public int getEndRow() {
	      return this.mEndRow;
	   }

	   public void setEndRow(int endRow) {
	      this.mEndRow = endRow;
	   }
	}
}
