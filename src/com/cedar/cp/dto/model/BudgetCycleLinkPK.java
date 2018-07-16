package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class BudgetCycleLinkPK extends PrimaryKey implements Serializable {
	private int mHashCode = -2147483648;
	int mBudgetCycleId;
	int mXmlFormId;
	
	public BudgetCycleLinkPK(int newBudgetCycleId, int newXmlFormId) {
		mBudgetCycleId = newBudgetCycleId;
		mXmlFormId = newXmlFormId;
	}

	public int getBudgetCycleId() {
		return mBudgetCycleId;
	}

	public int getXmlFormId() {
		return mXmlFormId;
	}

	public int hashCode() {
		if (mHashCode == -2147483648) {
			mHashCode += String.valueOf(mBudgetCycleId).hashCode();
			mHashCode += String.valueOf(mXmlFormId).hashCode();
		}

		return mHashCode;
	}

	public boolean equals(Object obj) {
		BudgetCycleLinkPK other = null;

		if ((obj instanceof BudgetCycleLinkCK)) {
			other = ((BudgetCycleLinkCK) obj).getBudgetCycleLinkPK();
		} else if ((obj instanceof BudgetCycleLinkPK)) {
			other = (BudgetCycleLinkPK) obj;
		} else {
			return false;
		}
		boolean eq = true;

		eq = (eq) && (mBudgetCycleId == other.mBudgetCycleId);
		eq = (eq) && (mXmlFormId == other.mXmlFormId);

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" BudgetCycleId=");
		sb.append(mBudgetCycleId);
		sb.append(",XmlFormId=");
		sb.append(mXmlFormId);
		return sb.toString().substring(1);
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		sb.append(mBudgetCycleId);
		sb.append(",");
		sb.append(mXmlFormId);
		return "BudgetCycleLinkPK|" + sb.toString().substring(1);
	}

	public static BudgetCycleLinkPK getKeyFromTokens(String extKey) {
		String[] extValues = extKey.split("[|]");

		if (extValues.length != 2) {
			throw new IllegalStateException(extKey + ": format incorrect");
		}
		if (!extValues[0].equals("BudgetCycleLinkPK")) {
			throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetCycleLinkPK|'");
		}
		extValues = extValues[1].split(",");

		int i = 0;
		int pBudgetCycleId = new Integer(extValues[(i++)]).intValue();
		int pXmlFormId = new Integer(extValues[(i++)]).intValue();
		return new BudgetCycleLinkPK(pBudgetCycleId, pXmlFormId);
	}
}