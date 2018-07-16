// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.ejb.impl.role.RoleSecurityEVO;
import com.cedar.cp.ejb.impl.role.RoleSecurityRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface RoleSecurityHome extends EJBHome {

   RoleSecurityRemote create(RoleSecurityEVO var1) throws EJBException, CreateException, RemoteException;

   RoleSecurityRemote findByPrimaryKey(RoleSecurityPK var1) throws EJBException, FinderException, RemoteException;
}
