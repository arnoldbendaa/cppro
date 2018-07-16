// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.api.role.Role;
import com.cedar.cp.dto.role.RolePK;
import java.io.Serializable;
import javax.swing.tree.TreeNode;

public class RoleImpl implements Role, Serializable, Cloneable {

   private TreeNode mTreeNode;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;


   public RoleImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (RolePK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public TreeNode getTreeRoot() {
      return this.mTreeNode;
   }

   public void setTreeNode(TreeNode treeNode) {
      this.mTreeNode = treeNode;
   }
}
