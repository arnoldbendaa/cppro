package com.cedar.cp.dto.recalculate;

import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class RecalculateBatchTaskCK extends CompositeKey implements Serializable {

	protected RecalculateBatchTaskPK mRecalculateBatchTaskPK;

	public RecalculateBatchTaskCK(RecalculateBatchTaskPK paramRecalculateBatchTaskPK) {
		mRecalculateBatchTaskPK = paramRecalculateBatchTaskPK;
	}

	public RecalculateBatchTaskPK getRecalculateBatchTaskPK() {
		return mRecalculateBatchTaskPK;
	}

	public PrimaryKey getPK() {
		return mRecalculateBatchTaskPK;
	}

	public int hashCode() {
		return mRecalculateBatchTaskPK.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof RecalculateBatchTaskPK)) {
			return obj.equals(this);
		}
		if (!(obj instanceof RecalculateBatchTaskCK)) {
			return false;
		}
		RecalculateBatchTaskCK other = (RecalculateBatchTaskCK) obj;
		boolean eq = true;

		eq = (eq) && (mRecalculateBatchTaskPK.equals(other.mRecalculateBatchTaskPK));

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(mRecalculateBatchTaskPK);
		sb.append("]");
		return sb.toString();
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append("RecalculateBatchTaskCK|");
		sb.append(mRecalculateBatchTaskPK.toTokens());
		return sb.toString();
	}

	public static RecalculateBatchTaskCK getKeyFromTokens(String extKey) {
		String[] token = extKey.split("[|]");
		int i = 0;
		checkExpected("ImportTaskCK", token[(i++)]);
		checkExpected("ImportTaskPK", token[(i++)]);
		i = 1;
		return new RecalculateBatchTaskCK(RecalculateBatchTaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
	}

	private static void checkExpected(String expected, String found) {
		if (!expected.equals(found))
			throw new IllegalArgumentException("expected=" + expected + " found=" + found);
	}
}