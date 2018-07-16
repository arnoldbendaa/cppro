// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreport;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreport.XmlReportEditorSession;
import com.cedar.cp.api.xmlreport.XmlReportsProcess;
import com.cedar.cp.ejb.api.xmlreport.XMLReportHelperServer;
import com.cedar.cp.ejb.api.xmlreport.XmlReportEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.xmlreport.XmlReportEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.xmlreport.ReportConfig;

public class XmlReportsProcessImpl extends BusinessProcessImpl implements XmlReportsProcess {

   private Log mLog = new Log(this.getClass());


   public XmlReportsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      XmlReportEditorSessionServer es = new XmlReportEditorSessionServer(this.getConnection());

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

   public XmlReportEditorSession getXmlReportEditorSession(Object key) throws ValidationException {
      XmlReportEditorSessionImpl sess = new XmlReportEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllXmlReports() {
      try {
         return this.getConnection().getListHelper().getAllXmlReports();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllXmlReports", var2);
      }
   }

   public EntityList getAllPublicXmlReports() {
      try {
         return this.getConnection().getListHelper().getAllPublicXmlReports();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllPublicXmlReports", var2);
      }
   }

   public EntityList getAllXmlReportsForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllXmlReportsForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllXmlReportsForUser", var3);
      }
   }

   public EntityList getXmlReportsForFolder(int param1) {
      try {
         return this.getConnection().getListHelper().getXmlReportsForFolder(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get XmlReportsForFolder", var3);
      }
   }

   public EntityList getSingleXmlReport(int param1, String param2) {
      try {
         return this.getConnection().getListHelper().getSingleXmlReport(param1, param2);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException("can\'t get SingleXmlReport", var4);
      }
   }

   public String getProcessName() {
      String ret = "Processing XmlReport";
      return ret;
   }

   protected int getProcessID() {
      return 58;
   }

   public ReportConfig getXMLReportConfig(String reportId) throws ValidationException, CPException {
      XMLReportHelperServer server = new XMLReportHelperServer(this.getConnection());
      return server.getXMLReportConfig(reportId);
   }
}
