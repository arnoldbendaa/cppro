// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.SecurityRangeEditorSession;
import com.cedar.cp.api.dimension.SecurityRangesProcess;
import com.cedar.cp.ejb.api.dimension.SecurityRangeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.dimension.SecurityRangeEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class SecurityRangesProcessImpl extends BusinessProcessImpl implements SecurityRangesProcess {

   private Log mLog = new Log(this.getClass());


   public SecurityRangesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      SecurityRangeEditorSessionServer es = new SecurityRangeEditorSessionServer(this.getConnection());

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

   public SecurityRangeEditorSession getSecurityRangeEditorSession(Object key) throws ValidationException {
      SecurityRangeEditorSessionImpl sess = new SecurityRangeEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllSecurityRanges() {
      try {
         return this.getConnection().getListHelper().getAllSecurityRanges();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllSecurityRanges", var2);
      }
   }

   public EntityList getAllSecurityRangesForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllSecurityRangesForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllSecurityRangesForModel", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing SecurityRange";
      return ret;
   }

   protected int getProcessID() {
      return 52;
   }
}
