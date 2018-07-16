// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.passwordhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryEditorSessionCSO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryEditorSessionSSO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PasswordHistoryEditorSessionRemote extends EJBObject {

   PasswordHistoryEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   PasswordHistoryEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   PasswordHistoryPK insert(PasswordHistoryEditorSessionCSO var1) throws ValidationException, RemoteException;

   PasswordHistoryPK copy(PasswordHistoryEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(PasswordHistoryEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
