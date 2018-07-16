// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskEditor;
import com.cedar.cp.api.admin.tidytask.TidyTaskEditorSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionSSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionServer;
import com.cedar.cp.impl.admin.tidytask.TidyTaskEditorImpl;
import com.cedar.cp.impl.admin.tidytask.TidyTasksProcessImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;

public class TidyTaskEditorSessionImpl extends BusinessSessionImpl implements TidyTaskEditorSession {

   protected TidyTaskEditorSessionSSO mServerSessionData;
   protected TidyTaskImpl mEditorData;
   protected TidyTaskEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public TidyTaskEditorSessionImpl(TidyTasksProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get TidyTask", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected TidyTaskEditorSessionServer getSessionServer() throws CPException {
      return new TidyTaskEditorSessionServer(this.getConnection());
   }

   public TidyTaskEditor getTidyTaskEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new TidyTaskEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
