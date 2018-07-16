// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.api.task.group.TaskGroupRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllTaskGroupsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"TaskGroup", "TgRow", "TgRowParam"};
   private transient TaskGroupRef mTaskGroupEntityRef;
   private transient String mDescription;
   private transient Timestamp mLastSubmit;


   public AllTaskGroupsELO() {
      super(new String[]{"TaskGroup", "Description", "LastSubmit"});
   }

   public void add(TaskGroupRef eRefTaskGroup, String col1, Timestamp col2) {
      ArrayList l = new ArrayList();
      l.add(eRefTaskGroup);
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
      this.mTaskGroupEntityRef = (TaskGroupRef)l.get(index);
      this.mDescription = (String)l.get(var4++);
      this.mLastSubmit = (Timestamp)l.get(var4++);
   }

   public TaskGroupRef getTaskGroupEntityRef() {
      return this.mTaskGroupEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
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
