// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.user.UserLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface UserLocalHome extends EJBLocalHome {

   UserLocal create(UserEVO var1) throws EJBException, CreateException;

   UserLocal findByPrimaryKey(UserPK var1) throws FinderException;
}
