// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter;

import com.coa.idm.UserRepositoryHolder;
import com.coa.idm.filter.SSOFilter;
import com.coa.idm.filter.SSOProviderType;

public class SSOBeanAccess {

   private SSOFilter mFilter = new SSOFilter();
   private UserRepositoryHolder mHolder;


   public SSOProviderType getProviderType() {
      return this.mFilter.getProviderType();
   }

   public UserRepositoryHolder getUserRepositoryHolder() {
      if(this.mHolder == null) {
         this.mHolder = this.mFilter.createUserRepositoryHolder();
      }

      return this.mHolder;
   }
}
