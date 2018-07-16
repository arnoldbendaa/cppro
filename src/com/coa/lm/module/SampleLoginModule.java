// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.module;

import com.coa.lm.principal.COAPrincipal;
import java.io.IOException;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class SampleLoginModule implements LoginModule {

   private Subject subject;
   private CallbackHandler callbackHandler;
   private Map sharedState;
   private Map options;
   private boolean debug = false;
   private boolean succeeded = false;
   private boolean commitSucceeded = false;
   private String username;
   private char[] password;
   private COAPrincipal userPrincipal;


   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
      this.subject = subject;
      this.callbackHandler = callbackHandler;
      this.sharedState = sharedState;
      this.options = options;
      this.debug = "true".equalsIgnoreCase((String)options.get("debug"));
   }

   public boolean login() throws LoginException {
      if(this.callbackHandler == null) {
         throw new LoginException("Error: no CallbackHandler available to garner authentication information from the user");
      } else {
         Callback[] callbacks = new Callback[]{new NameCallback("user name: "), new PasswordCallback("password: ", false)};

         try {
            this.callbackHandler.handle(callbacks);
            this.username = ((NameCallback)callbacks[0]).getName();
            char[] usernameCorrect = ((PasswordCallback)callbacks[1]).getPassword();
            if(usernameCorrect == null) {
               usernameCorrect = new char[0];
            }

            this.password = new char[usernameCorrect.length];
            System.arraycopy(usernameCorrect, 0, this.password, 0, usernameCorrect.length);
            ((PasswordCallback)callbacks[1]).clearPassword();
         } catch (IOException var5) {
            throw new LoginException(var5.toString());
         } catch (UnsupportedCallbackException var6) {
            throw new LoginException("Error: " + var6.getCallback().toString() + " not available to garner authentication information " + "from the user");
         }

         if(this.debug) {
            System.out.println("\t\t[SampleLoginModule] user entered user name: " + this.username);
            System.out.print("\t\t[SampleLoginModule] user entered password: ");

            for(int var7 = 0; var7 < this.password.length; ++var7) {
               System.out.print(this.password[var7]);
            }

            System.out.println();
         }

         boolean var8 = false;
         boolean passwordCorrect = false;
         if(this.username.equals("testUser")) {
            var8 = true;
         }

         if(var8 && this.password.length == 12 && this.password[0] == 116 && this.password[1] == 101 && this.password[2] == 115 && this.password[3] == 116 && this.password[4] == 80 && this.password[5] == 97 && this.password[6] == 115 && this.password[7] == 115 && this.password[8] == 119 && this.password[9] == 111 && this.password[10] == 114 && this.password[11] == 100) {
            passwordCorrect = true;
            if(this.debug) {
               System.out.println("\t\t[SampleLoginModule] authentication succeeded");
            }

            this.succeeded = true;
            return true;
         } else {
            if(this.debug) {
               System.out.println("\t\t[SampleLoginModule] authentication failed");
            }

            this.succeeded = false;
            this.username = null;

            for(int i = 0; i < this.password.length; ++i) {
               this.password[i] = 32;
            }

            this.password = null;
            if(!var8) {
               throw new FailedLoginException("User Name Incorrect");
            } else {
               throw new FailedLoginException("Password Incorrect");
            }
         }
      }
   }

   public boolean commit() throws LoginException {
      if(!this.succeeded) {
         return false;
      } else {
         this.userPrincipal = new COAPrincipal(this.username);
         if(!this.subject.getPrincipals().contains(this.userPrincipal)) {
            this.subject.getPrincipals().add(this.userPrincipal);
         }

         if(this.debug) {
            System.out.println("\t\t[SampleLoginModule] added SamplePrincipal to Subject");
         }

         this.username = null;

         for(int i = 0; i < this.password.length; ++i) {
            this.password[i] = 32;
         }

         this.password = null;
         this.commitSucceeded = true;
         return true;
      }
   }

   public boolean abort() throws LoginException {
      if(!this.succeeded) {
         return false;
      } else {
         if(this.succeeded && !this.commitSucceeded) {
            this.succeeded = false;
            this.username = null;
            if(this.password != null) {
               for(int i = 0; i < this.password.length; ++i) {
                  this.password[i] = 32;
               }

               this.password = null;
            }

            this.userPrincipal = null;
         } else {
            this.logout();
         }

         return true;
      }
   }

   public boolean logout() throws LoginException {
      this.subject.getPrincipals().remove(this.userPrincipal);
      this.succeeded = false;
      this.succeeded = this.commitSucceeded;
      this.username = null;
      if(this.password != null) {
         for(int i = 0; i < this.password.length; ++i) {
            this.password[i] = 32;
         }

         this.password = null;
      }

      this.userPrincipal = null;
      return true;
   }
}
