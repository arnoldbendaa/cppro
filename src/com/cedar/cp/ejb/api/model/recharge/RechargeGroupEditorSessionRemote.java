// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.recharge;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionCSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface RechargeGroupEditorSessionRemote extends EJBObject {

   RechargeGroupEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   RechargeGroupEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   RechargeGroupPK insert(RechargeGroupEditorSessionCSO var1) throws ValidationException, RemoteException;

   RechargeGroupPK copy(RechargeGroupEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(RechargeGroupEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
