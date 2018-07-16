// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreportfolder;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.xmlreportfolder.AllPublicXmlReportFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersForUserELO;
import com.cedar.cp.dto.xmlreportfolder.DecendentFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.ReportFolderWithIdELO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderDAO;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderEVO;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderLocal;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class XmlReportFolderAccessor implements Serializable {

   private XmlReportFolderLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public XmlReportFolderAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private XmlReportFolderLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (XmlReportFolderLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/XmlReportFolderLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up XmlReportFolderLocalHome", var2);
      }
   }

   private XmlReportFolderLocal getLocal(XmlReportFolderPK pk) throws Exception {
      XmlReportFolderLocal local = (XmlReportFolderLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public XmlReportFolderEVO create(XmlReportFolderEVO evo) throws Exception {
      XmlReportFolderLocal local = this.getLocalHome().create(evo);
      XmlReportFolderEVO newevo = local.getDetails("<UseLoadedEVOs>");
      XmlReportFolderPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(XmlReportFolderPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public XmlReportFolderEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof XmlReportFolderPK?this.getLocal((XmlReportFolderPK)key).getDetails(dependants):null;
   }

   public void setDetails(XmlReportFolderEVO evo) throws Exception {
      XmlReportFolderPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public XmlReportFolderEVO setAndGetDetails(XmlReportFolderEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public XmlReportFolderPK generateKeys(XmlReportFolderPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllXmlReportFoldersELO getAllXmlReportFolders() {
      XmlReportFolderDAO dao = new XmlReportFolderDAO();
      return dao.getAllXmlReportFolders();
   }

   public DecendentFoldersELO getDecendentFolders(int param1) {
      XmlReportFolderDAO dao = new XmlReportFolderDAO();
      return dao.getDecendentFolders(param1);
   }

   public ReportFolderWithIdELO getReportFolderWithId(int param1) {
      XmlReportFolderDAO dao = new XmlReportFolderDAO();
      return dao.getReportFolderWithId(param1);
   }

   public AllPublicXmlReportFoldersELO getAllPublicXmlReportFolders() {
      XmlReportFolderDAO dao = new XmlReportFolderDAO();
      return dao.getAllPublicXmlReportFolders();
   }

   public AllXmlReportFoldersForUserELO getAllXmlReportFoldersForUser(int param1) {
      XmlReportFolderDAO dao = new XmlReportFolderDAO();
      return dao.getAllXmlReportFoldersForUser(param1);
   }
}
