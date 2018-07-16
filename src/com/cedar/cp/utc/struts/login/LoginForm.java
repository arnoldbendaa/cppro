// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.login;

import com.cedar.cp.api.base.AdminOnlyException;
import com.cedar.cp.api.base.CPPrincipal;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.utc.common.CPAuthenticationPolicy;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.common.SingleEntryPointMismatchException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class LoginForm extends CPForm {

   public static final String ATTEMPT_ATTRIBUTE_NAME = "cpLogonCount";
   private String mUserId = "";
   private String mPassword = "";
   private CPContext mCPContext;


   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	   	  ActionErrors errors = new ActionErrors();
	      HttpSession session = request.getSession();
	      int logonCount = 0;
	      Integer lCount = (Integer)session.getAttribute("cpLogonCount");
	      if(lCount != null) {
	         logonCount = lCount.intValue() + 1;
	      }

	      session.setAttribute("cpLogonCount", new Integer(logonCount));
	      CPSystemProperties sysProps = this.getCPSystemProperties(request);
	      CPAuthenticationPolicy authPolicy = sysProps.getActiveAuthenticationPolicy();
	      if(logonCount >= authPolicy.getMaximumLogonAttempts()) {
	         return errors;
	      } else {
	         ActionMessage error;
	         try {
	            this.checkForEmpty("userId", "userId", this.mUserId, errors);
	            this.checkForEmpty("password", "password", this.mPassword, errors);
	            if(errors.isEmpty()) {
	               this.mCPContext = CPContext.logon(sysProps, request, this.getUserId(), this.getPassword());
	               this.getLogger().info("validate", "Connected user " + this.getUserId());
	            }
	         } catch (SingleEntryPointMismatchException var11) {
	            error = new ActionMessage("cp.login.error.singleEntryPoint");
	            errors.add("invalid.login", error);
	         } catch (InvalidCredentialsException var12) {
	            error = null;
	            if(var12.getType() == 1) {
	               error = new ActionMessage("cp.login.error.invalidpassword");
	            } else {
	               error = new ActionMessage("cp.login.error.invalidlogin");
	            }

	            errors.add("invalid.login", error);
	         } catch (UserLicenseException var13) {
	            error = new ActionMessage("cp.login.error.licence");
	            errors.add("invalid.licence", error);
	         } catch (UserDisabledException var14) {
	            error = new ActionMessage("cp.login.error.disabled");
	            errors.add("invalid.disabled", error);
	         } catch (AdminOnlyException e) {
	            error = new ActionMessage("cp.login.error.adminOnly");
	            errors.add("invalid.adminOny", error);
	         } catch (IllegalArgumentException var15) {
	            error = new ActionMessage("cp.login.error.invalidlogin");
	            errors.add("invalid.login", error);
	         }

	         return errors;
	      }
   }

   public void reset(ActionMapping mapping, HttpServletRequest request) {
      this.setUserId("");
      this.setPassword("");
      this.mCPContext = null;
   }

   public String getPassword() {
      return this.mPassword;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public void setPassword(String password) {
      this.mPassword = password;
   }

   public void setUserId(String userId) {
      this.mUserId = userId;
   }

   public CPContext getCPContext() {
      return this.mCPContext;
   }
}
