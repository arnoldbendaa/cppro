// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.external;

import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
import com.cedar.cp.api.report.destination.external.ExternalDestinationUsersRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllUsersForExternalDestinationIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ExternalDestination", "ExternalDestinationUsers", "ExternalDestinationUsers"};
   private transient ExternalDestinationRef mExternalDestinationEntityRef;
   private transient ExternalDestinationUsersRef mExternalDestinationUsersEntityRef;
   private transient String mDescription;
   private transient String mEmailAddress;


   public AllUsersForExternalDestinationIdELO() {
      super(new String[]{"ExternalDestination", "ExternalDestinationUsers", "Description", "EmailAddress"});
   }

   public void add(ExternalDestinationRef eRefExternalDestination, ExternalDestinationUsersRef eRefExternalDestinationUsers, String col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefExternalDestination);
      l.add(eRefExternalDestinationUsers);
      l.add(col1);
      l.add(col2);
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
      this.mExternalDestinationEntityRef = (ExternalDestinationRef)l.get(index);
      this.mExternalDestinationUsersEntityRef = (ExternalDestinationUsersRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mEmailAddress = (String)l.get(var4++);
   }

   public ExternalDestinationRef getExternalDestinationEntityRef() {
      return this.mExternalDestinationEntityRef;
   }

   public ExternalDestinationUsersRef getExternalDestinationUsersEntityRef() {
      return this.mExternalDestinationUsersEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getEmailAddress() {
      return this.mEmailAddress;
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
