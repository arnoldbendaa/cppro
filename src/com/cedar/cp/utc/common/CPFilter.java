// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPContextCache;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.common.SingleEntryPointMismatchException;
import com.cedar.cp.util.Log;
import com.coa.idm.UserRepository;
import com.coa.idm.UserRepositoryHolder;
import com.coa.portal.client.Application;
import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.web.PortalFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CPFilter extends PortalFilter {
   private static Log sLog = new Log(CPFilter.class);
   private FilterConfig mConfig;
   private Set<String> mUnprotectedPages = new HashSet();
   private String mDefaultSecurePage = "./";
   private String mPassthruLoginPage = "./";
   private String mPatchStatusPage = "./";
   private boolean mAllowPassthru;
   public static final String LOGIN_REDIRECT = "cp30.login.redirect";
   public static final String INITIAL_REQUEST = "cp30.initial.request";
   private boolean mSSO_Enabled;


   public void init(FilterConfig config) throws ServletException {
	  
	  CPSystemProperties systemProperties1 = new CPSystemProperties();
	  config.getServletContext().setAttribute("cpSystemProperties", systemProperties1);

      this.mConfig = config;
      CPSystemProperties systemProperties = (CPSystemProperties)config.getServletContext().getAttribute("cpSystemProperties");
      
      if(systemProperties != null) {
         this.mAllowPassthru = systemProperties.getAllowPassthru();
      }

      sLog.info("init", "allow passthru: " + this.mAllowPassthru);
      String unsecurePages = this.mConfig.getInitParameter("unsecurePages");
      StringTokenizer tk = new StringTokenizer(unsecurePages, ";");

      while(tk.hasMoreTokens()) {
         String token = tk.nextToken();
         sLog.info("init", "unsecure page " + token);
         this.mUnprotectedPages.add(token);
      }

      this.mDefaultSecurePage = this.mConfig.getInitParameter("unsecureRootPage");
      this.mPassthruLoginPage = this.mConfig.getInitParameter("passthruLoginPage");
      this.mPatchStatusPage = this.mConfig.getInitParameter("patchStatusPage");
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest servletRequest = (HttpServletRequest)request;
      HttpSession session = servletRequest.getSession();
      CPContext cpContext = (CPContext)session.getAttribute("cpContext");
      CPSystemProperties systemProperties = (CPSystemProperties)session.getServletContext().getAttribute("cpSystemProperties");
      String servletPath;
      RequestDispatcher rd2;
      
		// Logout and redirect to CP web app if user is a mobile user
		if (cpContext != null && cpContext.isMobileAPIUser()) {
			cpContext.mobileLogoff();
			session.removeAttribute("cpContext");
			session.invalidate();
			((HttpServletResponse) response).sendRedirect("/cp/");
			return;
		}
		
      if(systemProperties != null) {
         this.mSSO_Enabled = systemProperties.isSSOSignonFilterEnabled();
         if(systemProperties.getPatchStatus().length() > 0) {
            servletPath = servletRequest.getServletPath();
            if(servletPath != null && !servletPath.equals(this.mPatchStatusPage)) {
               rd2 = servletRequest.getRequestDispatcher(this.mPatchStatusPage);
               rd2.forward(request, response);
               return;
            }
         }
      }

      if(cpContext != null && cpContext.getCPConnection() == null) {
         cpContext = null;
         session.removeAttribute("cpContext");
      }

      if(cpContext == null) {
         PortalPrincipal servletPath1 = null;

         try {
        	String id = Application.CP.getId();
            servletPath1 = this.getPortalPrincipal(id, request);
         } catch (ServletException var15) {
            var15.printStackTrace();
         }

         if(servletPath1 != null) {
            try {
               CPSystemProperties rd = (CPSystemProperties)session.getServletContext().getAttribute("cpSystemProperties");
               cpContext = CPContext.logon(rd, servletRequest, servletPath1);
               cpContext.setPortalSignon(true);
               cpContext.setPortalUser(true);
               session.setAttribute("cpContext", cpContext);
               session.setMaxInactiveInterval('\ua8c0');
            } catch (SingleEntryPointMismatchException var13) {
               throw new IllegalStateException("Single entry exception");
            } catch (Exception var14) {
               throw new IllegalStateException("CPFilter can\'t process portal user", var14);
            }
         }
      }

      if(cpContext == null && this.mSSO_Enabled) {
         Object servletPath2 = session.getAttribute("com.coa.idm.user_repository");
         if(servletPath2 != null) {
            UserRepositoryHolder rd1 = (UserRepositoryHolder)session.getAttribute("com.coa.idm.user_repository");
            if(rd1 != null && rd1.isSSOEnabled()) {
               try {
                  UserRepository link = rd1.getUserRepository(servletRequest);
                  cpContext = CPContext.logon(systemProperties, servletRequest, link);
                  cpContext.setSSOSignon(true);
                  session.setAttribute("cpContext", cpContext);
               } catch (InvalidCredentialsException var11) {
                  ;
               } catch (Exception var12) {
                  throw new ServletException("We cannot get the user repository from the request", var12);
               }
            }
         }
      }

      if(cpContext == null || cpContext.isMustChangePassword()) {
         servletPath = servletRequest.getServletPath();
         sLog.debug("doFilter", "Checking for servletPath " + servletPath);
         if(servletPath != null) {
            if(!this.mUnprotectedPages.contains(servletPath)) {
               if(this.mAllowPassthru) {
                  StringBuffer link1 = new StringBuffer(servletPath);
                  if(servletRequest.getQueryString() != null) {
                     link1.append('?');
                     link1.append(servletRequest.getQueryString());
                  }

                  sLog.debug("doFilter", "remembering fast link to " + link1);
                  sLog.debug("doFilter", "forwarding to root page " + this.mPassthruLoginPage);
                  session.setAttribute("cp30.login.redirect", link1.toString());
                  rd2 = servletRequest.getRequestDispatcher(this.mPassthruLoginPage);
               } else {
                  rd2 = servletRequest.getRequestDispatcher(this.mDefaultSecurePage);
               }

               rd2.forward(request, response);
               return;
            }
         } else {
            sLog.debug("doFilter", "ignoring filter for " + servletPath);
         }
      }

      if(cpContext != null && (cpContext.isNtlmSignon() || cpContext.isCosignSignon() || cpContext.isPortalSignon())) {
         CPContextCache.getCPContextId(cpContext);
      }

      chain.doFilter(request, response);
   }

   public void destroy() {}

}
