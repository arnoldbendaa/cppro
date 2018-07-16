// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPAuthenticationPolicy;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPNtlmHttpFilterConfig;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.util.Log;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jcifs.http.NtlmHttpFilter;
import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.util.Base64;

public class CPNtlmHttpFilter extends NtlmHttpFilter {

   private static Log sLog = new Log(CPNtlmHttpFilter.class);
   private boolean mEnabled;


   public void init(FilterConfig config) throws ServletException {
      CPSystemProperties systemProperties = (CPSystemProperties)((FilterConfig)config).getServletContext().getAttribute("cpSystemProperties");
      if(systemProperties != null && systemProperties.isNtlmSignonFilterEnabled()) {
         sLog.debug("init", "Ntlm signon filter enabled");
         this.mEnabled = true;
         CPAuthenticationPolicy auth = systemProperties.getActiveAuthenticationPolicy();
         CPNtlmHttpFilterConfig wrappedConfig = new CPNtlmHttpFilterConfig((FilterConfig)config);
         wrappedConfig.addInitParameter("jcifs.netbios.wins", auth.getNtlmNetbiosWins());
         wrappedConfig.addInitParameter("jcifs.smb.client.domain", auth.getNtlmDomain());
         wrappedConfig.addInitParameter("jcifs.http.domainController", auth.getNtlmDomainController());
         wrappedConfig.addInitParameter("jcifs.util.loglevel", String.valueOf(auth.getNtlmLogLevel()));
         wrappedConfig.addInitParameter("jcifs.http.basicRealm", "CollaborativePlanning");
         config = wrappedConfig;
      }

      super.init((FilterConfig)config);
   }

   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
      HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
      HttpSession session = httpServletRequest.getSession();
      if(this.mEnabled) {
         CPContext context = (CPContext)session.getAttribute("cpContext");
         if(context != null && context.getCPConnection() == null) {
            context = null;
            session.removeAttribute("cpContext");
         }

         if(context == null) {
            try {
               NtlmPasswordAuthentication msg = this.negotiate(httpServletRequest, httpServletResponse, false);
               if(msg == null) {
                  return;
               }

               ServletContext src = session.getServletContext();
               CPSystemProperties type1 = (CPSystemProperties)src.getAttribute("cpSystemProperties");
               context = CPContext.logon(type1, httpServletRequest, msg);
               context.setNtlmSignon(true);
               context.setSingleSignon(true);
               session.setAttribute("cpContext", context);
            } catch (Exception var12) {
               sLog.warn("Can\'t perform logon", var12);
               httpServletResponse.sendError(401, var12.getMessage());
               return;
            }
         } else if(httpServletRequest.getMethod().equals("POST")) {
            String msg1 = httpServletRequest.getHeader("Authorization");
            if(msg1 != null && msg1.startsWith("NTLM ")) {
               byte[] src1 = Base64.decode(msg1.substring(5));
               if(src1[8] == 1) {
                  Type1Message type11 = new Type1Message(src1);
                  Type2Message type2 = new Type2Message(type11, new byte[8], (String)null);
                  msg1 = Base64.encode(type2.toByteArray());
                  httpServletResponse.setHeader("WWW-Authenticate", "NTLM " + msg1);
                  httpServletResponse.setStatus(401);
                  httpServletResponse.setContentLength(0);
                  httpServletResponse.flushBuffer();
                  return;
               }
            }
         }
      }

      filterChain.doFilter(httpServletRequest, httpServletResponse);
   }

   public void destroy() {
      this.mEnabled = false;
      super.destroy();
   }

}
