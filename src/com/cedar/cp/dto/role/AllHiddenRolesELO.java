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

public class AllHiddenRolesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Role", "Description", "Parent"};
   private transient RoleRef mRoleEntityRef;
   private transient String mDescription;
   private transient int mParentId;


   public AllHiddenRolesELO() {
      super(new String[]{"Role", "Description", "Parent"});
   }
   
   public AllHiddenRolesELO(RoleRef eRefRole, String description, int parent) {
       super(new String[]{"Role", "Description", "Parent"}); 
       this.mRoleEntityRef = eRefRole;
       this.mDescription= description;
       this.mParentId = parent;
    }
   
   public void add(RoleRef eRefRole, String description, int parent) {
      ArrayList l = new ArrayList();
      l.add(eRefRole);
      l.add(description);
      l.add(parent);
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
      this.mDescription = (String)l.get(var4++);
      this.mParentId = (Integer)l.get(var4++);
   }

   public RoleRef getRoleEntityRef() {
      return this.mRoleEntityRef;
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

	public int getParentId() {
		return mParentId;
	}


}
