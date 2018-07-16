// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.budgetinstruction;

import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionEVO;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface BudgetInstructionHome extends EJBHome {

   BudgetInstructionRemote create(BudgetInstructionEVO var1) throws EJBException, CreateException, RemoteException;

   BudgetInstructionRemote findByPrimaryKey(BudgetInstructionPK var1) throws EJBException, FinderException, RemoteException;
}
