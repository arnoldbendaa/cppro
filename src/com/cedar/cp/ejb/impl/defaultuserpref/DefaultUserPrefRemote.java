// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.defaultuserpref;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface DefaultUserPrefRemote extends EJBObject {

   DefaultUserPrefEVO getDetails(String var1) throws ValidationException, RemoteException;

   DefaultUserPrefPK generateKeys();

   void setDetails(DefaultUserPrefEVO var1) throws RemoteException;

   DefaultUserPrefEVO setAndGetDetails(DefaultUserPrefEVO var1, String var2) throws RemoteException;
}
