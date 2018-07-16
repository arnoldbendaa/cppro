// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.user;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionCSO;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface DataEntryProfileEditorSessionLocal extends EJBLocalObject {

   DataEntryProfileEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   DataEntryProfileEditorSessionSSO getNewItemData(int var1) throws EJBException;

   DataEntryProfileCK insert(DataEntryProfileEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   DataEntryProfileCK copy(DataEntryProfileEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(DataEntryProfileEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
