// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.sqlmon;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.sqlmon.SqlDetails;
import com.cedar.cp.ejb.impl.sqlmon.SqlMonitorDAO;
import com.cedar.cp.util.Log;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SqlMonitorProcessSEJB implements SessionBean {

   private transient Log mLog = new Log(this.getClass());
   private SessionContext mContext;


   public EntityList getAllOracleSessions() {
      try {
         SqlMonitorDAO e = new SqlMonitorDAO();
         EntityList elo = e.getAllOracleSessions();
         return elo;
      } catch (Exception var3) {
         throw new EJBException(var3);
      }
   }

   public SqlDetails getSqlDetails(Object key) {
      try {
         SqlMonitorDAO e = new SqlMonitorDAO();
         return e.getSqlDetails(key);
      } catch (Exception var3) {
         throw new EJBException(var3);
      }
   }

   private InitialContext getInitialContext() {
      try {
         return new InitialContext();
      } catch (NamingException var2) {
         throw new CPException(var2.getMessage(), var2);
      }
   }

   public void setSessionContext(SessionContext context) {
      this.mContext = context;
   }

   public void ejbRemove() {}

   public void ejbActivate() {}

   public void ejbPassivate() {}

   public void ejbCreate() {}
}
