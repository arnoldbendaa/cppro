// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.api.udeflookup.UdefLookupRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllUdefLookupsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"UdefLookup", "UdefLookupColumnDef"};
   private transient UdefLookupRef mUdefLookupEntityRef;
   private transient String mVisId;
   private transient String mDescription;
   private transient boolean mAutoSubmit;
   private transient Timestamp mLastSubmit;
   private transient Timestamp mDataUpdated;


   public AllUdefLookupsELO() {
      super(new String[]{"UdefLookup", "VisId", "Description", "AutoSubmit", "LastSubmit", "DataUpdated"});
   }

   public void add(UdefLookupRef eRefUdefLookup, String col1, String col2, boolean col3, Timestamp col4, Timestamp col5) {
      ArrayList l = new ArrayList();
      l.add(eRefUdefLookup);
      l.add(col1);
      l.add(col2);
      l.add(new Boolean(col3));
      l.add(col4);
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
      this.mUdefLookupEntityRef = (UdefLookupRef)l.get(index);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mAutoSubmit = ((Boolean)l.get(var4++)).booleanValue();
      this.mLastSubmit = (Timestamp)l.get(var4++);
      this.mDataUpdated = (Timestamp)l.get(var4++);
   }

   public UdefLookupRef getUdefLookupEntityRef() {
      return this.mUdefLookupEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getAutoSubmit() {
      return this.mAutoSubmit;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
   }

   public Timestamp getDataUpdated() {
      return this.mDataUpdated;
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
