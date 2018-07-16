// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.user.UserRoleRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllRolesForUsersELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"UserRole", "User"};
   private transient UserRoleRef mUserRoleEntityRef;
   private transient UserRef mUserEntityRef;
   private transient int mRoleId;


   public AllRolesForUsersELO() {
      super(new String[]{"UserRole", "User", "RoleId"});
   }

   public void add(UserRoleRef eRefUserRole, UserRef eRefUser, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefUserRole);
      l.add(eRefUser);
      l.add(new Integer(col1));
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
      this.mUserRoleEntityRef = (UserRoleRef)l.get(index);
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mRoleId = ((Integer)l.get(var4++)).intValue();
   }

   public UserRoleRef getUserRoleEntityRef() {
      return this.mUserRoleEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public int getRoleId() {
      return this.mRoleId;
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
