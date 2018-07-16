// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report;

import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.ejb.impl.report.ReportEVO;
import com.cedar.cp.ejb.impl.report.ReportRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ReportHome extends EJBHome {

   ReportRemote create(ReportEVO var1) throws EJBException, CreateException, RemoteException;

   ReportRemote findByPrimaryKey(ReportPK var1) throws EJBException, FinderException, RemoteException;
}
