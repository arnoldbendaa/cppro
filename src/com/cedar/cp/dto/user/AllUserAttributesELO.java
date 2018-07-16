// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllUserAttributesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "VirementRequest"};
   private transient UserRef mUserEntityRef;
   private transient String mFullName;
   private transient String mEMailAddress;
   private transient String mPasswordBytes;
   private transient Timestamp mPasswordDate;
   private transient boolean mChangePassword;
   private transient boolean mUserDisabled;
   private transient boolean mPasswordNeverExpires;


   public AllUserAttributesELO() {
      super(new String[]{"User", "FullName", "EMailAddress", "PasswordBytes", "PasswordDate", "ChangePassword", "UserDisabled", "PasswordNeverExpires"});
   }

   public void add(UserRef eRefUser, String col1, String col2, String col3, Timestamp col4, boolean col5, boolean col6, boolean col7) {
      ArrayList l = new ArrayList();
      l.add(eRefUser);
      l.add(col1);
      l.add(col2);
      l.add(col3);
      l.add(col4);
      l.add(new Boolean(col5));
      l.add(new Boolean(col6));
      l.add(new Boolean(col7));
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
      this.mEMailAddress = (String)l.get(var4++);
      this.mPasswordBytes = (String)l.get(var4++);
      this.mPasswordDate = (Timestamp)l.get(var4++);
      this.mChangePassword = ((Boolean)l.get(var4++)).booleanValue();
      this.mUserDisabled = ((Boolean)l.get(var4++)).booleanValue();
      this.mPasswordNeverExpires = ((Boolean)l.get(var4++)).booleanValue();
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public String getEMailAddress() {
      return this.mEMailAddress;
   }

   public String getPasswordBytes() {
      return this.mPasswordBytes;
   }

   public Timestamp getPasswordDate() {
      return this.mPasswordDate;
   }

   public boolean getChangePassword() {
      return this.mChangePassword;
   }

   public boolean getUserDisabled() {
      return this.mUserDisabled;
   }

   public boolean getPasswordNeverExpires() {
      return this.mPasswordNeverExpires;
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
