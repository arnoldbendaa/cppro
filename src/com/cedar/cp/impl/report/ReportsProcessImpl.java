// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.ReportEditorSession;
import com.cedar.cp.api.report.ReportsProcess;
import com.cedar.cp.ejb.api.report.ReportEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.ReportEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ReportsProcessImpl extends BusinessProcessImpl implements ReportsProcess {

   private Log mLog = new Log(this.getClass());


   public ReportsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ReportEditorSessionServer es = new ReportEditorSessionServer(this.getConnection());

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

   public ReportEditorSession getReportEditorSession(Object key) throws ValidationException {
      ReportEditorSessionImpl sess = new ReportEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllReports() {
      try {
         return this.getConnection().getListHelper().getAllReports();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReports", var2);
      }
   }

   public EntityList getAllReportsForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllReportsForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllReportsForUser", var3);
      }
   }

   public EntityList getAllReportsForAdmin() {
      try {
         return this.getConnection().getListHelper().getAllReportsForAdmin();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReportsForAdmin", var2);
      }
   }

   public EntityList getWebReportDetails(int param1) {
      try {
         return this.getConnection().getListHelper().getWebReportDetails(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get WebReportDetails", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing Report";
      return ret;
   }

   protected int getProcessID() {
      return 60;
   }

   public int issueReportUpdateTask(Object reportKey, boolean rollback) throws ValidationException {
      return (new ReportEditorSessionServer(this.getConnection())).issueReportUpdateTask(reportKey, rollback);
   }
}
