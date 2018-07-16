// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.type;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionCSO;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionSSO;
import com.cedar.cp.dto.report.type.ReportTypeImpl;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.report.type.ReportTypeEditorSessionHome;
import com.cedar.cp.ejb.api.report.type.ReportTypeEditorSessionLocal;
import com.cedar.cp.ejb.api.report.type.ReportTypeEditorSessionLocalHome;
import com.cedar.cp.ejb.api.report.type.ReportTypeEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ReportTypeEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ReportTypeEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ReportTypeEditorSessionLocalHome";
   protected ReportTypeEditorSessionRemote mRemote;
   protected ReportTypeEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ReportTypeEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ReportTypeEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ReportTypeEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            ReportTypeEditorSessionHome e = (ReportTypeEditorSessionHome)this.getHome(jndiName, ReportTypeEditorSessionHome.class);
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

   private ReportTypeEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            ReportTypeEditorSessionLocalHome e = (ReportTypeEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ReportTypeEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportTypeEditorSessionSSO e = null;
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

   public ReportTypeEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportTypeEditorSessionSSO e = null;
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

   public ReportTypePK insert(ReportTypeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportTypePK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ReportTypeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ReportTypeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportTypePK copy(ReportTypeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportTypePK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ReportTypeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ReportTypeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ReportTypeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ReportTypeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ReportTypeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ReportTypeEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ReportTypeEditorSessionLocalHome";
   }
}
