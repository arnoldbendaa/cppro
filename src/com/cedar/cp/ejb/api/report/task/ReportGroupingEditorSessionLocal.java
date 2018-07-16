// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.task;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionCSO;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionSSO;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ReportGroupingEditorSessionLocal extends EJBLocalObject {

   ReportGroupingEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ReportGroupingEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ReportGroupingPK insert(ReportGroupingEditorSessionCSO var1) throws ValidationException, EJBException;

   ReportGroupingPK copy(ReportGroupingEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ReportGroupingEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
