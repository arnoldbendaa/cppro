// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cm;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.cm.ChangeManagementHome;
import com.cedar.cp.ejb.api.cm.ChangeManagementLocal;
import com.cedar.cp.ejb.api.cm.ChangeManagementLocalHome;
import com.cedar.cp.ejb.api.cm.ChangeManagementRemote;
import com.cedar.cp.ejb.impl.cm.ChangeManagementSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ChangeManagementServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ChangeManagementRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ChangeManagementLocalHome";
   protected ChangeManagementRemote mRemote;
   protected ChangeManagementLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static ChangeManagementSEJB server = new ChangeManagementSEJB(); 

   public ChangeManagementServer(CPConnection conn_) {
      super(conn_);
   }

   public ChangeManagementServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ChangeManagementSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            ChangeManagementHome e = (ChangeManagementHome)this.getHome(jndiName, ChangeManagementHome.class);
//            this.mRemote = e.create();
//         } catch (CreateException var3) {
//            this.removeFromCache(jndiName);
//            var3.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " CreateException", var3);
//         } catch (RemoteException var4) {
//            this.removeFromCache(jndiName);
//            var4.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//         }
//      }
//
//      return this.mRemote;
	   return this.server;
   }

   private ChangeManagementSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            ChangeManagementLocalHome e = (ChangeManagementLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return this.server;
   }

   public void removeSession() throws CPException {}

   public void registerUpdateRequest(String xmlRequest) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().registerUpdateRequest(xmlRequest);
         } else {
            this.getLocal().registerUpdateRequest(xmlRequest);
         }

         if(timer != null) {
            timer.logDebug("registerUpdateRequest");
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueUpdateTask(ModelRef model) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         UserPK e = (UserPK)this.getConnection().getUserContext().getPrimaryKey();
         int taskId;
         if(this.isRemoteConnection()) {
            taskId = this.getRemote().issueUpdateTask(e, model);
         } else {
            taskId = this.getLocal().issueUpdateTask(e, model);
         }

         if(timer != null) {
            timer.logDebug("issueUpdateTask(" + model + ")");
         }

         return taskId;
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ChangeManagementRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ChangeManagementLocalHome";
   }
}
