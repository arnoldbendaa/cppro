// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsersWithSecurityStringELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "RoleSecurity", "RoleSecurityRel", "Role", "UserRole", "VirementRequest"};
   private transient int mUserId;
   private transient String mName;


   public UsersWithSecurityStringELO() {
      super(new String[]{"UserId", "Name"});
   }

   public void add(int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(new Integer(col1));
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
      this.mUserId = ((Integer)l.get(index)).intValue();
      this.mName = (String)l.get(var4++);
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getName() {
      return this.mName;
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
