// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm;

import com.coa.lm.module.FixedCallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class SampleAcn {

   public static void main(String[] args) {
      LoginContext lc = null;

      try {
         lc = new LoginContext("Sample", new FixedCallbackHandler("Access.LDAP@coa.local", "Password1"));
      } catch (LoginException var6) {
         System.err.println("Cannot create LoginContext. " + var6.getMessage());
         System.exit(-1);
      } catch (SecurityException var7) {
         System.err.println("Cannot create LoginContext. " + var7.getMessage());
         System.exit(-1);
      }

      int i = 0;

      while(i < 3) {
         try {
            lc.login();
            break;
         } catch (LoginException var8) {
            System.err.println("Authentication failed:");
            System.err.println("  " + var8.getMessage());

            try {
               Thread.currentThread();
               Thread.sleep(3000L);
            } catch (Exception var5) {
               ;
            }

            ++i;
         }
      }

      if(i == 3) {
         System.out.println("Sorry");
         System.exit(-1);
      }

      System.out.println("Authentication succeeded!");
   }
}
