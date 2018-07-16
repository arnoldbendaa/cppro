// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import java.util.List;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.ModelEditorSessionCSO;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelPK;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ModelEditorSessionLocal extends EJBLocalObject {

   ModelEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ModelEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ModelPK insert(ModelEditorSessionCSO var1) throws ValidationException, EJBException;

   ModelPK copy(ModelEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ModelEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   Object[][] checkForms(int modelId) throws EJBException;

   int issueImportDataModelTask(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId, int issuingTaskId) throws ValidationException, EJBException;
}
