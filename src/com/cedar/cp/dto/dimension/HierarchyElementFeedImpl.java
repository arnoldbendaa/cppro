// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.api.dimension.HierarchyElementFeed;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedRefImpl;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

public class HierarchyElementFeedImpl implements HierarchyElementFeed, Serializable {

   private HierarchyElementImpl mParent;
   private Object mPrimaryKey;
   private int mChildIndex;
   private String mVisId;
   private String mDescription;
   private boolean mDisabled;
   private int mCreditDebit;
   private int mAugCreditDebit;
   private HierarchyImpl mHierarchy;


   public HierarchyElementFeedImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mChildIndex = 0;
      this.mCreditDebit = 2;
      this.mAugCreditDebit = 0;
      this.mDisabled = false;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (HierarchyElementPK)paramKey;
   }

   public void setPrimaryKey(HierarchyElementFeedCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setChildIndex(int paramChildIndex) {
      this.mChildIndex = paramChildIndex;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public void setCreditDebit(int creditDebit) {
      this.mCreditDebit = creditDebit;
   }

   public TreeNode getChildAt(int childIndex) {
      return null;
   }

   public int getChildCount() {
      return 0;
   }

   public TreeNode getParent() {
      return this.mParent;
   }

   public int getIndex(TreeNode node) {
      return -1;
   }

   public boolean getAllowsChildren() {
      return false;
   }

   public boolean isLeaf() {
      return true;
   }

   public Enumeration children() {
      return Collections.enumeration(Collections.EMPTY_LIST);
   }

   public boolean isDisabled() {
      return this.mDisabled;
   }

   public void setParent(HierarchyElement parent) {
      this.mParent = (HierarchyElementImpl)parent;
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

   public void setParent(HierarchyElementImpl parent) {
      this.mParent = parent;
   }

   public void setDisabled(boolean disabled) {
      this.mDisabled = disabled;
   }

   public EntityRef getEntityRef() {
      return this.mPrimaryKey instanceof HierarchyElementFeedPK?new HierarchyElementFeedRefImpl((HierarchyElementFeedPK)this.mPrimaryKey, this.mVisId):(this.mPrimaryKey instanceof HierarchyElementFeedCK?new HierarchyElementFeedRefImpl((HierarchyElementFeedCK)this.mPrimaryKey, this.mVisId):null);
   }

   public boolean isAugmentElement() {
      return false;
   }

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public void setAugCreditDebit(int augCreditDebit) {
      this.mAugCreditDebit = augCreditDebit;
   }
}
