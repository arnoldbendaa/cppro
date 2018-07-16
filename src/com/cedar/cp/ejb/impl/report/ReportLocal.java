// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.ReportCK;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.ejb.impl.report.ReportEVO;
import javax.ejb.EJBLocalObject;

public interface ReportLocal extends EJBLocalObject {

   ReportEVO getDetails(String var1) throws ValidationException;

   ReportEVO getDetails(ReportCK var1, String var2) throws ValidationException;

   ReportPK generateKeys();

   void setDetails(ReportEVO var1);

   ReportEVO setAndGetDetails(ReportEVO var1, String var2);
}
