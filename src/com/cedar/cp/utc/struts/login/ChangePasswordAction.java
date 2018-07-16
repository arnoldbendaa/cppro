// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.login;

import com.cedar.cp.api.logon.AuthenticationResult;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPContextCache;
import com.cedar.cp.utc.struts.login.ChangePasswordForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChangePasswordAction extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      ChangePasswordForm cpForm = (ChangePasswordForm)form;
      CPContext context = this.getCPContext(request);
      AuthenticationResult result = context.getUserContext().getAuthenticationResult();
      result.setResult(1);
      context.setPassword(cpForm.getNewPassword());
      CPContextCache.getCPContextId(context);
      return mapping.findForward("success");
   }
}
