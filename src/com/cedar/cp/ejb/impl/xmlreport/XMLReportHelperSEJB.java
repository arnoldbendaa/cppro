// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportDAO;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlreport.ReportConfig;
import com.cedar.cp.util.xmlreport.reader.XMLReader;
import java.io.StringReader;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class XMLReportHelperSEJB implements SessionBean {

   private SessionContext mContext;
   private transient Log _log = new Log(this.getClass());


   public ReportConfig getXMLReportConfig(String reportId) throws EJBException {
      try {
         XmlReportPK e = XmlReportPK.getKeyFromTokens(reportId);
         XmlReportDAO dao = new XmlReportDAO();
         XmlReportEVO evo = dao.getDetails(e, (String)null);
         String definition = evo.getDefinition();
         Object result = null;
         XMLReader reader = new XMLReader();
         reader.init(reportId);
         StringReader sr = new StringReader(definition);
         reader.parseConfigFile(sr);
         return reader.getReportConfig();
      } catch (Exception var9) {
         throw new EJBException(var9);
      }
   }

   public void ejbCreate() {}

   public void ejbActivate() throws EJBException, RemoteException {}

   public void ejbPassivate() throws EJBException, RemoteException {}

   public void ejbRemove() throws EJBException, RemoteException {}

   public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
      this.mContext = sessionContext;
   }
}
