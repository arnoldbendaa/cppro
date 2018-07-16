package com.cedar.cp.ejb.api.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionCSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionSSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface GlobalMappedModel2EditorSessionRemote extends EJBObject {

   GlobalMappedModel2EditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   GlobalMappedModel2EditorSessionSSO getNewItemData(int var1) throws RemoteException;

   GlobalMappedModel2PK insert(GlobalMappedModel2EditorSessionCSO var1) throws ValidationException, RemoteException;

   GlobalMappedModel2PK copy(GlobalMappedModel2EditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(GlobalMappedModel2EditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   int issueModelImportTask(int var1, boolean var2, int[] var3, int var4) throws ValidationException, RemoteException;
   
   boolean isGlobalMappedModel(int modelId) throws ValidationException, RemoteException;
   
   void refreshMappedModelCalendar(int var1, GlobalMappedModel2PK var2) throws ValidationException, RemoteException;

   void refreshMappedModelHierarchy(int var1, GlobalMappedModel2PK var2) throws ValidationException, RemoteException;

   int issueMappedModelExportTask(int var1, int var2, String var3, List<EntityRef> var4) throws ValidationException, RemoteException;

   int getModelId(Object primaryKey) throws RemoteException;
}
