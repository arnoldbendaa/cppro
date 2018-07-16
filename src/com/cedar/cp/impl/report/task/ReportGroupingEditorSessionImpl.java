// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.task;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.task.ReportGroupingEditor;
import com.cedar.cp.api.report.task.ReportGroupingEditorSession;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionSSO;
import com.cedar.cp.dto.report.task.ReportGroupingImpl;
import com.cedar.cp.ejb.api.report.task.ReportGroupingEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.report.task.ReportGroupingEditorImpl;
import com.cedar.cp.util.Log;

public class ReportGroupingEditorSessionImpl extends BusinessSessionImpl implements ReportGroupingEditorSession {

   protected ReportGroupingEditorSessionSSO mServerSessionData;
   protected ReportGroupingImpl mEditorData;
   protected ReportGroupingEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ReportGroupingEditorSessionImpl(BusinessProcessImpl process) {
      super(process);
   }

   protected ReportGroupingEditorSessionServer getSessionServer() throws CPException {
      return new ReportGroupingEditorSessionServer(this.getConnection());
   }

   public ReportGroupingEditor getReportGroupingEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ReportGroupingEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
