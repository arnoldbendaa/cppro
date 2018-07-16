// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.passwordhistory;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.passwordhistory.AllPasswordHistorysELO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.dto.passwordhistory.UserPasswordHistoryELO;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryDAO;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryEVO;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryLocal;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PasswordHistoryAccessor implements Serializable {

   private PasswordHistoryLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public PasswordHistoryAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private PasswordHistoryLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (PasswordHistoryLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/PasswordHistoryLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up PasswordHistoryLocalHome", var2);
      }
   }

   private PasswordHistoryLocal getLocal(PasswordHistoryPK pk) throws Exception {
      PasswordHistoryLocal local = (PasswordHistoryLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public PasswordHistoryEVO create(PasswordHistoryEVO evo) throws Exception {
      PasswordHistoryLocal local = this.getLocalHome().create(evo);
      PasswordHistoryEVO newevo = local.getDetails("<UseLoadedEVOs>");
      PasswordHistoryPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(PasswordHistoryPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public PasswordHistoryEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof PasswordHistoryPK?this.getLocal((PasswordHistoryPK)key).getDetails(dependants):null;
   }

   public void setDetails(PasswordHistoryEVO evo) throws Exception {
      PasswordHistoryPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public PasswordHistoryEVO setAndGetDetails(PasswordHistoryEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public PasswordHistoryPK generateKeys(PasswordHistoryPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllPasswordHistorysELO getAllPasswordHistorys() {
      PasswordHistoryDAO dao = new PasswordHistoryDAO();
      return dao.getAllPasswordHistorys();
   }

   public UserPasswordHistoryELO getUserPasswordHistory(int param1) {
      PasswordHistoryDAO dao = new PasswordHistoryDAO();
      return dao.getUserPasswordHistory(param1);
   }
}
