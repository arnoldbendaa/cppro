// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.impexp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.impexp.ImpExpHdrCK;
import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ImpExpHdrRemote extends EJBObject {

   ImpExpHdrEVO getDetails(String var1) throws ValidationException, RemoteException;

   ImpExpHdrEVO getDetails(ImpExpHdrCK var1, String var2) throws ValidationException, RemoteException;

   ImpExpHdrPK generateKeys();

   void setDetails(ImpExpHdrEVO var1) throws RemoteException;

   ImpExpHdrEVO setAndGetDetails(ImpExpHdrEVO var1, String var2) throws RemoteException;
}
