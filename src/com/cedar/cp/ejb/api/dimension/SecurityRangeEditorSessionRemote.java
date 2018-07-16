// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionCSO;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface SecurityRangeEditorSessionRemote extends EJBObject {

   SecurityRangeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   SecurityRangeEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   SecurityRangeCK insert(SecurityRangeEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   SecurityRangeCK copy(SecurityRangeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(SecurityRangeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
