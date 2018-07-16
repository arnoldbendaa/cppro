package com.cedar.cp.dto.recalculate;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class RecalculateBatchTaskPK extends PrimaryKey implements Serializable {

	private int mHashCode = -2147483648;
	int mRecalculateBatchTaskId;

	public RecalculateBatchTaskPK(int newRecalculateBatchTaskId) {
		mRecalculateBatchTaskId = newRecalculateBatchTaskId;
	}

	public int getRecalculateBatchTaskId() {
		return mRecalculateBatchTaskId;
	}

	public int hashCode() {
		if (mHashCode == -2147483648) {
			mHashCode += String.valueOf(mRecalculateBatchTaskId).hashCode();
		}

		return mHashCode;
	}

	public boolean equals(Object obj) {
		RecalculateBatchTaskPK other = null;

		if ((obj instanceof RecalculateBatchTaskCK)) {
			other = ((RecalculateBatchTaskCK) obj).getRecalculateBatchTaskPK();
		}
		else if ((obj instanceof RecalculateBatchTaskPK))
			other = (RecalculateBatchTaskPK) obj;
		else {
			return false;
		}
		boolean eq = true;

		eq = (eq) && (mRecalculateBatchTaskId == other.mRecalculateBatchTaskId);

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" RecalculateBatchTaskId=");
		sb.append(mRecalculateBatchTaskId);
		return sb.toString().substring(1);
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		sb.append(mRecalculateBatchTaskId);
		return "RecalculateBatchTaskPK|" + sb.toString().substring(1);
	}

	public static RecalculateBatchTaskPK getKeyFromTokens(String extKey) {
		String[] extValues = extKey.split("[|]");

		if (extValues.length != 2) {
			throw new IllegalStateException(extKey + ": format incorrect");
		}
		if (!extValues[0].equals("RecalculateBatchTaskPK")) {
			throw new IllegalStateException(extKey + ": format incorrect - must start with 'RecalculateBatchTaskPK|'");
		}
		extValues = extValues[1].split(",");

		int i = 0;
		int pRecalculateBatchTaskId = new Integer(extValues[(i++)]).intValue();
		return new RecalculateBatchTaskPK(pRecalculateBatchTaskId);
	}
}