// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.passwordhistory;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.passwordhistory.PasswordHistory;
import java.sql.Timestamp;

public interface PasswordHistoryEditor extends BusinessEditor {

   void setUserId(int var1) throws ValidationException;

   void setPasswordBytes(String var1) throws ValidationException;

   void setPasswordDate(Timestamp var1) throws ValidationException;

   PasswordHistory getPasswordHistory();
}
