// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.destination.external;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.external.ExternalDestinationEditorSession;
import com.cedar.cp.api.report.destination.external.ExternalDestinationsProcess;
import com.cedar.cp.ejb.api.report.destination.external.ExternalDestinationEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.destination.external.ExternalDestinationEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ExternalDestinationsProcessImpl extends BusinessProcessImpl implements ExternalDestinationsProcess {

   private Log mLog = new Log(this.getClass());


   public ExternalDestinationsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ExternalDestinationEditorSessionServer es = new ExternalDestinationEditorSessionServer(this.getConnection());

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

   public ExternalDestinationEditorSession getExternalDestinationEditorSession(Object key) throws ValidationException {
      ExternalDestinationEditorSessionImpl sess = new ExternalDestinationEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllExternalDestinations() {
      try {
         return this.getConnection().getListHelper().getAllExternalDestinations();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllExternalDestinations", var2);
      }
   }

   public EntityList getAllExternalDestinationDetails() {
      try {
         return this.getConnection().getListHelper().getAllExternalDestinationDetails();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllExternalDestinationDetails", var2);
      }
   }

   public EntityList getAllUsersForExternalDestinationId(int param1) {
      try {
         return this.getConnection().getListHelper().getAllUsersForExternalDestinationId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllUsersForExternalDestinationId", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing ExternalDestination";
      return ret;
   }

   protected int getProcessID() {
      return 77;
   }
}
