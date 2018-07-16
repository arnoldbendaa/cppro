// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.AllReportsELO;
import com.cedar.cp.dto.report.AllReportsForAdminELO;
import com.cedar.cp.dto.report.AllReportsForUserELO;
import com.cedar.cp.dto.report.ReportCK;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.dto.report.WebReportDetailsELO;
import com.cedar.cp.ejb.impl.report.ReportDAO;
import com.cedar.cp.ejb.impl.report.ReportEVO;
import com.cedar.cp.ejb.impl.report.ReportLocal;
import com.cedar.cp.ejb.impl.report.ReportLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportAccessor implements Serializable {

   private ReportLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_C_U_B_E__P_E_N_D_I_N_G__T_R_A_N = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public ReportAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ReportLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ReportLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ReportLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ReportLocalHome", var2);
      }
   }

   private ReportLocal getLocal(ReportPK pk) throws Exception {
      ReportLocal local = (ReportLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ReportEVO create(ReportEVO evo) throws Exception {
      ReportLocal local = this.getLocalHome().create(evo);
      ReportEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ReportPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ReportPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ReportEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ReportCK) {
         ReportPK pk = ((ReportCK)key).getReportPK();
         return this.getLocal(pk).getDetails((ReportCK)key, dependants);
      } else {
         return key instanceof ReportPK?this.getLocal((ReportPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ReportEVO evo) throws Exception {
      ReportPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ReportEVO setAndGetDetails(ReportEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ReportPK generateKeys(ReportPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllReportsELO getAllReports() {
      ReportDAO dao = new ReportDAO();
      return dao.getAllReports();
   }

   public AllReportsForUserELO getAllReportsForUser(int param1) {
      ReportDAO dao = new ReportDAO();
      return dao.getAllReportsForUser(param1);
   }

   public AllReportsForAdminELO getAllReportsForAdmin() {
      ReportDAO dao = new ReportDAO();
      return dao.getAllReportsForAdmin();
   }

   public WebReportDetailsELO getWebReportDetails(int param1) {
      ReportDAO dao = new ReportDAO();
      return dao.getWebReportDetails(param1);
   }
}
