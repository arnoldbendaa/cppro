// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:05:46
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.recalculate;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;

import java.io.Serializable;
import java.util.List;

public class RecalculateBatchTaskAssignmentImpl implements RecalculateBatchTaskAssignment, Serializable, Cloneable {

	private Object mPrimaryKey;
	private ModelRef mOwningModelRef;
	private BudgetCycleRef mOwningBudgetCycleRef;
	private StructureElementRef mOwningBudgetLocationRef;
	private List mLocationParents;

	public RecalculateBatchTaskAssignmentImpl(Object paramKey) {
		this.mPrimaryKey = paramKey;
	}

	public Object getPrimaryKey() {
		return this.mPrimaryKey;
	}

	public void setPrimaryKey(Object paramKey) {
		this.mPrimaryKey = (RecalculateBatchTaskPK) paramKey;
	}

	public ModelRef getOwningModelRef() {
		return this.mOwningModelRef;
	}

	public BudgetCycleRef getOwningBudgetCycleRef() {
		return this.mOwningBudgetCycleRef;
	}

	public StructureElementRef getOwningBudgetLocationRef() {
		return this.mOwningBudgetLocationRef;
	}

	public void setOwningModelRef(ModelRef ref) {
		this.mOwningModelRef = ref;
	}

	public void setOwningBudgetCycleRef(BudgetCycleRef ref) {
		this.mOwningBudgetCycleRef = ref;
	}

	public void setOwningBudgetLocationRef(StructureElementRef ref) {
		this.mOwningBudgetLocationRef = ref;
	}

	public Object clone() throws CloneNotSupportedException {
		RecalculateBatchTaskAssignmentImpl copy = (RecalculateBatchTaskAssignmentImpl) super.clone();
		return copy;
	}

	public int getId() {
		RecalculateBatchTaskPK pk = (RecalculateBatchTaskPK) this.getPrimaryKey();
		return pk.getRecalculateBatchTaskId();
	}

	public void setParents(List path) {
		this.mLocationParents = path;
	}

	public List getParents() {
		return this.mLocationParents;
	}
}
