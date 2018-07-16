// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import java.io.Serializable;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class HierarchyImpl implements Hierarchy, Serializable, Cloneable {

   private boolean mChangeManagementRequestsPending;
   private boolean mSubmitChangeManagementRequest;
   private Integer mExternalSystemRef;
   private transient TreeModel mTreeModel;
   private HierarchyElementImpl mRoot;
   private int mHierarchyId;
   private ModelRefImpl mModel;
   private Object mPrimaryKey;
   private int mDimensionId;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private DimensionRef mDimensionRef;


   public HierarchyImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mDimensionId = 0;
      this.mVisId = "";
      this.mDescription = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (HierarchyPK)paramKey;
   }

   public void setPrimaryKey(HierarchyCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public DimensionRef getDimensionRef() {
      return this.mDimensionRef;
   }

   public void setDimensionRef(DimensionRef ref) {
      this.mDimensionRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setDimensionId(int paramDimensionId) {
      this.mDimensionId = paramDimensionId;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public HierarchyNode findElement(Object id) {
      if(id instanceof EntityRef) {
         id = ((EntityRef)id).getPrimaryKey();
      }

      return this.mRoot.findElement(id);
   }

   public TreeModel getTreeModel() {
      if(this.mTreeModel == null) {
         this.mTreeModel = new DefaultTreeModel(this.getRoot());
      }

      return this.mTreeModel;
   }

   public HierarchyNode getRoot() {
      return this.mRoot;
   }

   public void setRoot(HierarchyElementImpl root) {
      this.mRoot = root;
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public void setHierarchyId(int hierarchyId) {
      this.mHierarchyId = hierarchyId;
   }

   public ModelRef getModel() {
      return this.mModel;
   }

   public void setModel(ModelRefImpl model) {
      this.mModel = model;
   }

   public Integer getExternalSystemRef() {
      return this.mExternalSystemRef;
   }

   public void setExternalSystemRef(Integer externalSystemRef) {
      this.mExternalSystemRef = externalSystemRef;
   }

   public void setSubmitChangeManagementRequest(boolean b) {
      this.mSubmitChangeManagementRequest = b;
   }

   public boolean isSubmitChangeManagementRequest() {
      return this.mSubmitChangeManagementRequest;
   }

   public boolean isChangeManagementRequestsPending() {
      return this.mChangeManagementRequestsPending;
   }

   public void setChangeManagementRequestsPending(boolean changeManagementRequestsPending) {
      this.mChangeManagementRequestsPending = changeManagementRequestsPending;
   }

   public HierarchyNode findElement(String visId) {
      return this.mRoot.findElement(visId);
   }

   public boolean isNew() {
      return this.mPrimaryKey == null || this.mPrimaryKey instanceof HierarchyPK && ((HierarchyPK)this.mPrimaryKey).getHierarchyId() < 0 || this.mPrimaryKey instanceof HierarchyCK && ((HierarchyCK)this.mPrimaryKey).getHierarchyPK().getHierarchyId() < 0;
   }
}
