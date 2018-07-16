// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter.providers;

import com.coa.idm.UserRepository;
import com.coa.idm.filter.providers.BaseProvider;
import com.coa.idm.filter.providers.NoneUserRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class NoneProvider extends BaseProvider {

   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      filterChain.doFilter(servletRequest, servletResponse);
   }

   public void destroy() {}

   public boolean isSSOEnabled() {
      return false;
   }

   public UserRepository getUserRepository(HttpServletRequest servletRequest) {
      return new NoneUserRepository();
   }

   public UserRepository getUserRepository(String logonIdentity, Object identityKey) {
      return new NoneUserRepository();
   }

   public void checkUserRepository(HttpServletRequest servletRequest) throws Exception {}

   public void updateUserRepository(UserRepository userRepository, HttpServletRequest servletRequest) throws Exception {}

   public void updateUserRepository(UserRepository userRepository, String logonIdentity, Object identityKey) throws Exception {}
}
