// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyEditorSession;
import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicysProcess;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionServer;
import com.cedar.cp.impl.authenticationpolicy.AuthenticationPolicyEditorSessionImpl;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class AuthenticationPolicysProcessImpl extends BusinessProcessImpl implements AuthenticationPolicysProcess {

   private Log mLog = new Log(this.getClass());


   public AuthenticationPolicysProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      AuthenticationPolicyEditorSessionServer es = new AuthenticationPolicyEditorSessionServer(this.getConnection());

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

   public AuthenticationPolicyEditorSession getAuthenticationPolicyEditorSession(Object key) throws ValidationException {
      AuthenticationPolicyEditorSessionImpl sess = new AuthenticationPolicyEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllAuthenticationPolicys() {
      try {
         return this.getConnection().getListHelper().getAllAuthenticationPolicys();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllAuthenticationPolicys", var2);
      }
   }

   public EntityList getActiveAuthenticationPolicys() {
      try {
         return this.getConnection().getListHelper().getActiveAuthenticationPolicys();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get ActiveAuthenticationPolicys", var2);
      }
   }

   public EntityList getActiveAuthenticationPolicyForLogon() {
      try {
         return this.getConnection().getListHelper().getActiveAuthenticationPolicyForLogon();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get ActiveAuthenticationPolicyForLogon", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing AuthenticationPolicy";
      return ret;
   }

   protected int getProcessID() {
      return 92;
   }

   public int getActiveAuthenticationTechnique() {
      EntityList authenticationMethod = this.getActiveAuthenticationPolicyForLogon();
      if(authenticationMethod.getNumRows() != 1) {
         throw new IllegalStateException("Only one authentication policy may be active at once.");
      } else {
         return ((Integer)authenticationMethod.getValueAt(0, "AuthenticationTechnique")).intValue();
      }
   }
}
