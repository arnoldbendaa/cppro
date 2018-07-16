// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.user;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.UserEditor;
import com.cedar.cp.api.user.UserEditorSession;
import com.cedar.cp.api.user.UsersProcess;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPForm;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class MyAccountForm extends CPForm {

   private String mName = "";
   private String mEmail = "";
   private String mNewPassword = "";
   private String mConfirmPassword = "";
   private String mFinanceUser = "";


   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
      ActionErrors errors = new ActionErrors();
      CPContext context = this.getCPContext(request);
      CPConnection cnx = context.getCPConnection();
      this.checkForEmpty("Name", "name", this.mName, errors);
      if(!this.mNewPassword.equals(this.mConfirmPassword)) {
         ActionMessage editorSession = new ActionMessage("cp.myAccount.error.passwordMismatch");
         errors.add("password", editorSession);
      }

      UserEditorSession editorSession1 = null;
      UsersProcess process = null;

      try {
         if(errors.isEmpty()) {
            process = cnx.getUsersProcess();
            Object e = context.getUserContext().getPrimaryKey();
            editorSession1 = process.getUserEditorSession(e);
            UserEditor error1 = editorSession1.getUserEditor();
            error1.setFullName(this.mName);
            error1.setEMailAddress(this.mEmail);
            if(this.mNewPassword.trim().length() > 0) {
               error1.setPasswordChanged(true);
               error1.setPassword(this.mNewPassword);
            }

            error1.setExternalSystemUserName(this.mFinanceUser);
            if(error1.isContentModified()) {
               error1.commit();
               editorSession1.commit(false);
               if(this.mNewPassword.trim().length() > 0) {
                  context.setPassword(this.mNewPassword);
               }

               this.setUserPreferences(context);
            }
         }
      } catch (ValidationException var13) {
         ActionMessage error = new ActionMessage("cp.myAccount.error.server", var13.getMessage());
         errors.add("password", error);
      } finally {
         if(editorSession1 != null) {
            process.terminateSession(editorSession1);
         }

      }

      return errors;
   }

   private void setUserPreferences(CPContext context) {
      UsersProcess process = context.getCPConnection().getUsersProcess();
      EntityList preferences = process.getUserPreferencesForUser(context.getUserContext().getUserId());
      HashMap userPreferences = new HashMap();

      for(int i = 0; i < preferences.getNumRows(); ++i) {
         Object key = preferences.getValueAt(i, "PrefName");
         Object value = preferences.getValueAt(i, "PrefValue");
         userPreferences.put(key, value);
      }

      this.getLogger().debug("userPreferences = " + userPreferences);
      context.getUserContext().setUserPreferenceValues(userPreferences);
   }

   public void reset(ActionMapping mapping, HttpServletRequest request) {
      this.setName("");
      this.setEmail("");
      this.setNewPassword("");
      this.setConfirmPassword("");
      this.setFinanceUser("");
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getEmail() {
      return this.mEmail;
   }

   public void setEmail(String email) {
      this.mEmail = email;
   }

   public String getNewPassword() {
      return this.mNewPassword;
   }

   public void setNewPassword(String newPassword) {
      this.mNewPassword = newPassword;
   }

   public String getConfirmPassword() {
      return this.mConfirmPassword;
   }

   public void setConfirmPassword(String confirmPassword) {
      this.mConfirmPassword = confirmPassword;
   }

   public String getFinanceUser() {
      return this.mFinanceUser;
   }

   public void setFinanceUser(String mFinanceUser) {
      this.mFinanceUser = mFinanceUser;
   }
}
