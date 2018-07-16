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
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import jcifs.smb.NtlmPasswordAuthentication;

public interface LogonLocal extends EJBLocalObject {

   void add2LogonHistory(int var1, String var2) throws EJBException;

   AuthenticationResult authenticateUser(String var1, String var2, boolean var3) throws ValidationException, EJBException;

   AuthenticationResult authenticateUser(CosignPrincipal var1) throws ValidationException, EJBException;

   AuthenticationResult authenticateUser(UserRepository var1) throws ValidationException, EJBException;

   AuthenticationResult authenticateUser(PortalPrincipal var1) throws ValidationException, EJBException;

   AuthenticationResult authenticateUser(NtlmPasswordAuthentication var1) throws ValidationException, EJBException;

   AuthenticationResult changePassword(String var1, String var2) throws EJBException;

   void disableUser(String var1) throws EJBException;

   AuthenticationResult createUser(UserRepository var1) throws ValidationException, EJBException;

   void syncCPUserDetails(UserRepository var1) throws ValidationException, EJBException;
}
