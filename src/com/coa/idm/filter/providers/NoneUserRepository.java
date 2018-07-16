// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter.providers;

import com.coa.idm.UserRepository;
import com.coa.idm.UserRepositoryException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class NoneUserRepository implements UserRepository {

   private Principal mCurrentUser;


   public String getLogonIdentity() {
      return this.mCurrentUser != null?this.mCurrentUser.getName():null;
   }

   public String getLastName() throws Exception {
      return null;
   }

   public String getFirstName() throws Exception {
      return null;
   }

   public String getFullName() throws Exception {
      return null;
   }

   public String getEMail() throws Exception {
      return null;
   }

   public String getTelephoneNumber() throws Exception {
      return null;
   }

   public void setCurrentUser(Principal user) {
      this.mCurrentUser = user;
   }

   public Principal getCurrentUser() throws UserRepositoryException {
      return this.mCurrentUser;
   }

   public Map<String, Set<String>> getUserAttributes() throws Exception {
      throw new UnsupportedOperationException();
   }

   public void setUserAttributes(Map<String, Set<String>> attributes) throws Exception {
      throw new UnsupportedOperationException();
   }

   public Set<String> getGroupMemberships(String filterPrefix) throws Exception {
      return Collections.emptySet();
   }

   public void refreshSession() throws Exception {}

   public boolean isStillValid() throws Exception {
      return true;
   }
}
