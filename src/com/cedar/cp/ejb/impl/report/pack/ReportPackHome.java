// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ReportPackHome extends EJBHome {

   ReportPackRemote create(ReportPackEVO var1) throws EJBException, CreateException, RemoteException;

   ReportPackRemote findByPrimaryKey(ReportPackPK var1) throws EJBException, FinderException, RemoteException;
}
