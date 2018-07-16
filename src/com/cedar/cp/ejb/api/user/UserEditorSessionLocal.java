// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.user;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.UserEditorSessionCSO;
import com.cedar.cp.dto.user.UserEditorSessionSSO;
import com.cedar.cp.dto.user.UserPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface UserEditorSessionLocal extends EJBLocalObject {

   UserEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   UserEditorSessionSSO getNewItemData(int var1) throws EJBException;

   UserPK insert(UserEditorSessionCSO var1) throws ValidationException, EJBException;

   UserPK copy(UserEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(UserEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
