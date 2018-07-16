// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllUserDetailsReportELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "Role", "UserRole", "VirementRequest"};
   private transient String mName;
   private transient String mFullName;
   private transient String mEMailAddress;
   private transient boolean mUserDisabled;
   private transient String mVisId;


   public AllUserDetailsReportELO() {
      super(new String[]{"Name", "FullName", "EMailAddress", "UserDisabled", "VisId"});
   }

   public void add(String col1, String col2, String col3, boolean col4, String col5) {
      ArrayList l = new ArrayList();
      l.add(col1);
      l.add(col2);
      l.add(col3);
      l.add(new Boolean(col4));
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
      this.mName = (String)l.get(index);
      this.mFullName = (String)l.get(var4++);
      this.mEMailAddress = (String)l.get(var4++);
      this.mUserDisabled = ((Boolean)l.get(var4++)).booleanValue();
      this.mVisId = (String)l.get(var4++);
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

   public String getVisId() {
      return this.mVisId;
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
