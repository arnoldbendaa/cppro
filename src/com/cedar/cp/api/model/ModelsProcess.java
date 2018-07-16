// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelEditorSession;

import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeModel;

public interface ModelsProcess extends BusinessProcess {

   EntityList getAllModels();

   EntityList getAllModelsWeb();

   EntityList getAllModelsWebForUser(int var1);

   EntityList getAllModelsWithActiveCycleForUser(int var1);

   EntityList getAllBudgetHierarchies();

   EntityList getModelForDimension(int var1);

   EntityList getModelDimensions(int var1);

   EntityList getModelDimensionseExcludeCall(int var1);

   EntityList getModelDetailsWeb(int var1);

   EntityList getAllRootsForModel(int var1);

   EntityList getBudgetHierarchyRootNodeForModel(int var1);

   EntityList getBudgetCyclesToFixState(int var1);

   EntityList getMaxDepthForBudgetHierarchy(int var1);

   EntityList getCalendarSpecForModel(int var1);

   EntityList getHierarchiesForModel(int var1);

   ModelEditorSession getModelEditorSession(Object var1) throws ValidationException;

   EntityList getTreeInfoForDimTypeInModel(int var1, int var2);

   TreeModel[] getTreeInfoForModelDimTypes(int var1, int[] var2);

   TreeModel[] getTreeInfoForModelDimSeq(int var1, int[] var2);

   TreeModel[] getTreeInfoForModelRA(int var1);

   List doElementPickerSearch(int var1, String var2);

   TreeModel getRespAreaHierarchy(Object var1) throws ValidationException;

   DimensionRef getRaDimensionRef(Object var1);

   EntityList getAllHierarchiesForModel(Object var1);

   EntityList getModelDimensions(Object var1);

   int getModelId(Object var1);

   EntityList getAllDimensionsForModel(Object var1);

   List<Integer> getReadOnlyRaAccessPositions(int var1, int var2);

   EntityList getAllModelsForLoggedUser();

   EntityList getAllModelsForGlobalMappedModel(int modelId);
   
   Map<String, String> getPropertiesForModelVisId(String modelVisId);
   
   Map<String, String> getPropertiesForModelId(int modelId);

   int issueImportDataModelTask(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId) throws ValidationException;
}
