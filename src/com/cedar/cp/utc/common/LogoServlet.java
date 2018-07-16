// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.common.LogoServlet$DBHelper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoServlet extends HttpServlet {

   private byte[] mLogo;


   protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
      LogoServlet$DBHelper helper = null;
      ServletOutputStream out = httpServletResponse.getOutputStream();

      try {
         CPSystemProperties t = (CPSystemProperties)this.getServletContext().getAttribute("cpSystemProperties");
         int id = t.getSystemLogoId();
         Object stream;
         if(id == 0) {
            String buffer = "/images/acs-logo.png";
            URL length = new URL("http", httpServletRequest.getServerName(), httpServletRequest.getServerPort(), buffer);
            httpServletResponse.setContentType("image/png");
            stream = length.openStream();
         } else {
            httpServletResponse.setContentType("image/gif");
            helper = new LogoServlet$DBHelper(this);
            helper.loadLogo(id);
            stream = new ByteArrayInputStream(this.mLogo);
         }

         byte[] buffer1 = new byte[1024];

         int length1;
         while((length1 = ((InputStream)stream).read(buffer1)) >= 0) {
            if(length1 > 0) {
               out.write(buffer1, 0, length1);
            }
         }
      } catch (Exception var14) {
         System.err.println(var14.toString());
      } catch (Throwable var15) {
         System.err.println(var15.toString());
      } finally {
         out.close();
         if(helper != null) {
            helper.close();
         }

      }

   }

   // $FF: synthetic method
   static byte[] access$002(LogoServlet x0, byte[] x1) {
      return x0.mLogo = x1;
   }
}
