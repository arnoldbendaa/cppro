// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionCSO;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface SecurityRangeEditorSessionLocal extends EJBLocalObject {

   SecurityRangeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   SecurityRangeEditorSessionSSO getNewItemData(int var1) throws EJBException;

   SecurityRangeCK insert(SecurityRangeEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   SecurityRangeCK copy(SecurityRangeEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(SecurityRangeEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
