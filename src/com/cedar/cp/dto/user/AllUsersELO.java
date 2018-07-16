// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllUsersELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "VirementRequest"};
   private transient UserRef mUserEntityRef;
   private transient String mFullName;
   private transient boolean mUserDisabled;


   public AllUsersELO() {
      super(new String[]{"User", "FullName", "UserDisabled"});
   }

   public void add(UserRef eRefUser, String col1, boolean col2) {
      ArrayList l = new ArrayList();
      l.add(eRefUser);
      l.add(col1);
      l.add(new Boolean(col2));
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
      this.mUserEntityRef = (UserRef)l.get(index);
      this.mFullName = (String)l.get(var4++);
      this.mUserDisabled = ((Boolean)l.get(var4++)).booleanValue();
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public boolean getUserDisabled() {
      return this.mUserDisabled;
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
