// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionEditor;
import com.cedar.cp.api.dimension.DimensionEditorSession;
import com.cedar.cp.dto.dimension.DimensionEditorSessionSSO;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.dimension.DimensionEditorImpl;
import com.cedar.cp.impl.dimension.DimensionsProcessImpl;
import com.cedar.cp.util.Log;

public class DimensionEditorSessionImpl extends BusinessSessionImpl implements DimensionEditorSession {

   protected DimensionEditorSessionSSO mServerSessionData;
   private DimensionEditorSessionServer mEditorSessionServer = new DimensionEditorSessionServer(this.getConnection());
   protected DimensionImpl mEditorData;
   protected DimensionEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public DimensionEditorSessionImpl(DimensionsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get Dimension", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected DimensionEditorSessionServer getSessionServer() throws CPException {
      return this.mEditorSessionServer;
   }

   public DimensionEditor getDimensionEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new DimensionEditorImpl(this, this.mServerSessionData, this.mEditorData);
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

   public void terminate() {
      try {
         if(this.mEditorSessionServer != null) {
            this.mEditorSessionServer.removeSession();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }
}
