// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import java.util.List;

public interface ChangeBudgetStateResult {

   boolean isValid();

   String getMessage();

   Exception getException();

   List getBudgetLimitViolations();

   boolean isForceMessage();
}
