// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.currency;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.ejb.impl.currency.CurrencyEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface CurrencyRemote extends EJBObject {

   CurrencyEVO getDetails(String var1) throws ValidationException, RemoteException;

   CurrencyPK generateKeys();

   void setDetails(CurrencyEVO var1) throws RemoteException;

   CurrencyEVO setAndGetDetails(CurrencyEVO var1, String var2) throws RemoteException;
}
