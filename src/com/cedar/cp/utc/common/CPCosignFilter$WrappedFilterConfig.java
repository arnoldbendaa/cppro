// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

class CPCosignFilter$WrappedFilterConfig implements FilterConfig {

   private FilterConfig mDelegate;
   private Map mOverrides;


   public CPCosignFilter$WrappedFilterConfig(FilterConfig delegateFilterConfig, Map overrides) {
      this.mDelegate = delegateFilterConfig;
      this.mOverrides = overrides;
   }

   public String getFilterName() {
      return this.mDelegate.getFilterName();
   }

   public ServletContext getServletContext() {
      return this.mDelegate.getServletContext();
   }

   public String getInitParameter(String string) {
      Object o = this.mOverrides.get(string);
      return o != null?o.toString():this.mDelegate.getInitParameter(string);
   }

   public Enumeration getInitParameterNames() {
      return this.mDelegate.getInitParameterNames();
   }
}
