// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.type.AllReportTypesELO;
import com.cedar.cp.dto.report.type.ReportTypeCK;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.dto.report.type.param.AllReportTypeParamsELO;
import com.cedar.cp.dto.report.type.param.AllReportTypeParamsforTypeELO;
import com.cedar.cp.ejb.impl.report.type.ReportTypeDAO;
import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
import com.cedar.cp.ejb.impl.report.type.ReportTypeLocal;
import com.cedar.cp.ejb.impl.report.type.ReportTypeLocalHome;
import com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamDAO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportTypeAccessor implements Serializable {

   private ReportTypeLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_REPORT_TYPE_PARAMS = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public ReportTypeAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ReportTypeLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ReportTypeLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ReportTypeLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ReportTypeLocalHome", var2);
      }
   }

   private ReportTypeLocal getLocal(ReportTypePK pk) throws Exception {
      ReportTypeLocal local = (ReportTypeLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ReportTypeEVO create(ReportTypeEVO evo) throws Exception {
      ReportTypeLocal local = this.getLocalHome().create(evo);
      ReportTypeEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ReportTypePK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ReportTypePK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ReportTypeEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ReportTypeCK) {
         ReportTypePK pk = ((ReportTypeCK)key).getReportTypePK();
         return this.getLocal(pk).getDetails((ReportTypeCK)key, dependants);
      } else {
         return key instanceof ReportTypePK?this.getLocal((ReportTypePK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ReportTypeEVO evo) throws Exception {
      ReportTypePK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ReportTypeEVO setAndGetDetails(ReportTypeEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ReportTypePK generateKeys(ReportTypePK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllReportTypesELO getAllReportTypes() {
      ReportTypeDAO dao = new ReportTypeDAO();
      return dao.getAllReportTypes();
   }

   public AllReportTypeParamsELO getAllReportTypeParams() {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      return dao.getAllReportTypeParams();
   }

   public AllReportTypeParamsforTypeELO getAllReportTypeParamsforType(int param1) {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      return dao.getAllReportTypeParamsforType(param1);
   }
}
