// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.task;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.task.ReportGroupingCK;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingEVO;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingLocal;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportGroupingAccessor implements Serializable {

   private ReportGroupingLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_REPORT_GROUP_FILES = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public ReportGroupingAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ReportGroupingLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ReportGroupingLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ReportGroupingLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ReportGroupingLocalHome", var2);
      }
   }

   private ReportGroupingLocal getLocal(ReportGroupingPK pk) throws Exception {
      ReportGroupingLocal local = (ReportGroupingLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ReportGroupingEVO create(ReportGroupingEVO evo) throws Exception {
      ReportGroupingLocal local = this.getLocalHome().create(evo);
      ReportGroupingEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ReportGroupingPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ReportGroupingPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ReportGroupingEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ReportGroupingCK) {
         ReportGroupingPK pk = ((ReportGroupingCK)key).getReportGroupingPK();
         return this.getLocal(pk).getDetails((ReportGroupingCK)key, dependants);
      } else {
         return key instanceof ReportGroupingPK?this.getLocal((ReportGroupingPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ReportGroupingEVO evo) throws Exception {
      ReportGroupingPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ReportGroupingEVO setAndGetDetails(ReportGroupingEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ReportGroupingPK generateKeys(ReportGroupingPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }
}
