// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class CPBaseAJAXAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      Object o = null;

      try {
         CPContext out = this.getCPContext(httpServletRequest);
         CPConnection bout = out.getCPConnection();
         o = this.processRequest(actionForm, httpServletRequest, out, bout);
      } catch (Exception var8) {
         this.processError(httpServletResponse, var8.getMessage());
      }

      httpServletResponse.setContentType(this.getMimeType());
      ServletOutputStream out1 = httpServletResponse.getOutputStream();
      byte[] bout1 = null;
      if(o != null) {
         bout1 = o.toString().getBytes("UTF-8");
      }

      if(bout1 != null) {
         out1.write(bout1);
      }

      out1.flush();
      out1.close();
      return null;
   }

   public abstract Object processRequest(ActionForm var1, HttpServletRequest var2, CPContext var3, CPConnection var4) throws Exception;

   protected void processError(HttpServletResponse resp, String message) throws IOException, JSONException {
      JSONObject messageObj = new JSONObject();
      messageObj.put("message", message);
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("error", messageObj);
      resp.setContentType(this.getMimeType());
      ServletOutputStream out = resp.getOutputStream();
      byte[] bout = jsonObj.toString().getBytes("UTF-8");
      out.write(bout);
      out.flush();
      out.close();
   }

   protected String getMimeType() {
      return "text/plain;charset=utf-8";
   }
}
