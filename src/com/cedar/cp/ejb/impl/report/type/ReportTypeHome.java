// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type;

import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
import com.cedar.cp.ejb.impl.report.type.ReportTypeRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ReportTypeHome extends EJBHome {

   ReportTypeRemote create(ReportTypeEVO var1) throws EJBException, CreateException, RemoteException;

   ReportTypeRemote findByPrimaryKey(ReportTypePK var1) throws EJBException, FinderException, RemoteException;
}
