// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.type;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionCSO;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionSSO;
import com.cedar.cp.dto.report.type.ReportTypePK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportTypeEditorSessionRemote extends EJBObject {

   ReportTypeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ReportTypeEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ReportTypePK insert(ReportTypeEditorSessionCSO var1) throws ValidationException, RemoteException;

   ReportTypePK copy(ReportTypeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ReportTypeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
