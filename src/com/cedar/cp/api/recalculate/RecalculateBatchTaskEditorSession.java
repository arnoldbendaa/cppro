package com.cedar.cp.api.recalculate;
import java.util.List;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;

public interface RecalculateBatchTaskEditorSession extends BusinessSession {
	
	RecalculateBatchTaskEditor getRecalculateBatchTaskEditor();

	EntityList getAvailableModels();

	EntityList getBudgetCyclesForModel(int modelId);

	EntityList getDataEntryProfile(int budgetCycleId, int modelId);

	EntityList getHierarchiesForModel(int modelId);

	EntityList getWebModels();
	
	
}
