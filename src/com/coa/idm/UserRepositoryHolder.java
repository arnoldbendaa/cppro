// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm;

import com.coa.idm.UserRepository;
import com.coa.idm.filter.SSOProvider;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

public class UserRepositoryHolder implements Serializable {

   private SSOProvider mSSOProvider;
   private UserRepository mUserRepository;


   public UserRepositoryHolder(SSOProvider provider) {
      this.mSSOProvider = provider;
   }

   public boolean isSSOEnabled() {
      return this.mSSOProvider.isSSOEnabled();
   }

   public UserRepository getUserRepository(HttpServletRequest request) throws Exception {
      if(this.mUserRepository == null) {
         this.mUserRepository = this.mSSOProvider.getUserRepository(request);
      } else {
         this.mSSOProvider.updateUserRepository(this.mUserRepository, request);
      }

      return this.mUserRepository;
   }

   public UserRepository getUserRepository(String logonIdentity, Object identityKey) throws Exception {
      if(this.mUserRepository == null) {
         this.mUserRepository = this.mSSOProvider.getUserRepository(logonIdentity, identityKey);
      } else {
         this.mSSOProvider.updateUserRepository(this.mUserRepository, logonIdentity, identityKey);
      }

      return this.mUserRepository;
   }

   public void clearUserRepository() {
      this.mUserRepository = null;
   }
}
