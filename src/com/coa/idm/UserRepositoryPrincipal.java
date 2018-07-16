// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm;

import java.security.Principal;

public class UserRepositoryPrincipal implements Principal {

   private String mName;


   public UserRepositoryPrincipal(String name) {
      this.mName = name;
   }

   public boolean equals(Object o) {
      if(this == o) {
         return true;
      } else if(o != null && this.getClass() == o.getClass()) {
         UserRepositoryPrincipal that = (UserRepositoryPrincipal)o;
         if(this.mName != null) {
            if(!this.mName.equals(that.mName)) {
               return false;
            }
         } else if(that.mName != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.mName != null?this.mName.hashCode():0;
   }

   public String getName() {
      return this.mName;
   }
}
