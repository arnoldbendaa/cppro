// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.WhiteSpaceFilter$1;
import com.cedar.cp.utc.common.WhiteSpaceFilter$FilterResponse;
import com.cedar.cp.util.Log;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WhiteSpaceFilter implements Filter {

   private static Log sLog = new Log(WhiteSpaceFilter.class);
   private Set mFilterActions = new HashSet();


   public void init(FilterConfig config) throws ServletException {
      sLog.info("init", "White space filter initialising");
      String filterActions = config.getInitParameter("filterActions");
      StringTokenizer tk = new StringTokenizer(filterActions, ";");

      while(tk.hasMoreTokens()) {
         String token = tk.nextToken();
         sLog.info("init", "filter action " + token);
         this.mFilterActions.add(token);
      }

   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      long start = System.currentTimeMillis();
      HttpServletRequest servletRequest = (HttpServletRequest)request;
      String servletPath = servletRequest.getServletPath();
      Object filteredResponse = response;
      if(servletPath != null && this.mFilterActions.contains(servletPath)) {
         sLog.debug("doFilter", "Filtering action " + servletPath);
         filteredResponse = new WhiteSpaceFilter$FilterResponse(this, (HttpServletResponse)response, (WhiteSpaceFilter$1)null);
      }

      chain.doFilter(request, (ServletResponse)filteredResponse);
      sLog.info("doFilter", "Time to process request " + servletPath + " was " + (System.currentTimeMillis() - start));
   }

   public void destroy() {
      sLog.info("destroy", "WhiteSpaceFilter being destroyed");
   }

}
