// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.EntityRef;

public interface BudgetStateRef extends EntityRef {

   int STATE_INITIATED = 1;
   int STATE_PREPARING = 2;
   int STATE_SUBMITTED = 3;
   int STATE_AGREED = 4;
   int STATE_DISABLED = 5;
   int STATE_NOT_PLANNABLE = 6;

}
