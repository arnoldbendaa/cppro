// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.facades;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.facades.CPFunctionsBaseEvaluator;

public class CPFunctionsEvaluatorImpl extends CPFunctionsBaseEvaluator {


   public CPFunctionsEvaluatorImpl(CPFunctionDaoImpl dao){
       super(dao);
   }
   public CPFunctionsEvaluatorImpl(String userId, String password, String uri) throws UserDisabledException, InvalidCredentialsException, UserLicenseException, ValidationException {
      this(DriverManager.getConnection(uri, userId, password, CPConnection.ConnectionContext.INTERACTIVE_WEB));
   }

   public CPFunctionsEvaluatorImpl(CPConnection conn) {
       this(new CPFunctionDaoImpl(conn));
   }

}
