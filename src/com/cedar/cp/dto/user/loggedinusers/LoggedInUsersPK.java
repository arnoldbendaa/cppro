package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class LoggedInUsersPK extends PrimaryKey implements Serializable {

	private int mHashCode = -2147483648;
	int mLoggedInUsersId;

	public LoggedInUsersPK(int newLoggedInUsersId) {
		mLoggedInUsersId = newLoggedInUsersId;
	}

	public int getLoggedInUsersId() {
		return mLoggedInUsersId;
	}

	public int hashCode() {
		if (mHashCode == -2147483648) {
			mHashCode += String.valueOf(mLoggedInUsersId).hashCode();
		}

		return mHashCode;
	}

	public boolean equals(Object obj) {
		LoggedInUsersPK other = null;

		if ((obj instanceof LoggedInUsersCK)) {
			other = ((LoggedInUsersCK) obj).getLoggedInUsersPK();
		}
		else if ((obj instanceof LoggedInUsersPK))
			other = (LoggedInUsersPK) obj;
		else {
			return false;
		}
		boolean eq = true;

		eq = (eq) && (mLoggedInUsersId == other.mLoggedInUsersId);

		return eq;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" LoggedInUsersId=");
		sb.append(mLoggedInUsersId);
		return sb.toString().substring(1);
	}

	public String toTokens() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		sb.append(mLoggedInUsersId);
		return "LoggedInUsersPK|" + sb.toString().substring(1);
	}

	public static LoggedInUsersPK getKeyFromTokens(String extKey) {
		String[] extValues = extKey.split("[|]");

		if (extValues.length != 2) {
			throw new IllegalStateException(extKey + ": format incorrect");
		}
		if (!extValues[0].equals("LoggedInUsersPK")) {
			throw new IllegalStateException(extKey + ": format incorrect - must start with 'LoggedInUsersPK|'");
		}
		extValues = extValues[1].split(",");

		int i = 0;
		int pLoggedInUsersId = new Integer(extValues[(i++)]).intValue();
		return new LoggedInUsersPK(pLoggedInUsersId);
	}
}