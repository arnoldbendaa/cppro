// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.mappingtemplate;

import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateEVO;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ReportMappingTemplateLocalHome extends EJBLocalHome {

   ReportMappingTemplateLocal create(ReportMappingTemplateEVO var1) throws EJBException, CreateException;

   ReportMappingTemplateLocal findByPrimaryKey(ReportMappingTemplatePK var1) throws FinderException;
}
