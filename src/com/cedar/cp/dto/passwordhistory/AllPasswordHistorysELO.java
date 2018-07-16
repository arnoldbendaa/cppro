// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.passwordhistory;

import com.cedar.cp.api.passwordhistory.PasswordHistoryRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllPasswordHistorysELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"PasswordHistory"};
   private transient PasswordHistoryRef mPasswordHistoryEntityRef;
   private transient int mUserId;
   private transient String mPasswordBytes;
   private transient Timestamp mPasswordDate;


   public AllPasswordHistorysELO() {
      super(new String[]{"PasswordHistory", "UserId", "PasswordBytes", "PasswordDate"});
   }

   public void add(PasswordHistoryRef eRefPasswordHistory, int col1, String col2, Timestamp col3) {
      ArrayList l = new ArrayList();
      l.add(eRefPasswordHistory);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
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
      this.mPasswordHistoryEntityRef = (PasswordHistoryRef)l.get(index);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
      this.mPasswordBytes = (String)l.get(var4++);
      this.mPasswordDate = (Timestamp)l.get(var4++);
   }

   public PasswordHistoryRef getPasswordHistoryEntityRef() {
      return this.mPasswordHistoryEntityRef;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getPasswordBytes() {
      return this.mPasswordBytes;
   }

   public Timestamp getPasswordDate() {
      return this.mPasswordDate;
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
