// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.passwordhistory;

import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryEVO;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface PasswordHistoryLocalHome extends EJBLocalHome {

   PasswordHistoryLocal create(PasswordHistoryEVO var1) throws EJBException, CreateException;

   PasswordHistoryLocal findByPrimaryKey(PasswordHistoryPK var1) throws FinderException;
}
