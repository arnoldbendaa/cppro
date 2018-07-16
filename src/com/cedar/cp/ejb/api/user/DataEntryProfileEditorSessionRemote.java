// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.user;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionCSO;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface DataEntryProfileEditorSessionRemote extends EJBObject {

   DataEntryProfileEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   DataEntryProfileEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   DataEntryProfileCK insert(DataEntryProfileEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   DataEntryProfileCK copy(DataEntryProfileEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(DataEntryProfileEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
