// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.performance;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.performance.PerformanceData;
import com.cedar.cp.util.performance.PerformanceDataImpl;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class PerformanceSEJB implements SessionBean {

   private SessionContext mContext;
   private transient Log _log = new Log(this.getClass());


   public PerformanceData getPerformanceData() throws EJBException {
      return PerformanceDataImpl.getInstance();
   }

   public PerformanceType getPerformanceType(String type) throws EJBException {
      PerformanceDataImpl perfData = PerformanceDataImpl.getInstance();
      return perfData.getPerformanceType(type);
   }

   public PerformanceDatum getPerformanceDatum(String id) throws EJBException {
      PerformanceDataImpl perfData = PerformanceDataImpl.getInstance();
      return perfData.getPerformanceDatum(id);
   }

   public void ejbCreate() {}

   public void ejbActivate() throws EJBException, RemoteException {}

   public void ejbPassivate() throws EJBException, RemoteException {}

   public void ejbRemove() throws EJBException, RemoteException {}

   public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
      this.mContext = sessionContext;
   }
}
