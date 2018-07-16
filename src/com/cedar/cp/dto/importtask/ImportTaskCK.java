package com.cedar.cp.dto.importtask;

import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class ImportTaskCK extends CompositeKey implements Serializable {

	protected ImportTaskPK mImportTaskPK;

	public ImportTaskCK(ImportTaskPK paramImportTaskPK) {
		mImportTaskPK = paramImportTaskPK;
	}

	public ImportTaskPK getImportTaskPK() {
		return mImportTaskPK;
	}

	public PrimaryKey getPK() {
		return mImportTaskPK;
	}

	public int hashCode() {
		return mImportTaskPK.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof ImportTaskPK)) {
			return obj.equals(this);
		}
		if (!(obj instanceof ImportTaskCK)) {
			return false;
		}
		ImportTaskCK other = (ImportTaskCK) obj;
		boolean eq = true;

		eq = (eq) && (mImportTaskPK.equals(other.mImportTaskPK));

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(mImportTaskPK);
		sb.append("]");
		return sb.toString();
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append("ImportTaskCK|");
		sb.append(mImportTaskPK.toTokens());
		return sb.toString();
	}

	public static ImportTaskCK getKeyFromTokens(String extKey) {
		String[] token = extKey.split("[|]");
		int i = 0;
		checkExpected("ImportTaskCK", token[(i++)]);
		checkExpected("ImportTaskPK", token[(i++)]);
		i = 1;
		return new ImportTaskCK(ImportTaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
	}

	private static void checkExpected(String expected, String found) {
		if (!expected.equals(found))
			throw new IllegalArgumentException("expected=" + expected + " found=" + found);
	}
}