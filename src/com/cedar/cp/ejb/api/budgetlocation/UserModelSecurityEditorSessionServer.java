package com.cedar.cp.ejb.api.budgetlocation;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.impl.budgetlocation.UserModelSecurityEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class UserModelSecurityEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/UserModelSecurityEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/UserModelSecurityEditorSessionLocalHome";
	protected UserModelSecurityEditorSessionRemote mRemote;
	protected UserModelSecurityEditorSessionLocal mLocal;
	private Log mLog = new Log(getClass());
	public static UserModelSecurityEditorSessionSEJB server = new UserModelSecurityEditorSessionSEJB();

	public UserModelSecurityEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public UserModelSecurityEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private UserModelSecurityEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//		if (mRemote == null) {
//			String jndiName = getRemoteJNDIName();
//			try {
//				UserModelSecurityEditorSessionHome home = (UserModelSecurityEditorSessionHome) getHome(jndiName, UserModelSecurityEditorSessionHome.class);
//				mRemote = home.create();
//			} catch (CreateException e) {
//				removeFromCache(jndiName);
//				e.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " CreateException", e);
//			} catch (RemoteException e) {
//				removeFromCache(jndiName);
//				e.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " RemoteException", e);
//			}
//
//		}
//
//		return mRemote;
		return this.server;
	}

	private UserModelSecurityEditorSessionSEJB getLocal() throws CPException {
//		if (mLocal == null) {
//			try {
//				UserModelSecurityEditorSessionLocalHome home = (UserModelSecurityEditorSessionLocalHome) getLocalHome(getLocalJNDIName());
//				mLocal = home.create();
//			} catch (CreateException e) {
//				throw new CPException("can't create local session for " + getLocalJNDIName(), e);
//			}
//		}
//
//		return mLocal;
		return this.server;
	}

	public void removeSession() throws CPException {
	}

	public UserModelSecurityEditorSessionSSO getItemData(Object primaryKey_) throws ValidationException {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		try {
			UserModelSecurityEditorSessionSSO ret = null;
			if (isRemoteConnection())
				ret = getRemote().getItemData(primaryKey_);
			else {
				ret = getLocal().getItemData(primaryKey_);
			}
			if (timer != null) {
				timer.logDebug("getItemData", primaryKey_.toString());
			}
			return ret;
		} catch (Exception e) {
			throw unravelException(e);
		}
	}

	public void delete(Object primaryKey_) throws ValidationException {
	}

	public void update(UserModelSecurityImpl editorData) throws ValidationException {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		try {
			if (isRemoteConnection())
				getRemote().update(new UserModelSecurityEditorSessionCSO(getUserId(), editorData));
			else {
				getLocal().update(new UserModelSecurityEditorSessionCSO(getUserId(), editorData));
			}
			if (timer != null)
				timer.logDebug("update", editorData.getPrimaryKey());
		} catch (Exception e) {
			e.printStackTrace();
			throw unravelException(e);
		}
	}

	public void doImport(List<String[]> importValues) throws ValidationException {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		try {
			if (isRemoteConnection())
				getRemote().doImport(importValues);
			else {
				getLocal().doImport(importValues);
			}
			if (timer != null)
				timer.logDebug("doImport");
		} catch (Exception e) {
			e.printStackTrace();
			throw unravelException(e);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/UserModelSecurityEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/UserModelSecurityEditorSessionLocalHome";
	}
}