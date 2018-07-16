// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.common.CPAction;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageView extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      String attach = httpServletRequest.getParameter("attach_file");
      String mimeType = "";
      boolean error = true;
      this.getLogger().debug("execute", "attach_file param : " + attach);
      File f = null;
      if(attach != null) {
         f = new File(attach);
         if(f != null && f.exists()) {
            error = false;
         }
      }

      mimeType = httpServletRequest.getSession().getServletContext().getMimeType(attach);
      if(mimeType == null || mimeType.equals("")) {
         mimeType = "text/html";
      }

      this.getLogger().debug("execute", "We have the file for download : " + error);
      this.getLogger().debug("execute", "MimeType  : " + mimeType);
      httpServletResponse.reset();
      this.setHeaderInfo(httpServletResponse);
      httpServletResponse.setContentType(mimeType);
      if(error) {
         return actionMapping.findForward("fail");
      } else {
         FileInputStream in = new FileInputStream(f);
         ServletOutputStream out = httpServletResponse.getOutputStream();

         int b;
         while((b = in.read()) != -1) {
            out.write(b);
         }

         out.flush();
         out.close();
         in.close();
         return null;
      }
   }
}
