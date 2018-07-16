// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.utc.common.ResourceServlet$ExtendedAttachmentHelper;
import com.cedar.cp.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceServlet extends HttpServlet {

   protected Log mLog = new Log(this.getClass());


   protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
      String pId = httpServletRequest.getParameter("id");
      this.mLog.debug("id = " + pId);
      if(pId == null) {
         this.mLog.warn("doGet", "no id passed");
         httpServletResponse.sendError(400, "no id passed");
      } else {
         int id;
         try {
            id = Integer.valueOf(pId).intValue();
         } catch (NumberFormatException var13) {
            this.mLog.warn("doGet", "invalid id passed: " + pId);
            httpServletResponse.sendError(400, "invalid id passed: " + pId);
            return;
         }

         CPFileWrapper wrapper = null;
         ResourceServlet$ExtendedAttachmentHelper helper = null;

         label91: {
            try {
               helper = new ResourceServlet$ExtendedAttachmentHelper(this);
               wrapper = helper.loadResource(id);
               if(wrapper != null) {
                  break label91;
               }

               this.mLog.warn("doGet", "no resource found for id: " + id);
               httpServletResponse.sendError(404, "no resource found for id: " + id);
            } finally {
               if(helper != null) {
                  helper.close();
               }

            }

            return;
         }

         httpServletResponse.reset();
         StringBuilder sb = new StringBuilder();
         sb.append("inline;").append(" filename=").append(wrapper.getEscapedName());
         httpServletResponse.setContentType(wrapper.getMimeType(httpServletRequest));
         httpServletResponse.setHeader("Content-Disposition", sb.toString());
         ByteArrayInputStream in = new ByteArrayInputStream(wrapper.getData());
         ServletOutputStream out = httpServletResponse.getOutputStream();

         int b;
         while((b = in.read()) != -1) {
            out.write(b);
         }

         out.flush();
         out.close();
         in.close();
      }
   }
}
