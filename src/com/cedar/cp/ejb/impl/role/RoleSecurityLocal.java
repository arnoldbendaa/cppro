// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.ejb.impl.role.RoleSecurityEVO;
import javax.ejb.EJBLocalObject;

public interface RoleSecurityLocal extends EJBLocalObject {

   RoleSecurityEVO getDetails(String var1) throws ValidationException;

   RoleSecurityPK generateKeys();

   void setDetails(RoleSecurityEVO var1);

   RoleSecurityEVO setAndGetDetails(RoleSecurityEVO var1, String var2);
}
