// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.type.ReportTypeCK;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
import javax.ejb.EJBLocalObject;

public interface ReportTypeLocal extends EJBLocalObject {

   ReportTypeEVO getDetails(String var1) throws ValidationException;

   ReportTypeEVO getDetails(ReportTypeCK var1, String var2) throws ValidationException;

   ReportTypePK generateKeys();

   void setDetails(ReportTypeEVO var1);

   ReportTypeEVO setAndGetDetails(ReportTypeEVO var1, String var2);
}
