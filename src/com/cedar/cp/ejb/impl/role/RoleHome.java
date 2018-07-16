// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.ejb.impl.role.RoleEVO;
import com.cedar.cp.ejb.impl.role.RoleRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface RoleHome extends EJBHome {

   RoleRemote create(RoleEVO var1) throws EJBException, CreateException, RemoteException;

   RoleRemote findByPrimaryKey(RolePK var1) throws EJBException, FinderException, RemoteException;
}
