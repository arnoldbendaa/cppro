// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.definition.ReportDefinitionCK;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportDefinitionRemote extends EJBObject {

   ReportDefinitionEVO getDetails(String var1) throws ValidationException, RemoteException;

   ReportDefinitionEVO getDetails(ReportDefinitionCK var1, String var2) throws ValidationException, RemoteException;

   ReportDefinitionPK generateKeys();

   void setDetails(ReportDefinitionEVO var1) throws RemoteException;

   ReportDefinitionEVO setAndGetDetails(ReportDefinitionEVO var1, String var2) throws RemoteException;
}
