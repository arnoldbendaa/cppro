// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.role.RoleCK;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.ejb.impl.role.RoleEVO;
import javax.ejb.EJBLocalObject;

public interface RoleLocal extends EJBLocalObject {

   RoleEVO getDetails(String var1) throws ValidationException;

   RoleEVO getDetails(RoleCK var1, String var2) throws ValidationException;

   RolePK generateKeys();

   void setDetails(RoleEVO var1);

   RoleEVO setAndGetDetails(RoleEVO var1, String var2);
}
