// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPCosignFilter$WrappedFilterConfig;
import com.cedar.cp.utc.common.CPCosignServletCallbackHandler;
import com.cedar.cp.utc.common.CPRequestWrapper;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.util.Log;
import edu.umich.auth.cosign.CosignAuthenticationFilterIII;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CPCosignFilter extends CosignAuthenticationFilterIII {

   private static Log sLog = new Log(CPCosignFilter.class);
   private boolean mEnabled;
   private boolean mCacheInitialRequest;
   private String mSingleEntryKey;


   public void init(FilterConfig config) {
      CPSystemProperties systemProperties = (CPSystemProperties)config.getServletContext().getAttribute("cpSystemProperties");
      if(systemProperties != null && systemProperties.isCosignSignonFilterEnabled()) {
         sLog.debug("init", "Single signon filter enabled");
         this.mEnabled = true;
         String cosignConfigurationFile = systemProperties.getCosignConfigurationFile();
         if(cosignConfigurationFile != null && cosignConfigurationFile.length() != 0) {
            HashMap mappedValues = new HashMap();
            mappedValues.put("Cosign.ConfigurationFile", cosignConfigurationFile);
            mappedValues.put("Auth.JAASConfigurationFile", System.getProperty("jboss.server.home.dir") + File.separator + "conf" + File.separator + "login-config.xml");
            super.init(new CPCosignFilter$WrappedFilterConfig(config, mappedValues));
            super.setJAASServletCallbackHandler(CPCosignServletCallbackHandler.class);
         } else {
            sLog.error("init", "No cosign configuration file set");
            this.mEnabled = false;
         }
      }

      if(systemProperties != null) {
         this.mCacheInitialRequest = systemProperties.getSingleEntryPoint() != null;
         this.mSingleEntryKey = systemProperties.getSingleEntryPoint();
      }

   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
      HttpServletRequest servletRequest = (HttpServletRequest)request;
      CPRequestWrapper wrapper = new CPRequestWrapper(servletRequest, this.mSingleEntryKey);
      HttpServletResponse httpResponse = (HttpServletResponse)response;
      HttpSession session = servletRequest.getSession();
      if(this.mCacheInitialRequest && session.getAttribute("cp30.initial.request") == null) {
         session.setAttribute("cp30.initial.request", request.getParameter(this.mSingleEntryKey));
      }

      boolean doFilter = false;
      if(this.mEnabled) {
         CPContext e = (CPContext)session.getAttribute("cpContext");
         if(e != null && e.getCPConnection() == null) {
            e = null;
            session.removeAttribute("cpContext");
         }

         if(e == null) {
            super.doFilter(wrapper, response, chain);
         } else {
            doFilter = true;
         }
      } else {
         doFilter = true;
      }

      if(doFilter) {
         try {
            chain.doFilter(request, response);
         } catch (ServletException var10) {
            sLog.error("doFilter", "can\'t chain on", var10);
            httpResponse.sendError(503, var10.getMessage());
         }
      }

   }

   public void destroy() {
      this.mEnabled = false;
   }

}
