// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.xmlreport.AllPublicXmlReportsELO;
import com.cedar.cp.dto.xmlreport.AllXmlReportsELO;
import com.cedar.cp.dto.xmlreport.AllXmlReportsForUserELO;
import com.cedar.cp.dto.xmlreport.SingleXmlReportELO;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.dto.xmlreport.XmlReportsForFolderELO;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportDAO;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportEVO;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportLocal;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class XmlReportAccessor implements Serializable {

   private XmlReportLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public XmlReportAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private XmlReportLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (XmlReportLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/XmlReportLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up XmlReportLocalHome", var2);
      }
   }

   private XmlReportLocal getLocal(XmlReportPK pk) throws Exception {
      XmlReportLocal local = (XmlReportLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public XmlReportEVO create(XmlReportEVO evo) throws Exception {
      XmlReportLocal local = this.getLocalHome().create(evo);
      XmlReportEVO newevo = local.getDetails("<UseLoadedEVOs>");
      XmlReportPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(XmlReportPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public XmlReportEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof XmlReportPK?this.getLocal((XmlReportPK)key).getDetails(dependants):null;
   }

   public void setDetails(XmlReportEVO evo) throws Exception {
      XmlReportPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public XmlReportEVO setAndGetDetails(XmlReportEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public XmlReportPK generateKeys(XmlReportPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllXmlReportsELO getAllXmlReports() {
      XmlReportDAO dao = new XmlReportDAO();
      return dao.getAllXmlReports();
   }

   public AllPublicXmlReportsELO getAllPublicXmlReports() {
      XmlReportDAO dao = new XmlReportDAO();
      return dao.getAllPublicXmlReports();
   }

   public AllXmlReportsForUserELO getAllXmlReportsForUser(int param1) {
      XmlReportDAO dao = new XmlReportDAO();
      return dao.getAllXmlReportsForUser(param1);
   }

   public XmlReportsForFolderELO getXmlReportsForFolder(int param1) {
      XmlReportDAO dao = new XmlReportDAO();
      return dao.getXmlReportsForFolder(param1);
   }

   public SingleXmlReportELO getSingleXmlReport(int param1, String param2) {
      XmlReportDAO dao = new XmlReportDAO();
      return dao.getSingleXmlReport(param1, param2);
   }
}
