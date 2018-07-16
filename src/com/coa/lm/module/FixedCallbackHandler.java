// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.module;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class FixedCallbackHandler implements CallbackHandler {

   private String mUser;
   private String mPassword;


   public FixedCallbackHandler(String user, String password) {
      this.mUser = user;
      this.mPassword = password;
   }

   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
      for(int i = 0; i < callbacks.length; ++i) {
         if(callbacks[i] instanceof TextOutputCallback) {
            TextOutputCallback pc = (TextOutputCallback)callbacks[i];
            switch(pc.getMessageType()) {
            case 0:
               System.out.println(pc.getMessage());
               break;
            case 1:
               System.out.println("WARNING: " + pc.getMessage());
               break;
            case 2:
               System.out.println("ERROR: " + pc.getMessage());
               break;
            default:
               throw new IOException("Unsupported message type: " + pc.getMessageType());
            }
         } else if(callbacks[i] instanceof NameCallback) {
            NameCallback var4 = (NameCallback)callbacks[i];
            var4.setName(this.mUser);
         } else {
            if(!(callbacks[i] instanceof PasswordCallback)) {
               throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }

            PasswordCallback var5 = (PasswordCallback)callbacks[i];
            var5.setPassword(this.mPassword.toCharArray());
         }
      }

   }
}
