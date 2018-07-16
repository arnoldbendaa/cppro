// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.ReportEditorSessionCSO;
import com.cedar.cp.dto.report.ReportEditorSessionSSO;
import com.cedar.cp.dto.report.ReportPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportEditorSessionRemote extends EJBObject {

   ReportEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ReportEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ReportPK insert(ReportEditorSessionCSO var1) throws ValidationException, RemoteException;

   ReportPK copy(ReportEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ReportEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   int issueReportUpdateTask(int var1, Object var2, boolean var3) throws ValidationException, RemoteException;
}
