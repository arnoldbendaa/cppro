// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter;

import com.coa.idm.UserRepository;
import com.coa.idm.UserRepositoryHolder;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class IdentityManagerRequestWrapper extends HttpServletRequestWrapper {

   private boolean mPassThruRealm;


   public IdentityManagerRequestWrapper(HttpServletRequest request, boolean passThruRealm) {
      super(request);
      this.mPassThruRealm = passThruRealm;
   }

   public boolean isUserInRole(String s) {
      if(this.mPassThruRealm) {
         return true;
      } else {
         UserRepositoryHolder userRepHolder = (UserRepositoryHolder)this.getSession().getAttribute("com.coa.idm.user_repository");
         if(userRepHolder == null) {
            return false;
         } else {
            try {
               UserRepository e = userRepHolder.getUserRepository(this);
               if(e == null) {
                  return false;
               } else {
                  Set roles = e.getGroupMemberships((String)null);
                  return roles.contains(s)?true:((HttpServletRequest)this.getRequest()).isUserInRole(s);
               }
            } catch (Exception var5) {
               var5.printStackTrace();
               return false;
            }
         }
      }
   }
}
