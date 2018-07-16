// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreport;

import com.cedar.cp.util.xmlreport.ReportConfig;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface XMLReportHelperRemote extends EJBObject {

   ReportConfig getXMLReportConfig(String var1) throws RemoteException;
}
