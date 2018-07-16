// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.mapping.AllMappedModelsELO;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubesELO;
import com.cedar.cp.dto.model.mapping.MappedModelCK;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelLocal;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MappedModelAccessor implements Serializable {

   private MappedModelLocalHome mLocalHome;
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
   public static MappedModelEEJB server = new MappedModelEEJB();

   public MappedModelAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private MappedModelLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (MappedModelLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/MappedModelLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up MappedModelLocalHome", var2);
      }
   }

   private MappedModelEEJB getLocal(MappedModelPK pk) throws Exception {
//      MappedModelLocal local = (MappedModelLocal)this.mLocals.get(pk);
//      if(local == null) {
//         local = this.getLocalHome().findByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
//      }
//
//      return local;
	   return this.server;
   }

   public MappedModelEVO create(MappedModelEVO evo) throws Exception {
//      MappedModelLocal local = this.getLocalHome().create(evo);
	   MappedModelPK local = this.server.ejbCreate(evo);
      MappedModelEVO newevo = this.server.getDetails("<UseLoadedEVOs>");
      MappedModelPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(MappedModelPK pk) throws Exception {
      this.getLocal(pk).ejbRemove();
   }

   public MappedModelEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof MappedModelCK) {
         MappedModelPK pk = ((MappedModelCK)key).getMappedModelPK();
         return this.getLocal(pk).getDetails((MappedModelCK)key, dependants);
      } else {
//         return key instanceof MappedModelPK?this.getLocal((MappedModelPK)key).getDetails(dependants):null;
    	  MappedModelPK pk = (MappedModelPK)key;
    	  MappedModelCK ck = new MappedModelCK(pk);
    	  if(key instanceof MappedModelPK)
    		  return this.getLocal((MappedModelPK)key).getDetails(ck,dependants);
    	  else return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(MappedModelEVO evo) throws Exception {
      MappedModelPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public MappedModelEVO setAndGetDetails(MappedModelEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public MappedModelPK generateKeys(MappedModelPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllMappedModelsELO getAllMappedModels() {
      MappedModelDAO dao = new MappedModelDAO();
      return dao.getAllMappedModels();
   }

   public MappedFinanceCubesELO getMappedFinanceCubes(int param1) {
      MappedModelDAO dao = new MappedModelDAO();
      return dao.getMappedFinanceCubes(param1);
   }

	public AllMappedModelsELO getAllMappedModelsForLoggedUser(int userId) {
		MappedModelDAO dao = new MappedModelDAO();
	    return dao.getAllMappedModelsForLoggedUser(userId);
	}

}
