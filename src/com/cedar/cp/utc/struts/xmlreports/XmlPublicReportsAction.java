// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.xmlreport.XmlReportsProcess;
import com.cedar.cp.api.xmlreportfolder.XmlReportFoldersProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.xmlreports.FolderDTO;
import com.cedar.cp.utc.struts.xmlreports.FolderReportDTO;
import com.cedar.cp.utc.struts.xmlreports.XmlReportsForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class XmlPublicReportsAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      XmlReportsForm myFormXml = (XmlReportsForm)actionForm;
      CPContext cpContext = this.getCPContext(httpServletRequest, "WEB_PROCESS.XMLReports");
      CPConnection cpConnection = cpContext.getCPConnection();
      XmlReportFoldersProcess folderProcess = cpConnection.getXmlReportFoldersProcess();
      EntityList publicFolders = folderProcess.getAllPublicXmlReportFolders();
      XmlReportsProcess process = cpConnection.getXmlReportsProcess();
      EntityList publicReports = process.getAllPublicXmlReports();
      HashMap folderMappings = new HashMap();
      boolean isAdministrator = cpContext.getIsUserAdministrator();
      ArrayList folders = new ArrayList();
      FolderDTO publicFolder = new FolderDTO();
      publicFolder.setPublic(true);
      publicFolder.setEditable(isAdministrator);
      publicFolder.setFolderId("PUBF");
      publicFolder.setReportsId("PUBR0");
      publicFolder.setName("Public Analysis");
      publicFolder.setLevel(1);
      folders.add(publicFolder);
      folderMappings.put("PUBF", publicFolder);
      Iterator iter = publicFolders.getDataAsList().iterator();

      List row;
      EntityRef ref;
      while(iter.hasNext()) {
         row = (List)iter.next();
         ref = (EntityRef)row.get(0);
         Integer folderId = (Integer)row.get(1);
         Object folder = row.get(2);
         FolderDTO report = new FolderDTO();
         report.setFolderKey(ref.getTokenizedKey());
         report.setPublic(true);
         report.setEditable(isAdministrator);
         String folderId1 = folder.toString();
         report.setFolderId(folderId1);
         report.setReportsId("PUBR" + folder.toString());
         report.setName(ref.toString());
         report.setLevel(folderId.intValue() + 1);
         folders.add(report);
         folderMappings.put(folderId1, report);
      }

      iter = publicReports.getDataAsList().iterator();

      while(iter.hasNext()) {
         row = (List)iter.next();
         ref = (EntityRef)row.get(0);
         String folderId2 = row.get(1).toString();
         if(folderId2.equals("0")) {
            folderId2 = "PUBF";
         }

         FolderDTO folder1 = (FolderDTO)folderMappings.get(folderId2);
         if(folder1 != null) {
            FolderReportDTO report1 = new FolderReportDTO();
            report1.setReportId(ref.getTokenizedKey());
            report1.setTitle(ref.toString());
            folder1.addReport(report1);
         }
      }

      myFormXml.setFolders(folders);
      return actionMapping.findForward("success");
   }
}
