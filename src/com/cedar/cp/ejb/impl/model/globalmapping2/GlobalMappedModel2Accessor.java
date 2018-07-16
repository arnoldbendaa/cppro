package com.cedar.cp.ejb.impl.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.globalmapping2.AllGlobalMappedModels2ELO;
import com.cedar.cp.dto.model.globalmapping2.MappedFinanceCubesELO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2DAO;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2Local;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2LocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class GlobalMappedModel2Accessor implements Serializable {

   private GlobalMappedModel2LocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_MAPPED_CALENDAR_YEARS = "<0>";
   public static final String GET_MAPPED_CALENDAR_ELEMENTS = "<1>";
   public static final String GET_MAPPED_FINANCE_CUBES = "<2>";
   public static final String GET_MAPPED_DATA_TYPES = "<3>";
   public static final String GET_MAPPED_DIMENSIONS = "<4>";
   public static final String GET_MAPPED_DIMENSION_ELEMENTS = "<5>";
   public static final String GET_MAPPED_HIERARCHYS = "<6>";
   public static final String GET_ALL_DEPENDANTS = "<0><1><2><3><4><5><6>";

   GlobalMappedModel2EEJB globalMappedModel2Ejb = new GlobalMappedModel2EEJB();
   public GlobalMappedModel2Accessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private GlobalMappedModel2LocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (GlobalMappedModel2LocalHome)this.mInitialContext.lookup("java:comp/env/ejb/GlobalMappedModel2LocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException e) {
         throw new RuntimeException("error looking up GlobalMappedModel2LocalHome", e);
      }
   }

   private GlobalMappedModel2Local getLocal(GlobalMappedModel2PK pk) throws Exception {
      GlobalMappedModel2Local local = (GlobalMappedModel2Local)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public GlobalMappedModel2EVO create(GlobalMappedModel2EVO evo) throws Exception {
      GlobalMappedModel2Local local = this.getLocalHome().create(evo);
      GlobalMappedModel2EVO newevo = local.getDetails("<UseLoadedEVOs>");
      GlobalMappedModel2PK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(GlobalMappedModel2PK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public GlobalMappedModel2EVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof GlobalMappedModel2CK) {
    	 GlobalMappedModel2PK pk = ((GlobalMappedModel2CK)key).getMappedModelPK();
         return this.getLocal(pk).getDetails((GlobalMappedModel2CK)key, dependants);
      } else {
    	  GlobalMappedModel2PK pk = (GlobalMappedModel2PK)key;
    	  GlobalMappedModel2CK ck = new GlobalMappedModel2CK(pk);
    	  if(key instanceof GlobalMappedModel2PK)
    		  return globalMappedModel2Ejb.getDetails(ck,dependants);
    	  else 
    		  return null;
//         return key instanceof GlobalMappedModel2PK?this.getLocal((GlobalMappedModel2PK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(GlobalMappedModel2EVO evo) throws Exception {
	  GlobalMappedModel2PK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public GlobalMappedModel2EVO setAndGetDetails(GlobalMappedModel2EVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public GlobalMappedModel2PK generateKeys(GlobalMappedModel2PK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllGlobalMappedModels2ELO getAllGlobalMappedModels2() {
      GlobalMappedModel2DAO dao = new GlobalMappedModel2DAO();
      return dao.getAllGlobalMappedModels2();
   }

   public MappedFinanceCubesELO getMappedFinanceCubes(int param1) {
      GlobalMappedModel2DAO dao = new GlobalMappedModel2DAO();
      return dao.getMappedFinanceCubes(param1);
   }

	public AllGlobalMappedModels2ELO getAllGlobalMappedModelsForLoggedUser(int userId) {
		GlobalMappedModel2DAO dao = new GlobalMappedModel2DAO();
	    return dao.getAllGlobalMappedModelsForLoggedUser(userId);
	}
}
