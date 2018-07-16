// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreportfolder;

import com.cedar.cp.ejb.api.xmlreportfolder.XmlReportFolderEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface XmlReportFolderEditorSessionLocalHome extends EJBLocalHome {

   XmlReportFolderEditorSessionLocal create() throws CreateException;
}
