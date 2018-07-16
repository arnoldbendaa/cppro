// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.module;

import com.coa.lm.module.AbstractLoginModule;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

public class LdapLoginModule extends AbstractLoginModule {

   private String mProtocol;
   private String mUrl;
   private String mStart;
   private String mAttributeName;
   private String mFilter;
   private String mAdminUser;
   private String mAdminPassword;


   public boolean authenticateUser(String userName, char[] password) throws Exception {
      String ldapCtxFactory = "com.sun.jndi.ldap.LdapCtxFactory";
      InitialLdapContext initialContext = null;

      boolean var17;
      try {
         Hashtable e = new Hashtable();
         e.put("java.naming.factory.initial", ldapCtxFactory);
         e.put("java.naming.security.authentication", this.mProtocol);
         e.put("java.naming.security.principal", this.mAdminUser);
         e.put("java.naming.security.credentials", this.mAdminPassword);
         e.put("java.naming.provider.url", this.mUrl);
         initialContext = new InitialLdapContext(e, (Control[])null);
         SearchControls sc = new SearchControls();
         sc.setSearchScope(2);
         sc.setReturningAttributes(new String[]{"distinguishedName", this.mAttributeName});
         String ldapWhere = "(&(" + this.mAttributeName + "=" + userName + ")" + this.mFilter + ")";
         NamingEnumeration results = initialContext.search(this.mStart, ldapWhere, sc);
         if(results == null || !results.hasMoreElements()) {
            return false;
         }

         SearchResult result = (SearchResult)results.nextElement();
         if(results.hasMoreElements()) {
            if(this.mDebug) {
               System.out.println("More than one user record found");
            }

            throw new IllegalStateException("More than one record found");
         }

         Attributes attrs = result.getAttributes();
         if(attrs == null) {
            return false;
         }

         Attribute attr = attrs.get(this.mAttributeName);
         if(attr == null) {
            return false;
         }

         Attribute dnAttr = attrs.get("distinguishedName");
         String dn = dnAttr.get().toString();
         Hashtable userEnv = new Hashtable();
         userEnv.put("java.naming.factory.initial", ldapCtxFactory);
         userEnv.put("java.naming.security.authentication", this.mProtocol);
         userEnv.put("java.naming.security.principal", userName);
         userEnv.put("java.naming.security.credentials", new String(password));
         userEnv.put("java.naming.provider.url", this.mUrl + dn);
         InitialLdapContext userContext = null;

         try {
            Exception ue;
            try {
               userContext = new InitialLdapContext(userEnv, (Control[])null);
               if(userContext == null) {
                  return false;
               }

               //ue = true;
               //return (boolean)ue;
               return true;
            } catch (Exception var28) {
               //ue = var28;
               var17 = false;
            }
         } finally {
            if(userContext != null) {
               userContext.close();
            }

         }
      } catch (Exception var30) {
         if(this.mDebug) {
            var30.printStackTrace();
         }

         throw var30;
      } finally {
         if(initialContext != null) {
            initialContext.close();
         }

      }

      return var17;
   }

   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
      super.initialize(subject, callbackHandler, sharedState, options);
      this.mProtocol = (String)options.get("protocol");
      this.mUrl = (String)options.get("url");
      this.mStart = (String)options.get("start");
      this.mAttributeName = (String)options.get("attributeName");
      this.mFilter = (String)options.get("filter");
      this.mAdminUser = (String)options.get("adminUser");
      this.mAdminPassword = (String)options.get("adminPassword");
      if(this.mProtocol == null) {
         this.mProtocol = "simple";
      }

   }
}
