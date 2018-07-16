// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.defaultuserpref;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefEVO;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefLocal;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DefaultUserPrefAccessor implements Serializable {

   private DefaultUserPrefLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public DefaultUserPrefAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private DefaultUserPrefLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (DefaultUserPrefLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/DefaultUserPrefLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up DefaultUserPrefLocalHome", var2);
      }
   }

   private DefaultUserPrefLocal getLocal(DefaultUserPrefPK pk) throws Exception {
      DefaultUserPrefLocal local = (DefaultUserPrefLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public DefaultUserPrefEVO create(DefaultUserPrefEVO evo) throws Exception {
      DefaultUserPrefLocal local = this.getLocalHome().create(evo);
      DefaultUserPrefEVO newevo = local.getDetails("<UseLoadedEVOs>");
      DefaultUserPrefPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(DefaultUserPrefPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public DefaultUserPrefEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof DefaultUserPrefPK?this.getLocal((DefaultUserPrefPK)key).getDetails(dependants):null;
   }

   public void setDetails(DefaultUserPrefEVO evo) throws Exception {
      DefaultUserPrefPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public DefaultUserPrefEVO setAndGetDetails(DefaultUserPrefEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public DefaultUserPrefPK generateKeys(DefaultUserPrefPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }
}
