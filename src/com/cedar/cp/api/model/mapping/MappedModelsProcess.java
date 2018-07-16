// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.MappedModelEditorSession;
import java.util.List;

public interface MappedModelsProcess extends BusinessProcess {

   EntityList getAllMappedModels();
   
   EntityList getAllMappedModelsForLoggedUser();

   EntityList getMappedFinanceCubes(int var1);

   MappedModelEditorSession getMappedModelEditorSession(Object var1) throws ValidationException;

   int issueModelImportTask(boolean var1, int[] var2) throws Exception;

   int issueMappedModelExportTask(int var1, String var2, List<EntityRef> var3) throws ValidationException;
}
