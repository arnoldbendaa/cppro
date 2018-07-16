// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.logonhistory;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logonhistory.LogonHistoryEditorSession;
import com.cedar.cp.api.logonhistory.LogonHistorysProcess;
import com.cedar.cp.ejb.api.logonhistory.LogonHistoryEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.logonhistory.LogonHistoryEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.sql.Timestamp;

public class LogonHistorysProcessImpl extends BusinessProcessImpl implements LogonHistorysProcess {

   private Log mLog = new Log(this.getClass());


   public LogonHistorysProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      LogonHistoryEditorSessionServer es = new LogonHistoryEditorSessionServer(this.getConnection());

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

   public LogonHistoryEditorSession getLogonHistoryEditorSession(Object key) throws ValidationException {
      LogonHistoryEditorSessionImpl sess = new LogonHistoryEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllLogonHistorys() {
      try {
         return this.getConnection().getListHelper().getAllLogonHistorys();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllLogonHistorys", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing LogonHistory";
      return ret;
   }

   protected int getProcessID() {
      return 93;
   }

   public EntityList getAllLogonHistorysReport(String param1, Timestamp param2, int param3) {
      try {
         return this.getConnection().getListHelper().getAllLogonHistorysReport(param1, param2, param3);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new RuntimeException("can\'t get AllLogonHistorysReport", var5);
      }
   }
}
