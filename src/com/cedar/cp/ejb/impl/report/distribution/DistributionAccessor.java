// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.distribution;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.distribution.AllDistributionsELO;
import com.cedar.cp.dto.report.distribution.CheckExternalDestinationELO;
import com.cedar.cp.dto.report.distribution.CheckInternalDestinationELO;
import com.cedar.cp.dto.report.distribution.DistributionCK;
import com.cedar.cp.dto.report.distribution.DistributionDetailsForVisIdELO;
import com.cedar.cp.dto.report.distribution.DistributionForVisIdELO;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.ejb.impl.report.distribution.DistributionDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionEVO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLinkDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLocal;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DistributionAccessor implements Serializable {

   private DistributionLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_DISTRIBUTION_DESTINATION_LIST = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public DistributionAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private DistributionLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (DistributionLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/DistributionLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up DistributionLocalHome", var2);
      }
   }

   private DistributionLocal getLocal(DistributionPK pk) throws Exception {
      DistributionLocal local = (DistributionLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public DistributionEVO create(DistributionEVO evo) throws Exception {
      DistributionLocal local = this.getLocalHome().create(evo);
      DistributionEVO newevo = local.getDetails("<UseLoadedEVOs>");
      DistributionPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(DistributionPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public DistributionEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof DistributionCK) {
         DistributionPK pk = ((DistributionCK)key).getDistributionPK();
         return this.getLocal(pk).getDetails((DistributionCK)key, dependants);
      } else {
         return key instanceof DistributionPK?this.getLocal((DistributionPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(DistributionEVO evo) throws Exception {
      DistributionPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public DistributionEVO setAndGetDetails(DistributionEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public DistributionPK generateKeys(DistributionPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllDistributionsELO getAllDistributions() {
      DistributionDAO dao = new DistributionDAO();
      return dao.getAllDistributions();
   }

   public DistributionForVisIdELO getDistributionForVisId(String param1) {
      DistributionDAO dao = new DistributionDAO();
      return dao.getDistributionForVisId(param1);
   }

   public DistributionDetailsForVisIdELO getDistributionDetailsForVisId(String param1) {
      DistributionDAO dao = new DistributionDAO();
      return dao.getDistributionDetailsForVisId(param1);
   }

   public CheckInternalDestinationELO getCheckInternalDestination(int param1) {
      DistributionLinkDAO dao = new DistributionLinkDAO();
      return dao.getCheckInternalDestination(param1);
   }

   public CheckExternalDestinationELO getCheckExternalDestination(int param1) {
      DistributionLinkDAO dao = new DistributionLinkDAO();
      return dao.getCheckExternalDestination(param1);
   }
}
