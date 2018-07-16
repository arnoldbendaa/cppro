// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.AppletBrowser;
import com.cedar.cp.util.ProgramLauncher;
import java.applet.Applet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

public class Browser extends AppletBrowser {

   private static final String WIN_PATH = "rundll32";
   private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
   private static final String UNIX_PATH = "netscape";
   private static final String UNIX_FLAG = "-remote openURL";


   public static void displayURL(String url) {
      displayURL((Applet)null, url);
   }

   public static void displayURL(Applet applet, String url) {
      if(applet != null) {
         displayURLFromApplet(applet, url);
      } else {
         displayURLFromApplication(url);
      }

   }

   public static boolean isWindowsPlatform() {
      return ProgramLauncher.isWindowsPlatform();
   }

   protected static void displayURLFromApplication(String url) {
      String cmd = null;

      try {
         URL ue;
         try {
            ue = new URL(url);
         } catch (MalformedURLException var7) {
            throw new UnavailableServiceException(var7.getMessage());
         }

         BasicService x2 = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
         x2.showDocument(ue);
      } catch (UnavailableServiceException var8) {
         try {
            if(isWindowsPlatform()) {
               cmd = "rundll32 url.dll,FileProtocolHandler " + url;
               Runtime.getRuntime().exec(cmd);
            } else {
               cmd = "netscape -remote openURL(" + url + ")";
               Process x = Runtime.getRuntime().exec(cmd);

               try {
                  int x1 = x.waitFor();
                  if(x1 != 0) {
                     cmd = "netscape " + url;
                     Runtime.getRuntime().exec(cmd);
                  }
               } catch (InterruptedException var5) {
                  System.err.println("Error bringing up browser, cmd=\'" + cmd + "\'");
                  System.err.println("Caught: " + var5);
               }
            }
         } catch (IOException var6) {
            System.err.println("Could not invoke browser, command=" + cmd);
            System.err.println("Caught: " + var6);
         }
      }

   }
}
