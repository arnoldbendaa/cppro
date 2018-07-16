package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class BudgetCycleLinkCK extends BudgetCycleCK implements Serializable {

	protected BudgetCycleLinkPK mBudgetCycleLinkPK;

	public BudgetCycleLinkCK(ModelPK paramModelPK, BudgetCyclePK paramBudgetCyclePK, BudgetCycleLinkPK paramBudgetCycleLinkPK) {
		super(paramModelPK, paramBudgetCyclePK);

		mBudgetCycleLinkPK = paramBudgetCycleLinkPK;
	}

	public BudgetCycleLinkPK getBudgetCycleLinkPK() {
		return mBudgetCycleLinkPK;
	}

	public PrimaryKey getPK() {
		return mBudgetCycleLinkPK;
	}

	public int hashCode() {
		return mBudgetCycleLinkPK.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof BudgetCycleLinkPK)) {
			return obj.equals(this);
		}
		if (!(obj instanceof BudgetCycleLinkCK)) {
			return false;
		}
		BudgetCycleLinkCK other = (BudgetCycleLinkCK) obj;
		boolean eq = true;

		eq = (eq) && (mModelPK.equals(other.mModelPK));
		eq = (eq) && (mBudgetCyclePK.equals(other.mBudgetCyclePK));
		eq = (eq) && (mBudgetCycleLinkPK.equals(other.mBudgetCycleLinkPK));

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("[");
		sb.append(mBudgetCycleLinkPK);
		sb.append("]");
		return sb.toString();
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append("BudgetCycleLinkCK|");
		sb.append(super.getPK().toTokens());
		sb.append('|');
		sb.append(mBudgetCycleLinkPK.toTokens());
		return sb.toString();
	}

	public static BudgetCycleLinkCK getKeyFromTokens(String extKey) {
		String[] token = extKey.split("[|]");
		int i = 0;
		checkExpected("BudgetCycleLinkCK", token[(i++)]);
		checkExpected("ModelPK", token[(i++)]);
		i++;
		checkExpected("BudgetCyclePK", token[(i++)]);
	    i++;
		checkExpected("BudgetCycleLinkPK", token[(i++)]);
		i = 1;
		  return new BudgetCycleLinkCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetCyclePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetCycleLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
	}

	private static void checkExpected(String expected, String found) {
		if (!expected.equals(found))
			throw new IllegalArgumentException("expected=" + expected + " found=" + found);
	}
}