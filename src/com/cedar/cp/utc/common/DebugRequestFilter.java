// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.util.Log;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class DebugRequestFilter implements Filter {

   private static Log sLog = new Log(DebugRequestFilter.class);


   public void init(FilterConfig config) throws ServletException {}

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest servletRequest = (HttpServletRequest)request;
      String servletPath = servletRequest.getServletPath();
      if(sLog.isDebugEnabled()) {
         sLog.debug("debugRequestFilter", servletRequest.getMethod() + " " + servletPath + "?" + servletRequest.getQueryString());
         Enumeration names = servletRequest.getHeaderNames();

         while(names.hasMoreElements()) {
            String header = names.nextElement().toString();
            sLog.debug("debugRequestFilter", "\tHeader field " + header + "=" + servletRequest.getHeader(header));
         }
      }

      chain.doFilter(request, response);
   }

   public void destroy() {}

}
