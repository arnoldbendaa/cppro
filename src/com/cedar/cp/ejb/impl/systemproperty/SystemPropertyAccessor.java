// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.systemproperty;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.systemproperty.AllMailPropsELO;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysELO;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysUncachedELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyCK;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.dto.systemproperty.WebSystemPropertyELO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyLocal;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SystemPropertyAccessor implements Serializable {

   public static final String SYSTEM_NAME_PORPERTY = "WEB: Environment Name";
   public static final String JAAS_ENTRY_NAME_PORPERTY = "SYS: JAAS Entry Name";
   private SystemPropertyLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   SystemPropertyEEJB systemPropertyEjb = new SystemPropertyEEJB();

   public SystemPropertyAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private SystemPropertyLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (SystemPropertyLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/SystemPropertyLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up SystemPropertyLocalHome", var2);
      }
   }

   private SystemPropertyLocal getLocal(SystemPropertyPK pk) throws Exception {
      SystemPropertyLocal local = (SystemPropertyLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public SystemPropertyEVO create(SystemPropertyEVO evo) throws Exception {
      SystemPropertyLocal local = this.getLocalHome().create(evo);
      SystemPropertyEVO newevo = local.getDetails("<UseLoadedEVOs>");
      SystemPropertyPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(SystemPropertyPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public SystemPropertyEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

//      return key instanceof SystemPropertyPK?this.getLocal((SystemPropertyPK)key).getDetails(dependants):null;
      if(key instanceof SystemPropertyPK){
    	  SystemPropertyPK pk = (SystemPropertyPK)key;
    	  SystemPropertyCK ck = new SystemPropertyCK(pk);
    	  return systemPropertyEjb.getDetails(ck, dependants);
      }else 
    	 return null;
   }

   public void setDetails(SystemPropertyEVO evo) throws Exception {
      SystemPropertyPK pk = evo.getPK();
//      this.getLocal(pk).setDetails(evo);
      systemPropertyEjb.ejbFindByPrimaryKey(pk);
      systemPropertyEjb.setDetails(evo);
      systemPropertyEjb.ejbStore();
   }

   public SystemPropertyEVO setAndGetDetails(SystemPropertyEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public SystemPropertyPK generateKeys(SystemPropertyPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllSystemPropertysELO getAllSystemPropertys() {
      SystemPropertyDAO dao = new SystemPropertyDAO();
      return dao.getAllSystemPropertys();
   }

   public AllSystemPropertysUncachedELO getAllSystemPropertysUncached() {
      SystemPropertyDAO dao = new SystemPropertyDAO();
      return dao.getAllSystemPropertysUncached();
   }

   public AllMailPropsELO getAllMailProps() {
      SystemPropertyDAO dao = new SystemPropertyDAO();
      return dao.getAllMailProps();
   }

   public SystemPropertyELO getSystemProperty(String param1) {
      SystemPropertyDAO dao = new SystemPropertyDAO();
      return dao.getSystemProperty(param1);
   }

   public WebSystemPropertyELO getWebSystemProperty(String param1) {
      SystemPropertyDAO dao = new SystemPropertyDAO();
      return dao.getWebSystemProperty(param1);
   }
}
