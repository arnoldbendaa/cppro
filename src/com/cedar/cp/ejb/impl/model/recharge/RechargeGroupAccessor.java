// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.recharge.AllRechargeGroupsELO;
import com.cedar.cp.dto.model.recharge.RechargeGroupCK;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupDAO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupLocal;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RechargeGroupAccessor implements Serializable {

   private RechargeGroupLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_RECHARGES = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public RechargeGroupAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private RechargeGroupLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (RechargeGroupLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/RechargeGroupLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up RechargeGroupLocalHome", var2);
      }
   }

   private RechargeGroupLocal getLocal(RechargeGroupPK pk) throws Exception {
      RechargeGroupLocal local = (RechargeGroupLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public RechargeGroupEVO create(RechargeGroupEVO evo) throws Exception {
      RechargeGroupLocal local = this.getLocalHome().create(evo);
      RechargeGroupEVO newevo = local.getDetails("<UseLoadedEVOs>");
      RechargeGroupPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(RechargeGroupPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public RechargeGroupEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof RechargeGroupCK) {
         RechargeGroupPK pk = ((RechargeGroupCK)key).getRechargeGroupPK();
         return this.getLocal(pk).getDetails((RechargeGroupCK)key, dependants);
      } else {
         return key instanceof RechargeGroupPK?this.getLocal((RechargeGroupPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(RechargeGroupEVO evo) throws Exception {
      RechargeGroupPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public RechargeGroupEVO setAndGetDetails(RechargeGroupEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public RechargeGroupPK generateKeys(RechargeGroupPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllRechargeGroupsELO getAllRechargeGroups() {
      RechargeGroupDAO dao = new RechargeGroupDAO();
      return dao.getAllRechargeGroups();
   }
}
