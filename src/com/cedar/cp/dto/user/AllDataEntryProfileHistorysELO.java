// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
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

public class AllDataEntryProfileHistorysELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataEntryProfileHistory", "DataEntryProfile", "User"};
   private transient DataEntryProfileHistoryRef mDataEntryProfileHistoryEntityRef;
   private transient DataEntryProfileRef mDataEntryProfileEntityRef;
   private transient UserRef mUserEntityRef;


   public AllDataEntryProfileHistorysELO() {
      super(new String[]{"DataEntryProfileHistory", "DataEntryProfile", "User"});
   }

   public void add(DataEntryProfileHistoryRef eRefDataEntryProfileHistory, DataEntryProfileRef eRefDataEntryProfile, UserRef eRefUser) {
      ArrayList l = new ArrayList();
      l.add(eRefDataEntryProfileHistory);
      l.add(eRefDataEntryProfile);
      l.add(eRefUser);
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
      this.mDataEntryProfileHistoryEntityRef = (DataEntryProfileHistoryRef)l.get(index);
      this.mDataEntryProfileEntityRef = (DataEntryProfileRef)l.get(var4++);
      this.mUserEntityRef = (UserRef)l.get(var4++);
   }

   public DataEntryProfileHistoryRef getDataEntryProfileHistoryEntityRef() {
      return this.mDataEntryProfileHistoryEntityRef;
   }

   public DataEntryProfileRef getDataEntryProfileEntityRef() {
      return this.mDataEntryProfileEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
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
