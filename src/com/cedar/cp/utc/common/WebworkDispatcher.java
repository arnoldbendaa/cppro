// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.WebworkDispatcher$1;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webwork.action.Action;
import webwork.action.ServletActionContext;
import webwork.action.client.ActionResult;
import webwork.action.factory.ContextActionFactoryProxy;

public class WebworkDispatcher extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
   }

   public void service(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException {
      try {
//         ObjectInputStream e = this.createObjectInputStream(aRequest.getInputStream());
//         Action action = (Action)e.readObject();
//         ServletActionContext.setContext(aRequest, aResponse, this.getServletContext(), (String)null);
//         (new ContextActionFactoryProxy(new WebworkDispatcher$1(this, action))).getActionImpl((String)null);
//
//         ActionResult actionResult;
//         try {
//            String out = action.execute();
//            actionResult = new ActionResult(action, out);
//         } catch (Exception var7) {
//            actionResult = new ActionResult(action, (String)null);
//            actionResult.setException(var7);
//         }
//
//         ObjectOutputStream out1 = this.createObjectOutputStream(aResponse.getOutputStream());
//         out1.writeObject(actionResult);
//         out1.flush();
      } catch (Exception var8) {
         var8.printStackTrace();
         throw new ServletException(var8);
      }
   }

   protected ObjectInputStream createObjectInputStream(InputStream in) throws IOException {
      return new ObjectInputStream(in);
   }

   protected ObjectOutputStream createObjectOutputStream(OutputStream out) throws IOException {
      return new ObjectOutputStream(out);
   }
}
