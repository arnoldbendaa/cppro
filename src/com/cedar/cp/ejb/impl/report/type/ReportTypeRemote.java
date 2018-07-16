// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.type.ReportTypeCK;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportTypeRemote extends EJBObject {

   ReportTypeEVO getDetails(String var1) throws ValidationException, RemoteException;

   ReportTypeEVO getDetails(ReportTypeCK var1, String var2) throws ValidationException, RemoteException;

   ReportTypePK generateKeys();

   void setDetails(ReportTypeEVO var1) throws RemoteException;

   ReportTypeEVO setAndGetDetails(ReportTypeEVO var1, String var2) throws RemoteException;
}
