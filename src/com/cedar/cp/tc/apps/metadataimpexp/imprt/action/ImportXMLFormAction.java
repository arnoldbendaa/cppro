// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.action;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlFormEditor;
import com.cedar.cp.api.xmlform.XmlFormEditorSession;
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.action.ImportAction;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.services.FinanceCubeService;
import com.cedar.cp.tc.apps.metadataimpexp.services.XMLFormService;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.xmlform.FormConfig;

import java.io.StringWriter;

public class ImportXMLFormAction extends ImportAction {

   protected XmlFormsProcess process = null;
   private FinanceCubeService cubeService = new FinanceCubeService();
   private XMLFormService formService = new XMLFormService();


   public ImportXMLFormAction(CommonImpExpItem importObj) {
      super(importObj);
      this.process = this.applicationState.getConnection().getXmlFormsProcess();
   }

   public int doImport() {
      if(this.importObj != null && this.importObj.getTreeNodeType() == 2 && this.importObj instanceof XMLFormModel) {
         try {
            Object ex = this.applicationState.queryXmlFormPK(this.importObj.getItemName());
            if(ex == null) {
               this.addNewForm();
            } else if(ex != null && this.importObj.isOverwrite()) {
               XmlFormEditorSession session = this.process.getXmlFormEditorSession(ex);
               XmlFormEditor editor = session.getXmlFormEditor();
               XMLFormModel xmlFormModel = (XMLFormModel)this.importObj;
               this.populateXMLFormEditor(editor, xmlFormModel);
               editor.commit();
               session.commit(false);
               this.process.terminateSession(session);
            } else if(ex != null && this.importObj.hasAlternativeName() && this.importObj.getAlternativeName() != null && this.importObj.getAlternativeName().trim().length() > 0) {
               this.addNewForm();
            }
         } catch (Exception var5) {
            this.importObj.setErrorMsg(var5.getMessage());
            return -1;
         }
      }

      return 1;
   }

   private void addNewForm() throws Exception {
      XmlFormEditorSession session = this.process.getXmlFormEditorSession((Object)null);
      XmlFormEditor editor = session.getXmlFormEditor();
      XMLFormModel xmlFormModel = (XMLFormModel)this.importObj;
      this.populateXMLFormEditor(editor, xmlFormModel);
      editor.commit();
      session.commit(false);
      this.process.terminateSession(session);
   }

   private void populateXMLFormEditor(XmlFormEditor editor, XMLFormModel xmlFormModel) throws Exception {
      if(editor != null && xmlFormModel != null) {
         editor.setDescription(xmlFormModel.getDescription());
         editor.setType(xmlFormModel.getFormType());
         FormConfig formConfig = xmlFormModel.getFormConfig();
         if(formConfig != null && (xmlFormModel.getFormType() != 4) && (xmlFormModel.getFormType() != 6) && (xmlFormModel.getFormType() != 7)) {
            editor.setDefinition(this.formService.updateSchemaLocation(formConfig));
         } else if(xmlFormModel.getWorkBook() != null && xmlFormModel.getFormType() == 4) {
            Workbook valueMapping = xmlFormModel.getWorkBook();
            StringWriter financeCubeId = new StringWriter();
            valueMapping.writeXml(financeCubeId);
            if(financeCubeId != null) {
               editor.setDefinition(financeCubeId.toString());
            }
         } else if ((xmlFormModel.getWorkBook() != null) && (xmlFormModel.getFormType() == 6) || (xmlFormModel.getFormType() == 7)) {
             Workbook valueMapping = xmlFormModel.getWorkBook();
             StringWriter financeCubeId = new StringWriter();
             valueMapping.writeXml(financeCubeId);
             if(financeCubeId != null) {
                editor.setDefinition(financeCubeId.toString());
             }
             editor.setExcelFile(xmlFormModel.getExcelFile());
         } else {
            editor.setDefinition(xmlFormModel.getDefFileContent());
         }

         if(xmlFormModel.getFinanceCubeVisId() != null && xmlFormModel.getFinanceCubeVisId().trim().length() > 0) {
            DefaultValueMapping valueMapping1 = this.cubeService.buildFinanceCubeMapping();
            int financeCubeId1 = ((Integer)valueMapping1.getValue(xmlFormModel.getFinanceCubeVisId())).intValue();
            editor.setFinanceCubeId(financeCubeId1);
         }

         if(!xmlFormModel.hasAlternativeName()) {
            editor.setVisId(xmlFormModel.getItemName());
         } else {
            editor.setVisId(xmlFormModel.getAlternativeName());
         }
         String json = toJSON(xmlFormModel.getExcelFile());
         editor.setJsonForm(json);
      }
   }
   
   private String toJSON(byte[] xls) throws ValidationException {
       String json = "";
       /* ExcelIO converter */
       //ExcelIOServiceImpl excelIO = new ExcelIOServiceImpl();
       //json = excelIO.toJSON(xmlFormModel.getExcelFile()).toString();
       
       /* Wijmo converter */
       json = process.getConnection().getExcelIOProcess().convertXlsToJson(xls, "");

       return json;
   }
}
