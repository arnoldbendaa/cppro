// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.ModelEditorSessionCSO;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelPK;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

public interface ModelEditorSessionRemote extends EJBObject {

   ModelEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ModelEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ModelPK insert(ModelEditorSessionCSO var1) throws ValidationException, RemoteException;

   ModelPK copy(ModelEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ModelEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   Object[][] checkForms(int modelId) throws RemoteException;

   int issueImportDataModelTask(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId, int issuingTaskId) throws ValidationException, RemoteException;
}
