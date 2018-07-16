// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreport;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.xmlreport.XMLReportHelperHome;
import com.cedar.cp.ejb.api.xmlreport.XMLReportHelperLocal;
import com.cedar.cp.ejb.api.xmlreport.XMLReportHelperLocalHome;
import com.cedar.cp.ejb.api.xmlreport.XMLReportHelperRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlreport.ReportConfig;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class XMLReportHelperServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/XMLReportHelperRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/XMLReportHelperLocalHome";
   protected XMLReportHelperRemote mRemote;
   protected XMLReportHelperLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public XMLReportHelperServer(CPConnection conn_) {
      super(conn_);
   }

   public XMLReportHelperServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private XMLReportHelperRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            XMLReportHelperHome e = (XMLReportHelperHome)this.getHome(jndiName, XMLReportHelperHome.class);
            this.mRemote = e.create();
         } catch (CreateException var3) {
            this.removeFromCache(jndiName);
            var3.printStackTrace();
            throw new CPException("getRemote " + jndiName + " CreateException", var3);
         } catch (RemoteException var4) {
            this.removeFromCache(jndiName);
            var4.printStackTrace();
            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
         }
      }

      return this.mRemote;
   }

   private XMLReportHelperLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            XMLReportHelperLocalHome e = (XMLReportHelperLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public ReportConfig getXMLReportConfig(String reportId) throws ValidationException {
      ReportConfig result = null;

      try {
         if(this.isRemoteConnection()) {
            result = this.getRemote().getXMLReportConfig(reportId);
         } else {
            result = this.getLocal().getXMLReportConfig(reportId);
         }

         return result;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/XMLReportHelperRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/XMLReportHelperLocalHome";
   }
}
