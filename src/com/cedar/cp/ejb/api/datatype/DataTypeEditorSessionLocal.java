// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.datatype;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionCSO;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionSSO;
import com.cedar.cp.dto.datatype.DataTypePK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface DataTypeEditorSessionLocal extends EJBLocalObject {

   DataTypeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   DataTypeEditorSessionSSO getNewItemData(int var1) throws EJBException;

   DataTypePK insert(DataTypeEditorSessionCSO var1) throws ValidationException, EJBException;

   DataTypePK copy(DataTypeEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(DataTypeEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int issueCreateAllExternalViews(int var1) throws EJBException;
}
