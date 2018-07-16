package com.cedar.cp.dto.recalculate;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class RecalculateBatchTaskRespAreaPK extends PrimaryKey implements Serializable {
	private int mHashCode = -2147483648;
	int mRecalculateBatchTaskId;
	int mRecalculateBatchTaskRespAreaId;

	public RecalculateBatchTaskRespAreaPK(int newRecalculateBatchTaskId, int newRecalculateBatchTaskRespAreaId) {
		mRecalculateBatchTaskId = newRecalculateBatchTaskId;
		mRecalculateBatchTaskRespAreaId = newRecalculateBatchTaskRespAreaId;
	}

	public int getRecalculateBatchTaskId() {
		return mRecalculateBatchTaskId;
	}

	public int getRecalculateBatchTaskRespAreaId() {
		return mRecalculateBatchTaskRespAreaId;
	}

	public int hashCode() {
		if (mHashCode == -2147483648) {
			mHashCode += String.valueOf(mRecalculateBatchTaskId).hashCode();
			mHashCode += String.valueOf(mRecalculateBatchTaskRespAreaId).hashCode();
		}

		return mHashCode;
	}

	public boolean equals(Object obj) {
		RecalculateBatchTaskRespAreaPK other = null;

		if ((obj instanceof RecalculateBatchTaskRespAreaCK)) {
			other = ((RecalculateBatchTaskRespAreaCK) obj).getRecalculateBatchTaskRespAreaPK();
		} else if ((obj instanceof RecalculateBatchTaskRespAreaPK))
			other = (RecalculateBatchTaskRespAreaPK) obj;
		else {
			return false;
		}
		boolean eq = true;

		eq = (eq) && (mRecalculateBatchTaskId == other.mRecalculateBatchTaskId);
		eq = (eq) && (mRecalculateBatchTaskRespAreaId == other.mRecalculateBatchTaskRespAreaId);

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" RecalculateBatchTaskId=");
		sb.append(mRecalculateBatchTaskId);
		sb.append(",RecalculateBatchTaskRespAreaId=");
		sb.append(mRecalculateBatchTaskRespAreaId);
		return sb.toString().substring(1);
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		sb.append(mRecalculateBatchTaskId);
		sb.append(",");
		sb.append(mRecalculateBatchTaskRespAreaId);
		return "RecalculateBatchTaskRespAreaPK|" + sb.toString().substring(1);
	}

	public static RecalculateBatchTaskRespAreaPK getKeyFromTokens(String extKey) {
		String[] extValues = extKey.split("[|]");

		if (extValues.length != 2) {
			throw new IllegalStateException(extKey + ": format incorrect");
		}
		if (!extValues[0].equals("RecalculateBatchTaskRespAreaPK")) {
			throw new IllegalStateException(extKey + ": format incorrect - must start with 'RecalculateBatchTaskRespAreaPK|'");
		}
		extValues = extValues[1].split(",");

		int i = 0;
		int pRecalculateBatchTaskId = new Integer(extValues[(i++)]).intValue();
		int pRecalculateBatchTaskRespAreaId = new Integer(extValues[(i++)]).intValue();
		return new RecalculateBatchTaskRespAreaPK(pRecalculateBatchTaskId, pRecalculateBatchTaskRespAreaId);
	}
}