package com.cedar.cp.impl.importtask;

import com.cedar.cp.api.importtask.ImportTaskEditor;
import com.cedar.cp.api.importtask.ImportTaskEditorSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionSSO;
import com.cedar.cp.dto.importtask.ImportTaskImpl;
import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionServer;
import com.cedar.cp.impl.importtask.ImportTaskEditorImpl;
import com.cedar.cp.impl.importtask.ImportTasksProcessImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;

public class ImportTaskEditorSessionImpl extends BusinessSessionImpl implements ImportTaskEditorSession {

   protected ImportTaskEditorSessionSSO mServerSessionData;
   protected ImportTaskImpl mEditorData;
   protected ImportTaskEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ImportTaskEditorSessionImpl(ImportTasksProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get ImportTask", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ImportTaskEditorSessionServer getSessionServer() throws CPException {
      return new ImportTaskEditorSessionServer(this.getConnection());
   }

   public ImportTaskEditor getImportTaskEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ImportTaskEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
