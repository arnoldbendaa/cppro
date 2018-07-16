// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.logonhistory;

import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryEVO;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface LogonHistoryLocalHome extends EJBLocalHome {

   LogonHistoryLocal create(LogonHistoryEVO var1) throws EJBException, CreateException;

   LogonHistoryLocal findByPrimaryKey(LogonHistoryPK var1) throws FinderException;
}
