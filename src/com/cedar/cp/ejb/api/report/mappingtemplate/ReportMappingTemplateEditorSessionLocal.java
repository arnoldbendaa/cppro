// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.mappingtemplate;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionCSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ReportMappingTemplateEditorSessionLocal extends EJBLocalObject {

   ReportMappingTemplateEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ReportMappingTemplateEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ReportMappingTemplatePK insert(ReportMappingTemplateEditorSessionCSO var1) throws ValidationException, EJBException;

   ReportMappingTemplatePK copy(ReportMappingTemplateEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ReportMappingTemplateEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
