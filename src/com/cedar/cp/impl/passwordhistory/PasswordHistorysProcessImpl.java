// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.passwordhistory;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.passwordhistory.PasswordHistoryEditorSession;
import com.cedar.cp.api.passwordhistory.PasswordHistorysProcess;
import com.cedar.cp.ejb.api.passwordhistory.PasswordHistoryEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.passwordhistory.PasswordHistoryEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class PasswordHistorysProcessImpl extends BusinessProcessImpl implements PasswordHistorysProcess {

   private Log mLog = new Log(this.getClass());


   public PasswordHistorysProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      PasswordHistoryEditorSessionServer es = new PasswordHistoryEditorSessionServer(this.getConnection());

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

   public PasswordHistoryEditorSession getPasswordHistoryEditorSession(Object key) throws ValidationException {
      PasswordHistoryEditorSessionImpl sess = new PasswordHistoryEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllPasswordHistorys() {
      try {
         return this.getConnection().getListHelper().getAllPasswordHistorys();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllPasswordHistorys", var2);
      }
   }

   public EntityList getUserPasswordHistory(int param1) {
      try {
         return this.getConnection().getListHelper().getUserPasswordHistory(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get UserPasswordHistory", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing PasswordHistory";
      return ret;
   }

   protected int getProcessID() {
      return 94;
   }
}
