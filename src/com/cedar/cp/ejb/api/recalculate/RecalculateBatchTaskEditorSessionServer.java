package com.cedar.cp.ejb.api.recalculate;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionCSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionSSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionHome;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionLocal;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionLocalHome;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionRemote;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEditorSessionSEJB;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.Context;

public class RecalculateBatchTaskEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/RecalculateBatchTaskEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/RecalculateBatchTaskEditorSessionLocalHome";
	protected RecalculateBatchTaskEditorSessionRemote mRemote;
	protected RecalculateBatchTaskEditorSessionLocal mLocal;
	public static RecalculateBatchTaskEditorSessionSEJB server = new RecalculateBatchTaskEditorSessionSEJB();
	private Log mLog = new Log(this.getClass());

	public RecalculateBatchTaskEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public RecalculateBatchTaskEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private RecalculateBatchTaskEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//		if (this.mRemote == null) {
//			String jndiName = this.getRemoteJNDIName();
//
//			try {
//				RecalculateBatchTaskEditorSessionHome e = (RecalculateBatchTaskEditorSessionHome) this.getHome(jndiName, RecalculateBatchTaskEditorSessionHome.class);
//				this.mRemote = e.create();
//			} catch (CreateException ce) {
//				this.removeFromCache(jndiName);
//				ce.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " CreateException", ce);
//			} catch (RemoteException re) {
//				this.removeFromCache(jndiName);
//				re.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " RemoteException", re);
//			}
//		}
//
//		return this.mRemote;
		return this.server;
	}

	private RecalculateBatchTaskEditorSessionSEJB getLocal() throws CPException {
//		if (this.mLocal == null) {
//			try {
//				RecalculateBatchTaskEditorSessionLocalHome e = (RecalculateBatchTaskEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
//				this.mLocal = e.create();
//			} catch (CreateException e) {
//				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), e);
//			}
//		}
//
//		return this.mLocal;
		return this.server;
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

	public RecalculateBatchTaskEditorSessionSSO getNewItemData() throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			RecalculateBatchTaskEditorSessionSSO e = null;
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

	public RecalculateBatchTaskEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			RecalculateBatchTaskEditorSessionSSO e = null;
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

	public RecalculateBatchTaskPK insert(RecalculateBatchTaskImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			RecalculateBatchTaskPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().insert(new RecalculateBatchTaskEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().insert(new RecalculateBatchTaskEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public RecalculateBatchTaskPK copy(RecalculateBatchTaskImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			RecalculateBatchTaskPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().copy(new RecalculateBatchTaskEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().copy(new RecalculateBatchTaskEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public void update(RecalculateBatchTaskImpl mEditorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().update(new RecalculateBatchTaskEditorSessionCSO(this.getUserId(), mEditorData));
			} else {
				this.getLocal().update(new RecalculateBatchTaskEditorSessionCSO(this.getUserId(), mEditorData));
			}

			if (timer != null) {
				timer.logDebug("update", mEditorData.getPrimaryKey());
			}

		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public int issueRecalculateBatchTask(EntityRef ref, int userId) throws CPException, ValidationException {
		return this.issueRecalculateBatchTask(ref, userId, 0);
	}

	public int issueRecalculateBatchTask(EntityRef ref, int userId, int issuingTaskId) throws CPException, ValidationException {
		boolean id = false;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			int taskId;
			if (this.isRemoteConnection()) {
				taskId = this.getRemote().issueRecalculateBatchTask(ref, userId, issuingTaskId);
			} else {
				taskId = this.getLocal().issueRecalculateBatchTask(ref, userId, issuingTaskId);
			}

			if (timer != null) {
				timer.logDebug("issueRecalculateBatchTask");
			}

			return taskId;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public int issueTestTask(Integer testid, int userId) throws CPException, ValidationException {
		boolean id = false;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			int id1;
			if (this.isRemoteConnection()) {
				id1 = this.getRemote().issueTestTask(testid, userId);
			} else {
				id1 = this.getLocal().issueTestTask(testid, userId);
			}

			if (timer != null) {
				timer.logDebug("issueTestTask");
			}

			return id1;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public int issueTestRollbackTask() throws CPException, ValidationException {
		boolean id = false;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			int id1;
			if (this.isRemoteConnection()) {
				id1 = this.getRemote().issueTestRollbackTask(this.getUserId());
			} else {
				id1 = this.getLocal().issueTestRollbackTask(this.getUserId());
			}

			if (timer != null) {
				timer.logDebug("issueTestRollbackTask");
			}

			return id1;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/RecalculateBatchTaskEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/RecalculateBatchTaskEditorSessionLocalHome";
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
