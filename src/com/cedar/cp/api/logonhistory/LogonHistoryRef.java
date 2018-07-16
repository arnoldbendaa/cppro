// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.logonhistory;

import com.cedar.cp.api.base.EntityRef;

public interface LogonHistoryRef extends EntityRef {

   int TIMED_OUT = -2;
   int LOGOFF = -1;
   int SUCCESSFUL = 1;
   int FAILED = 2;

}
