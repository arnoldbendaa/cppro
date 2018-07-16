// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.impexp;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.impexp.AllImpExpHdrsELO;
import com.cedar.cp.dto.impexp.ImpExpHdrCK;
import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrDAO;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrEVO;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrLocal;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ImpExpHdrAccessor implements Serializable {

   private ImpExpHdrLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_I_M_P__E_X_P__R_O_W = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public ImpExpHdrAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ImpExpHdrLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ImpExpHdrLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ImpExpHdrLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ImpExpHdrLocalHome", var2);
      }
   }

   private ImpExpHdrLocal getLocal(ImpExpHdrPK pk) throws Exception {
      ImpExpHdrLocal local = (ImpExpHdrLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ImpExpHdrEVO create(ImpExpHdrEVO evo) throws Exception {
      ImpExpHdrLocal local = this.getLocalHome().create(evo);
      ImpExpHdrEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ImpExpHdrPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ImpExpHdrPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ImpExpHdrEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ImpExpHdrCK) {
         ImpExpHdrPK pk = ((ImpExpHdrCK)key).getImpExpHdrPK();
         return this.getLocal(pk).getDetails((ImpExpHdrCK)key, dependants);
      } else {
         return key instanceof ImpExpHdrPK?this.getLocal((ImpExpHdrPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ImpExpHdrEVO evo) throws Exception {
      ImpExpHdrPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ImpExpHdrEVO setAndGetDetails(ImpExpHdrEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ImpExpHdrPK generateKeys(ImpExpHdrPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllImpExpHdrsELO getAllImpExpHdrs() {
      ImpExpHdrDAO dao = new ImpExpHdrDAO();
      return dao.getAllImpExpHdrs();
   }
}
