// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.financesystem;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface FinanceSystemCheckDetail extends Serializable {

   boolean isValid();

   Set getValidSet();

   List getList();

   String getErrorType();

   List getErrorMessages();

   boolean isOverRide();
}
