package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class LoggedInUsersCK extends CompositeKey implements Serializable {

	protected LoggedInUsersPK mLoggedInUsersPK;

	public LoggedInUsersCK(LoggedInUsersPK paramLoggedInUsersPK) {
		mLoggedInUsersPK = paramLoggedInUsersPK;
	}

	public LoggedInUsersPK getLoggedInUsersPK() {
		return mLoggedInUsersPK;
	}

	public PrimaryKey getPK() {
		return mLoggedInUsersPK;
	}

	public int hashCode() {
		return mLoggedInUsersPK.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof LoggedInUsersPK)) {
			return obj.equals(this);
		}
		if (!(obj instanceof LoggedInUsersCK)) {
			return false;
		}
		LoggedInUsersCK other = (LoggedInUsersCK) obj;
		boolean eq = true;

		eq = (eq) && (mLoggedInUsersPK.equals(other.mLoggedInUsersPK));

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(mLoggedInUsersPK);
		sb.append("]");
		return sb.toString();
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append("LoggedInUsersCK|");
		sb.append(mLoggedInUsersPK.toTokens());
		return sb.toString();
	}

	public static LoggedInUsersCK getKeyFromTokens(String extKey) {
		String[] token = extKey.split("[|]");
		int i = 0;
		checkExpected("LoggedInUsersCK", token[(i++)]);
		checkExpected("LoggedInUsersPK", token[(i++)]);
		i = 1;
		return new LoggedInUsersCK(LoggedInUsersPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
	}

	private static void checkExpected(String expected, String found) {
		if (!expected.equals(found))
			throw new IllegalArgumentException("expected=" + expected + " found=" + found);
	}
}