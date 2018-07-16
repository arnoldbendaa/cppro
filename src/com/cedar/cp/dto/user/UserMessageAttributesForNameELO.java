// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserMessageAttributesForNameELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "VirementRequest"};
   private transient UserRef mUserEntityRef;
   private transient String mName;
   private transient String mFullName;
   private transient boolean mUserDisabled;
   private transient String mEMailAddress;


   public UserMessageAttributesForNameELO() {
      super(new String[]{"User", "Name", "FullName", "UserDisabled", "EMailAddress"});
   }

   public void add(UserRef eRefUser, String col1, String col2, boolean col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefUser);
      l.add(col1);
      l.add(col2);
      l.add(new Boolean(col3));
      l.add(col4);
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
      this.mName = (String)l.get(var4++);
      this.mFullName = (String)l.get(var4++);
      this.mUserDisabled = ((Boolean)l.get(var4++)).booleanValue();
      this.mEMailAddress = (String)l.get(var4++);
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public String getName() {
      return this.mName;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public boolean getUserDisabled() {
      return this.mUserDisabled;
   }

   public String getEMailAddress() {
      return this.mEMailAddress;
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
