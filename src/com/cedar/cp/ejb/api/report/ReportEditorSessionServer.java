// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.ReportEditorSessionCSO;
import com.cedar.cp.dto.report.ReportEditorSessionSSO;
import com.cedar.cp.dto.report.ReportImpl;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.report.ReportEditorSessionHome;
import com.cedar.cp.ejb.api.report.ReportEditorSessionLocal;
import com.cedar.cp.ejb.api.report.ReportEditorSessionLocalHome;
import com.cedar.cp.ejb.api.report.ReportEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ReportEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ReportEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ReportEditorSessionLocalHome";
   protected ReportEditorSessionRemote mRemote;
   protected ReportEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ReportEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ReportEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ReportEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            ReportEditorSessionHome e = (ReportEditorSessionHome)this.getHome(jndiName, ReportEditorSessionHome.class);
            this.mRemote = e.create();
         } catch (CreateException var3) {
            this.removeFromCache(jndiName);
            var3.printStackTrace();
            throw new CPException("getRemote " + jndiName + " CreateException", var3);
         } catch (RemoteException var4) {
            this.removeFromCache(jndiName);
            var4.printStackTrace();
            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
         }
      }

      return this.mRemote;
   }

   private ReportEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            ReportEditorSessionLocalHome e = (ReportEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public void delete(Object primaryKey_) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().delete(this.getUserId(), primaryKey_);
         } else {
            this.getLocal().delete(this.getUserId(), primaryKey_);
         }

         if(timer != null) {
            timer.logDebug("delete", primaryKey_.toString());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getNewItemData(this.getUserId());
         } else {
            e = this.getLocal().getNewItemData(this.getUserId());
         }

         if(timer != null) {
            timer.logDebug("getNewItemData", "");
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public ReportEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getItemData(this.getUserId(), key);
         } else {
            e = this.getLocal().getItemData(this.getUserId(), key);
         }

         if(timer != null) {
            timer.logDebug("getItemData", key.toString());
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportPK insert(ReportImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ReportEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ReportEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportPK copy(ReportImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ReportEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ReportEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ReportImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ReportEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ReportEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueReportUpdateTask(Object reportKey, boolean rollback) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().issueReportUpdateTask(this.getUserId(), reportKey, rollback);
         } else {
            e = this.getLocal().issueReportUpdateTask(this.getUserId(), reportKey, rollback);
         }

         if(timer != null) {
            timer.logDebug("performReportUpdate", reportKey);
         }

         return e;
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ReportEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ReportEditorSessionLocalHome";
   }
}
