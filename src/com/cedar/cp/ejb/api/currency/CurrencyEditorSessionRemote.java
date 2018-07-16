// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.currency;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.currency.CurrencyEditorSessionCSO;
import com.cedar.cp.dto.currency.CurrencyEditorSessionSSO;
import com.cedar.cp.dto.currency.CurrencyPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface CurrencyEditorSessionRemote extends EJBObject {

   CurrencyEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   CurrencyEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   CurrencyPK insert(CurrencyEditorSessionCSO var1) throws ValidationException, RemoteException;

   CurrencyPK copy(CurrencyEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(CurrencyEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
