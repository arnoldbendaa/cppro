// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.task;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.task.TaskViewer;
import com.cedar.cp.api.task.TaskViewerProcess;
import com.cedar.cp.api.task.TaskViewerSession;
import com.cedar.cp.dto.task.TaskDetails;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.api.task.TaskProcessServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.task.TaskViewerImpl;
import com.cedar.cp.impl.task.TaskViewerProcessImpl;

public class TaskViewerSessionImpl extends BusinessSessionImpl implements TaskViewerSession {

   private TaskViewer mViewer;
   private String mUser;
   private TaskDetails mTaskDetails;


   public TaskViewerSessionImpl(TaskViewerProcessImpl process, Object key) throws CPException {
      super(process);

      try {
         TaskProcessServer e = new TaskProcessServer(this.getConnection());
         this.mTaskDetails = e.getTask((TaskPK)key);
         this.mUser = this.getUserForKey((TaskPK)key);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new CPException("Can\'t get TaskViewerSessionImpl", var4);
      }
   }

   private String getUserForKey(TaskPK key) throws CPException {
      EntityList coll = ((TaskViewerProcess)this.getBusinessProcess()).getTasks(-1);
      Integer[] ids = (Integer[])((Integer[])coll.getValues("TaskId"));
      Object[] names = coll.getValues("User");

      for(int i = 0; i < ids.length; ++i) {
         if(ids[i].equals(new Integer(key.getTaskId()))) {
            return ((EntityRef)names[i]).getNarrative();
         }
      }

      return null;
   }

   public TaskViewer getTaskViewer() {
      try {
         if(this.mViewer == null) {
            this.mViewer = new TaskViewerImpl((BusinessProcessImpl)this.getBusinessProcess(), this.mTaskDetails.getTaskId(), this.mTaskDetails.getTaskName(), this.mTaskDetails.getOriginalTaskId(), this.mUser, this.mTaskDetails.getStatus(), this.mTaskDetails.getStep(), this.mTaskDetails.getException(), this.mTaskDetails.getExclusiveAccessList(), this.mTaskDetails.getCreateDate());
            this.mActiveEditors.add(this.mViewer);
         }

         return this.mViewer;
      } catch (Exception var2) {
         throw new RuntimeException("Failed to build a TaskViewer object", var2);
      }
   }

   public Object getPrimaryKey() {
      return this.mTaskDetails.getPK();
   }

   public void terminate() {}

   public Object persistModifications(boolean cloneOnSave) {
      return null;
   }
}
