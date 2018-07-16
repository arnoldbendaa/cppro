// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.impexp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.dto.impexp.CubeImportTaskRequest;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class ImportExportSEJB implements SessionBean {

   private SessionContext mSessionContext;


   public int issueImportTask(int financeCubeId, DataExtract dataExtract) throws ValidationException, EJBException {
      try {
         CubeImportTaskRequest e = new CubeImportTaskRequest(financeCubeId, dataExtract);
         return TaskMessageFactory.issueNewTask(new InitialContext(), true, e, dataExtract.getUserId());
      } catch (Exception var4) {
         throw new EJBException(var4.getMessage(), var4);
      }
   }

   public void ejbCreate() {}

   public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
      this.mSessionContext = sessionContext;
   }

   public void ejbRemove() throws EJBException, RemoteException {}

   public void ejbActivate() throws EJBException, RemoteException {}

   public void ejbPassivate() throws EJBException, RemoteException {}
}
