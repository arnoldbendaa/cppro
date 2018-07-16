// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.mappingtemplate;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionCSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportMappingTemplateEditorSessionRemote extends EJBObject {

   ReportMappingTemplateEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ReportMappingTemplateEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ReportMappingTemplatePK insert(ReportMappingTemplateEditorSessionCSO var1) throws ValidationException, RemoteException;

   ReportMappingTemplatePK copy(ReportMappingTemplateEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ReportMappingTemplateEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
