// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.login;

import com.cedar.cp.api.logon.AuthenticationResult;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ChangePasswordForm extends CPForm {

   private String mNewPassword;
   private String mConfirmPassword;
   private CPContext mCPContext;


   public String getNewPassword() {
      return this.mNewPassword;
   }

   public void setNewPassword(String pNewPassword) {
      this.mNewPassword = pNewPassword;
   }

   public String getConfirmPassword() {
      return this.mConfirmPassword;
   }

   public void setConfirmPassword(String pConfirmPassword) {
      this.mConfirmPassword = pConfirmPassword;
   }

   public ActionErrors validate(ActionMapping pActionMapping, HttpServletRequest pHttpServletRequest) {
      ActionErrors errors = new ActionErrors();
      this.mCPContext = this.getCPContext(pHttpServletRequest);

      ActionMessage error;
      try {
         ActionMessage e;
         if(!this.mNewPassword.equals(this.mConfirmPassword)) {
            e = new ActionMessage("cp.myAccount.error.passwordMismatch");
            errors.add("password", e);
         }

         if(errors.isEmpty()) {
            if(this.mNewPassword.trim().length() > 0) {
               AuthenticationResult e1 = this.mCPContext.getCPConnection().changePassword(this.mCPContext.getUserId(), this.mNewPassword);
               if(e1.getResult() == 10) {
                  error = new ActionMessage("cp.changePassword.error.shortPassword");
                  errors.add("password", error);
               } else if(e1.getResult() == 13) {
                  error = new ActionMessage("cp.changePassword.error.samePassword");
                  errors.add("password", error);
               } else if(e1.getResult() == 12) {
                  error = new ActionMessage("cp.changePassword.error", e1.getValidationErrorMsg());
                  errors.add("password", error);
               }
            } else {
               e = new ActionMessage("cp.changePassword.error.emptyPassword");
               errors.add("password", e);
            }
         }
      } catch (Exception var6) {
         error = new ActionMessage("cp.myAccount.error.server", var6.getMessage());
         errors.add("password", error);
      }

      return errors;
   }
}
