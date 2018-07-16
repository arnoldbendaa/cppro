// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.task.group;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.group.TaskGroupEditor;
import com.cedar.cp.api.task.group.TaskGroupEditorSession;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionSSO;
import com.cedar.cp.dto.task.group.TaskGroupImpl;
import com.cedar.cp.ejb.api.task.group.TaskGroupEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.task.group.TaskGroupEditorImpl;
import com.cedar.cp.impl.task.group.TaskGroupsProcessImpl;
import com.cedar.cp.util.Log;

public class TaskGroupEditorSessionImpl extends BusinessSessionImpl implements TaskGroupEditorSession {

   protected TaskGroupEditorSessionSSO mServerSessionData;
   protected TaskGroupImpl mEditorData;
   protected TaskGroupEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public TaskGroupEditorSessionImpl(TaskGroupsProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get TaskGroup", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected TaskGroupEditorSessionServer getSessionServer() throws CPException {
      return new TaskGroupEditorSessionServer(this.getConnection());
   }

   public TaskGroupEditor getTaskGroupEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new TaskGroupEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
      if(this.mClientEditor != null) {
         this.mClientEditor.saveModifications();
      }

      if(this.mEditorData.getPrimaryKey() == null) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
      } else if(cloneOnSave) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
      } else {
         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {}
}
