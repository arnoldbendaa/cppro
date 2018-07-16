// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.passwordhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PasswordHistoryRemote extends EJBObject {

   PasswordHistoryEVO getDetails(String var1) throws ValidationException, RemoteException;

   PasswordHistoryPK generateKeys();

   void setDetails(PasswordHistoryEVO var1) throws RemoteException;

   PasswordHistoryEVO setAndGetDetails(PasswordHistoryEVO var1, String var2) throws RemoteException;
}
