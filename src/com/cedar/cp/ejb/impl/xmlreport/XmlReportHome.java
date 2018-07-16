// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportEVO;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface XmlReportHome extends EJBHome {

   XmlReportRemote create(XmlReportEVO var1) throws EJBException, CreateException, RemoteException;

   XmlReportRemote findByPrimaryKey(XmlReportPK var1) throws EJBException, FinderException, RemoteException;
}
