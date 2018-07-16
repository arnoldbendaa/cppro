// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.utc.struts.approver.ChangeBudgetCycleForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class StatusConfirmForm extends ChangeBudgetCycleForm {

   private String mStatusMessageAction;
   private String mMessage;


   public String getStatusMessageAction() {
      return this.mStatusMessageAction;
   }

   public void setStatusMessageAction(String statusMessageAction) {
      this.mStatusMessageAction = statusMessageAction;
   }

   public String getMessage() {
      return this.mMessage;
   }

   public void setMessage(String message) {
      this.mMessage = message;
   }

   public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      ActionErrors errors = new ActionErrors();
      String s = this.getStatusMessageAction();
      if(s == null) {
         s = "";
      }

      if(s.equals("Ok")) {
         this.checkForEmpty("message", "message", this.mMessage, errors);
      }

      return errors;
   }
}
