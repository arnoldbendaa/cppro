// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.mappingtemplate;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionCSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateImpl;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.report.mappingtemplate.ReportMappingTemplateEditorSessionHome;
import com.cedar.cp.ejb.api.report.mappingtemplate.ReportMappingTemplateEditorSessionLocal;
import com.cedar.cp.ejb.api.report.mappingtemplate.ReportMappingTemplateEditorSessionLocalHome;
import com.cedar.cp.ejb.api.report.mappingtemplate.ReportMappingTemplateEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ReportMappingTemplateEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ReportMappingTemplateEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ReportMappingTemplateEditorSessionLocalHome";
   protected ReportMappingTemplateEditorSessionRemote mRemote;
   protected ReportMappingTemplateEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ReportMappingTemplateEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ReportMappingTemplateEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ReportMappingTemplateEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            ReportMappingTemplateEditorSessionHome e = (ReportMappingTemplateEditorSessionHome)this.getHome(jndiName, ReportMappingTemplateEditorSessionHome.class);
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

   private ReportMappingTemplateEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            ReportMappingTemplateEditorSessionLocalHome e = (ReportMappingTemplateEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ReportMappingTemplateEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportMappingTemplateEditorSessionSSO e = null;
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

   public ReportMappingTemplateEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportMappingTemplateEditorSessionSSO e = null;
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

   public ReportMappingTemplatePK insert(ReportMappingTemplateImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportMappingTemplatePK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ReportMappingTemplateEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ReportMappingTemplateEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportMappingTemplatePK copy(ReportMappingTemplateImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportMappingTemplatePK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ReportMappingTemplateEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ReportMappingTemplateEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ReportMappingTemplateImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ReportMappingTemplateEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ReportMappingTemplateEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ReportMappingTemplateEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ReportMappingTemplateEditorSessionLocalHome";
   }
}
