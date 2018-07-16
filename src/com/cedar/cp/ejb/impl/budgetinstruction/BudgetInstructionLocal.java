// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.budgetinstruction;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionEVO;
import javax.ejb.EJBLocalObject;

public interface BudgetInstructionLocal extends EJBLocalObject {

   BudgetInstructionEVO getDetails(String var1) throws ValidationException;

   BudgetInstructionEVO getDetails(BudgetInstructionCK var1, String var2) throws ValidationException;

   BudgetInstructionPK generateKeys();

   void setDetails(BudgetInstructionEVO var1);

   BudgetInstructionEVO setAndGetDetails(BudgetInstructionEVO var1, String var2);
}
