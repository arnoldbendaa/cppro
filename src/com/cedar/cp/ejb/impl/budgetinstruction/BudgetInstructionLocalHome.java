// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.budgetinstruction;

import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionEVO;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface BudgetInstructionLocalHome extends EJBLocalHome {

   BudgetInstructionLocal create(BudgetInstructionEVO var1) throws EJBException, CreateException;

   BudgetInstructionLocal findByPrimaryKey(BudgetInstructionPK var1) throws FinderException;
}
