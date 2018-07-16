// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.api.role.RoleSecurityRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSecurityRolesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"RoleSecurity"};
   private transient RoleSecurityRef mRoleSecurityEntityRef;
   private transient String mSecurityString;
   private transient String mDescription;


   public AllSecurityRolesELO() {
      super(new String[]{"RoleSecurity", "SecurityString", "Description"});
   }

   public void add(RoleSecurityRef eRefRoleSecurity, String col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefRoleSecurity);
      l.add(col1);
      l.add(col2);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mRoleSecurityEntityRef = (RoleSecurityRef)l.get(index);
      this.mSecurityString = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public RoleSecurityRef getRoleSecurityEntityRef() {
      return this.mRoleSecurityEntityRef;
   }

   public String getSecurityString() {
      return this.mSecurityString;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
