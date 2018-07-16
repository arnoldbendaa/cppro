// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter;

import com.coa.idm.UserRepositoryHolder;
import com.coa.idm.filter.IdentityManagerRequestWrapper;
import com.coa.idm.filter.SSOFilter$1;
import com.coa.idm.filter.SSOProvider;
import com.coa.idm.filter.SSOProviderType;
import com.coa.idm.filter.providers.COAConfigurationEntry;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSOFilter implements Filter {

   private static Log sLog = LogFactory.getLog(SSOFilter.class);
   private FilterConfig mFilterConfig;
   private SSOProviderType mProviderType;
   private SSOProvider mProvider;
   public static final String USER_REPOSITORY_KEY = "com.coa.idm.user_repository";
   private static final String JAAS_REALM = "AMRealm";
   private boolean mPassthruRealm;


   public SSOFilter() {
      this.mProviderType = SSOProviderType.NONE;
      this.mProvider = null;
      this.mPassthruRealm = false;
      SSOProviderType defaultProviderType = getDefaultSSOProviderType();
      if(defaultProviderType != null) {
         this.setSSOProvider(defaultProviderType);
      }

   }

   public static SSOProviderType getDefaultSSOProviderType() {
      SSOProviderType defaultProviderType = SSOProviderType.NONE;
      InputStream location = SSOFilter.class.getResourceAsStream("/IdentityManager.properties");
      if(location != null) {
         Properties p = new Properties();

         try {
            p.load(location);
            String ie = p.getProperty("Provider");
            sLog.debug("Provider=" + ie);
            if(ie != null) {
               defaultProviderType = SSOProviderType.valueOf(ie.toUpperCase());
               if(defaultProviderType == null) {
                  throw new IllegalArgumentException("Unknown provider " + ie);
               }
            }
         } catch (IOException var4) {
            sLog.debug("Can\'t process /IdentityManager.properties", var4);
         }
      } else {
         sLog.debug("Can\'t locate /IdentityManager.properties");
      }

      return defaultProviderType;
   }

   public void init(FilterConfig filterConfig) throws ServletException {
      this.mFilterConfig = filterConfig;
      this.ensureAMRealmExists();
   }

   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      this.ensureProviderSet();
      this.ensureUserRepositorySet(servletRequest);
      this.mProvider.doFilter(new IdentityManagerRequestWrapper((HttpServletRequest)servletRequest, this.mPassthruRealm), servletResponse, filterChain);
   }

   public void setSSOProvider(String provider) {
      sLog.debug("Setting Provider=" + provider);
      this.mProviderType = SSOProviderType.NONE;
      if(provider != null) {
         SSOProviderType p = SSOProviderType.valueOf(provider.toUpperCase());
         if(p == null) {
            throw new IllegalArgumentException("Unknown provider " + provider);
         }

         this.setSSOProvider(p);
      }

   }

   public void setSSOProvider(SSOProviderType type) {
      this.mProviderType = type;
   }

   public boolean isSSOEnabled() {
      return !this.mProviderType.equals(SSOProviderType.NONE);
   }

   private void ensureAMRealmExists() {
      Configuration currentConfiguration = Configuration.getConfiguration();
      AppConfigurationEntry[] ammAppConfigurationEntries = currentConfiguration.getAppConfigurationEntry("AMRealm");
      if(ammAppConfigurationEntries != null && !"other".equals(ammAppConfigurationEntries[0].getOptions().get("jboss.security.security_domain"))) {
         if(ammAppConfigurationEntries[0].getClass().getName().equals(COAConfigurationEntry.class.getName())) {
            this.mPassthruRealm = true;
         }
      } else {
         this.mPassthruRealm = true;
         AppConfigurationEntry[] newCoaAppConfigurationEntries = new AppConfigurationEntry[]{new COAConfigurationEntry()};
         Configuration.setConfiguration(new SSOFilter$1(this, newCoaAppConfigurationEntries, currentConfiguration));
      }

   }

   private void ensureProviderSet() throws ServletException {
      if(this.mProvider == null) {
         try {
            Class e = Class.forName(this.mProviderType.getProviderClass());
            this.mProvider = (SSOProvider)e.newInstance();
            if(this.mFilterConfig != null) {
               this.mProvider.init(this.mFilterConfig);
            }

            this.initialiseProvider(this.mProviderType, this.mProvider);
         } catch (Exception var2) {
            throw new ServletException("Unable to create SSO Provider class " + this.mProviderType, var2);
         }
      }

   }

   protected void initialiseProvider(SSOProviderType providerType, SSOProvider provider) throws ServletException {}

   private void ensureUserRepositorySet(ServletRequest servletRequest) {
      HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
      UserRepositoryHolder holder = (UserRepositoryHolder)httpRequest.getSession().getAttribute("com.coa.idm.user_repository");
      if(holder == null) {
         holder = new UserRepositoryHolder(this.mProvider);
         httpRequest.getSession().setAttribute("com.coa.idm.user_repository", holder);
      }

   }

   public UserRepositoryHolder createUserRepositoryHolder() throws IllegalStateException {
      try {
         this.ensureProviderSet();
         return new UserRepositoryHolder(this.mProvider);
      } catch (ServletException var2) {
         throw new IllegalStateException("Internal error " + var2);
      }
   }

   public void destroy() {
      if(this.mProvider != null) {
         this.mProvider.destroy();
      }

   }

   public SSOProviderType getProviderType() {
      return this.mProviderType;
   }

}
