// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftestrun;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunEditor;
import com.cedar.cp.api.perftestrun.PerfTestRunEditorSession;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionSSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunImpl;
import com.cedar.cp.ejb.api.perftestrun.PerfTestRunEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.perftestrun.PerfTestRunEditorImpl;
import com.cedar.cp.impl.perftestrun.PerfTestRunsProcessImpl;
import com.cedar.cp.util.Log;

public class PerfTestRunEditorSessionImpl extends BusinessSessionImpl implements PerfTestRunEditorSession {

   protected PerfTestRunEditorSessionSSO mServerSessionData;
   protected PerfTestRunImpl mEditorData;
   protected PerfTestRunEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public PerfTestRunEditorSessionImpl(PerfTestRunsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get PerfTestRun", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected PerfTestRunEditorSessionServer getSessionServer() throws CPException {
      return new PerfTestRunEditorSessionServer(this.getConnection());
   }

   public PerfTestRunEditor getPerfTestRunEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new PerfTestRunEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
