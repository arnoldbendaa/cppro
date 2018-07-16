// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPRoleSecurityException;
import com.cedar.cp.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class CPExceptionHandler extends ExceptionHandler {

   private static Log sLog = new Log(CPExceptionHandler.class);


   public ActionForward execute(Exception ex, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
      long ref = System.currentTimeMillis();
      String refString = "\'" + ref + "\'";
      sLog.error("execute", "We have just had an exception, internal ref=" + refString, ex);
      String fwd = "system.error";
      if(ex instanceof CPRoleSecurityException) {
         fwd = "system.nosecurity";
      }

      ActionForward nextPage = mapping.findForward(fwd);
      String[] args = new String[]{refString, mapping != null?mapping.getName():"n/a", form != null?form.getClass().getName():"n/a"};
      ActionMessage error = new ActionMessage(config.getKey(), args);
      this.storeException(request, error.getKey(), error, nextPage, config.getScope());
      return nextPage;
   }

}
