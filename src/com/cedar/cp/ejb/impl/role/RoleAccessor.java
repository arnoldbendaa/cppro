// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.role.AllHiddenRolesELO;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.role.AllRolesForUserELO;
import com.cedar.cp.dto.role.RoleCK;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.ejb.impl.role.RoleDAO;
import com.cedar.cp.ejb.impl.role.RoleEVO;
import com.cedar.cp.ejb.impl.role.RoleLocal;
import com.cedar.cp.ejb.impl.role.RoleLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RoleAccessor implements Serializable {

   private RoleLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_ROLE_SECURITY = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";
   RoleEEJB roleEjb = new RoleEEJB();

   public RoleAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private RoleLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (RoleLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/RoleLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up RoleLocalHome", var2);
      }
   }

   private RoleLocal getLocal(RolePK pk) throws Exception {
      RoleLocal local = (RoleLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public RoleEVO create(RoleEVO evo) throws Exception {
//      RoleLocal local = this.getLocalHome().create(evo);
//      RoleEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      RolePK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
//      return newevo;
	   roleEjb.ejbCreate(evo);
	   RoleEVO newevo = roleEjb.getDetails("<UseLoadedEVOs>");
	   RolePK pk = newevo.getPK();
	   this.mLocals.put(pk, roleEjb);
	   return newevo;
   }

   public void remove(RolePK pk) throws Exception {
//      this.getLocal(pk).remove();
	   roleEjb.ejbRemove();
   }

   public RoleEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof RoleCK) {
         RolePK pk = ((RoleCK)key).getRolePK();
         return this.getLocal(pk).getDetails((RoleCK)key, dependants);
      } else {
//         return key instanceof RolePK?this.getLocal((RolePK)key).getDetails(dependants):null;
    	  RolePK pk = (RolePK)key;
    	  RoleCK ck = new RoleCK(pk);
    	  if(key instanceof RolePK)
    		  return roleEjb.getDetails(ck,dependants);
    	  else return null;
    	  
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(RoleEVO evo) throws Exception {
      RolePK pk = evo.getPK();
//      this.getLocal(pk).setDetails(evo);
      roleEjb.ejbFindByPrimaryKey(pk);
      roleEjb.setDetails(evo);
      roleEjb.ejbStore();
   }

   public RoleEVO setAndGetDetails(RoleEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public RolePK generateKeys(RolePK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllRolesELO getAllRoles() {
      RoleDAO dao = new RoleDAO();
      return dao.getAllRoles();
   }
   
   public AllHiddenRolesELO getAllHiddenRoles() {
	      RoleDAO dao = new RoleDAO();
	      return dao.getAllHiddenRoles();
	   }

   public AllRolesForUserELO getAllRolesForUser(int param1) {
      RoleDAO dao = new RoleDAO();
      return dao.getAllRolesForUser(param1);
   }
   
   public AllRolesForUserELO getAllHiddenRolesForUser(int param1) {
      RoleDAO dao = new RoleDAO();
      return dao.getAllHiddenRolesForUser(param1);
   }
}
