package com.cedar.cp.ejb.api.importtask;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionCSO;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionSSO;
import com.cedar.cp.dto.importtask.ImportTaskImpl;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionHome;
import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionLocal;
import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionLocalHome;
import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionRemote;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEditorSessionSEJB;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ImportTaskEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/ImportTaskEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/ImportTaskEditorSessionLocalHome";
	protected ImportTaskEditorSessionRemote mRemote;
	protected ImportTaskEditorSessionLocal mLocal;
	public static ImportTaskEditorSessionSEJB server = new ImportTaskEditorSessionSEJB();
	private Log mLog = new Log(this.getClass());

	public ImportTaskEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public ImportTaskEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private ImportTaskEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//		if (this.mRemote == null) {
//			String jndiName = this.getRemoteJNDIName();
//
//			try {
//				ImportTaskEditorSessionHome e = (ImportTaskEditorSessionHome) this.getHome(jndiName, ImportTaskEditorSessionHome.class);
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

		return server;
	}

	private ImportTaskEditorSessionSEJB getLocal() throws CPException {
//		if (this.mLocal == null) {
//			try {
//				ImportTaskEditorSessionLocalHome e = (ImportTaskEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
//				this.mLocal = e.create();
//			} catch (CreateException e) {
//				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), e);
//			}
//		}
//
//		return this.mLocal;
		return server;
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

	public ImportTaskEditorSessionSSO getNewItemData() throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ImportTaskEditorSessionSSO e = null;
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

	public ImportTaskEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ImportTaskEditorSessionSSO e = null;
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

	public ImportTaskPK insert(ImportTaskImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ImportTaskPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().insert(new ImportTaskEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().insert(new ImportTaskEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public ImportTaskPK copy(ImportTaskImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ImportTaskPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().copy(new ImportTaskEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().copy(new ImportTaskEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public void update(ImportTaskImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().update(new ImportTaskEditorSessionCSO(this.getUserId(), editorData));
			} else {
				this.getLocal().update(new ImportTaskEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("update", editorData.getPrimaryKey());
			}

		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public int issueImportTask(EntityRef ref, int userId, String externalSystemVisId) throws CPException, ValidationException {
		return this.issueImportTask(ref, userId, 0, externalSystemVisId);
	}

	public int issueImportTask(EntityRef ref, int userId, int issuingTaskId, String externalSystemVisId) throws CPException, ValidationException {
		boolean id = false;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			int taskId;
			if (this.isRemoteConnection()) {
				taskId = this.getRemote().issueImportTask(ref, userId, issuingTaskId, externalSystemVisId);
			} else {
				taskId = this.getLocal().issueImportTask(ref, userId, issuingTaskId, externalSystemVisId);
			}

			if (timer != null) {
				timer.logDebug("issueImportTask");
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
		return "ejb/ImportTaskEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/ImportTaskEditorSessionLocalHome";
	}
}
