// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.role;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.role.Role;
import com.cedar.cp.dto.role.AllSecurityRolesForRoleELO;
import com.cedar.cp.dto.role.RoleImpl;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.impl.role.RoleEditorSessionImpl;
import com.cedar.cp.impl.role.RoleSecuritySelectionImpl;
import java.util.HashSet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class RoleAdapter implements Role {

   private RoleImpl mEditorData;
   private RoleEditorSessionImpl mEditorSessionImpl;


   public RoleAdapter(RoleEditorSessionImpl e, RoleImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected RoleEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected RoleImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(RolePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public TreeNode getTreeRoot() {
      if(this.mEditorData.getTreeRoot() == null) {
         DefaultMutableTreeNode root = new DefaultMutableTreeNode("Security Strings");
         CPConnection conn = this.mEditorSessionImpl.getConnection();
         EntityList available = conn.getListHelper().getAllSecurityRoles();
         Object selected = null;
         if(this.mEditorData.getPrimaryKey() == null) {
            selected = new AllSecurityRolesForRoleELO();
         } else {
            selected = conn.getListHelper().getAllSecurityRolesForRole(((RolePK)this.mEditorData.getPrimaryKey()).getRoleId());
         }

         int size = ((EntityList)selected).getNumRows();
         HashSet selectedList = new HashSet(size);

         for(int ref = 0; ref < size; ++ref) {
            selectedList.add(((EntityList)selected).getValueAt(ref, "RoleSecurity"));
         }

         size = available.getNumRows();
         EntityRef var15 = null;
         String[] securityAction = null;
         String oldProcess = null;
         DefaultMutableTreeNode group = null;
         RoleSecuritySelectionImpl actionObject = null;
         DefaultMutableTreeNode action = null;

         for(int i = 0; i < size; ++i) {
            var15 = (EntityRef)available.getValueAt(i, "RoleSecurity");
            securityAction = var15.getNarrative().split("\\.");
            String process = securityAction[0];
            if(oldProcess == null || !oldProcess.equals(process)) {
               group = new DefaultMutableTreeNode(process);
               root.add(group);
            }

            actionObject = new RoleSecuritySelectionImpl();
            actionObject.setRoleSecurity(var15);
            if(selectedList.contains(var15)) {
               actionObject.setSelected(true);
            } else {
               actionObject.setSelected(false);
            }

            action = new DefaultMutableTreeNode(actionObject);
            group.add(action);
            oldProcess = process;
         }

         this.mEditorData.setTreeNode(root);
      }

      return this.mEditorData.getTreeRoot();
   }
}
