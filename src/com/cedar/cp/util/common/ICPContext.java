package com.cedar.cp.util.common;

import javax.servlet.http.HttpSession;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.UserContext;

public interface ICPContext {

	public String getUserName();

	public UserContext getUserContext();

	public String getUserId();

	public String getPassword();

	public String getConnectionURL();

	public CPConnection getCPConnection();
	
	public int getIntUserId();
	
	public String getClientIP();

	public String getClientHost();
	
	public boolean getIsUserAdministrator();
	
	public HttpSession getSession();
	
}
