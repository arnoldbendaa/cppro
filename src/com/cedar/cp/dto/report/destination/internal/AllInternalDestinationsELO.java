// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.internal;

import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllInternalDestinationsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"InternalDestination", "InternalDestinationUsers"};
   private transient InternalDestinationRef mInternalDestinationEntityRef;
   private transient String mDescription;


   public AllInternalDestinationsELO() {
      super(new String[]{"InternalDestination", "Description"});
   }

   public void add(InternalDestinationRef eRefInternalDestination, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefInternalDestination);
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
      this.mInternalDestinationEntityRef = (InternalDestinationRef)l.get(index);
      this.mDescription = (String)l.get(var4++);
   }

   public InternalDestinationRef getInternalDestinationEntityRef() {
      return this.mInternalDestinationEntityRef;
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
