package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.utc.common.CPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PasswordResetSubmit extends CPAction
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    return mapping.findForward("success");
  }
}