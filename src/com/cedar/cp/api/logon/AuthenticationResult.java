// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.logon;

import java.util.Map;
import java.util.Set;

public interface AuthenticationResult {

   int VALID_CAN_CHANGE_PWD = 1;
   int VALID_PWD_EXPIRES = 2;
   int VALID_CANT_CHANGE_PWD = 3;
   int VALID_FORCE_CHANGE_PASSWORD = 4;
   int INVALID_USER_NAME = 5;
   int INVALID_PASSWORD = 6;
   int USER_DISABLED = 7;
   int PASSWORD_EXPIRED = 8;
   int CHANGE_PASSWORD_WRONG_OLD = 9;
   int CHANGE_PASSWORD_INVALID_NEW = 10;
   int CHANGE_PASSWORD_COMPLETE = 11;
   int CHANGE_PASSWORD_FAILED = 12;
   int CHANGE_PASSWORD_SAME_OLD = 13;
   String VALID_PWD_EXPIRES_MESSAGE = "Your password expires in ";
   String VALID_FORCE_CHANGE_PASSWORD_MESSAGE = "You must change your password before logging off";
   String INVALID_USER_NAME_MESSAGE = "The User ID is invalid.";
   String INVALID_PASSWORD_MESSAGE = "The password for the User ID is invalid.";
   String USER_DISABLED_MESSAGE = "This User has been disabled, contact administrator.";
   String PASSWORD_EXPIRED_MESSAGE = "Your password has expired, contact administrator.";


   int getResult();

   long getExpiryDays();

   Object getPrimaryKey();

   String getUserName();

   String getFullUserName();

   String getLogonID();

   Set getUserSecurityStrings();

   Map<String, String> getRoleDescriptions();

   Map<String, Set<String>> getUserRoleSecurityStrings();

   String getSystemName();

   int getMinPasswordSize();

   String getPassword();

   String getValidationErrorMsg();

   void setResult(int var1);
   
   void setCluster(boolean cluster);
   
   boolean isCluster();
   
   boolean isNewFeaturesEnabled();

   boolean areButtonsVisible();
   
   boolean getRoadMapAvailable();
   
   boolean isShowBudgetActivity();
   
   boolean isNewView();
}
