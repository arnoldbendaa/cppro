// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreportfolder;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderEditorSession;
import com.cedar.cp.api.xmlreportfolder.XmlReportFoldersProcess;
import com.cedar.cp.ejb.api.xmlreportfolder.XmlReportFolderEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.xmlreportfolder.XmlReportFolderEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class XmlReportFoldersProcessImpl extends BusinessProcessImpl implements XmlReportFoldersProcess {

   private Log mLog = new Log(this.getClass());


   public XmlReportFoldersProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      XmlReportFolderEditorSessionServer es = new XmlReportFolderEditorSessionServer(this.getConnection());

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

   public XmlReportFolderEditorSession getXmlReportFolderEditorSession(Object key) throws ValidationException {
      XmlReportFolderEditorSessionImpl sess = new XmlReportFolderEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllXmlReportFolders() {
      try {
         return this.getConnection().getListHelper().getAllXmlReportFolders();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllXmlReportFolders", var2);
      }
   }

   public EntityList getDecendentFolders(int param1) {
      try {
         return this.getConnection().getListHelper().getDecendentFolders(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get DecendentFolders", var3);
      }
   }

   public EntityList getReportFolderWithId(int param1) {
      try {
         return this.getConnection().getListHelper().getReportFolderWithId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ReportFolderWithId", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing XmlReportFolder";
      return ret;
   }

   protected int getProcessID() {
      return 67;
   }

   public EntityList getAllPublicXmlReportFolders() {
      try {
         return this.getConnection().getListHelper().getAllPublicXmlReportFolders();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllPublicXmlReportFolders", var2);
      }
   }

   public EntityList getAllXmlReportFoldersForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllXmlReportFoldersForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllXmlReportFoldersForUser", var3);
      }
   }
}
