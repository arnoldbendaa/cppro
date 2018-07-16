// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.task;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.task.ReportGroupingCK;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportGroupingRemote extends EJBObject {

   ReportGroupingEVO getDetails(String var1) throws ValidationException, RemoteException;

   ReportGroupingEVO getDetails(ReportGroupingCK var1, String var2) throws ValidationException, RemoteException;

   ReportGroupingPK generateKeys();

   void setDetails(ReportGroupingEVO var1) throws RemoteException;

   ReportGroupingEVO setAndGetDetails(ReportGroupingEVO var1, String var2) throws RemoteException;
}