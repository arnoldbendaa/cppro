// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface AuthenticationPolicyRemote extends EJBObject {

   AuthenticationPolicyEVO getDetails(String var1) throws ValidationException, RemoteException;

   AuthenticationPolicyPK generateKeys();

   void setDetails(AuthenticationPolicyEVO var1) throws RemoteException;

   AuthenticationPolicyEVO setAndGetDetails(AuthenticationPolicyEVO var1, String var2) throws RemoteException;
}
