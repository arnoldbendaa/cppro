// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface XmlReportRemote extends EJBObject {

   XmlReportEVO getDetails(String var1) throws ValidationException, RemoteException;

   XmlReportPK generateKeys();

   void setDetails(XmlReportEVO var1) throws RemoteException;

   XmlReportEVO setAndGetDetails(XmlReportEVO var1, String var2) throws RemoteException;
}
