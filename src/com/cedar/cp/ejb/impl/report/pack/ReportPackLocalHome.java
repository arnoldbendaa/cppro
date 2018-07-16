// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ReportPackLocalHome extends EJBLocalHome {

   ReportPackLocal create(ReportPackEVO var1) throws EJBException, CreateException;

   ReportPackLocal findByPrimaryKey(ReportPackPK var1) throws FinderException;
}
