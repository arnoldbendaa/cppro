// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.webwork.ClientDispatcher;
import java.applet.Applet;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import webwork.action.Action;
import webwork.action.client.ActionResult;

public class CPClientDispatcher extends ClientDispatcher {

   private static boolean sProxyConfig = false;
   private static String sProxyHost = "";
   private static int sProxyPort = 0;
   private Cookie mSessionId;
   private HttpClient mHttpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
   private transient Log mLog = new Log(this.getClass());


   public CPClientDispatcher(Applet applet, String s) throws MalformedURLException {
      super(applet, s);
   }

   public CPClientDispatcher(String s) {
      super(s);
   }

   public ActionResult execute(Action action) throws Exception {
      String host = this.getHost();
      URL webworkHostServlet = new URL(host);
      String proxy;
      int proxyPort;
      if(!isProxyConfig()) {
         proxy = this.getProxyConfig(webworkHostServlet.getHost());
         proxyPort = -1;
         int post = proxy.indexOf(":");
         if(post != -1) {
            proxyPort = Integer.parseInt(proxy.substring(post + 1));
            proxy = proxy.substring(0, post);
         }

         setProxyHost(proxy);
         setProxyPort(proxyPort);
         setProxyConfig(true);
      }
//
//      proxy = getProxyHost();
//      proxyPort = getProxyPort();
//      PostMethod var25 = new PostMethod(host);
//      if(proxyPort != -1) {
//         HostConfiguration sessionId = this.mHttpClient.getHostConfiguration();
//         if(sessionId == null) {
//            sessionId = new HostConfiguration();
//         }
//
//         sessionId.setProxy(proxy, proxyPort);
//         this.mHttpClient.setHostConfiguration(sessionId);
//      }
//
//      var25.addParameter("Content-Type", "java-internal/" + action.getClass().getName());
//      Cookie var26 = this.getSessionId();
//      if(var26 != null) {
//         this.mHttpClient.getState().addCookie(var26);
//      }
//
//      ByteArrayOutputStream out = new ByteArrayOutputStream();
//      ObjectOutputStream objOut = new ObjectOutputStream(out);
//      objOut.writeObject(action);
//      BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(out.toByteArray()));
//      InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(in);
//      var25.setRequestEntity(requestEntity);
      ActionResult actionResult = null;
//
//      try {
//         this.mHttpClient.executeMethod(var25);
//
//         InputStream inputStream;
//         try {
//            inputStream = var25.getResponseBodyAsStream();
//         } catch (Exception var23) {
//            this.mLog.warn("execute", var23.getMessage());
//            throw var23;
//         }
//
//         ObjectInputStream inObj = this.createObjectInputStream(new BufferedInputStream(inputStream));
//         var26 = this.getSessionId();
//         if(var26 == null) {
//            Cookie[] cookies = this.mHttpClient.getState().getCookies();
//            Cookie[] arr$ = cookies;
//            int len$ = cookies.length;
//
//            for(int i$ = 0; i$ < len$; ++i$) {
//               Cookie cookie = arr$[i$];
//               if(cookie.getName().equals("JSESSIONID")) {
//                  this.setSessionId(cookie);
//                  break;
//               }
//            }
//         }
//
//         actionResult = (ActionResult)inObj.readObject();
//         if(actionResult.getException() != null) {
//            throw actionResult.getException();
//         }
//      } finally {
//         in.close();
//         var25.releaseConnection();
//      }
//
      return actionResult;
   }

   private String getProxyConfig(String connectionHost) {
      String proxies;
      String overrides;
      if(System.getProperty("javaplugin.proxy.usebrowsersettings", "false").equalsIgnoreCase("true")) {
         proxies = System.getProperty("javaplugin.proxy.config.list");
         overrides = System.getProperty("javaplugin.proxy.config.bypass");
      } else {
         proxies = System.getProperty("javaplugin.proxy.settings");
         overrides = System.getProperty("javaplugin.proxy.bypass");
      }

      String separator = ";";
      if(proxies != null && !proxies.equals("")) {
         int semi = proxies.indexOf(59);
         if(semi == -1) {
            separator = ",";
         }

         StringTokenizer tokenizer;
         String token;
         if(overrides != null && !overrides.equals("")) {
            tokenizer = new StringTokenizer(overrides, ",");

            while(tokenizer.hasMoreTokens()) {
               token = tokenizer.nextToken();
               if(token.equals("<local>") && (connectionHost.equals("127.0.0.1") || connectionHost.equalsIgnoreCase("localhost"))) {
                  return "";
               }

               if(token.equalsIgnoreCase(connectionHost)) {
                  return "";
               }
            }
         }

         tokenizer = new StringTokenizer(proxies, separator);

         while(tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if(token.startsWith("http=")) {
               return token.substring("http=".length());
            }
         }
      }

      return "";
   }

   public String getHost() {
      return super.getHost();
   }

   private static boolean isProxyConfig() {
      return sProxyConfig;
   }

   private static void setProxyConfig(boolean proxyConfig) {
      sProxyConfig = proxyConfig;
   }

   private static String getProxyHost() {
      return sProxyHost;
   }

   private static void setProxyHost(String proxyHost) {
      sProxyHost = proxyHost;
   }

   private static int getProxyPort() {
      return sProxyPort;
   }

   private static void setProxyPort(int proxyPort) {
      sProxyPort = proxyPort;
   }

   public Cookie getSessionId() {
      return this.mSessionId;
   }

   public void setSessionId(Cookie sessionId) {
      this.mSessionId = sessionId;
   }

}
