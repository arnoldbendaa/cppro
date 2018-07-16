// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.user;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.DataEntryProfileEditorSession;
import com.cedar.cp.api.user.DataEntryProfilesProcess;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.user.DataEntryProfileEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class DataEntryProfilesProcessImpl extends BusinessProcessImpl implements DataEntryProfilesProcess {

   private Log mLog = new Log(this.getClass());


   public DataEntryProfilesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      DataEntryProfileEditorSessionServer es = new DataEntryProfileEditorSessionServer(this.getConnection());

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

   public DataEntryProfileEditorSession getDataEntryProfileEditorSession(Object key) throws ValidationException {
      DataEntryProfileEditorSessionImpl sess = new DataEntryProfileEditorSessionImpl(this, key);
      //System.out.println("----- DataEntryProfilesProcessImpl: mActiveSessions.size: "+mActiveSessions.size()+" / ID: "+System.identityHashCode(mActiveSessions));
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllDataEntryProfiles() {
      try {
         return this.getConnection().getListHelper().getAllDataEntryProfiles();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllDataEntryProfiles", var2);
      }
   }

   public EntityList getAllDataEntryProfilesForUser(int param1, int param2, int budgetCycleId) {
      try {
         return this.getConnection().getListHelper().getAllDataEntryProfilesForUser(param1, param2, budgetCycleId);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException("can\'t get AllDataEntryProfilesForUser", var4);
      }
   }

   public EntityList getAllUsersForDataEntryProfilesForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllUsersForDataEntryProfilesForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllUsersForDataEntryProfilesForModel", var3);
      }
   }

   public EntityList getAllDataEntryProfilesForForm(int param1) {
      try {
         return this.getConnection().getListHelper().getAllDataEntryProfilesForForm(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllDataEntryProfilesForForm", var3);
      }
   }

   public EntityList getDefaultDataEntryProfile(int param1, int param2, int param3, int param4) {
      try {
         return this.getConnection().getListHelper().getDefaultDataEntryProfile(param1, param2, param3, param4);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new RuntimeException("can\'t get DefaultDataEntryProfile", var5);
      }
   }

   public String getProcessName() {
      String ret = "Processing DataEntryProfile";
      return ret;
   }

   protected int getProcessID() {
      return 72;
   }
}
