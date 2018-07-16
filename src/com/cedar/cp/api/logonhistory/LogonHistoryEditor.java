// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.logonhistory;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logonhistory.LogonHistory;
import java.sql.Timestamp;

public interface LogonHistoryEditor extends BusinessEditor {

   void setEventType(int var1) throws ValidationException;

   void setUserName(String var1) throws ValidationException;

   void setEventDate(Timestamp var1) throws ValidationException;

   LogonHistory getLogonHistory();
}
