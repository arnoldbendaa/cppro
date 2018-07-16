// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.recharge;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.dto.model.recharge.RechargeEditorSessionCSO;
import com.cedar.cp.dto.model.recharge.RechargeEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface RechargeEditorSessionRemote extends EJBObject {

   RechargeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   RechargeEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   RechargeCK insert(RechargeEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   RechargeCK copy(RechargeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(RechargeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
