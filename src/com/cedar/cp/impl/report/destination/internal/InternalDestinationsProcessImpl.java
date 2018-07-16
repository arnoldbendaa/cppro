// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.destination.internal;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.internal.InternalDestinationEditorSession;
import com.cedar.cp.api.report.destination.internal.InternalDestinationsProcess;
import com.cedar.cp.ejb.api.report.destination.internal.InternalDestinationEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.destination.internal.InternalDestinationEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class InternalDestinationsProcessImpl extends BusinessProcessImpl implements InternalDestinationsProcess {

   private Log mLog = new Log(this.getClass());


   public InternalDestinationsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      InternalDestinationEditorSessionServer es = new InternalDestinationEditorSessionServer(this.getConnection());

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

   public InternalDestinationEditorSession getInternalDestinationEditorSession(Object key) throws ValidationException {
      InternalDestinationEditorSessionImpl sess = new InternalDestinationEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllInternalDestinations() {
      try {
         return this.getConnection().getListHelper().getAllInternalDestinations();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllInternalDestinations", var2);
      }
   }

   public EntityList getAllInternalDestinationDetails() {
      try {
         return this.getConnection().getListHelper().getAllInternalDestinationDetails();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllInternalDestinationDetails", var2);
      }
   }

   public EntityList getAllUsersForInternalDestinationId(int param1) {
      try {
         return this.getConnection().getListHelper().getAllUsersForInternalDestinationId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllUsersForInternalDestinationId", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing InternalDestination";
      return ret;
   }

   protected int getProcessID() {
      return 78;
   }

   public EntityList getDistinctInternalDestinationUsers(String[] ids) {
      try {
         return this.getConnection().getListHelper().getDistinctInternalDestinationUsers(ids);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllDistinctInternalDestinationUsers", var3);
      }
   }
}
