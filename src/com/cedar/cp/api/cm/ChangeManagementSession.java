// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;

public interface ChangeManagementSession {

   void registerUpdateRequest(String var1) throws ValidationException;

   int issueUpdateTask(ModelRef var1) throws ValidationException;
}
