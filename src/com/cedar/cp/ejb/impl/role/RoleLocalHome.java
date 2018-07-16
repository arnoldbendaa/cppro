// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.ejb.impl.role.RoleEVO;
import com.cedar.cp.ejb.impl.role.RoleLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface RoleLocalHome extends EJBLocalHome {

   RoleLocal create(RoleEVO var1) throws EJBException, CreateException;

   RoleLocal findByPrimaryKey(RolePK var1) throws FinderException;
}
