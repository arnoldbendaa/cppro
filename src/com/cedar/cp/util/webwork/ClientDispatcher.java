// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.webwork;

import com.cedar.cp.util.Log;
import java.applet.Applet;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import webwork.action.Action;
import webwork.action.client.ActionResult;

public class ClientDispatcher {

   String host;
   public static final String DISPATCHER = "/servlet/dispatch";
   private transient Log mLog;


   public ClientDispatcher(Applet anApplet, String context) throws MalformedURLException {
      this((new URL(anApplet.getDocumentBase(), context + "/servlet/dispatch")).toExternalForm());
   }

   public ClientDispatcher(String aHost) {
      this.mLog = new Log(this.getClass());
      this.host = aHost;
   }

   public String getHost() {
      return this.host;
   }

   public ActionResult execute(Action anAction) throws Exception {
      URL webworkHostServlet = new URL(this.host);
      HttpURLConnection webworkHostServletConnection = (HttpURLConnection)webworkHostServlet.openConnection();
      webworkHostServletConnection.setDoInput(true);
      webworkHostServletConnection.setDoOutput(true);
      webworkHostServletConnection.setUseCaches(false);
      webworkHostServletConnection.setRequestProperty("Content-Type", "java-internal/" + anAction.getClass().getName());
      ObjectOutputStream out = this.createObjectOutputStream(new BufferedOutputStream(webworkHostServletConnection.getOutputStream()));
      out.writeObject(anAction);
      out.flush();
      ObjectInputStream in = this.createObjectInputStream(new BufferedInputStream(webworkHostServletConnection.getInputStream()));
      ActionResult actionResult = (ActionResult)in.readObject();
      out.close();
      in.close();
      if(actionResult.getException() != null) {
         this.mLog.warn("execute", actionResult.getException().getMessage());
         throw actionResult.getException();
      } else {
         return actionResult;
      }
   }

   protected ObjectInputStream createObjectInputStream(InputStream in) throws IOException {
      return new ObjectInputStream(in);
   }

   protected ObjectOutputStream createObjectOutputStream(OutputStream out) throws IOException {
      return new ObjectOutputStream(out);
   }
}
