// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.SecurityAccessDefCK;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionCSO;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface SecurityAccessDefEditorSessionLocal extends EJBLocalObject {

   SecurityAccessDefEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   SecurityAccessDefEditorSessionSSO getNewItemData(int var1) throws EJBException;

   SecurityAccessDefCK insert(SecurityAccessDefEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   SecurityAccessDefCK copy(SecurityAccessDefEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(SecurityAccessDefEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
