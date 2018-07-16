// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.role;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.role.RoleSecuritySelection;
import java.io.Serializable;

public class RoleSecuritySelectionImpl implements RoleSecuritySelection, Serializable {

   private boolean mSelected;
   private EntityRef mRoleSecurity;


   public RoleSecuritySelectionImpl() {}

   public RoleSecuritySelectionImpl(boolean selected, EntityRef roleSecurity) {
      this.mSelected = selected;
      this.mRoleSecurity = roleSecurity;
   }

   public void setSelected(boolean selected) {
      this.mSelected = selected;
   }

   public void setRoleSecurity(EntityRef roleSecurity) {
      this.mRoleSecurity = roleSecurity;
   }

   public EntityRef getRoleSecurity() {
      return this.mRoleSecurity;
   }

   public boolean isSelected() {
      return this.mSelected;
   }

   public String toString() {
      String[] actions = this.mRoleSecurity.toString().split("\\.");
      return actions != null && actions.length > 1?actions[1]:this.mRoleSecurity.toString();
   }
}
