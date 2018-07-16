// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreportfolder;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderEVO;
import javax.ejb.EJBLocalObject;

public interface XmlReportFolderLocal extends EJBLocalObject {

   XmlReportFolderEVO getDetails(String var1) throws ValidationException;

   XmlReportFolderPK generateKeys();

   void setDetails(XmlReportFolderEVO var1);

   XmlReportFolderEVO setAndGetDetails(XmlReportFolderEVO var1, String var2);
}
