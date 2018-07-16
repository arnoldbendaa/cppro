package com.cedar.cp.impl.budgetlocation;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.UserModelSecurityEditorSession;
import com.cedar.cp.api.budgetlocation.UserModelSecurityProcess;
import com.cedar.cp.ejb.api.budgetlocation.UserModelSecurityEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class UserModelSecurityProcessImpl extends BusinessProcessImpl implements UserModelSecurityProcess {

	private Log mLog = new Log(getClass());

	public UserModelSecurityProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;

		UserModelSecurityEditorSessionServer es = new UserModelSecurityEditorSessionServer(getConnection());
		try {
			es.delete(primaryKey);
		} catch (CPException e) {
			throw new RuntimeException("can't delete " + primaryKey, e);
		}

		if (timer != null)
			timer.logDebug("deleteObject", primaryKey);
	}

	public UserModelSecurityEditorSession getUserModelSecurityEditorSession(Object key) throws ValidationException {
		UserModelSecurityEditorSessionImpl sess = new UserModelSecurityEditorSessionImpl(this, key);
		mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getUserModelSecurity() {
		try {
			return getConnection().getListHelper().getUserModelSecurity();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get UserModelSecurity", e);
		}
	}

	public String getProcessName() {
		String ret = "Processing Budget Location";
		return ret;
	}

	protected int getProcessID() {
		return 101;
	}
}