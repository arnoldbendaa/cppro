// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.logonhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionCSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionSSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface LogonHistoryEditorSessionRemote extends EJBObject {

   LogonHistoryEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   LogonHistoryEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   LogonHistoryPK insert(LogonHistoryEditorSessionCSO var1) throws ValidationException, RemoteException;

   LogonHistoryPK copy(LogonHistoryEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(LogonHistoryEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
