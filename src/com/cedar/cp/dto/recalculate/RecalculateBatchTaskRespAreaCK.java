package com.cedar.cp.dto.recalculate;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class RecalculateBatchTaskRespAreaCK extends RecalculateBatchTaskCK implements Serializable {
	protected RecalculateBatchTaskRespAreaPK mRecalculateBatchTaskRespAreaPK;

	public RecalculateBatchTaskRespAreaCK(RecalculateBatchTaskPK paramRecalculateBatchTaskPK, RecalculateBatchTaskRespAreaPK paramRecalculateBatchTaskRespAreaPK) {
		super(paramRecalculateBatchTaskPK);

		mRecalculateBatchTaskRespAreaPK = paramRecalculateBatchTaskRespAreaPK;
	}

	public RecalculateBatchTaskRespAreaPK getRecalculateBatchTaskRespAreaPK() {
		return mRecalculateBatchTaskRespAreaPK;
	}

	public PrimaryKey getPK() {
		return mRecalculateBatchTaskRespAreaPK;
	}

	public int hashCode() {
		return mRecalculateBatchTaskRespAreaPK.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof RecalculateBatchTaskRespAreaPK)) {
			return obj.equals(this);
		}
		if (!(obj instanceof RecalculateBatchTaskRespAreaCK)) {
			return false;
		}
		RecalculateBatchTaskRespAreaCK other = (RecalculateBatchTaskRespAreaCK) obj;
		boolean eq = true;

		eq = (eq) && (mRecalculateBatchTaskPK.equals(other.mRecalculateBatchTaskPK));
		eq = (eq) && (mRecalculateBatchTaskRespAreaPK.equals(other.mRecalculateBatchTaskRespAreaPK));

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("[");
		sb.append(mRecalculateBatchTaskRespAreaPK);
		sb.append("]");
		return sb.toString();
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append("RecalculateBatchTaskRespAreaCK|");
		sb.append(super.getPK().toTokens());
		sb.append('|');
		sb.append(mRecalculateBatchTaskRespAreaPK.toTokens());
		return sb.toString();
	}

	public static RecalculateBatchTaskCK getKeyFromTokens(String extKey) {
		String[] token = extKey.split("[|]");
		int i = 0;
		checkExpected("RecalculateBatchTaskRespAreaCK", token[(i++)]);
		checkExpected("RecalculateBatchTaskPK", token[(i++)]);
		i++;
		checkExpected("RecalculateBatchTaskRespAreaPK", token[(i++)]);
		i = 1;
		return new RecalculateBatchTaskRespAreaCK(RecalculateBatchTaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), RecalculateBatchTaskRespAreaPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
	}

	private static void checkExpected(String expected, String found) {
		if (!expected.equals(found))
			throw new IllegalArgumentException("expected=" + expected + " found=" + found);
	}

}