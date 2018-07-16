// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.user;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.UserEditorSession;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.user.UsersProcess;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserPreferencesForUserELO;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.ejb.api.user.UserEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.user.UserEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class UsersProcessImpl extends BusinessProcessImpl implements UsersProcess {

   private Log mLog = new Log(this.getClass());


   public UsersProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      int selectedId = ((UserPK)primaryKey).getUserId();
      int loggedOnUserId = this.getConnection().getUserContext().getUserId();
      if(selectedId == loggedOnUserId) {
         throw new ValidationException("Can\'t Delete logged on user.");
      } else {
         UserEditorSessionServer es = new UserEditorSessionServer(this.getConnection());

         try {
            es.delete(primaryKey);
         } catch (ValidationException var7) {
            throw var7;
         } catch (CPException var8) {
            throw new RuntimeException("can\'t delete " + primaryKey, var8);
         }

         if(timer != null) {
            timer.logDebug("deleteObject", primaryKey);
         }

      }
   }

   public UserEditorSession getUserEditorSession(Object key) throws ValidationException {
      UserEditorSessionImpl sess = new UserEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllUsers() {
      try {
         return this.getConnection().getListHelper().getAllUsers();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllUsers", var2);
      }
   }

   public EntityList getSecurityStringsForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getSecurityStringsForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get SecurityStringsForUser", var3);
      }
   }

   public EntityList getAllUsersExport() {
      try {
         return this.getConnection().getListHelper().getAllUsersExport();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllUsersExport", var2);
      }
   }

   public EntityList getAllUserAttributes() {
      try {
         return this.getConnection().getListHelper().getAllUserAttributes();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllUserAttributes", var2);
      }
   }

   public EntityList getAllNonDisabledUsers() {
      try {
         return this.getConnection().getListHelper().getAllNonDisabledUsers();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllNonDisabledUsers", var2);
      }
   }

   public EntityList getUserMessageAttributes() {
      try {
         return this.getConnection().getListHelper().getUserMessageAttributes();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get UserMessageAttributes", var2);
      }
   }

   public EntityList getUserMessageAttributesForId(int param1) {
      try {
         return this.getConnection().getListHelper().getUserMessageAttributesForId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get UserMessageAttributesForId", var3);
      }
   }

   public EntityList getUserMessageAttributesForName(String param1) {
      try {
         return this.getConnection().getListHelper().getUserMessageAttributesForName(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get UserMessageAttributesForName", var3);
      }
   }

   public EntityList getFinanceSystemUserName(int param1) {
      try {
         return this.getConnection().getListHelper().getFinanceSystemUserName(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get FinanceSystemUserName", var3);
      }
   }

   public EntityList getUsersWithSecurityString(String param1) {
      try {
         return this.getConnection().getListHelper().getUsersWithSecurityString(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get UsersWithSecurityString", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing User";
      return ret;
   }

   protected int getProcessID() {
      return 27;
   }

   public EntityList getUserPreferencesForUser(int userId) throws CPException {
      try {
         UserPreferencesForUserELO e = (new ListSessionServer(this.getConnection())).getUserPreferencesForUser(userId);
         return e;
      } catch (Exception var3) {
         throw new CPException("exception in getUserPreferencesForUser", var3);
      }
   }

   public EntityList getExportResponsibilityAreaDetailsForUser(UserRef userRef) throws CPException {
      return null;
   }

   public UserEditorSession getUserEditorSession(int key) throws ValidationException {
      UserPK pk = new UserPK(key);
      return this.getUserEditorSession(pk);
   }

   public EntityList getAllUserAssignments(String pName, String pFullName, String pModel, String pLocation) throws CPException {
      try {
         EntityList e = (new ListSessionServer(this.getConnection())).getAllUserAssignments(pName, pFullName, pModel, pLocation);
         return e;
      } catch (Exception var6) {
         throw new CPException("exception in getAllUserAssignments");
      }
   }

   public EntityList getAllUserDetailsReport(String param1, String param2, String param3, boolean param4) {
      try {
         return (new ListSessionServer(this.getConnection())).getAllUserDetailsReport(param1, param2, param3, param4);
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new RuntimeException("can\'t get AllUserDetailsReport", var6);
      }
   }
   
	public EntityList getNodeAndUpUserAssignments(int structureElementId, int structureId) throws CPException {
		try {
			return new ListSessionServer(getConnection()).getNodeAndUpUserAssignments(structureElementId, structureId);
		} catch (Exception e) {
			throw new CPException("exception in getNodeAndUpUserAssignments");
		}
	}
}
