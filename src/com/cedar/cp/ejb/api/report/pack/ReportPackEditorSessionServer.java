// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.pack;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.report.pack.ReportPackProjection;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionCSO;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionSSO;
import com.cedar.cp.dto.report.pack.ReportPackImpl;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionHome;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionLocal;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionLocalHome;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ReportPackEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ReportPackEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ReportPackEditorSessionLocalHome";
   protected ReportPackEditorSessionRemote mRemote;
   protected ReportPackEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ReportPackEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ReportPackEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ReportPackEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            ReportPackEditorSessionHome e = (ReportPackEditorSessionHome)this.getHome(jndiName, ReportPackEditorSessionHome.class);
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

   private ReportPackEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            ReportPackEditorSessionLocalHome e = (ReportPackEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ReportPackEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportPackEditorSessionSSO e = null;
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

   public ReportPackEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportPackEditorSessionSSO e = null;
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

   public ReportPackPK insert(ReportPackImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportPackPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ReportPackEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ReportPackEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportPackPK copy(ReportPackImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportPackPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ReportPackEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ReportPackEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ReportPackImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ReportPackEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ReportPackEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueReport(int intUserId, EntityRef ref, ReportPackOption options) throws ValidationException {
      return this.issueReport(intUserId, ref, options, 0);
   }

   public int issueReport(int intUserId, EntityRef ref, ReportPackOption options, int issueingTaskId) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueReport(intUserId, ref, options, issueingTaskId):this.getLocal().issueReport(intUserId, ref, options, issueingTaskId);
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public List issueReportLine(int intUserId, EntityRef definition, EntityRef distribution, ReportPackOption options, boolean group, int issueingTaskId) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueReportLine(intUserId, definition, distribution, options, group, issueingTaskId):this.getLocal().issueReportLine(intUserId, definition, distribution, options, group, issueingTaskId);
      } catch (Exception var8) {
         throw this.unravelException(var8);
      }
   }

   public ReportPackProjection getReportPackProjection(int intUserId, Object key) throws ValidationException {
      ReportPackProjection result = null;

      try {
         if(this.isRemoteConnection()) {
            result = this.getRemote().getReportPackProjection(intUserId, key);
         } else {
            result = this.getLocal().getReportPackProjection(intUserId, key);
         }

         return result;
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ReportPackEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ReportPackEditorSessionLocalHome";
   }
}
