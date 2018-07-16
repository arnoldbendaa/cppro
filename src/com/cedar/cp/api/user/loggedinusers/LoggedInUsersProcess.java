package com.cedar.cp.api.user.loggedinusers;

import java.util.List;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;

public interface LoggedInUsersProcess extends BusinessProcess {

	EntityList getAllLoggedInUsers();
	
	void logoutUsersByUserName(List<String> userNames);
}
