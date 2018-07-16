// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecurityStringsForUserELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "RoleSecurity", "UserRole", "RoleSecurityRel", "VirementRequest"};
   private transient String mSecurityString;


   public SecurityStringsForUserELO() {
      super(new String[]{"SecurityString"});
   }

   public void add(String col1) {
      ArrayList l = new ArrayList();
      l.add(col1);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      byte var10002 = index;
      int index1 = index + 1;
      this.mSecurityString = (String)l.get(var10002);
   }

   public String getSecurityString() {
      return this.mSecurityString;
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
