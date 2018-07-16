// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.definition;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionEditor;
import com.cedar.cp.api.report.definition.ReportDefinitionEditorSession;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.api.report.definition.WebReportOption;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionSSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionImpl;
import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.report.definition.ReportDefinitionEditorImpl;
import com.cedar.cp.impl.report.definition.ReportDefinitionsProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.ValueMapping;

public class ReportDefinitionEditorSessionImpl extends BusinessSessionImpl implements ReportDefinitionEditorSession {

   protected ReportDefinitionEditorSessionSSO mServerSessionData;
   protected ReportDefinitionImpl mEditorData;
   protected ReportDefinitionEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ReportDefinitionEditorSessionImpl(ReportDefinitionsProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get ReportDefinition", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ReportDefinitionEditorSessionServer getSessionServer() throws CPException {
      return new ReportDefinitionEditorSessionServer(this.getConnection());
   }

   public ReportDefinitionEditor getReportDefinitionEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ReportDefinitionEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
      if(this.mClientEditor != null) {
         this.mClientEditor.saveModifications();
      }

      if(this.mEditorData.getPrimaryKey() == null) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
      } else if(cloneOnSave) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
      } else {
         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {}

   public ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails() {
      try {
         return this.getSessionServer().getReportDefinitionFormRunDetails();
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage(), var2);
      }
   }

   public ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails() {
      try {
         return this.getSessionServer().getReportDefinitionMappingRunDetails();
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage(), var2);
      }
   }

   public ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails() {
      try {
         return this.getSessionServer().getReportDefinitionCalculationRunDetails();
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage(), var2);
      }
   }

   public ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails() {
      try {
         return this.getSessionServer().getReportDefinitionSumaryCalculationRunDetails();
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage(), var2);
      }
   }

   public ValueMapping buildReportDefValueMapping() {
      try {
         return this.getSessionServer().buildReportDefValueMapping();
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage(), var2);
      }
   }

   public ValueMapping buildAutoExpendDepthValueMapping() {
      try {
         return this.getSessionServer().buildAutoExpendDepthValueMapping();
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage(), var2);
      }
   }

   public int issueReport(int userId, WebReportOption wro) {
      try {
         return this.getSessionServer().issueReport(userId, wro);
      } catch (Exception var4) {
         throw new RuntimeException(var4.getMessage(), var4);
      }
   }
}
