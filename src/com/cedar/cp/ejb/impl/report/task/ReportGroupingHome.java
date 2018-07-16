// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.task;

import com.cedar.cp.dto.report.task.ReportGroupingPK;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingEVO;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ReportGroupingHome extends EJBHome {

   ReportGroupingRemote create(ReportGroupingEVO var1) throws EJBException, CreateException, RemoteException;

   ReportGroupingRemote findByPrimaryKey(ReportGroupingPK var1) throws EJBException, FinderException, RemoteException;
}
