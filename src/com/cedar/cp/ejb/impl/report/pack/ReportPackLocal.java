// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.pack.ReportPackCK;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import javax.ejb.EJBLocalObject;

public interface ReportPackLocal extends EJBLocalObject {

   ReportPackEVO getDetails(String var1) throws ValidationException;

   ReportPackEVO getDetails(ReportPackCK var1, String var2) throws ValidationException;

   ReportPackPK generateKeys();

   void setDetails(ReportPackEVO var1);

   ReportPackEVO setAndGetDetails(ReportPackEVO var1, String var2);
}