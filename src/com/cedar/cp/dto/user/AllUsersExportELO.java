// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllUsersExportELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "Role", "UserRole", "VirementRequest"};
   private transient int mUserId;
   private transient String mName;
   private transient String mFullName;
   private transient String mEMailAddress;
   private transient boolean mUserDisabled;
   private transient boolean mPasswordNeverExpires;
   private transient String mExternalSystemUserName;
   private transient String mVisId;
   private transient String mLogonAlias;


   public AllUsersExportELO() {
      super(new String[]{"UserId", "Name", "FullName", "EMailAddress", "UserDisabled", "PasswordNeverExpires", "ExternalSystemUserName", "VisId", "LogonAlias"});
   }

   public void add(int col1, String col2, String col3, String col4, boolean col5, boolean col6, String col7, String col8, String col9) {
      ArrayList l = new ArrayList();
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(col4);
      l.add(new Boolean(col5));
      l.add(new Boolean(col6));
      l.add(col7);
      l.add(col8);
      l.add(col9);
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
      this.mFullName = (String)l.get(var4++);
      this.mEMailAddress = (String)l.get(var4++);
      this.mUserDisabled = ((Boolean)l.get(var4++)).booleanValue();
      this.mPasswordNeverExpires = ((Boolean)l.get(var4++)).booleanValue();
      this.mExternalSystemUserName = (String)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mLogonAlias = (String)l.get(var4++);
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getName() {
      return this.mName;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public String getEMailAddress() {
      return this.mEMailAddress;
   }

   public boolean getUserDisabled() {
      return this.mUserDisabled;
   }

   public boolean getPasswordNeverExpires() {
      return this.mPasswordNeverExpires;
   }

   public String getExternalSystemUserName() {
      return this.mExternalSystemUserName;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getLogonAlias() {
      return this.mLogonAlias;
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
