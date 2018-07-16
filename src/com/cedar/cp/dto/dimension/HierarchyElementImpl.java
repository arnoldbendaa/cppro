// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedImpl;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyElementRefImpl;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.TreeNode;

public class HierarchyElementImpl implements HierarchyElement, Serializable, Cloneable {

   private Object mPrimaryKey;
   private HierarchyImpl mHierarchy;
   private String mVisId;
   private String mDescription;
   private TreeNode mParent;
   private List mChildren;
   private int mCreditDebit;
   private int mAugCreditDebit;
   static final long serialVersionUID = 1L;


   public HierarchyElementImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mCreditDebit = 2;
      this.mCreditDebit = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (HierarchyElementPK)paramKey;
   }

   void setPrimaryKey(HierarchyElementCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setCreditDebit(int paramCreditDebit) {
      this.mCreditDebit = paramCreditDebit;
   }

   public TreeNode getChildAt(int childIndex) {
      return this.mChildren != null?(TreeNode)this.mChildren.get(childIndex):null;
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
      return false;
   }

   public boolean isLeaf() {
      return this.mChildren == null || this.mChildren.isEmpty();
   }

   public Enumeration children() {
      return Collections.enumeration(this.mChildren != null?this.mChildren:Collections.EMPTY_LIST);
   }

   public void setParent(TreeNode parent) {
      this.mParent = parent;
   }

   public void addChildElement(TreeNode node) {
      if(this.mChildren == null) {
         this.mChildren = new ArrayList();
      }

      this.addChildElement(this.mChildren.size(), node);
   }

   public void addChildElement(int index, TreeNode node) {
      if(this.mChildren == null) {
         this.mChildren = new ArrayList();
      }

      this.mChildren.add(index, node);
      if(node instanceof HierarchyElementImpl) {
         ((HierarchyElementImpl)node).setParent(this);
      } else if(node instanceof HierarchyElementFeedImpl) {
         ((HierarchyElementFeedImpl)node).setParent(this);
      }

   }

   public void removeChildElement(TreeNode node) {
      if(this.mChildren != null) {
         this.mChildren.remove(node);
      }

   }

   public HierarchyNode findElement(Object id) {
      if(this.mPrimaryKey.equals(id)) {
         return this;
      } else {
         if(this.mChildren != null) {
            Iterator i = this.mChildren.iterator();

            while(i.hasNext()) {
               HierarchyNode child = (HierarchyNode)i.next();
               if(child instanceof HierarchyElementImpl) {
                  if((child = ((HierarchyElementImpl)child).findElement(id)) != null) {
                     return child;
                  }
               } else if(child.getPrimaryKey().equals(id)) {
                  return child;
               }
            }
         }

         return null;
      }
   }

   public HierarchyNode findElement(String id) {
      if(this.mVisId.equals(id)) {
         return this;
      } else {
         if(this.mChildren != null) {
            Iterator i = this.mChildren.iterator();

            while(i.hasNext()) {
               HierarchyNode child = (HierarchyNode)i.next();
               if(child instanceof HierarchyElementImpl) {
                  if((child = ((HierarchyElementImpl)child).findElement(id)) != null) {
                     return child;
                  }
               } else if(child.getVisId().equals(id)) {
                  return child;
               }
            }
         }

         return null;
      }
   }

   public EntityRef getEntityRef() {
      if(this.mPrimaryKey instanceof HierarchyElementCK) {
         return new HierarchyElementRefImpl((HierarchyElementCK)this.mPrimaryKey, this.mDescription);
      } else if(this.mPrimaryKey instanceof HierarchyElementPK) {
         return new HierarchyElementRefImpl((HierarchyElementPK)this.mPrimaryKey, this.mDescription);
      } else {
         throw new IllegalStateException("Unexpected primary key type for hierarchy element:" + this.mPrimaryKey);
      }
   }

   public Hierarchy getHierarchy() {
      return this.mHierarchy;
   }

   public void setHierarchy(HierarchyImpl h) {
      this.mHierarchy = h;
   }

   public String toString() {
      return this.mVisId;
   }

   public boolean isAugmentElement() {
      return false;
   }

   protected List getChildren() {
      return this.mChildren;
   }

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public void setAugCreditDebit(int augCreditDebit) {
      this.mAugCreditDebit = augCreditDebit;
   }
}
