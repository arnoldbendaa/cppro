// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.systemproperty;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionCSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionSSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface SystemPropertyEditorSessionLocal extends EJBLocalObject {

   SystemPropertyEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   SystemPropertyEditorSessionSSO getNewItemData(int var1) throws EJBException;

   SystemPropertyPK insert(SystemPropertyEditorSessionCSO var1) throws ValidationException, EJBException;

   SystemPropertyPK copy(SystemPropertyEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(SystemPropertyEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
