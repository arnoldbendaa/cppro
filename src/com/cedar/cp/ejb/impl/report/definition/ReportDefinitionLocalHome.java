// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ReportDefinitionLocalHome extends EJBLocalHome {

   ReportDefinitionLocal create(ReportDefinitionEVO var1) throws EJBException, CreateException;

   ReportDefinitionLocal findByPrimaryKey(ReportDefinitionPK var1) throws FinderException;
}
