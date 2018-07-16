// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.pack.AllReportPacksELO;
import com.cedar.cp.dto.report.pack.CheckReportDefELO;
import com.cedar.cp.dto.report.pack.CheckReportDistributionELO;
import com.cedar.cp.dto.report.pack.ReportDefDistListELO;
import com.cedar.cp.dto.report.pack.ReportPackCK;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.impl.report.pack.ReportPackDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLinkDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLocal;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportPackAccessor implements Serializable {

   private ReportPackLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_REPORT_PACK_DEFINITION_DISTRIBUTION_LIST = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public ReportPackAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ReportPackLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ReportPackLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ReportPackLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ReportPackLocalHome", var2);
      }
   }

   private ReportPackLocal getLocal(ReportPackPK pk) throws Exception {
      ReportPackLocal local = (ReportPackLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ReportPackEVO create(ReportPackEVO evo) throws Exception {
      ReportPackLocal local = this.getLocalHome().create(evo);
      ReportPackEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ReportPackPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ReportPackPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ReportPackEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ReportPackCK) {
         ReportPackPK pk = ((ReportPackCK)key).getReportPackPK();
         return this.getLocal(pk).getDetails((ReportPackCK)key, dependants);
      } else {
         return key instanceof ReportPackPK?this.getLocal((ReportPackPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ReportPackEVO evo) throws Exception {
      ReportPackPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ReportPackEVO setAndGetDetails(ReportPackEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ReportPackPK generateKeys(ReportPackPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllReportPacksELO getAllReportPacks() {
      ReportPackDAO dao = new ReportPackDAO();
      return dao.getAllReportPacks();
   }

   public ReportDefDistListELO getReportDefDistList(String param1) {
      ReportPackDAO dao = new ReportPackDAO();
      return dao.getReportDefDistList(param1);
   }

   public CheckReportDefELO getCheckReportDef(int param1) {
      ReportPackLinkDAO dao = new ReportPackLinkDAO();
      return dao.getCheckReportDef(param1);
   }

   public CheckReportDistributionELO getCheckReportDistribution(int param1) {
      ReportPackLinkDAO dao = new ReportPackLinkDAO();
      return dao.getCheckReportDistribution(param1);
   }
}
