// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserPreferenceRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserPreferencesForUserELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"UserPreference", "User"};
   private transient UserPreferenceRef mUserPreferenceEntityRef;
   private transient UserRef mUserEntityRef;
   private transient int mUserId;
   private transient String mPrefName;
   private transient String mPrefValue;
   private transient int mPrefType;
   private transient String mHelpId;


   public UserPreferencesForUserELO() {
      super(new String[]{"UserPreference", "User", "UserId", "PrefName", "PrefValue", "PrefType", "HelpId"});
   }

   public void add(UserPreferenceRef eRefUserPreference, UserRef eRefUser, int col1, String col2, String col3, int col4, String col5) {
      ArrayList l = new ArrayList();
      l.add(eRefUserPreference);
      l.add(eRefUser);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(new Integer(col4));
      l.add(col5);
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
      this.mUserPreferenceEntityRef = (UserPreferenceRef)l.get(index);
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
      this.mPrefName = (String)l.get(var4++);
      this.mPrefValue = (String)l.get(var4++);
      this.mPrefType = ((Integer)l.get(var4++)).intValue();
      this.mHelpId = (String)l.get(var4++);
   }

   public UserPreferenceRef getUserPreferenceEntityRef() {
      return this.mUserPreferenceEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getPrefName() {
      return this.mPrefName;
   }

   public String getPrefValue() {
      return this.mPrefValue;
   }

   public int getPrefType() {
      return this.mPrefType;
   }

   public String getHelpId() {
      return this.mHelpId;
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
