// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.ejb.impl.role.RoleSecurityEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface RoleSecurityRemote extends EJBObject {

   RoleSecurityEVO getDetails(String var1) throws ValidationException, RemoteException;

   RoleSecurityPK generateKeys();

   void setDetails(RoleSecurityEVO var1) throws RemoteException;

   RoleSecurityEVO setAndGetDetails(RoleSecurityEVO var1, String var2) throws RemoteException;
}
