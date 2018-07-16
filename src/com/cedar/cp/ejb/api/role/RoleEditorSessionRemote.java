// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.role;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.role.RoleEditorSessionCSO;
import com.cedar.cp.dto.role.RoleEditorSessionSSO;
import com.cedar.cp.dto.role.RolePK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface RoleEditorSessionRemote extends EJBObject {

   RoleEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   RoleEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   RolePK insert(RoleEditorSessionCSO var1) throws ValidationException, RemoteException;

   RolePK copy(RoleEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(RoleEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
