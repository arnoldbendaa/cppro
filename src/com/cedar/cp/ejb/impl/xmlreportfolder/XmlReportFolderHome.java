// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreportfolder;

import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderEVO;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface XmlReportFolderHome extends EJBHome {

   XmlReportFolderRemote create(XmlReportFolderEVO var1) throws EJBException, CreateException, RemoteException;

   XmlReportFolderRemote findByPrimaryKey(XmlReportFolderPK var1) throws EJBException, FinderException, RemoteException;
}
