package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class LoggedInUsersRefImpl extends EntityRefImpl implements Serializable {

	public LoggedInUsersRefImpl(LoggedInUsersPK key, String narrative) {
		super(key, narrative);
	}

	public LoggedInUsersPK getLoggedInUsersPK() {
		return (LoggedInUsersPK) this.mKey;
	}
}
