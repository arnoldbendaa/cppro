// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.destination.internal;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.internal.InternalDestinationEditor;
import com.cedar.cp.api.report.destination.internal.InternalDestinationEditorSession;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationImpl;
import com.cedar.cp.ejb.api.report.destination.internal.InternalDestinationEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.report.destination.internal.InternalDestinationEditorImpl;
import com.cedar.cp.impl.report.destination.internal.InternalDestinationsProcessImpl;
import com.cedar.cp.util.Log;

public class InternalDestinationEditorSessionImpl extends BusinessSessionImpl implements InternalDestinationEditorSession {

   protected InternalDestinationEditorSessionSSO mServerSessionData;
   protected InternalDestinationImpl mEditorData;
   protected InternalDestinationEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public InternalDestinationEditorSessionImpl(InternalDestinationsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get InternalDestination", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected InternalDestinationEditorSessionServer getSessionServer() throws CPException {
      return new InternalDestinationEditorSessionServer(this.getConnection());
   }

   public InternalDestinationEditor getInternalDestinationEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new InternalDestinationEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
