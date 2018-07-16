// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellAuditDetailELO extends AbstractELO implements Serializable {

   private transient String mDate;
   private transient String mTime;
   private transient String mUserId;
   private transient String mUserName;
   private transient Object mOriginal;
   private transient Object mChange;
   private transient Object mCurrent;


   public CellAuditDetailELO() {
      super(new String[]{"Date", "Time", "UserId", "UserName", "Original", "Change", "Current"});
   }

   public void add(String date, String time, String userId, String userName, Object original, Object change, Object current) {
      ArrayList l = new ArrayList();
      l.add(date);
      l.add(time);
      l.add(userId);
      l.add(userName);
      l.add(original);
      l.add(change);
      l.add(current);
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
      this.mDate = l.get(index).toString();
      this.mTime = l.get(var4++).toString();
      this.mUserId = l.get(var4++).toString();
      this.mUserName = l.get(var4++).toString();
      this.mOriginal = l.get(var4++);
      this.mChange = l.get(var4++);
      this.mCurrent = l.get(var4);
   }

   public String getDate() {
      return this.mDate;
   }

   public String getTime() {
      return this.mTime;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public String getUserName() {
      return this.mUserName;
   }

   public Object getOriginal() {
      return this.mOriginal;
   }

   public Object getChange() {
      return this.mChange;
   }

   public Object getCurrent() {
      return this.mCurrent;
   }
}
