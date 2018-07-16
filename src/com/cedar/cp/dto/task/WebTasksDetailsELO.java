// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task;

import com.cedar.cp.api.task.TaskRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WebTasksDetailsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Task", "User"};
   private transient TaskRef mTaskEntityRef;
   private transient UserRef mUserEntityRef;
   private transient int mTaskId;
   private transient String mTaskName;
   private transient int mUserId;
   private transient String mName;
   private transient int mOriginalTaskId;
   private transient int mStatus;
   private transient Timestamp mCreateDate;
   private transient Timestamp mEndDate;
   private transient String mStep;


   public WebTasksDetailsELO() {
      super(new String[]{"Task", "User", "TaskId", "TaskName", "UserId", "Name", "OriginalTaskId", "Status", "CreateDate", "EndDate", "Step"});
   }

   public void add(TaskRef eRefTask, UserRef eRefUser, int col1, String col2, int col3, String col4, int col5, int col6, Timestamp col7, Timestamp col8, String col9) {
      ArrayList l = new ArrayList();
      l.add(eRefTask);
      l.add(eRefUser);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Integer(col3));
      l.add(col4);
      l.add(new Integer(col5));
      l.add(new Integer(col6));
      l.add(col7);
      l.add(col8);
      l.add(col9);
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
      this.mTaskEntityRef = (TaskRef)l.get(index);
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mTaskId = ((Integer)l.get(var4++)).intValue();
      this.mTaskName = (String)l.get(var4++);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
      this.mName = (String)l.get(var4++);
      this.mOriginalTaskId = ((Integer)l.get(var4++)).intValue();
      this.mStatus = ((Integer)l.get(var4++)).intValue();
      this.mCreateDate = (Timestamp)l.get(var4++);
      this.mEndDate = (Timestamp)l.get(var4++);
      this.mStep = (String)l.get(var4++);
   }

   public TaskRef getTaskEntityRef() {
      return this.mTaskEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public String getTaskName() {
      return this.mTaskName;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getName() {
      return this.mName;
   }

   public int getOriginalTaskId() {
      return this.mOriginalTaskId;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public Timestamp getCreateDate() {
      return this.mCreateDate;
   }

   public Timestamp getEndDate() {
      return this.mEndDate;
   }

   public String getStep() {
      return this.mStep;
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
