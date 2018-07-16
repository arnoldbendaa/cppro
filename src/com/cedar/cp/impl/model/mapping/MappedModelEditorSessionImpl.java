// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedModelEditor;
import com.cedar.cp.api.model.mapping.MappedModelEditorSession;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionSSO;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.mapping.MappedModelEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedModelsProcessImpl;
import com.cedar.cp.util.Log;

public class MappedModelEditorSessionImpl extends BusinessSessionImpl implements MappedModelEditorSession {

   protected MappedModelEditorSessionSSO mServerSessionData;
   protected MappedModelImpl mEditorData;
   protected MappedModelEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public MappedModelEditorSessionImpl(MappedModelsProcessImpl process, Object key) throws ValidationException {
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

   protected MappedModelEditorSessionServer getSessionServer() throws CPException {
      return new MappedModelEditorSessionServer(this.getConnection());
   }

   public MappedModelEditor getMappedModelEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new MappedModelEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
