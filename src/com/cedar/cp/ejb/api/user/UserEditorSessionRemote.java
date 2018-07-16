// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.user;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.UserEditorSessionCSO;
import com.cedar.cp.dto.user.UserEditorSessionSSO;
import com.cedar.cp.dto.user.UserPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface UserEditorSessionRemote extends EJBObject {

   UserEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   UserEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   UserPK insert(UserEditorSessionCSO var1) throws ValidationException, RemoteException;

   UserPK copy(UserEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(UserEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
