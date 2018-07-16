// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.template;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.template.ReportTemplateEditorSession;
import com.cedar.cp.api.report.template.ReportTemplatesProcess;
import com.cedar.cp.ejb.api.report.template.ReportTemplateEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.template.ReportTemplateEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ReportTemplatesProcessImpl extends BusinessProcessImpl implements ReportTemplatesProcess {

   private Log mLog = new Log(this.getClass());


   public ReportTemplatesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ReportTemplateEditorSessionServer es = new ReportTemplateEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public ReportTemplateEditorSession getReportTemplateEditorSession(Object key) throws ValidationException {
      ReportTemplateEditorSessionImpl sess = new ReportTemplateEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllReportTemplates() {
      try {
         return this.getConnection().getListHelper().getAllReportTemplates();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReportTemplates", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing ReportTemplate";
      return ret;
   }

   protected int getProcessID() {
      return 76;
   }
}
