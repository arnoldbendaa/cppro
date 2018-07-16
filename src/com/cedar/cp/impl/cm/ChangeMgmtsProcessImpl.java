// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cm;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cm.ChangeManagementSession;
import com.cedar.cp.api.cm.ChangeMgmtEditorSession;
import com.cedar.cp.api.cm.ChangeMgmtsProcess;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.cm.ChangeManagamentSessionImpl;
import com.cedar.cp.impl.cm.ChangeMgmtEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ChangeMgmtsProcessImpl extends BusinessProcessImpl implements ChangeMgmtsProcess {

   private Log mLog = new Log(this.getClass());


   public ChangeMgmtsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ChangeMgmtEditorSessionServer es = new ChangeMgmtEditorSessionServer(this.getConnection());

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

   public ChangeMgmtEditorSession getChangeMgmtEditorSession(Object key) throws ValidationException {
      ChangeMgmtEditorSessionImpl sess = new ChangeMgmtEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllChangeMgmts() {
      try {
         return this.getConnection().getListHelper().getAllChangeMgmts();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllChangeMgmts", var2);
      }
   }

   public EntityList getAllChangeMgmtsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllChangeMgmtsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllChangeMgmtsForModel", var3);
      }
   }

   public EntityList getAllChangeMgmtsForModelWithXML(int param1) {
      try {
         return this.getConnection().getListHelper().getAllChangeMgmtsForModelWithXML(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllChangeMgmtsForModelWithXML", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing ChangeMgmt";
      return ret;
   }

   protected int getProcessID() {
      return 54;
   }

   public ChangeManagementSession getChangeManagementSession() {
      return new ChangeManagamentSessionImpl(this.getConnection());
   }

   public void tidyBudgetState(int modelId) throws ValidationException {
      ChangeMgmtEditorSessionServer es = new ChangeMgmtEditorSessionServer(this.getConnection());

      try {
         es.tidyBudgetState(modelId);
      } catch (ValidationException var4) {
         throw var4;
      } catch (CPException var5) {
         throw new RuntimeException("tidy error");
      }
   }
}
