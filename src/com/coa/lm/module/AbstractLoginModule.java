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

public abstract class AbstractLoginModule implements LoginModule {

   private CallbackHandler mCallbackHandler;
   private Subject mSubject;
   protected boolean mDebug = false;
   private boolean mSucceeded = false;
   private boolean mCommitSucceeded = false;
   private String mUsername;
   private char[] mPassword;
   private COAPrincipal mUserPrincipal;


   public boolean commit() throws LoginException {
      if(!this.mSucceeded) {
         return false;
      } else {
         this.mUserPrincipal = new COAPrincipal(this.mUsername);
         if(!this.mSubject.getPrincipals().contains(this.mUserPrincipal)) {
            this.mSubject.getPrincipals().add(this.mUserPrincipal);
         }

         this.mUsername = null;

         for(int i = 0; i < this.mPassword.length; ++i) {
            this.mPassword[i] = 32;
         }

         this.mPassword = null;
         this.mCommitSucceeded = true;
         return true;
      }
   }

   public boolean abort() throws LoginException {
      if(!this.mSucceeded) {
         return false;
      } else {
         if(this.mSucceeded && !this.mCommitSucceeded) {
            this.mSucceeded = false;
            this.mUsername = null;
            if(this.mPassword != null) {
               for(int i = 0; i < this.mPassword.length; ++i) {
                  this.mPassword[i] = 32;
               }

               this.mPassword = null;
            }

            this.mUserPrincipal = null;
         } else {
            this.logout();
         }

         return true;
      }
   }

   public Callback[] configureCallbacks() {
      Callback[] callbacks = new Callback[]{new NameCallback("Enter user name"), new PasswordCallback("Enter Password", false)};
      return callbacks;
   }

   public abstract boolean authenticateUser(String var1, char[] var2) throws Exception;

   public boolean login() throws LoginException {
      Callback[] callbacks = new Callback[]{new NameCallback("user name: "), new PasswordCallback("mPassword: ", false)};

      try {
         if(this.mCallbackHandler == null) {
            throw new LoginException("No callback handler");
         }

         this.mCallbackHandler.handle(callbacks);
         this.mUsername = ((NameCallback)callbacks[0]).getName();
         char[] e = ((PasswordCallback)callbacks[1]).getPassword();
         if(e == null) {
            e = new char[0];
         }

         this.mPassword = new char[e.length];
         System.arraycopy(e, 0, this.mPassword, 0, e.length);
         ((PasswordCallback)callbacks[1]).clearPassword();
      } catch (IOException var4) {
         throw new LoginException(var4.toString());
      } catch (UnsupportedCallbackException var5) {
         throw new LoginException("Error: " + var5.getCallback().toString() + " not available to garner authentication information from the user");
      }

      int var6;
      if(this.mDebug) {
         for(var6 = 0; var6 < this.mPassword.length; ++var6) {
            System.out.print(this.mPassword[var6]);
         }

         System.out.println();
      }

      try {
         if(this.authenticateUser(this.mUsername, this.mPassword)) {
            this.mSucceeded = true;
            return true;
         } else {
            this.mSucceeded = false;
            this.mUsername = null;

            for(var6 = 0; var6 < this.mPassword.length; ++var6) {
               this.mPassword[var6] = 32;
            }

            this.mPassword = null;
            throw new FailedLoginException("Invalid credentials");
         }
      } catch (Exception var3) {
         if(this.mDebug) {
            var3.printStackTrace();
         }

         throw new FailedLoginException(var3.getMessage());
      }
   }

   public boolean logout() throws LoginException {
      this.mSubject.getPrincipals().remove(this.mUserPrincipal);
      this.mSucceeded = false;
      this.mSucceeded = this.mCommitSucceeded;
      this.mUsername = null;
      if(this.mPassword != null) {
         for(int i = 0; i < this.mPassword.length; ++i) {
            this.mPassword[i] = 32;
         }

         this.mPassword = null;
      }

      this.mUserPrincipal = null;
      return true;
   }

   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
      this.mCallbackHandler = callbackHandler;
      this.mSubject = subject;
      this.mDebug = "true".equalsIgnoreCase((String)options.get("debug"));
   }
}
