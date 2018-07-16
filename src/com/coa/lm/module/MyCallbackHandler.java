// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.util.Arrays;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class MyCallbackHandler implements CallbackHandler {

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
            System.err.print(var4.getPrompt());
            System.err.flush();
            var4.setName((new BufferedReader(new InputStreamReader(System.in))).readLine());
         } else {
            if(!(callbacks[i] instanceof PasswordCallback)) {
               throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }

            PasswordCallback var5 = (PasswordCallback)callbacks[i];
            System.err.print(var5.getPrompt());
            System.err.flush();
            var5.setPassword(this.readPassword(System.in));
         }
      }

   }

   private char[] readPassword(InputStream in) throws IOException {
      char[] lineBuffer;
      char[] buf = lineBuffer = new char[128];
      int room = buf.length;
      int offset = 0;

      label32:
      while(true) {
         int c;
         switch(c = ((InputStream)in).read()) {
         case -1:
         case 10:
            break label32;
         case 13:
            int ret = ((InputStream)in).read();
            if(ret == 10 || ret == -1) {
               break label32;
            }

            if(!(in instanceof PushbackInputStream)) {
               in = new PushbackInputStream((InputStream)in);
            }

            ((PushbackInputStream)in).unread(ret);
         default:
            --room;
            if(room < 0) {
               buf = new char[offset + 128];
               room = buf.length - offset - 1;
               System.arraycopy(lineBuffer, 0, buf, 0, offset);
               Arrays.fill(lineBuffer, ' ');
               lineBuffer = buf;
            }

            buf[offset++] = (char)c;
         }
      }

      if(offset == 0) {
         return null;
      } else {
         char[] var9 = new char[offset];
         System.arraycopy(buf, 0, var9, 0, offset);
         Arrays.fill(buf, ' ');
         return var9;
      }
   }
}
