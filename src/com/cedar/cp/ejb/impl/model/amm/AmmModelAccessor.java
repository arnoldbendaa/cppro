// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.amm.AllAmmModelsELO;
import com.cedar.cp.dto.model.amm.AmmModelCK;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.impl.model.amm.AmmModelDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelLocal;
import com.cedar.cp.ejb.impl.model.amm.AmmModelLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AmmModelAccessor implements Serializable {

   private AmmModelLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_AMM_DIMENSIONS = "<0>";
   public static final String GET_AMM_DIM_ELEMENTS = "<1>";
   public static final String GET_AMM_SOURCE_ELEMENTS = "<2>";
   public static final String GET_AMM_FINANCE_CUBES = "<3>";
   public static final String GET_AMM_DATA_TYPES = "<4>";
   public static final String GET_ALL_DEPENDANTS = "<0><1><2><3><4>";


   public AmmModelAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private AmmModelLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (AmmModelLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/AmmModelLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up AmmModelLocalHome", var2);
      }
   }

   private AmmModelLocal getLocal(AmmModelPK pk) throws Exception {
      AmmModelLocal local = (AmmModelLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public AmmModelEVO create(AmmModelEVO evo) throws Exception {
      AmmModelLocal local = this.getLocalHome().create(evo);
      AmmModelEVO newevo = local.getDetails("<UseLoadedEVOs>");
      AmmModelPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(AmmModelPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public AmmModelEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof AmmModelCK) {
         AmmModelPK pk = ((AmmModelCK)key).getAmmModelPK();
         return this.getLocal(pk).getDetails((AmmModelCK)key, dependants);
      } else {
         return key instanceof AmmModelPK?this.getLocal((AmmModelPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(AmmModelEVO evo) throws Exception {
      AmmModelPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public AmmModelEVO setAndGetDetails(AmmModelEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public AmmModelPK generateKeys(AmmModelPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllAmmModelsELO getAllAmmModels() {
      AmmModelDAO dao = new AmmModelDAO();
      return dao.getAllAmmModels();
   }

	public AllAmmModelsELO getAllAmmModelsForLoggedUser(int userId) {
		AmmModelDAO dao = new AmmModelDAO();
	    return dao.getAllAmmModelsForLoggedUser(userId);
	}
}
