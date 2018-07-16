// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.principal;

import java.io.Serializable;
import java.security.Principal;

public class COAPrincipal implements Principal, Serializable {

   private String mName;


   public COAPrincipal(String name) {
      if(name == null) {
         throw new NullPointerException("illegal null input");
      } else {
         this.mName = name;
      }
   }

   public String getName() {
      return this.mName;
   }

   public String toString() {
      return "COAPrincipal:  " + this.mName;
   }

   public boolean equals(Object o) {
      if(o == null) {
         return false;
      } else if(this == o) {
         return true;
      } else if(!(o instanceof COAPrincipal)) {
         return false;
      } else {
         COAPrincipal that = (COAPrincipal)o;
         return this.getName().equals(that.getName());
      }
   }

   public int hashCode() {
      return this.mName.hashCode();
   }
}
