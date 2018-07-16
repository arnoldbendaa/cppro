// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEVO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ChangeMgmtLocalHome extends EJBLocalHome {

   ChangeMgmtLocal create(ChangeMgmtEVO var1) throws EJBException, CreateException;

   ChangeMgmtLocal findByPrimaryKey(ChangeMgmtPK var1) throws FinderException;
}
