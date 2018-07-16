// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm;

import com.coa.idm.UserRepositoryException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

public interface UserRepository {

   String getLogonIdentity();

   String getLastName() throws Exception;

   String getFirstName() throws Exception;

   String getFullName() throws Exception;

   String getEMail() throws Exception;

   String getTelephoneNumber() throws Exception;

   Principal getCurrentUser() throws UserRepositoryException;

   Map<String, Set<String>> getUserAttributes() throws Exception;

   void setUserAttributes(Map<String, Set<String>> var1) throws Exception;

   Set<String> getGroupMemberships(String var1) throws Exception;

   void refreshSession() throws Exception;

   boolean isStillValid() throws Exception;
}
