// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.mappingtemplate;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateEVO;
import javax.ejb.EJBLocalObject;

public interface ReportMappingTemplateLocal extends EJBLocalObject {

   ReportMappingTemplateEVO getDetails(String var1) throws ValidationException;

   ReportMappingTemplatePK generateKeys();

   void setDetails(ReportMappingTemplateEVO var1);

   ReportMappingTemplateEVO setAndGetDetails(ReportMappingTemplateEVO var1, String var2);
}
