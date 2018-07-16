// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.SecurityAccessDefCK;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionCSO;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface SecurityAccessDefEditorSessionRemote extends EJBObject {

   SecurityAccessDefEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   SecurityAccessDefEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   SecurityAccessDefCK insert(SecurityAccessDefEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   SecurityAccessDefCK copy(SecurityAccessDefEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(SecurityAccessDefEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
