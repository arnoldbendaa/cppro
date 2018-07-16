// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportEVO;
import javax.ejb.EJBLocalObject;

public interface XmlReportLocal extends EJBLocalObject {

   XmlReportEVO getDetails(String var1) throws ValidationException;

   XmlReportPK generateKeys();

   void setDetails(XmlReportEVO var1);

   XmlReportEVO setAndGetDetails(XmlReportEVO var1, String var2);
}
