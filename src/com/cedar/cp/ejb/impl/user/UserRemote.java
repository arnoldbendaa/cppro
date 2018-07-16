// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface UserRemote extends EJBObject {

   UserEVO getDetails(String var1) throws ValidationException, RemoteException;

   UserEVO getDetails(UserCK var1, String var2) throws ValidationException, RemoteException;

   UserPK generateKeys();

   void setDetails(UserEVO var1) throws RemoteException;

   UserEVO setAndGetDetails(UserEVO var1, String var2) throws RemoteException;
}
