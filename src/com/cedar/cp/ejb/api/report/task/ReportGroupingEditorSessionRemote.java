// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.task;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionCSO;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionSSO;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportGroupingEditorSessionRemote extends EJBObject {

   ReportGroupingEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ReportGroupingEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ReportGroupingPK insert(ReportGroupingEditorSessionCSO var1) throws ValidationException, RemoteException;

   ReportGroupingPK copy(ReportGroupingEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ReportGroupingEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
