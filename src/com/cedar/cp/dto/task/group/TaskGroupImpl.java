// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.api.task.group.TaskGroup;
import com.cedar.cp.api.task.group.TaskGroupRow;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskGroupImpl implements TaskGroup, Serializable, Cloneable {

   public List<TaskGroupRow> mTGRows;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private Timestamp mLastSubmit;


   public TaskGroupImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mLastSubmit = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (TaskGroupPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setLastSubmit(Timestamp paramLastSubmit) {
      this.mLastSubmit = paramLastSubmit;
   }

   public List<TaskGroupRow> getTGRows() {
      if(this.mTGRows == null) {
         this.mTGRows = new ArrayList();
      }

      return this.mTGRows;
   }

   public void setTGRows(List<TaskGroupRow> TGRows) {
      this.mTGRows = TGRows;
   }
}
