// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class ChangeManagementSEJB implements SessionBean {

   private SessionContext mSessionContext;


   public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
      this.mSessionContext = sessionContext;
   }

   public void registerUpdateRequest(String xmlRequest) throws ValidationException, EJBException {
      try {
         ChangeMgmtEngine e = new ChangeMgmtEngine(new InitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
         e.registerUpdateRequest(xmlRequest);
      } catch (Exception var3) {
         throw new EJBException(var3.getMessage(), var3);
      }
   }

   public int issueUpdateTask(UserPK userPK, ModelRef model) throws ValidationException, EJBException {
      try {
         ChangeMgmtEngine e = new ChangeMgmtEngine(new InitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
         return e.issueUpdateTask(userPK, model);
      } catch (Exception var4) {
         throw new EJBException(var4.getMessage(), var4);
      }
   }

   public void ejbCreate() {}

   public void ejbRemove() throws EJBException, RemoteException {}

   public void ejbActivate() throws EJBException, RemoteException {}

   public void ejbPassivate() throws EJBException, RemoteException {}
}
