// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityKeyFactory;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreport.XmlReportsProcess;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderEditor;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderEditorSession;
import com.cedar.cp.api.xmlreportfolder.XmlReportFoldersProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.xmlreports.OrganiseReportsForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OrganiseReportsAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      OrganiseReportsForm myForm = (OrganiseReportsForm)actionForm;
      CPContext cpContext = this.getCPContext(httpServletRequest);
      CPConnection cpConnection = cpContext.getCPConnection();
      int userId = 0;
      if(!myForm.isPublic()) {
         userId = cpConnection.getUserContext().getUserId();
      }

      String action = myForm.getAction();
      if(action.equals("newFolder")) {
         this.newFolder(userId, cpConnection, myForm);
      } else if(action.equals("alterFolder")) {
         this.alterFolder(userId, cpConnection, myForm);
      } else if(action.equals("deleteFolder")) {
         this.deleteFolder(userId, cpConnection, myForm);
      } else if(action.equals("deleteReport")) {
         this.deleteReport(userId, cpConnection, myForm);
      }

      return actionMapping.findForward("success");
   }

   private void newFolder(int userId, CPConnection cpConnection, OrganiseReportsForm myForm) throws ValidationException {
      String name = myForm.getName();
      if(name != null && name.trim().length() > 0) {
         XmlReportFoldersProcess process = cpConnection.getXmlReportFoldersProcess();
         XmlReportFolderEditorSession session = process.getXmlReportFolderEditorSession((Object)null);
         XmlReportFolderEditor editor = session.getXmlReportFolderEditor();
         editor.setParentFolderId(myForm.getParentFolderId());
         editor.setUserId(userId);
         editor.setVisId(myForm.getName());
         editor.commit();
         session.commit(false);
      }

   }

   private void alterFolder(int userId, CPConnection cpConnection, OrganiseReportsForm myForm) throws ValidationException {
      String name = myForm.getName();
      if(name != null && name.trim().length() > 0) {
         XmlReportFoldersProcess process = cpConnection.getXmlReportFoldersProcess();
         EntityKeyFactory factory = cpConnection.getEntityKeyFactory();
         Object key = factory.getKeyFromTokens(myForm.getFolderKey());
         XmlReportFolderEditorSession session = process.getXmlReportFolderEditorSession(key);
         XmlReportFolderEditor editor = session.getXmlReportFolderEditor();
         editor.setVisId(myForm.getName());
         editor.commit();
         session.commit(false);
      }

   }

   private void deleteFolder(int userId, CPConnection cpConnection, OrganiseReportsForm myForm) throws ValidationException {
      if(myForm.getFolderKey() != null) {
         XmlReportFoldersProcess process = cpConnection.getXmlReportFoldersProcess();
         EntityKeyFactory factory = cpConnection.getEntityKeyFactory();
         Object key = factory.getKeyFromTokens(myForm.getFolderKey());
         process.deleteObject(key);
      }

   }

   private void deleteReport(int userId, CPConnection cpConnection, OrganiseReportsForm myForm) throws ValidationException {
      if(myForm.getReportKey() != null) {
         XmlReportsProcess process = cpConnection.getXmlReportsProcess();
         EntityKeyFactory factory = cpConnection.getEntityKeyFactory();
         Object key = factory.getKeyFromTokens(myForm.getReportKey());
         process.deleteObject(key);
      }

   }
}
