// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.pack;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.pack.ReportPackEditorSession;
import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.report.pack.ReportPackProjection;
import com.cedar.cp.api.report.pack.ReportPacksProcess;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.pack.ReportPackEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ReportPacksProcessImpl extends BusinessProcessImpl implements ReportPacksProcess {

   private Log mLog = new Log(this.getClass());


   public ReportPacksProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ReportPackEditorSessionServer es = new ReportPackEditorSessionServer(this.getConnection());

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

   public ReportPackEditorSession getReportPackEditorSession(Object key) throws ValidationException {
      ReportPackEditorSessionImpl sess = new ReportPackEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllReportPacks() {
      try {
         return this.getConnection().getListHelper().getAllReportPacks();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReportPacks", var2);
      }
   }

   public EntityList getReportDefDistList(String param1) {
      try {
         return this.getConnection().getListHelper().getReportDefDistList(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ReportDefDistList", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing ReportPack";
      return ret;
   }

   protected int getProcessID() {
      return 80;
   }

   public int issueReport(int intUserId, EntityRef ref, ReportPackOption options) throws ValidationException, CPException {
      ReportPackEditorSessionServer server = new ReportPackEditorSessionServer(this.getConnection());
      return server.issueReport(intUserId, ref, options);
   }

   public ReportPackProjection getReportPackProjection(int intUserId, Object key) throws ValidationException, CPException {
      ReportPackEditorSessionServer server = new ReportPackEditorSessionServer(this.getConnection());
      return server.getReportPackProjection(intUserId, key);
   }
}
