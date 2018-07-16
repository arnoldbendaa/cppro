package com.cedar.cp.dto.recalculate;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class RecalculateBatchTaskFormPK extends PrimaryKey implements Serializable {
	private int mHashCode = -2147483648;
	int mRecalculateBatchTaskId;
	int mRecalculateBatchTaskFormId;

	public RecalculateBatchTaskFormPK(int newRecalculateBatchTaskId, int newRecalculateBatchTaskFormId) {
		mRecalculateBatchTaskId = newRecalculateBatchTaskId;
		mRecalculateBatchTaskFormId = newRecalculateBatchTaskFormId;
	}

	public int getRecalculateBatchTaskId() {
		return mRecalculateBatchTaskId;
	}

	public int getRecalculateBatchTaskFormId() {
		return mRecalculateBatchTaskFormId;
	}

	public int hashCode() {
		if (mHashCode == -2147483648) {
			mHashCode += String.valueOf(mRecalculateBatchTaskId).hashCode();
			mHashCode += String.valueOf(mRecalculateBatchTaskFormId).hashCode();
		}

		return mHashCode;
	}

	public boolean equals(Object obj) {
		RecalculateBatchTaskFormPK other = null;

		if ((obj instanceof RecalculateBatchTaskFormCK)) {
			other = ((RecalculateBatchTaskFormCK) obj).getRecalculateBatchTaskFormPK();
		} else if ((obj instanceof RecalculateBatchTaskFormPK))
			other = (RecalculateBatchTaskFormPK) obj;
		else {
			return false;
		}
		boolean eq = true;

		eq = (eq) && (mRecalculateBatchTaskId == other.mRecalculateBatchTaskId);
		eq = (eq) && (mRecalculateBatchTaskFormId == other.mRecalculateBatchTaskFormId);

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" RecalculateBatchTaskId=");
		sb.append(mRecalculateBatchTaskId);
		sb.append(",RecalculateBatchTaskFormId=");
		sb.append(mRecalculateBatchTaskFormId);
		return sb.toString().substring(1);
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		sb.append(mRecalculateBatchTaskId);
		sb.append(",");
		sb.append(mRecalculateBatchTaskFormId);
		return "RecalculateBatchTaskFormPK|" + sb.toString().substring(1);
	}

	public static RecalculateBatchTaskFormPK getKeyFromTokens(String extKey) {
		String[] extValues = extKey.split("[|]");

		if (extValues.length != 2) {
			throw new IllegalStateException(extKey + ": format incorrect");
		}
		if (!extValues[0].equals("RecalculateBatchTaskFormPK")) {
			throw new IllegalStateException(extKey + ": format incorrect - must start with 'RecalculateBatchTaskFormPK|'");
		}
		extValues = extValues[1].split(",");

		int i = 0;
		int pRecalculateBatchTaskId = new Integer(extValues[(i++)]).intValue();
		int pRecalculateBatchTaskFormId = new Integer(extValues[(i++)]).intValue();
		return new RecalculateBatchTaskFormPK(pRecalculateBatchTaskId, pRecalculateBatchTaskFormId);
	}
}