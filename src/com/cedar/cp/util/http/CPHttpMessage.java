// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.http;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class CPHttpMessage {

   private HttpClient mClient;


   public void doPost(String address, Map items) throws Exception {
      HttpClient client = this.getClient();
      PostMethod method = new PostMethod(address);
      method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler(3, false));
      Iterator e = items.entrySet().iterator();

      while(e.hasNext()) {
         Object o = e.next();
         Entry currElement = (Entry)o;
         method.addParameter((String)currElement.getKey(), (String)currElement.getValue());
      }

      try {
         client.executeMethod(method);
      } catch (Exception var11) {
         throw var11;
      } finally {
         method.releaseConnection();
      }

   }

   private HttpClient getClient() {
      if(this.mClient == null) {
         this.mClient = new HttpClient();
      }

      return this.mClient;
   }
}
