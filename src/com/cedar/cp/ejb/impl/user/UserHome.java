// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.user.UserRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface UserHome extends EJBHome {

   UserRemote create(UserEVO var1) throws EJBException, CreateException, RemoteException;

   UserRemote findByPrimaryKey(UserPK var1) throws EJBException, FinderException, RemoteException;
}
