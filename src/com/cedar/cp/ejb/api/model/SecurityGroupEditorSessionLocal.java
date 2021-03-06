// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.SecurityGroupCK;
import com.cedar.cp.dto.model.SecurityGroupEditorSessionCSO;
import com.cedar.cp.dto.model.SecurityGroupEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface SecurityGroupEditorSessionLocal extends EJBLocalObject {

   SecurityGroupEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   SecurityGroupEditorSessionSSO getNewItemData(int var1) throws EJBException;

   SecurityGroupCK insert(SecurityGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   SecurityGroupCK copy(SecurityGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(SecurityGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
