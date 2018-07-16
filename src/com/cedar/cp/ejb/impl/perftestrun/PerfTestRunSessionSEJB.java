// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.perfTest.PerfTest;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class PerfTestRunSessionSEJB implements SessionBean {

   private SessionContext mContext;
   private transient Log _log = new Log(this.getClass());


   public long executePerfTest(String className) throws EJBException {
      long result = -1L;

      try {
         Class e = Class.forName(className);
         Object instance = e.newInstance();
         if(instance instanceof PerfTest) {
            PerfTest test = (PerfTest)instance;
            result = test.runTest();
         }

         return result;
      } catch (Exception var7) {
         throw new EJBException("Unable to run PerfTest " + className, var7);
      }
   }

   public void ejbCreate() {}

   public void ejbActivate() throws EJBException, RemoteException {}

   public void ejbPassivate() throws EJBException, RemoteException {}

   public void ejbRemove() throws EJBException, RemoteException {}

   public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
      this.mContext = sessionContext;
   }
}
