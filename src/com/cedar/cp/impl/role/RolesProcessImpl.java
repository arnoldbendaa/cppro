// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.role;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.role.RoleEditorSession;
import com.cedar.cp.api.role.RolesProcess;
import com.cedar.cp.ejb.api.role.RoleEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.role.RoleEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class RolesProcessImpl extends BusinessProcessImpl implements RolesProcess {

   private Log mLog = new Log(this.getClass());


   public RolesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      RoleEditorSessionServer es = new RoleEditorSessionServer(this.getConnection());

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

   public RoleEditorSession getRoleEditorSession(Object key) throws ValidationException {
      RoleEditorSessionImpl sess = new RoleEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllRoles() {
      try {
         return this.getConnection().getListHelper().getAllRoles();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllRoles", var2);
      }
   }

   public EntityList getAllRolesForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllRolesForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllRolesForUser", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing Role";
      return ret;
   }

   protected int getProcessID() {
      return 71;
   }
}
