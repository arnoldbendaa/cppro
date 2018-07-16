// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.recalculate;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import java.util.List;

public interface RecalculateBatchTaskAssignment {

	Object getPrimaryKey();

	void setPrimaryKey(Object var1);

	ModelRef getOwningModelRef();

	BudgetCycleRef getOwningBudgetCycleRef();

	StructureElementRef getOwningBudgetLocationRef();

	void setOwningModelRef(ModelRef var1);

	void setOwningBudgetCycleRef(BudgetCycleRef var1);

	void setOwningBudgetLocationRef(StructureElementRef var1);

	int getId();

	List getParents();

	void setParents(List var1);
}
