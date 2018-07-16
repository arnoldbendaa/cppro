// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicyForLogonELO;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AllAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyCK;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyDAO;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyLocal;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AuthenticationPolicyAccessor implements Serializable {

   private AuthenticationPolicyLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   
   AuthenticationPolicyEEJB authenticationEjb = new AuthenticationPolicyEEJB();
   public AuthenticationPolicyAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private AuthenticationPolicyLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (AuthenticationPolicyLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/AuthenticationPolicyLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up AuthenticationPolicyLocalHome", var2);
      }
   }

   private AuthenticationPolicyLocal getLocal(AuthenticationPolicyPK pk) throws Exception {
      AuthenticationPolicyLocal local = (AuthenticationPolicyLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public AuthenticationPolicyEVO create(AuthenticationPolicyEVO evo) throws Exception {
	   authenticationEjb.ejbCreate(evo);
//      AuthenticationPolicyLocal local = this.getLocalHome().create(evo);
//      AuthenticationPolicyEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      AuthenticationPolicyPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
	   AuthenticationPolicyEVO newevo = authenticationEjb.getDetails("<UseLoadedEVOs>");
	   AuthenticationPolicyPK pk = newevo.getPK();
	   this.mLocals.put(pk, authenticationEjb);
      return newevo;
   }

   public void remove(AuthenticationPolicyPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public AuthenticationPolicyEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }
      if(key instanceof AuthenticationPolicyPK){
    	  AuthenticationPolicyPK pk = (AuthenticationPolicyPK)key;
    	  AuthenticationPolicyCK ck = new AuthenticationPolicyCK(pk);
    	  return authenticationEjb.getDetails(ck,dependants);
      }else 
    	  return null;
//      return key instanceof AuthenticationPolicyPK?this.getLocal((AuthenticationPolicyPK)key).getDetails(dependants):null;
   }

   public void setDetails(AuthenticationPolicyEVO evo) throws Exception {
      AuthenticationPolicyPK pk = evo.getPK();
      authenticationEjb.ejbFindByPrimaryKey(pk);
      authenticationEjb.setDetails(evo);
      authenticationEjb.ejbStore();
//      this.getLocal(pk).setDetails(evo);
   }

   public AuthenticationPolicyEVO setAndGetDetails(AuthenticationPolicyEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public AuthenticationPolicyPK generateKeys(AuthenticationPolicyPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllAuthenticationPolicysELO getAllAuthenticationPolicys() {
      AuthenticationPolicyDAO dao = new AuthenticationPolicyDAO();
      return dao.getAllAuthenticationPolicys();
   }

   public ActiveAuthenticationPolicysELO getActiveAuthenticationPolicys() {
      AuthenticationPolicyDAO dao = new AuthenticationPolicyDAO();
      return dao.getActiveAuthenticationPolicys();
   }

   public ActiveAuthenticationPolicyForLogonELO getActiveAuthenticationPolicyForLogon() {
      AuthenticationPolicyDAO dao = new AuthenticationPolicyDAO();
      return dao.getActiveAuthenticationPolicyForLogon();
   }

   public AuthenticationPolicyEVO getActiveAuthenticationPolicy() throws Exception {
      ActiveAuthenticationPolicysELO list = this.getActiveAuthenticationPolicys();
      if(list.hasNext()) {
         list.next();
         return this.getDetails(list.getAuthenticationPolicyEntityRef(), (String)null);
      } else {
         AuthenticationPolicyEVO evo = new AuthenticationPolicyEVO();
         evo.setAuthenticationTechnique(1);
         evo.setMinimumPasswordLength(8);
         return evo;
      }
   }
}
