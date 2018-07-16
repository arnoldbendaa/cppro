// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreport;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreport.XmlReport;
import com.cedar.cp.api.xmlreport.XmlReportEditor;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionSSO;
import com.cedar.cp.dto.xmlreport.XmlReportImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.xmlreport.XmlReportAdapter;
import com.cedar.cp.impl.xmlreport.XmlReportEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class XmlReportEditorImpl extends BusinessEditorImpl implements XmlReportEditor {

   private XmlReportEditorSessionSSO mServerSessionData;
   private XmlReportImpl mEditorData;
   private XmlReportAdapter mEditorDataAdapter;


   public XmlReportEditorImpl(XmlReportEditorSessionImpl session, XmlReportEditorSessionSSO serverSessionData, XmlReportImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(XmlReportEditorSessionSSO serverSessionData, XmlReportImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setXmlReportFolderId(int newXmlReportFolderId) throws ValidationException {
      this.validateXmlReportFolderId(newXmlReportFolderId);
      if(this.mEditorData.getXmlReportFolderId() != newXmlReportFolderId) {
         this.setContentModified();
         this.mEditorData.setXmlReportFolderId(newXmlReportFolderId);
      }
   }

   public void setUserId(int newUserId) throws ValidationException {
      this.validateUserId(newUserId);
      if(this.mEditorData.getUserId() != newUserId) {
         this.setContentModified();
         this.mEditorData.setUserId(newUserId);
      }
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDefinition(String newDefinition) throws ValidationException {
      if(newDefinition != null) {
         newDefinition = StringUtils.rtrim(newDefinition);
      }

      this.validateDefinition(newDefinition);
      if(this.mEditorData.getDefinition() == null || !this.mEditorData.getDefinition().equals(newDefinition)) {
         this.setContentModified();
         this.mEditorData.setDefinition(newDefinition);
      }
   }

   public void validateXmlReportFolderId(int newXmlReportFolderId) throws ValidationException {}

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 120) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 120 on a XmlReport");
      }
   }

   public void validateUserId(int newUserId) throws ValidationException {}

   public void validateDefinition(String newDefinition) throws ValidationException {}

   public XmlReport getXmlReport() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new XmlReportAdapter((XmlReportEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
