package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2EditorSession;
import java.util.List;

public interface GlobalMappedModels2Process extends BusinessProcess {

   EntityList getAllGlobalMappedModels2();
   
   EntityList getAllGlobalMappedModelsForLoggedUser();

   EntityList getMappedFinanceCubes(int var1);

   GlobalMappedModel2EditorSession getMappedModelEditorSession(Object var1) throws ValidationException;

   int issueModelImportTask(boolean var1, int[] var2) throws Exception;

   int issueMappedModelExportTask(int var1, String var2, List<EntityRef> var3) throws ValidationException;

   boolean isGlobalMappedModel(int modelId);
}
