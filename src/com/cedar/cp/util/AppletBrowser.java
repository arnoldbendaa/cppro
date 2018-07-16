// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.Browser;
import java.applet.Applet;
import java.applet.AppletContext;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

public class AppletBrowser {

   private static final Logger mLog = Logger.getLogger(AppletBrowser.class);


   public static void displayURL(String url) {
      displayURL((Applet)null, url);
   }

   public static void displayURL(Applet applet, String url) {
      if(applet != null) {
         displayURLFromApplet(applet, url);
      } else {
         Browser.displayURLFromApplication(url);
      }

   }

   protected static void displayURLFromApplet(Applet applet, String url) {
      AppletContext appletContext = applet.getAppletContext();

      try {
         mLog.debug("url = " + url);
         appletContext.showDocument(new URL("javascript:doLaunch(\"" + url + "\")"));
      } catch (MalformedURLException var4) {
         mLog.error("Could not invoke browser from applet");
         mLog.error("Caught: " + var4);
      }

   }

}
