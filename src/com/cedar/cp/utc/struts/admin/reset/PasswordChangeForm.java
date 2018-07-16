package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.base.CPPrincipal;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.logon.AuthenticationResult;
import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.common.CPSystemProperties;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class PasswordChangeForm extends CPForm
{
  private String newpassword;
  private String confirmpassword;
  private String userID;

  public String getUserID()
  {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getNewpassword() {
    return newpassword;
  }

  public void setNewpassword(String newpassword) {
    this.newpassword = newpassword;
  }

  public String getConfirmpassword() {
    return confirmpassword;
  }

  public void setConfirmpassword(String confirmpassword) {
    this.confirmpassword = confirmpassword;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
  {
    ActionErrors errors = new ActionErrors();
    CPSystemProperties sysProps = getCPSystemProperties(request);
    try
    {
      CPConnection conn = DriverManager.getConnection(sysProps.getConnectionURL(), new CPPrincipal(getUserID(), ""), true, CPConnection.ConnectionContext.SERVER_SESSION);
      if (!newpassword.equals(confirmpassword))
      {
        ActionMessage error = new ActionMessage("cp.myAccount.error.passwordMismatch");
        errors.add("password", error);
      }

      if (errors.isEmpty())
      {
        if (newpassword.trim().length() > 0)
        {
          AuthenticationResult result = conn.changePassword(getUserID(), newpassword);

          if (result.getResult() == 10)
          {
            ActionMessage error = new ActionMessage("cp.changePassword.error.shortPassword");
            errors.add("password", error);
          }
          else if (result.getResult() == 13)
          {
            ActionMessage error = new ActionMessage("cp.changePassword.error.samePassword");
            errors.add("password", error);
          }
          else if (result.getResult() == 12)
          {
            ActionMessage error = new ActionMessage("cp.changePassword.error", result.getValidationErrorMsg());
            errors.add("password", error);
          }
        }
        else
        {
          ActionMessage error = new ActionMessage("cp.changePassword.error.emptyPassword");
          errors.add("password", error);
        }
      }
    }
    catch (Exception e)
    {
      ActionMessage error = new ActionMessage("cp.myAccount.error.server", e.getMessage());
      errors.add("password", error);
    }

    return errors;
  }
}