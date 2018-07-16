// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllRolesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Role", "RoleSecurityRel"};
   private transient RoleRef mRoleEntityRef;
   private transient String mVisId;
   private transient String mDescription;


   public AllRolesELO() {
      super(new String[]{"Role", "VisId", "Description"});
   }

   public void add(RoleRef eRefRole, String col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefRole);
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
      this.mRoleEntityRef = (RoleRef)l.get(index);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public RoleRef getRoleEntityRef() {
      return this.mRoleEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
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
