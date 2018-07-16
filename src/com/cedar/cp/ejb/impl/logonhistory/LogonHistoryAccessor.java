// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.logonhistory;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.logonhistory.AllLogonHistorysELO;
import com.cedar.cp.dto.logonhistory.AllLogonHistorysReportELO;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryDAO;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryEVO;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryLocal;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryLocalHome;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LogonHistoryAccessor implements Serializable {

   private LogonHistoryLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public LogonHistoryAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private LogonHistoryLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (LogonHistoryLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/LogonHistoryLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up LogonHistoryLocalHome", var2);
      }
   }

   private LogonHistoryLocal getLocal(LogonHistoryPK pk) throws Exception {
      LogonHistoryLocal local = (LogonHistoryLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public LogonHistoryEVO create(LogonHistoryEVO evo) throws Exception {
      LogonHistoryLocal local = this.getLocalHome().create(evo);
      LogonHistoryEVO newevo = local.getDetails("<UseLoadedEVOs>");
      LogonHistoryPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(LogonHistoryPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public LogonHistoryEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof LogonHistoryPK?this.getLocal((LogonHistoryPK)key).getDetails(dependants):null;
   }

   public void setDetails(LogonHistoryEVO evo) throws Exception {
      LogonHistoryPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public LogonHistoryEVO setAndGetDetails(LogonHistoryEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public LogonHistoryPK generateKeys(LogonHistoryPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllLogonHistorysELO getAllLogonHistorys() {
      LogonHistoryDAO dao = new LogonHistoryDAO();
      return dao.getAllLogonHistorys();
   }

   public AllLogonHistorysReportELO getAllLogonHistorysReport(String param1, Timestamp param2, int param3) {
      LogonHistoryDAO dao = new LogonHistoryDAO();
      return dao.getAllLogonHistorysReport(param1, param2, param3);
   }
}
