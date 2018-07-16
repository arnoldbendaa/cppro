package com.cedar.cp.ejb.api.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionCSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionSSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface GlobalMappedModel2EditorSessionLocal extends EJBLocalObject {

   GlobalMappedModel2EditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   GlobalMappedModel2EditorSessionSSO getNewItemData(int var1) throws EJBException;

   GlobalMappedModel2PK insert(GlobalMappedModel2EditorSessionCSO var1) throws ValidationException, EJBException;

   GlobalMappedModel2PK copy(GlobalMappedModel2EditorSessionCSO var1) throws ValidationException, EJBException;

   void update(GlobalMappedModel2EditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int issueModelImportTask(int var1, boolean var2, int[] var3, int var4) throws ValidationException, EJBException;

   boolean isGlobalMappedModel(int modelId) throws ValidationException, EJBException;
   
   void refreshMappedModelCalendar(int var1, GlobalMappedModel2PK var2) throws ValidationException, EJBException;

   void refreshMappedModelHierarchy(int var1, GlobalMappedModel2PK var2) throws ValidationException, EJBException;

   int issueMappedModelExportTask(int var1, int var2, String var3, List<EntityRef> var4) throws ValidationException, EJBException;

   int getModelId(Object primaryKey) throws EJBException;
}
