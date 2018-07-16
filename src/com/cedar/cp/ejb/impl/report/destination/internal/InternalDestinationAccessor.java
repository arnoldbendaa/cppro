// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.internal;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationDetailsELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationUsersELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
import com.cedar.cp.dto.report.destination.internal.AllUsersForInternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.internal.CheckInternalDestinationUsersELO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationCK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationEVO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationLocal;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationLocalHome;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationUsersDAO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class InternalDestinationAccessor implements Serializable {

   private InternalDestinationLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_INTERNAL_USER_LIST = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";

   InternalDestinationEEJB internalDestinationEjb = new InternalDestinationEEJB();
   public InternalDestinationAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private InternalDestinationLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (InternalDestinationLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/InternalDestinationLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up InternalDestinationLocalHome", var2);
      }
   }

   private InternalDestinationLocal getLocal(InternalDestinationPK pk) throws Exception {
      InternalDestinationLocal local = (InternalDestinationLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public InternalDestinationEVO create(InternalDestinationEVO evo) throws Exception {
//      InternalDestinationLocal local = this.getLocalHome().create(evo);
//      InternalDestinationEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      InternalDestinationPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
//      return newevo;
	   internalDestinationEjb.ejbCreate(evo);
	   InternalDestinationEVO newevo = internalDestinationEjb.getDetails("<UseLoadedEVOs>");
	   InternalDestinationPK pk = newevo.getPK();
	   this.mLocals.put(pk, internalDestinationEjb);
	   return newevo;
   }

   public void remove(InternalDestinationPK pk) throws Exception {
//      this.getLocal(pk).remove();
	   internalDestinationEjb.ejbRemove();
   }

   public InternalDestinationEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof InternalDestinationCK) {
         InternalDestinationPK pk = ((InternalDestinationCK)key).getInternalDestinationPK();
         return this.getLocal(pk).getDetails((InternalDestinationCK)key, dependants);
      } else {
//         return key instanceof InternalDestinationPK?this.getLocal((InternalDestinationPK)key).getDetails(dependants):null;
    	  InternalDestinationPK pk = (InternalDestinationPK)key;
    	  InternalDestinationCK ck = new InternalDestinationCK(pk);
    	  if(key instanceof InternalDestinationPK)
    		  return internalDestinationEjb.getDetails(ck,dependants);
    	  else 
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(InternalDestinationEVO evo) throws Exception {
      InternalDestinationPK pk = evo.getPK();
//      this.getLocal(pk).setDetails(evo);
      internalDestinationEjb.ejbFindByPrimaryKey(pk);
      internalDestinationEjb.setDetails(evo);
      internalDestinationEjb.ejbStore();
   }

   public InternalDestinationEVO setAndGetDetails(InternalDestinationEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public InternalDestinationPK generateKeys(InternalDestinationPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllInternalDestinationsELO getAllInternalDestinations() {
      InternalDestinationDAO dao = new InternalDestinationDAO();
      return dao.getAllInternalDestinations();
   }

   public AllInternalDestinationDetailsELO getAllInternalDestinationDetails() {
      InternalDestinationDAO dao = new InternalDestinationDAO();
      return dao.getAllInternalDestinationDetails();
   }

   public AllUsersForInternalDestinationIdELO getAllUsersForInternalDestinationId(int param1) {
      InternalDestinationDAO dao = new InternalDestinationDAO();
      return dao.getAllUsersForInternalDestinationId(param1);
   }

   public AllInternalDestinationUsersELO getAllInternalDestinationUsers() {
      InternalDestinationUsersDAO dao = new InternalDestinationUsersDAO();
      return dao.getAllInternalDestinationUsers();
   }

   public CheckInternalDestinationUsersELO getCheckInternalDestinationUsers(int param1) {
      InternalDestinationUsersDAO dao = new InternalDestinationUsersDAO();
      return dao.getCheckInternalDestinationUsers(param1);
   }
}
