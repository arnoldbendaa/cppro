// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.ReportEditorSessionCSO;
import com.cedar.cp.dto.report.ReportEditorSessionSSO;
import com.cedar.cp.dto.report.ReportPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ReportEditorSessionLocal extends EJBLocalObject {

   ReportEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ReportEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ReportPK insert(ReportEditorSessionCSO var1) throws ValidationException, EJBException;

   ReportPK copy(ReportEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ReportEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int issueReportUpdateTask(int var1, Object var2, boolean var3) throws ValidationException, EJBException;
}
