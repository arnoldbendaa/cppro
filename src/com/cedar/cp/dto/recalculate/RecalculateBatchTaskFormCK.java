package com.cedar.cp.dto.recalculate;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class RecalculateBatchTaskFormCK extends RecalculateBatchTaskCK implements Serializable {
	protected RecalculateBatchTaskFormPK mRecalculateBatchTaskFormPK;

	public RecalculateBatchTaskFormCK(RecalculateBatchTaskPK paramRecalculateBatchTaskPK, RecalculateBatchTaskFormPK paramRecalculateBatchTaskFormPK) {
		super(paramRecalculateBatchTaskPK);

		mRecalculateBatchTaskFormPK = paramRecalculateBatchTaskFormPK;
	}

	public RecalculateBatchTaskFormPK getRecalculateBatchTaskFormPK() {
		return mRecalculateBatchTaskFormPK;
	}

	public PrimaryKey getPK() {
		return mRecalculateBatchTaskFormPK;
	}

	public int hashCode() {
		return mRecalculateBatchTaskFormPK.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof RecalculateBatchTaskFormPK)) {
			return obj.equals(this);
		}
		if (!(obj instanceof RecalculateBatchTaskFormCK)) {
			return false;
		}
		RecalculateBatchTaskFormCK other = (RecalculateBatchTaskFormCK) obj;
		boolean eq = true;

		eq = (eq) && (mRecalculateBatchTaskPK.equals(other.mRecalculateBatchTaskPK));
		eq = (eq) && (mRecalculateBatchTaskFormPK.equals(other.mRecalculateBatchTaskFormPK));

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("[");
		sb.append(mRecalculateBatchTaskFormPK);
		sb.append("]");
		return sb.toString();
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append("RecalculateBatchTaskFormCK|");
		sb.append(super.getPK().toTokens());
		sb.append('|');
		sb.append(mRecalculateBatchTaskFormPK.toTokens());
		return sb.toString();
	}

	public static RecalculateBatchTaskCK getKeyFromTokens(String extKey) {
		String[] token = extKey.split("[|]");
		int i = 0;
		checkExpected("RecalculateBatchTaskFormCK", token[(i++)]);
		checkExpected("RecalculateBatchTaskPK", token[(i++)]);
		i++;
		checkExpected("RecalculateBatchTaskFormPK", token[(i++)]);
		i = 1;
		return new RecalculateBatchTaskFormCK(RecalculateBatchTaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), RecalculateBatchTaskFormPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
	}

	private static void checkExpected(String expected, String found) {
		if (!expected.equals(found))
			throw new IllegalArgumentException("expected=" + expected + " found=" + found);
	}
}