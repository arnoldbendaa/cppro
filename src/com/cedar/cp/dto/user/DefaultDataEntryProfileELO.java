// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.DataEntryProfileHistoryRef;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefaultDataEntryProfileELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataEntryProfile", "User", "DataEntryProfileHistory", "DataEntryProfileHistory", "XmlForm"};
   private transient DataEntryProfileRef mDataEntryProfileEntityRef;
   private transient UserRef mUserEntityRef;
   private transient DataEntryProfileHistoryRef mDataEntryProfileHistoryEntityRef;
   private transient String mDescription;


   public DefaultDataEntryProfileELO() {
      super(new String[]{"DataEntryProfile", "User", "DataEntryProfileHistory", "Description"});
   }

   public void add(DataEntryProfileRef eRefDataEntryProfile, UserRef eRefUser, DataEntryProfileHistoryRef eRefDataEntryProfileHistory, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefDataEntryProfile);
      l.add(eRefUser);
      l.add(eRefDataEntryProfileHistory);
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
      int var4 = index + 1;
      this.mDataEntryProfileEntityRef = (DataEntryProfileRef)l.get(index);
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mDataEntryProfileHistoryEntityRef = (DataEntryProfileHistoryRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public DataEntryProfileRef getDataEntryProfileEntityRef() {
      return this.mDataEntryProfileEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public DataEntryProfileHistoryRef getDataEntryProfileHistoryEntityRef() {
      return this.mDataEntryProfileHistoryEntityRef;
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
