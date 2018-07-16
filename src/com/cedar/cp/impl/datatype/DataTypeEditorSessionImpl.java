// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.datatype;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeEditor;
import com.cedar.cp.api.datatype.DataTypeEditorSession;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionSSO;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.datatype.DataTypeEditorImpl;
import com.cedar.cp.impl.datatype.DataTypesProcessImpl;
import com.cedar.cp.util.Log;

public class DataTypeEditorSessionImpl extends BusinessSessionImpl implements DataTypeEditorSession {

   protected DataTypeEditorSessionSSO mServerSessionData;
   protected DataTypeImpl mEditorData;
   protected DataTypeEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public DataTypeEditorSessionImpl(DataTypesProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get DataType", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected DataTypeEditorSessionServer getSessionServer() throws CPException {
      return new DataTypeEditorSessionServer(this.getConnection());
   }

   public DataTypeEditor getDataTypeEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new DataTypeEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
