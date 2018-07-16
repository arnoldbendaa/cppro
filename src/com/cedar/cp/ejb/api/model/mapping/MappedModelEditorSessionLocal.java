// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.mapping;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionCSO;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionSSO;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface MappedModelEditorSessionLocal extends EJBLocalObject {

   MappedModelEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   MappedModelEditorSessionSSO getNewItemData(int var1) throws EJBException;

   MappedModelPK insert(MappedModelEditorSessionCSO var1) throws ValidationException, EJBException;

   MappedModelPK copy(MappedModelEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(MappedModelEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int issueModelImportTask(int var1, boolean var2, int[] var3, int var4) throws ValidationException, EJBException;

   void refreshMappedModelCalendar(int var1, MappedModelPK var2) throws ValidationException, EJBException;

   void refreshMappedModelHierarchy(int var1, MappedModelPK var2) throws ValidationException, EJBException;

   int issueMappedModelExportTask(int var1, int var2, String var3, List<EntityRef> var4) throws ValidationException, EJBException;

   int getModelId(Object primaryKey) throws EJBException;
}
