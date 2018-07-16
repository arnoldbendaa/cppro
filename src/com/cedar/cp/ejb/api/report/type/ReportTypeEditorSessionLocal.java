// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.type;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionCSO;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionSSO;
import com.cedar.cp.dto.report.type.ReportTypePK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ReportTypeEditorSessionLocal extends EJBLocalObject {

   ReportTypeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ReportTypeEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ReportTypePK insert(ReportTypeEditorSessionCSO var1) throws ValidationException, EJBException;

   ReportTypePK copy(ReportTypeEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ReportTypeEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
