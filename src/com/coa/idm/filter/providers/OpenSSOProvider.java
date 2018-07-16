// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:44
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter.providers;

import com.coa.idm.UserRepository;
import com.coa.idm.UserRepositoryException;
import com.coa.idm.filter.providers.BaseProvider;
import com.coa.idm.filter.providers.OpenSSOUserRepository;
//import com.sun.identity.agents.filter.AmAgentFilter;//arnold
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class OpenSSOProvider extends BaseProvider {

   private Filter mWrappedFilter = null;


   public void init(FilterConfig filterConfig) throws ServletException {
      super.init(filterConfig);
      this.getWrappedFilter().init(filterConfig);
   }

   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      this.getWrappedFilter().doFilter(servletRequest, servletResponse, filterChain);
   }

   public void destroy() {
      this.getWrappedFilter().destroy();
   }

   public boolean isSSOEnabled() {
      return true;
   }

   private Filter getWrappedFilter() {
      if(this.mWrappedFilter == null) {
//         this.mWrappedFilter = new AmAgentFilter();//arnold
      }

      return this.mWrappedFilter;
   }

   public UserRepository getUserRepository(HttpServletRequest servletRequest) throws Exception {
      if(servletRequest.getUserPrincipal() == null) {
         throw new IllegalArgumentException("The servletRequest has no user principal");
      } else {
         OpenSSOUserRepository userRep = new OpenSSOUserRepository(servletRequest.getUserPrincipal().getName());
         userRep.createSSOToken(servletRequest);
         return userRep;
      }
   }

   public UserRepository getUserRepository(String logonIdentity, Object identityKey) throws Exception {
      OpenSSOUserRepository userRep = new OpenSSOUserRepository(logonIdentity);
      userRep.createSSOToken(identityKey.toString());
      return userRep;
   }

   public void updateUserRepository(UserRepository userRepository, HttpServletRequest servletRequest) throws Exception {
      if(userRepository.getLogonIdentity() != null && !userRepository.getLogonIdentity().equals(servletRequest.getUserPrincipal().getName())) {
         throw new UserRepositoryException("The principal has changed");
      } else {
         if(!userRepository.isStillValid()) {
            OpenSSOUserRepository userRep = (OpenSSOUserRepository)userRepository;
            userRep.createSSOToken(servletRequest);
         }

      }
   }

   public void updateUserRepository(UserRepository userRepository, String logonIdentity, Object identityKey) throws Exception {
      if(userRepository.getLogonIdentity() != null && !userRepository.getLogonIdentity().equals(logonIdentity)) {
         throw new UserRepositoryException("The principal has changed");
      } else {
         if(!userRepository.isStillValid()) {
            OpenSSOUserRepository userRep = (OpenSSOUserRepository)userRepository;
            userRep.createSSOToken(identityKey.toString());
         }

      }
   }
}
