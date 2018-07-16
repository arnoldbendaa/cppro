// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

public class CPNtlmHttpFilterConfig implements FilterConfig {

   private FilterConfig mWrappedConfig;
   private Vector<String> mFilterParameterNames;
   private Map<String, String> mFilterParameters;


   public CPNtlmHttpFilterConfig(FilterConfig filterConfig) {
      this.mWrappedConfig = filterConfig;
      this.mFilterParameterNames = new Vector();
      Enumeration e = filterConfig.getInitParameterNames();

      while(e.hasMoreElements()) {
         this.mFilterParameterNames.add(e.nextElement().toString());
      }

      this.mFilterParameters = new HashMap();
   }

   public String getFilterName() {
      return this.mWrappedConfig.getFilterName();
   }

   public ServletContext getServletContext() {
      return this.mWrappedConfig.getServletContext();
   }

   public String getInitParameter(String string) {
      String value = (String)this.mFilterParameters.get(string);
      if(value == null) {
         value = this.mWrappedConfig.getInitParameter(string);
      }

      return value;
   }

   public Enumeration getInitParameterNames() {
      return this.mFilterParameterNames.elements();
   }

   public void addInitParameter(String name, String value) {
      if(value != null) {
         this.mFilterParameters.put(name, value);
         this.mFilterParameterNames.add(name);
      }

   }
}
