// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.template;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.template.ReportTemplateEditor;
import com.cedar.cp.api.report.template.ReportTemplateEditorSession;
import com.cedar.cp.dto.report.template.ReportTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.template.ReportTemplateImpl;
import com.cedar.cp.ejb.api.report.template.ReportTemplateEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.report.template.ReportTemplateEditorImpl;
import com.cedar.cp.impl.report.template.ReportTemplatesProcessImpl;
import com.cedar.cp.util.Log;

public class ReportTemplateEditorSessionImpl extends BusinessSessionImpl implements ReportTemplateEditorSession {

   protected ReportTemplateEditorSessionSSO mServerSessionData;
   protected ReportTemplateImpl mEditorData;
   protected ReportTemplateEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ReportTemplateEditorSessionImpl(ReportTemplatesProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get ReportTemplate", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ReportTemplateEditorSessionServer getSessionServer() throws CPException {
      return new ReportTemplateEditorSessionServer(this.getConnection());
   }

   public ReportTemplateEditor getReportTemplateEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ReportTemplateEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
