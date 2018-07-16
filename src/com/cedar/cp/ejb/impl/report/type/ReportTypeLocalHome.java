// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type;

import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
import com.cedar.cp.ejb.impl.report.type.ReportTypeLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ReportTypeLocalHome extends EJBLocalHome {

   ReportTypeLocal create(ReportTypeEVO var1) throws EJBException, CreateException;

   ReportTypeLocal findByPrimaryKey(ReportTypePK var1) throws FinderException;
}
