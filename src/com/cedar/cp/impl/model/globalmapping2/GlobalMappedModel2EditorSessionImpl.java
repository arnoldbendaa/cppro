package com.cedar.cp.impl.model.globalmapping2;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2Editor;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2EditorSession;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionSSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2Impl;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModel2EditorImpl;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModels2ProcessImpl;
import com.cedar.cp.util.Log;

public class GlobalMappedModel2EditorSessionImpl extends BusinessSessionImpl implements GlobalMappedModel2EditorSession {

   protected GlobalMappedModel2EditorSessionSSO mServerSessionData;
   protected GlobalMappedModel2Impl mEditorData;
   protected GlobalMappedModel2EditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public GlobalMappedModel2EditorSessionImpl(GlobalMappedModels2ProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get MappedModel", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected GlobalMappedModel2EditorSessionServer getSessionServer() throws CPException {
      return new GlobalMappedModel2EditorSessionServer(this.getConnection());
   }

   public GlobalMappedModel2Editor getMappedModelEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new GlobalMappedModel2EditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public ModelRef[] getAvailableOwningModelRefs() {
      try {
         return this.getSessionServer().getAvailableOwningModelRefs();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public ExternalSystemRef[] getAvailableExternalSystemRefs() {
      try {
         return this.getSessionServer().getAvailableExternalSystemRefs();
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
