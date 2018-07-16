// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.pack;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.pack.ReportPackEditor;
import com.cedar.cp.api.report.pack.ReportPackEditorSession;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionSSO;
import com.cedar.cp.dto.report.pack.ReportPackImpl;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.report.pack.ReportPackEditorImpl;
import com.cedar.cp.impl.report.pack.ReportPacksProcessImpl;
import com.cedar.cp.util.Log;

public class ReportPackEditorSessionImpl extends BusinessSessionImpl implements ReportPackEditorSession {

   protected ReportPackEditorSessionSSO mServerSessionData;
   protected ReportPackImpl mEditorData;
   protected ReportPackEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ReportPackEditorSessionImpl(ReportPacksProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get ReportPack", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ReportPackEditorSessionServer getSessionServer() throws CPException {
      return new ReportPackEditorSessionServer(this.getConnection());
   }

   public ReportPackEditor getReportPackEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ReportPackEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
