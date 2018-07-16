// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRef;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.task.Task;
import com.cedar.cp.dto.task.TaskObjects;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.dto.task.TaskRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class TaskEVO implements Serializable {

   private transient TaskPK mPK;
   private int mTaskId;
   private int mTaskType;
   private long mSystemTimeMillis;
   private String mTaskName;
   private int mUserId;
   private int mOriginalTaskId;
   private int mStatus;
   private boolean mMustComplete;
   private byte[] mObjects;
   private Timestamp mCreateDate;
   private Timestamp mEndDate;
   private String mStep;
   private boolean mModified;
   public static final int TASK_TYPE_SIMPLE = 0;
   public static final int TASK_TYPE_GROUP = 1;
   public static final int CREATED = 0;
   public static final int RECEIVED = 1;
   public static final int DESPATCHED = 2;
   public static final int STARTED = 3;
   public static final int FAILED = 4;
   public static final int COMPLETE = 5;
   public static final int WAITING_FOR_TASK = 6;
   public static final int COMMIT_STATE_STARTED = 7;
   public static final int COMMIT_STATE_COMPLETE = 8;
   public static final int COMPLETE_WITH_EXCEPTION = 9;
   public static final int UNSAFE_DELETED = 10;


   public TaskEVO() {}

   public TaskEVO(int newTaskId, int newTaskType, long newSystemTimeMillis, String newTaskName, int newUserId, int newOriginalTaskId, int newStatus, boolean newMustComplete, byte[] newObjects, Timestamp newCreateDate, Timestamp newEndDate, String newStep) {
      this.mTaskId = newTaskId;
      this.mTaskType = newTaskType;
      this.mSystemTimeMillis = newSystemTimeMillis;
      this.mTaskName = newTaskName;
      this.mUserId = newUserId;
      this.mOriginalTaskId = newOriginalTaskId;
      this.mStatus = newStatus;
      this.mMustComplete = newMustComplete;
      this.mObjects = newObjects;
      this.mCreateDate = newCreateDate;
      this.mEndDate = newEndDate;
      this.mStep = newStep;
   }

   public TaskPK getPK() {
      if(this.mPK == null) {
         this.mPK = new TaskPK(this.mTaskId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public int getTaskType() {
      return this.mTaskType;
   }

   public long getSystemTimeMillis() {
      return this.mSystemTimeMillis;
   }

   public String getTaskName() {
      return this.mTaskName;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getOriginalTaskId() {
      return this.mOriginalTaskId;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public boolean getMustComplete() {
      return this.mMustComplete;
   }

   public byte[] getObjects() {
      return this.mObjects;
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

   public void setTaskId(int newTaskId) {
      if(this.mTaskId != newTaskId) {
         this.mModified = true;
         this.mTaskId = newTaskId;
         this.mPK = null;
      }
   }

   public void setTaskType(int newTaskType) {
      if(this.mTaskType != newTaskType) {
         this.mModified = true;
         this.mTaskType = newTaskType;
      }
   }

   public void setSystemTimeMillis(long newSystemTimeMillis) {
      if(this.mSystemTimeMillis != newSystemTimeMillis) {
         this.mModified = true;
         this.mSystemTimeMillis = newSystemTimeMillis;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
      }
   }

   public void setOriginalTaskId(int newOriginalTaskId) {
      if(this.mOriginalTaskId != newOriginalTaskId) {
         this.mModified = true;
         this.mOriginalTaskId = newOriginalTaskId;
      }
   }

   public void setStatus(int newStatus) {
      if(this.mStatus != newStatus) {
         this.mModified = true;
         this.mStatus = newStatus;
      }
   }

   public void setMustComplete(boolean newMustComplete) {
      if(this.mMustComplete != newMustComplete) {
         this.mModified = true;
         this.mMustComplete = newMustComplete;
      }
   }

   public void setTaskName(String newTaskName) {
      if(this.mTaskName != null && newTaskName == null || this.mTaskName == null && newTaskName != null || this.mTaskName != null && newTaskName != null && !this.mTaskName.equals(newTaskName)) {
         this.mTaskName = newTaskName;
         this.mModified = true;
      }

   }

   public void setObjects(byte[] newObjects) {
      if(this.mObjects != null && newObjects == null || this.mObjects == null && newObjects != null || this.mObjects != null && newObjects != null && !this.mObjects.equals(newObjects)) {
         this.mObjects = newObjects;
         this.mModified = true;
      }

   }

   public void setCreateDate(Timestamp newCreateDate) {
      if(this.mCreateDate != null && newCreateDate == null || this.mCreateDate == null && newCreateDate != null || this.mCreateDate != null && newCreateDate != null && !this.mCreateDate.equals(newCreateDate)) {
         this.mCreateDate = newCreateDate;
         this.mModified = true;
      }

   }

   public void setEndDate(Timestamp newEndDate) {
      if(this.mEndDate != null && newEndDate == null || this.mEndDate == null && newEndDate != null || this.mEndDate != null && newEndDate != null && !this.mEndDate.equals(newEndDate)) {
         this.mEndDate = newEndDate;
         this.mModified = true;
      }

   }

   public void setStep(String newStep) {
      if(this.mStep != null && newStep == null || this.mStep == null && newStep != null || this.mStep != null && newStep != null && !this.mStep.equals(newStep)) {
         this.mStep = newStep;
         this.mModified = true;
      }

   }

   public void setDetails(TaskEVO newDetails) {
      this.setTaskId(newDetails.getTaskId());
      this.setTaskType(newDetails.getTaskType());
      this.setSystemTimeMillis(newDetails.getSystemTimeMillis());
      this.setTaskName(newDetails.getTaskName());
      this.setUserId(newDetails.getUserId());
      this.setOriginalTaskId(newDetails.getOriginalTaskId());
      this.setStatus(newDetails.getStatus());
      this.setMustComplete(newDetails.getMustComplete());
      this.setObjects(newDetails.getObjects());
      this.setCreateDate(newDetails.getCreateDate());
      this.setEndDate(newDetails.getEndDate());
      this.setStep(newDetails.getStep());
   }

   public TaskEVO deepClone() {
      TaskEVO cloned = new TaskEVO();
      cloned.mModified = this.mModified;
      cloned.mTaskId = this.mTaskId;
      cloned.mTaskType = this.mTaskType;
      cloned.mSystemTimeMillis = this.mSystemTimeMillis;
      cloned.mUserId = this.mUserId;
      cloned.mOriginalTaskId = this.mOriginalTaskId;
      cloned.mStatus = this.mStatus;
      cloned.mMustComplete = this.mMustComplete;
      if(this.mTaskName != null) {
         cloned.mTaskName = this.mTaskName;
      }

      if(this.mObjects != null) {
         cloned.mObjects = new byte[this.mObjects.length];
         int i = -1;

         try {
            while(true) {
               ++i;
               cloned.mObjects[i] = this.mObjects[i];
            }
         } catch (ArrayIndexOutOfBoundsException var4) {
            ;
         }
      }

      if(this.mCreateDate != null) {
         cloned.mCreateDate = Timestamp.valueOf(this.mCreateDate.toString());
      }

      if(this.mEndDate != null) {
         cloned.mEndDate = Timestamp.valueOf(this.mEndDate.toString());
      }

      if(this.mStep != null) {
         cloned.mStep = this.mStep;
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(int startKey) {
      return startKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public TaskRef getEntityRef() {
      return new TaskRefImpl(this.getPK(), this.mTaskName);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("TaskId=");
      sb.append(String.valueOf(this.mTaskId));
      sb.append(' ');
      sb.append("TaskType=");
      sb.append(String.valueOf(this.mTaskType));
      sb.append(' ');
      sb.append("SystemTimeMillis=");
      sb.append(String.valueOf(this.mSystemTimeMillis));
      sb.append(' ');
      sb.append("TaskName=");
      sb.append(String.valueOf(this.mTaskName));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("OriginalTaskId=");
      sb.append(String.valueOf(this.mOriginalTaskId));
      sb.append(' ');
      sb.append("Status=");
      sb.append(String.valueOf(this.mStatus));
      sb.append(' ');
      sb.append("MustComplete=");
      sb.append(String.valueOf(this.mMustComplete));
      sb.append(' ');
      sb.append("Objects=");
      sb.append(String.valueOf(this.mObjects));
      sb.append(' ');
      sb.append("CreateDate=");
      sb.append(String.valueOf(this.mCreateDate));
      sb.append(' ');
      sb.append("EndDate=");
      sb.append(String.valueOf(this.mEndDate));
      sb.append(' ');
      sb.append("Step=");
      sb.append(String.valueOf(this.mStep));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("Task: ");
      sb.append(this.toString());
      return sb.toString();
   }

   public TaskCheckpoint getCheckpoint() {
      return (new TaskObjects(this.getObjects())).getCheckpoint();
   }

   public void setCheckpoint(TaskCheckpoint check) {
      TaskObjects to = new TaskObjects(this.getObjects());
      to.setCheckpoint(check);
      this.setObjects(to.getBytes());
   }

   public TaskRequest getRequest() {
      return (new TaskObjects(this.getObjects())).getRequest();
   }

   public void setRequest(TaskRequest req) {
      TaskObjects to = new TaskObjects(this.getObjects());
      to.setRequest(req);
      this.setObjects(to.getBytes());
   }

   public Task createTask() {
      if((new TaskObjects(this.getObjects())).getRequest() == null) {
         return null;
      } else {
         String taskClassName = (new TaskObjects(this.getObjects())).getRequest().getService();

         try {
            Class ie = Class.forName(taskClassName);
            Task task = (Task)ie.newInstance();
            task.setTaskId(this.getTaskId());
            task.setUserId(this.getUserId());
            task.setRequest(this.getRequest());
            task.setCheckpoint(this.getCheckpoint());
            task.setOriginalTaskId(this.getOriginalTaskId());
            return task;
         } catch (InstantiationException var4) {
            throw new CPException("InstantiationException:" + taskClassName);
         } catch (ClassNotFoundException var5) {
            throw new CPException("ClassNotFoundException:" + taskClassName);
         } catch (IllegalAccessException var6) {
            throw new CPException("IllegalAccessException:" + taskClassName);
         }
      }
   }

   public static String getStatusString(int status) {
      switch(status) {
      case 0:
         return "created";
      case 1:
         return "received";
      case 2:
         return "despatched";
      case 3:
         return "started";
      case 4:
         return "failed";
      case 5:
         return "complete";
      case 6:
         return "waiting for task";
      case 7:
         return "committing started state";
      case 8:
         return "committing completed state";
      case 9:
         return "complete - exceptions";
      case 10:
         return "unsafe deleted";
      default:
         return "status=" + status;
      }
   }
}
