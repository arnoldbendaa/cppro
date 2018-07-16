// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.external;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationDetailsELO;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
import com.cedar.cp.dto.report.destination.external.AllUsersForExternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationCK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationEVO;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationLocal;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ExternalDestinationAccessor implements Serializable {

   private ExternalDestinationLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_EXTERNAL_USER_LIST = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";

   ExternalDestinationEEJB externalDestinationEjb = new ExternalDestinationEEJB();
   public ExternalDestinationAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ExternalDestinationLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ExternalDestinationLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ExternalDestinationLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ExternalDestinationLocalHome", var2);
      }
   }

   private ExternalDestinationLocal getLocal(ExternalDestinationPK pk) throws Exception {
      ExternalDestinationLocal local = (ExternalDestinationLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ExternalDestinationEVO create(ExternalDestinationEVO evo) throws Exception {
//      ExternalDestinationLocal local = this.getLocalHome().create(evo);
//      ExternalDestinationEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      ExternalDestinationPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
	   externalDestinationEjb.ejbCreate(evo);
	   ExternalDestinationEVO newevo = externalDestinationEjb.getDetails("<UseLoadedEVOs>");
	   ExternalDestinationPK pk = newevo.getPK();
	   this.mLocals.put(pk, externalDestinationEjb);
      return newevo;
   }

   public void remove(ExternalDestinationPK pk) throws Exception {
//      this.getLocal(pk).remove();
      externalDestinationEjb.ejbRemove();
   }

   public ExternalDestinationEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ExternalDestinationCK) {
         ExternalDestinationPK pk = ((ExternalDestinationCK)key).getExternalDestinationPK();
         return this.getLocal(pk).getDetails((ExternalDestinationCK)key, dependants);
      } else {
//         return key instanceof ExternalDestinationPK?this.getLocal((ExternalDestinationPK)key).getDetails(dependants):null;
    	  ExternalDestinationPK pk = (ExternalDestinationPK)key;
    	  ExternalDestinationCK ck = new ExternalDestinationCK(pk);
    	  if(key instanceof ExternalDestinationPK)
    		  return externalDestinationEjb.getDetails(ck,dependants);
    	  else 
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ExternalDestinationEVO evo) throws Exception {
      ExternalDestinationPK pk = evo.getPK();
      externalDestinationEjb.ejbFindByPrimaryKey(pk);
      externalDestinationEjb.setDetails(evo);
      externalDestinationEjb.ejbStore();
//      this.getLocal(pk).setDetails(evo);
   }

   public ExternalDestinationEVO setAndGetDetails(ExternalDestinationEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ExternalDestinationPK generateKeys(ExternalDestinationPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllExternalDestinationsELO getAllExternalDestinations() {
      ExternalDestinationDAO dao = new ExternalDestinationDAO();
      return dao.getAllExternalDestinations();
   }

   public AllExternalDestinationDetailsELO getAllExternalDestinationDetails() {
      ExternalDestinationDAO dao = new ExternalDestinationDAO();
      return dao.getAllExternalDestinationDetails();
   }

   public AllUsersForExternalDestinationIdELO getAllUsersForExternalDestinationId(int param1) {
      ExternalDestinationDAO dao = new ExternalDestinationDAO();
      return dao.getAllUsersForExternalDestinationId(param1);
   }
}
