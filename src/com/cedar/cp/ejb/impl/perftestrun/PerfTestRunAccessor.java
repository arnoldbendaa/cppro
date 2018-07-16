// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.perftestrun.AllPerfTestRunResultsELO;
import com.cedar.cp.dto.perftestrun.AllPerfTestRunsELO;
import com.cedar.cp.dto.perftestrun.PerfTestRunCK;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunDAO;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunEVO;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunLocal;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunLocalHome;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunResultDAO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PerfTestRunAccessor implements Serializable {

   private PerfTestRunLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_PERF_TEST_RUN_RESULTS = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public PerfTestRunAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private PerfTestRunLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (PerfTestRunLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/PerfTestRunLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up PerfTestRunLocalHome", var2);
      }
   }

   private PerfTestRunLocal getLocal(PerfTestRunPK pk) throws Exception {
      PerfTestRunLocal local = (PerfTestRunLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public PerfTestRunEVO create(PerfTestRunEVO evo) throws Exception {
      PerfTestRunLocal local = this.getLocalHome().create(evo);
      PerfTestRunEVO newevo = local.getDetails("<UseLoadedEVOs>");
      PerfTestRunPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(PerfTestRunPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public PerfTestRunEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof PerfTestRunCK) {
         PerfTestRunPK pk = ((PerfTestRunCK)key).getPerfTestRunPK();
         return this.getLocal(pk).getDetails((PerfTestRunCK)key, dependants);
      } else {
         return key instanceof PerfTestRunPK?this.getLocal((PerfTestRunPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(PerfTestRunEVO evo) throws Exception {
      PerfTestRunPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public PerfTestRunEVO setAndGetDetails(PerfTestRunEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public PerfTestRunPK generateKeys(PerfTestRunPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllPerfTestRunsELO getAllPerfTestRuns() {
      PerfTestRunDAO dao = new PerfTestRunDAO();
      return dao.getAllPerfTestRuns();
   }

   public AllPerfTestRunResultsELO getAllPerfTestRunResults() {
      PerfTestRunResultDAO dao = new PerfTestRunResultDAO();
      return dao.getAllPerfTestRunResults();
   }
}
