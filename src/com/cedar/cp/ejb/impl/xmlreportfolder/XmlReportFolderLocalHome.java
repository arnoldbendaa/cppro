// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreportfolder;

import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderEVO;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface XmlReportFolderLocalHome extends EJBLocalHome {

   XmlReportFolderLocal create(XmlReportFolderEVO var1) throws EJBException, CreateException;

   XmlReportFolderLocal findByPrimaryKey(XmlReportFolderPK var1) throws FinderException;
}
