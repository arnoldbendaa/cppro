// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityKeyFactory;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.xmlreport.XmlReportEditor;
import com.cedar.cp.api.xmlreport.XmlReportEditorSession;
import com.cedar.cp.api.xmlreport.XmlReportsProcess;
import com.cedar.cp.api.xmlreportfolder.XmlReportFoldersProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.xmlreports.FolderDTO;
import com.cedar.cp.utc.struts.xmlreports.MoveXmlReportForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MoveReportSetupAction extends CPAction {

   public static final int PUBLIC_FOLDER = -1;
   public static final int PRIVATE_FOLDER = -2;


   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      MoveXmlReportForm myForm = (MoveXmlReportForm)actionForm;
      CPContext cpContext = this.getCPContext(httpServletRequest);
      CPConnection cpConnection = cpContext.getCPConnection();
      if(myForm.getNewFolderId() != 0) {
         int newUserId1 = cpConnection.getUserContext().getUserId();
         int newFolderId1 = myForm.getNewFolderId();
         if(newFolderId1 == -1) {
            newFolderId1 = 0;
            newUserId1 = 0;
         } else if(newFolderId1 == -2) {
            newFolderId1 = 0;
         } else {
            XmlReportFoldersProcess process1 = cpConnection.getXmlReportFoldersProcess();
            EntityList factory1 = process1.getReportFolderWithId(newFolderId1);
            newUserId1 = ((Integer)factory1.getValueAt(0, "UserId")).intValue();
         }

         XmlReportsProcess process2 = cpConnection.getXmlReportsProcess();
         EntityKeyFactory factory2 = cpConnection.getEntityKeyFactory();
         Object key1 = factory2.getKeyFromTokens(myForm.getReportKey());
         XmlReportEditorSession session1 = process2.getXmlReportEditorSession(key1);
         XmlReportEditor editor1 = session1.getXmlReportEditor();
         editor1.setUserId(newUserId1);
         editor1.setXmlReportFolderId(newFolderId1);
         editor1.commit();
         session1.commit(false);
         return actionMapping.findForward("all_done");
      } else {
         XmlReportFoldersProcess newUserId = cpConnection.getXmlReportFoldersProcess();
         EntityList newFolderId = newUserId.getAllPublicXmlReportFolders();
         EntityList process = newUserId.getAllXmlReportFoldersForUser(cpConnection.getUserContext().getUserId());
         HashMap factory = new HashMap();
         boolean key = cpContext.getIsUserAdministrator();
         ArrayList session = new ArrayList();
         FolderDTO editor;
         Iterator iter;
         EntityRef ref;
         List row;
         Object primaryKey;
         Integer level;
         String folderId;
         FolderDTO newFolder;
         if(key) {
            editor = new FolderDTO();
            editor.setPublic(true);
            editor.setEditable(key);
            editor.setFolderId(String.valueOf(-1));
            editor.setReportsId("PUBR0");
            editor.setName("Public Reports");
            editor.setLevel(1);
            session.add(editor);
            factory.put("PUBF", editor);
            iter = newFolderId.getDataAsList().iterator();

            while(iter.hasNext()) {
               row = (List)iter.next();
               ref = (EntityRef)row.get(0);
               level = (Integer)row.get(1);
               primaryKey = row.get(2);
               newFolder = new FolderDTO();
               newFolder.setFolderKey(ref.getTokenizedKey());
               newFolder.setPublic(true);
               newFolder.setEditable(key);
               folderId = primaryKey.toString();
               newFolder.setFolderId(folderId);
               newFolder.setReportsId("PUBR" + primaryKey.toString());
               newFolder.setName(ref.toString());
               newFolder.setLevel(level.intValue() + 1);
               session.add(newFolder);
               factory.put(folderId, newFolder);
            }
         }

         editor = new FolderDTO();
         editor.setPublic(false);
         editor.setEditable(true);
         editor.setFolderId(String.valueOf(-2));
         editor.setReportsId("PRIR0");
         editor.setName("My Reports");
         editor.setLevel(1);
         session.add(editor);
         factory.put("PRIF", editor);
         iter = process.getDataAsList().iterator();

         while(iter.hasNext()) {
            row = (List)iter.next();
            ref = (EntityRef)row.get(0);
            level = (Integer)row.get(1);
            primaryKey = row.get(2);
            newFolder = new FolderDTO();
            newFolder.setPublic(false);
            newFolder.setEditable(true);
            folderId = primaryKey.toString();
            newFolder.setFolderKey(ref.getTokenizedKey());
            newFolder.setFolderId(folderId);
            newFolder.setReportsId("PRIR" + primaryKey.toString());
            newFolder.setName(ref.toString());
            newFolder.setLevel(level.intValue() + 1);
            session.add(newFolder);
            factory.put(folderId, newFolder);
         }

         myForm.setFolders(session);
         return actionMapping.findForward("success");
      }
   }
}
