// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.internal;

import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.api.report.destination.internal.InternalDestinationUsersRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckInternalDestinationUsersELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"InternalDestinationUsers", "InternalDestination"};
   private transient InternalDestinationUsersRef mInternalDestinationUsersEntityRef;
   private transient InternalDestinationRef mInternalDestinationEntityRef;
   private transient int mUserId;


   public CheckInternalDestinationUsersELO() {
      super(new String[]{"InternalDestinationUsers", "InternalDestination", "UserId"});
   }

   public void add(InternalDestinationUsersRef eRefInternalDestinationUsers, InternalDestinationRef eRefInternalDestination, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefInternalDestinationUsers);
      l.add(eRefInternalDestination);
      l.add(new Integer(col1));
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
      this.mInternalDestinationUsersEntityRef = (InternalDestinationUsersRef)l.get(index);
      this.mInternalDestinationEntityRef = (InternalDestinationRef)l.get(var4++);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
   }

   public InternalDestinationUsersRef getInternalDestinationUsersEntityRef() {
      return this.mInternalDestinationUsersEntityRef;
   }

   public InternalDestinationRef getInternalDestinationEntityRef() {
      return this.mInternalDestinationEntityRef;
   }

   public int getUserId() {
      return this.mUserId;
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
