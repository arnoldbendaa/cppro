// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.definition;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionEditorSession;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionsProcess;
import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.definition.ReportDefinitionEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.ValueMapping;

public class ReportDefinitionsProcessImpl extends BusinessProcessImpl implements ReportDefinitionsProcess {

   private Log mLog = new Log(this.getClass());


   public ReportDefinitionsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ReportDefinitionEditorSessionServer es = new ReportDefinitionEditorSessionServer(this.getConnection());

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

   public ReportDefinitionEditorSession getReportDefinitionEditorSession(Object key) throws ValidationException {
      ReportDefinitionEditorSessionImpl sess = new ReportDefinitionEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllReportDefinitions() {
      try {
         return this.getConnection().getListHelper().getAllReportDefinitions();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReportDefinitions", var2);
      }
   }
   
   public EntityList getAllReportDefinitionsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllReportDefinitionsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllReportDefinitionsForLoggedUser", var2);
      }
   }

   public EntityList getAllPublicReportByType(int param1) {
      try {
         return this.getConnection().getListHelper().getAllPublicReportByType(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllPublicReportByType", var3);
      }
   }

   public EntityList getReportDefinitionForVisId(String param1) {
      try {
         return this.getConnection().getListHelper().getReportDefinitionForVisId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ReportDefinitionForVisId", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing ReportDefinition";
      return ret;
   }

   protected int getProcessID() {
      return 75;
   }

   public ReportDefinitionEditorSession getReportDefinitionEditorSession(String visid) throws ValidationException {
      EntityList eList = this.getConnection().getListHelper().getReportDefinitionForVisId(visid);
      EntityRef eRef = (EntityRef)eList.getValueAt(0, "ReportDefinition");
      return this.getReportDefinitionEditorSession(eRef.getPrimaryKey());
   }

   public ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails(String visid) throws ValidationException {
      ReportDefinitionEditorSession session = this.getReportDefinitionEditorSession(visid);
      return session.getReportDefinitionFormRunDetails();
   }

   public ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails(String visid) throws ValidationException {
      ReportDefinitionEditorSession session = this.getReportDefinitionEditorSession(visid);
      return session.getReportDefinitionCalculationRunDetails();
   }

   public ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails(String visid) throws ValidationException {
      ReportDefinitionEditorSession session = this.getReportDefinitionEditorSession(visid);
      return session.getReportDefinitionSumaryCalculationRunDetails();
   }

   public ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails(String visid) throws ValidationException {
      ReportDefinitionEditorSession session = this.getReportDefinitionEditorSession(visid);
      return session.getReportDefinitionMappingRunDetails();
   }

   public ValueMapping buildReportDefValueMapping() throws ValidationException {
      ReportDefinitionEditorSessionServer sessionServer = new ReportDefinitionEditorSessionServer(this.getConnection());
      return sessionServer.buildReportDefValueMapping();
   }

   public ValueMapping buildAutoExpendDepthValueMapping() throws ValidationException {
      ReportDefinitionEditorSessionServer sessionServer = new ReportDefinitionEditorSessionServer(this.getConnection());
      return sessionServer.buildAutoExpendDepthValueMapping();
   }
}
