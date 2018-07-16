// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.mappingtemplate;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplateEditorSession;
import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplatesProcess;
import com.cedar.cp.ejb.api.report.mappingtemplate.ReportMappingTemplateEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.mappingtemplate.ReportMappingTemplateEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ReportMappingTemplatesProcessImpl extends BusinessProcessImpl implements ReportMappingTemplatesProcess {

   private Log mLog = new Log(this.getClass());


   public ReportMappingTemplatesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ReportMappingTemplateEditorSessionServer es = new ReportMappingTemplateEditorSessionServer(this.getConnection());

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

   public ReportMappingTemplateEditorSession getReportMappingTemplateEditorSession(Object key) throws ValidationException {
      ReportMappingTemplateEditorSessionImpl sess = new ReportMappingTemplateEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllReportMappingTemplates() {
      try {
         return this.getConnection().getListHelper().getAllReportMappingTemplates();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReportMappingTemplates", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing ReportMappingTemplate";
      return ret;
   }

   protected int getProcessID() {
      return 84;
   }
}
