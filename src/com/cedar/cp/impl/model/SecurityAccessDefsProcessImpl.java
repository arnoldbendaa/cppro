// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.SecurityAccessDefEditorSession;
import com.cedar.cp.api.model.SecurityAccessDefsProcess;
import com.cedar.cp.ejb.api.model.SecurityAccessDefEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.SecurityAccessDefEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class SecurityAccessDefsProcessImpl extends BusinessProcessImpl implements SecurityAccessDefsProcess {

   private Log mLog = new Log(this.getClass());


   public SecurityAccessDefsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      SecurityAccessDefEditorSessionServer es = new SecurityAccessDefEditorSessionServer(this.getConnection());

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

   public SecurityAccessDefEditorSession getSecurityAccessDefEditorSession(Object key) throws ValidationException {
      SecurityAccessDefEditorSessionImpl sess = new SecurityAccessDefEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllSecurityAccessDefs() {
      try {
         return this.getConnection().getListHelper().getAllSecurityAccessDefs();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllSecurityAccessDefs", var2);
      }
   }

   public EntityList getAllSecurityAccessDefsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllSecurityAccessDefsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllSecurityAccessDefsForModel", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing SecurityAccessDef";
      return ret;
   }

   protected int getProcessID() {
      return 51;
   }
}
