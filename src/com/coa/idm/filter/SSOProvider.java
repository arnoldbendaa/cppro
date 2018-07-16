// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter;

import com.coa.idm.UserRepository;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

public interface SSOProvider extends Filter {

   boolean isSSOEnabled();

   UserRepository getUserRepository(HttpServletRequest var1) throws Exception;

   UserRepository getUserRepository(String var1, Object var2) throws Exception;

   void updateUserRepository(UserRepository var1, HttpServletRequest var2) throws Exception;

   void updateUserRepository(UserRepository var1, String var2, Object var3) throws Exception;
}
