// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.type;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.type.ReportTypeEditorSession;
import com.cedar.cp.api.report.type.ReportTypesProcess;
import com.cedar.cp.ejb.api.report.type.ReportTypeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.type.ReportTypeEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ReportTypesProcessImpl extends BusinessProcessImpl implements ReportTypesProcess {

   private Log mLog = new Log(this.getClass());


   public ReportTypesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ReportTypeEditorSessionServer es = new ReportTypeEditorSessionServer(this.getConnection());

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

   public ReportTypeEditorSession getReportTypeEditorSession(Object key) throws ValidationException {
      ReportTypeEditorSessionImpl sess = new ReportTypeEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllReportTypes() {
      try {
         return this.getConnection().getListHelper().getAllReportTypes();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReportTypes", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing ReportType";
      return ret;
   }

   protected int getProcessID() {
      return 74;
   }
}
