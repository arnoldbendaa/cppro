// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetUserDetailsELO extends AbstractELO implements Serializable {

   private transient Integer mUserId;
   private transient String mName;
   private transient String mFullName;
   private transient String mEmail;


   public BudgetUserDetailsELO() {
      super(new String[]{"UserId", "Name", "FullName", "EMailAddress"});
   }

   public void add(int userId, String name, String fullName, String email) {
      ArrayList l = new ArrayList();
      l.add(new Integer(userId));
      l.add(name);
      l.add(fullName);
      l.add(email);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.mCurrRowIndex = -1;
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mUserId = (Integer)l.get(index);
      this.mName = (String)l.get(var4++);
      this.mFullName = (String)l.get(var4++);
      this.mEmail = (String)l.get(var4++);
   }
}
