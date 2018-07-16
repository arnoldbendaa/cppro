package com.cedar.cp.dto.importtask;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class ImportTaskPK extends PrimaryKey implements Serializable {

	private int mHashCode = -2147483648;
	int mImportTaskId;

	public ImportTaskPK(int newImportTaskId) {
		mImportTaskId = newImportTaskId;
	}

	public int getImportTaskId() {
		return mImportTaskId;
	}

	public int hashCode() {
		if (mHashCode == -2147483648) {
			mHashCode += String.valueOf(mImportTaskId).hashCode();
		}

		return mHashCode;
	}

	public boolean equals(Object obj) {
		ImportTaskPK other = null;

		if ((obj instanceof ImportTaskCK)) {
			other = ((ImportTaskCK) obj).getImportTaskPK();
		}
		else if ((obj instanceof ImportTaskPK))
			other = (ImportTaskPK) obj;
		else {
			return false;
		}
		boolean eq = true;

		eq = (eq) && (mImportTaskId == other.mImportTaskId);

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ImportTaskId=");
		sb.append(mImportTaskId);
		return sb.toString().substring(1);
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		sb.append(mImportTaskId);
		return "ImportTaskPK|" + sb.toString().substring(1);
	}

	public static ImportTaskPK getKeyFromTokens(String extKey) {
		String[] extValues = extKey.split("[|]");

		if (extValues.length != 2) {
			throw new IllegalStateException(extKey + ": format incorrect");
		}
		if (!extValues[0].equals("ImportTaskPK")) {
			throw new IllegalStateException(extKey + ": format incorrect - must start with 'ImportTaskPK|'");
		}
		extValues = extValues[1].split(",");

		int i = 0;
		int pImportTaskId = new Integer(extValues[(i++)]).intValue();
		return new ImportTaskPK(pImportTaskId);
	}
}