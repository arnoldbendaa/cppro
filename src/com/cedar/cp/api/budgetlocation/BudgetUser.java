// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.budgetlocation;

import com.cedar.cp.api.base.EntityRef;
import java.io.Serializable;

public interface BudgetUser extends Serializable {

   int getStructureElementId();

   EntityRef getUserRef();

   boolean isReadOnly();

   boolean isInsertPending();

   boolean isDeletePending();

   boolean isModified();
}
