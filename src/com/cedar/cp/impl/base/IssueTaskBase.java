// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.ParamHelper;
import com.cedar.cp.util.ParamHelperAppl;

public abstract class IssueTaskBase {

   protected String mServer;
   protected int mPort;
   protected String mUserName;
   protected String mPassword;
   protected CPConnection mConnection;
   public transient Log mLog = new Log(this.getClass());


   public int processArgs(String[] args) {
      byte exitCode = -1;

      try {
         this.checkParams(new ParamHelperAppl(args));
         this.go();
         exitCode = 0;
      } catch (IllegalArgumentException var4) {
         var4.printStackTrace();
      } catch (InvalidCredentialsException var5) {
         this.mLog.error("processArgs", "Logon details invalid");
      } catch (UserDisabledException var6) {
         this.mLog.error("processArgs", "User has been disabled");
      } catch (UserLicenseException var7) {
         this.mLog.error("processArgs", "License problem");
      } catch (ValidationException var8) {
         this.mLog.error("processArgs", var8.getMessage());
      }

      return exitCode;
   }

   protected abstract String[][] getParameterInfo();

   protected abstract void checkSpecificParams(ParamHelper var1);

   protected abstract void issueRequest();

   private void checkParams(ParamHelper ph) {
      ph.setParamSpec(this.getParameterInfo());
      this.mServer = ph.getString("-s");
      this.mUserName = ph.getString("-u");
      this.mPassword = ph.getString("-pass");
      this.checkSpecificParams(ph);
   }

   private void go() throws InvalidCredentialsException, UserDisabledException, UserLicenseException, ValidationException {
      this.mConnection = DriverManager.getConnection(this.mServer, this.mUserName, this.mPassword, ConnectionContext.SERVER_TASK);
      this.issueRequest();
   }
}
