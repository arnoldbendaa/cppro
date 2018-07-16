// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.role.AllSecurityRolesELO;
import com.cedar.cp.dto.role.AllSecurityRolesForRoleELO;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.ejb.impl.role.RoleSecurityDAO;
import com.cedar.cp.ejb.impl.role.RoleSecurityEVO;
import com.cedar.cp.ejb.impl.role.RoleSecurityLocal;
import com.cedar.cp.ejb.impl.role.RoleSecurityLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RoleSecurityAccessor implements Serializable {

   private RoleSecurityLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public RoleSecurityAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private RoleSecurityLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (RoleSecurityLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/RoleSecurityLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up RoleSecurityLocalHome", var2);
      }
   }

   private RoleSecurityLocal getLocal(RoleSecurityPK pk) throws Exception {
      RoleSecurityLocal local = (RoleSecurityLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public RoleSecurityEVO create(RoleSecurityEVO evo) throws Exception {
      RoleSecurityLocal local = this.getLocalHome().create(evo);
      RoleSecurityEVO newevo = local.getDetails("<UseLoadedEVOs>");
      RoleSecurityPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(RoleSecurityPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public RoleSecurityEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof RoleSecurityPK?this.getLocal((RoleSecurityPK)key).getDetails(dependants):null;
   }

   public void setDetails(RoleSecurityEVO evo) throws Exception {
      RoleSecurityPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public RoleSecurityEVO setAndGetDetails(RoleSecurityEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public RoleSecurityPK generateKeys(RoleSecurityPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllSecurityRolesELO getAllSecurityRoles() {
      RoleSecurityDAO dao = new RoleSecurityDAO();
      return dao.getAllSecurityRoles();
   }

   public AllSecurityRolesForRoleELO getAllSecurityRolesForRole(int param1) {
      RoleSecurityDAO dao = new RoleSecurityDAO();
      return dao.getAllSecurityRolesForRole(param1);
   }
}
