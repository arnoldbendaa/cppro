// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.internal;

import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.api.report.destination.internal.InternalDestinationUsersRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllUsersForInternalDestinationIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"InternalDestination", "InternalDestinationUsers", "User", "InternalDestinationUsers"};
   private transient InternalDestinationRef mInternalDestinationEntityRef;
   private transient UserRef mUserEntityRef;
   private transient InternalDestinationUsersRef mInternalDestinationUsersEntityRef;
   private transient String mDescription;
   private transient int mMessageType;
   private transient String mName;
   private transient String mFullName;


   public AllUsersForInternalDestinationIdELO() {
      super(new String[]{"InternalDestination", "User", "InternalDestinationUsers", "Description", "MessageType", "Name", "FullName"});
   }

   public void add(InternalDestinationRef eRefInternalDestination, UserRef eRefUser, InternalDestinationUsersRef eRefInternalDestinationUsers, String col1, int col2, String col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefInternalDestination);
      l.add(eRefUser);
      l.add(eRefInternalDestinationUsers);
      l.add(col1);
      l.add(new Integer(col2));
      l.add(col3);
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
      this.mInternalDestinationEntityRef = (InternalDestinationRef)l.get(index);
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mInternalDestinationUsersEntityRef = (InternalDestinationUsersRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mMessageType = ((Integer)l.get(var4++)).intValue();
      this.mName = (String)l.get(var4++);
      this.mFullName = (String)l.get(var4++);
   }

   public InternalDestinationRef getInternalDestinationEntityRef() {
      return this.mInternalDestinationEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public InternalDestinationUsersRef getInternalDestinationUsersEntityRef() {
      return this.mInternalDestinationUsersEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getMessageType() {
      return this.mMessageType;
   }

   public String getName() {
      return this.mName;
   }

   public String getFullName() {
      return this.mFullName;
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
