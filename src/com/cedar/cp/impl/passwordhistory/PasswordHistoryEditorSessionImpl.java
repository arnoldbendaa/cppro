// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.passwordhistory;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.passwordhistory.PasswordHistoryEditor;
import com.cedar.cp.api.passwordhistory.PasswordHistoryEditorSession;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryEditorSessionSSO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryImpl;
import com.cedar.cp.ejb.api.passwordhistory.PasswordHistoryEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.passwordhistory.PasswordHistoryEditorImpl;
import com.cedar.cp.impl.passwordhistory.PasswordHistorysProcessImpl;
import com.cedar.cp.util.Log;

public class PasswordHistoryEditorSessionImpl extends BusinessSessionImpl implements PasswordHistoryEditorSession {

   protected PasswordHistoryEditorSessionSSO mServerSessionData;
   protected PasswordHistoryImpl mEditorData;
   protected PasswordHistoryEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public PasswordHistoryEditorSessionImpl(PasswordHistorysProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get PasswordHistory", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected PasswordHistoryEditorSessionServer getSessionServer() throws CPException {
      return new PasswordHistoryEditorSessionServer(this.getConnection());
   }

   public PasswordHistoryEditor getPasswordHistoryEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new PasswordHistoryEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
