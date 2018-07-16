package com.cedar.cp.impl.user.loggedinusers;

import java.util.List;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.impl.user.loggedinusers.LoggedInUsersEditorSessionImpl;
import com.cedar.cp.api.user.loggedinusers.LoggedInUsersEditorSession;
import com.cedar.cp.api.user.loggedinusers.LoggedInUsersProcess;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class LoggedInUsersProcessImpl extends BusinessProcessImpl implements LoggedInUsersProcess {

   private Log mLog = new Log(this.getClass());

	public LoggedInUsersProcessImpl(CPConnection connection) {
		super(connection);
	}

	@SuppressWarnings("unchecked")
    public LoggedInUsersEditorSession getLoggedInUsersEditorSession(Object key) throws ValidationException {
		LoggedInUsersEditorSessionImpl sess = new LoggedInUsersEditorSessionImpl(this, key);
		this.mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getAllLoggedInUsers() {
		try {
			return getConnection().getListHelper().getAllLoggedInUsers();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllLoggedInUsers", var2);
		}
	}

	public String getProcessName() {
		String ret = "Processing LoggedInUsers";
		return ret;
	}

	protected int getProcessID() {
		return 106;
	}

	public void logoutUsersByUserName(List<String> userNames) {
		getConnection().getListHelper().removeContextByUserName(userNames);
	}
}
