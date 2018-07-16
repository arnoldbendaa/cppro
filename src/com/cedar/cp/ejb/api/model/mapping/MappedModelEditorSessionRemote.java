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
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface MappedModelEditorSessionRemote extends EJBObject {

   MappedModelEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   MappedModelEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   MappedModelPK insert(MappedModelEditorSessionCSO var1) throws ValidationException, RemoteException;

   MappedModelPK copy(MappedModelEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(MappedModelEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   int issueModelImportTask(int var1, boolean var2, int[] var3, int var4) throws ValidationException, RemoteException;

   void refreshMappedModelCalendar(int var1, MappedModelPK var2) throws ValidationException, RemoteException;

   void refreshMappedModelHierarchy(int var1, MappedModelPK var2) throws ValidationException, RemoteException;

   int issueMappedModelExportTask(int var1, int var2, String var3, List<EntityRef> var4) throws ValidationException, RemoteException;

   int getModelId(Object primaryKey) throws RemoteException;
}
