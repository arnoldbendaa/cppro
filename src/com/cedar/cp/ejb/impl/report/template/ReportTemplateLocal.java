// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.template;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateEVO;
import javax.ejb.EJBLocalObject;

public interface ReportTemplateLocal extends EJBLocalObject {

   ReportTemplateEVO getDetails(String var1) throws ValidationException;

   ReportTemplatePK generateKeys();

   void setDetails(ReportTemplateEVO var1);

   ReportTemplateEVO setAndGetDetails(ReportTemplateEVO var1, String var2);
}
