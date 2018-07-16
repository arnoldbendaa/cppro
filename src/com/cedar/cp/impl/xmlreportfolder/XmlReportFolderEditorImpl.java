// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreportfolder;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolder;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderEditor;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionSSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.xmlreportfolder.XmlReportFolderAdapter;
import com.cedar.cp.impl.xmlreportfolder.XmlReportFolderEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class XmlReportFolderEditorImpl extends BusinessEditorImpl implements XmlReportFolderEditor {

   private XmlReportFolderEditorSessionSSO mServerSessionData;
   private XmlReportFolderImpl mEditorData;
   private XmlReportFolderAdapter mEditorDataAdapter;


   public XmlReportFolderEditorImpl(XmlReportFolderEditorSessionImpl session, XmlReportFolderEditorSessionSSO serverSessionData, XmlReportFolderImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(XmlReportFolderEditorSessionSSO serverSessionData, XmlReportFolderImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setParentFolderId(int newParentFolderId) throws ValidationException {
      this.validateParentFolderId(newParentFolderId);
      if(this.mEditorData.getParentFolderId() != newParentFolderId) {
         this.setContentModified();
         this.mEditorData.setParentFolderId(newParentFolderId);
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

   public void validateParentFolderId(int newParentFolderId) throws ValidationException {}

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 120) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 120 on a XmlReportFolder");
      }
   }

   public void validateUserId(int newUserId) throws ValidationException {}

   public XmlReportFolder getXmlReportFolder() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new XmlReportFolderAdapter((XmlReportFolderEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
