// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftest;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.perftest.AllPerfTestsELO;
import com.cedar.cp.dto.perftest.PerfTestPK;
import com.cedar.cp.ejb.impl.perftest.PerfTestDAO;
import com.cedar.cp.ejb.impl.perftest.PerfTestEVO;
import com.cedar.cp.ejb.impl.perftest.PerfTestLocal;
import com.cedar.cp.ejb.impl.perftest.PerfTestLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PerfTestAccessor implements Serializable {

   private PerfTestLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public PerfTestAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private PerfTestLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (PerfTestLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/PerfTestLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up PerfTestLocalHome", var2);
      }
   }

   private PerfTestLocal getLocal(PerfTestPK pk) throws Exception {
      PerfTestLocal local = (PerfTestLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public PerfTestEVO create(PerfTestEVO evo) throws Exception {
      PerfTestLocal local = this.getLocalHome().create(evo);
      PerfTestEVO newevo = local.getDetails("<UseLoadedEVOs>");
      PerfTestPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(PerfTestPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public PerfTestEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof PerfTestPK?this.getLocal((PerfTestPK)key).getDetails(dependants):null;
   }

   public void setDetails(PerfTestEVO evo) throws Exception {
      PerfTestPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public PerfTestEVO setAndGetDetails(PerfTestEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public PerfTestPK generateKeys(PerfTestPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllPerfTestsELO getAllPerfTests() {
      PerfTestDAO dao = new PerfTestDAO();
      return dao.getAllPerfTests();
   }
}
