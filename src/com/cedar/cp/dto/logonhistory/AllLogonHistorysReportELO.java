// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.logonhistory;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllLogonHistorysReportELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"LogonHistory"};
   private transient String mUserName;
   private transient Timestamp mEventDate;
   private transient int mEventType;


   public AllLogonHistorysReportELO() {
      super(new String[]{"UserName", "EventDate", "EventType"});
   }

   public void add(String col1, Timestamp col2, int col3) {
      ArrayList l = new ArrayList();
      l.add(col1);
      l.add(col2);
      l.add(new Integer(col3));
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
      this.mUserName = (String)l.get(index);
      this.mEventDate = (Timestamp)l.get(var4++);
      this.mEventType = ((Integer)l.get(var4++)).intValue();
   }

   public String getUserName() {
      return this.mUserName;
   }

   public Timestamp getEventDate() {
      return this.mEventDate;
   }

   public int getEventType() {
      return this.mEventType;
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
