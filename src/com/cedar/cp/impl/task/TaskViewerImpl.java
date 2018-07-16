// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.task;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskViewer;
import com.cedar.cp.ejb.api.task.TaskProcessServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import java.sql.Timestamp;
import java.util.List;

public class TaskViewerImpl implements TaskViewer {

   private BusinessProcessImpl mProcess;
   private int mTaskId;
   private String mTaskName;
   private int mOriginalTaskId;
   private String mUser;
   private int mStatus;
   private String mServiceStep;
   private Exception mException;
   private List mExclusiveAccessList;
   private Timestamp mCreateDate;
   private List mEvents;


   public TaskViewerImpl(BusinessProcessImpl process, int TaskId, String taskName, int OriginalTaskId, String user, int status, String serviceStep, Exception exception, List eal, Timestamp createDate) {
      this.mProcess = process;
      this.mTaskId = TaskId;
      this.mTaskName = taskName;
      this.mOriginalTaskId = OriginalTaskId;
      this.mUser = user;
      this.mStatus = status;
      this.mServiceStep = serviceStep;
      this.mException = exception;
      this.mExclusiveAccessList = eal;
      this.mCreateDate = createDate;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public String getTaskName() {
      return this.mTaskName;
   }

   public int getOriginalTaskId() {
      return this.mOriginalTaskId;
   }

   public String getUser() {
      return this.mUser;
   }

   public List getEvents() {
      if(this.mEvents == null) {
         ;
      }

      return this.mEvents;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public String getServiceStep() {
      return this.mServiceStep;
   }

   public Exception getException() {
      return this.mException;
   }

   public Timestamp getCreateDate() {
      return this.mCreateDate;
   }

   public void refresh() {
      this.mEvents = null;
   }

   public EntityList getTaskEvents() {
      try {
         TaskProcessServer e = new TaskProcessServer(this.mProcess.getConnection());
         return e.getTaskEvents(this.mTaskId);
      } catch (Exception var2) {
         throw new RuntimeException("exception in getTasks", var2);
      }
   }

   public List getExclusiveAccessList() {
      return this.mExclusiveAccessList;
   }

   public BusinessSession getBusinessSession() {
      return null;
   }

   public boolean isContentModified() {
      return false;
   }

   public void commit() throws ValidationException {}
}
