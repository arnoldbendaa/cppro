package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleLinkRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class BudgetCycleLinkRefImpl extends EntityRefImpl implements BudgetCycleLinkRef, Serializable {

	public BudgetCycleLinkRefImpl(BudgetCycleLinkCK key, String narrative) {
		super(key, narrative);
	}

	public BudgetCycleLinkRefImpl(BudgetCycleLinkPK key, String narrative) {
		super(key, narrative);
	}

	public BudgetCycleLinkPK getBudgetCycleLinkPK() {
		if ((mKey instanceof BudgetCycleLinkCK))
			return ((BudgetCycleLinkCK) mKey).getBudgetCycleLinkPK();
		return (BudgetCycleLinkPK) mKey;
	}
}