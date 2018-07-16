// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.cm.AllChangeMgmtsELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelWithXMLELO;
import com.cedar.cp.dto.cm.ChangeMgmtCK;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtDAO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEVO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtLocal;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ChangeMgmtAccessor implements Serializable {

   private ChangeMgmtLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static ChangeMgmtEEJB server = new ChangeMgmtEEJB();

   public ChangeMgmtAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ChangeMgmtLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ChangeMgmtLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ChangeMgmtLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ChangeMgmtLocalHome", var2);
      }
   }

   private ChangeMgmtEEJB getLocal(ChangeMgmtPK pk) throws Exception {
//      ChangeMgmtLocal local = (ChangeMgmtLocal)this.mLocals.get(pk);
//      if(local == null) {
//         local = this.getLocalHome().findByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
//      }
//
//      return local;
	   return server;
   }

   public ChangeMgmtEVO create(ChangeMgmtEVO evo) throws Exception {
//      ChangeMgmtLocal local = this.getLocalHome().create(evo);
	  server.ejbCreate(evo);
      ChangeMgmtEVO newevo = server.getDetails("<UseLoadedEVOs>");
      ChangeMgmtPK pk = newevo.getPK();
      this.mLocals.put(pk, server);
      return newevo;
   }

   public void remove(ChangeMgmtPK pk) throws Exception {
      this.getLocal(pk).ejbRemove();
   }

   public ChangeMgmtEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      //return key instanceof ChangeMgmtPK?this.getLocal((ChangeMgmtPK)key).getDetails(dependants):null;
      if(key instanceof ChangeMgmtPK){
    	  ChangeMgmtPK pk = (ChangeMgmtPK)key;
    	  ChangeMgmtCK ck = new ChangeMgmtCK(pk);
    	  return this.getLocal(pk).getDetails(ck,dependants);
      }else 
    	  return null;
    	  
   }

   public void setDetails(ChangeMgmtEVO evo) throws Exception {
      ChangeMgmtPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ChangeMgmtEVO setAndGetDetails(ChangeMgmtEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ChangeMgmtPK generateKeys(ChangeMgmtPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllChangeMgmtsELO getAllChangeMgmts() {
      ChangeMgmtDAO dao = new ChangeMgmtDAO();
      return dao.getAllChangeMgmts();
   }

   public AllChangeMgmtsForModelELO getAllChangeMgmtsForModel(int param1) {
      ChangeMgmtDAO dao = new ChangeMgmtDAO();
      return dao.getAllChangeMgmtsForModel(param1);
   }

   public AllChangeMgmtsForModelWithXMLELO getAllChangeMgmtsForModelWithXML(int param1) {
      ChangeMgmtDAO dao = new ChangeMgmtDAO();
      return dao.getAllChangeMgmtsForModelWithXML(param1);
   }
}
