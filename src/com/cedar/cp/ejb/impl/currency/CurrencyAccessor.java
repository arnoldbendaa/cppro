// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.currency;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.currency.AllCurrencysELO;
import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.ejb.impl.currency.CurrencyDAO;
import com.cedar.cp.ejb.impl.currency.CurrencyEVO;
import com.cedar.cp.ejb.impl.currency.CurrencyLocal;
import com.cedar.cp.ejb.impl.currency.CurrencyLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CurrencyAccessor implements Serializable {

   private CurrencyLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public CurrencyAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private CurrencyLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (CurrencyLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/CurrencyLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up CurrencyLocalHome", var2);
      }
   }

   private CurrencyLocal getLocal(CurrencyPK pk) throws Exception {
      CurrencyLocal local = (CurrencyLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public CurrencyEVO create(CurrencyEVO evo) throws Exception {
      CurrencyLocal local = this.getLocalHome().create(evo);
      CurrencyEVO newevo = local.getDetails("<UseLoadedEVOs>");
      CurrencyPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(CurrencyPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public CurrencyEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof CurrencyPK?this.getLocal((CurrencyPK)key).getDetails(dependants):null;
   }

   public void setDetails(CurrencyEVO evo) throws Exception {
      CurrencyPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public CurrencyEVO setAndGetDetails(CurrencyEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public CurrencyPK generateKeys(CurrencyPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllCurrencysELO getAllCurrencys() {
      CurrencyDAO dao = new CurrencyDAO();
      return dao.getAllCurrencys();
   }
}
