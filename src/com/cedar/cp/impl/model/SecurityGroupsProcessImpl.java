// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.SecurityGroupEditorSession;
import com.cedar.cp.api.model.SecurityGroupsProcess;
import com.cedar.cp.ejb.api.model.SecurityGroupEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.SecurityGroupEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class SecurityGroupsProcessImpl extends BusinessProcessImpl implements SecurityGroupsProcess {

   private Log mLog = new Log(this.getClass());


   public SecurityGroupsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      SecurityGroupEditorSessionServer es = new SecurityGroupEditorSessionServer(this.getConnection());

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

   public SecurityGroupEditorSession getSecurityGroupEditorSession(Object key) throws ValidationException {
      SecurityGroupEditorSessionImpl sess = new SecurityGroupEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllSecurityGroups() {
      try {
         return this.getConnection().getListHelper().getAllSecurityGroups();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllSecurityGroups", var2);
      }
   }

   public EntityList getAllSecurityGroupsUsingAccessDef(int param1) {
      try {
         return this.getConnection().getListHelper().getAllSecurityGroupsUsingAccessDef(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllSecurityGroupsUsingAccessDef", var3);
      }
   }

   public EntityList getAllSecurityGroupsForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllSecurityGroupsForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllSecurityGroupsForUser", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing SecurityGroup";
      return ret;
   }

   protected int getProcessID() {
      return 50;
   }
}
