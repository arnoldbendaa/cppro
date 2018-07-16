// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.udeflookup;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.udeflookup.AllUdefLookupsELO;
import com.cedar.cp.dto.udeflookup.UdefLookupCK;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupDAO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEVO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupLocal;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class UdefLookupAccessor implements Serializable {

   private UdefLookupLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_COLUMN_DEF = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public UdefLookupAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private UdefLookupLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (UdefLookupLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/UdefLookupLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up UdefLookupLocalHome", var2);
      }
   }

   private UdefLookupLocal getLocal(UdefLookupPK pk) throws Exception {
      UdefLookupLocal local = (UdefLookupLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public UdefLookupEVO create(UdefLookupEVO evo) throws Exception {
      UdefLookupLocal local = this.getLocalHome().create(evo);
      UdefLookupEVO newevo = local.getDetails("<UseLoadedEVOs>");
      UdefLookupPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(UdefLookupPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public UdefLookupEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof UdefLookupCK) {
         UdefLookupPK pk = ((UdefLookupCK)key).getUdefLookupPK();
         return this.getLocal(pk).getDetails((UdefLookupCK)key, dependants);
      } else {
         return key instanceof UdefLookupPK?this.getLocal((UdefLookupPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(UdefLookupEVO evo) throws Exception {
      UdefLookupPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public UdefLookupEVO setAndGetDetails(UdefLookupEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public UdefLookupPK generateKeys(UdefLookupPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllUdefLookupsELO getAllUdefLookups() {
      UdefLookupDAO dao = new UdefLookupDAO();
      return dao.getAllUdefLookups();
   }
	
	public AllUdefLookupsELO getAllUdefLookupsForLoggedUser(int userId) {
		UdefLookupDAO dao = new UdefLookupDAO();
	      return dao.getAllUdefLookupsForLoggedUser(userId);
	}
}
