// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


import com.cedar.cp.util.Pair;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MTree<T extends Object> implements Serializable {

   private int mLeafLevel;
   private static int sId = 0;
   private Region mInsWorkRect;
   private Region mPickSeedsWorkRect1;
   private Region mPickSeedsWorkRect2;
   private Region mPickNextWorkRect;
   private Region mSplitNodeLeftWorkRect;
   private Region mSplitNodeRightWorkRect;
   private int[] mSplitNodeResultArray = new int[]{0, 0};
   private List<RTElem> mSplitNodeAllElems = new ArrayList(30);
   private int mNumDims;
   private int mMaxNodeSize;
   private RTreeNode mRoot = new ValueNode();


   private boolean isNodeFull(RTreeNode node) {
      return node.size() >= this.mMaxNodeSize;
   }

   public MTree(int numDims, int maxNodeSize) {
      this.mNumDims = numDims;
      this.mMaxNodeSize = maxNodeSize;
      this.initWorkingStorage();
   }

   private int[] pickSeeds(List<RTElem> elems, int[] result) {
      long bestRating = 0L;

      for(int i = 0; i < elems.size(); ++i) {
         RTElem e1 = (RTElem)elems.get(i);

         for(int j = 0; j < elems.size(); ++j) {
            if(j != i) {
               RTElem e2 = (RTElem)elems.get(j);
               this.mPickSeedsWorkRect1.set(e1.getRect());
               this.mPickSeedsWorkRect1.enlarge(e2.getRect());
               long rating = this.mPickSeedsWorkRect1.area() - e1.getRect().area() - e2.getRect().area();
               if(e1.getRect().intersects(e2.getRect())) {
                  rating += Region.intersection(e1.getRect(), e2.getRect(), this.mPickSeedsWorkRect2).area();
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

   private int pickNext(List<RTElem> elems, Region r1, Region r2) {
      long biggestDiff = -1L;
      int result = -1;

      for(int i = 0; i < elems.size(); ++i) {
         RTElem elem = (RTElem)elems.get(i);
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

   private void add(Region region) {
      this.add(region, region.toString());
   }

   public void add(Region region, Object o) {
      this.insertNode(region, o, -1);
   }

   private void insertNode(Region region, Object o, int level) {
      RTreeNode startNode = this.findInsertNode(this.mRoot, 0, region, level);
      RTreeNode newNode = null;
      if(!this.isNodeFull(startNode)) {
         Object newElem;
         if(!startNode.isLeaf()) {
            newElem = new DirElem(region, (RTreeNode)o);
            ((RTreeNode)o).setParent((DirectoryNode)startNode);
         } else {
            newElem = new LeafElem(region, o);
         }

         startNode.add((RTElem)newElem);
      } else {
         newNode = this.splitNode(startNode, region, o);
      }

      this.adjustTree(startNode, newNode);
   }

   private RTreeNode splitNode(RTreeNode<T> targetNode, Region region, Object o) {
      List allElems = this.mSplitNodeAllElems;
      this.mSplitNodeAllElems.clear();
      allElems.addAll((List)targetNode);
      allElems.add(o instanceof RTreeNode?new DirElem(region, (RTreeNode)o):new LeafElem(region, o));
      int[] seedIdx = this.pickSeeds(allElems, this.mSplitNodeResultArray);
      targetNode.clear();
      Region leftRect = new Region(((RTElem)allElems.get(seedIdx[0])).getRect());
      RTreeNode leftNode = targetNode;
      RTElem leftSeedElem = (RTElem)allElems.get(seedIdx[0]);
      targetNode.add(leftSeedElem);
      if(!targetNode.isLeaf()) {
         leftSeedElem.getNode().setParent((DirectoryNode)targetNode);
      }

      Region rightRect = new Region(((RTElem)allElems.get(seedIdx[1])).getRect());
      Object rightNode = targetNode.isLeaf()?new ValueNode():new DirectoryNode();
      RTElem rightSeedElem = (RTElem)allElems.get(seedIdx[1]);
      ((RTreeNode)rightNode).add(rightSeedElem);
      if(!((RTreeNode)rightNode).isLeaf()) {
         rightSeedElem.getNode().setParent((DirectoryNode)rightNode);
      }

      allElems.remove(Math.max(seedIdx[0], seedIdx[1]));
      allElems.remove(Math.min(seedIdx[0], seedIdx[1]));

      int targetIdx;
      for(; !allElems.isEmpty(); allElems.remove(targetIdx)) {
         RTElem targetElem;
         Iterator targetIdx1;
         if(this.getMinimumElementCount() - leftNode.size() == allElems.size()) {
            for(targetIdx1 = allElems.iterator(); targetIdx1.hasNext(); leftRect.enlarge(targetElem.getRect())) {
               targetElem = (RTElem)targetIdx1.next();
               leftNode.add(targetElem);
               if(!leftNode.isLeaf()) {
                  targetElem.getNode().setParent((DirectoryNode)leftNode);
               }
            }

            return (RTreeNode)rightNode;
         }

         if(this.getMinimumElementCount() - ((RTreeNode)rightNode).size() == allElems.size()) {
            for(targetIdx1 = allElems.iterator(); targetIdx1.hasNext(); rightRect.enlarge(targetElem.getRect())) {
               targetElem = (RTElem)targetIdx1.next();
               ((RTreeNode)rightNode).add(targetElem);
               if(!((RTreeNode)rightNode).isLeaf()) {
                  targetElem.getNode().setParent((DirectoryNode)rightNode);
               }
            }

            return (RTreeNode)rightNode;
         }

         targetIdx = allElems.size() > 1?this.pickNext(allElems, leftRect, rightRect):0;
         targetElem = (RTElem)allElems.get(targetIdx);
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
               targetElem.getNode().setParent((DirectoryNode)leftNode);
            }
         } else {
            rightRect.enlarge(this.mSplitNodeRightWorkRect);
            ((RTreeNode)rightNode).add(targetElem);
            if(!((RTreeNode)rightNode).isLeaf()) {
               targetElem.getNode().setParent((DirectoryNode)rightNode);
            }
         }
      }

      return (RTreeNode)rightNode;
   }

   private void adjustTree(RTreeNode leftNode, RTreeNode rightNode) {
      DirectoryNode parentNode = leftNode.getParent();
      if(parentNode != null) {
         Region leftNodeMBR = leftNode.calcMBR(this.mNumDims);
         int leftIndexInParent = parentNode.indexOfNode(leftNode);
         RTElem elem = (RTElem)parentNode.get(leftIndexInParent);
         elem.getRect().set(leftNodeMBR);
         RTreeNode newNode = null;
         if(rightNode != null) {
            Region rightNodeMBR = rightNode.calcMBR(this.mNumDims);
            if(this.isNodeFull(parentNode)) {
               newNode = this.splitNode(parentNode, rightNodeMBR, rightNode);
            } else {
               rightNode.setParent(parentNode);
               parentNode.add(new DirElem(rightNodeMBR, rightNode));
            }
         }

         this.adjustTree(parentNode, newNode);
      } else if(rightNode != null) {
         this.mRoot = parentNode = new DirectoryNode();
         parentNode.add(new DirElem(leftNode.calcMBR(this.mNumDims), leftNode));
         parentNode.add(new DirElem(rightNode.calcMBR(this.mNumDims), rightNode));
         leftNode.setParent(parentNode);
         rightNode.setParent(parentNode);
         ++this.mLeafLevel;
      }

   }

   public boolean remove(Region region) {
      return this.remove(region, region.toString());
   }

   public boolean remove(Region region, Object userObject) {
      Pair leafNodePair = this.findNodeForDelete(this.mRoot, region, userObject);
      if(leafNodePair == null) {
         return false;
      } else {
         ((RTreeNode)leafNodePair.getChild1()).remove(((Integer)leafNodePair.getChild2()).intValue());
         this.condenseTree((RTreeNode)leafNodePair.getChild1());
         if(this.mRoot.size() == 1 && !this.mRoot.isLeaf()) {
            this.mRoot = this.mRoot.get(0).getNode();
            this.mRoot.setParent((DirectoryNode)null);
            --this.mLeafLevel;
         }

         return true;
      }
   }

   private void condenseTree(RTreeNode node) {
      if(this.mRoot != node) {
         ArrayList deletedNodes = null;

         int origLeafLevel;
         for(DirectoryNode parentNode = ((RTreeNode)node).getParent(); node != this.mRoot; parentNode = parentNode.getParent()) {
            origLeafLevel = parentNode.indexOfNode((RTreeNode)node);
            RTElem i$ = (RTElem)parentNode.get(origLeafLevel);
            if(((RTreeNode)node).size() < this.getMinimumElementCount()) {
               parentNode.remove(i$);
               if(deletedNodes == null) {
                  deletedNodes = new ArrayList();
               }

               deletedNodes.add(new Pair(node, Integer.valueOf(((RTreeNode)node).getLevel())));
            } else {
               ((RTreeNode)node).calcMBR(i$.getRect());
            }

            node = parentNode;
         }

         if(deletedNodes != null) {
            origLeafLevel = this.getLeafLevel();
            Iterator var11 = deletedNodes.iterator();

            while(var11.hasNext()) {
               Pair deleteNodePair = (Pair)var11.next();
               RTreeNode deletedNode = (RTreeNode)deleteNodePair.getChild1();
               int deletedLevel = ((Integer)deleteNodePair.getChild2()).intValue();

               for(int i = 0; i < deletedNode.size(); ++i) {
                  RTElem elm = deletedNode.get(i);
                  if(elm.getNode() != null) {
                     elm.getNode().setParent((DirectoryNode)null);
                  }

                  this.insertNode(elm.getRect(), elm.getUserObject(), deletedLevel + (this.getLeafLevel() - origLeafLevel));
               }
            }
         }

      }
   }

   private Pair<RTreeNode, Integer> findNodeForDelete(RTreeNode node, Region searchRect, Object userObject) {
      for(int i = 0; i < node.size(); ++i) {
         RTElem elem = node.get(i);
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

   private void findExact(RTreeNode node, Region searchRect, Collection<T> results) {
      for(int i = 0; i < node.size(); ++i) {
         RTElem<T> elem = node.get(i);
         if(elem.getRect().intersects(searchRect)) {
            if(node.isLeaf()) {
               if(elem.getRect().equals(searchRect)) {
                  results.add(elem.getUserObject());
               }
            } else {
               this.findExact(elem.getNode(), searchRect, results);
            }
         }
      }

   }

   public void findExact(Region region, Collection<T> result) {
      if(result == null) {
         result = new ArrayList();
      }

      this.findExact(this.mRoot, region, (Collection)result);
   }

   public Collection<T> find(Region region, Collection<T> result) {
      if(result == null) {
         result = new ArrayList();
      }

      this.find(this.mRoot, region, (Collection)result);
      return (Collection)result;
   }

   private void find(RTreeNode<T> node, Region searchRect, Collection<T> result) {
      for(int i = 0; i < node.size(); ++i) {
         RTElem<T> elem = node.get(i);
         if(elem.getRect().intersects(searchRect)) {
            if(node.isLeaf()) {
               result.add(elem.getUserObject());
            } else {
               this.find(elem.getNode(), searchRect, result);
            }
         }
      }

   }

   private RTreeNode findInsertNode(RTreeNode node, int currentLevel, Region searchRect, int searchLevel) {
      if((searchLevel != -1 || !node.isLeaf()) && currentLevel != searchLevel) {
         RTElem bestElem = null;
         long bestEnlargement = 0L;
         long bestArea = 0L;

         for(int i = 0; i < node.size(); ++i) {
            RTElem elem = node.get(i);
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

   public boolean isEmpty() {
      return this.mRoot.isLeaf() && this.mRoot.size() == 0;
   }

   public void displayTree(PrintStream ps) {
      this.mRoot.displayTree(ps);
   }

   public Iterator<T> interator() {
      return new Iter();
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

   private void initWorkingStorage() {
      this.mInsWorkRect = new Region(this.mNumDims);
      this.mPickSeedsWorkRect1 = new Region(this.mNumDims);
      this.mPickSeedsWorkRect2 = new Region(this.mNumDims);
      this.mPickNextWorkRect = new Region(this.mNumDims);
      this.mSplitNodeLeftWorkRect = new Region(this.mNumDims);
      this.mSplitNodeRightWorkRect = new Region(this.mNumDims);
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
   static RTreeNode accessMethod200(MTree x0) {
      return x0.mRoot;
   }
   
   /*      */   class Iter
   /*      */     implements Iterator<T>
   /*      */   {
   /*      */     private RTreeNode<T> mNode;
   /*      */     private int mIndex;
   /*      */ 
   /*      */     Iter()
   /*      */     {
   /* 1142 */       this.mNode = MTree.this.mRoot;
   /* 1143 */       while ((this.mNode != null) && (!this.mNode.isLeaf()))
   /* 1144 */         this.mNode = this.mNode.get(0).getNode();
   /*      */     }
   /*      */ 
   /*      */     public boolean hasNext()
   /*      */     {
   /* 1149 */       return (this.mNode != null) && (this.mIndex < this.mNode.size());
   /*      */     }
   /*      */ 
   /*      */     public T next()
   /*      */     {
   /* 1154 */       T result = this.mNode.get(this.mIndex).getUserObject();
   /* 1155 */       this.mIndex += 1;
   /* 1156 */       if (this.mIndex >= this.mNode.size())
   /*      */       {
   /* 1158 */         this.mNode = this.mNode.getNextSibling();
   /* 1159 */         this.mIndex = 0;
   /*      */       }
   /* 1161 */       return result;
   /*      */     }
   /*      */ 
   /*      */     public void remove()
   /*      */     {
   /* 1166 */       throw new UnsupportedOperationException("Remove is not supported in the rtree interator");
   /*      */     }
   /*      */   }
   /*      */ 
   /*      */   class ValueNode extends ArrayList<MTree.LeafElem<T>>
   /*      */     implements MTree.RTreeNode<T>
   /*      */   {
   /*      */     private int mId;
   /*      */     private MTree.DirectoryNode mParent;
   /*      */ 
   /*      */     public ValueNode()
   /*      */     {
   /*  420 */       this.mId = MTree.accessMethod000();
   /*      */     }
   /*      */ 
   /*      */     public boolean isLeaf()
   /*      */     {
   /*  425 */       return true;
   /*      */     }
   /*      */ 
   /*      */     public MTree.DirectoryNode getParent()
   /*      */     {
   /*  430 */       return this.mParent;
   /*      */     }
   /*      */ 
   /*      */     public void setParent(MTree.DirectoryNode parent)
   /*      */     {
   /*  435 */       this.mParent = parent;
   /*      */     }
   /*      */ 
   /*      */     public int indexOfNode(MTree.RTreeNode<T> node)
   /*      */     {
   /*  440 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  442 */         MTree.RTElem rtElem = (MTree.RTElem)get(i);
   /*  443 */         if (rtElem.getNode() == node)
   /*      */         {
   /*  445 */           return i;
   /*      */         }
   /*      */       }
   /*  448 */       return -1;
   /*      */     }
   /*      */ 
   /*      */     public boolean add(MTree.RTElem<T> elem)
   /*      */     {
   /*  453 */       return super.add((MTree.LeafElem)elem);
   /*      */     }
   /*      */ 
   /*      */     public void enlarge(MTree.Region region)
   /*      */     {
   /*  458 */       if (getParent() != null)
   /*      */       {
   /*  460 */         MTree.DirectoryNode parent = getParent();
   /*  461 */         int indexInParent = parent.indexOfNode(this);
   /*  462 */         MTree.RTElem elem = (MTree.RTElem)parent.get(indexInParent);
   /*  463 */         elem.getRect().enlarge(region);
   /*      */       }
   /*      */     }
   /*      */ 
   /*      */     public MTree.Region calcMBR(int dims)
   /*      */     {
   /*  469 */       MTree.Region mbr = new MTree.Region(dims);
   /*  470 */       mbr.set(((MTree.LeafElem)get(0)).getRect());
   /*  471 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  473 */         mbr.enlarge(((MTree.LeafElem)get(i)).getRect());
   /*      */       }
   /*  475 */       return mbr;
   /*      */     }
   /*      */ 
   /*      */     public MTree.Region calcMBR(MTree.Region result)
   /*      */     {
   /*  480 */       result.set(((MTree.LeafElem)get(0)).getRect());
   /*  481 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  483 */         result.enlarge(((MTree.LeafElem)get(i)).getRect());
   /*      */       }
   /*  485 */       return result;
   /*      */     }
   /*      */ 
   /*      */     public void displayTree(PrintStream ps)
   /*      */     {
   /*  490 */       if (getParent() != null)
   /*      */       {
   /*  492 */         MTree.accessMethod100(getParent().indexOfNode(this) != -1);
   /*      */       }
   /*      */ 
   /*  495 */       MTree.os(getLevel(), ps);
   /*  496 */       ps.println("ValueNode Id:" + getId());
   /*  497 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  499 */         MTree.os(getLevel(), ps);
   /*  500 */         ((MTree.LeafElem)get(i)).displayTree(ps);
   /*  501 */         ps.println();
   /*      */       }
   /*  503 */       ps.println();
   /*      */     }
   /*      */     public int getLevel() {
   /*  506 */       return this.mParent != null ? this.mParent.getLevel() + 1 : 0;
   /*      */     }
   /*  508 */     public int getId() { return this.mId; }
   /*      */ 
   /*      */     public MTree.RTreeNode getNextSibling()
   /*      */     {
   /*  512 */       if (getParent() != null)
   /*      */       {
   /*  514 */         MTree.RTreeNode parent = getParent();
   /*  515 */         int index = parent.indexOfNode(this);
   /*  516 */         if (index < parent.size() - 1) {
   /*  517 */           return parent.get(index + 1).getNode();
   /*      */         }
   /*      */ 
   /*  520 */         MTree.RTreeNode parentSibling = parent.getNextSibling();
   /*  521 */         return parentSibling != null ? parentSibling.get(0).getNode() : null;
   /*      */       }
   /*      */ 
   /*  524 */       return null;
   /*      */     }
   /*      */   }
   /*      */ 
   /*      */   static class DirectoryNode extends ArrayList<MTree.DirElem>
   /*      */     implements MTree.RTreeNode
   /*      */   {
   /*      */     private DirectoryNode mParent;
   /*      */     private int mId;
   /*      */ 
   /*      */     public DirectoryNode()
   /*      */     {
   /*  300 */       this.mId = MTree.accessMethod000();
   /*      */     }
   /*      */ 
   /*      */     public boolean isLeaf()
   /*      */     {
   /*  305 */       return false;
   /*      */     }
   /*      */ 
   /*      */     public DirectoryNode getParent()
   /*      */     {
   /*  310 */       return this.mParent;
   /*      */     }
   /*      */ 
   /*      */     public void setParent(DirectoryNode parent)
   /*      */     {
   /*  315 */       this.mParent = parent;
   /*      */     }
   /*      */ 
   /*      */     public int indexOfNode(MTree.RTreeNode node)
   /*      */     {
   /*  320 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  322 */         MTree.RTElem rtElem = (MTree.RTElem)get(i);
   /*  323 */         if (rtElem.getNode() == node)
   /*      */         {
   /*  325 */           return i;
   /*      */         }
   /*      */       }
   /*  328 */       return -1;
   /*      */     }
   /*      */ 
   /*      */     public boolean add(MTree.RTElem elem)
   /*      */     {
   /*  333 */       return super.add((MTree.DirElem)elem);
   /*      */     }
   /*      */ 
   /*      */     public void enlarge(MTree.Region region)
   /*      */     {
   /*  338 */       if (getParent() != null)
   /*      */       {
   /*  340 */         DirectoryNode parent = getParent();
   /*  341 */         int indexInParent = parent.indexOfNode(this);
   /*  342 */         MTree.RTElem elem = (MTree.RTElem)parent.get(indexInParent);
   /*  343 */         elem.getRect().enlarge(region);
   /*      */       }
   /*      */     }
   /*      */ 
   /*      */     public MTree.Region calcMBR(int dims)
   /*      */     {
   /*  349 */       MTree.Region mbr = new MTree.Region(dims);
   /*  350 */       mbr.set(((MTree.DirElem)get(0)).getRect());
   /*  351 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  353 */         mbr.enlarge(((MTree.DirElem)get(i)).getRect());
   /*      */       }
   /*  355 */       return mbr;
   /*      */     }
   /*      */ 
   /*      */     public MTree.Region calcMBR(MTree.Region result)
   /*      */     {
   /*  360 */       result.set(((MTree.DirElem)get(0)).getRect());
   /*  361 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  363 */         result.enlarge(((MTree.DirElem)get(i)).getRect());
   /*      */       }
   /*  365 */       return result;
   /*      */     }
   /*      */ 
   /*      */     public void displayTree(PrintStream ps)
   /*      */     {
   /*  370 */       if (getParent() != null)
   /*      */       {
   /*  372 */         MTree.accessMethod100(getParent().indexOfNode(this) != -1);
   /*      */       }
   /*  374 */       MTree.os(getLevel(), ps);
   /*  375 */       ps.println("DirectoryNode Id:" + getId());
   /*  376 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  378 */         MTree.os(getLevel(), ps);
   /*  379 */         ((MTree.DirElem)get(i)).displayTree(ps);
   /*  380 */         ps.println();
   /*      */       }
   /*  382 */       ps.println();
   /*      */ 
   /*  384 */       for (int i = 0; i < size(); i++)
   /*      */       {
   /*  386 */         ((MTree.DirElem)get(i)).getNode().displayTree(ps);
   /*      */       }
   /*      */     }
   /*      */ 
   /*      */     public int getLevel() {
   /*  390 */       return this.mParent != null ? this.mParent.getLevel() + 1 : 0;
   /*      */     }
   /*  392 */     public int getId() { return this.mId; }
   /*      */ 
   /*      */     public MTree.RTreeNode getNextSibling()
   /*      */     {
   /*  396 */       if (getParent() != null)
   /*      */       {
   /*  398 */         MTree.RTreeNode parent = getParent();
   /*  399 */         int index = parent.indexOfNode(this);
   /*  400 */         if (index < parent.size() - 1) {
   /*  401 */           return parent.get(index + 1).getNode();
   /*      */         }
   /*      */ 
   /*  404 */         MTree.RTreeNode parentSibling = parent.getNextSibling();
   /*  405 */         return parentSibling != null ? parentSibling.get(0).getNode() : null;
   /*      */       }
   /*      */ 
   /*  408 */       return null;
   /*      */     }
   /*      */   }
   /*      */ 
   /*      */   static class DirElem extends MTree.LeafElem<MTree.RTreeNode>
   /*      */   {
   /*      */     public DirElem(MTree.Region region, MTree.RTreeNode node)
   /*      */     {
   /*  281 */       super(region, node);
   /*      */       long x;
   /*  283 */       if (node == null)
   /*      */       {
   /*  285 */         x = region.area();
   /*      */       }
   /*      */     }
   /*      */ 
   /*      */     public MTree.RTreeNode getNode()
   /*      */     {
   /*  291 */       return (MTree.RTreeNode)this.mObj;
   /*      */     }
   /*      */   }
   /*      */ 
   /*      */   static class LeafElem<T>
   /*      */     implements MTree.RTElem<T>
   /*      */   {
   /*      */     protected MTree.Region mRect;
   /*      */     protected T mObj;
   /*      */ 
   /*      */     public LeafElem(MTree.Region region, T obj)
   /*      */     {
   /*  247 */       this.mRect = region;
   /*  248 */       this.mObj = obj;
   /*      */     }
   /*      */ 
   /*      */     public MTree.Region getRect()
   /*      */     {
   /*  253 */       return this.mRect;
   /*      */     }
   /*      */ 
   /*      */     public MTree.RTreeNode getNode()
   /*      */     {
   /*  258 */       return null;
   /*      */     }
   /*      */ 
   /*      */     public T getUserObject()
   /*      */     {
   /*  263 */       return this.mObj;
   /*      */     }
   /*      */ 
   /*      */     public void displayTree(PrintStream pw)
   /*      */     {
   /*  268 */       pw.print("Region:" + this.mRect);
   /*  269 */       pw.print(" - User Obj:" + this.mObj);
   /*      */     }
   /*      */   }
   /*      */ 
   /*      */   static abstract interface RTreeNode<T> extends Serializable
   /*      */   {
   /*      */     public abstract int size();
   /*      */ 
   /*      */     public abstract MTree.RTElem<T> get(int paramInt);
   /*      */ 
   /*      */     public abstract boolean add(MTree.RTElem<T> paramRTElem);
   /*      */ 
   /*      */     public abstract int indexOfNode(RTreeNode<T> paramRTreeNode);
   /*      */ 
   /*      */     public abstract boolean isLeaf();
   /*      */ 
   /*      */     public abstract MTree.DirectoryNode getParent();
   /*      */ 
   /*      */     public abstract void setParent(MTree.DirectoryNode paramDirectoryNode);
   /*      */ 
   /*      */     public abstract void clear();
   /*      */ 
   /*      */     public abstract void enlarge(MTree.Region paramRegion);
   /*      */ 
   /*      */     public abstract MTree.Region calcMBR(int paramInt);
   /*      */ 
   /*      */     public abstract MTree.Region calcMBR(MTree.Region paramRegion);
   /*      */ 
   /*      */     public abstract void displayTree(PrintStream paramPrintStream);
   /*      */ 
   /*      */     public abstract Object remove(int paramInt);
   /*      */ 
   /*      */     public abstract int getLevel();
   /*      */ 
   /*      */     public abstract int getId();
   /*      */ 
   /*      */     public abstract RTreeNode getNextSibling();
   /*      */   }
   /*      */ 
   /*      */   static abstract interface RTElem<T> extends Serializable
   /*      */   {
   /*      */     public abstract MTree.Region getRect();
   /*      */ 
   /*      */     public abstract MTree.RTreeNode<T> getNode();
   /*      */ 
   /*      */     public abstract T getUserObject();
   /*      */ 
   /*      */     public abstract void displayTree(PrintStream paramPrintStream);
   /*      */   }
   /*      */ 
   /*      */   public static class Region
   /*      */     implements Serializable
   /*      */   {
   /*      */     private Interval[] mIntervals;
   /*      */ 
   /*      */     public Region(int size)
   /*      */     {
   /*   22 */       this.mIntervals = new Interval[size];
   /*   23 */       for (int i = 0; i < size; i++)
   /*   24 */         this.mIntervals[i] = new Interval(0, 0);
   /*      */     }
   /*      */ 
   /*      */     public Region(int start, int end)
   /*      */     {
   /*   34 */       this(1);
   /*   35 */       this.mIntervals[0].setStart(Math.min(start, end));
   /*   36 */       this.mIntervals[0].setEnd(Math.max(start, end));
   /*      */     }
   /*      */ 
   /*      */     public Region(int startColumn, int startRow, int endColumn, int endRow)
   /*      */     {
   /*   42 */       this(2);
   /*   43 */       this.mIntervals[0].setStart(Math.min(startColumn, endColumn));
   /*   44 */       this.mIntervals[0].setEnd(Math.max(startColumn, endColumn));
   /*      */ 
   /*   46 */       this.mIntervals[1].setStart(Math.min(startRow, endRow));
   /*   47 */       this.mIntervals[1].setEnd(Math.max(startRow, endRow));
   /*      */     }
   /*      */ 
   /*      */     public Region(Region src)
   /*      */     {
   /*   52 */       this(src.mIntervals.length);
   /*   53 */       for (int i = 0; i < this.mIntervals.length; i++)
   /*   54 */         this.mIntervals[i].set(src.mIntervals[i]);
   /*      */     }
   /*      */ 
   /*      */     public boolean intersects(Region other)
   /*      */     {
   /*   64 */       int size = Math.min(this.mIntervals.length, other.mIntervals.length);
   /*   65 */       for (int i = 0; i < size; i++)
   /*      */       {
   /*   67 */         if (!this.mIntervals[i].intersects(other.mIntervals[i]))
   /*   68 */           return false;
   /*      */       }
   /*   70 */       return true;
   /*      */     }
   /*      */ 
   /*      */     public void set(Region other)
   /*      */     {
   /*   79 */       int size = Math.min(this.mIntervals.length, other.mIntervals.length);
   /*   80 */       for (int i = 0; i < size; i++)
   /*   81 */         this.mIntervals[i].set(other.mIntervals[i]);
   /*      */     }
   /*      */ 
   /*      */     public long area()
   /*      */     {
   /*   87 */       long area = 1L;
   /*   88 */       for (int i = 0; i < this.mIntervals.length; i++)
   /*   89 */         area *= (this.mIntervals[i].size() + 1);
   /*   90 */       return area;
   /*      */     }
   /*      */ 
   /*      */     public static Region mbr(Region r1, Region r2, Region r3)
   /*      */     {
   /*  102 */       int size = Math.min(r1.mIntervals.length, Math.min(r2.mIntervals.length, r3.mIntervals.length));
   /*  103 */       for (int i = 0; i < size; i++)
   /*      */       {
   /*  105 */         Interval i1 = r1.mIntervals[i];
   /*  106 */         Interval i2 = r2.mIntervals[i];
   /*  107 */         Interval i3 = r3.mIntervals[i];
   /*  108 */         i3.setStart(Math.min(i1.getStart(), i2.getStart()));
   /*  109 */         i3.setEnd(Math.max(i1.getEnd(), i2.getEnd()));
   /*      */       }
   /*  111 */       return r3;
   /*      */     }
   /*      */ 
   /*      */     public boolean inside(Region other)
   /*      */     {
   /*  121 */       int size = Math.min(this.mIntervals.length, other.mIntervals.length);
   /*  122 */       for (int i = 0; i < size; i++)
   /*      */       {
   /*  124 */         if (!this.mIntervals[i].inside(other.mIntervals[i]))
   /*  125 */           return false;
   /*      */       }
   /*  127 */       return true;
   /*      */     }
   /*      */ 
   /*      */     public static Region intersection(Region r1, Region r2, Region r3)
   /*      */     {
   /*  139 */       int size = Math.min(r1.mIntervals.length, Math.min(r2.mIntervals.length, r3.mIntervals.length));
   /*  140 */       for (int i = 0; i < size; i++)
   /*      */       {
   /*  142 */         Interval i1 = r1.mIntervals[i];
   /*  143 */         Interval i2 = r2.mIntervals[i];
   /*  144 */         Interval i3 = r3.mIntervals[i];
   /*  145 */         i3.setStart(Math.max(i1.getStart(), i2.getStart()));
   /*  146 */         i3.setEnd(Math.min(i1.getEnd(), i2.getEnd()));
   /*      */       }
   /*  148 */       return r3;
   /*      */     }
   /*      */ 
   /*      */     public long areaOfMBR(Region other, Region workRect)
   /*      */     {
   /*  159 */       return mbr(this, other, workRect).area();
   /*      */     }
   /*      */ 
   /*      */     public void enlarge(Region other)
   /*      */     {
   /*  168 */       int size = Math.min(this.mIntervals.length, other.mIntervals.length);
   /*  169 */       for (int i = 0; i < size; i++)
   /*      */       {
   /*  171 */         Interval i1 = this.mIntervals[i];
   /*  172 */         Interval i2 = other.mIntervals[i];
   /*  173 */         i1.setStart(Math.min(i1.getStart(), i2.getStart()));
   /*  174 */         i1.setEnd(Math.max(i1.getEnd(), i2.getEnd()));
   /*      */       }
   /*      */     }
   /*      */ 
   /*      */     public boolean equals(Object obj)
   /*      */     {
   /*  180 */       if (obj == this)
   /*      */       {
   /*  182 */         return true;
   /*      */       }
   /*      */ 
   /*  185 */       if ((obj instanceof Region))
   /*      */       {
   /*  187 */         Region other = (Region)obj;
   /*  188 */         return Arrays.equals(this.mIntervals, other.mIntervals);
   /*      */       }
   /*      */ 
   /*  191 */       return super.equals(obj);
   /*      */     }
   /*      */ 
   /*      */     public String toString()
   /*      */     {
   /*  196 */       StringBuilder sb = new StringBuilder();
   /*  197 */       for (int i = 0; i < this.mIntervals.length; i++)
   /*  198 */         sb.append(" i").append(i).append("[").append(this.mIntervals[i]).append(']');
   /*  199 */       return sb.toString();
   /*      */     }
   /*      */   }

}
