package com.cedar.cp.ejb.api.user.loggedinusers;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersImpl;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionCSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionSSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersPK;
import com.cedar.cp.ejb.api.user.loggedinusers.LoggedInUsersEditorSessionHome;
import com.cedar.cp.ejb.api.user.loggedinusers.LoggedInUsersEditorSessionLocal;
import com.cedar.cp.ejb.api.user.loggedinusers.LoggedInUsersEditorSessionLocalHome;
import com.cedar.cp.ejb.api.user.loggedinusers.LoggedInUsersEditorSessionRemote;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.Context;

public class LoggedInUsersEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/LoggedInUsersEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/LoggedInUsersEditorSessionLocalHome";
	protected LoggedInUsersEditorSessionRemote mRemote;
	protected LoggedInUsersEditorSessionLocal mLocal;
	private Log mLog = new Log(this.getClass());

	public LoggedInUsersEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public LoggedInUsersEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private LoggedInUsersEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
		if (this.mRemote == null) {
			String jndiName = this.getRemoteJNDIName();

			try {
				LoggedInUsersEditorSessionHome e = (LoggedInUsersEditorSessionHome) this.getHome(jndiName, LoggedInUsersEditorSessionHome.class);
				this.mRemote = e.create();
			} catch (CreateException ce) {
				this.removeFromCache(jndiName);
				ce.printStackTrace();
				throw new CPException("getRemote " + jndiName + " CreateException", ce);
			} catch (RemoteException re) {
				this.removeFromCache(jndiName);
				re.printStackTrace();
				throw new CPException("getRemote " + jndiName + " RemoteException", re);
			}
		}

		return this.mRemote;
	}

	private LoggedInUsersEditorSessionLocal getLocal() throws CPException {
		if (this.mLocal == null) {
			try {
				LoggedInUsersEditorSessionLocalHome e = (LoggedInUsersEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
				this.mLocal = e.create();
			} catch (CreateException e) {
				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), e);
			}
		}

		return this.mLocal;
	}

	public void removeSession() throws CPException {
	}

	public void delete(Object primaryKey_) throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().delete(this.getUserId(), primaryKey_);
			} else {
				this.getLocal().delete(this.getUserId(), primaryKey_);
			}

			if (timer != null) {
				timer.logDebug("delete", primaryKey_.toString());
			}

		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public LoggedInUsersEditorSessionSSO getNewItemData() throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			LoggedInUsersEditorSessionSSO e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().getNewItemData(this.getUserId());
			} else {
				e = this.getLocal().getNewItemData(this.getUserId());
			}

			if (timer != null) {
				timer.logDebug("getNewItemData", "");
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public LoggedInUsersEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			LoggedInUsersEditorSessionSSO e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().getItemData(this.getUserId(), key);
			} else {
				e = this.getLocal().getItemData(this.getUserId(), key);
			}

			if (timer != null) {
				timer.logDebug("getItemData", key.toString());
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public LoggedInUsersPK insert(LoggedInUsersImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			LoggedInUsersPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().insert(new LoggedInUsersEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().insert(new LoggedInUsersEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public LoggedInUsersPK copy(LoggedInUsersImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			LoggedInUsersPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().copy(new LoggedInUsersEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().copy(new LoggedInUsersEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public void update(LoggedInUsersImpl mEditorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().update(new LoggedInUsersEditorSessionCSO(this.getUserId(), mEditorData));
			} else {
				this.getLocal().update(new LoggedInUsersEditorSessionCSO(this.getUserId(), mEditorData));
			}

			if (timer != null) {
				timer.logDebug("update", mEditorData.getPrimaryKey());
			}

		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/LoggedInUsersEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/LoggedInUsersEditorSessionLocalHome";
	}

	public EntityList getAvailableModels() throws CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		EntityList ret = this.getConnection().getListHelper().getAllModels();
		if (timer != null) {
			timer.logDebug("getModelList", "");
		}
		return ret;
	}

	public EntityList getBudgetCyclesForModel(int modelId) throws CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		EntityList ret = this.getConnection().getListHelper().getBudgetCyclesForModel(modelId);
		if (timer != null) {
			timer.logDebug("getBudgetCyclesForModel", "");
		}
		return ret;
	}

	public EntityList getDataEntryProfile(int bcId, int modelId) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		EntityList ret = this.getConnection().getListHelper().getDataEntryProfile(bcId, modelId);
		if (timer != null) {
			timer.logDebug("getDataEntryProfile", "");
		}
		return ret;
	}

	public EntityList getHierarchiesForModel(int modelId) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		EntityList ret = this.getConnection().getListHelper().getHierarchiesForModel(modelId);
		if (timer != null) {
			timer.logDebug("getHierarchiesForModel", "");
		}
		return ret;
	}

	public EntityList getWebModels() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		EntityList ret = this.getConnection().getListHelper().getAllModelsWebForUser(this.getConnection().getUserContext().getUserId());
		if (timer != null) {
			timer.logDebug("getModelList", "");
		}
		return ret;
	}
}
