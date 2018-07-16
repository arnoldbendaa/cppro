// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.logonhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionCSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionSSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface LogonHistoryEditorSessionLocal extends EJBLocalObject {

   LogonHistoryEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   LogonHistoryEditorSessionSSO getNewItemData(int var1) throws EJBException;

   LogonHistoryPK insert(LogonHistoryEditorSessionCSO var1) throws ValidationException, EJBException;

   LogonHistoryPK copy(LogonHistoryEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(LogonHistoryEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
