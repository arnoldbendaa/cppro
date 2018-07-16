// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.logon;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logon.AuthenticationResult;
import com.coa.idm.UserRepository;
import com.coa.portal.client.PortalPrincipal;
import edu.umich.auth.cosign.CosignPrincipal;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import jcifs.smb.NtlmPasswordAuthentication;

public interface LogonRemote extends EJBObject {

   void add2LogonHistory(int var1, String var2) throws RemoteException;

   AuthenticationResult authenticateUser(String var1, String var2, boolean var3) throws ValidationException, RemoteException;

   AuthenticationResult authenticateUser(CosignPrincipal var1) throws ValidationException, RemoteException;

   AuthenticationResult authenticateUser(UserRepository var1) throws ValidationException, RemoteException;

   AuthenticationResult authenticateUser(PortalPrincipal var1) throws ValidationException, RemoteException;

   AuthenticationResult authenticateUser(NtlmPasswordAuthentication var1) throws ValidationException, RemoteException;

   AuthenticationResult changePassword(String var1, String var2) throws RemoteException;

   void disableUser(String var1) throws RemoteException;

   AuthenticationResult createUser(UserRepository var1) throws ValidationException, RemoteException;

   void syncCPUserDetails(UserRepository var1) throws ValidationException, RemoteException;
}
