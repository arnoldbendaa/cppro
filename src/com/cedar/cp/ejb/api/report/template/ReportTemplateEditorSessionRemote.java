// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.template;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.template.ReportTemplateEditorSessionCSO;
import com.cedar.cp.dto.report.template.ReportTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportTemplateEditorSessionRemote extends EJBObject {

   ReportTemplateEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ReportTemplateEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ReportTemplatePK insert(ReportTemplateEditorSessionCSO var1) throws ValidationException, RemoteException;

   ReportTemplatePK copy(ReportTemplateEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ReportTemplateEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
