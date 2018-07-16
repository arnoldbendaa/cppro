// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreport;

import com.cedar.cp.ejb.api.xmlreport.XMLReportHelperLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface XMLReportHelperLocalHome extends EJBLocalHome {

   XMLReportHelperLocal create() throws CreateException;
}
