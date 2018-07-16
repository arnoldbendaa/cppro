// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportEVO;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface XmlReportLocalHome extends EJBLocalHome {

   XmlReportLocal create(XmlReportEVO var1) throws EJBException, CreateException;

   XmlReportLocal findByPrimaryKey(XmlReportPK var1) throws FinderException;
}
