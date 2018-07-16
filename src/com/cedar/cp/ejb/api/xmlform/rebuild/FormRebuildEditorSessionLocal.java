// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlform.rebuild;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildEditorSessionCSO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildEditorSessionSSO;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface FormRebuildEditorSessionLocal extends EJBLocalObject {

   FormRebuildEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   FormRebuildEditorSessionSSO getNewItemData(int var1) throws EJBException;

   FormRebuildCK insert(FormRebuildEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   FormRebuildCK copy(FormRebuildEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(FormRebuildEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   List submit(EntityRef var1, int var2, int var3) throws ValidationException, EJBException;
}
