// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cm;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cm.ChangeMgmtEditor;
import com.cedar.cp.api.cm.ChangeMgmtEditorSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionSSO;
import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.cm.ChangeMgmtEditorImpl;
import com.cedar.cp.impl.cm.ChangeMgmtsProcessImpl;
import com.cedar.cp.util.Log;

public class ChangeMgmtEditorSessionImpl extends BusinessSessionImpl implements ChangeMgmtEditorSession {

   protected ChangeMgmtEditorSessionSSO mServerSessionData;
   protected ChangeMgmtImpl mEditorData;
   protected ChangeMgmtEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ChangeMgmtEditorSessionImpl(ChangeMgmtsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get ChangeMgmt", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ChangeMgmtEditorSessionServer getSessionServer() throws CPException {
      return new ChangeMgmtEditorSessionServer(this.getConnection());
   }

   public ChangeMgmtEditor getChangeMgmtEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ChangeMgmtEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public ModelRef[] getAvailableRelatedModelRefs() {
      try {
         return this.getSessionServer().getAvailableRelatedModelRefs();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
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
