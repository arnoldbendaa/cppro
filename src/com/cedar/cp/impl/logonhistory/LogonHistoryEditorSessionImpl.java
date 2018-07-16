// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.logonhistory;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logonhistory.LogonHistoryEditor;
import com.cedar.cp.api.logonhistory.LogonHistoryEditorSession;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionSSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryImpl;
import com.cedar.cp.ejb.api.logonhistory.LogonHistoryEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.logonhistory.LogonHistoryEditorImpl;
import com.cedar.cp.impl.logonhistory.LogonHistorysProcessImpl;
import com.cedar.cp.util.Log;

public class LogonHistoryEditorSessionImpl extends BusinessSessionImpl implements LogonHistoryEditorSession {

   protected LogonHistoryEditorSessionSSO mServerSessionData;
   protected LogonHistoryImpl mEditorData;
   protected LogonHistoryEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public LogonHistoryEditorSessionImpl(LogonHistorysProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get LogonHistory", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected LogonHistoryEditorSessionServer getSessionServer() throws CPException {
      return new LogonHistoryEditorSessionServer(this.getConnection());
   }

   public LogonHistoryEditor getLogonHistoryEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new LogonHistoryEditorImpl(this, this.mServerSessionData, this.mEditorData);
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