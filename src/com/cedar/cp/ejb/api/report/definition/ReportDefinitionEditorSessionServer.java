// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.definition;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.api.report.definition.WebReportOption;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionCSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionSSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionHome;
import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionLocal;
import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionLocalHome;
import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.ValueMapping;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ReportDefinitionEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ReportDefinitionEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ReportDefinitionEditorSessionLocalHome";
   protected ReportDefinitionEditorSessionRemote mRemote;
   protected ReportDefinitionEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ReportDefinitionEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ReportDefinitionEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ReportDefinitionEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            ReportDefinitionEditorSessionHome e = (ReportDefinitionEditorSessionHome)this.getHome(jndiName, ReportDefinitionEditorSessionHome.class);
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

   private ReportDefinitionEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            ReportDefinitionEditorSessionLocalHome e = (ReportDefinitionEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ReportDefinitionEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportDefinitionEditorSessionSSO e = null;
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

   public ReportDefinitionEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportDefinitionEditorSessionSSO e = null;
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

   public ReportDefinitionPK insert(ReportDefinitionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportDefinitionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ReportDefinitionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ReportDefinitionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportDefinitionPK copy(ReportDefinitionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ReportDefinitionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ReportDefinitionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ReportDefinitionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ReportDefinitionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ReportDefinitionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ReportDefinitionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails() throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getReportDefinitionFormRunDetails():this.getLocal().getReportDefinitionFormRunDetails();
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails() throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getReportDefinitionMappingRunDetails():this.getLocal().getReportDefinitionMappingRunDetails();
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails() throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getReportDefinitionCalculationRunDetails():this.getLocal().getReportDefinitionCalculationRunDetails();
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails() throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getReportDefinitionSumaryCalculationRunDetails():this.getLocal().getReportDefinitionSumaryCalculationRunDetails();
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public ValueMapping buildReportDefValueMapping() throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().buildReportDefValueMapping():this.getLocal().buildReportDefValueMapping();
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public ValueMapping buildAutoExpendDepthValueMapping() throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().buildAutoExpendDepthValueMapping():this.getLocal().buildAutoExpendDepthValueMapping();
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public int issueReport(int userId, WebReportOption wro) throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueReport(userId, wro):this.getLocal().issueReport(userId, wro);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public List<Integer> issueWebReport(int userId, WebReportOption wro, int taskId) throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueWebReport(userId, wro, taskId):this.getLocal().issueWebReport(userId, wro, taskId);
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ReportDefinitionEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ReportDefinitionEditorSessionLocalHome";
   }
}
