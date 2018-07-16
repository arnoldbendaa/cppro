// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.coa.idm.UserRepository;
import com.coa.idm.UserRepositoryException;
import com.coa.idm.UserRepositoryPrincipal;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CPAdminUserRepositoryImpl implements UserRepository, Serializable {

   protected Map<String, Set<String>> mMap;
   protected String mUserId;


   public CPAdminUserRepositoryImpl() {}

   public CPAdminUserRepositoryImpl(String userId) {
      this.mMap = new HashMap();
      HashSet value = new HashSet();
      value.add(userId);
      this.mMap.put("uid", value);
      this.mUserId = userId;
   }

   public String getPhysicalRole(String logicalRole) {
      return null;
   }

   public Principal getCurrentUser() throws UserRepositoryException {
      return new UserRepositoryPrincipal(this.mUserId);
   }

   public Map<String, Set<String>> getUserAttributes() throws Exception {
      return this.mMap;
   }

   public void setUserAttributes(Map<String, Set<String>> attributes) throws Exception {
      this.mMap = attributes;
   }

   public Set<String> getLogicalGroupMemberships(String s) throws Exception {
      throw new IllegalStateException("Not implemented");
   }

   public String getLogonIdentity() {
      return this.mUserId;
   }

   public Set<String> getGroupMemberships(String s) throws Exception {
      return Collections.EMPTY_SET;
   }

   public void refreshSession() throws Exception {}

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

   public boolean isStillValid() throws Exception {
      return true;
   }
}
