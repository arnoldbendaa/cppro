// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.role;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.role.RoleEditorSessionCSO;
import com.cedar.cp.dto.role.RoleEditorSessionSSO;
import com.cedar.cp.dto.role.RolePK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface RoleEditorSessionLocal extends EJBLocalObject {

   RoleEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   RoleEditorSessionSSO getNewItemData(int var1) throws EJBException;

   RolePK insert(RoleEditorSessionCSO var1) throws ValidationException, EJBException;

   RolePK copy(RoleEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(RoleEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
